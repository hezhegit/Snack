package com.gaolang.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int length;//蛇的长度
    int[] snackX = new int[600];//蛇的坐标X
    int[] snackY = new int[600];//蛇的坐标Y
    String fx;//蛇头方向
    boolean isStart = false;//游戏是否开始

    Timer timer = new Timer(100,this);

    //定义一个食物
    int foodX;
    int foodY;
    Random random = new Random();

    //死亡判断
    boolean isFail = false;

    //积分系统
    int score;

    //构造器
    public GamePanel(){
        init();
        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();//让时间动起来
    }

    //初始化
    public void init(){
        //初始长度3
        length = 3;
        //蛇头部初始坐标
        snackX[0] = 100;
        snackY[0] = 100;
        //第一个身体坐标
        snackX[1] = 75;
        snackY[1] = 100;
        //第二个身体坐标
        snackX[2] = 50;
        snackY[2] = 100;
        //蛇头方向
        fx = "R";
        //食物
        foodX = 25 + 25* random.nextInt(5);
        foodY = 75 + 75* random.nextInt(5);

        score = 0;
    }

    //画板
    //Graphics：画笔
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        this.setBackground(Color.BLACK);//设置背景颜色

        //放置头部图片
        Data.header.paintIcon(this,g,25,11);

        //绘制游戏区域
        g.fillRect(25,75,850,600);

        //绘制蛇
        if (fx.equals("R")){
            Data.right.paintIcon(this,g,snackX[0],snackY[0]);
        }else if (fx.equals("L")){
            Data.left.paintIcon(this,g,snackX[0],snackY[0]);
        }else if (fx.equals("U")){
            Data.up.paintIcon(this,g,snackX[0],snackY[0]);
        }else if (fx.equals("D")){
            Data.down.paintIcon(this,g,snackX[0],snackY[0]);
        }


        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this,g,snackX[i],snackY[i]);//蛇的身体
        }

        //画积分
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度"+length,750,35);
        g.drawString("分数"+score,750,50);

        //画食物
        Data.food.paintIcon(this,g,foodX,foodY);

        if(isStart==false){
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,300);
        }

        //失败提醒
        if (isFail){
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏失败，按下空格重新开始",200,300);
        }
    }

    //implements KeyListener实现键盘监听
    @Override
    public void keyTyped(KeyEvent e) {
        //键盘按下，弹起：敲击
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //键盘按下，未释放

        //获取按下键盘的是那个键
        int keyCode = e.getKeyCode();
        if (keyCode==KeyEvent.VK_SPACE){
            if (isFail){
                //失败，游戏重新开始
                isFail = false;
                init();//重新初始化游戏
            }else {
                isStart = !isStart;
            }

            repaint();//刷新界面
        }
        if (keyCode==KeyEvent.VK_LEFT){
            fx = "L";
        }else if (keyCode==KeyEvent.VK_RIGHT){
            fx = "R";
        }else if (keyCode==KeyEvent.VK_UP){
            fx = "U";
        }else if (keyCode==KeyEvent.VK_DOWN){
            fx = "D";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //释放某个键
    }



    //定时器，监听时间  帧  执行定时操作

    @Override
    public void actionPerformed(ActionEvent e) {
        //游戏开始状态
        if (isStart && isFail==false){
            //右移
            for (int i = length-1; i > 0; i--) {//身体向前移动
                snackX[i] = snackX[i-1];
                snackY[i] = snackY[i-1];
            }

            //通过控制方向让头部移动
            if (fx.equals("R")){
                snackX[0] += 25;
                //边界判断
                if (snackX[0]>850){
                    snackX[0] = 25;
                }
            }else if (fx.equals("L")){
                snackX[0] -= 25;
                //边界判断
                if (snackX[0]<25){
                    snackX[0] = 850;
                }
            }else if (fx.equals("U")){
                snackY[0] -= 25;
                //边界判断
                if (snackY[0]<75){
                    snackY[0] = 650;
                }
            }else if (fx.equals("D")){
                snackY[0] += 25;
                //边界判断
                if (snackY[0]>650){
                    snackY[0] = 75;
                }
            }

            //如果蛇头与食物坐标重合，表示吃到食物了
            if (snackX[0]==foodX && snackY[0]==foodY){
                //长度+1
                length++;
                score += 10;
                snackX[length-1] = length-1;
                snackY[length-1] = length-1;
                //重新生成食物
                foodX = 25 + 25* random.nextInt(5);
                foodY = 75 + 75* random.nextInt(5);

            }

            //结束判断
            for (int i = 1; i < length; i++) {
                if (snackX[0]==snackX[i]&&snackY[0]==snackY[i]){//撞到身体
                    isFail = true;
                }

            }



            repaint();//刷新界面
        }
        timer.start();//让时间动起来
    }


}
