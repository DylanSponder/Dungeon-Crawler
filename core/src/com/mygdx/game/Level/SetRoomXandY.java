package com.mygdx.game.level;

public class SetRoomXandY {

    //this class holds two functions which are almost the same - the difference being:
    // - checkRoomForIntersection is used to set dummy values which simulate a room generating, for use in collision detection equations.
    // - setNextRoomDimensions is for rooms which are confirmed to have no intersections with any previous rooms.

    public int checkRoomForIntersection(boolean startingRoom, int doorDirection, int roomX, int levelY, int previousRoomSize, int currentRoomSize, int previousLongestRow, int longestRow) {
        if (!startingRoom) {
            //1 is up, 2 is right, 3 is down, 4 is left
            if (doorDirection == 1){
                GenerateLevel.testLevelY = levelY + currentRoomSize;
            }
            else if (doorDirection == 2) {
                //GenerateLevel.testLevelY = GenerateLevel.testLevelY + currentRoomSize;
                GenerateLevel.testRoomX = roomX + previousLongestRow;
            }
            else if (doorDirection == 3){
                GenerateLevel.testLevelY = GenerateLevel.testLevelY - (previousRoomSize);
            }
            else if (doorDirection == 4){
                GenerateLevel.testLevelY = levelY + (currentRoomSize - previousRoomSize);
                GenerateLevel.testRoomX = roomX - longestRow;
            }
        }
        return doorDirection;
    }

    public int setNextRoomDimensions(int doorDirection, int roomX, int levelY, int previousRoomSize, int currentRoomSize, int previousLongestRow, int longestRow){
        //1 is up, 2 is right, 3 is down, 4 is left
        if (doorDirection == 1){
            GenerateLevel.levelY = levelY + (previousRoomSize + currentRoomSize);
        }
        else if (doorDirection == 2) {
            GenerateLevel.levelY = levelY + (previousRoomSize);
            GenerateLevel.roomX = roomX + previousLongestRow;
        }
        else if (doorDirection == 3){
            //nothing needs to be done here - rooms naturally generate downwards (levelY increments down by 1 to place each row)
        }
        else if (doorDirection == 4){
            GenerateLevel.levelY = levelY + currentRoomSize;
            GenerateLevel.roomX = roomX - longestRow;
        }
        return doorDirection;
    }

    public void resetDimensions() {
        GenerateLevel.testLevelY = GenerateLevel.levelY;
        GenerateLevel.testRoomX = GenerateLevel.roomX;
    }
}
