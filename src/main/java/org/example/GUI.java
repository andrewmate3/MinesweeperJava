package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Andrew Gilligan
 * @version 1.0
 * @since 03/03/2024
 */

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
    public static JFrame game = new JFrame();
    public JFrame start = new JFrame();

    JLabel textLabel = new JLabel();

    public static int score;

    JPanel textPanel = new JPanel();

    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("Menu");


    public GUI() {

    }

    public void startView(){

        start.setSize(screenWidth, screenHeight);
        start.setLocationRelativeTo(null);
        start.setResizable(false);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setLayout(new BorderLayout());

        JPanel titleTxt = new JPanel();
        titleTxt.setBounds(300, 400, 600, 150);
        titleTxt.setBackground(Color.black);
        JLabel titleLabel = new JLabel("Minesweeper");
        titleLabel.setForeground(Color.white);
        titleTxt.setLayout(new BorderLayout());
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel startButtonPanel = new JPanel();
        startButtonPanel.setBounds(250, 350, 200, 100);
        startButtonPanel.setBackground(Color.black);
        startButtonPanel.setLayout(new BorderLayout());

        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);

        JPanel instructionButtonPanel = new JPanel();
        instructionButtonPanel.setBounds(250, 350, 200, 100);
        instructionButtonPanel.setBackground(Color.black);
        instructionButtonPanel.setLayout(new BorderLayout());

        JButton instructionButton = new JButton("Instructions");
        instructionButton.setBackground(Color.black);
        instructionButton.setForeground(Color.white);

        JPanel quitPanel = new JPanel();
        quitPanel.setBounds(200, 300, 200, 100);
        quitPanel.setBackground(Color.black);
        quitPanel.setLayout(new BorderLayout());

        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(Color.black);
        quitButton.setForeground(Color.white);

        titleTxt.add(titleLabel, BorderLayout.NORTH);
        startButtonPanel.add(startButton, BorderLayout.SOUTH);
        instructionButtonPanel.add(instructionButton, BorderLayout.SOUTH);
        quitPanel.add(quitButton, BorderLayout.SOUTH);
        start.add(quitPanel);
        start.add(instructionButtonPanel);
        start.add(titleTxt);
        start.add(startButtonPanel);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    start.setVisible(false);
                    gameView();
                }
            }
        });

        instructionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    openURL(URI.create("www.wikihow.com/Play-Minesweeper#:~:text=Use%20the%20mouse's%20left%20and,flag%20squares%20that%20contain%20mines."));
                }
            }
        });

        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    System.exit(0);
                }
            }
        });

        start.setVisible(true);
    }

    public void gameView(){

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
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: " + score);
        textLabel.setOpaque(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        game.add(textPanel, BorderLayout.NORTH);
        game.setVisible(true);

        JMenuItem m1 = new JMenuItem("Save");
        JMenuItem m2 = new JMenuItem("Exit to main menu");
        JMenuItem m3 = new JMenuItem("Exit to desktop");

        menu.add(m1);
        menu.add(m2);
        menu.add(m3);

        menuBar.add(menu);
        game.setJMenuBar(menuBar);

        m2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    game.setVisible(false);
                    startView();
                }
            }
        });

        m3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

    }

    public void openURL(URI uri){
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)){
            try{
                desktop.browse(uri);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void gameOver(){
        if (gameOver){
            game.setVisible(false);
            startView();
            JOptionPane.showMessageDialog(this, "Game Over");
        }
    }

}

