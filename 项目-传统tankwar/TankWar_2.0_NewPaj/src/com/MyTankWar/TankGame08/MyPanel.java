package com.MyTankWar.TankGame08;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * tank大战绘图区
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义 敌人
    Vector<Enemy> enemies = new Vector<>();
    int enemySize = 5;
    //定义3张图片,爆炸效果   //初始化爆炸图片对象
    Image image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/图片素材/EXP_1.png"));
    Image image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/图片素材/EXP_2.png"));
    Image image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/图片素材/EXP_3.png"));
    //定义对应敌人的 炸弹Vector
    Vector<Bomb> bombs = new Vector<>();
    //定义Nodes，存储上局游戏的信息
    Vector<Node> nodes = null;

    public MyPanel(String key) {
        switch (key) {
            case "1":
                hero = new Hero(0, 500);//初始化自己的坦克
                Tank.tanks.add(hero);//添加进tank列表

                //初始化敌人坦克 ？这里的size待修改？
                for (int i = 0; i < enemySize; i++) {
                    Enemy enemy = new Enemy(100 * (1 + i), 10);
                    enemies.add(enemy);
                    Tank.tanks.add(enemy);//添加进tank列表
                }
                break;
            case "2":
                //初始化上局的tank参数
                nodes = Recorder.recoverXYDToNode();
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //第一个位置必定为hero坦克，否则就为enemyTank
                    if (i != 0) {
                        Enemy enemy = new Enemy(node.getX(), node.getY(), node.getDirect());
                        enemies.add(enemy);
                        Tank.tanks.add(enemy);//添加进tank列表
                    } else {
                        hero = new Hero(node.getX(), node.getY());
                        Tank.tanks.add(hero);//添加进tank列表
                    }
                }

                break;
            default:
                System.out.println("输入有误！");
                break;
        }

        //System.out.println("当前有" + Tank.tanks.size() + "个tank");

        //初始化爆炸图片
