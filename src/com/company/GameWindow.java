package com.company;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static java.lang.Math.cos;

public class GameWindow extends JFrame {

    private static GameWindow gameWindow;
    private static Long lastFrameTime;
    private static Image bg;
    private static Image leaf;
    private static Image gameOver;
    private static float leafLeft = 200;
    private static float leafTop = -100;
    private static float leafV = 200;
    private static int score;


    public static void main (String[] args) throws IOException {
        bg = ImageIO.read(GameWindow.class.getResourceAsStream("bg.jpg"));
        leaf = ImageIO.read(GameWindow.class.getResourceAsStream("leaf.png"));
        gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("gameover.png"));
        gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200,100);
        gameWindow.setSize(906,478);
        gameWindow.setResizable(false);
        lastFrameTime = System.nanoTime();
        gameField aGameField = new gameField();
        aGameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float leafRight = leafLeft + leaf.getWidth(null);
                float leafBottom = leafTop + leaf.getHeight(null);
                boolean isLeafCaught = (x >= leafLeft && x <= leafRight && y <= leafBottom && y >= leafTop);
                if (isLeafCaught) {
                    leafTop = -100;
                    leafLeft = (int)(Math.random() * (aGameField.getWidth() - leaf.getWidth(null)) - 200);
                    leafV = leafV + 20;
                    score++;
                    gameWindow.setTitle("Score: " + score);
                }
            }
        });
        gameWindow.add(aGameField);
        gameWindow.setVisible(true);
    }

    private static void onRepaint(Graphics g) {
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;

        leafLeft = leafLeft + leafV * deltaTime;
        leafTop = leafTop + leafV * deltaTime;

        g.drawImage(bg, 0, 0, null);
        g.drawImage(leaf, (int)leafLeft, (int)leafTop, null);
        if (leafTop > gameWindow.getHeight()) g.drawImage(gameOver, 330, 120, null);

    }

    private static class gameField extends JPanel {

        @Override
        protected void paintComponent (Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }



}



