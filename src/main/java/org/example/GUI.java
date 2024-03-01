package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;


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

    static boolean gameOver = false;



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
                Mines.placeMines();
                mines.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Mines tile = (Mines)e.getSource();

                        if(e.getButton() == MouseEvent.BUTTON1){
                            if (Objects.equals(tile.getText(), "")) { // If tile is currently un-clicked
                                if(Mines.minesList.contains(tile)){ // If tile contains a mine on click
                                    Mines.showMines(); // show all current mines (flip all tiles)
                                    gameOver = true;
                                    gameOver();
                                } else { // if you have not clicked a mine
                                    Mines.validateArea(tile.r, tile.c);
                                }
                            }
                        } else if(e.getButton() == MouseEvent.BUTTON3){
                            if(Objects.equals(tile.getText(), "") && tile.isEnabled()){
                                tile.setText("\uD83D\uDEA9");
                            } else if(Objects.equals(tile.getText(), "\uD83D\uDEA9")){
                                tile.setText("");
                            }
                        }
                    }
                });
            }
        }
        game.setVisible(true);
    }

    public void gameOver(){
        if (gameOver){
            JOptionPane.showMessageDialog(this, "Game Over");
        }
    }
}

