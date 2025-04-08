package com.mygdx.game.level;

import box2dLight.ConeLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.*;
import com.mygdx.game.Random;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.behaviours.fsm.*;
import com.mygdx.game.level.objects.*;
import com.mygdx.game.level.objects.Tutorial;

import java.io.IOException;
import java.util.*;

import static com.mygdx.game.DungeonCrawler.*;

public class GenerateLevel {
    public static InitLevel init;
    private BodyFactory bf;
    private CreateAssets tx;
    private PickDirection pd;
    private SetRoomXandY xy;
    private CreateCorridor cc;
    private float PLAYER_X, PLAYER_Y;
    private TiledMapTileLayer layer;
    public static int roomX, levelY, testRoomX, testLevelY;
    public int initialTestRoomX, initialTestLevelY;
    public int roomsIndex;
    public int tries;
    public static int numRooms;
    public int testPreviousLongestRow, testLongestRow, testPreviousRoomSize, testCurrentRoomSize, testPreviousRoomX, testPreviousLevelY, testCurrentRow;
    private int xOffset, yOffset;
    private boolean startingRoom, roomHitboxCreated;
    private int doorTop, doorBottom, doorLeft, doorRight, doorResult;
    private ArrayList list, itemIndex;
    public static ArrayList<Integer> path;
    public ArrayList<Integer> directionsAvailableIndexed, possibleRoomNumbers;
    public HashMap<String, String> doorMap;
    public boolean failed;
    private HashMap<String, String> doorMapPrevious;
    private String itemKind;
    private int invMin, invMax, amountMin, amountMax, amountIndex, indexMin, indexMax, randomIndex, randomChosenIndex, invRandom, cost;
    private int speechMinY, speechMaxY, randomSpeechYOffset;
    private int speechMinX, speechMaxX, randomSpeechXOffset;

    public ArrayList generateLevel(float PLAYER_X, float PLAYER_Y) {

        init = new InitLevel();
        init.InitializeLevel();
        pd = new PickDirection();
        xy = new SetRoomXandY();
        cc = new CreateCorridor();
        layer = init.layer;
        roomX = init.roomX;
        levelY = init.levelY;
        testRoomX = init.testRoomX;
        testLevelY = init.testLevelY;
        roomsIndex = 0;
        roomHitboxCreated = false;

        int min = 11;
        int max = 11;
        numRooms = (int) (Math.random() * (max - min + 1)) + min;

        possibleRoomNumbers = new ArrayList<>();

        int roomIDMax = 12;

        for (int i = 0; i < roomIDMax; i++) {
            //create an arraylist of all possible room IDs
            possibleRoomNumbers.add(i);
        }


        path = new ArrayList() {
        };

        boolean temp;
        temp = attemptLevelGen(1);

        return list;
    }

