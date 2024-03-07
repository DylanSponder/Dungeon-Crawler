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
    private float PLAYER_X, PLAYER_Y;
    private TiledMapTileLayer layer;
    private int roomX, levelY, previousRoomX, previousLevelY;
    private int testRoomX, testLevelY;
    private boolean intersecting;
    private List<Integer> xValues, yValues;
    //public List<Room> rooms;
    public int shortestRow, longestRow, currentRow, previousLongestRow;
    private int doorDirection, previousDoorDirection, roomSize, currentRoomSize, previousRoomSize;
    private List layerSizes;
    private int[] doorDirections;
    public Fixture roomHitbox;

public List generateLevel(float PLAYER_X, float PLAYER_Y) {
    init = new InitLevel();
    init.InitializeLevel();
    pd = new PickDirection();
    layer = init.layer;
    roomX = init.roomX;
    levelY = init.levelY;

    //sets the total number of rooms to generate
    // int numRooms = (int) (Math.random() * 10 + 3);
    int numRooms = 10;

    //System.out.println("Random number of rooms generated: " + numRooms);

    List list = null;
    for (int i = 0; i < numRooms; i++) {
        System.out.println("TEST");
        if (i == 0) {
            list = generateRoom(world, true, 0);
            doorDirection = pd.pickDirection(0);
        } else {
            if (doorDirection == 1) {
                doorDirection = pd.pickDirection(1);
                list = generateRoom(world, false, doorDirection);
                System.out.println("STARTING ROOM");
            } else if (doorDirection == 2) {
                doorDirection = pd.pickDirection(2);
                if (doorDirection == 2) {
                    levelY = levelY - roomSize;
                }
                list = generateRoom(world, false, doorDirection);
            } else if (doorDirection == 3) {
                doorDirection = pd.pickDirection(3);
                list = generateRoom(world, false, doorDirection);
            } else if (doorDirection == 4) {
                doorDirection = pd.pickDirection(4);
                list = generateRoom(world, false, doorDirection);
            }
        }
    }
    return list;
}

    public int setNextRoomDimensions(int doorDirection){
        //1 is up, 2 is right, 3 is down, 4 is left
        if (doorDirection == 1){
            levelY = levelY + (previousRoomSize + currentRoomSize);
        }
        else if (doorDirection == 2) {
            levelY = levelY + (previousRoomSize);
            roomX = roomX + previousLongestRow;
        }
        else if (doorDirection == 3){
            //nothing needs to be done here - rooms naturally generate going downwards
        }
        else if (doorDirection == 4){
            levelY = levelY + currentRoomSize;
            roomX = roomX - longestRow;
        }
        return doorDirection;
    }

    public int checkRoomForIntersection(int doorDirection) {
        //1 is up, 2 is right, 3 is down, 4 is left
        if (doorDirection == 1){
            testLevelY = levelY + (previousRoomSize + currentRoomSize);
        }
        else if (doorDirection == 2) {
            testLevelY = levelY + (previousRoomSize);
            testRoomX = roomX + previousLongestRow;
        }
        else if (doorDirection == 3){
            //nothing needs to be done here - rooms naturally generate going downwards
        }
        else if (doorDirection == 4){
            testLevelY = levelY + currentRoomSize;
            testRoomX = roomX - longestRow;
        }
        return doorDirection;
    }

    public List generateRoom(World world, boolean startingRoom, int doorDirection) {

        //randomly pick from available prefabs
        int random = (int)(Math.random() * 3 + 1);

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


           // checkRoomForIntersection(doorDirection);

            int h = currentRoomSize;
            int w = longestRow;

            int x1 = roomX;
            int x2 = roomX + w;
            int y1 = levelY;
            int y2 = levelY - h;

            //create a room object with the dimensions of the room to-be generated
            Room newRoom = new Room();
            newRoom.x1 = x1;
            newRoom.x2 = x2;
            newRoom.y1 = y1;
            newRoom.y2 = y2;

            for (Room r : init.rooms) {

                if (((x2 > r.x1 && x2 <= r.x2) && ((y1 > r.y2) && (y2 < r.y1)))){
                    System.out.println("INTERSECTION LEFT");
                    //pickNewDirection(doorDirection);
                    //checkRoomForIntersection(doorDirection);
                    //intersecting = true;
                }
                else if ((x1 < r.x2 && x1 >= r.x1) && ((y1 > r.y2) && (y2 < r.y1))){
                    System.out.println("INTERSECTION RIGHT");
                    //pickNewDirection(doorDirection);
                    //checkRoomForIntersection(doorDirection);
                    //intersecting = true;
                }
                else {

                }
            }
            setNextRoomDimensions(doorDirection);

            init.rooms.add(newRoom);


            //create a box with the dimensions of the to-be-generated room - originally intended for collision detection but cannot be used that way
            //will instead be used for detecting if the player has entered a room for opening and clsoing doors
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
        List list = new ArrayList();
        list.add(layer);
        list.add(PLAYER_X);
        list.add(PLAYER_Y);
        return list;
    }
}
