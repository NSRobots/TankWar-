package com.MyTankWar.TankGame08;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class HspTankGame extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
    Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        HspTankGame hspTankGame = new HspTankGame();
    }

    public HspTankGame() {
        System.out.println("请输入选择 1：新游戏；2：继续上局");

        mp = new MyPanel(s.next());
        /*开启MyPanel的线程,让画板不断地重绘*/
        new Thread(mp).start();
        /**/
        this.add(mp);//把面板(游戏绘图区)加入
        this.setSize(1300, 750);
        this.addKeyListener(mp);//让JFrame 监听 键盘
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame中 增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecorde();
                System.exit(0);
            }
        });
    }
}
