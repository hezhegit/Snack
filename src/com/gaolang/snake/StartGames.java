package com.gaolang.snake;

import javax.swing.*;

public class StartGames {

    public static void main(String[] args){
        //1.绘制一个静态窗口JFrame   GUI
        JFrame frame = new JFrame("贪吃蛇小游戏");
        //设置界面的大小
        frame.setBounds(10,10,900,720);
        frame.setResizable(false);//窗口大小不可以改变
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口关闭事件

        //2.面板 JPanel
        frame.add(new GamePanel());

        frame.setVisible(true);//窗口可见


    }
}
