package com.MyTankWar.PanelTest;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel() {
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/EXP_1.jpg"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/EXP_2.jpg"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/EXP_3.jpg"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.fillRect(0, 0, 1000, 750);//填充画板，默认黑色
        g.drawImage(image2, 100, 100, 60, 60, Color.BLACK, this);
    }
}
