package com.MyTankWar.PanelTest;

import javax.swing.*;

public class MyMain extends JFrame {
    private MyPanel myPanel;

    public MyMain() {
        myPanel = new MyPanel();
        this.add(myPanel);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MyMain();
    }
}
