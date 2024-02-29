package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Mines extends JButton {
    public int r;
    public int c;

    public static int mineCount = 20;

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
            mine.setText("*");
        }
    }

    public static void validateArea(int r, int c){

        Mines tile = GUI.mineMap[r][c];
        tile.setEnabled(false);
        int minesTotal = 0;

        for(int i = c - 1; i <= c + 1; i++){
            minesTotal += checkProximity(r - 1, i);
        }

        for(int i = c - 1; i <= c + 1; i++){
            if(i != c){
                minesTotal += checkProximity(r, i);
            }
        }

        for(int i = c - 1; i <= c + 1; i++){
            minesTotal += checkProximity(r + 1, i);
        }

        if(minesTotal > 0){
            tile.setText(Integer.toString(minesTotal));
        } else {

            tile.setText("");

            for(int i = c - 1; i <= c + 1; i++){
                minesTotal += checkProximity(r - 1, i);
            }

            for(int i = c - 1; i <= c + 1; i++){
                if(i != c){
                    minesTotal += checkProximity(r, i);
                }
            }

            for(int i = c - 1; i <= c + 1; i++){
                minesTotal += checkProximity(r + 1, i);
            }
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
