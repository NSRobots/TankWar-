package com.MyTankWar.TankGame08;


public class Bullet implements Runnable {
    private int x;
    private int y;
    private int speed;
    private int direction;  //根据hero的方向,决定子弹的方向
    private boolean isLive = true;

    public Bullet(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        speed = 30;
    }

    //发射子弹,就是开启一个线程,让子弹自己移动
    @Override
    public void run() {
        while (true) {
            switch (direction) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            //添加休眠，让子弹发射慢一些
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("bullet的x:" + x + " y:" + y);
            //出界时,子弹 线程销毁\停止
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                //System.out.println("子弹销毁");
                isLive = false;
                break;
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
