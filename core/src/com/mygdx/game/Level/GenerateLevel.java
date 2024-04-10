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
    private float PLAYER_X, PLAYER_Y;
    private TiledMapTileLayer layer;
    public static int roomX, levelY, testRoomX, testLevelY;
    public int initialTestRoomX, initialTestLevelY;
    public int previousRoomX, previousLevelY, longestRow, currentRow, previousLongestRow, rollbackIndex, roomsIndex, tries;
    public int doorDirection, roomSize, currentRoomSize, previousRoomSize;
    public int testPreviousLongestRow, testLongestRow, testPreviousRoomSize, testCurrentRoomSize, testPreviousRoomX, testPreviousLevelY, testCurrentRow;
    private boolean intersecting, startingRoom, roomHitboxCreated;
    private int doorTop, doorBottom, doorLeft, doorRight;
    private List<Integer> xValues, yValues;
    public List<Room> rooms, testRooms;
    private List layerSizes;
    private ArrayList list;
    //public Room newRoom;
    public ArrayList<Integer> path, directionsAvailableIndexed;
    public ArrayList<Room> rolledbackRooms;

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

        int min = 20;
        int max = 30;
        int numRooms = (int)(Math.random() * (max - min + 1)) + min;

        path = new ArrayList(){};
        int currentDoorDirection = 0;
        int previousDoorDirection = 0;
        currentDoorDirection =  pd.pickInitialDirection(currentDoorDirection);
        for (int i = 0; i<numRooms; i++){
            if (i-1 != -1){
                previousDoorDirection = path.get(i-1);
            }
            else {
                previousDoorDirection = 0;
            }
            currentDoorDirection =  pd.pickInitialDirection(currentDoorDirection);
            path.add(i, currentDoorDirection);
        }
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
            if (r-1 == -1){ //create the first room
                startingRoom = true;
                roomHitboxCreated = false;
                list = generateRoom(
                        world,
                        startingRoom,
                        init.roomList.get(r),
                        init.roomList.get(r).roomNum,
                        init.roomList.get(r).index,
                        path.get(r), 0, init.roomList.get(r+1).directionTaken,
                        init.roomList.get(r).x1, init.roomList.get(r).y1,
                        init.roomList.get(r).roomSize, 0,
                        init.roomList.get(r).longestRow, 0
                );

            }
            else {
                if (r != init.roomList.size()-1){
                    startingRoom = false;
                    roomHitboxCreated = false;
                    list = generateRoom(
                            world,
                            startingRoom,
                            init.roomList.get(r),
                            init.roomList.get(r).roomNum,
                            init.roomList.get(r).index,
                            path.get(r), path.get(r-1), path.get(r+1),
                            init.roomList.get(r).x1, init.roomList.get(r).y1,
                            init.roomList.get(r).roomSize, init.roomList.get(r-1).roomSize,
                            init.roomList.get(r).longestRow, init.roomList.get(r-1).longestRow
                    );
                }
                else {
                    startingRoom = false;
                    roomHitboxCreated = false;
                    list = generateRoom(
                            world,
                            startingRoom,
                            init.roomList.get(r),
                            init.roomList.get(r).roomNum,
                            init.roomList.get(r).index,
                            path.get(r), path.get(r-1), 0,
                            init.roomList.get(r).x1, init.roomList.get(r).y1,
                            init.roomList.get(r).roomSize, init.roomList.get(r-1).roomSize,
                            init.roomList.get(r).longestRow, init.roomList.get(r-1).longestRow
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

            //TODO: FIX DIRECTION 2 WITH UNUSED PREVIOUSLONGESTROW
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

        int x1 = testRoomX;
        int x2 = testRoomX + w;
        int y1 = testLevelY;
        int y2 = testLevelY - h;

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
                newRoom.y1 = y1 + 4;
                newRoom.y2 = y2 + 4;

                newRoom.x1 = x1;
                newRoom.x2 = x2;
            }
            if (doorDirection == 2) {
                newRoom.x1 = x1 + 4;
                newRoom.x2 = x2 + 4;

                newRoom.y1 = y1;
                newRoom.y2 = y2;
            }
            if (doorDirection == 3) {
                newRoom.y1 = y1 - 4;
                newRoom.y2 = y2 - 4;

                newRoom.x1 = x1;
                newRoom.x2 = x2;

            }
            if (doorDirection == 4) {
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

        int doorY = y1;

        try{
            List<List<String>> roomFile = init.lp.read("Rooms/room" + init.roomList.get(roomIndex).roomNum + ".csv");

            testLongestRow = 0;
            for (int rowNum = 0; rowNum < testCurrentRoomSize; rowNum++) {
                testCurrentRow = roomFile.get(rowNum).size();
                if (testLongestRow < testCurrentRow) {
                    testLongestRow = testCurrentRow;
                }
                //TODO: grab X and Y door values at this point
                //TODO: USE TRANSLATE SYMBOLS TO SHIFT X OR Y VALUES TO MATCH DOOR COORDINATES BY GIVING ROOM DOOR LOCATIONS THEIR X AND Y VALUES RESPECTIVELY

                HashMap<String, String> doorMap = init.rr.translateSymbolsToFindDoors(roomFile, rowNum, roomIndex, path.get(roomIndex), init.roomList.get(roomIndex).doorLocations, x1, doorY);
                doorY = doorY + rowNum;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (Room r : init.roomList) {
          //  if (tries >= 4) {
          //      return failed = false;
          //  }
            if (!(r.y1 == 0 || r.x1 == 0)) {

                //checks first to see if the current room is NOT evaluating against itself
                if (init.roomList.get(roomIndex).index != r.index) {
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





        return failed = false;
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
                           //TODO: Create a comma splitter that isolates X and Y values, for door manipulation
                            if (doorTop <= 1  && ((nextDirection == 1 || doorDirection == 3))) {
                                //System.out.println("CURRENT DIRECTION: " + doorDirection + " PREVIOUS DIRECTION: " + previousDoorDirection + " NEXT DIRECTION: " + nextDirection);
                                /*
                                String topLeftX = Integer.toString((roomX + i) + 16);
                                String topLeftY = Integer.toString(levelY);
                                String topLeft = topLeftX + "," + topLeftY;
                                r.doorLocations.put("TopLeft", topLeft);

                                 */

                                if (!startingRoom) {
                                    //1 is up, 2 is right, 3 is down, 4 is left
                                    int currentRoomX = r.x1;
                                    int currentRoomY = r.y1;

                                    //int currentDoorX = (roomX + i) + 16;

                                    if (!(roomIndex+1> init.roomList.size())){


                                       // int nextDoorX = init.roomList.get(roomIndex + 1).x1;
                                       // int nextDoorY = init.roomList.get(roomIndex + 1).y1;

                                      //  init.roomList.get(roomIndex + 1).x1 = nextDoorX + currentRoomX;
                                    }
                                    else {

                                    }

                                    // String s = rooms.get(roomIndex).doorLocations.get("TopLeft");
                                    // s.split(",");


                                    //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("TopLeft"));
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
                        case "doorTopRight":
                            if (doorTop <= 1 && ((nextDirection == 1 || doorDirection == 3))) {
                                String topRightX = Integer.toString((roomX + i) + 16);
                                String topRightY = Integer.toString(levelY);
                                String topRight = topRightX + "," + topRightY;
                                r.doorLocations.put("TopRight", topRight);
                                //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("TopRight"));
                                currentCell = init.cr.doorTopRight;
                                doorTop++;
                                break;
                            }
                            else {
                                currentCell = init.cr.topWallTile;
                                Body newTopWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newTopWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorLeftUpper":
                            if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {
                                String upperLeftX = Integer.toString((roomX + i) + 16);
                                String upperLeftY = Integer.toString(levelY);
                                String upperLeft = upperLeftX + "," + upperLeftY;
                                r.doorLocations.put("UpperLeft", upperLeft);
                                //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("UpperLeft"));
                                currentCell = init.cr.doorLeftUpper;
                                doorLeft++;
                                break;
                            }
                            else {
                                currentCell = init.cr.leftWallTile;
                                Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newLeftWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorLeftLower":
                            if (doorLeft <= 1 && ((nextDirection == 4 || doorDirection == 2))) {
                                String lowerLeftX = Integer.toString((roomX + i) + 16);
                                String lowerLeftY = Integer.toString(levelY);
                                String lowerLeft = lowerLeftX + "," + lowerLeftY;
                                r.doorLocations.put("LowerLeft", lowerLeft);
                                //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("LowerLeft"));
                                currentCell = init.cr.doorLeftLower;
                                doorLeft++;
                                break;
                            }
                            else {
                                currentCell = init.cr.leftWallTile;
                                Body newLeftWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newLeftWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorRightUpper":
                            if (doorRight <= 1 && ((nextDirection == 2 || doorDirection == 4))) {
                                String upperRightX = Integer.toString((roomX + i) + 16);
                                String upperRightY = Integer.toString(levelY);
                                String upperRight = upperRightX + "," + upperRightY;
                                r.doorLocations.put("UpperRight", upperRight);
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
                        case "doorRightLower":
                            if (doorRight <= 1 && ((nextDirection == 2 || doorDirection == 4))) {
                                String lowerRightX = Integer.toString((roomX + i) + 16);
                                String lowerRightY = Integer.toString(levelY);
                                String lowerRight = lowerRightX + "," + lowerRightY;
                                r.doorLocations.put("LowerRight", lowerRight);
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
                        case "doorBottomLeft":
                            if (doorBottom <= 1 && ((nextDirection == 3 || doorDirection == 1))) {
                                String bottomLeftX = Integer.toString((roomX + i) + 16);
                                String bottomLeftY = Integer.toString(levelY);
                                String bottomLeft = bottomLeftX + "," + bottomLeftY;
                                r.doorLocations.put("BottomLeft", bottomLeft);
                                //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("BottomLeft"));
                                currentCell = init.cr.doorBottomLeft;
                                doorBottom++;
                                break;
                            }
                            else {
                                currentCell = init.cr.bottomWallTile;
                                Body newBottomWallCover = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                                newBottomWallCover.setUserData("Wall");
                                break;
                            }
                        case "doorBottomRight":
                            if (doorBottom <= 1 && (nextDirection == 3 || doorDirection == 1)) {
                                String bottomRightX = Integer.toString((roomX + i) + 16);
                                String bottomRightY = Integer.toString(levelY);
                                String bottomRight = bottomRightX + "," + bottomRightY;
                                r.doorLocations.put("BottomRight", bottomRight);
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