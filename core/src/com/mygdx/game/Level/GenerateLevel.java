package com.mygdx.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.*;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.behaviours.fsm.Enemy;

import java.io.IOException;
import java.util.*;

import static com.mygdx.game.DungeonCrawler.world;

public class GenerateLevel {
    private InitLevel init;
    private BodyFactory bf;
    private CreateTexture tx;
    private PickDirection pd;
    private SetRoomXandY xy;
    private AlignDoors doors;
    private CreateCorridor cc;
    private float PLAYER_X, PLAYER_Y;
    private TiledMapTileLayer layer;
    public static int roomX, levelY, testRoomX, testLevelY;
    public int initialTestRoomX, initialTestLevelY;
    public int longestRow, currentRow, previousLongestRow, rollbackIndex, roomsIndex, tries, repeatRoom;
    public int doorDirection, roomSize, currentRoomSize, previousRoomSize;
    public int testPreviousLongestRow, testLongestRow, testPreviousRoomSize, testCurrentRoomSize, testPreviousRoomX, testPreviousLevelY, testCurrentRow;
    private int xOffset, yOffset;
    private boolean intersecting, startingRoom, roomHitboxCreated;
    private int doorTop, doorBottom, doorLeft, doorRight, doorResult;
    private String doorLeftX, doorLeftY, doorRightX, doorRightY;
    private List layerSizes;
    private ArrayList list;
    public ArrayList<Integer> path, directionsAvailableIndexed;
    public ArrayList<Room> rolledbackRooms;
    public HashMap<String, String> doorMap;
    private int[] doorDirections;
    public Fixture roomHitbox;
    public boolean failed;
    public Room rollbackRoom;

    public ArrayList generateLevel(float PLAYER_X, float PLAYER_Y) {
        init = new InitLevel();
        init.InitializeLevel();
        pd = new PickDirection();
        xy = new SetRoomXandY();
        doors = new AlignDoors();
        layer = init.layer;
        roomX = init.roomX;
        levelY = init.levelY;
        testRoomX = init.testRoomX;
        testLevelY = init.testLevelY;
        roomsIndex = 0;
        roomHitboxCreated = false;

        int min = 10;
        int max = 10;
        int numRooms = (int)(Math.random() * (max - min + 1)) + min;
        //int numRooms = 7;

        path = new ArrayList(){};

        for (int i = 0; i < 10; i++) {
            //path.add(1);
        }

        int currentDoorDirection = 0;
        int previousDoorDirection = 0;
        currentDoorDirection =  pd.pickInitialDirection(currentDoorDirection);

        /*
        path.add(0,0);
        path.add(1,1);
        path.add(2,1);
        path.add(3,4);
        path.add(4,4);
         */

        /*
        path.add(0,0);
        path.add(1,1);
        path.add(2,1);
        path.add(3,2);
        path.add(4,2);
        path.add(5,1);
        path.add(6,1);
         */

        for (int i = 0; i<numRooms*2; i++){
            if (i-1 != -1){
                previousDoorDirection = path.get(i-1);
            }
            else {
                previousDoorDirection = 0;
            }
            currentDoorDirection =  pd.pickInitialDirection(currentDoorDirection);

//            for (int i2 = 0; i2<2; i2++) {
//                if (){
//
//                }

                path.add(i, currentDoorDirection);
                path.add(i+1, currentDoorDirection);
                i++;
        }

        //path.set(5,1);

        System.out.println("PATH BEFORE: " + path);

        for (int i = 0; i < numRooms; i++) {
            Room newRoom = new Room();
            init.roomList.add(newRoom);
            //newRoom.directionTaken = init.roomList.get(i).directionTaken;
            roomsIndex++;
            newRoom.index = i;
            int random = (int) (Math.random() * 2 + 1);
            newRoom.roomNum = random;
           // init.roomList.get(i).roomNum = random;
        }

        System.out.println("Random number of rooms generated: " + numRooms);

        for (int i = 0; i < numRooms; i++) {
         //   roomsIndex++;
            directionsAvailableIndexed = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
            if (i == 0) {
                startingRoom = true;
                //doorDirection = 0;
                testGenerateRoom(startingRoom, path.get(i), i);
            } else {
                startingRoom = false;
                testGenerateRoom(startingRoom, path.get(i), i);
            }
        }

        for (int r = 0; r < init.roomList.size(); r++) {
            if (r - 1 == -1) { //create the first room
                startingRoom = true;
                roomHitboxCreated = false;
                list = generateRoom(
                        world,
                        startingRoom,
                        init.roomList.get(r),
                        init.roomList.get(r).roomNum,
                        init.roomList.get(r).index,
                        path.get(r), 0, init.roomList.get(r + 1).directionTaken,
                        init.roomList.get(r).x1, init.roomList.get(r).y1,
                        init.roomList.get(r).roomSize, 0,
                        init.roomList.get(r).longestRow, 0
                );
                //cc.CreateCorridor(world, init.roomList.get(init.roomList.get(r).index).doorLocations.get()  , );

            } else {
                if (r != init.roomList.size() - 1) {
                    startingRoom = false;
                    roomHitboxCreated = false;
                    list = generateRoom(
                            world,
                            startingRoom,
                            init.roomList.get(r),
                            init.roomList.get(r).roomNum,
                            init.roomList.get(r).index,
                            path.get(r), path.get(r - 1), path.get(r + 1),
                            init.roomList.get(r).x1, init.roomList.get(r).y1,
                            init.roomList.get(r).roomSize, init.roomList.get(r - 1).roomSize,
                            init.roomList.get(r).longestRow, init.roomList.get(r - 1).longestRow
                    );
                } else {
                    startingRoom = false;
                    roomHitboxCreated = false;
                    list = generateRoom(
                            world,
                            startingRoom,
                            init.roomList.get(r),
                            init.roomList.get(r).roomNum,
                            init.roomList.get(r).index,
                            path.get(r), path.get(r - 1), 0,
                            init.roomList.get(r).x1, init.roomList.get(r).y1,
                            init.roomList.get(r).roomSize, init.roomList.get(r - 1).roomSize,
                            init.roomList.get(r).longestRow, init.roomList.get(r - 1).longestRow
                    );
                }
            }
            //Outputs the X and Y values of every room, for debugging purposes.
            /*
            System.out.println(
                    "ROOM " + (r + 1) +
                            " X1: " + init.roomList.get(r).x1 +
                            " X2: " + init.roomList.get(r).x2 +
                            " Y1: " + init.roomList.get(r).y1 +
                            " Y2: " + init.roomList.get(r).y2 +
                            " Longest Row: " + init.roomList.get(r).longestRow +
                            " Room Size: " + init.roomList.get(r).roomSize +
                            " Door Locations: " + init.roomList.get(r).doorLocations
            );

             */
        }

        System.out.println("PATH AFTER: " + path);
        return list;
    }

