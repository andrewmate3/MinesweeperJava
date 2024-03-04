package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.example.Mines.*;
import static org.junit.jupiter.api.Assertions.*;

class MinesTest {

    @Test
    void checkProximityTest1(){
        minesList = new ArrayList<>();
        Mines[][] mine = GUI.mineMap;
        minesList.add(mine[7][7]);
        Assertions.assertEquals(0, checkProximity(23, 12));
    }

    @Test
    void checkProximityTest2(){
        minesList = new ArrayList<>();
        Mines[][] mine = GUI.mineMap;
        minesList.add(mine[5][5]);
        Assertions.assertEquals(1, checkProximity(4, 5));
    }

    @Test
    void checkProximityTest(){
        minesList = new ArrayList<>();
        Mines[][] mine = GUI.mineMap;
        minesList.add(mine[2][3]);
        Assertions.assertEquals(1, checkProximity(6, 6));
    }


    @Test
    void showMines() {
    }

    @Test
    void validateArea() {
    }
}