package com.mygdx.game;

public class Random {

    //utility class for random numbers and booleans

    public static int randomInt(int max, int min) {
        int random = min + (int) (Math.random() * ((max - min) + 1));
        return random;
    }

    public static float randomFloat(float max, float min) {
        float random = min + (float) (Math.random() * ((max - min) + 1));
        return random;
    }

    public static boolean randomBoolean() {
        java.util.Random randomUtil = new java.util.Random();
        boolean random = randomUtil.nextBoolean();
        return random;
    }
}
