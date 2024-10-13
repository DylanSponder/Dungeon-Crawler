package com.mygdx.game;

public class Random {

    public static int randomInt(int max, int min) {
        int random = (int) (Math.random() * max + min);
        return random;
    }

    public static float randomFloat(float max, float min) {
        float random = (float) (Math.random() * max + min);
        return random;
    }

    public static boolean randomBoolean() {
        java.util.Random randomUtil = new java.util.Random();
        boolean random = randomUtil.nextBoolean();
        return random;
    }
}
