package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Objects;

/**
 * @author Andrew Gilligan
 * @version 1.0
 * @since 03/03/2024
 */

public class GUI extends JPanel {

    final int tileSize = 16; // initialise the tile size
    final int scale = 3; // scale factor
    final int size = tileSize * scale; // screen size
    public static final int maxScreenColumns = 23; // define x and y
    public static final int maxScreenRows = 12; // define x and y
    public final int screenWidth = size * maxScreenColumns; // compute screen width
    public final int screenHeight = size * maxScreenRows; // compute screen height
    static Mines[][] mineMap = new Mines[maxScreenRows][maxScreenColumns]; // initialise list to store mines
    static JPanel tiles = new JPanel(); // initialise tiles as a panel

    static boolean gameOver = false; // game over boolean
    public static JFrame game = new JFrame(); // game frame
    public JFrame start = new JFrame(); // start frame

    JLabel textLabel = new JLabel(); // holds text

    public static int score; // score counter (why doesn't this work??)

    JPanel textPanel = new JPanel(); // panel to display text

    JMenuBar menuBar = new JMenuBar(); // menu bar

    JMenu menu = new JMenu("Menu"); // menu in the menu bar


    public GUI() {

    }

    /**
     * This method initialises the start menu and relevant buttons/text
     */

    public void startView(){

        start.setSize(screenWidth, screenHeight); // set params of start menu main frame
        start.setLocationRelativeTo(null);
        start.setResizable(false);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setLayout(new BorderLayout());

        JPanel titleTxt = new JPanel(); // set params of the title
        titleTxt.setBounds(300, 400, 600, 150);
        titleTxt.setBackground(Color.black);
        JLabel titleLabel = new JLabel("Minesweeper");
        titleLabel.setForeground(Color.white);
        titleTxt.setLayout(new BorderLayout());
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel startButtonPanel = new JPanel(); // set params of the start button
        startButtonPanel.setBounds(250, 350, 200, 100);
        startButtonPanel.setBackground(Color.black);
        startButtonPanel.setLayout(new BorderLayout());

        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);

        JPanel instructionButtonPanel = new JPanel(); // set params of the instruction button
        instructionButtonPanel.setBounds(250, 350, 200, 100);
        instructionButtonPanel.setBackground(Color.black);
        instructionButtonPanel.setLayout(new BorderLayout());

        JButton instructionButton = new JButton("Instructions");
        instructionButton.setBackground(Color.black);
        instructionButton.setForeground(Color.white);

        JPanel quitPanel = new JPanel(); // set params of the quit button
        quitPanel.setBounds(200, 300, 200, 100);
        quitPanel.setBackground(Color.black);
        quitPanel.setLayout(new BorderLayout());

        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(Color.black);
        quitButton.setForeground(Color.white);

        titleTxt.add(titleLabel, BorderLayout.NORTH); // add the relevant text/buttons to their panels
        startButtonPanel.add(startButton, BorderLayout.SOUTH);
        instructionButtonPanel.add(instructionButton, BorderLayout.SOUTH);
        quitPanel.add(quitButton, BorderLayout.SOUTH);
        start.add(quitPanel); // add the panels to the frame
        start.add(instructionButtonPanel);
        start.add(titleTxt);
        start.add(startButtonPanel);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    start.setVisible(false); // start the game activity on start button click
                    gameView();
                }
            }
        });

        instructionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){ // open up instruction webpage on instruction button click
                    openURL(URI.create("www.wikihow.com/Play-Minesweeper#:~:text=Use%20the%20mouse's%20left%20and,flag%20squares%20that%20contain%20mines."));
                }
            }
        });

        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    System.exit(0); // terminate the process on quit button click
                }
            }
        });

        start.setVisible(true);
    }

    /**
     * This method initialises the game activity and all the logic from the classes is implemented here
     */
    public void gameView(){

        game.setSize(screenWidth, screenHeight); // initialise game frame params
        game.setLocationRelativeTo(null);
        game.setResizable(false);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLayout(new BorderLayout());

        tiles.setLayout(new GridLayout(maxScreenRows, maxScreenColumns)); // set button layout to grid style
        game.add(tiles);

        for(int r = 0; r < maxScreenRows; r++){
            for(int c = 0; c < maxScreenColumns; c++){ // iterate through the grid
                Mines mines = new Mines(r, c);
                mineMap[r][c] = mines;
                mines.setFocusable(false);
                mines.setMargin(new Insets(0, 0, 0, 0));
                tiles.add(mines); // add mines to the tiles in the grid
                Mines.placeMines();
                mines.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Mines tile = (Mines)e.getSource(); // get the co-ordinates of a clicked tile

                        if(e.getButton() == MouseEvent.BUTTON1){
                            if (Objects.equals(tile.getText(), "")) { // If tile is currently un-clicked
                                if(Mines.minesList.contains(tile)){ // If tile contains a mine on click
                                    Mines.showMines(); // show all current mines (flip all tiles)
                                    gameOver = true;
                                    gameOver();
                                } else { // if you have not clicked a mine
                                    Mines.validateArea(tile.r, tile.c); // run the checking algorithm
                                }
                            }
                        } else if(e.getButton() == MouseEvent.BUTTON3){
                            if(Objects.equals(tile.getText(), "") && tile.isEnabled()){ // only if there is no text and it is enabled
                                tile.setText("\uD83D\uDEA9"); // flag a tile
                            } else if(Objects.equals(tile.getText(), "\uD83D\uDEA9")){
                                tile.setText("");
                            }
                        }
                    }
                });
            }
        }
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper: " + score);
        textLabel.setOpaque(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        game.add(textPanel, BorderLayout.NORTH);
        game.setVisible(true);

        JMenuItem m1 = new JMenuItem("Restart"); // initialise the menu bar
        JMenuItem m2 = new JMenuItem("Exit to main menu");
        JMenuItem m3 = new JMenuItem("Exit to desktop");

        menu.add(m1);
        menu.add(m2);
        menu.add(m3);

        menuBar.add(menu);
        game.setJMenuBar(menuBar);

        m1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                game.setVisible(false);
                gameView();
            }
        });

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
            String [] data = {"Try again", "Exit to desktop"};
            int n = JOptionPane.showOptionDialog(this, "Game over", "Select an option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, menu.getIcon(), data, data[0]);
            if(n == JOptionPane.YES_OPTION){
                startView();
            } else if(n == JOptionPane.NO_OPTION){
                System.exit(0);
            }
        }
    }

    public Dimension getTileSize(){
        return tiles.getSize();
    }

}

