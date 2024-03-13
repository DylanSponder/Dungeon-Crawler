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
    private float PLAYER_X, PLAYER_Y;
    private TiledMapTileLayer layer;
    public static int roomX, levelY, testRoomX, testLevelY;
    public int initialTestRoomX, initialTestLevelY;
    public int previousRoomX, previousLevelY, shortestRow, longestRow, currentRow, previousLongestRow;
    public int random, doorDirection, previousDoorDirection, roomSize, currentRoomSize, previousRoomSize;
    public int testPreviousLongestRow, testLongestRow, testPreviousRoomSize,testCurrentRoomSize, testPreviousRoomX, testPreviousLevelY, testCurrentRow;
    private boolean intersecting;
    private List<Integer> xValues, yValues;
    public List<Room> rooms, testRooms;
    private List layerSizes;
    private ArrayList list;
    private int[] doorDirections;
    public Fixture roomHitbox;
    public boolean failed;

public ArrayList generateLevel(float PLAYER_X, float PLAYER_Y) {
    init = new InitLevel();
    init.InitializeLevel();
    pd = new PickDirection();
    xy = new SetRoomXandY();
    layer = init.layer;
    roomX = init.roomX;
    levelY = init.levelY;
    testRoomX = init.testRoomX;
    testLevelY = init.testLevelY;

    //sets the total number of rooms to generate
    // int numRooms = (int) (Math.random() * 10 + 3);
    int numRooms = 20;

    //System.out.println("Random number of rooms generated: " + numRooms);

    for (int i = 0; i < numRooms; i++) {
        if (i == 0) {
            doorDirection = pd.pickInitialDirection(0);
            //random = (int)(Math.random() * 3 + 1);
            //generateRoom(world, true, doorDirection);
            testGenerateRoom(true);
            //list = generateRoom(world, true, 0);

        } else {
            if (doorDirection == 1) {
                doorDirection = pd.pickInitialDirection(1);
                testGenerateRoom(false);
                //list = generateRoom(world, false, doorDirection);
            } else if (doorDirection == 2) {
                doorDirection = pd.pickInitialDirection(2);

              //  if (doorDirection == 2) {
               //     levelY = levelY - roomSize;
              //  }

                testGenerateRoom(false);
                //list = generateRoom(world, false, doorDirection);
            } else if (doorDirection == 3) {
                doorDirection = pd.pickInitialDirection(3);
                testGenerateRoom(false);
            } else if (doorDirection == 4) {
                doorDirection = pd.pickInitialDirection(4);
                testGenerateRoom(false);
            }
        }
    }
    for (Room r : init.testRooms) {

        System.out.println(
                "ROOM " + (init.testRooms.indexOf(r) + 1) +
                " X1: " + init.testRooms.get(init.testRooms.indexOf(r)).x1 +
                " X2: " + init.testRooms.get(init.testRooms.indexOf(r)).x2 +
                " Y1: " + init.testRooms.get(init.testRooms.indexOf(r)).y1 +
                " Y2: " + init.testRooms.get(init.testRooms.indexOf(r)).y2 +
                " Longest Row: " + init.testRooms.get(init.testRooms.indexOf(r)).longestRow +
                " Room Size: " + init.testRooms.get(init.testRooms.indexOf(r)).roomSize
        );

    }




    return list;
}
    public List testGenerateRoom(boolean startingRoom){
        random = (int)(Math.random() * 3 + 1);

        try {
            List<List<String>> room = init.lp.read("Rooms/room"+random+".csv");

            if (previousRoomSize == 0) {
                testPreviousRoomSize = room.size();
                testPreviousLongestRow = 0;
                testCurrentRoomSize = room.size();
            }
            else {
                testPreviousRoomSize = currentRoomSize;
                testPreviousLongestRow = longestRow;
                testPreviousRoomX = roomX;
                testPreviousLevelY  = levelY;
                testCurrentRoomSize = room.size();
            }
            testLongestRow = 0;
            for (int columnNum = 0; columnNum < testCurrentRoomSize; columnNum++) {
                testCurrentRow = room.get(columnNum).size();
                if (testLongestRow < testCurrentRow){
                    testLongestRow = testCurrentRow;
                }
            }
            failed = false;
            checkForIntersection(startingRoom);
            while (failed) {
                doorDirection = pd.pickNewDirection(doorDirection);
                checkForIntersection(startingRoom);
            }

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return list;
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

        if (!(init.testRooms.contains(newRoom))){
            init.testRooms.add(newRoom);
        }

        failed = false;

            for (Room r : init.testRooms) {
                //checks first to see if the current room is NOT evaluating against itself
                if (!(x1 == r.x1 && x2 == r.x2 && y1 == r.y1 && y2 == r.y2)){
                    if ((x2 > r.x1 && x2 <= r.x2) && (y1 > r.y2 && y2 < r.y1)){
                        System.out.println("LEFT INTERSECTION WITH ROOM: " + (init.testRooms.indexOf(r)+1));
                        System.out.println("ROOM X1: " + x1 + " OLD ROOM X1: " + r.x1);
                        System.out.println("ROOM X2: " + x2 + " OLD ROOM X2: " + r.x2);
                        System.out.println("ROOM Y1: " + y1 + " OLD ROOM Y1: " + r.y1);
                        System.out.println("ROOM Y2: " + y2 + " OLD ROOM Y2: " + r.y2);

                        testLevelY = initialTestLevelY;
                        testRoomX = initialTestRoomX;

                        return failed = true;
                    }
                    else if ((x1 < r.x2 && x1 >= r.x1) && (y1 > r.y2 && y2 < r.y1)){
                        System.out.println("RIGHT INTERSECTION WITH ROOM: " + + (init.testRooms.indexOf(r)+1));
                        System.out.println("ROOM X1: " + x1 + " OLD ROOM X1: " + r.x1);
                        System.out.println("ROOM X2: " + x2 + " OLD ROOM X2: " + r.x2);
                        System.out.println("ROOM Y1: " + y1 + " OLD ROOM Y1: " + r.y1);
                        System.out.println("ROOM Y2: " + y2 + " OLD ROOM Y2: " + r.y2);

                        testLevelY = initialTestLevelY;
                        testRoomX = initialTestRoomX;

                        return failed = true;
                    }
                }
            }
        list = generateRoom(world, startingRoom, doorDirection);
        return failed;
    }

    public ArrayList generateRoom(World world, boolean startingRoom, int doorDirection) {

        //randomly pick from available prefabs
        //int random = (int)(Math.random() * 3 + 1);

        try {
            List<List<String>> room = init.lp.read("Rooms/room"+random+".csv");

            //levelY is what determines the size of the level.
            //When levelY is either 1000 or 0 the map will be outside the TiledMapTileLayer and thus will not render
            if (previousRoomSize == 0) {
                previousRoomSize = room.size();
                previousLongestRow = 0;
                currentRoomSize = room.size();
            }
            else {
                previousRoomSize = currentRoomSize;
                previousLongestRow = longestRow;
                previousRoomX = roomX;
                previousLevelY  = levelY;
                currentRoomSize = room.size();
            }
            longestRow = 0;
            for (int columnNum = 0; columnNum < currentRoomSize; columnNum++) {
                currentRow = room.get(columnNum).size();
                if (longestRow < currentRow){
                    longestRow = currentRow;
                }
            }

            xy.setNextRoomDimensions(doorDirection, roomX, levelY, previousRoomSize, currentRoomSize, previousLongestRow, longestRow);

            //xy.resetDimensions();

            //create a box with the dimensions of the to-be-generated room - originally intended for collision detection but cannot be used that way
            //will instead be used for detecting if the player has entered a room for opening and closing doors
            roomHitbox = bf.createRoom(world, ((roomX*16)+16*16)+(longestRow*16)/2, (levelY *16-(currentRoomSize*16)/2)+16, currentRoomSize*16/2, longestRow*16/2);
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
                            Body newTopLeftWall = init.bf.createWall(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopLeftWall.setUserData("Wall");
                            break;
                        case "topWallTile":
                            currentCell = init.cr.topWallTile;
                            //System.out.println("i is: "+i);
                            //System.out.println("whilst roomX is: " + roomX);
                            Body newTopWall = init.bf.createWall(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopWall.setUserData("Wall");
                            break;
                        case "topRightWallTile":
                            currentCell = init.cr.topRightWallTile;
                            Body newTopRightWall = init.bf.createWall(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopRightWall.setUserData("Wall");
                            break;
                        case "leftWallTile":
                            currentCell = init.cr.leftWallTile;
                            Body newLeftWall = init.bf.createWall(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newLeftWall.setUserData("Wall");
                            break;
                        case "rightWallTile":
                            currentCell = init.cr.rightWallTile;
                            Body newRightWall = init.bf.createWall(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newRightWall.setUserData("Wall");
                            break;
                        case "bottomLeftWallTile":
                            currentCell = init.cr.bottomLeftWallTile;
                            Body newBottomLeftWall = init.bf.createWall(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomLeftWall.setUserData("Wall");
                            break;
                        case "bottomWallTile":
                            currentCell = init.cr.bottomWallTile;
                            Body newBottomWall = init.bf.createWall(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomWall.setUserData("Wall");
                            break;
                        case "bottomRightWallTile":
                            currentCell = init.cr.bottomRightWallTile;
                            Body newBottomRightWall = init.bf.createWall(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomRightWall.setUserData("Wall");
                            break;
                        case "topLeftTurnTile":
                            currentCell = init.cr.topLeftTurnTile;
                            Body newTopLeftTurn = init.bf.createWallTurn(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 0.1f);
                            newTopLeftTurn.setUserData("Wall");
                            break;
                        case "topRightTurnTile":
                            currentCell = init.cr.topRightTurnTile;
                            Body newTopRightTurn = init.bf.createWallTurn(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 0.1f);
                            newTopRightTurn.setUserData("Wall");
                            break;
                        case "bottomLeftTurnTile":
                            currentCell = init.cr.bottomLeftTurnTile;
                            Body newBottomLeftTurn = init.bf.createWallTurn(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
                            newBottomLeftTurn.setUserData("Wall");
                            break;
                        case "bottomRightTurnTile":
                            currentCell = init.cr.bottomRightTurnTile;
                            Body newBottomRightTurn = init.bf.createWallTurn(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
                            newBottomRightTurn.setUserData("Wall");
                            break;
                        case "enemy":
                            currentCell = init.cr.middleFloorTile;
                            Enemy enemy = new Enemy(DungeonCrawler.world, ((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            DungeonCrawler.enemies.add(enemy);
                            break;
                        case "door":
                            //TODO: have doors remain their original tile if not used - not just floor tiles
                            currentCell = init.cr.middleFloorTile;
                            break;
                        case "obstacle1":
                            currentCell = init.cr.obstacle1;
                            Body newObstacle1 = bf.createObstacle(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "obstacle2":
                            currentCell = init.cr.obstacle2;
                            Body newObstacle2 = bf.createObstacle(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "obstacle3":
                            currentCell = init.cr.obstacle3;
                            Body newObstacle3 = bf.createObstacle(world,((roomX + i) * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                    }

                    layer.setCell((roomX + i) + 16, levelY, currentCell);

                    if (startingRoom){
                        //set player starting coordinates according to the position of the first generated room
                        PLAYER_X = ((roomX + i) * 16) + Gdx.graphics.getHeight() / 30;
                        PLAYER_Y = (levelY * 16 + currentRoomSize * 16 - 16) + Gdx.graphics.getHeight() / 30;
                        //player.createPlayer(world, PLAYER_X, PLAYER_Y);
                        startingRoom = false;
                    }
                }
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