    public boolean attemptLevelGen(int level) {

        int currentDoorDirection = 0;
        int previousDoorDirection = 0;
        currentDoorDirection =  pd.pickInitialDirection(currentDoorDirection);

        for (int i = 0; i<numRooms*2; i++){
            if (i-1 != -1){
                previousDoorDirection = path.get(i-1);
            }
            else {
                previousDoorDirection = 0;
            }
            currentDoorDirection =  pd.pickInitialDirection(currentDoorDirection);

            path.add(i, currentDoorDirection);
            path.add(i+1, currentDoorDirection);
            i++;
        }

        for (int i = 0; i < numRooms; i++) {
            Room newRoom = new Room();
            init.roomList.add(newRoom);
            //newRoom.directionTaken = init.roomList.get(i).directionTaken;
            roomsIndex++;
            newRoom.index = i;
            //room number randomizer
            if (i == 0){
                newRoom.roomNum = 0;
            }

            else {
                if (i == 5 || i == 10) {
                    newRoom.roomNum = 5;
                }
                //else if (i == 1){
                //    newRoom.roomNum = 4;
                //}

                else {
                    //pick a random room ID then take it out of the arraylist - no room will appear twice
                    int random = Random.randomInt(possibleRoomNumbers.size()-1, 0);
                    int IDchosen = random;
                    random = possibleRoomNumbers.get(random) + 1;
                    possibleRoomNumbers.remove(IDchosen);

                    while (random == 5) {
                        //shop spawns are pre-determined so are reassigned before being generated randomly
                        random = Random.randomInt(12, 1);
                    }
                    //assign the room its random index
                    int temp = numRooms + 1;
                    if (roomsIndex == numRooms - 1) {
                        newRoom.roomNum = 13;
                    } else {
                        newRoom.roomNum = random;
                    }


                    //newRoom.roomNum = 13;
                }

                //determines which pre-gen room is placed next in sequence
                //rooms are numbered, room1 etc

                //shops are always unlocked
                if (newRoom.roomNum == 5) {
                    newRoom.isShop = true;
                    newRoom.unlockAllDoors(world, newRoom,false);
                }
            }
        }

        for (int i = 0; i < numRooms; i++) {
            //   roomsIndex++;
            directionsAvailableIndexed = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
            if (i == 0) {
                startingRoom = true;
                //doorDirection = 0;
                boolean temp;
                testGenerateRoom(startingRoom, path.get(i), i);
            } else {
                startingRoom = false;
                boolean temp;
                temp = testGenerateRoom(startingRoom, path.get(i), i);
                if (!temp) {
                    path.clear();
                    System.out.println("ERROR: LEVEL ROOMS INTERSECTED - PLEASE RERUN PROGRAM");
                    list = new ArrayList();
                    return false;
                }
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
                        0,
                        init.roomList.get(r).index,
                        path.get(r), 0, init.roomList.get(r + 1).directionTaken,
                        init.roomList.get(r).x1, init.roomList.get(r).y1,
                        init.roomList.get(r).roomSize, 0,
                        init.roomList.get(r).longestRow, 0
                );
                if ((r + 1 < numRooms+1)) {
                    if (init.roomList.get(r+1).directionTaken == 1) {
                        HashMap<String, String> doorMap =  init.roomList.get(r).doorLocations;
                        String doorBottomLeft = doorMap.get("TopLeft");
                        String[] doorBottomLeftXY = doorBottomLeft.split(",");
                        String doorBottomLeftX = doorBottomLeftXY[0];
                        float corridorStartX = Integer.parseInt(doorBottomLeftX);
                        cc.CreateCorridor(init.roomList.get(r).roomLayer, world, corridorStartX,init.roomList.get(init.roomList.get(r).index).y1+4, true);
                    }
                    if (init.roomList.get(r+1).directionTaken == 2) {
                        HashMap<String, String> doorMap =  init.roomList.get(r).doorLocations;
                        String doorUpperRight = doorMap.get("UpperRight");
                        String[] doorUpperRightXY = doorUpperRight.split(",");
                        String doorUpperRightY = doorUpperRightXY[1];
                        float corridorStartY = Integer.parseInt(doorUpperRightY);
                        cc.CreateCorridor(init.roomList.get(r).roomLayer, world, init.roomList.get(init.roomList.get(r).index).x2+1, corridorStartY+1, false);
                    }
                    if (init.roomList.get(r+1).directionTaken == 3) {
                        HashMap<String, String> doorMap =  init.roomList.get(r).doorLocations;
                        String doorTopLeft = doorMap.get("BottomLeft");
                        String[] doorTopLeftXY = doorTopLeft.split(",");
                        String doorTopLeftX = doorTopLeftXY[0];
                        float corridorStartX = Integer.parseInt(doorTopLeftX);
                        cc.CreateCorridor(init.roomList.get(r).roomLayer, world, corridorStartX, init.roomList.get(init.roomList.get(r).index).y2, true);
                    }
                    if (init.roomList.get(r+1).directionTaken == 4) {
                        HashMap<String, String> doorMap =  init.roomList.get(r).doorLocations;
                        String doorUpperLeft = doorMap.get("UpperLeft");
                        String[] doorUpperLeftXY = doorUpperLeft.split(",");
                        String doorTopLeftY = doorUpperLeftXY[1];
                        float corridorStartY = Integer.parseInt(doorTopLeftY);
                        cc.CreateCorridor(init.roomList.get(r).roomLayer, world,init.roomList.get(init.roomList.get(r).index).x1-3, corridorStartY+1, false);
                    }
                    init.roomList.get(r).unlockAllDoors(world, init.roomList.get(r), startingRoom);
                }
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

                    if ((r + 1 < numRooms+1)) {
                        if (init.roomList.get(r+1).directionTaken == 1) {
                            HashMap<String, String> doorMap =  init.roomList.get(r).doorLocations;
                            String doorBottomLeft = doorMap.get("TopLeft");
                            String[] doorBottomLeftXY = doorBottomLeft.split(",");
                            String doorBottomLeftX = doorBottomLeftXY[0];
                            float corridorStartX = Integer.parseInt(doorBottomLeftX);
                            cc.CreateCorridor(init.roomList.get(r).roomLayer, world, corridorStartX,init.roomList.get(init.roomList.get(r).index).y1+4, true);
                        }
                        if (init.roomList.get(r+1).directionTaken == 2) {
                            HashMap<String, String> doorMap =  init.roomList.get(r).doorLocations;
                            String doorUpperRight = doorMap.get("UpperRight");
                            String[] doorUpperRightXY = doorUpperRight.split(",");
                            String doorUpperRightY = doorUpperRightXY[1];
                            float corridorStartY = Integer.parseInt(doorUpperRightY);
                            cc.CreateCorridor(init.roomList.get(r).roomLayer, world, init.roomList.get(init.roomList.get(r).index).x2+1, corridorStartY+1, false);
                        }
                        if (init.roomList.get(r+1).directionTaken == 3) {
                            HashMap<String, String> doorMap =  init.roomList.get(r).doorLocations;
                            String doorBottomLeft = doorMap.get("BottomLeft");
                            String[] doorBottomLeftXY = doorBottomLeft.split(",");
                            String doorBottomLeftX = doorBottomLeftXY[0];
                            float corridorStartX = Integer.parseInt(doorBottomLeftX);
                            cc.CreateCorridor(init.roomList.get(r).roomLayer, world, corridorStartX, init.roomList.get(init.roomList.get(r).index).y2, true);
                        }
                        if (init.roomList.get(r+1).directionTaken == 4) {
                            HashMap<String, String> doorMap =  init.roomList.get(r).doorLocations;
                            String doorUpperLeft = doorMap.get("UpperLeft");
                            String[] doorUpperLeftXY = doorUpperLeft.split(",");
                            String doorTopLeftY = doorUpperLeftXY[1];
                            float corridorStartY = Integer.parseInt(doorTopLeftY);
                            cc.CreateCorridor(init.roomList.get(r).roomLayer, world,init.roomList.get(init.roomList.get(r).index).x1-3, corridorStartY+1, false);
                        }

                    }
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
            //outputs the X and Y values of every room, for debugging purposes
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

        for (Room r : init.roomList) {
            r.createLocks(world);

            if (r.roomNum == 0 && !DungeonCrawler.debug) {
                r.lockDoor(world, r);
            }
            if (r.roomNum > 0) {
                r.lockAllDoors(world, r, false);
            }
        }
        return true;
    }

    public boolean testGenerateRoom(boolean startingRoom, int currentDoorDirection, int roomIndex) {
        tries = 1;

        try {
            List<List<String>> roomFile = init.lp.read("Rooms/room" + init.roomList.get(roomIndex).roomNum + ".csv");

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

            if (failed) {
                return false;
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
             */


            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return true;
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

        int doorY = y1;

        locateDoors(roomIndex, x1, doorY);

        for (Room r : init.roomList) {

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
                            return failed = true;
                            /*
                                  tries++;
                            newRoom = initialRoom;
                            testLevelY = initialTestLevelY;
                            testRoomX = initialTestRoomX;

                             */

                        } else if ((newRoom.x1 < r.x2 && newRoom.x1 >= r.x1) && (newRoom.y1  > r.y2 &&  newRoom.y2 < r.y1)) {
                            System.out.println("ROOM " + roomIndex + " HAD A 'RIGHT' INTERSECTION WITH ROOM: " + (init.roomList.indexOf(r) + 1));
                            System.out.println("DIRECTION INDEX RIGHT INTERSECTION: "+directionsAvailableIndexed.indexOf(doorDirection));
                            System.out.println("DIRECTION VALUE RIGHT INTERSECTION: "+directionsAvailableIndexed.get(directionsAvailableIndexed.indexOf(doorDirection)));
                            //  init.roomList.remove(newRoom);
                            //if (directionsAvailableIndexed.contains(directionsAvailableIndexed.indexOf(doorDirection))){
                            //    directionsAvailableIndexed.remove(directionsAvailableIndexed.indexOf(doorDirection));
                            // }
                            //System.out.println("AVAILABLE DIRECTIONS REMAINING: " + directionsAvailableIndexed);
                            /*
                            tries++;
                            newRoom = initialRoom;
                            testLevelY = initialTestLevelY;
                            testRoomX = initialTestRoomX;
                            return failed = true;
                             */
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
        }

        init.roomList.get(roomIndex).x1 = initialRoom.x1;
        init.roomList.get(roomIndex).x2 = initialRoom.x2;
        init.roomList.get(roomIndex).y1 = initialRoom.y1;
        init.roomList.get(roomIndex).y2 = initialRoom.y2;
        newRoom = initialRoom;

        newRoom.x1 = x1;
        newRoom.x2 = x2;
        newRoom.y1 = y1;
        newRoom.y2 = y2;
        newRoom.directionTaken = doorDirection;

        doorResult = 0;
        xOffset = 0;
        yOffset = 0;

        if (startingRoom) {
            doorMapPrevious =  init.roomList.get(roomIndex).doorLocations;
        }
        else {
            doorMapPrevious =  init.roomList.get(roomIndex-1).doorLocations;
        }

            if (doorDirection==1) {
                String doorTopLeft = doorMapPrevious.get("TopLeft");
                String[] doorTopLeftXY = doorTopLeft.split(",");
                String doorTopLeftX = doorTopLeftXY[0].toString();
                String doorTopLeftY = doorTopLeftXY[1].toString();
                int doorTopLeftXAsInt = Integer.parseInt(doorTopLeftX);
                int doorTopLeftYAsInt = Integer.parseInt(doorTopLeftY);

                String doorBottomLeft = doorMap.get("BottomLeft");
                String[] doorBottomLeftXY = doorBottomLeft.split(",");
                String doorBottomLeftX = doorBottomLeftXY[0].toString();
                String doorBottomLeftY = doorBottomLeftXY[1].toString();
                int doorBottomLeftXAsInt = Integer.parseInt(doorBottomLeftX);
                int doorBottomLeftYAsInt = Integer.parseInt(doorBottomLeftY);

                if (doorTopLeftXAsInt > doorBottomLeftXAsInt){
                    doorResult = doorTopLeftXAsInt - doorBottomLeftXAsInt;

                    System.out.println("1 AND TOP LEFT IS LARGER");

                    yOffset = doorTopLeftYAsInt - doorBottomLeftYAsInt + 4;
                    System.out.println("YOFFSET----> "+ yOffset);

                    xOffset = doorResult;

                    System.out.println("XOFFSET---->: " + xOffset);

                    init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                    init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                    if ((path.get(roomIndex+1) == 2)) {
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);

                    }
                    else if ((path.get(roomIndex+1) == 4)) {
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1 + xOffset, init.roomList.get(roomIndex).y1);
                    }
                    else {
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    }

                }
                else if (doorBottomLeftXAsInt > doorTopLeftXAsInt) {
                    doorResult = doorBottomLeftXAsInt - doorTopLeftXAsInt;
                    doorResult = doorResult * -1;

                    yOffset = doorTopLeftYAsInt - doorBottomLeftYAsInt + 4;
                    xOffset = doorResult;

                    init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                    init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                    if (path.get(roomIndex+1) == 2 || path.get(roomIndex+1) == 4) {
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);

                        testRoomX = testRoomX + xOffset;
                    }
                    else {
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    }
                }
                else {
                    init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                    init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                    locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                }

            }
            if (doorDirection==2) {
                String doorUpperRight = doorMapPrevious.get("UpperRight");
                String[] doorUpperRightXY = doorUpperRight.split(",");
                //String doorUpperRightX = doorUpperRightXY[0].toString();
                String doorUpperRightY = doorUpperRightXY[1].toString();
                int doorUpperRightYAsInt = Integer.parseInt(doorUpperRightY);

                String doorUpperLeft = doorMap.get("UpperLeft");
                String[] doorUpperLeftXY = doorUpperLeft.split(",");
                //String doorUpperLeftX = doorUpperLeftXY[0].toString();
                String doorUpperLeftY = doorUpperLeftXY[1].toString();
                int doorUpperLeftYAsInt = Integer.parseInt(doorUpperLeftY);

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

                testLevelY = testLevelY + yOffset;

                locateDoors(roomIndex, init.roomList.get(roomIndex).x1+xOffset, init.roomList.get(roomIndex).y1);
            }
            if (doorDirection==3) {
                String doorTopLeft = doorMap.get("TopLeft");
                String[] doorTopLeftXY = doorTopLeft.split(",");
                String doorTopLeftX = doorTopLeftXY[0].toString();
                int doorTopLeftXAsInt = Integer.parseInt(doorTopLeftX);

                String doorBottomLeft = doorMapPrevious.get("BottomLeft");
                String[] doorBottomLeftXY = doorBottomLeft.split(",");
                String doorBottomLeftX = doorBottomLeftXY[0].toString();
                int doorBottomLeftXAsInt = Integer.parseInt(doorBottomLeftX);


                if (doorTopLeftXAsInt > doorBottomLeftXAsInt){
                    doorResult = doorBottomLeftXAsInt - doorTopLeftXAsInt;
                    xOffset = doorResult;

                    init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                    init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                    locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1+yOffset);
                }
                else if (doorBottomLeftXAsInt > doorTopLeftXAsInt) {
                    doorResult = doorTopLeftXAsInt - doorBottomLeftXAsInt;
                    doorResult = doorResult * -1;
                    xOffset = doorResult;

                    init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                    init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                    locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1+yOffset);
                } else {
                    locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1+yOffset);
                }
                if ((path.get(roomIndex+1) == 2 || path.get(roomIndex+1) == 4) ) {
                    testRoomX = testRoomX + xOffset;
                }
            }
            //TODO rooms generated to the left still sometimes move too far left - to be fixed
            if (doorDirection==4) {
                String doorUpperRight = doorMap.get("UpperRight");
                String[] doorUpperRightXY = doorUpperRight.split(",");
                String doorUpperRightY = doorUpperRightXY[1].toString();
                int doorUpperRightYAsInt = Integer.parseInt(doorUpperRightY);

                String doorUpperLeft = doorMapPrevious.get("UpperLeft");
                String[] doorUpperLeftXY = doorUpperLeft.split(",");
                String doorUpperLeftY = doorUpperLeftXY[1].toString();
                int doorUpperLeftYAsInt = Integer.parseInt(doorUpperLeftY);

                if (doorUpperLeftYAsInt > doorUpperRightYAsInt){
                    doorResult = doorUpperRightYAsInt - doorUpperLeftYAsInt;
                    System.out.println("Upper left is higher calculation: " + doorUpperRightYAsInt +"-"+ doorUpperLeftYAsInt );
                    doorResult = doorResult * -1;
                }
                else if (doorUpperRightYAsInt > doorUpperLeftYAsInt) {
                    doorResult = doorUpperLeftYAsInt - doorUpperRightYAsInt;

                }

                yOffset = doorResult;
                init.roomList.get(roomIndex).y1 = init.roomList.get(roomIndex).y1 + yOffset;
                init.roomList.get(roomIndex).y2 = init.roomList.get(roomIndex).y2 + yOffset;
                testLevelY = testLevelY + yOffset;
                locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);


                if (path.get(roomIndex+1) == 1 || path.get(roomIndex+1) == 3) {
                    //   testLevelY = testLevelY + yOffset;
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

                if (startingRoom) {
                    doorMap = init.rr.translateSymbolsToFindDoors(roomFile, rowNum, roomIndex, path.get(roomIndex), path.get(roomIndex), init.roomList.get(roomIndex).doorLocations, x1, doorY);
                }
                else{
                    doorMap = init.rr.translateSymbolsToFindDoors(roomFile, rowNum, roomIndex, path.get(roomIndex), path.get(roomIndex-1), init.roomList.get(roomIndex).doorLocations, x1, doorY);
                }
                doorY--;
            }

            init.roomList.get(roomIndex).doorLocations = doorMap;
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

        doorTop = 0;
        doorBottom = 0;
        doorLeft = 0;
        doorRight = 0;

        try {
            List<List<String>> room = init.lp.read("Rooms/room" + roomNum + ".csv");
            //levelY is what determines the size of the level.
            //When levelY is either 1000 or 0 the map will be outside the TiledMapTileLayer and thus will not render

         //   xy.setNextRoomDimensions(doorDirection, roomX, levelY, previousRoomSize, currentRoomSize, previousLongestRow, longestRow);

for (int rowNum = 0; rowNum < currentRoomSize; rowNum++) {
List<String> levelTextures = init.rr.translateSymbols(room, rowNum, init.roomList.indexOf(r), init.roomList.get(init.roomList.indexOf(r)).doorLocations, roomX, levelY);

//TODO NEXT \/ CREATE DOOR AREA HITBOX
if (!roomHitboxCreated){
    //create a box with the dimensions of the to-be-generated room - originally intended for collision detection but cannot be used that way
    //will instead be used for detecting if the player has entered a room for opening and closing doors
    init.roomList.get(roomIndex).roomHitbox = bf.createRoom(roomIndex, world, (((roomX * 16) + 16 * 16) + (longestRow * 16) / 2), ((levelY * 16 - (currentRoomSize * 16) / 2) + 16), currentRoomSize * 16 / 2-16, (longestRow * 16 / 2)-16);
    init.roomList.get(roomIndex).roomHitbox.setSensor(true);
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
        case "middleFloor2Tile":
            currentCell = init.cr.middleFloor2Tile;
            break;
        case "middleFloor3Tile":
            currentCell = init.cr.middleFloor3Tile;
            break;
        case "decorFloorUpTile":
            currentCell = init.cr.decorFloorUpTile;
            break;
        case "decorFloorDownTile":
            currentCell = init.cr.decorFloorDownTile;
            break;
        case "decorFloorLeftTile":
            currentCell = init.cr.decorFloorLeftTile;
            break;
        case "decorFloorRightTile":
            currentCell = init.cr.decorFloorRightTile;
            break;
        case "decorFloorTopLeftTile":
            currentCell = init.cr.decorFloorTopLeftTile;
            break;
        case "decorFloorTopRightTile":
            currentCell = init.cr.decorFloorTopRightTile;
            break;
        case "decorFloorBottomLeftTile":
            currentCell = init.cr.decorFloorBottomLeftTile;
            break;
        case "decorFloorBottomRightTile":
            currentCell = init.cr.decorFloorBottomRightTile;
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
        case "topWallTrapTile":
            currentCell = init.cr.topWallTile;
            Body newTopWallTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopWallTrap.setUserData("Wall");
            Trap topWallTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 1);
            topWallTrap.createTrap();
            traps.add(topWallTrap);
            break;
        case "topWallFireTrapTile":
            currentCell = init.cr.topWallTile;
            Body newTopWallFireTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopWallFireTrap.setUserData("Wall");
            Trap topWallFireTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,2, 1);
            topWallFireTrap.createTrap();
            traps.add(topWallFireTrap);
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
        case "leftWallTrapTile":
            currentCell = init.cr.leftWallTile;
            Body newLeftWallTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newLeftWallTrap.setUserData("Wall");
            Trap leftWallTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 4);
            leftWallTrap.createTrap();
            traps.add(leftWallTrap);
            break;
        case "leftWallFireTrapTile":
            currentCell = init.cr.leftWallTile;
            Body newLeftWallFireTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newLeftWallFireTrap.setUserData("Wall");
            Trap leftWallFireTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,2, 4);
            leftWallFireTrap.createTrap();
            traps.add(leftWallFireTrap);
            break;
        case "rightWallTile":
            currentCell = init.cr.rightWallTile;
            Body newRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newRightWall.setUserData("Wall");
            break;
        case "rightWallTrapTile":
            currentCell = init.cr.rightWallTile;
            Body newRightWallTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newRightWallTrap.setUserData("Wall");
            Trap rightWallTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30,1, 2);
            rightWallTrap.createTrap();
            traps.add(rightWallTrap);
            break;
        case "rightWallFireTrapTile":
            currentCell = init.cr.rightWallTile;
            Body newRightWallFireTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newRightWallFireTrap.setUserData("Wall");
            Trap rightWallFireTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30,2, 2);
            rightWallFireTrap.createTrap();
            traps.add(rightWallFireTrap);
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
        case "bottomWallTrapTile":
            currentCell = init.cr.bottomWallTile;
            Body newBottomWallTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newBottomWallTrap.setUserData("Wall");
            Trap bottomWallTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16 + 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 3);
            bottomWallTrap.createTrap();
            traps.add(bottomWallTrap);
            break;
        case "bottomWallFireTrapTile":
            currentCell = init.cr.bottomWallTile;
            Body newBottomWallFireTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newBottomWallFireTrap.setUserData("Wall");
            Trap bottomWallFireTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16 + 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,2, 3);
            bottomWallFireTrap.createTrap();
            traps.add(bottomWallFireTrap);
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

        case "topFenceTile":
            currentCell = init.cr.topFenceTile;
            Body newTopFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopFence.setUserData("Wall");
            break;
        case "bottomFenceTile":
            currentCell = init.cr.bottomFenceTile;
            Body newBottomFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newBottomFence.setUserData("Wall");
            break;
        case "leftFenceTile":
            currentCell = init.cr.leftFenceTile;
            Body newLeftFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newLeftFence.setUserData("Wall");
            break;
        case "rightFenceTile":
            currentCell = init.cr.rightFenceTile;
            Body newRightFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newRightFence.setUserData("Wall");
            break;

        case "bottomRightFenceTile":
            currentCell = init.cr.bottomRightFenceTile;
            Body newBottomRightFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newBottomRightFence.setUserData("Wall");
            break;
        case "bottomLeftFenceTile":
            currentCell = init.cr.bottomLeftFenceTile;
            Body newBottomLeftFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newBottomLeftFence.setUserData("Wall");
            break;
        case "topRightFenceTile":
            currentCell = init.cr.topRightFenceTile;
            Body newTopRightFence= init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopRightFence.setUserData("Wall");
            break;
        case "topLeftFenceTile":
            currentCell = init.cr.topLeftFenceTile;
            Body newTopLeftFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopLeftFence.setUserData("Wall");
            break;
        case "bottomRightTurnFenceTile":
            currentCell = init.cr.bottomRightTurnFenceTile;
            Body newBottomRightTurnFence = init.bf.createFenceTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 4f, 14f, 2);
            newBottomRightTurnFence.setUserData("Wall");
            break;
        case "bottomLeftTurnFenceTile":
            currentCell = init.cr.bottomLeftTurnFenceTile;
            Body newBottomLeftTurnFence = init.bf.createFenceTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 12f, 14f, 2);
            newBottomLeftTurnFence.setUserData("Wall");
            break;
        case "topRightTurnFenceTile":
            currentCell = init.cr.topRightTurnFenceTile;
            Body newTopRightTurnFence = init.bf.createFenceTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 1f, 2f, 1);
            newTopRightTurnFence.setUserData("Wall");
            break;
        case "topLeftTurnFenceTile":
            currentCell = init.cr.topLeftTurnFenceTile;
            Body newTopLeftTurnFence = init.bf.createFenceTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15f, 2f, 1);
            newTopLeftTurnFence.setUserData("Wall");
            break;
        case "bottomFenceLeftEndTile":
            currentCell = init.cr.bottomFenceLeftEndTile;
            Body newBottomLeftEndFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newBottomLeftEndFence.setUserData("Wall");
            break;
        case "bottomFenceRightEndTile":
            currentCell = init.cr.bottomFenceRightEndTile;
            Body newBottomRightEndFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newBottomRightEndFence.setUserData("Wall");
            break;
        case "topFenceLeftEndTile":
            currentCell = init.cr.topFenceLeftEndTile;
            Body newTopLeftEndFence= init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopLeftEndFence.setUserData("Wall");
            break;
        case "topFenceRightEndTile":
            currentCell = init.cr.topFenceRightEndTile;
            Body newTopRightEndFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopRightEndFence.setUserData("Wall");
            break;

        case "leftFenceTopEndTile":
            currentCell = init.cr.leftFenceTopEndTile;
            Body newLeftTopEndFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newLeftTopEndFence.setUserData("Wall");
            break;
        case "leftFenceBottomEndTile":
            currentCell = init.cr.leftFenceBottomEndTile;
            Body newLeftBottomEndFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newLeftBottomEndFence.setUserData("Wall");
            break;
        case "rightFenceTopEndTile":
            currentCell = init.cr.rightFenceTopEndTile;
            Body newRightTopEndFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newRightTopEndFence.setUserData("Wall");
            break;
        case "rightFenceBottomEndTile":
            currentCell = init.cr.rightFenceBottomEndTile;
            Body newRightBottomEndFence = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newRightBottomEndFence.setUserData("Wall");
            break;



        case "doorTopLeftWall":
            if (((nextDirection == 1 || doorDirection == 3))) {
                currentCell = init.cr.doorTopLeftWall;
                Body newDoorTopLeftWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newDoorTopLeftWall.setUserData("Wall");
                break;
            } else {
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
            } else {
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
            } else {
                currentCell = init.cr.leftWallTile;
                Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newLeftWallCover.setUserData("Wall");
                break;
            }
        case "doorLeftLowerWall":
            if (doorLeft <= 2 && ((nextDirection == 4 || doorDirection == 2))) {
                currentCell = init.cr.doorLeftLowerWall;
                Body newDoorLeftLowerWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newDoorLeftLowerWall.setUserData("Wall");
                break;
            } else {
                currentCell = init.cr.leftWallTile;
                Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newLeftWallCover.setUserData("Wall");
                break;
            }
        case "doorRightUpperWall":
            if (((nextDirection == 2 || doorDirection == 4))) {
                currentCell = init.cr.doorRightUpperWall;
                Body newDoorRightUpperWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newDoorRightUpperWall.setUserData("Wall");
                break;
            } else {
                currentCell = init.cr.rightWallTile;
                Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newRightWallCover.setUserData("Wall");
                break;
            }

        case "doorRightLowerWall":
            if (((nextDirection == 2 || doorDirection == 4))) {
                currentCell = init.cr.doorRightLowerWall;
                Body newDoorRightLowerWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newDoorRightLowerWall.setUserData("Wall");
                break;
            } else {
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
            } else {
                currentCell = init.cr.bottomWallTile;
                Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newBottomWallCover.setUserData("Wall");
                break;
            }
        case "doorBottomRightWall":
            if (doorBottom <= 2 && (nextDirection == 3 || doorDirection == 1)) {
                currentCell = init.cr.doorBottomRightWall;
                Body newDoorBottomRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newDoorBottomRightWall.setUserData("Wall");
                break;
            } else {
                currentCell = init.cr.bottomWallTile;
                Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newBottomWallCover.setUserData("Wall");
                break;
            }
        case "doorTopLeft":
            if (!startingRoom) {
                if (doorTop <= 1 && ((nextDirection == 1 || doorDirection == 3))) {
                    Door newDoorTopLeft = new Door(world, "TopLeft", init.roomList.get(roomIndex).doorLocations.get("TopLeft"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, false, true, roomNum);
                    newDoorTopLeft.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("TopLeft", newDoorTopLeft);
                    init.roomList.get(roomIndex).doors.add(newDoorTopLeft);

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
        case "doorTopLeftFence":
            if (!startingRoom) {
                if (doorTop <= 1 && ((nextDirection == 1 || doorDirection == 3))) {
                    Door newDoorTopLeft = new Door(world, "TopLeft", init.roomList.get(roomIndex).doorLocations.get("TopLeft"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, false, true, roomNum);
                    newDoorTopLeft.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("TopLeft", newDoorTopLeft);
                    init.roomList.get(roomIndex).doors.add(newDoorTopLeft);


                    currentCell = init.cr.doorTopLeft;
                    doorTop++;
                    break;
                } else {
                    currentCell = init.cr.topFenceTile;
                    Body newTopFenceCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newTopFenceCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "doorTopRight":
            if (!startingRoom) {
                if (doorTop <= 1 && ((nextDirection == 1 || doorDirection == 3))) {

                    Door newDoorTopRight = new Door(world, "TopRight", init.roomList.get(roomIndex).doorLocations.get("TopRight"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, false, false, roomNum);
                    newDoorTopRight.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("TopRight", newDoorTopRight);
                    init.roomList.get(roomIndex).doors.add(newDoorTopRight);

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
        case "doorTopRightFence":
            if (!startingRoom) {
                if (doorTop <= 1 && ((nextDirection == 1 || doorDirection == 3))) {

                    Door newDoorTopRight = new Door(world, "TopRight", init.roomList.get(roomIndex).doorLocations.get("TopRight"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, false, false, roomNum);
                    newDoorTopRight.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("TopRight", newDoorTopRight);
                    init.roomList.get(roomIndex).doors.add(newDoorTopRight);

                    currentCell = init.cr.doorTopRight;
                    doorTop++;
                    break;
                } else {
                    currentCell = init.cr.topFenceTile;
                    Body newTopFenceCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newTopFenceCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "doorLeftUpper":
            if (!startingRoom) {
                if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {

                    Door newDoorLeftUpper = new Door(world, "UpperLeft", init.roomList.get(roomIndex).doorLocations.get("UpperLeft"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true, true, roomNum);
                    newDoorLeftUpper.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("UpperLeft", newDoorLeftUpper);
                    init.roomList.get(roomIndex).doors.add(newDoorLeftUpper);

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
        case "doorLeftUpperFence":
            if (!startingRoom) {
                if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {

                    Door newDoorLeftUpper = new Door(world, "UpperLeft", init.roomList.get(roomIndex).doorLocations.get("UpperLeft"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true, true, roomNum);
                    newDoorLeftUpper.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("UpperLeft", newDoorLeftUpper);
                    init.roomList.get(roomIndex).doors.add(newDoorLeftUpper);

                    currentCell = init.cr.doorLeftUpper;
                    doorLeft++;
                    break;
                } else {
                    currentCell = init.cr.leftFenceTile;
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

                    Door newDoorLeftLower = new Door(world, "LowerLeft", init.roomList.get(roomIndex).doorLocations.get("LowerLeft"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true, false, roomNum);
                    newDoorLeftLower.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("LowerLeft", newDoorLeftLower);
                    init.roomList.get(roomIndex).doors.add(newDoorLeftLower);

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
        case "doorLeftLowerFence":
            // if(roomIndex - 1 != -1) {
            if (!startingRoom) {
                if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {

                    Door newDoorLeftLower = new Door(world, "LowerLeft", init.roomList.get(roomIndex).doorLocations.get("LowerLeft"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true, false, roomNum);
                    newDoorLeftLower.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("LowerLeft", newDoorLeftLower);
                    init.roomList.get(roomIndex).doors.add(newDoorLeftLower);

                    currentCell = init.cr.doorLeftLower;
                    doorLeft++;
                    break;
                } else {
                    currentCell = init.cr.leftFenceTile;
                    Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newLeftWallCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "doorRightUpper":
            if (!startingRoom) {
                if (doorRight <= 1 && ((nextDirection == 2 || doorDirection == 4))) {

                    Door newDoorRightUpper = new Door(world, "UpperRight", init.roomList.get(roomIndex).doorLocations.get("UpperRight"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true, true, roomNum);
                    newDoorRightUpper.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("UpperRight", newDoorRightUpper);
                    init.roomList.get(roomIndex).doors.add(newDoorRightUpper);

                    currentCell = init.cr.doorRightUpper;
                    doorRight++;
                    break;
                } else {
                    currentCell = init.cr.rightWallTile;
                    Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newRightWallCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "doorRightUpperFence":
            if (!startingRoom) {
                if (doorRight <= 1 && ((nextDirection == 2 || doorDirection == 4))) {

                    Door newDoorRightUpper = new Door(world, "UpperRight", init.roomList.get(roomIndex).doorLocations.get("UpperRight"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true, true, roomNum);
                    newDoorRightUpper.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("UpperRight", newDoorRightUpper);
                    init.roomList.get(roomIndex).doors.add(newDoorRightUpper);

                    currentCell = init.cr.doorRightUpper;
                    doorRight++;
                    break;
                } else {
                    currentCell = init.cr.rightFenceTile;
                    Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newRightWallCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "doorRightLower":
            if (!startingRoom) {
                if (doorRight <= 2 && ((nextDirection == 2 || doorDirection == 4))) {

                    Door newDoorRightLower = new Door(world, "LowerRight", init.roomList.get(roomIndex).doorLocations.get("LowerRight"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true, false, roomNum);
                    newDoorRightLower.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("LowerRight", newDoorRightLower);
                    init.roomList.get(roomIndex).doors.add(newDoorRightLower);

                    currentCell = init.cr.doorRightLower;
                    doorRight++;
                    break;
                } else {
                    currentCell = init.cr.rightWallTile;
                    Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newRightWallCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "doorRightLowerFence":
            if (!startingRoom) {
                if (doorRight <= 2 && ((nextDirection == 2 || doorDirection == 4))) {

                    Door newDoorRightLower = new Door(world, "LowerRight", init.roomList.get(roomIndex).doorLocations.get("LowerRight"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true, false, roomNum);
                    newDoorRightLower.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("LowerRight", newDoorRightLower);
                    init.roomList.get(roomIndex).doors.add(newDoorRightLower);

                    currentCell = init.cr.doorRightLower;
                    doorRight++;
                    break;
                } else {
                    currentCell = init.cr.rightFenceTile;
                    Body newRightWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newRightWallCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "doorBottomLeft":
            if (!startingRoom) {
                if (doorBottom <= 1 && ((nextDirection == 3 || doorDirection == 1))) {

                    Door newDoorBottomLeft = new Door(world, "BottomLeft", init.roomList.get(roomIndex).doorLocations.get("BottomLeft"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, false, true, roomNum);
                    newDoorBottomLeft.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("BottomLeft", newDoorBottomLeft);
                    init.roomList.get(roomIndex).doors.add(newDoorBottomLeft);

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
        case "doorBottomLeftFence":
            if (!startingRoom) {
                if (doorBottom <= 1 && ((nextDirection == 3 || doorDirection == 1))) {

                    Door newDoorBottomLeft = new Door(world, "BottomLeft", init.roomList.get(roomIndex).doorLocations.get("BottomLeft"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, false, true, roomNum);
                    newDoorBottomLeft.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("BottomLeft", newDoorBottomLeft);
                    init.roomList.get(roomIndex).doors.add(newDoorBottomLeft);

                    currentCell = init.cr.doorBottomLeft;
                    doorBottom++;
                    break;
                } else {
                    currentCell = init.cr.bottomFenceTile;
                    Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newBottomWallCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "doorBottomRight":
            if (!startingRoom) {
                if (doorBottom <= 1 && (nextDirection == 3 || doorDirection == 1)) {

                    Door newDoorBottomRight = new Door(world, "BottomRight", init.roomList.get(roomIndex).doorLocations.get("BottomRight"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, false, false, roomNum);
                    newDoorBottomRight.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("BottomRight", newDoorBottomRight);
                    init.roomList.get(roomIndex).doors.add(newDoorBottomRight);

                    currentCell = init.cr.doorBottomRight;
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
        case "doorBottomRightFence":
            if (!startingRoom) {
                if (doorBottom <= 1 && (nextDirection == 3 || doorDirection == 1)) {

                    Door newDoorBottomRight = new Door(world, "BottomRight", init.roomList.get(roomIndex).doorLocations.get("BottomRight"), ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, false, false, roomNum);
                    newDoorBottomRight.createDoor();
                    init.roomList.get(roomIndex).doorArrayMap.put("BottomRight", newDoorBottomRight);
                    init.roomList.get(roomIndex).doors.add(newDoorBottomRight);

                    currentCell = init.cr.doorBottomRight;
                    doorBottom++;
                    break;
                } else {
                    currentCell = init.cr.bottomFenceTile;
                    Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                    newBottomWallCover.setUserData("Wall");
                    break;
                }
            }
            break;
        case "obstacle":
            int rand = Random.randomInt(3,1);
            currentCell = init.cr.middleFloorTile;
            Obstacle newObstacle = new Obstacle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, rand);
            obstacles.add(newObstacle);
            break;
        case "obstacle1":
            currentCell = init.cr.middleFloorTile;
            Obstacle newObstacle1 = new Obstacle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 1);
            obstacles.add(newObstacle1);
            break;
        case "obstacle2":
            currentCell = init.cr.middleFloorTile;
            Obstacle newObstacle2 = new Obstacle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 2);
            obstacles.add(newObstacle2);
            break;
        case "obstacle3":
            currentCell = init.cr.middleFloorTile;
            Obstacle newObstacle3 = new Obstacle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 3);
            obstacles.add(newObstacle3);
            break;
        case "cand":
            currentCell = init.cr.middleFloorTile;
            Candle newCandle = new Candle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 1);
            Body candBody = newCandle.createCandle();
            Fire fCan = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 5.5f,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 3.5f, false,0f, 3, false, 0);
            fires.add(fCan);
            fCan.createFire(new Color(0.25f,0.20f,0,0.7f),20, null);
            candles.add(newCandle);
            lights.add(fCan);
            break;
        case "cands":
            currentCell = init.cr.middleFloorTile;
            Candle newCandles = new Candle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 2);
            Body candsBody = newCandles.createCandle();
            Fire fCans = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 4.5f,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 2.5f, false,0f, 3, false, 0);
            fires.add(fCans);
            lights.add(fCans);
            fCans.createFire(new Color(0.25f,0.20f,0,0.5f),20, null);
            Fire fCans2 = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 8.5f,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 6.5f, false,0f, 3, false, 0);
            fires.add(fCans2);
            lights.add(fCans2);
            fCans2.createFire(new Color(0.25f,0.20f,0,0.5f),20, null);

            candles.add(newCandles);
            break;
        //tutorial in starting room
        case "tuto":
            currentCell = init.cr.middleFloorTile;
            Tutorial t = new Tutorial(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            tutorial.add(t);
            break;
        case "torl":
            currentCell = init.cr.torchWallLeftTile;
            Body newTorchWallLeft = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTorchWallLeft.setUserData("Wall");
            Torch torL = new Torch(rayHandler, world, (((roomX + i) * 16) + 16 * 16) + 6, (levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8);
            Fire fL = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 4,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 4, true,0f, 3, false, 4);
            fires.add(fL);
            torches.add(torL);
            lights.add(torL);
            ConeLight torchLightL = torL.createTorch(4);
            Color colorFL = new Color(0.25f,0.20f,0,0.7f);
            Color colorBL = new Color(0f,0,1f,0.7f);

            if (roomNum == 13) {
                fL.blue = true;
                fL.createFire(colorBL,60,torchLightL);
                init.roomList.get(roomIndex).fires.add(fL);
            } else {
                fL.blue = false;
                fL.createFire(colorFL,60,torchLightL);
            }
            break;
        case "torloff":
            currentCell = init.cr.torchWallLeftTile;
            Body newTorchWallLeftOff = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTorchWallLeftOff.setUserData("Wall");
            Torch torLoff = new Torch(rayHandler, world, (((roomX + i) * 16) + 16 * 16) + 6, (levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8);
            Fire fLoff = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 4,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 4, true,0f, 3, false, 4);
            fires.add(fLoff);
            torches.add(torLoff);
            lights.add(torLoff);
            ConeLight torchLightLOff = torLoff.createTorch(4);
            fLoff.createFire(new Color(0.25f,0.20f,0,0.7f),60, torchLightLOff);
            fLoff.extinguish = false;
            fLoff.smoking = true;
            fLoff.active = false;
            break;
        case "torr":
            currentCell = init.cr.torchWallRightTile;
            Body newTorchWallRight = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTorchWallRight.setUserData("Wall");
            Torch torR = new Torch(rayHandler, world, (((roomX + i) * 16) + 16 * 16) + 10, (levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8);
            Fire fR = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 7,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 4, true,0f, 3, false, 2);
            fires.add(fR);
            torches.add(torR);
            lights.add(torR);
            ConeLight torchLightR = torR.createTorch(2);

            Color colorFR = new Color(0.25f,0.20f,0,0.7f);
            Color colorBR = new Color(0f,0,1f,0.7f);

            if (roomNum == 13) {
                fR.blue = true;
                fR.createFire(colorBR,60,torchLightR);
                init.roomList.get(roomIndex).fires.add(fR);
            } else {
                fR.blue = false;
                fR.createFire(colorFR,60,torchLightR);
            }
            break;
        case "torroff":
            currentCell = init.cr.torchWallRightTile;
            Body newTorchWallRightOff = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTorchWallRightOff.setUserData("Wall");
            Torch torRoff = new Torch(rayHandler, world, (((roomX + i) * 16) + 16 * 16) + 10, (levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8);
            Fire fRoff = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 7,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 4, true,0f, 3, false, 2);
            fires.add(fRoff);
            torches.add(torRoff);
            lights.add(torRoff);
            ConeLight torchLightROff = torRoff.createTorch(2);
            fRoff.createFire(new Color(0.25f,0.20f,0,0.7f),60, torchLightROff);
            fRoff.extinguish = false;
            fRoff.smoking = true;
            fRoff.active = false;
            break;
        case "toru":
            currentCell = init.cr.torchWallUpTile;
            Body newTorchWallUp = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTorchWallUp.setUserData("Wall");
            Torch torU = new Torch(rayHandler, world, (((roomX + i) * 16) + 16 * 16) + 8, (levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 15);
            Fire fU = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 5,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 5, true,0f, 3, false, 1);
            fires.add(fU);
            torches.add(torU);
            lights.add(torU);
            ConeLight torchLightU = torU.createTorch(1);
            Color colorFU = new Color(0.25f,0.20f,0,0.7f);
            Color colorBU = new Color(0f,0,1f,0.7f);

            if (roomNum == 13) {
                fU.blue = true;
                fU.createFire(colorBU,60,torchLightU);
                init.roomList.get(roomIndex).fires.add(fU);
            } else {
                fU.blue = false;
                fU.createFire(colorFU,60,torchLightU);
            }
            break;
        case "toruoff":
            currentCell = init.cr.torchWallUpTile;
            Body newTorchWallUpOff = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTorchWallUpOff.setUserData("Wall");
            Torch torUoff = new Torch(rayHandler, world, (((roomX + i) * 16) + 16 * 16) + 8, (levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 15);
            Fire fUoff = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 5,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 5, true,0f, 3, false, 1);
            fires.add(fUoff);
            torches.add(torUoff);
            lights.add(torUoff);
            ConeLight torchLightUOff = torUoff.createTorch(1);
            fUoff.createFire(new Color(0.25f,0.20f,0,0.7f),60,torchLightUOff);
            fUoff.extinguish = false;
            fUoff.smoking = true;
            fUoff.active = false;
            break;
        case "tord":
            currentCell = init.cr.torchWallDownTile;
            Body newTorchWallDown = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTorchWallDown.setUserData("Wall");
            Torch torD = new Torch(rayHandler, world, (((roomX + i) * 16) + 16 * 16) + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 10);
            Fire fD = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 + 10,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 + 7, true,0f, 3, true, 3);
            fires.add(fD);
            torches.add(torD);
            lights.add(torD);
            ConeLight torchLightD = torD.createTorch(3);
            Color colorFD = new Color(0.25f,0.20f,0,0.7f);
            Color colorBD = new Color(0f,0,1f,0.7f);

            if (roomNum == 13) {
                fD.blue = true;
                fD.createFire(colorBD,60,torchLightD);
                init.roomList.get(roomIndex).fires.add(fD);
            } else {
                fD.blue = false;
                fD.createFire(colorFD,60,torchLightD);
            }
            break;
        case "tordoff":
            currentCell = init.cr.torchWallDownTile;
            Body newTorchWallDownOff = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTorchWallDownOff.setUserData("Wall");
            Torch torDoff = new Torch(rayHandler, world, (((roomX + i) * 16) + 16 * 16) + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 10);
            Fire fDoff = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 + 10,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 + 7, true,0f, 3, true, 3);
            fires.add(fDoff);
            torches.add(torDoff);
            lights.add(torDoff);
            ConeLight torchLightDOff = torDoff.createTorch(3);
            fDoff.createFire(new Color(0.25f,0.20f,0,0.7f),60, torchLightDOff);
            fDoff.extinguish = false;
            fDoff.smoking = true;
            fDoff.active = false;
            break;
        case "pot":
            currentCell = init.cr.middleFloorTile;
            int randOb = Random.randomInt(9,1);
            Pot p = new Pot(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, randOb);
            pots.add(p);
            break;
        case "pot2":
            currentCell = init.cr.middleFloorTile;
            Pot p2 = new Pot(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 2);
            pots.add(p2);
            break;

        case "cobweb":
            currentCell = init.cr.middleFloorTile;
            Cobweb c1 = new Cobweb(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true);
            cobwebs.add(c1);
            break;

        //entities
        case "enemySkull":
            currentCell = init.cr.middleFloorTile;
            EnemySkull enemy = new EnemySkull(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemySkulls.add(enemy);
            enemy.createEnemy(1, 150);
            enemies.add(enemy);
            enemy.room = roomIndex;
            DungeonCrawler.enemySkulls.add(enemy);
            break;
        case "enemySpider":
            currentCell = init.cr.middleFloorTile;
            EnemySpider enemy2 = new EnemySpider(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemySpiders.add(enemy2);
            enemy2.createEnemy(2, 150);
            enemies.add(enemy2);
            enemy2.room = roomIndex;
            DungeonCrawler.enemySpiders.add(enemy2);
            break;
        case "enemyGhost":
            currentCell = init.cr.middleFloorTile;
            EnemyGhost enemy3 = new EnemyGhost(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemyGhosts.add(enemy3);
            enemy3.createEnemy(3, 40);
            enemies.add(enemy3);
            enemy3.room = roomIndex;
            DungeonCrawler.enemyGhosts.add(enemy3);
            break;
        case "enemyCyclops":
            currentCell = init.cr.middleFloorTile;
            EnemyCyclops enemy4 = new EnemyCyclops(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemyEyes.add(enemy4);
            enemy4.createEnemy(4, 40);
            enemies.add(enemy4);
            enemy4.room = roomIndex;
            DungeonCrawler.enemyEyes.add(enemy4);
            break;
        case "bossMinotaur":
            currentCell = init.cr.middleFloorTile;
            BossMinotaur boss1 = new BossMinotaur(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).bossMinotaurs.add(boss1);
            boss1.createEnemy(5, boss1.defaultSpeed);
            enemies.add(boss1);
            boss1.room = roomIndex;
            DungeonCrawler.bossMinotaurs.add(boss1);
            break;
        case "shop":
            currentCell = init.cr.middleFloor3Tile;

            Text shopMessage = new Text(DungeonCrawler.defaultFont, "WELCOME!", Color.WHITE, true, 1f, 0.0200f, false, false, null, 0);
            speechMinX = -10;
            speechMaxX = 10;
            randomSpeechXOffset = (int) (Math.random() * (speechMaxX - speechMinX + 1)) + speechMinX;

            speechMinY = -10;
            speechMaxY = 10;
            randomSpeechYOffset = (int) (Math.random() * (speechMaxY - speechMinY + 1)) + speechMinY;
            shopMessage.textX = ((roomX + i) * 16) + 16 * 16 - 16 + randomSpeechXOffset;
            shopMessage.textY = levelY * 16 + Gdx.graphics.getHeight() / 30 - 1 + randomSpeechYOffset;
            messages.add(shopMessage);
            Shopkeeper shopkeeper = new Shopkeeper(DungeonCrawler.world, ((roomX + i) * 16) + 16.5f * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 4, shopMessage);

            invMin = 2;
            invMax = 4;
            invRandom = (int) (Math.random() * (invMax - invMin + 1)) + invMin;

            itemIndex = new ArrayList();
            itemIndex.add(0,1);
            itemIndex.add(1,2);
            itemIndex.add(2,3);
            itemIndex.add(3,4);
            itemIndex.add(4,5);
            itemIndex.add(5,6);

            indexMin = 0;
            indexMax = 5;

            for (int i2 = 0; i2 < 6; i2++) {
            //TODO: Make this only pick between items that haven't been chosen yet

            randomIndex = (int) (Math.random() * (indexMax - indexMin + 1)) + indexMin;

            randomChosenIndex = (int) itemIndex.get(randomIndex);
            itemIndex.remove(randomIndex);

            indexMax--;

            amountMin = 1;
            amountMax = 1;
            amountIndex = (int) (Math.random() * (amountMax - amountMin + 1)) + amountMin;

            switch (randomChosenIndex) {
                case 1:
                    itemKind = "WINE";
                    cost = 3;
                    break;
                case 2:
                    itemKind = "GREEK FIRE";
                    cost = 5;
                    break;
                case 3:
                    itemKind = "SHIELD";
                    cost = 6;
                    break;
                case 4:
                    itemKind = "TORCH";
                    cost = 6;
                    break;
                case 5:
                    itemKind = "BELT";
                    cost = 2;
                    break;
                case 6:
                    itemKind = "CHISEL";
                    cost = 3;
                    break;
                case 7:
                    itemKind = "LANCE";
                    cost = 5;
                    break;
                case 8:
                    itemKind = "HELM";
                    cost = 5;
                    break;
            }
                Text t2 = shopkeeper.Stock(itemKind, i2);
                Text t3 = shopkeeper.DescribeStock(itemKind, i2, cost);
                ShopItem s1 = new ShopItem();
                s1.createItem(i2, itemKind, amountIndex, cost, t3);
                shopkeeper.inventory.put(i2, s1);
                //overall placement of the text
                t2.textX = shopkeeper.posX - 74;
                t2.textY = shopkeeper.posY + 37;
                t3.textX = shopkeeper.posX - 69;
                t3.textY = shopkeeper.posY + 37;
    }
        shopkeeper.messages.add(shopMessage);
        DungeonCrawler.shopkeepers.add(shopkeeper);
        break;
        case "twColTop1":
        case "toruColTop1":
            currentCell = init.cr.topWallTile;
        Body newTopWallCol1 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
        newTopWallCol1.setUserData("Wall");
        Column twCol1 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1);
        twCol1.createColumnTop(false);
        break;
        case "twColTop2":
        case "toruColTop2":
        currentCell = init.cr.topWallTile;
        Body newTopWallCol2 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
        newTopWallCol2.setUserData("Wall");
        Column twCol2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,2);
        twCol2.createColumnTop(false);
        break;
        case "twColTop3":
        case "toruColTop3":
        currentCell = init.cr.topWallTile;
        Body newTopWallCol3 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
        newTopWallCol3.setUserData("Wall");
        Column twCol3 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,3);
        twCol3.createColumnTop(false);
        break;
        case "twColStem":
        case "toruColStem":
        currentCell = init.cr.topWallTile;
        Body newTopWallCol4 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
        newTopWallCol4.setUserData("Wall");
        Column twCol4 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,4);
        twCol4.createColumnStem(false);
        break;
        case "twColStemDamaged1":
        case "toruColStemDamaged1":
        currentCell = init.cr.topWallTile;
        Body newTopWallCol5 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
        newTopWallCol5.setUserData("Wall");
        Column twCol5 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,5);
        twCol5.createColumnStem(false);
        break;
        case "twColStemDamaged2":
        case "toruColStemDamaged2":
        currentCell = init.cr.topWallTile;
        Body newTopWallCol6 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
        newTopWallCol6.setUserData("Wall");
        Column twCol6 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,6);
        twCol6.createColumnStem(false);
        break;
        case "twColTop4":
        case "toruColTop4":
            currentCell = init.cr.topWallTile;
            Body newTopWallCol10 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopWallCol10.setUserData("Wall");
            Column twCol10 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,10);
            twCol10.createColumnTop(false);
            break;
        case "twColTop5":
        case "toruColTop5":
            currentCell = init.cr.topWallTile;
            Body newTopWallCol11 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopWallCol11.setUserData("Wall");
            Column twCol11 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,11);
            twCol11.createColumnTop(false);
            break;
        case "twColStem2":
        case "toruColStem2":
            currentCell = init.cr.topWallTile;
            Body newTopWallCol12 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopWallCol12.setUserData("Wall");
            Column twCol12 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,12);
            twCol12.createColumnTop(false);
            break;
        case "twColStem3":
        case "toruColStem3":
            currentCell = init.cr.topWallTile;
            Body newTopWallCol13 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopWallCol13.setUserData("Wall");
            Column twCol13 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,13);
            twCol13.createColumnTop(false);
            break;
        case "twCol10Fire":
            currentCell = init.cr.topWallTile;
            Body newTopWallCol10Fire = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            newTopWallCol10Fire.setUserData("Wall");
            Column twFireCol10 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,10);
            twFireCol10.createColumnTop(false);
            Fire twfirecol10 = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 3, false, 0f, 1, false, 0);
            twfirecol10.createFire(new Color(0.25f,0.20f,0,0.75f),60, null);
            fires.add(twfirecol10);
            lights.add(twfirecol10);
            break;
        case "brtColTop1":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnCol1 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnCol1.setUserData("Wall");
            Column brtCol1 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1);
            brtCol1.createColumnTop(false);
            break;
        case "brtColTop2":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnCol2 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnCol2.setUserData("Wall");
            Column brtCol2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,2);
            brtCol2.createColumnTop(false);
            break;
        case "brtColTop3":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnCol3 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnCol3.setUserData("Wall");
            Column brtCol3 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,3);
            brtCol3.createColumnTop(false);
            break;
        case "brtColTop4":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnCol4 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnCol4.setUserData("Wall");
            Column brtCol4 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,10);
            brtCol4.createColumnTop(false);
            break;
        case "brtColTop5":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnCol5 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnCol5.setUserData("Wall");
            Column brtCol5 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,11);
            brtCol5.createColumnTop(false);
            break;
        case "brtColStem1":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnColStem = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnColStem.setUserData("Wall");
            Column brtColStem = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,4);
            brtColStem.createColumnStem(false);
            break;
        case "brtColStemDamaged1":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnColStemDamaged1 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnColStemDamaged1.setUserData("Wall");
            Column brtColStemDamaged1 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,5);
            brtColStemDamaged1.createColumnStem(false);
            break;
        case "brtColStemDamaged2":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnColStemDamaged2 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnColStemDamaged2.setUserData("Wall");
            Column brtColStemDamaged2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,6);
            brtColStemDamaged2.createColumnStem(false);
            break;
        case "brtColStem2":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnColStem2 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnColStem2.setUserData("Wall");
            Column brtColStem2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,12);
            brtColStem2.createColumnTop(false);
            break;
        case "brtColStem3":
            currentCell = init.cr.bottomRightTurnTile;
            Body newBottomRightTurnColStem3 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBottomRightTurnColStem3.setUserData("Wall");
            Column brtColStem3 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,13);
            brtColStem3.createColumnTop(false);
            break;
        case "bltColTop1":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnCol1 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnCol1.setUserData("Wall");
            Column bltCol1 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1);
            bltCol1.createColumnTop(false);
            break;
        case "bltColTop2":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnCol2 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnCol2.setUserData("Wall");
            Column bltCol2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,2);
            bltCol2.createColumnTop(false);
            break;
        case "bltColTop3":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnCol3 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnCol3.setUserData("Wall");
            Column bltCol3 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,3);
            bltCol3.createColumnTop(false);
            break;
        case "bltColTop4":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnCol4 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnCol4.setUserData("Wall");
            Column bltCol4 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,10);
            bltCol4.createColumnTop(false);
            break;
        case "bltColTop5":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnCol5 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnCol5.setUserData("Wall");
            Column bltCol5 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,11);
            bltCol5.createColumnTop(false);
            break;
        case "bltColStem1":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnColStem = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnColStem.setUserData("Wall");
            Column bltColStem = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,4);
            bltColStem.createColumnStem(false);
            break;
        case "bltColStemDamaged1":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnColStemDamaged1 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnColStemDamaged1.setUserData("Wall");
            Column bltColStemDamaged1 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,5);
            bltColStemDamaged1.createColumnStem(false);
            break;
        case "bltColStemDamaged2":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnColStemDamaged2 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnColStemDamaged2.setUserData("Wall");
            Column bltColStemDamaged2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,6);
            bltColStemDamaged2.createColumnStem(false);
            break;
        case "bltColStem2":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnColStem2 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnColStem2.setUserData("Wall");
            Column bltColStem2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,12);
            bltColStem2.createColumnTop(false);
            break;
        case "bltColStem3":
            currentCell = init.cr.bottomLeftTurnTile;
            Body newBottomLeftTurnColStem3 = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBottomLeftTurnColStem3.setUserData("Wall");
            Column bltColStem3 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,13);
            bltColStem3.createColumnTop(false);
            break;

        case "fcolIo":
            currentCell = init.cr.middleFloorTile;
            Column colIo = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16,1);
            colIo.createColumnTop(false);
            Column colIoBase = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,7);
            colIoBase.createColumnBase();
            Column colIoBaseLower = new Column(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 70);
            colIoBaseLower.createColumnBaseLower(1);
            break;
        case "fcolDo":
            currentCell = init.cr.middleFloorTile;
            Column colDo = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16,11);
            colDo.createColumnTop(false);
            Column colDoBase = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,7);
            colDoBase.createColumnBase();
            Column colDoBaseLower = new Column(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 70);
            colDoBaseLower.createColumnBaseLower(1);
            break;
        case "fcolTu":
            currentCell = init.cr.middleFloorTile;
            Column colTu = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16,10);
            colTu.createColumnTop(false);
            Column colTuBase = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,7);
            colTuBase.createColumnBase();
            Column colTuBaseLower = new Column(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 70);
            colTuBaseLower.createColumnBaseLower(1);
            break;

        case "fcol1":
            currentCell = init.cr.middleFloorTile;
            Column col1 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1);
            col1.createColumnTop(false);
            break;
        case "fcol2":
            currentCell = init.cr.middleFloorTile;
            Column col2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,2);
            col2.createColumnTop(false);
            break;
        case "fcol3":
            currentCell = init.cr.middleFloorTile;
            Column col3 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,3);
            col3.createColumnTop(false);
            break;
        case "fcol4":
            currentCell = init.cr.middleFloorTile;
            Column col4 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,4);
            col4.createColumnStem(false);
            break;
        case "fcol5":
            currentCell = init.cr.middleFloorTile;
            Column col5 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,5);
            col5.createColumnStem(false);
            break;
        case "fcol6":
            currentCell = init.cr.middleFloorTile;
            Column col6 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,6);
            col6.createColumnStem(false);
            break;
        case "fcol7":
            currentCell = init.cr.middleFloorTile;
            Column col7 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,7);
            col7.createColumnBase();
            Column colLower = new Column(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 70);
            colLower.createColumnBaseLower(1);
            break;
        case "fcolf":
            currentCell = init.cr.middleFloorTile;
            Column col72 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,18);
            col72.createColumnBase();
            Column colLower2 = new Column(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 71);
            colLower2.createColumnBaseLower(2);
            break;
        case "fcol8":
            currentCell = init.cr.middleFloorTile;
            Column col8 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,8);
            col8.createColumnBase();
            break;
        case "fcol9":
            currentCell = init.cr.middleFloorTile;
            Column col9 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,9);
            col9.createColumnBase();
            break;
        case "fcol10":
            currentCell = init.cr.middleFloorTile;
            Column col10 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,10);
            col10.createColumnTop(false);
            break;
        case "fcol10fire":
            currentCell = init.cr.middleFloorTile;
            Column fireCol10 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,10);
            fireCol10.createColumnTop(false);
            Fire firecol10 = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 3, false, 0f, 1, false, 0);
            firecol10.createFire(new Color(0.25f,0.20f,0,0.75f),60, null);
            fires.add(firecol10);
            lights.add(firecol10);
            break;
        case "fcol11":
            currentCell = init.cr.middleFloorTile;
            Column col11 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,11);
            col11.createColumnTop(false);
            break;
        case "fcol12":
            currentCell = init.cr.middleFloorTile;
            Column col12 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,12);
            col12.createColumnStem(false);
            break;
        case "fcol13":
            currentCell = init.cr.middleFloorTile;
            Column col13 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,13);
            col13.createColumnStem(false);
            break;
        case "fcol14":
            currentCell = init.cr.middleFloorTile;
            Column col14 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,18);
            col14.createColumnBase();
            break;
        case "fped1":
            currentCell = init.cr.middleFloorTile;
            Column ped1 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1.createPedestal();
            break;
        case "fped2":
            currentCell = init.cr.middleFloorTile;
            Column ped2 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,15);
            ped2.createPedestal();
            break;
        case "fped3":
            currentCell = init.cr.middleFloorTile;
            Column ped3 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,16);
            ped3.createPedestal();
            break;
        case "fped4":
            currentCell = init.cr.middleFloorTile;
            Column ped4 = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,17);
            ped4.createPedestal();
            break;
        case "fped1fire":
            currentCell = init.cr.middleFloorTile;
            Column ped1fire = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1fire.createPedestal();
            Fire fireped1 = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6, true, 0f, 1, false, 0);
            fireped1.createFire(new Color(0.25f,0.20f,0,0.75f), 60, null);
            fires.add(fireped1);
            lights.add(fireped1);
            break;
        case "fped1fireoff":
            currentCell = init.cr.middleFloorTile;
            Column ped1fireoff = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1fireoff.createPedestal();
            Fire fireped1off = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6, true, 0f, 1, false, 0);
            fireped1off.createFire(new Color(0.25f,0.20f,0,0.75f), 60, null);
            fires.add(fireped1off);
            lights.add(fireped1off);
            fireped1off.extinguish = false;
            fireped1off.smoking = true;
            fireped1off.active = false;
            break;
        case "fped1fireB":
            currentCell = init.cr.middleFloorTile;
            Column ped1fireb = new Column(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1fireb.createPedestal();
            Fire fireped1b = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6, true, 0f, 2, false, 0);
            Color colorb = new Color(0f,0,1f,0.7f);
            fireped1b.createFire(colorb, 10, null);
            fires.add(fireped1b);
            lights.add(fireped1b);
            init.roomList.get(roomIndex).spawners.add(fireped1b);
            break;
        default: {
            if (levelTextures.get(i).matches("roof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.topLeftWallTile;
                Body newTopLeftWall2 = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newTopLeftWall2.setUserData("Wall");
                Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                roofs.add(roof);
                init.roomList.get(roomIndex).roofs.add(roof);
            }
            else if (levelTextures.get(i).matches("TFroof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 6);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.topFenceTile;
                Body newTopLeftFenceRoof = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newTopLeftFenceRoof.setUserData("Wall");
                Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                roofs.add(roof);
                init.roomList.get(roomIndex).roofs.add(roof);
            }
            else if (levelTextures.get(i).matches("Froof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.topLeftFenceTile;
                Body newTopLeftFenceRoof = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newTopLeftFenceRoof.setUserData("Wall");
                Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                roofs.add(roof);
                init.roomList.get(roomIndex).roofs.add(roof);
            }
            else if (levelTextures.get(i).matches("froof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.middleFloorTile;
                Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                roofs.add(roof);
                init.roomList.get(roomIndex).roofs.add(roof);
            }
            else if (levelTextures.get(i).matches("TWroof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 6);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.topWallTile;
                Body newTopWallRoof = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newTopWallRoof.setUserData("Wall");
                Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                roofs.add(roof);
                init.roomList.get(roomIndex).roofs.add(roof);
            }
            else if (levelTextures.get(i).matches("LWroof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 6);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.leftWallTile;
                Body newTopWallRoof = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newTopWallRoof.setUserData("Wall");
                Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                roofs.add(roof);
                init.roomList.get(roomIndex).roofs.add(roof);
            }
            else if (levelTextures.get(i).matches("RWroof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 6);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.rightWallTile;
                Body newTopWallRoof = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newTopWallRoof.setUserData("Wall");
                Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                roofs.add(roof);
                init.roomList.get(roomIndex).roofs.add(roof);
            }
            else if (levelTextures.get(i).matches("BWroof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 6);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.bottomWallTile;
                Body newTopWallRoof = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newTopWallRoof.setUserData("Wall");
                Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                roofs.add(roof);
                init.roomList.get(roomIndex).roofs.add(roof);
            }
        }
    }

    layer.setCell((roomX + i) + 16, levelY, currentCell);
    init.roomList.get(roomIndex).roomLayer = layer;

    if (startingRoom) {
        //set player starting coordinates according to the position of the first generated room
        String doorUpperLeft = (init.roomList.get(0).doorLocations.get("TopLeft"));
        String[] doorUpperLeftXY = doorUpperLeft.split(",");
        String doorUpperLeftX = doorUpperLeftXY[0].toString();

        double doorUpperLeftXAsDouble = Float.parseFloat(doorUpperLeftX) * 1.65;
        String test = String.valueOf(doorUpperLeftXAsDouble);
        float doorUpperLeftXAsFloat = Float.parseFloat(test);
        PLAYER_X = (doorUpperLeftXAsFloat * 10) + 16;

        String doorUpperLeftY = doorUpperLeftXY[1].toString();
        float doorUpperLeftYAsFloat = Float.parseFloat(doorUpperLeftY);
        PLAYER_Y = doorUpperLeftYAsFloat * 16 - (1 * 16);

        startingRoom = false;
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
