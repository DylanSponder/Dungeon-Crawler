package com.mygdx.game.level;

import java.util.Random;

public class PickDirection {

    private InitLevel init;
    private int doorDirection, previousDoorDirection;

    public int pickInitialDirection(int doorDirection) {
        init = new InitLevel();

        int nextDirection = doorDirection;

        if (doorDirection == 0) {
            init.doorDirections = new int[] {1,2,3,4};
        }
        if (doorDirection == 1){
            previousDoorDirection = 1;
            init.doorDirections = new int[] {1,2,4};
        }
        if (doorDirection == 2){
            previousDoorDirection = 2;
            init.doorDirections = new int[] {1,2,3};
        }
        if (doorDirection == 3){
            previousDoorDirection = 3;
            init.doorDirections = new int[] {2,3,4};
        }
        if (doorDirection == 4){
            previousDoorDirection = 4;
            init.doorDirections = new int[] {1,3,4};
        }
        nextDirection = new Random().nextInt(init.doorDirections.length);
        nextDirection = init.doorDirections[nextDirection];
        System.out.println("INITIAL DOOR DIRECTION IS: "+nextDirection);
        return nextDirection;
    }

    public int pickNewDirection(int doorDirection) {
        int nextDirection = doorDirection;

        switch (doorDirection) {
            case 1:
                //init.doorDirections = new int[] {2,3,4};
                switch (previousDoorDirection){
                    case 1:
                        init.doorDirections = new int[] {2,4};
                    case 2:
                        init.doorDirections = new int[] {2,3};
                    case 4:
                        init.doorDirections = new int[] {3,4};
                }
                break;
            case 2:
                //init.doorDirections = new int[] {2,3,4};
                switch (previousDoorDirection){
                    case 1:
                        init.doorDirections = new int[] {1,4};
                    case 2:
                        init.doorDirections = new int[] {1,3};
                    case 3:
                        init.doorDirections = new int[] {2,4};
                }
                break;
            case 3:
                //init.doorDirections = new int[] {1,2,4};
                switch (previousDoorDirection){
                    case 2:
                        init.doorDirections = new int[] {3,4};
                    case 3:
                        init.doorDirections = new int[] {2,4};
                    case 4:
                        init.doorDirections = new int[] {1,4};
                }
                break;
            case 4:
                //init.doorDirections = new int[] {1,2,3};
                switch (previousDoorDirection){
                    case 1:
                        init.doorDirections = new int[] {3,4};
                    case 3:
                        init.doorDirections = new int[] {1,4};
                    case 4:
                        init.doorDirections = new int[] {1,3};
                }
                break;
        }

        nextDirection = new Random().nextInt(init.doorDirections.length);
        nextDirection = init.doorDirections[nextDirection];
        System.out.println("NEW DIRECTION: " + nextDirection);

        return nextDirection;
    }

}
