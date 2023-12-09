package com.MyTankWar.TankGame08;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

public class Tank implements Serializable {
    protected int x;
    protected int y;
    protected int type;   //确认tank 的类型:0-玩家,1-敌人
    protected int direct;  //tank的方向值--0.向上，1.向右，2.向下，3.向左
    protected int speed;
    //判断tank是否还存活
    protected boolean isLive = true;
    //Vector,记录所有的坦克;用于判断tank位置是否会相触,也顺便用于记录tank位置
    public static Vector<Tank> tanks = new Vector<>();

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 10;
    }

    /**
     * 编写方法，画出坦克;位置应该使用子类的位置
     *
     * @param x      坦克的x坐标
     * @param y      坦克的y坐标
     * @param g      画笔
     * @param direct 坦克方向(上下左右)
     * @param type   tank的类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        //根据不同类型的tank，生成对应的坦克
        switch (type) {
            case 0: // 我们的坦克
                g.setColor(Color.CYAN);
                break;
            case 1: //敌人的坦克
                g.setColor(Color.yellow);
                break;
        }
        //调用"Tank"类 的绘制
        /**
         *根据不同的方向，绘制对应方向的tank
         * direct--0.向上，1.向右，2.向下，3.向左
         */
        switch (direct) {
            case 0://向上
                g.fill3DRect(x, y, 10, 60, false);//画出tank左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出tank右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出tank盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画tank圆盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画坦克的炮口
                break;
            case 1://向右
                g.fill3DRect(x, y, 60, 10, false);//画出tank左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出tank右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出tank盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画tank圆盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画坦克的炮口
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);//画出tank左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出tank右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出tank盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画tank圆盖子
                g.drawLine(x + 20, y + 60, x + 20, y + 30);//画坦克的炮口
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);//画出tank左边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出tank右边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出tank盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画tank圆盖子
                g.drawLine(x, y + 20, x + 30, y + 20);//画坦克的炮口
                break;
        }
    }

    /**
     * 利用Tank.tanks,判断tank位置是否重合
     *
     * @return boolean值
     */
    public boolean isTauch() {
        for (int i = 0; i < Tank.tanks.size(); i++) {
            Tank otherTank = tanks.get(i);
            if (otherTank != this) {//先排除自己
                switch (direct) {
                    case 0://当前向上移动时
                        if (otherTank.getDirect() == 0 || otherTank.getDirect() == 2) {//如图1情况
                            if (this.getX() >= otherTank.getX()
                                    && this.getX() <= otherTank.getX() + 40
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 60) {
                                return true;
                            }
                            if (this.getX() + 40 >= otherTank.getX()
                                    && this.getX() + 40 <= otherTank.getX() + 40
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (otherTank.getDirect() == 1 || otherTank.getDirect() == 3) {//如图2情况
                            //敌人坦克范围 x-[enemy.getX(),enemy.getX()+60]
                            //           y-[enemy.getY(),enemy.getY()+40]
                            //当前tank 左上角坐标[this.getX(),this.getY()]
                            if (this.getX() >= otherTank.getX()
                                    && this.getX() <= otherTank.getX() + 60
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 40) {
                                return true;
                            }
                            //当前tank 右上角坐标[this.getX(),this.getY()]
                            if (this.getX() + 40 >= otherTank.getX()
                                    && this.getX() + 40 <= otherTank.getX() + 60
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 40) {
                                return true;
                            }
                        }
                        break;
                    case 1://向右
                        if (otherTank.getDirect() == 0 || otherTank.getDirect() == 2) {//如图3情况
                            //enemy范围 x-[enemy.getX(),enemy.getX()+40]
                            //      y-[enemy.getY(),enemy.getY()+60]
                            //当前tank的 右上角坐标 [this.getX()+60,this.getY()]
                            if (this.getX() + 60 >= otherTank.getX()
                                    && this.getX() + 60 <= otherTank.getX() + 40
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 60) {
                                return true;
                            }
                            //当前tank的 右下角坐标 [this.getX()+60,this.getY()+40]
                            if (this.getX() + 60 >= otherTank.getX()
                                    && this.getX() + 60 + 40 <= otherTank.getX() + 40
                                    && this.getY() + 40 >= otherTank.getY()
                                    && this.getY() + 40 <= otherTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (otherTank.getDirect() == 1 || otherTank.getDirect() == 3) {//如图4情况
                            // enemy范围 x-[enemy.getX(),enemy.getX()+60]
                            //      y-[enemy.getY(),enemy.getY()+40]
                            //当前tank的 右上角坐标 [this.getX()+60,this.getY()]
                            if (this.getX() + 60 >= otherTank.getX()
                                    && this.getX() + 60 <= otherTank.getX() + 60
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 40) {
                                return true;
                            }
                            //当前tank的 右上角坐标 [this.getX()+60,this.getY()+40]
                            if (this.getX() + 60 >= otherTank.getX()
                                    && this.getX() + 60 + 40 <= otherTank.getX() + 60
                                    && this.getY() + 40 >= otherTank.getY()
                                    && this.getY() + 40 <= otherTank.getY() + 40) {
                                return true;
                            }
                        }
                        break;
                    case 2://向下
                        if (otherTank.getDirect() == 0 || otherTank.getDirect() == 2) {//如图5情况
                            // enemy范围 x-[enemy.getX(),enemy.getX()40]
                            //      y-[enemy.getY(),enemy.getY()+60]
                            //当前tank的 左下角坐标 [this.getX()+60,this.getY()]
                            if (this.getX() + 60 >= otherTank.getX()
                                    && this.getX() + 60 <= otherTank.getX() + 40
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 60) {
                                return true;
                            }//当前tank的 右下角坐标 [this.getX()+60,this.getY()+40]
                            if (this.getX() + 60 >= otherTank.getX()
                                    && this.getX() + 60 <= otherTank.getX() + 40
                                    && this.getY() + 40 >= otherTank.getY()
                                    && this.getY() + 40 <= otherTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (otherTank.getDirect() == 1 || otherTank.getDirect() == 3) {//如图6情况
                            //enemy范围 x-[enemy.getX(),enemy.getX()+60]
                            //      y-[enemy.getY(),enemy.getY()+40]
                            //当前tank的 左下角坐标 [this.getX()+60,this.getY()]
                            if (this.getX() + 60 >= otherTank.getX()
                                    && this.getX() + 60 <= otherTank.getX() + 60
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 40) {
                                return true;
                            }//当前tank的 右下角坐标 [this.getX()+60,this.getY()+40]
                            if (this.getX() + 60 >= otherTank.getX()
                                    && this.getX() + 60 <= otherTank.getX() + 40
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 60) {
                                return true;
                            }
                        }
                        break;
                    case 3://向左
                        if (otherTank.getDirect() == 0 || otherTank.getDirect() == 2) {//如图7情况
                            // enemy范围 x-[enemy.getX(),enemy.getX()+40]
                            //      y-[enemy.getY(),enemy.getY()+60]
                            //当前tank的 左上角坐标 [this.getX(),this.getY()]
                            if (this.getX() >= otherTank.getX()
                                    && this.getX() <= otherTank.getX() + 40
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 60) {
                                return true;
                            }//当前tank的 左下角坐标 [this.getX(),this.getY()+40]
                            if (this.getX() >= otherTank.getX()
                                    && this.getX() <= otherTank.getX() + 40
                                    && this.getY() + 40 >= otherTank.getY()
                                    && this.getY() + 40 <= otherTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (otherTank.getDirect() == 1 || otherTank.getDirect() == 3) {//如图8情况
                            // enemy范围 x-[enemy.getX(),enemy.getX()+60]
                            //        y-[enemy.getY(),enemy.getY()+40]
                            //当前tank的 左上角坐标 [this.getX(),this.getY()]
                            if (this.getX() >= otherTank.getX()
                                    && this.getX() <= otherTank.getX() + 60
                                    && this.getY() >= otherTank.getY()
                                    && this.getY() <= otherTank.getY() + 40) {
                                return true;
                            }//当前tank的 左下角坐标 [this.getX(),this.getY()+40]
                            if (this.getX() >= otherTank.getX()
                                    && this.getX() <= otherTank.getX() + 60
                                    && this.getY() + 40 >= otherTank.getY()
                                    && this.getY() + 40 <= otherTank.getY() + 40) {
                                return true;
                            }
                        }
                        break;
                }
            }
        }
        return false;
    }

    public void moveUp() {
        //设置tank,使其移动时无法出界
        if (y <= 0 || isTauch()) {
            return;
        }
        y -= speed;
    }

    public void moveDown() {
        if (y >= 750 - 60 || isTauch()) {
            return;
        }
        y += speed;
    }

    public void moveLeft() {
        if (x <= 0 || isTauch()) {
            return;
        }
        x -= speed;
    }

    public void moveRight() {
        if (x >= 1000 - 60 || isTauch()) {
            return;
        }
        x += speed;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
