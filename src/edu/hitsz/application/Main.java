package edu.hitsz.application;

import edu.hitsz.DAO.Daolmpl;
import edu.hitsz.DAO.GameRecord;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

/*
    public static final int WINDOW_WIDTH = 768;
    public static final int WINDOW_HEIGHT = 1152;
 */

    public static void main(String[] args) throws IOException {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        frame.add(game);
        frame.setVisible(true);
        game.action();

        Daolmpl dao=new Daolmpl();
        //TODO 从文件中读入数据

        Path path= Paths.get("Records.txt");



        if(game.isGameOverFlag()){
            //如果游戏结束了
            String playerName=new String("Me");
            SimpleDateFormat time=new SimpleDateFormat("yyyy MM dd HH mm ss");
            Date date=new Date();
            GameRecord gameRecord=new GameRecord(date,playerName,game.getScore(),dao.getAll().size());
            dao.Add(gameRecord);
        }
    }
}
