package com.MyTankWar.TankGame08;

import java.awt.*;
import java.util.Vector;

/**
 * 玩家坦克
 */
public class Hero extends Tank {
    //玩家tank最多可以发射5发子弹,故用Vector进行添加删除
    Vector<Bullet> heroBullets = new Vector<>();
    Bullet bullet = null;//？全局的bullet,留还是不留？


    public Hero(int x, int y) {
        super(x, y);
        direct = 1;//玩家初始化是向上的
        type = 0;
    }

    public Hero(int x, int y, int direct) {
        super(x, y);
        this.direct = direct;
        type = 0;
    }

    //hero的绘制
    public void drawHero(Graphics g) {
        this.drawTank(x, y, g, direct, type);
    }

    //hero的子弹发射
    public void shot() {
        //Vector只能装4个Bullet
        if (heroBullets.size() > 3) {
            return;
        }

        switch (direct) {
            case 0:
                bullet = new Bullet(x + 20, y, direct);
                heroBullets.add(bullet);
                break;
            case 1:
                bullet = new Bullet(x + 60, y + 20, direct);
                heroBullets.add(bullet);
                break;
            case 2:
                bullet = new Bullet(x + 20, y + 60, direct);
                heroBullets.add(bullet);
                break;
            case 3:
                bullet = new Bullet(x, y + 20, direct);
                heroBullets.add(bullet);
                break;
        }
        //启动bullet的运动 线程
        new Thread(bullet).start();
    }
}
