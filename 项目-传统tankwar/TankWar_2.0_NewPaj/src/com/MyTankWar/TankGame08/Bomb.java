package com.MyTankWar.TankGame08;

/**
 * 炸弹动画类
 */
public class Bomb {
    int x, y;   //炸弹的坐标
    int life = 9;   //炸弹的生命周期
    boolean isLive = true; //是否还存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDowe() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
