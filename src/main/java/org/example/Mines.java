package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Mines extends JButton {
    public int r;
    public int c;

    static int tilesClicked = 0;

    public static int mineCount = 50;

    static ArrayList<Mines> minesList;

    public Mines(int r, int c){
        this.r = r;
        this.c = c;
    }

    public static void placeMines(){

        minesList = new ArrayList<>();
        Random rand = new Random();

        for(int i = 0; i < mineCount; i++){
           int r = rand.nextInt(GUI.maxScreenRows);
           int c = rand.nextInt(GUI.maxScreenColumns);

           Mines mine = GUI.mineMap[r][c];
           if(!minesList.contains(mine)){
               minesList.add(mine);
           }
        }
    }

    public static void showMines(){
        for(Mines mine : minesList){
            mine.setText("\uD83D\uDCA3");
        }
    }

    public static void validateArea(int r, int c){

        if (r < 0 || r >= GUI.maxScreenRows || c < 0 || c >= GUI.maxScreenColumns) {
            return;
        }

        Mines tile = GUI.mineMap[r][c];
        if(!tile.isEnabled()){
            return;
        }
        tile.setEnabled(false);
        int minesTotal = 0;


        tile.setEnabled(false);
        tilesClicked += 1;

        minesTotal += checkProximity(r - 1, c - 1);
        minesTotal += checkProximity(r - 1, c);
        minesTotal += checkProximity(r - 1, c + 1);
        minesTotal += checkProximity(r , c - 1);
        minesTotal += checkProximity(r , c + 1);
        minesTotal += checkProximity(r + 1, c - 1);
        minesTotal += checkProximity(r + 1, c);
        minesTotal += checkProximity(r + 1, c + 1);

        if(minesTotal > 0){
            tile.setText(Integer.toString(minesTotal));

        } else {

            tile.setText("");

             validateArea(r - 1, c - 1);
             validateArea(r - 1, c);
             validateArea(r - 1, c + 1);
             validateArea(r ,c - 1);
             validateArea(r ,c + 1);
             validateArea(r + 1, c - 1);
             validateArea(r + 1, c);
             validateArea(r + 1, c + 1);

        }

        if(tilesClicked == (GUI.maxScreenRows * GUI.maxScreenColumns) - minesList.size()){
            JOptionPane.showMessageDialog(null, "Mines cleared");
        }
    }

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
