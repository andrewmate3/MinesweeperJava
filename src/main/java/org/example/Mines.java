package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Mines extends JButton {
    public int r;
    public int c;

    public static int mineCount;

    static ArrayList<Mines> minesList;

    public Mines(int r, int c){
        this.r = r;
        this.c = c;
    }

    public static void placeMines(){

        minesList = new ArrayList<>();
        mineCount = 20;
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
            mine.setText("^");
        }
    }
}
