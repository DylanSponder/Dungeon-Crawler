package com.mygdx.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.*;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.behaviours.fsm.Enemy;

import java.io.IOException;
import java.sql.SQLSyntaxErrorException;
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
    public int previousRoomX, previousLevelY, longestRow, currentRow, previousLongestRow, rollbackIndex, roomsIndex;
    public int random, doorDirection, previousDoorDirection, roomSize, currentRoomSize, previousRoomSize;
    public int testPreviousLongestRow, testLongestRow, testPreviousRoomSize, testCurrentRoomSize, testPreviousRoomX, testPreviousLevelY, testCurrentRow;
    private boolean intersecting, startingRoom;
    private int doorTop, doorBottom, doorLeft, doorRight;
    private List<Integer> xValues, yValues;
    public List<Room> rooms, testRooms;
    private List layerSizes;
    private ArrayList list;
    //public Room newRoom;
    public ArrayList<Integer> directionsAvailable;
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
        roomsIndex = -1;
        //testRooms = init.testRooms;
        //rolledbackRooms = new ArrayList();

        //sets the total number of rooms to generate
        int numRooms = 15;
       // int numRooms = (int) (Math.random() * 2 + 1);

        System.out.println("Random number of rooms generated: " + numRooms);

        for (int i = 0; i < numRooms; i++) {
            roomsIndex++;
            directionsAvailable = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
            if (i == 0) {
                startingRoom = true;
                doorDirection = 0;
                testGenerateRoom(startingRoom);
            } else {
                startingRoom = false;
                testGenerateRoom(startingRoom);
            }
        }
        for (int r = 0; r < init.testRooms.size(); r++) {
            if (r-1 == -1){
                startingRoom = true;
                list = generateRoom(
                        world,
                        startingRoom,
                        init.testRooms.get(r),
                        init.testRooms.get(r).roomNum,
                        init.testRooms.get(r).directionTaken, 0, init.testRooms.get(r+1).directionTaken,
                        init.testRooms.get(r).x1, init.testRooms.get(r).y1,
                        init.testRooms.get(r).roomSize, 0,
                        init.testRooms.get(r).longestRow, 0
                );

            }
            else {
                if (r != init.testRooms.size()-1){
                    startingRoom = false;
                    list = generateRoom(
                            world,
                            startingRoom,
                            init.testRooms.get(r),
                            init.testRooms.get(r).roomNum,
                            init.testRooms.get(r).directionTaken, init.testRooms.get(r-1).directionTaken, init.testRooms.get(r+1).directionTaken,
                            init.testRooms.get(r).x1, init.testRooms.get(r).y1,
                            init.testRooms.get(r).roomSize, init.testRooms.get(r-1).roomSize,
                            init.testRooms.get(r).longestRow, init.testRooms.get(r-1).longestRow
                    );
                }
                else {
                    startingRoom = false;
                    list = generateRoom(
                            world,
                            startingRoom,
                            init.testRooms.get(r),
                            init.testRooms.get(r).roomNum,
                            init.testRooms.get(r).directionTaken, init.testRooms.get(r-1).directionTaken, 0,
                            init.testRooms.get(r).x1, init.testRooms.get(r).y1,
                            init.testRooms.get(r).roomSize, init.testRooms.get(r-1).roomSize,
                            init.testRooms.get(r).longestRow, init.testRooms.get(r-1).longestRow
                    );
                }
            }
            //Outputs the X and Y values of every room, for debugging purposes.
            System.out.println(
                    "ROOM " + (r + 1) +
                            " X1: " + init.testRooms.get(r).x1 +
                            " X2: " + init.testRooms.get(r).x2 +
                            " Y1: " + init.testRooms.get(r).y1 +
                            " Y2: " + init.testRooms.get(r).y2 +
                            " Longest Row: " + init.testRooms.get(r).longestRow +
                            " Room Size: " + init.testRooms.get(r).roomSize +
                            " Door Locations: " + init.testRooms.get(r).doorLocations
            );
        }
        return list;
    }

    public void testGenerateRoom(boolean startingRoom) {
        doorDirection = pd.pickInitialDirection(doorDirection);
        random = (int) (Math.random() * 2 + 1);

        try {
            List<List<String>> room = init.lp.read("Rooms/room" + random + ".csv");
            System.out.println("ROOM " + random + " CHOSEN" );

            if (previousRoomSize == 0) {
                testPreviousRoomSize = room.size();
                testPreviousLongestRow = 0;
                testCurrentRoomSize = room.size();
            } else {
                testPreviousRoomSize = currentRoomSize;
                testPreviousLongestRow = longestRow;
                testPreviousRoomX = roomX;
                testPreviousLevelY = levelY;
                testCurrentRoomSize = room.size();
            }
            testLongestRow = 0;
            for (int columnNum = 0; columnNum < testCurrentRoomSize; columnNum++) {
                testCurrentRow = room.get(columnNum).size();
                if (testLongestRow < testCurrentRow) {
                    testLongestRow = testCurrentRow;
                }
            }
            failed = false;

            checkForIntersection(startingRoom);
            while (failed) {

                if (directionsAvailable.isEmpty()){
                    System.out.println("DIRECTIONS EXHAUSTED - ATTEMPTING ROLLBACK");
                    rollbackIndex--;
                    rollbackRoom = init.testRooms.get(rollbackIndex);

                    if (!rolledbackRooms.contains(rollbackRoom)) {
                        rolledbackRooms.add(rollbackRoom);
                    }
                    else {
                        rollbackIndex--;
                        rollbackRoom = init.testRooms.get(rollbackIndex);
                    }

                    testRoomX = rollbackRoom.x1;
                    testLevelY = rollbackRoom.y1;
                    testCurrentRoomSize = previousRoomSize;
                    testLongestRow = testPreviousLongestRow;
                    System.out.println("WENT BACK TO PREVIOUS ROOM");
                    failed = false;
                }
                else {
                    doorDirection = pd.pickNewDirection(doorDirection);
                    checkForIntersection(startingRoom);
                }
            }

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkForIntersection(boolean startingRoom) {

        initialTestLevelY = testLevelY;
        initialTestRoomX = testRoomX;

        xy.checkRoomForIntersection(startingRoom, doorDirection, testRoomX, testLevelY, testPreviousRoomSize, testCurrentRoomSize, testPreviousLongestRow, testLongestRow);

        int h = testCurrentRoomSize;
        int w = testLongestRow;

        int x1 = testRoomX;
        int x2 = testRoomX + w;
        int y1 = testLevelY;
        int y2 = testLevelY - h;

        //create a room object with the dimensions of the room to-be generated
        Room newRoom = new Room();
        newRoom.x1 = x1;
        newRoom.x2 = x2;
        newRoom.y1 = y1;
        newRoom.y2 = y2;
        newRoom.roomSize = testCurrentRoomSize;
        newRoom.longestRow = testLongestRow;
        newRoom.roomNum = random;

        if (!(init.testRooms.contains(newRoom))) {
            init.testRooms.add(newRoom);
        }

        rollbackIndex = init.testRooms.size();

        for (Room r : init.testRooms) {
            //checks first to see if the current room is NOT evaluating against itself
            if (r != newRoom){
           // if (!(x1 == r.x1 && x2 == r.x2 && y1 == r.y1 && y2 == r.y2)) {
                if ((x2 > r.x1 && x2 <= r.x2) && (y1 > r.y2 && y2 < r.y1)) {
                    System.out.println("'LEFT' INTERSECTION WITH ROOM: " + (init.testRooms.indexOf(r) + 1));
                    init.testRooms.remove(newRoom);
                    /*
                    System.out.println("ROOM X1: " + x1 + " OLD ROOM X1: " + r.x1);
                    System.out.println("ROOM X2: " + x2 + " OLD ROOM X2: " + r.x2);
                    System.out.println("ROOM Y1: " + y1 + " OLD ROOM Y1: " + r.y1);
                    System.out.println("ROOM Y2: " + y2 + " OLD ROOM Y2: " + r.y2);
                     */
                   // System.out.println("DIRECTION INDEX: "+directionsAvailable.indexOf(doorDirection));
                    //System.out.println("DIRECTION VALUE: "+directionsAvailable.get(directionsAvailable.indexOf(doorDirection)));

                    if (directionsAvailable.contains(directionsAvailable.indexOf(doorDirection))){
                        directionsAvailable.remove(directionsAvailable.indexOf(doorDirection));
                    }
                    //System.out.println("AVAILABLE DIRECTIONS REMAINING: " + directionsAvailable);
                    testLevelY = initialTestLevelY;
                    testRoomX = initialTestRoomX;
                    return failed = true;
                } else if ((x1 < r.x2 && x1 >= r.x1) && (y1 > r.y2 && y2 < r.y1)) {
                    System.out.println("'RIGHT' INTERSECTION WITH ROOM: " + (init.testRooms.indexOf(r) + 1));
                    init.testRooms.remove(newRoom);
                    if (directionsAvailable.contains(directionsAvailable.indexOf(doorDirection))){
                        directionsAvailable.remove(directionsAvailable.indexOf(doorDirection));
                    }
                    //System.out.println("AVAILABLE DIRECTIONS REMAINING: " + directionsAvailable);
                    testLevelY = initialTestLevelY;
                    testRoomX = initialTestRoomX;
                    return failed = true;
                }
            }
        }
        newRoom.directionTaken = doorDirection;

        return failed = false;
    }

    public ArrayList generateRoom(World world, boolean startingRoom, Room r, int roomNum, int doorDirection, int previousDoorDirection, int nextDirection, int roomX, int levelY, int currentRoomSize, int previousRoomSize, int longestRow, int previousLongestRow) {
        //testGenerateRoom(startingRoom);
        //nextDirection = pd.pickInitialDirection(doorDirection);


       // doors.AlignDoors(startingRoom, r, init.testRooms, roomX, levelY);

        if (doorDirection == 1){
           //String s = r.doorLocations.get("TopLeft");

        }

        System.out.println("CURRENT DIRECTION: " + doorDirection + " PREVIOUS DIRECTION: " + previousDoorDirection + " NEXT DIRECTION: " + nextDirection);

        doorTop = 0;
        doorBottom = 0;
        doorLeft = 0;
        doorRight = 0;

        //randomly pick from available prefabs
        //int random = (int)(Math.random() * 3 + 1);

        try {
            List<List<String>> room = init.lp.read("Rooms/room" + roomNum + ".csv");
            //levelY is what determines the size of the level.
            //When levelY is either 1000 or 0 the map will be outside the TiledMapTileLayer and thus will not render

            longestRow = 0;
            for (int columnNum = 0; columnNum < currentRoomSize; columnNum++) {
                currentRow = room.get(columnNum).size();
                if (longestRow < currentRow) {
                    longestRow = currentRow;
                }
            }

         //   xy.setNextRoomDimensions(doorDirection, roomX, levelY, previousRoomSize, currentRoomSize, previousLongestRow, longestRow);

            //create a box with the dimensions of the to-be-generated room - originally intended for collision detection but cannot be used that way
            //will instead be used for detecting if the player has entered a room for opening and closing doors
            roomHitbox = bf.createRoom(world, ((roomX * 16) + 16 * 16) + (longestRow * 16) / 2, (levelY * 16 - (currentRoomSize * 16) / 2) + 16, currentRoomSize * 16 / 2, longestRow * 16 / 2);
            roomHitbox.setSensor(true);

            for (int columnNum = 0; columnNum < currentRoomSize; columnNum++) {

                List<String> levelTextures = init.rr.translateSymbols(room, columnNum);

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
                            currentCell = init.cr.doorTopLeftWall;
                            Body newDoorTopLeftWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newDoorTopLeftWall.setUserData("Wall");
                            break;
                        case "doorTopRightWall":
                            currentCell = init.cr.doorTopRightWall;
                            Body newDoorTopRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newDoorTopRightWall.setUserData("Wall");
                            break;
                        case "doorLeftUpperWall":
                            currentCell = init.cr.doorLeftUpperWall;
                            Body newDoorLeftUpperWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newDoorLeftUpperWall.setUserData("Wall");
                            break;
                        case "doorLeftLowerWall":
                            currentCell = init.cr.doorLeftLowerWall;
                            Body newDoorLeftLowerWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newDoorLeftLowerWall.setUserData("Wall");
                            break;
                        case "doorRightUpperWall":
                            currentCell = init.cr.doorRightUpperWall;
                            Body newDoorRightUpperWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newDoorRightUpperWall.setUserData("Wall");
                            break;
                        case "doorRightLowerWall":
                            currentCell = init.cr.doorRightLowerWall;
                            Body newDoorRightLowerWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newDoorRightLowerWall.setUserData("Wall");
                            break;
                        case "doorBottomLeftWall":
                            currentCell = init.cr.doorBottomLeftWall;
                            Body newDoorBottomLeftWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newDoorBottomLeftWall.setUserData("Wall");
                            break;
                        case "doorBottomRightWall":
                            currentCell = init.cr.doorBottomRightWall;
                            Body newDoorBottomRightWall = init.bf.createWall(world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newDoorBottomRightWall.setUserData("Wall");
                            break;
                        case "doorTopLeft":
                           //TODO: Create a comma splitter that isolates X and Y values, for door manipulation
                            if (doorTop <= 1  && ((nextDirection == 1 || doorDirection == 3))) {
                                System.out.println("CURRENT DIRECTION: "+doorDirection + " PREVIOUS DIRECTION: "+ previousDoorDirection + " NEXT DIRECTION: " + nextDirection);
                                String topLeftX = Integer.toString((roomX + i) + 16);
                                String topLeftY = Integer.toString(levelY);
                                String topLeft = topLeftX + "," + topLeftY;
                                r.doorLocations.put("TopLeft", topLeft);
                                //System.out.println("DOOR LOCATION: " + (r.doorLocations).get("TopLeft"));
                                currentCell = init.cr.doorTopLeft;
                                doorTop++;
                                break;
                            }
                            else {
                                 currentCell = init.cr.topWallTile; break;
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
                                currentCell = init.cr.topWallTile; break;
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
                                currentCell = init.cr.leftWallTile; break;
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
                                currentCell = init.cr.leftWallTile; break;
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
                                currentCell = init.cr.rightWallTile; break;
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
                                currentCell = init.cr.rightWallTile; break;
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
                                currentCell = init.cr.bottomWallTile; break;
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
                               currentCell = init.cr.bottomWallTile; break;
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

                        PLAYER_Y = init.testRooms.get(0).y1 * 16;
                        PLAYER_X = init.testRooms.get(0).x1 * 16;
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