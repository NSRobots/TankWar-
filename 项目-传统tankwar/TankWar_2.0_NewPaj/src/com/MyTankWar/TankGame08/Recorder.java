package com.MyTankWar.TankGame08;

import java.io.*;
import java.util.Vector;

/**
 * 该类用于记录相关信息，和文件交互
 */
public class Recorder {
    //记录我方击毁tank数量
    private static int index = 0;
    //定义一个IO对象
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static final String recodeFile = "src/图片素材/recordeFile.txt";

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        Recorder.index = index;
    }

    //当我方tank击毁enemy时，index++
    public static void addIndex() {
        Recorder.index++;
    }

    /**
     * 当游戏退出时，进行“分数记录”处理
     * 游戏退出时，记录所有坦克的位置
     **/
    public static void keepRecorde() {
        try {
            //记录分数
            bw = new BufferedWriter(new FileWriter(recodeFile));
            bw.write(index + "\r\n");
            //记录坦克们的位置
            for (int i = 0; i < Tank.tanks.size(); i++) {
                Tank allTank = Tank.tanks.get(i);
                if (allTank.isLive) {
                    String pRecorde = allTank.getX() + " " + allTank.getY() + " " + allTank.getDirect();
                    System.out.println("已存tank" + i + ",方向为：" + allTank.getDirect());
                    bw.write(pRecorde + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 用于恢复所有tank的信息
     * 在游戏启动时调用
     */
    public static Vector<Node> recoverXYDToNode() {
        //存储所有的node信息，用于恢复分数和tank的位置
        Vector<Node> nodes = new Vector<>();

        try {
            br = new BufferedReader(new FileReader(recodeFile));
            //读取第一行的信息
            index = Integer.parseInt(br.readLine());
            //循环读取XYD
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(
                        Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                //别忘了加入
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }
}
