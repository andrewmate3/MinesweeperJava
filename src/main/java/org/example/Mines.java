package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Andrew Gilligan
 * @version 1.0
 * @since 02/03/2024
 */

public class Mines extends JButton {
    public int r;
    public int c;

    static int counter = 0;

    public static int mineCount = 50;

    static ArrayList<Mines> minesList;

    /**
     * Constructor initialises co-ordinates of a mine
     * @param r Map co-ordinates of a mine (rows/x)
     * @param c Map co-ordinates of a mine (columns/y)
     */
    public Mines(int r, int c){
        this.r = r;
        this.c = c;
    }

    /**
     * This method creates a list of mines and places mines at a
     * random location on the board
     */
    public static void placeMines(){

        minesList = new ArrayList<>(); // initialise array list
        Random rand = new Random(); // initialise random

        for(int i = 0; i < mineCount; i++){ //loop through list and add random values as mines
           int r = rand.nextInt(GUI.maxScreenRows);
           int c = rand.nextInt(GUI.maxScreenColumns);

           Mines mine = GUI.mineMap[r][c];
           if(!minesList.contains(mine)){
               minesList.add(mine); // if space doesn't already have a mine, add
           }
        }
    }

    /**
     * This method shows the mines as a bomb emoji (unicode)
     */
    public static void showMines(){
        for(Mines mine : minesList){ // iterate through the mine list
            mine.setText("\uD83D\uDCA3"); // set text
        }
    }

    /**
     * This method recursively checks an 8 x 8 grid around r, c to see if there
     * is a mine and to set the text at the tile r, c for how many mines are in the grid
     * @param r x co-ordinate of mine
     * @param c y co-ordinate of mine
     */
    public static void validateArea(int r, int c){

        if (r < 0 || r >= GUI.maxScreenRows || c < 0 || c >= GUI.maxScreenColumns) {
            return; // if the new co-ordinate is off the grid or negative, cancel (recursion base case)
        }

        Mines tile = GUI.mineMap[r][c]; // initialise tile
        if(!tile.isEnabled()){ // if tile is already clicked, return (another base case)
            return;
        }
        tile.setEnabled(false); // make sure tile is not enabled
        int minesTotal = 0; // initialise variable to store number of mines


        tile.setEnabled(false);
        counter += 1; // add a counter to record how many tiles are left

        for(int i = c - 1; i <= c + 1; i++){ // loop through the 8 x 8 grid
            minesTotal += checkProximity(r - 1, i); // validate top row
            minesTotal += checkProximity(r + 1, i); // validate bottom row
            if(i != c){
                minesTotal+= checkProximity(r, i); // validate middle row bar the original square (we know is not a mine)
            }
        }

        if(minesTotal > 0){ // if there are mines in the counter
            tile.setText(Integer.toString(minesTotal)); // set the text of the tile to the number of mines in the tiles' grid

        } else {

            tile.setText(""); // otherwise set this tile to blank (0 mines in its grid)

             validateArea(r - 1, c - 1); // recursively perform the same process to the grid to set the "shockwave" motion
             validateArea(r - 1, c);
             validateArea(r - 1, c + 1);
             validateArea(r ,c - 1);
             validateArea(r ,c + 1);
             validateArea(r + 1, c - 1);
             validateArea(r + 1, c);
             validateArea(r + 1, c + 1);

        }

        if(counter == (GUI.maxScreenRows * GUI.maxScreenColumns) - minesList.size()){ // finish the game once there are no tiles left
            JOptionPane.showMessageDialog(null, "Mines cleared");
        }
    }

    /**
     * Either one of 3 cases are true in minesweeper
     * 1- An adjacent tile is out of bounds of the map -> return 0
     * 2- An adjacent tile has a mine under it -> increment the mine counter
     * 3- An adjacent tile has no mine under it -> return 0
     * @param r x co-ordinates of tile
     * @param c y co-ordinates of tile
     * @return return a 1 or 0 dependent on if there is a mine under a tile so value can be added to mine counter
     */
    public static int checkProximity(int r, int c) {

        if(r < 0 || r >= GUI.maxScreenRows || c < 0 || c >= GUI.maxScreenColumns){
            return 0;
        }
        if(minesList.contains(GUI.mineMap[r][c])){
            return 1;
        }
            return 0;
    }

}
