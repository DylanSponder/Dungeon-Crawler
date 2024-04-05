package com.mygdx.game.level;

import java.util.HashMap;
import java.util.List;

public class AlignDoors {

    private InitLevel init;
    private int currentRoomX, currentRoomY;

    public void AlignDoors(boolean startingRoom, Room r, List<Room> rooms, int roomIndex, HashMap<String, String> map, int roomX, int levelY) {
        // init.testRooms.get()
        init = new InitLevel();
        if (!startingRoom) {
            //1 is up, 2 is right, 3 is down, 4 is left
            if (rooms.get(roomIndex).directionTaken == 1) {
                currentRoomX = rooms.get(roomIndex).x1;
                currentRoomX = rooms.get(roomIndex).x2;

               // String s = rooms.get(roomIndex).doorLocations.get("TopLeft");
               // s.split(",");

                System.out.println("TESTING DOOR LOCATIONS TESTING: " + rooms.get(roomIndex).doorLocations + "  " + r.doorLocations + " " + map.size());


            /*
            } else if (doorDirection == 2) {
                GenerateLevel.testLevelY = levelY - previousLevelY;
            } else if (doorDirection == 3) {
                GenerateLevel.testLevelY = GenerateLevel.testLevelY - (previousRoomSize);
            } else if (doorDirection == 4) {
                GenerateLevel.testLevelY = levelY + (currentRoomSize - previousRoomSize);
                GenerateLevel.testRoomX = roomX - longestRow;
            }

             */
            }

        }
    }

    public void SplitCoordValue(String s) {





    }

}
