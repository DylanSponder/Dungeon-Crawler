package com.mygdx.game.level;

import box2dLight.ConeLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.StandaloneFileSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.*;
import com.mygdx.game.Random;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.behaviours.fsm.*;
import com.mygdx.game.entity.behaviours.fsm.drops.Skull;
import com.mygdx.game.level.objects.*;
import com.mygdx.game.level.objects.Tutorial;

import java.io.IOException;
import java.util.*;

import static com.mygdx.game.DungeonCrawler.*;
import static com.mygdx.game.DungeonCrawler.world;

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
    private int floor2Chance;

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
        floor2Chance = 8;

        int min = 10;
        int max = 10;
        numRooms = (int) (Math.random() * (max - min + 1)) + min;

        possibleRoomNumbers = new ArrayList<>();

        int roomIDMax = 18;

        for (int i = 0; i < roomIDMax; i++) {
            //create an arraylist of all possible room IDs
            possibleRoomNumbers.add(i);
        }


        path = new ArrayList() {
        };

        path.add(2);
        path.add(2);
        path.add(2);
        path.add(1);
        path.add(1);
        path.add(2);
        path.add(2);
        path.add(2);
        path.add(1);
        path.add(1);
        path.add(2);
        path.add(2);

        boolean temp;
        temp = attemptLevelGen(1);

        return list;
    }

    public boolean attemptLevelGen(int level) {

        /* old code for picking a random new direction - this was fine temporarily but was holding the game back
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
         */

        for (int i = 0; i < numRooms; i++) {
            Room newRoom = new Room();
            init.roomList.add(newRoom);
            //newRoom.directionTaken = init.roomList.get(i).directionTaken;
            roomsIndex++;
            newRoom.index = i;
            //room number randomizer
            if (i == 0){
                //newRoom.roomNum = 0;
            }
            else {//shop placements

                    //pick a random room ID then take it out of the arraylist - no room will appear twice
                    int random = Random.randomInt(possibleRoomNumbers.size()-1, 1);

                    int IDchosen = random;
                    random = possibleRoomNumbers.get(random) + 1;
                    possibleRoomNumbers.remove(IDchosen);

                    while (random == 5 || random == 13) {
                        //shop spawns are pre-determined so are reassigned before being generated randomly
                        random = Random.randomInt(18, 1);//12, 1
                    }
                    //assign the room its random index
                    int temp = numRooms + 1;
                     if (roomsIndex == numRooms - 1) {
                        newRoom.roomNum = 13;
                    } else {
                        newRoom.roomNum = 4;//random
                        System.out.println("ROOM NUMBER: " + random);
                    }
                }
                if (roomsIndex == 6){
                    newRoom.roomNum = 5;
                }

                //determines which pre-gen room is placed next in sequence
                //rooms are numbered, room1 etc

                //shops are always unlocked
                if (newRoom.roomNum == 5) {
                    newRoom.isShop = true;
                    newRoom.unlockAllDoors(world, newRoom,false);
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

    int corridorLength = 4;

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
        System.out.println("ROOM BEING CREATED");

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

                    //yOffset = doorTopLeftYAsInt - doorBottomLeftYAsInt + 4;
                   // System.out.println("YOFFSET----> "+ yOffset);
                    yOffset = 0;
                    xOffset = doorResult;

                   // System.out.println("XOFFSET---->: " + xOffset);

                    init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                    init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                    if ((path.get(roomIndex+1) == 2)) {
                        //locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    }
                    else if ((path.get(roomIndex+1) == 4)) {
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1 + xOffset, init.roomList.get(roomIndex).y1);
                    }
                    else if ((path.get(roomIndex-1) == 1)) {
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    } else {
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

                    //if (path.get(roomIndex+1) == 2 || path.get(roomIndex+1) == 4) {
                       // System.out.println("YOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                        //locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);

                        //testRoomX = testRoomX + xOffset;
                    //}
                    //else {
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    //}
                }
                else {
                    init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                    init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                    locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                }

            }
            else if (doorDirection==2) {
                System.out.println("----DIRECTION 2----");

                String doorUpperRight = doorMapPrevious.get("UpperRight");
                String[] doorUpperRightXY = doorUpperRight.split(",");
                //String doorUpperRightX = doorUpperRightXY[0].toString();
                String doorUpperRightY = doorUpperRightXY[1].toString();
                int doorUpperRightYAsInt = Integer.parseInt(doorUpperRightY);

                String doorUpperRightX = doorUpperRightXY[0].toString();
                int doorUpperRightXAsInt = Integer.parseInt(doorUpperRightX);

                String doorUpperLeft = doorMap.get("UpperLeft");
                String[] doorUpperLeftXY = doorUpperLeft.split(",");
                //String doorUpperLeftX = doorUpperLeftXY[0].toString();
                String doorUpperLeftY = doorUpperLeftXY[1].toString();
                int doorUpperLeftYAsInt = Integer.parseInt(doorUpperLeftY);

                String doorUpperLeftX = doorUpperLeftXY[0].toString();
                int doorUpperLeftXAsInt = Integer.parseInt(doorUpperLeftX);

                if (doorUpperLeftYAsInt > doorUpperRightYAsInt){
                    System.out.println("NEGATIVE A1");

                   // System.out.println("TEST1 +" + init.roomList.get(roomIndex).index);
                    doorResult = doorUpperLeftYAsInt - doorUpperRightYAsInt;
                    System.out.println("DOORRESULT Y " + doorResult);
                    doorResult = doorResult;
                    yOffset = doorResult;

                    init.roomList.get(roomIndex).y1 = init.roomList.get(roomIndex).y1 + yOffset;
                    init.roomList.get(roomIndex).y2 = init.roomList.get(roomIndex).y2 + yOffset;

                    if (doorUpperRightXAsInt > doorUpperLeftXAsInt) {
                        System.out.println("A4");
                        //System.out.println("DOORRESULTY" + doorResult);
                        doorResult = doorUpperRightXAsInt - doorUpperLeftXAsInt;
                        System.out.println("DOORRESULT X " + doorResult);
                        xOffset = doorResult;
                        init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                        init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    }
                    else if (doorUpperLeftXAsInt > doorUpperRightXAsInt) {
                        init.roomList.get(roomIndex).y1 = init.roomList.get(roomIndex).y1 - yOffset;
                        init.roomList.get(roomIndex).y2 = init.roomList.get(roomIndex).y2 - yOffset;

                        System.out.println("A10");
                        System.out.println("DOORRESULT X " + doorResult);
                        doorResult = doorUpperLeftXAsInt - doorUpperRightXAsInt;
                        xOffset = doorResult * -1;
                        init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                        init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    }
                    else if (!(doorUpperLeftXAsInt > doorUpperRightXAsInt + 4)) {
                        System.out.println("A6");
                        System.out.println("DOORRESULT X " + doorResult);
                        doorResult = doorUpperLeftXAsInt - doorUpperRightXAsInt - 1;
                        xOffset = doorResult;
                        init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                        init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    }
                    else {
                        System.out.println("A8");
                        System.out.println("DOORRESULT X " + doorResult);
                        //doorResult = doorUpperLeftXAsInt - doorUpperRightXAsInt;
                        //xOffset = doorResult;
                        //init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                        //init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;
                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);

                    }

                    //locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);

                }
                else if (doorUpperRightYAsInt > doorUpperLeftYAsInt) {
                    //System.out.println("TEST2 +" + (doorUpperRightYAsInt));
                   // System.out.println("TEST2 +" + (doorUpperLeftYAsInt));
                    doorResult = doorUpperRightYAsInt - doorUpperLeftYAsInt;
                    yOffset = doorResult;
                    //doorResult = doorResult * -1;

                    init.roomList.get(roomIndex).y1 = init.roomList.get(roomIndex).y1 + yOffset;
                    init.roomList.get(roomIndex).y2 = init.roomList.get(roomIndex).y2 + yOffset;
                    //doorResult = doorResult * -1;
                    //System.out.println(doorResult);
                    /*
                                        if (doorUpperLeftXAsInt > doorUpperRightXAsInt) {
                        System.out.println("A1");
                        System.out.println("DOORRESULTY" + doorResult);
                        doorResult = doorUpperLeftXAsInt - doorUpperRightXAsInt;
                        System.out.println("DOORRESULTX" + doorResult);
                        xOffset = doorResult;
                        init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                        init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    } else
                     */
                    if (doorUpperRightXAsInt > doorUpperLeftXAsInt) {
                        System.out.println("A2");
                        System.out.println("DOORRESULT Y " + doorResult);
                        doorResult = doorUpperRightXAsInt - doorUpperLeftXAsInt;
                        doorResult = doorResult * -1;
                        System.out.println("DOORRESULT X " + doorResult);
                        xOffset = doorResult;
                        init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + xOffset;
                        init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + xOffset;

                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    } else if (doorUpperRightXAsInt == doorUpperLeftXAsInt) {
                        System.out.println("A5");
                        System.out.println("DOORRESULT Y " + doorResult);

                        //init.roomList.get(roomIndex).x1 = init.roomList.get(roomIndex).x1 + 4;
                        //init.roomList.get(roomIndex).x2 = init.roomList.get(roomIndex).x2 + 4;

                        locateDoors(roomIndex, init.roomList.get(roomIndex).x1, init.roomList.get(roomIndex).y1);
                    }
                } else if (doorUpperRightXAsInt == doorUpperLeftXAsInt) {
                    System.out.println("NUHHHH");

                } else {
                    System.out.println("HUH? " + "UP RIGHT X: " + doorUpperRightXAsInt + ", UP LEFT X: " + doorUpperLeftXAsInt);
                    System.out.println("DOORRESULT Y " + doorResult);
                }
                testLevelY = testLevelY + yOffset;


            }
            else if (doorDirection==3) {
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
            else if (doorDirection==4) {
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
        System.out.println("ROOM FINISHED BEING CREATED");
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

    //create the background ocean
    if (!r.oceanCreated) {
        //T0DO Fix - oceanWater should generate EVERYWHERE - with just waves generating on top (maybe lower opacity get the same look as currently?)
            for (int rowNumOc = 0; rowNumOc < currentRoomSize + 4; rowNumOc++) {
                for (int iOc = -4; iOc < layerSize; iOc++) {
                    Water water = new Water(world, ((roomX + iOc) * 16) + 16 * 16 + 32,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + (16 * rowNumOc) - (16 * currentRoomSize) - 16, 1);
                    ocean.add(water);
                    if (rowNumOc % 2 == 0) {
                        Wave wave = new Wave(world, ((roomX + iOc) * 16) + 16 * 16 + 32,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + (16 * rowNumOc) - (16 * currentRoomSize) - 16 + 3, 0);
                        waves.add(wave);
                    } else {
                        Wave wave = new Wave(world, ((roomX + iOc) * 16) + 16 * 16 + 32,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + (16 * rowNumOc) - (16 * currentRoomSize) - 16 + 3, 415);//4.95 4.885  375 4.92
                        waves.add(wave);
                    }
                }
            }
            r.oceanCreated = true;
    }
/*
    if (i % 4 == 0) {
        Wave wave = new Wave(world, ((roomX + i) * 16) + 16 * 16 - (longestRow * 16),levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + rowNum);
        waves.add(wave);

        Wave wave2 = new Wave(world, ((roomX + i) * 16) + 16 * 16 - (longestRow * 16) + 16 ,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + rowNum);
        waves.add(wave2);

        Wave wave3 = new Wave(world, ((roomX + i) * 16) + 16 * 16 - (longestRow * 16) + 32,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + rowNum);
        waves.add(wave3);

        Wave wave4 = new Wave(world, ((roomX + i) * 16) + 16 * 16 - (longestRow * 16) + 48,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + rowNum);
        waves.add(wave4);
    }

 */

    TiledMapTileLayer.Cell currentCell = new TiledMapTileLayer.Cell();
    switch (levelTextures.get(i)) {
        case "middleFloorTile":
            int randIntFloor = Random.randomInt(floor2Chance,1);
            if (randIntFloor != floor2Chance) {
                currentCell = init.cr.middleFloorTile;
            } else {
                currentCell = init.cr.middleFloorTile2;
            }
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
        case "topLeftStairTile":
            currentCell = init.cr.topLeftStairTile;
            break;
        case "topRightStairTile":
            currentCell = init.cr.topRightStairTile;
            break;
        case "topStairTile":
            currentCell = init.cr.topStairTile;
            break;
        case "bottomStairTile":
            currentCell = init.cr.bottomStairTile;
            break;
        case "leftStairTile":
            currentCell = init.cr.leftStairTile;
            break;
        case "rightStairTile":
            currentCell = init.cr.rightStairTile;
            break;
        case "bottomRightStairTile":
            currentCell = init.cr.bottomRightStairTile;
            break;
        case "bottomLeftStairTile":
            currentCell = init.cr.bottomLeftStairTile;
            break;
        case "leftCornerStairTile":
            currentCell = init.cr.leftCornerStairTile;
            break;
        case "rightCornerStairTile":
            currentCell = init.cr.rightCornerStairTile;
            break;
        case "raisedFloorTile":
            currentCell = init.cr.middleFloorTile2;
            RaisedFloor raf = new RaisedFloor(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            raf.createRaisedFloor();
            raisedFloors.add(raf);
            break;

        case "pit":
            currentCell = init.cr.pitTile;
            Body pit = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            pit.setUserData("Pit");
            break;
        case "pitRubble":
            currentCell = init.cr.pitTile;
           // Body rubbleBody = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
           // rubbleBody.setUserData("PitRubble");
            int randRubble = Random.randomInt(3,1);
            int randRub = Random.randomInt(3,1);
            if (randRub == 3) {
                Rubble pitRubble = new Rubble(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, randRubble);
                pitRubble.createRubble();
                rubble.add(pitRubble);
            }
            break;
        case "pitRubble1":
            currentCell = init.cr.pitTile;
            Rubble pitRubble1 = new Rubble(world, ((roomX + i) * 16) + 16 * 16 + 0.5f, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 1);
            pitRubble1.createRubble();
            rubble.add(pitRubble1);
            break;
        case "pitRubble2":
            currentCell = init.cr.pitTile;
            Rubble pitRubble2 = new Rubble(world, ((roomX + i) * 16) + 16 * 16 + 0.5f, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 2);
            pitRubble2.createRubble();
            rubble.add(pitRubble2);
            break;
        case "pitRubble3":
            currentCell = init.cr.pitTile;
            Rubble pitRubble3 = new Rubble(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 3);
            pitRubble3.createRubble();
            rubble.add(pitRubble3);
            break;
        case "pitRubble3Random":
            currentCell = init.cr.pitTile;
            int randRub3R = Random.randomInt(5,1);
            if (randRub3R == 5) {
                Rubble pitRubble = new Rubble(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 3);
                pitRubble.createRubble();
                rubble.add(pitRubble);
            }
            Body pit3r = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            pit3r.setUserData("Pit");
            break;
        case "pitFloor1":
            currentCell = init.cr.pitFloorTile;
            Body pitF1 = init.bf.createHalfWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            pitF1.setUserData("Pit");
            break;
        case "pitFloor2":
            currentCell = init.cr.pitFloor2Tile;
            Body pitF2 = init.bf.createHalfWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            pitF2.setUserData("Pit");
            break;
        case "pitStairs":
            currentCell = init.cr.pitStairsTile;
            Body pitSt = init.bf.createHalfWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            pitSt.setUserData("Pit");
            break;
        case "pitDown":
            currentCell = init.cr.pitBottomTile;
            Body pitD = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            pitD.setUserData("Pit");
            break;
        case "pitLeft":
            currentCell = init.cr.pitLeftTile;
            Body pitL = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            pitL.setUserData("Pit");
            break;
        case "pitRight":
            currentCell = init.cr.pitRightTile;
            Body pitR = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            pitR.setUserData("Pit");
            break;

        case "waterFloorTile":
            currentCell = init.cr.middleFloorTile;
            Water waf = new Water(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 2);
            water.add(waf);
            waf.createWaterBody();
            break;
        case "innerWallUpTrap":
            currentCell = init.cr.innerWallUpTile;
            Body innerWallUpTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            innerWallUpTrap.setUserData("Wall");
            Trap innerUpTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 1);
            innerUpTrap.createTrap();
            traps.add(innerUpTrap);
            break;
        case "innerWallDownTrap":
            currentCell = init.cr.innerWallDownTile;
            Body innerWallDownTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            innerWallDownTrap.setUserData("Wall");
            Trap innerDownTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16 + 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 3);
            innerDownTrap.createTrap();
            traps.add(innerDownTrap);
            break;
        case "innerWallLeftTrap":
            currentCell = init.cr.innerWallLeftTile;
            Body innerWallLeftTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            innerWallLeftTrap.setUserData("Wall");
            Trap innerLeftTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 4);
            innerLeftTrap.createTrap();
            traps.add(innerLeftTrap);
            break;
        case "innerWallRightTrap":
            currentCell = init.cr.innerWallRightTile;
            Body innerWallRightTrap = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            innerWallRightTrap.setUserData("Wall");
            Trap innerRightTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30,1, 2);
            innerRightTrap.createTrap();
            traps.add(innerRightTrap);
            break;


        case "innerWallTLCorner":
            currentCell = init.cr.innerWallTLCornerTile;
            Body newTLCorner = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 0.1f);
            newTLCorner.setUserData("Wall");
            break;
        case "innerWallTRCorner":
            currentCell = init.cr.innerWallTRCornerTile;
            Body newTRCorner = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 0.1f);
            newTRCorner.setUserData("Wall");
            break;
        case "innerWallBLCorner":
            currentCell = init.cr.innerWallBLCornerTile;
            Body newBLCorner = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBLCorner.setUserData("Wall");
            break;
        case "innerWallBRCorner":
            currentCell = init.cr.innerWallBRCornerTile;
            Body newBRCorner = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBRCorner.setUserData("Wall");
            break;

        case "innerWallTLTurn":
            currentCell = init.cr.innerWallTLTurnTile;
            Body newTLTurn = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 0.1f);
            newTLTurn.setUserData("Wall");
            break;
        case "innerWallTRTurn":
            currentCell = init.cr.innerWallTRTurnTile;
            Body newTRTurn = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 0.1f);
            newTRTurn.setUserData("Wall");
            break;
        case "innerWallBLTurn":
            currentCell = init.cr.innerWallBLTurnTile;
            Body newBLTurn = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
            newBLTurn.setUserData("Wall");
            break;
        case "innerWallBRTurn":
            currentCell = init.cr.innerWallBRTurnTile;
            Body newBRTurn = init.bf.createWallTurn(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
            newBRTurn.setUserData("Wall");
            break;


        case "innerWallUp":
            currentCell = init.cr.innerWallUpTile;
            Body innerWallUp = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            innerWallUp.setUserData("Wall");
            break;
        case "innerWallDown":
            currentCell = init.cr.innerWallDownTile;
            Body innerWallDown = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            innerWallDown.setUserData("Wall");
            break;
        case "innerWallLeft":
            currentCell = init.cr.innerWallLeftTile;
            Body innerWallLeft = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            innerWallLeft.setUserData("Wall");
            break;
        case "innerWallRight":
            currentCell = init.cr.innerWallRightTile;
            Body innerWallRight = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            innerWallRight.setUserData("Wall");
            break;
        case "block":
            currentCell = init.cr.blockTile;
            Body block = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            block.setUserData("Wall");
            break;
        case "blockWallUp":
            currentCell = init.cr.blockWallUpTile;
            Body blockWallUp = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            blockWallUp.setUserData("Wall");
            break;
        case "blockTrapDown":
            currentCell = init.cr.blockWallUpTile;
            Body blockDownTrapWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            blockDownTrapWall.setUserData("Wall");
            Trap blockDownTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 1);
            blockDownTrap.createTrap();
            traps.add(blockDownTrap);
            break;
        case "blockTrapLeft":
            currentCell = init.cr.blockTile;
            Body blockLeftTrapWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            blockLeftTrapWall.setUserData("Wall");
            Trap blockLeftTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 4);
            blockLeftTrap.createTrap();
            traps.add(blockLeftTrap);
            break;
        case "blockTrapRight":
            currentCell = init.cr.blockTile;
            Body blockRightTrapWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            blockRightTrapWall.setUserData("Wall");
            Trap blockRightTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30,1, 2);
            blockRightTrap.createTrap();
            traps.add(blockRightTrap);
            break;
        case "blockTrapUp":
            currentCell = init.cr.blockTile;
            Body blockUpTrapWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
            blockUpTrapWall.setUserData("Wall");
            Trap blockUpTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16 + 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,1, 3);
            blockUpTrap.createTrap();
            traps.add(blockUpTrap);
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
            fCan.createFire(new Color(0.30f,0.12f,0,0.7f),30, null);
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
            fCans.createFire(new Color(0.30f,0.12f,0,0.5f),20, null);
            Fire fCans2 = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 8.5f,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 6.5f, false,0f, 3, false, 0);
            fires.add(fCans2);
            lights.add(fCans2);
            fCans2.createFire(new Color(0.30f,0.12f,0,0.5f),30, null);
            candles.add(newCandles);
            break;
        case "2cand":
            currentCell = init.cr.middleFloor2Tile;
            Candle newCandle2 = new Candle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 1);
            Body candBody2 = newCandle2.createCandle();
            Fire fCan2 = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 5.5f,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 3.5f, false,0f, 3, false, 0);
            fires.add(fCan2);
            fCan2.createFire(new Color(0.30f,0.12f,0,0.7f),30, null);
            candles.add(newCandle2);
            lights.add(fCan2);
            break;
        case "2cands":
            currentCell = init.cr.middleFloor2Tile;
            Candle newCandles2 = new Candle(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 2);
            Body candsBody2 = newCandles2.createCandle();
            Fire fCans22 = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 4.5f,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 2.5f, false,0f, 3, false, 0);
            fires.add(fCans22);
            lights.add(fCans22);
            fCans22.createFire(new Color(0.30f,0.12f,0,0.5f),20, null);
            Fire fCans222 = new Fire(world,rayHandler,(((roomX + i) * 16) + 16 * 16) + 6 - 8.5f,(levelY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 6.5f, false,0f, 3, false, 0);
            fires.add(fCans222);
            lights.add(fCans222);
            fCans222.createFire(new Color(0.30f,0.12f,0,0.5f),30, null);
            candles.add(newCandles2);
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
            Color colorFL = new Color(0.30f,0.12f,0,0.7f);
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
            fLoff.createFire(new Color(0.30f,0.12f,0,0.7f),60, torchLightLOff);
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

            Color colorFR = new Color(0.30f,0.12f,0,0.7f);
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
            fRoff.createFire(new Color(0.30f,0.12f,0,0.7f),60, torchLightROff);
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
            Color colorFU = new Color(0.30f,0.12f,0,0.7f);
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
            fUoff.createFire(new Color(0.30f,0.12f,0,0.7f),60,torchLightUOff);
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
            Color colorFD = new Color(0.30f,0.12f,0,0.7f);
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
            fDoff.createFire(new Color(0.30f,0.12f,0,0.7f),60, torchLightDOff);
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
            currentCell = init.cr.middleFloor2Tile;
            int randOb2 = Random.randomInt(9,1);
            Pot p2 = new Pot(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, randOb2);
            pots.add(p2);
            break;
        case "coin":
            currentCell = init.cr.middleFloorTile;
            Coin coin = new Coin(world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            coin.createCoin(coinArrayMap, rayHandler);
            coins.add(coin);
            coinArrayMap.put(coin.coinBody, coin);
            break;
        case "skull":
            currentCell = init.cr.middleFloorTile;
            Skull skull = new Skull(world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            skulls.add(skull);
            break;
        case "cobweb":
            currentCell = init.cr.middleFloorTile;
            Cobweb c1 = new Cobweb(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, true);
            cobwebs.add(c1);
            break;

        //entities
        case "enemySkull":
            int randIntFloorSkull = Random.randomInt(floor2Chance,1);
            if (randIntFloorSkull != floor2Chance) {
                currentCell = init.cr.middleFloorTile;
            } else {
                currentCell = init.cr.middleFloorTile2;
            }
            EnemySkull enemy = new EnemySkull(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemySkulls.add(enemy);
            enemy.createEnemy(1, 22);
            enemies.add(enemy);
            enemy.room = roomIndex;
            DungeonCrawler.enemySkulls.add(enemy);
            break;
        case "enemySpider":
            int randIntFloorSpider = Random.randomInt(floor2Chance,1);
            if (randIntFloorSpider != floor2Chance) {
                currentCell = init.cr.middleFloorTile;
            } else {
                currentCell = init.cr.middleFloorTile2;
            }
            EnemySpider enemy2 = new EnemySpider(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemySpiders.add(enemy2);
            enemy2.createEnemy(2, 150);
            enemies.add(enemy2);
            enemy2.room = roomIndex;
            DungeonCrawler.enemySpiders.add(enemy2);
            break;
        case "enemyGhost":
            int randIntFloorGhost = Random.randomInt(floor2Chance,1);
            if (randIntFloorGhost != floor2Chance) {
                currentCell = init.cr.middleFloorTile;
            } else {
                currentCell = init.cr.middleFloorTile2;
            }
            EnemyGhost enemy3 = new EnemyGhost(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemyGhosts.add(enemy3);
            enemy3.createEnemy(3, 40);
            enemies.add(enemy3);
            enemy3.room = roomIndex;
            DungeonCrawler.enemyGhosts.add(enemy3);
            break;
        case "enemyCyclops":
            int randIntFloorCyclops = Random.randomInt(floor2Chance,1);
            if (randIntFloorCyclops != floor2Chance) {
                currentCell = init.cr.middleFloorTile;
            } else {
                currentCell = init.cr.middleFloorTile2;
            }
            EnemyCyclops enemy4 = new EnemyCyclops(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemyEyes.add(enemy4);
            enemy4.createEnemy(4, 10);
            enemies.add(enemy4);
            enemy4.room = roomIndex;
            DungeonCrawler.enemyEyes.add(enemy4);
            break;
        case "enemySkull2":
            currentCell = init.cr.middleFloor2Tile;
            EnemySkull enemy12 = new EnemySkull(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemySkulls.add(enemy12);
            enemy12.createEnemy(1, 22);
            enemies.add(enemy12);
            enemy12.room = roomIndex;
            DungeonCrawler.enemySkulls.add(enemy12);
            break;
        case "enemySpider2":
            currentCell = init.cr.middleFloor2Tile;
            EnemySpider enemy22 = new EnemySpider(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemySpiders.add(enemy22);
            enemy22.createEnemy(2, 175);
            enemies.add(enemy22);
            enemy22.room = roomIndex;
            DungeonCrawler.enemySpiders.add(enemy22);
            break;
        case "enemyGhost2":
            currentCell = init.cr.middleFloor2Tile;
            EnemyGhost enemy32 = new EnemyGhost(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemyGhosts.add(enemy32);
            enemy32.createEnemy(3, 40);
            enemies.add(enemy32);
            enemy32.room = roomIndex;
            DungeonCrawler.enemyGhosts.add(enemy32);
            break;
        case "enemyCyclops2":
            currentCell = init.cr.middleFloor2Tile;
            EnemyCyclops enemy42 = new EnemyCyclops(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 8);
            init.roomList.get(roomIndex).enemyCounter++;
            init.roomList.get(roomIndex).enemyEyes.add(enemy42);
            enemy42.createEnemy(4, 10);
            enemies.add(enemy42);
            enemy42.room = roomIndex;
            DungeonCrawler.enemyEyes.add(enemy42);
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
            susMessages.add(shopMessage);
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

        case "fped1":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped1 = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1.createPedestal();
            break;
        case "fped2":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped2 = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,15);
            ped2.createPedestal();
            break;
        case "fped3":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped3 = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,16);
            ped3.createPedestal();
            break;
        case "fped3fire":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped3fire = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,16);
            ped3fire.createPedestal();
            Fire fireped3 = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6 + 1, true, 0f, 1, false, 0);
            fireped3.createFire(new Color(0.30f,0.12f,0,0.75f), 60, null);
            fires.add(fireped3);
            lights.add(fireped3);
            break;
        case "fped4":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped4 = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,17);
            ped4.createPedestal();
            break;
        case "fped1heal":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped1heal = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1heal.createPedestal();
            Potion potion = new Potion(world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 20, 1);
            potion.createPotion(potionArrayMap, rayHandler);
            potions.add(potion);
            potionArrayMap.put(potion.potionBody, potion);
            break;
        case "fped1statue1":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped1statue = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1statue.createPedestal();
            Statue ped1Stat1 = new Statue(world, ((roomX + i) * 16) + 16 * 16 + 3, levelY * 16 + Gdx.graphics.getHeight() / 30 + 2,1);
            ped1Stat1.createStatueHitbox(((roomX + i) * 16) + 16 * 16 + 4f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16.5f, world);
            ped1Stat1.createStatuePedestalHitbox(((roomX + i) * 16) + 16 * 16 + 4f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 14f, world);
            statues.add(ped1Stat1);
            //Body ped1st1 = init.bf.createStatuePedestalHitbox(world, ((roomX + i) * 16) + 16 * 16 + 4f, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16.5f);
            break;
        case "fped1statue2":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped1statue2 = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1statue2.createPedestal();
            Statue ped1Stat2 = new Statue(world, ((roomX + i) * 16) + 16 * 16 + 1, levelY * 16 + Gdx.graphics.getHeight() / 30 + 1,2);
            ped1Stat2.createStatueHitbox(((roomX + i) * 16) + 16 * 16 + 3f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16.5f, world);
            ped1Stat2.createStatuePedestalHitbox(((roomX + i) * 16) + 16 * 16 + 3f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 14f, world);
            statues.add(ped1Stat2);

            break;
        case "fped2statue1":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped2statue1 = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,15);
            ped2statue1.createPedestal();
            Statue ped2Stat1 = new Statue(world, ((roomX + i) * 16) + 16 * 16 + 3, levelY * 16 + Gdx.graphics.getHeight() / 30 + 2,1);
            ped2Stat1.createStatueHitbox(((roomX + i) * 16) + 16 * 16 + 4f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 14.5f, world);
            ped2Stat1.createStatuePedestalHitbox(((roomX + i) * 16) + 16 * 16 + 4f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 12f, world);
            statues.add(ped2Stat1);

            break;
        case "fped2statue2":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped2statue2 = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,15);
            ped2statue2.createPedestal();
            Statue ped2Stat2 = new Statue(world, ((roomX + i) * 16) + 16 * 16 + 1, levelY * 16 + Gdx.graphics.getHeight() / 30 + 1,2);
            ped2Stat2.createStatueHitbox(((roomX + i) * 16) + 16 * 16 + 3f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 14.5f, world);
            ped2Stat2.createStatuePedestalHitbox(((roomX + i) * 16) + 16 * 16 + 3f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 12f, world);
            statues.add(ped2Stat2);

            break;
        case "fped2heal":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped2heal = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,15);
            ped2heal.createPedestal();
            Potion potion2 = new Potion(world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 20, 1);
            potion2.createPotion(potionArrayMap, rayHandler);
            potions.add(potion2);
            potionArrayMap.put(potion2.potionBody, potion2);
            break;
        case "fped1fire":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped1fire = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1fire.createPedestal();
            Fire fireped1 = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6 + 2, true, 0f, 1, false, 0);
            fireped1.createFire(new Color(0.30f,0.12f,0,0.75f), 60, null);
            fires.add(fireped1);
            lights.add(fireped1);
            break;
        case "fped1fireoff":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped1fireoff = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1fireoff.createPedestal();
            Fire fireped1off = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6 + 2, true, 0f, 1, false, 0);
            fireped1off.createFire(new Color(0.30f,0.12f,0,0.75f), 60, null);
            fires.add(fireped1off);
            lights.add(fireped1off);
            fireped1off.extinguish = false;
            fireped1off.smoking = true;
            fireped1off.active = false;
            break;
        case "fped1fireB":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped1fireb = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,14);
            ped1fireb.createPedestal();
            Fire fireped1b = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6, true, 0f, 2, false, 0);
            Color colorb = new Color(0f,0,1f,0.7f);
            fireped1b.createFire(colorb, 10, null);
            fires.add(fireped1b);
            lights.add(fireped1b);
            init.roomList.get(roomIndex).spawners.add(fireped1b);
            break;
        case "fped2fire":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped2fire = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,15);
            ped2fire.createPedestal();
            Fire fireped2 = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6 + 2, true, 0f, 1, false, 0);
            fireped2.createFire(new Color(0.30f,0.12f,0,0.75f), 60, null);
            fires.add(fireped2);
            lights.add(fireped2);
            break;
        case "fped2fireoff":
            currentCell = init.cr.middleFloorTile;
            ColumnPiece ped2fireoff = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,15);
            ped2fireoff.createPedestal();
            Fire fireped2off = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 6 + 2, true, 0f, 1, false, 0);
            fireped2off.createFire(new Color(0.30f,0.12f,0,0.75f), 60, null);
            fires.add(fireped2off);
            lights.add(fireped2off);
            fireped2off.extinguish = false;
            fireped2off.smoking = true;
            fireped2off.active = false;
            break;
        default: {
            //coltu, coldo, colio,
            // coltb, coldb, colib,
            // cfltu, cfldo, cflio,
            // cfltb, cfldb, cflib,
            // csltb, csldb, cslib
            //TODO: missing:
            //costu, cosdo, cosio //columns with squared bases
            //costb, cosb, cosib //columns with full squared bases
            //csstb, cssdb, cssib //columns with statues and full squared bases
            //cfstb, cfsdb, cfsib //columns with fire and full squared bases
            //cfstu, cfsdo, cfsio //columns with fire and squared bases
            //outdated - fix


            if (levelTextures.get(i).matches("coltu.+")) {//tuscan

                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,7,70,false,false,0,false,0, false, 0, false, 0);
            }
            if (levelTextures.get(i).matches("coltB.+")) {//tuscan with squared base

                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,40,740,false,false,0,false,0, false, 0,false, 0);
            }
            if (levelTextures.get(i).matches("coftu.+")) {//tuscan with flag

                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,7,70,false,false,0,true,1, false, 0,false, 0);
            }
            if (levelTextures.get(i).matches("cottu.+")) {//tuscan with trap

                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,7,70,false,false,0,false,1, false, 0, true, 3);
            }
            if (levelTextures.get(i).matches("coTtu.+")) {//tuscan with fire trap

                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,7,70,false,false,0,false,1, false, 0, true, 4);
            }
            if (levelTextures.get(i).matches("coldo.+")) {//doric
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,7,70,false,false,0,false,0, false, 0, false, 0);
            }
            if (levelTextures.get(i).matches("coldB.+")) {//doric with squared base

                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,40,740,false,false,0,false,0, false, 0, false, 0);
            }
            if (levelTextures.get(i).matches("cofdo.+")) {//doric with flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,7,70,false,false,0,true,1, false, 0, false, 0);
            }
            if (levelTextures.get(i).matches("cotdo.+")) {//doric with trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,7,70,false,false,0,false,1, false, 0, true, 3);
            }
            if (levelTextures.get(i).matches("coTdo.+")) {//doric with fire trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,7,70,false,false,0,false,1, false, 0, true, 4);
            }
            if (levelTextures.get(i).matches("colio.+")) {//ionic
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,7,70,false,false,0,false,0, false, 0, false, 0);
            }
            if (levelTextures.get(i).matches("coliB.+")) {//ionic with squared base

                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,40,740,false,false,0,false,0, false, 0, false, 0);
            }
            if (levelTextures.get(i).matches("cofio.+")) {//ionic with flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,7,70,false,false,0,true,1, false, 0, false, 0);
            }
            if (levelTextures.get(i).matches("cotio.+")) {//ionic with trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,7,70,false,false,0,false,1, false, 0, true, 3);
            }
            if (levelTextures.get(i).matches("coTio.+")) {//ionic with fire trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,7,70,false,false,0,false,1, false, 0, true, 4);
            }
            if (levelTextures.get(i).matches("coltb.+")) {//tuscan with full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,false,0,false,0, false, 0, false,0);
            }
            if (levelTextures.get(i).matches("coldb.+")) {//doric with full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,false,0,false,0, false, 0, false,0);
            }
            if (levelTextures.get(i).matches("colib.+")) {//ionic with full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,false,0,false,0, false, 0, false,0);

            }

            if (levelTextures.get(i).matches("cfltu.+")) {//tuscan with fire
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,7,70,false,true,0,false,0, false, 0, false,0);
            }
            if (levelTextures.get(i).matches("cfldo.+")) {//doric with fire
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,7,70,false,true,0,false,0, false, 0, false,0);

            }
            if (levelTextures.get(i).matches("cflio.+")) {//ionic with fire
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,7,70,false,true,0,false,0, false, 0, false,0);
            }
            if (levelTextures.get(i).matches("cfltb.+")) {//tuscan with fire and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,true,0,false,0, false, 0, false,0);
            }
            if (levelTextures.get(i).matches("cfldb.+")) {//doric with fire and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,true,0,false,0, false, 0, false,0);
            }
            if (levelTextures.get(i).matches("cflib.+")) {//ionic with fire and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,true,0,false,0, false, 0, false,0);
            }
            if (levelTextures.get(i).matches("csltb.+")) {//tuscan with male statue and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,false,0,false,0, true,1, false,0);
            }
            if (levelTextures.get(i).matches("cSltb.+")) {//tuscan with female statue and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,false,0,false,0, true,2, false,0);
            }
            if (levelTextures.get(i).matches("csldb.+")) {//doric with male statue and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,false,0,false,0, true,1, false,0);
            }
            if (levelTextures.get(i).matches("cSldb.+")) {//doric with female statue and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,false,0,false,0, true,2, false,0);
            }
            if (levelTextures.get(i).matches("cslib.+")) {//ionic with male statue and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,false,0,false,0, true,1, false,0);
            }
            if (levelTextures.get(i).matches("cSlib.+")) {//ionic with female statue and full base
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,false,0,false,0, true,2, false,0);
            }
            if (levelTextures.get(i).matches("coftb.+")) {//tuscan with full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,false,0,true,1, false,0, false,0);
            }
            if (levelTextures.get(i).matches("cottb.+")) {//tuscan with full base and trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,false,0,false,1, false,0, true,3);
            }
            if (levelTextures.get(i).matches("coTtb.+")) {//tuscan with full base and fire trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,false,0,false,1, false,0, true,4);
            }

            if (levelTextures.get(i).matches("cofdb.+")) {//doric with full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,false,0,true,1, false,0, false,0);
            }
            if (levelTextures.get(i).matches("cotdb.+")) {//doric with full base and trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,false,0,false,1, false,0, true,3);
            }
            if (levelTextures.get(i).matches("coTdb.+")) {//doric with full base and fire trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,false,0,false,1, false,0, true,4);
            }
            if (levelTextures.get(i).matches("cofib.+")) {//ionic with full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,false,0,true,1, false,0, false,0);
            }
            if (levelTextures.get(i).matches("cotib.+")) {//ionic with full base and trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,false,0,false,1, false,0, true,3);
            }
            if (levelTextures.get(i).matches("coTib.+")) {//ionic with full base and fire trap
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,false,0,false,1, false,0, true,4);
            }
            if (levelTextures.get(i).matches("cfftb.+")) {//tuscan with fire and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,true,0,true,0, false,0, false,0);
            }
            if (levelTextures.get(i).matches("cfftu.+")) {//tuscan with fire and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,7,70,false,true,0,true,0, false,0, false,0);
            }
            if (levelTextures.get(i).matches("cffdb.+")) {//doric with fire and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,true,0,true,0, false,0, false,0);
            }
            if (levelTextures.get(i).matches("cffdo.+")) {//doric with fire and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,7,70,false,true,0,true,0, false,0, false,0);
            }
            if (levelTextures.get(i).matches("cffib.+")) {//ionic with fire and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,true,0,true,0, false,0, false,0);
            }
            if (levelTextures.get(i).matches("cffio.+")) {//ionic with fire and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,7,70,false,true,0,true,0, false,0, false,0);
            }
            if (levelTextures.get(i).matches("csftb.+")) {//tuscan with male statue and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,false,0,true,1, true,1, false,0);
            }
            if (levelTextures.get(i).matches("csftu.+")) {//tuscan with male statue and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,7,70,false,false,0,true,1, true,1, false,0);
            }
            if (levelTextures.get(i).matches("cSftb.+")) {//tuscan with female statue and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,18,71,true,false,0,true,1, true,2, false,0);
            }
            if (levelTextures.get(i).matches("cSftu.+")) {//tuscan with female statue and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 2,4,7,70,false,false,0,true,1, true,2, false,0);
            }
            if (levelTextures.get(i).matches("csfdb.+")) {//doric with male statue and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,false,0,true,1, true,1, false,0);
            }
            if (levelTextures.get(i).matches("cSfdb.+")) {//doric with female statue and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;
                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 3,4,18,71,true,false,0,true,1, true,2, false,0);
            }
            if (levelTextures.get(i).matches("csfib.+")) {//ionic with male statue and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,false,0,true,1, true,1, false,0);
            }
            if (levelTextures.get(i).matches("csfio.+")) {//ionic with male statue and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,7,70,false,false,0,true,1, true,1, false,0);
            }
            if (levelTextures.get(i).matches("cSfio.+")) {//ionic with female statue and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,7,70,false,false,0,true,1, true,2, false,0);
            }
            if (levelTextures.get(i).matches("cSfib.+")) {//ionic with female statue and full base and flag
                Column coltu = new Column();

                String colStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(colStr);
                sb.delete(0, 5);
                String strRoof = sb.toString();

                String colExt = String.valueOf(strRoof.charAt(0));
                Integer colExt2 = Integer.parseInt(colExt);

                currentCell = init.cr.middleFloorTile;

                generateColumn(colExt2, strRoof, i,world, roomX, levelY, 1,4,18,71,true,false,0,true,1, true,2, false,0);
            }

            //WATER
            else if (levelTextures.get(i).matches("wf1.+")) {

                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 3);
                String strRoof = sb.toString();
                int width = Integer.parseInt(String.valueOf(strRoof.charAt(0)));
                int height = Integer.parseInt(String.valueOf(strRoof.charAt(1)));

                boolean waterCreated = false;

                for (int iW = 0; iW < width; iW++) {
                    for (int iH = 0; iH < height ; iH++) {
                        Water wf1 = new Water(world, ((roomX + i) * 16) + 16 * 16 + (iW * 16), levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 - (iH * 16), 2);
                        water.add(wf1);
                        if (!waterCreated) {
                            waterCreated = true;

                            Body newBody = init.bf.createModularWaterBody(world, ((roomX + i) * 16) + 16 * 16 + (iW * 16), levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 - (iH * 16), width, height);
                            newBody.setUserData("Water");

                            Fixture newFixture = init.bf.createModularWaterFixture(newBody, width, height);
                            Water.setUserData(newBody, newFixture);
                            newFixture.setUserData("Water");
                        }
                    }
                }
                currentCell = init.cr.middleFloorTile;
            }

            //ROOFS---------------------------------------------------------
            else if (levelTextures.get(i).matches("roof.+")) {

                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 4);
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
            else if (levelTextures.get(i).matches("BFroof.+")) {
                String roofStr = levelTextures.get(i);
                StringBuffer sb = new StringBuffer(roofStr);
                sb.delete(0, 6);
                String strRoof = sb.toString();

                String roofType = String.valueOf(strRoof.charAt(0));

                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();

                currentCell = init.cr.bottomFenceTile;
                Body newBottomFenceRoof = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newBottomFenceRoof.setUserData("Wall");
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

                //String roofXtend = String.valueOf(strRoof.charAt(0));

                String roofType = String.valueOf(strRoof.charAt(0));
                StringBuffer sb2 = new StringBuffer(strRoof);
                sb2.delete(0, 1);
                String strRoofExt = sb2.toString();
                currentCell = init.cr.topWallTile;

                Body newTopWallRoof = init.bf.createWall(world, ((roomX + i) * 16 + 16 * 16) , levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                newTopWallRoof.setUserData("Wall");

                if (roofType.matches("[7]")) {
                    Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16 + 8, levelY * 16 + Gdx.graphics.getHeight() / 30 - 14,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                    roofs.add(roof);
                    init.roomList.get(roomIndex).roofs.add(roof);
                }
                else {
                    Roof roof = new Roof(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,false,false, Integer.parseInt(roofType), Integer.parseInt(strRoofExt));
                    roofs.add(roof);
                    init.roomList.get(roomIndex).roofs.add(roof);
                }
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
        PLAYER_X = (doorUpperLeftXAsFloat * 10) + 20;

        String doorUpperLeftY = doorUpperLeftXY[1].toString();
        float doorUpperLeftYAsFloat = Float.parseFloat(doorUpperLeftY);
        PLAYER_Y = doorUpperLeftYAsFloat * 16 - (3 * 20);

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
    public void generateColumn(int colExte, String colStr, int i, World world, float roomX, float levelY,
                               int topType, int stemType, int baseType, int lowerBaseType,
                               boolean fullBase,
                               boolean fire,
                               int fireType,
                               boolean flag,
                               int flagType,
                               boolean statue,
                               int statueType,
                               boolean trap,
                               int trapType) {

        Column coltu = new Column();

        for (int e = 0; e < colExte; e++) {
            ColumnPiece colTuStem = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16 + (e * 16),stemType);
            colTuStem.createColumnStem(false);
            colTuStem.stem = true;
            coltu.addPiece(colTuStem);
        }

        if (colExte == 0) {
            ColumnPiece colTuTop = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16,topType);
            colTuTop.createColumnTop(false);
            colTuTop.top = true;
            coltu.addPiece(colTuTop);
            if (fire) {
                Fire firecfl = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 + 2, false, 0f, 1, false, 0);
                firecfl.createFire(new Color(0.30f,0.12f,0,0.75f),60, null);
                fires.add(firecfl);
                lights.add(firecfl);
            }
            if (flag) {
                Flag flag2 = new Flag(world, ((roomX + i) * 16) + 16 * 16 + 3.5f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 0.5f);
                flag2.createFlagHitbox(((roomX + i) * 16) + 16 * 16 + 3.5f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 0.5f, world);
                flags.add(flag2);
            }
            if (trap) {
                Trap colTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16 - 0.5f, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 0.5f + 3.5f,trapType, 1);
                colTrap.createTrap();
                traps.add(colTrap);
            }
            if (statue) {
                if (statueType == 2) {
                    Statue statTu = new Statue(world, ((roomX + i) * 16) + 16 * 16 + 1, levelY * 16 + Gdx.graphics.getHeight() / 30 + 10,statueType);
                    statTu.createStatueHitbox(((roomX + i) * 16) + 16 * 16 + 3f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 26.5f, world);
                    statues.add(statTu);
                } else {
                    Statue statTu = new Statue(world, ((roomX + i) * 16) + 16 * 16 + 1, levelY * 16 + Gdx.graphics.getHeight() / 30 + 10,statueType);
                    statTu.createStatueHitbox(((roomX + i) * 16) + 16 * 16 + 4f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 26.5f, world);
                    statues.add(statTu);
                }
            }
        } else {
            //extend the column by the extension amount
            ColumnPiece colTuTop = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 16 + (colExte * 16),topType);
            colTuTop.createColumnTop(false);
            colTuTop.top = true;
            coltu.addPiece(colTuTop);
            if (fire) {
                Fire firecfl = new Fire(world, rayHandler, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 + 2 + (colExte * 16), false, 0f, 1, false, 0);
                firecfl.createFire(new Color(0.30f,0.12f,0,0.75f),60, null);
                fires.add(firecfl);
                lights.add(firecfl);
            }
            if (flag) {
                Flag flag2 = new Flag(world, ((roomX + i) * 16) + 16 * 16 + 3.5f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 0.5f + (colExte * 16));
                flag2.createFlagHitbox(((roomX + i) * 16) + 16 * 16 + 3.5f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 0.5f + (colExte * 16), world);
                flags.add(flag2);
            }
            if (trap) {
                Trap colTrap = new Trap(world, ((roomX + i) * 16) + 16 * 16 - 0.5f, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 0.5f + (colExte * 16),trapType, 1);
                colTrap.createTrap();
                traps.add(colTrap);
            }
            if (statue) {
                Statue statTu = new Statue(world, ((roomX + i) * 16) + 16 * 16 + 1, levelY * 16 + Gdx.graphics.getHeight() / 30 + 10 + (colExte * 16),statueType);
                statTu.createStatueHitbox(((roomX + i) * 16) + 16 * 16 + 4f,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16 + 26.5f + (colExte * 16), world);
                statues.add(statTu);
            }
        }

        columns.add(coltu);

        ColumnPiece colTuBase = new ColumnPiece(world,((roomX + i) * 16) + 16 * 16,levelY * 16 + Gdx.graphics.getHeight() / 30 - 16,baseType);
        colTuBase.createColumnBase();
        colTuBase.base = true;
        coltu.addPiece(colTuBase);
        ColumnPiece colTuBaseLower = new ColumnPiece(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, lowerBaseType);
        colTuBaseLower.createColumnBaseLower(fullBase);

        coltu.createColumnHitbox(colExte, fullBase, colTuBase.columnX, colTuBase.columnY,world);

    }
}
