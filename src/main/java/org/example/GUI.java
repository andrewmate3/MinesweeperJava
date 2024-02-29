package org.example;

import javax.swing.*;
import java.awt.*;


public class GUI extends JPanel {

    final int tileSize = 16;
    final int scale = 3;

    final int size = tileSize * scale;
    public static final int maxScreenColumns = 23;
    public static final int maxScreenRows = 12;
    final int screenWidth = size * maxScreenColumns;
    final int screenHeight = size * maxScreenRows;
    static Mines[][] mineMap = new Mines[maxScreenRows][maxScreenColumns];
    static JPanel tiles = new JPanel();


    public GUI() {

        JFrame game = new JFrame();
        game.setSize(screenWidth, screenHeight);
        game.setLocationRelativeTo(null);
        game.setResizable(false);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLayout(new BorderLayout());

        tiles.setLayout(new GridLayout(maxScreenRows, maxScreenColumns));
        game.add(tiles);

        for(int r = 0; r < maxScreenRows; r++){
            for(int c = 0; c < maxScreenColumns; c++){
                Mines mines = new Mines(r, c);
                mineMap[r][c] = mines;
                mines.setFocusable(false);
                mines.setMargin(new Insets(0, 0, 0, 0));
                tiles.add(mines);
            }
        }

        game.setVisible(true);

    }
}

