package com.mygdx.game;

public class RandomInteger {

    public static int randomInt(int max, int min) {
        int random = (int) (Math.random() * max + min);
        return random;
    }
}
