package com.mygdx.game.level;

import java.util.Random;

public class PickDirection {

    private InitLevel init;
    private int doorDirection, previousDoorDirection;

    public int pickDirection(int doorDirection) {
        init = new InitLevel();

        int nextDirection = doorDirection;

        if (doorDirection == 0) {
            init.doorDirections = new int[] {1,2,3,4};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }
        if (doorDirection == 1){
            previousDoorDirection = 1;
            init.doorDirections = new int[] {1,2,4};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }
        if (doorDirection == 2){
            previousDoorDirection = 2;
            init.doorDirections = new int[] {1,2,3};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }
        if (doorDirection == 3){
            previousDoorDirection = 3;
            init.doorDirections = new int[] {2,3,4};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }
        if (doorDirection == 4){
            previousDoorDirection = 4;
            init.doorDirections = new int[] {1,3,4};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }

        System.out.println("DOOR DIRECTION IS: "+nextDirection);
        return nextDirection;
    }

    public int pickNewDirection(int doorDirection) {

        int nextDirection = doorDirection;

        if (doorDirection == 1){
            previousDoorDirection = 1;
            init.doorDirections = new int[] {2,3,4};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }
        if (doorDirection == 2){
            previousDoorDirection = 2;
            init.doorDirections = new int[] {2,3,4};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }
        if (doorDirection == 3){
            previousDoorDirection = 3;
            init.doorDirections = new int[] {1,2,4};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }
        if (doorDirection == 4){
            previousDoorDirection = 4;
            init.doorDirections = new int[] {1,2,3};
            nextDirection = new Random().nextInt(init.doorDirections.length);
            nextDirection = init.doorDirections[nextDirection];
        }

        System.out.println("DOOR DIRECTION IS: "+nextDirection);
        return nextDirection;
    }

}