    public void testGenerateRoom(boolean startingRoom, int currentDoorDirection, int roomIndex) {
        tries = 1;
        //if (!startingRoom){
        //    previousDoorDirection = 0;

        //}

                //pd.pickInitialDirection(doorDirection);

        try {
            List<List<String>> roomFile = init.lp.read("Rooms/room" + init.roomList.get(roomIndex).roomNum + ".csv");
            //System.out.println("ROOM " + init.roomList.get(roomIndex).roomNum + " CHOSEN" );

            if (startingRoom){
                testPreviousRoomSize = roomFile.size();
                testPreviousLongestRow = 0;
                testCurrentRoomSize = roomFile.size();
            } else {
                testPreviousRoomSize = testCurrentRoomSize;
                testPreviousLongestRow = testLongestRow;
                testPreviousRoomX = roomX;
                testPreviousLevelY = levelY;
                testCurrentRoomSize = roomFile.size();
            }

            failed = false;

            testLongestRow = 0;
            for (int columnNum = 0; columnNum < testCurrentRoomSize; columnNum++) {
                testCurrentRow = roomFile.get(columnNum).size();
                if (testLongestRow < testCurrentRow) {
                    testLongestRow = testCurrentRow;
                }

               // HashMap<String, String> doorMap = init.rr.translateSymbolsToFindDoors(roomFile, columnNum, roomIndex, init.roomList.get(roomIndex), init.roomList.get(roomIndex).doorLocations, testRoomX, testLevelY);
            }

            checkForIntersection(startingRoom, currentDoorDirection, roomIndex);

            while (failed) {

            /*
                if (directionsAvailableIndexed.isEmpty()){
                    System.out.println("DIRECTIONS EXHAUSTED - ATTEMPTING ROLLBACK");
                    rollbackIndex--;
                    rollbackRoom = init.roomList.get(rollbackIndex);

                    if (!rolledbackRooms.contains(rollbackRoom)) {
                        rolledbackRooms.add(rollbackRoom);
                    }
                    else {
                        rollbackIndex--;
                        rollbackRoom = init.roomList.get(rollbackIndex);
                    }

                    testRoomX = rollbackRoom.x1;
                    testLevelY = rollbackRoom.y1;
                    testCurrentRoomSize = previousRoomSize;
                    testLongestRow = testPreviousLongestRow;
                    System.out.println("WENT BACK TO PREVIOUS ROOM");
                    failed = false;
                }
             */
                    if (!startingRoom){

                       // int newDirection = pd.pickNewDirection(currentDoorDirection, path.get(roomIndex-1));
                        int newDirection = pd.pickInitialDirection(currentDoorDirection);
                        path.set(roomIndex, newDirection);
                    }
                    else {
                        int newDirection = pd.pickNewDirection(currentDoorDirection, 0);
                        path.set(roomIndex, newDirection);
                    }

                    currentDoorDirection =  path.get(roomIndex);
                    checkForIntersection(startingRoom, currentDoorDirection, roomIndex);

            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkForIntersection(boolean startingRoom, int doorDirection, int roomIndex) {//calculations creating a dummy test room and evaluating against all previous rooms in the rooms arraylist, before (after a successful room placement) adding it to the arraylist

        initialTestLevelY = testLevelY;
        initialTestRoomX = testRoomX;

        xy.checkRoomForIntersection(startingRoom, doorDirection, testRoomX, testLevelY, testPreviousRoomSize, testCurrentRoomSize, testPreviousLongestRow, testLongestRow);

        int h = testCurrentRoomSize;
        int w = testLongestRow;

        int x1 = init.roomList.get(roomIndex).x1 + testRoomX;
        int x2 = init.roomList.get(roomIndex).x2 + testRoomX + w;
        int y1 = init.roomList.get(roomIndex).y1 + testLevelY;
        int y2 = init.roomList.get(roomIndex).y2 + testLevelY - h;

        int testingX1, testingY1, testingX2, testingY2;

        //testingX1 = x1;
        //testingY1 = y1;
        //testingX2 = x2;
        //testingY2 = y2;

        Room newRoom = init.roomList.get(roomIndex);

        //newRoom.index = roomsIndex;

        newRoom.x1 = x1;
        newRoom.x2 = x2;
        newRoom.y1 = y1;
        newRoom.y2 = y2;

        if (startingRoom) {
            newRoom.x1 = x1;
            newRoom.x2 = x2;
            newRoom.y1 = y1;
            newRoom.y2 = y2;
        }
        else {
            if (doorDirection == 1) {
                /*
                if (init.roomList.get(roomIndex-1).directionTaken == 1 || init.roomList.get(roomIndex-1).directionTaken == 3) {
                    newRoom.y1 = y1 + 4 + xOffset;
                    newRoom.y2 = y2 + 4 + xOffset;
                    newRoom.x1 = x1;
                    newRoom.x2 = x2;

                } else {

                 */
                    newRoom.y1 = y1 + 4;
                    newRoom.y2 = y2 + 4;
                    newRoom.x1 = x1;
                    newRoom.x2 = x2;
            }
            else if (doorDirection == 2) {
                newRoom.x1 = x1 + 4;
                newRoom.x2 = x2 + 4;
                newRoom.y1 = y1;
                newRoom.y2 = y2;
            }
            else if (doorDirection == 3) {
                newRoom.y1 = y1 - 4;
                newRoom.y2 = y2 - 4;
                newRoom.x1 = x1;
                newRoom.x2 = x2;

            }
            else if (doorDirection == 4) {
                newRoom.x1 = x1 - 4;
                newRoom.x2 = x2 - 4;
                newRoom.y1 = y1;
                newRoom.y2 = y2;
            }
        }

        newRoom.roomSize = testCurrentRoomSize;
        newRoom.longestRow = testLongestRow;
        Room initialRoom;
        initialRoom = newRoom;
        //newRoom.roomNum = random;

        // if (!(init.roomList.contains(newRoom))) {
        //  init.roomList.add(newRoom);
        //}
        /*
        if (roomIndex == 0) {

           init.roomList.get(roomIndex).directionTaken = -1;
        }
         */

        int doorY = y1;

        locateDoors(roomIndex, x1, doorY);

        for (Room r : init.roomList) {

           // System.out.println(doorLeft + doorRight);

          //  if (tries >= 4) {
          //      return failed = false;
          //  }
            if (!(r.y1 == 0 || r.x1 == 0)) {
                if (init.roomList.get(roomIndex).index != r.index) { //checks first to see if the current room is NOT evaluating against itself
                    if (!((newRoom.x1 == r.x1) && (newRoom.x2 == r.x2) && (newRoom.y1  == r.y1) && (newRoom.y2 == r.y2))) { //checks to see if rooms are an exact match - rare but possible
                        if ((newRoom.x2 > r.x1 && newRoom.x2 <= r.x2) && (newRoom.y1  > r.y2 &&  newRoom.y2 < r.y1)) {
                            System.out.println("ROOM " + (roomIndex+1) + " HAD A 'LEFT' INTERSECTION WITH ROOM: " + (init.roomList.indexOf(r) + 1));
                            System.out.println("DIRECTION INDEX LEFT INTERSECTION: "+directionsAvailableIndexed.indexOf(doorDirection));
                            System.out.println("DIRECTION VALUE LEFT INTERSECTION: "+directionsAvailableIndexed.get(directionsAvailableIndexed.indexOf(doorDirection)));
                    /*
                    if (directionsAvailableIndexed.contains(directionsAvailableIndexed.indexOf(doorDirection))){
                        directionsAvailableIndexed.remove(directionsAvailableIndexed.indexOf(doorDirection));
                    }
                    //System.out.println("AVAILABLE DIRECTIONS REMAINING: " + directionsAvailableIndexed);
                     */
                            tries++;
                            newRoom = initialRoom;
                            testLevelY = initialTestLevelY;
                            testRoomX = initialTestRoomX;
                            return failed = true;
                        } else if ((newRoom.x1 < r.x2 && newRoom.x1 >= r.x1) && (newRoom.y1  > r.y2 &&  newRoom.y2 < r.y1)) {
                            System.out.println("ROOM " + roomIndex + " HAD A 'RIGHT' INTERSECTION WITH ROOM: " + (init.roomList.indexOf(r) + 1));
                            System.out.println("DIRECTION INDEX RIGHT INTERSECTION: "+directionsAvailableIndexed.indexOf(doorDirection));
                            System.out.println("DIRECTION VALUE RIGHT INTERSECTION: "+directionsAvailableIndexed.get(directionsAvailableIndexed.indexOf(doorDirection)));
                            //  init.roomList.remove(newRoom);
                            //if (directionsAvailableIndexed.contains(directionsAvailableIndexed.indexOf(doorDirection))){
                            //    directionsAvailableIndexed.remove(directionsAvailableIndexed.indexOf(doorDirection));
                            // }
                            //System.out.println("AVAILABLE DIRECTIONS REMAINING: " + directionsAvailableIndexed);
                            tries++;
                            newRoom = initialRoom;
                            testLevelY = initialTestLevelY;
                            testRoomX = initialTestRoomX;
                            return failed = true;
                        }
                    }
                    else {
                        System.out.println("ROOM DIMENSIONS IDENTICAL - INTERSECTION");
                        tries++;
                        newRoom = initialRoom;
                        testLevelY = initialTestLevelY;
                        testRoomX = initialTestRoomX;
                        return failed = true;
                    }
                }
            }
        }

        init.roomList.get(roomIndex).x1 = initialRoom.x1;
        init.roomList.get(roomIndex).x2 = initialRoom.x2;
        init.roomList.get(roomIndex).y1 = initialRoom.y1;
        init.roomList.get(roomIndex).y2 = initialRoom.y2;
        newRoom = initialRoom;

        //create a room object with the dimensions of the room to-be generated
        //  Room newRoom = new Room();

        //   rollbackIndex = init.roomList.size();

        //if (tries == 1) {

       // }

        newRoom.x1 = x1;
        newRoom.x2 = x2;
        newRoom.y1 = y1;
        newRoom.y2 = y2;
        newRoom.directionTaken = doorDirection;


        if (startingRoom) {


        }
        else {
            doorResult = 0;
            HashMap<String, String> doorMapPrevious =  init.roomList.get(roomIndex-1).doorLocations;
            System.out.println("CURRENT ROOM: "+roomIndex);
            System.out.println("DOOR MAP PREVIOUS ROOM INDEX " + (roomIndex-1) + ": " + doorMapPrevious);

            if (doorDirection==1) {
                String doorTopLeft = doorMapPrevious.get("TopLeft");
                String[] doorTopLeftXY = doorTopLeft.split(",");
                String doorTopLeftX = doorTopLeftXY[0].toString();
                //String doorTopLeftY = doorTopLeftXY[1].toString();
                int doorTopLeftXAsInt = Integer.parseInt(doorTopLeftX);

                String doorTopRight = doorMapPrevious.get("TopRight");
                String[] doorTopRightXY = doorTopRight.split(",");
                String doorTopRightX = doorTopRightXY[0].toString();
                String doorTopRightY = doorTopRightXY[1].toString();
                int doorTopRightXAsInt = Integer.parseInt(doorTopRightX);

                String doorBottomLeft = doorMap.get("BottomLeft");
                String[] doorBottomLeftXY = doorBottomLeft.split(",");
                String doorBottomLeftX = doorBottomLeftXY[0].toString();
                String doorBottomLeftY = doorBottomLeftXY[1].toString();
                int doorBottomLeftXAsInt = Integer.parseInt(doorBottomLeftX);

                String doorBottomRight = doorMap.get("BottomRight");
                String[] doorBottomRightXY = doorBottomRight.split(",");
                String doorBottomRightX = doorBottomRightXY[0].toString();
                String doorBottomRightY = doorBottomRightXY[1].toString();
                int doorBottomRightXAsInt = Integer.parseInt(doorBottomRightX);

                if (doorTopLeftXAsInt > doorBottomLeftXAsInt){
                    doorResult = doorTopLeftXAsInt - doorBottomLeftXAsInt;

                }
                else if (doorBottomLeftXAsInt > doorTopLeftXAsInt) {
                    doorResult = doorBottomLeftXAsInt - doorTopLeftXAsInt;
                    doorResult = doorResult * -1;
                }

                xOffset = doorResult;

                init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;
                if (path.get(roomIndex+1) == 2 || path.get(roomIndex+1) == 4) {
                    testRoomX = testRoomX + xOffset;
                }

                locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
            }
            if (doorDirection==2) {
                String doorUpperRight = doorMapPrevious.get("UpperRight");
                String[] doorUpperRightXY = doorUpperRight.split(",");
                String doorUpperRightX = doorUpperRightXY[0].toString();
                String doorUpperRightY = doorUpperRightXY[1].toString();
                int doorUpperRightYAsInt = Integer.parseInt(doorUpperRightY);

                String doorLowerRight = doorMapPrevious.get("LowerRight");
                String[] doorLowerRightXY = doorLowerRight.split(",");
                String doorLowerRightX = doorLowerRightXY[0].toString();
                String doorLowerRightY = doorLowerRightXY[1].toString();

                String doorUpperLeft = doorMap.get("UpperLeft");
                String[] doorUpperLeftXY = doorUpperLeft.split(",");
                String doorUpperLeftX = doorUpperLeftXY[0].toString();
                String doorUpperLeftY = doorUpperLeftXY[1].toString();
                int doorUpperLeftYAsInt = Integer.parseInt(doorUpperLeftY);

                String doorLowerLeft = doorMap.get("LowerLeft");
                String[] doorLowerLeftXY = doorLowerLeft.split(",");
                String doorLowerLeftX = doorLowerLeftXY[0].toString();
                String doorLowerLeftY = doorLowerLeftXY[1].toString();

                if (doorUpperLeftYAsInt > doorUpperRightYAsInt){
                    doorResult = doorUpperRightYAsInt - doorUpperLeftYAsInt;
                }
                else if (doorUpperRightYAsInt > doorUpperLeftYAsInt) {
                    doorResult = doorUpperLeftYAsInt - doorUpperRightYAsInt;
                    doorResult = doorResult * -1;
                }

                yOffset = doorResult;

                init.roomList.get(roomIndex).y1 = init.roomList.get(roomIndex).y1 + yOffset;
                init.roomList.get(roomIndex).y2 = init.roomList.get(roomIndex).y2 + yOffset;
                if (path.get(roomIndex+1) == 1 || path.get(roomIndex+1) == 3) {
                 //   testLevelY = testLevelY + yOffset;
                }
                locateDoors(roomIndex, init.roomList.get(roomIndex).x1+xOffset, init.roomList.get(roomIndex).y1);
            }
            if (doorDirection==3) {
                /*
                //gets the top left and right door locations from the doormap and de-concatenates them
                String doorTopLeft = doorMap.get("TopLeft");
                String[] doorTopLeftXY = doorTopLeft.split(",");
                String doorTopLeftX = doorTopLeftXY[0].toString();
                String doorTopLeftY = doorTopLeftXY[1].toString();
                //   System.out.println("DOOR TOP LEFT X COORDS: "  + doorTopLeftX + " DOOR TOP LEFT Y COORDS: " + doorTopLeftY);

                String doorTopRight = doorMap.get("TopRight");
                String[] doorTopRightXY = doorTopRight.split(",");
                String doorTopRightX = doorTopRightXY[0].toString();
                String doorTopRightY = doorTopRightXY[1].toString();
                //  System.out.println("DOOR TOP RIGHT X COORDS: "  + doorTopRightX + " DOOR TOP RIGHT Y COORDS: " + doorTopRightY);
                 */

                String doorTopLeft = doorMap.get("TopLeft");
                //String doorTopLeft = doorMapPrevious.get("TopLeft");
                String[] doorTopLeftXY = doorTopLeft.split(",");
                String doorTopLeftX = doorTopLeftXY[0].toString();
                String doorTopLeftY = doorTopLeftXY[1].toString();
                int doorTopLeftXAsInt = Integer.parseInt(doorTopLeftX);

                String doorTopRight = doorMap.get("TopRight");
                //String doorTopRight = doorMapPrevious.get("TopRight");
                String[] doorTopRightXY = doorTopRight.split(",");
                String doorTopRightX = doorTopRightXY[0].toString();
                String doorTopRightY = doorTopRightXY[1].toString();
                int doorTopRightXAsInt = Integer.parseInt(doorTopRightX);

                String doorBottomLeft = doorMapPrevious.get("BottomLeft");
                //String doorBottomLeft = doorMap.get("BottomLeft");
                String[] doorBottomLeftXY = doorBottomLeft.split(",");
                String doorBottomLeftX = doorBottomLeftXY[0].toString();
                String doorBottomLeftY = doorBottomLeftXY[1].toString();
                int doorBottomLeftXAsInt = Integer.parseInt(doorBottomLeftX);

                String doorBottomRight = doorMapPrevious.get("BottomRight");
                //String doorBottomRight = doorMap.get("BottomRight");
                String[] doorBottomRightXY = doorBottomRight.split(",");
                String doorBottomRightX = doorBottomRightXY[0].toString();
                String doorBottomRightY = doorBottomRightXY[1].toString();
                int doorBottomRightXAsInt = Integer.parseInt(doorBottomRightX);

                if (doorTopLeftXAsInt > doorBottomLeftXAsInt){
                    doorResult = doorBottomLeftXAsInt - doorTopLeftXAsInt;

                }
                else if (doorBottomLeftXAsInt > doorTopLeftXAsInt) {
                    doorResult = doorTopLeftXAsInt - doorBottomLeftXAsInt;
                    doorResult = doorResult * -1;
                }

                xOffset = doorResult;
                System.out.println("3 taken offset: " + xOffset);

                init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                if ((path.get(roomIndex+1) == 2 || path.get(roomIndex+1) == 4) && roomIndex+1<init.roomList.size()) {
                    System.out.println(init.roomList.get(roomIndex+1).x1);
                    testRoomX = testRoomX + xOffset;
                    //    init.roomList.get(roomIndex+1).x1 = init.roomList.get(roomIndex+1).x1 + xOffset;
                    //    init.roomList.get(roomIndex+1).x2 = init.roomList.get(roomIndex+1).x2 + xOffset;
                    System.out.println(init.roomList.get(roomIndex+1).x1);
                }


                System.out.println(init.roomList.get(roomIndex).x1);
                locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                System.out.println(init.roomList.get(roomIndex).x1);

            }
            if (doorDirection==4) {
                String doorUpperRight = doorMapPrevious.get("UpperRight");
                String[] doorUpperRightXY = doorUpperRight.split(",");
                String doorUpperRightX = doorUpperRightXY[0].toString();
                String doorUpperRightY = doorUpperRightXY[1].toString();
                int doorUpperRightYAsInt = Integer.parseInt(doorUpperRightY);

                String doorLowerRight = doorMapPrevious.get("LowerRight");
                String[] doorLowerRightXY = doorLowerRight.split(",");
                String doorLowerRightX = doorLowerRightXY[0].toString();
                String doorLowerRightY = doorLowerRightXY[1].toString();

                String doorUpperLeft = doorMap.get("UpperLeft");
                String[] doorUpperLeftXY = doorUpperLeft.split(",");
                String doorUpperLeftX = doorUpperLeftXY[0].toString();
                String doorUpperLeftY = doorUpperLeftXY[1].toString();
                int doorUpperLeftYAsInt = Integer.parseInt(doorUpperLeftY);

                String doorLowerLeft = doorMap.get("LowerLeft");
                String[] doorLowerLeftXY = doorLowerLeft.split(",");
                String doorLowerLeftX = doorLowerLeftXY[0].toString();
                String doorLowerLeftY = doorLowerLeftXY[1].toString();

                if (doorUpperLeftYAsInt > doorUpperRightYAsInt){
                    doorResult = doorUpperRightYAsInt - doorUpperLeftYAsInt;
                }
                else if (doorUpperRightYAsInt > doorUpperLeftYAsInt) {
                    doorResult = doorUpperLeftYAsInt - doorUpperRightYAsInt;
                    doorResult = doorResult * -1;
                }

                yOffset = doorResult;

                init.roomList.get(roomIndex).y1 = init.roomList.get(roomIndex).y1 + yOffset;
                init.roomList.get(roomIndex).y2 = init.roomList.get(roomIndex).y2 + yOffset;
                if (path.get(roomIndex+1) == 1 || path.get(roomIndex+1) == 3) {
                    //   testLevelY = testLevelY + yOffset;
                }

                locateDoors(roomIndex, init.roomList.get(roomIndex).x1+xOffset, init.roomList.get(roomIndex).y1);

            }
        }

        return failed = false;
    }

    public void locateDoors(int roomIndex, int x1, int doorY) {
        try{
            List<List<String>> roomFile = init.lp.read("Rooms/room" + init.roomList.get(roomIndex).roomNum + ".csv");

            testLongestRow = 0;
            for (int rowNum = 0; rowNum < testCurrentRoomSize; rowNum++) {
                testCurrentRow = roomFile.get(rowNum).size();
                if (testLongestRow < testCurrentRow) {
                    testLongestRow = testCurrentRow;
                }
                //TODO: GET X AND Y VALUES - ASSIGN X TO X1 AT FIRST AND Y TO LEVELY AT FIRST - MOVE AS IN GENERATELEVEL FUNCTION
                if (startingRoom) {
                    doorMap = init.rr.translateSymbolsToFindDoors(roomFile, rowNum, roomIndex, path.get(roomIndex), path.get(roomIndex), init.roomList.get(roomIndex).doorLocations, x1, doorY);
                }
                else{
                    doorMap = init.rr.translateSymbolsToFindDoors(roomFile, rowNum, roomIndex, path.get(roomIndex), path.get(roomIndex-1), init.roomList.get(roomIndex).doorLocations, x1, doorY);
                }
                doorY--;
            }

            init.roomList.get(roomIndex).doorLocations = doorMap;
            //System.out.println("DOOR LOCATIONS VIA RR" + init.roomList.get(roomIndex).doorLocations + "ROOM INDEX: " + roomIndex);
            //System.out.println(doorMap);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList generateRoom(World world,
                                  boolean startingRoom,
                                  Room r, int roomNum, int roomIndex,
                                  int doorDirection, int previousDoorDirection, int nextDirection,
                                  int roomX, int levelY,
                                  int currentRoomSize, int previousRoomSize,
                                  int longestRow, int previousLongestRow) {

        //doors.AlignDoors(startingRoom, r, init.roomList, init.roomList.indexOf(r), r.doorLocations, roomX, levelY);

        //System.out.println("INDEX: " + roomIndex);
        //System.out.println("CURRENT DIRECTION: " + doorDirection + " PREVIOUS DIRECTION: " + previousDoorDirection + " NEXT DIRECTION: " + nextDirection);

        doorTop = 0;
        doorBottom = 0;
        doorLeft = 0;
        doorRight = 0;

        //int roomIndex =  init.roomList.indexOf(r);

        //randomly pick from available prefabs
        //int random = (int)(Math.random() * 3 + 1);

        try {
            List<List<String>> room = init.lp.read("Rooms/room" + roomNum + ".csv");
            //levelY is what determines the size of the level.
            //When levelY is either 1000 or 0 the map will be outside the TiledMapTileLayer and thus will not render


         //   xy.setNextRoomDimensions(doorDirection, roomX, levelY, previousRoomSize, currentRoomSize, previousLongestRow, longestRow);

            for (int rowNum = 0; rowNum < currentRoomSize; rowNum++) {
                List<String> levelTextures = init.rr.translateSymbols(room, rowNum, init.roomList.indexOf(r), init.roomList.get(init.roomList.indexOf(r)).doorLocations, roomX, levelY);

                if (!roomHitboxCreated){
                    //create a box with the dimensions of the to-be-generated room - originally intended for collision detection but cannot be used that way
                    //will instead be used for detecting if the player has entered a room for opening and closing doors
                    roomHitbox = bf.createRoom(world, ((roomX * 16) + 16 * 16) + (longestRow * 16) / 2, (levelY * 16 - (currentRoomSize * 16) / 2) + 16, currentRoomSize * 16 / 2, longestRow * 16 / 2);
                    roomHitbox.setSensor(true);
                    roomHitboxCreated = true;
                }

                int layerSize = levelTextures.size();
                init.layerSizes.add(layerSize);

                for (int i = 0; i < layerSize; i++) {
                    TiledMapTileLayer.Cell currentCell = new TiledMapTileLayer.Cell();
                    switch (levelTextures.get(i)) {
                        case "middleFloorTile":
                            currentCell = init.cr.middleFloorTile;
                            break;
                        case "topLeftWallTile":
                            currentCell = init.cr.topLeftWallTile;
                            Body newTopLeftWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopLeftWall.setUserData("Wall");
                            break;
                        case "topWallTile":
                            currentCell = init.cr.topWallTile;
                            Body newTopWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopWall.setUserData("Wall");
                            break;
                        case "topRightWallTile":
                            currentCell = init.cr.topRightWallTile;
                            Body newTopRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopRightWall.setUserData("Wall");
                            break;
                        case "leftWallTile":
                            currentCell = init.cr.leftWallTile;
                            Body newLeftWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newLeftWall.setUserData("Wall");
                            break;
                        case "rightWallTile":
                            currentCell = init.cr.rightWallTile;
                            Body newRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newRightWall.setUserData("Wall");
                            break;
                        case "bottomLeftWallTile":
                            currentCell = init.cr.bottomLeftWallTile;
                            Body newBottomLeftWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomLeftWall.setUserData("Wall");
                            break;
                        case "bottomWallTile":
                            currentCell = init.cr.bottomWallTile;
                            Body newBottomWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomWall.setUserData("Wall");
                            break;
                        case "bottomRightWallTile":
                            currentCell = init.cr.bottomRightWallTile;
                            Body newBottomRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomRightWall.setUserData("Wall");
                            break;
                        case "topLeftTurnTile":
                            currentCell = init.cr.topLeftTurnTile;
                            Body newTopLeftTurn = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 0.1f);
                            newTopLeftTurn.setUserData("Wall");
                            break;
                        case "topRightTurnTile":
                            currentCell = init.cr.topRightTurnTile;
                            Body newTopRightTurn = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 0.1f);
                            newTopRightTurn.setUserData("Wall");
                            break;
                        case "bottomLeftTurnTile":
                            currentCell = init.cr.bottomLeftTurnTile;
                            Body newBottomLeftTurn = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
                            newBottomLeftTurn.setUserData("Wall");
                            break;
                        case "bottomRightTurnTile":
                            currentCell = init.cr.bottomRightTurnTile;
                            Body newBottomRightTurn = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
                            newBottomRightTurn.setUserData("Wall");
                            break;
                        case "enemy":
                            currentCell = init.cr.middleFloorTile;
                            Enemy enemy = new Enemy(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            DungeonCrawler.enemies.add(enemy);
                            break;
                        case "doorTopLeftWall":
                            if (((nextDirection == 1 || doorDirection == 3))) {
                                currentCell = init.cr.doorTopLeftWall;
                                Body newDoorTopLeftWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newDoorTopLeftWall.setUserData("Wall");
                                break;
                            }
                            else {
                                currentCell = init.cr.topWallTile;
                                Body newTopWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newTopWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorTopRightWall":
                            if (((nextDirection == 1 || doorDirection == 3))) {
                                currentCell = init.cr.doorTopRightWall;
                                Body newDoorTopRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newDoorTopRightWall.setUserData("Wall");
                                break;
                            }
                            else {
                                currentCell = init.cr.topWallTile;
                                Body newTopWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newTopWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorLeftUpperWall":
                            if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {
                                currentCell = init.cr.doorLeftUpperWall;
                                Body newDoorLeftUpperWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newDoorLeftUpperWall.setUserData("Wall");
                                break;
                            }
                            else {
                                currentCell = init.cr.leftWallTile;
                                Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newLeftWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorLeftLowerWall":
                            if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {
                                currentCell = init.cr.doorLeftLowerWall;
                                Body newDoorLeftLowerWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newDoorLeftLowerWall.setUserData("Wall");
                                break;
                            }
                            else {
                                currentCell = init.cr.leftWallTile;
                                Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newLeftWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorRightUpperWall":
                            if (doorTop <= 1 && ((nextDirection == 1 || doorDirection == 3))) {
                                currentCell = init.cr.doorRightUpperWall;
                                Body newDoorRightUpperWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newDoorRightUpperWall.setUserData("Wall");
                                break;
                            }
                            else {
                                currentCell = init.cr.rightWallTile;
                                Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newRightWallCover.setUserData("Wall");
                                break;
                            }

                        case "doorRightLowerWall":
                            if (doorTop <= 1 && ((nextDirection == 1 || doorDirection == 3))) {
                                currentCell = init.cr.doorRightLowerWall;
                                Body newDoorRightLowerWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newDoorRightLowerWall.setUserData("Wall");
                                break;
                            }
                            else {
                                currentCell = init.cr.rightWallTile;
                                Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newRightWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorBottomLeftWall":
                            if (doorBottom <= 1 && ((nextDirection == 3 || doorDirection == 1))) {
                                currentCell = init.cr.doorBottomLeftWall;
                                Body newDoorBottomLeftWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newDoorBottomLeftWall.setUserData("Wall");
                                break;
                            }
                            else {
                                currentCell = init.cr.bottomWallTile;
                                Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newBottomWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorBottomRightWall":
                            if (doorBottom <= 1 && ((nextDirection == 3 || doorDirection == 1))) {
                                currentCell = init.cr.doorBottomRightWall;
                                Body newDoorBottomRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newDoorBottomRightWall.setUserData("Wall");
                                break;
                            }
                            else {
                                currentCell = init.cr.bottomWallTile;
                                Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newBottomWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorTopLeft":
                            if(roomIndex -1 == -1){

                            }
                            if (!startingRoom) {
                            if (doorTop <= 1  && ((nextDirection == 1 || doorDirection == 3))) {

                                    // String s = rooms.get(roomIndex).doorLocations.get("TopLeft");
                                    // s.split(",");
                                    currentCell = init.cr.doorTopLeft;
                                    doorTop++;
                                    break;
                                } else {
                                    currentCell = init.cr.topWallTile;
                                    Body newTopWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                    newTopWallCover.setUserData("Wall");
                                    break;
                                }
                            }
                            break;
                        case "doorTopRight":
                            if (!startingRoom) {
                                if (doorTop <= 1 && ((nextDirection == 1 || doorDirection == 3))) {

                                    //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("TopRight"));
                                    currentCell = init.cr.doorTopRight;
                                    doorTop++;
                                    break;
                                } else {
                                    currentCell = init.cr.topWallTile;
                                    Body newTopWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                    newTopWallCover.setUserData("Wall");
                                    break;
                                }
                            }
                            break;
                        case "doorLeftUpper":
                           // if((roomIndex - 1 != -1) && (init.roomList.get(roomIndex-1).directionTaken != 2)) {
                            if (!startingRoom) {
                                if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {

                                    //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("UpperLeft"));
                                    currentCell = init.cr.doorLeftUpper;
                                    doorLeft++;
                                    break;
                                } else {
                                    currentCell = init.cr.leftWallTile;
                                    Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                    newLeftWallCover.setUserData("Wall");
                                    break;
                                }
                            }
                            break;
                        case "doorLeftLower":
                           // if(roomIndex - 1 != -1) {
                            if (!startingRoom) {
                                if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {

                                    //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("LowerLeft"));
                                    currentCell = init.cr.doorLeftLower;
                                    doorLeft++;
                                    break;
                                } else {
                                    currentCell = init.cr.leftWallTile;
                                    Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                    newLeftWallCover.setUserData("Wall");
                                    break;
                                }
                            }
                            break;
                        case "doorRightUpper":
                            if (!startingRoom) {
                                if (doorRight <= 1 && ((nextDirection == 2 || doorDirection == 4))) {

                                    //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("UpperRight"));
                                    currentCell = init.cr.doorRightUpper;
                                    doorRight++;
                                    break;
                                }
                                else {
                                    currentCell = init.cr.rightWallTile;
                                    Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                    newRightWallCover.setUserData("Wall");
                                    break;
                                }
                            }
                            break;
                        case "doorRightLower":
                            if (!startingRoom) {
                            if (doorRight <= 1 && ((nextDirection == 2 || doorDirection == 4))) {

                                //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("LowerRight"));
                                currentCell = init.cr.doorRightLower;
                                doorRight++;
                                break;
                            }
                            else {
                                    currentCell = init.cr.rightWallTile;
                                    Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                    newRightWallCover.setUserData("Wall");
                                    break;
                                }
                            }
                            break;
                        case "doorBottomLeft":
                            if (!startingRoom) {
                            if (doorBottom <= 1 && ((nextDirection == 3 || doorDirection == 1))) {

                                    //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("BottomLeft"));
                                    currentCell = init.cr.doorBottomLeft;
                                    doorBottom++;
                                    break;
                                } else {
                                    currentCell = init.cr.bottomWallTile;
                                    Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                    newBottomWallCover.setUserData("Wall");
                                    break;
                                }
                            }
                            break;
                        case "doorBottomRight":
                            if (!startingRoom) {
                            if (doorBottom <= 1 && (nextDirection == 3 || doorDirection == 1)) {
                                //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("BottomRight"));
                                currentCell = init.cr.doorBottomRight;
                                doorBottom++;
                                break;
                            }
                            else {
                                    currentCell = init.cr.bottomWallTile;
                                    Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                    newBottomWallCover.setUserData("Wall");
                                    break;
                                }
                            }
                            break;
                        case "obstacle1":
                            currentCell = init.cr.obstacle1;
                            Body newObstacle1 = bf.createObstacle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "obstacle2":
                            currentCell = init.cr.obstacle2;
                            Body newObstacle2 = bf.createObstacle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "obstacle3":
                            currentCell = init.cr.obstacle3;
                            Body newObstacle3 = bf.createObstacle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                    }

                    layer.setCell((roomX + i) + 16, levelY, currentCell);

                    if (startingRoom) {
                        //set player starting coordinates according to the position of the first generated room

                        PLAYER_Y = init.roomList.get(0).y1 * 16;
                        PLAYER_X = init.roomList.get(0).x1 * 16;
                        startingRoom = false;

                        /*
                        PLAYER_X = ((roomX + i) * 16) + Gdx.graphics.getHeight() / 30;
                        PLAYER_Y = (levelY * 16 + currentRoomSize * 16 - 16) + Gdx.graphics.getHeight() / 30;
                        //player.createPlayer(world, PLAYER_X, PLAYER_Y);
                         */
                    }
                }
                //lower Y by 1 to move down one row
                levelY--;
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        //add current layers to the TileMap and assign it a renderer
        list = new ArrayList();
        list.add(layer);
        list.add(PLAYER_X);
        list.add(PLAYER_Y);
        return list;
    }
}