//        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/图片素材/EXP_1.png"));
//        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/图片素材/EXP_2.png"));
//        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/图片素材/EXP_3.png"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充画板，默认黑色
        showInfo(g);

        //画出玩家坦克-封装方法
        if (hero.isLive()) { /* * hero tank 存活时,才绘制 * */
            hero.drawHero(g);
        }
        //画出玩家tank 打出的子弹,绘制不会覆盖
        /* 绘制hero的子弹,同绘制敌人子弹的原理 */
        for (int i = 0; i < hero.heroBullets.size(); i++) {
            //取出 hero的子弹
            Bullet h_b = hero.heroBullets.get(i);
            //绘制子弹,子弹存活 就绘制
            if (hero.heroBullets.get(i) != null && hero.heroBullets.get(i).isLive()) {
                g.fill3DRect(hero.heroBullets.get(i).getX() - 3, hero.heroBullets.get(i).getY() - 3, 5, 5, false);
            } else { //否则这颗子弹就不存在，就从Vector中移除它
                hero.heroBullets.remove(h_b);
            }
        }
        /**/

        //画出敌人tank
        for (int i = 0; i < enemies.size(); i++) {
            //取出 敌人坦克
            Enemy enemy = enemies.get(i);
            /* * else就是敌人tank被击中消失 * */
            if (enemy.isLive()) { /* * 当前 敌人tank 存活时,才绘制 * */
                //绘制 敌人坦克
                enemy.drawEnemy(g);

                /*绘制 所有敌人坦克 的子弹*/
                //与这里的enemy.E_bullets 有关？
                for (int j = 0; j < enemy.E_bullets.size(); j++) {
                    //取出 子弹
                    Bullet e_b = enemy.E_bullets.get(j);
                    //绘制 子弹 //存活就绘制
                    if (e_b.isLive()) {
                        g.draw3DRect(e_b.getX() - 3, e_b.getY() - 3, 3, 3, false);
                    } else {    //否则这颗子弹就不存在，就从Vector中移除它
                        System.out.println("移除子弹!");
                        enemy.E_bullets.remove(e_b);
                    }
                }
                /**/
            } else {/* * 并且移除当前这个敌人tank * */
                System.out.println("移除坦克!");
                enemies.remove(i);
            }
        }

        /* *绘制爆炸,并播放* */
        if (bombs != null) {
            for (int i = 0; i < bombs.size(); i++) {
                //取出爆炸
                Bomb b = bombs.get(i);
                //利用life值,模拟动画播放
                if (b.life > 6) {
                    System.out.println("爆炸1触发了,x:" + b.x + "y:" + b.y);
                    g.drawImage(image1, b.x, b.y, 60, 60, this);
                } else if (b.life > 3) {
                    System.out.println("爆炸2触发了,x:" + b.x + "y:" + b.y);
                    g.drawImage(image2, b.x, b.y, 60, 60, this);
                } else {
                    System.out.println("爆炸3触发了,x:" + b.x + "y:" + b.y);
                    g.drawImage(image3, b.x, b.y, 60, 60, this);
                }
                b.lifeDowe();//播放动画
                if (b.life == 0) {//播放结束
                    bombs.remove(b);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //下面逻辑，是针对hero的移动
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                hero.setDirect(0);
                hero.moveUp();
                break;
            case KeyEvent.VK_S:
                hero.setDirect(2);
                hero.moveDown();
                break;
            case KeyEvent.VK_A:
                hero.setDirect(3);
                hero.moveLeft();
                break;
            case KeyEvent.VK_D:
                hero.setDirect(1);
                hero.moveRight();
                break;
            case KeyEvent.VK_J:
                System.out.println("已经发射子弹");
                hero.shot();
                break;
        }
        //this.repaint(); //确保按键监听后，就重新绘制

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /*
        给面板单开一个线程，让其不停地重绘
        刷新tank地移动,以及子弹地移动
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /* 判断hero子弹是否击中了敌人的tank */
            //取出hero的4颗子弹依次判断
            for (int j = 0; j < hero.heroBullets.size(); j++) {
                if (hero.heroBullets.get(j) != null && hero.heroBullets.get(j).isLive()) {
                    /*由于我们不知道接下来哪个敌人将被击中
                    故 先取得所有敌人的tank,获取位置,都进行一遍hitTank方法判断*/
                    for (int i = 0; i < enemies.size(); i++) {
                        Enemy enemy = enemies.get(i);
                        hitTank(hero.heroBullets.get(j), enemy);
                    }
                }
            }
            /**/

            /* 判断Enemy子弹是否击中了hero的tank */
            /*由于我们不知道接下来那颗 子弹 将击中 hero
            故 先取得敌人所有的子弹,获取位置,都进行一遍hitTank方法判断*/
            for (int j = 0; j < enemies.size(); j++) {//先获取敌人
                Enemy enemy = enemies.get(j);
                for (int i = 0; i < enemy.E_bullets.size(); i++) {//再获取它们的子弹
                    if (enemy.E_bullets.get(i) != null && enemy.E_bullets.get(i).isLive()) {
                        //依次 取出
                        Bullet e_b = enemy.E_bullets.get(i);
                        hitTank(e_b, hero);
                    }
                }
            }
            /**/

            repaint();
        }
    }

    /**
     * 子弹打到 坦克 时的逻辑
     *
     * @param b 子弹对象
     * @param t 坦克对象
     */
    public void hitTank(Bullet b, Tank t) {
        switch (t.getDirect()) {
            case 0://坦克向上
            case 2://坦克向下
                if (b.getX() > t.getX() && b.getX() < t.getX() + 40
                        && b.getY() > t.getY() && b.getY() < t.getY() + 60) {
                    b.setLive(false);
                    t.setLive(false);
                    //删除该tank的记录
                    Tank.tanks.remove(t);

                    //当我方击毁一个敌方tank时，就Recorder.index++
                    if (t instanceof Enemy) {
                        Recorder.addIndex();
                    }

                    /* 测试信息 */
                    if (t instanceof Hero) {
                        System.out.println("击中我方坦克*************");
                    } else {
                        System.out.println("击中敌方坦克*************");
                    }
                    /* */
                    //hit中了tank,就new一个bomb
                    Bomb bomb = new Bomb(t.getX(), t.getY());
                    bombs.add(bomb);
                    //System.out.println("创建爆炸");
                }
                break;
            case 1:
            case 3:
                if (b.getX() > t.getX() && b.getX() < t.getX() + 60
                        && b.getY() > t.getY() && b.getY() < t.getY() + 40) {
                    b.setLive(false);
                    t.setLive(false);
                    //删除该tank的记录
                    Tank.tanks.remove(t);

                    //当我方击毁一个敌方tank时，就Recorder.index++
                    if (t instanceof Enemy) {
                        Recorder.addIndex();
                    }

                    /* 测试信息 */
                    if (t instanceof Hero) {
                        System.out.println("击中我方坦克*************");
                    } else {
                        System.out.println("击中敌方坦克*************");
                    }
                    /* */
                    //hit中了tank,就new一个bomb
                    Bomb bomb = new Bomb(t.getX(), t.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    /**
     * 编写方法，显示我方tank击毁敌方tank的信息
     */
    public void showInfo(Graphics g) {
        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("累计击毁敌方坦克", 1020, 30);
        new Tank(1020, 50).drawTank(1020, 50, g, 0, 1);
        g.setColor(Color.BLACK);//重新将颜色改为BLACK
        g.drawString(Recorder.getIndex() + "", 1080, 100);
    }
}
