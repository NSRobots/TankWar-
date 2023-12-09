package com.MyTankWar.TankGame08;

import java.awt.*;
import java.util.Vector;

public class Enemy extends Tank implements Runnable {
    //创建对应各辆敌人tank 的子弹Vector;
    Vector<Bullet> E_bullets = new Vector<>();//该容器唯一,且可全局调用
//    Bullet enemyBullet = null;

    public Enemy(int x, int y) {
        super(x, y);
        direct = 2;//敌人初始化是向下的
        type = 1;
        /* 每新建一个enemy,就生成对应的一个bullet对象 */
//        enemyBullet = new Bullet(x + 20, y + 60, direct);
//        E_bullets.add(enemyBullet);
//        new Thread(enemyBullet).start();//添加完毕就启动 发射子弹 的线程
        /**/
        /* 每新建一个enemy,就开启它的移动线程 */
        new Thread(this).start();
        /**/
    }

    public Enemy(int x, int y, int direct) {
        super(x, y);
        this.direct = direct;//敌人初始化是向下的
        type = 1;
        /* 每新建一个enemy,就生成对应的一个bullet对象 */
//        enemyBullet = new Bullet(x + 20, y + 60, direct);
//        E_bullets.add(enemyBullet);
//        new Thread(enemyBullet).start();//添加完毕就启动 发射子弹 的线程
        /**/
        /* 每新建一个enemy,就开启它的移动线程 */
        new Thread(this).start();
        /**/
    }

    public void drawEnemy(Graphics g) {
        this.drawTank(x, y, g, direct, type);
    }

    /**
     * 敌人自动射击的方法
     */
    public void autoShot() {
        switch (direct) {
            case 0:
                if (E_bullets.size() == 0) { //当前子弹vector没有子弹，就根据方向创建新子弹
                    Bullet enemyBullet = new Bullet(x + 20, y, direct);
                    E_bullets.add(enemyBullet); //并添加
                    new Thread(enemyBullet).start();//开启子弹的运动 线程
                }
                break;
            case 1:
                if (E_bullets.size() == 0) { //当前子弹vector没有子弹，就根据方向创建新子弹
                    Bullet enemyBullet = new Bullet(x + 60, y + 20, direct);
                    E_bullets.add(enemyBullet); //并添加
                    new Thread(enemyBullet).start();//开启子弹的运动 线程
                }
                break;
            case 2:
                if (E_bullets.size() == 0) { //当前子弹vector没有子弹，就根据方向创建新子弹
                    Bullet enemyBullet = new Bullet(x + 20, y + 60, direct);
                    E_bullets.add(enemyBullet); //并添加
                    new Thread(enemyBullet).start();//开启子弹的运动 线程
                }
                break;
            case 3:
                if (E_bullets.size() == 0) { //当前子弹vector没有子弹，就根据方向创建新子弹
                    Bullet enemyBullet = new Bullet(x, y + 20, direct);
                    E_bullets.add(enemyBullet); //并添加
                    new Thread(enemyBullet).start();//开启子弹的运动 线程
                }
                break;
        }
    }

    /**
     * 线程开启，敌人自动移动。当敌人tank存在时，就自动移动
     */
    @Override
    public void run() {
        //敌人移动的随机数
        int rNum = 0;

        while (isLive) {
            //不断重置
            rNum = (int) (Math.random() * 100 + 1);
            //休眠一下
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //选择移动
            if (rNum >= 0 && rNum < 25) {//向下移动
                direct = 2;
                enemyMoveDown();
            } else if (rNum >= 25 && rNum < 50) {//向右移动
                direct = 3;
                enemyMoveLeft();
            } else if (rNum >= 50 && rNum < 75) {
                direct = 0;
                enemyMoveUp();
            } else {
                direct = 1;
                enemyMoveRigth();
            }

            //敌人子弹自动射击
            autoShot();
        }
    }

    public void enemyMoveDown() {
        for (int i = 0; i < 15; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveDown();
        }
    }

    public void enemyMoveRigth() {
        for (int i = 0; i < 15; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveRight();
        }
    }

    public void enemyMoveUp() {
        for (int i = 0; i < 15; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveUp();
        }
    }

    public void enemyMoveLeft() {
        for (int i = 0; i < 15; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveLeft();
        }
    }

}
