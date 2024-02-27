package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.behaviours.fsm.Enemy;

import java.io.IOException;
import java.util.*;

public class GenerateLevel {

    private LevelParser lp;
    private RenderRules rr;
    private CreateCell cr;
    private BodyFactory bf;
    private CreateTexture tx;
    private Player player;
    private float PLAYER_X, PLAYER_Y;
    private TiledMapTileLayer layer;
    private int levelY;
    private int longestRow, currentRow;
    private int xOffset;
    private int roomX;
    private List layerSizes;

public void initLevel() {
    //load level
      lp = new LevelParser();
      rr = new RenderRules();
      cr = new CreateCell();
      bf = new BodyFactory();
      tx = new CreateTexture();
      tx.textureRegionBuilder();
      player = new Player();

    cr.middleFloorTile.setTile(new StaticTiledMapTile(tx.roomMiddleFloorTexture));
    cr.topLeftWallTile.setTile(new StaticTiledMapTile(tx.roomTopLeftWallTexture));
    cr.topWallTile.setTile(new StaticTiledMapTile(tx.roomTopWallTexture));
    cr.topRightWallTile.setTile(new StaticTiledMapTile(tx.roomTopRightWallTexture));
    cr.leftWallTile.setTile(new StaticTiledMapTile(tx.roomLeftWallTexture));
    cr.rightWallTile.setTile(new StaticTiledMapTile(tx.roomRightWallTexture));
    cr.bottomLeftWallTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftWallTexture));
    cr.bottomWallTile.setTile(new StaticTiledMapTile(tx.roomBottomWallTexture));
    cr.bottomRightWallTile.setTile(new StaticTiledMapTile(tx.roomBottomRightWallTexture));
    cr.topLeftTurnTile.setTile(new StaticTiledMapTile(tx.roomTopLeftTurnTexture));
    cr.topRightTurnTile.setTile(new StaticTiledMapTile(tx.roomTopRightTurnTexture));
    cr.bottomLeftTurnTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftTurnTexture));
    cr.bottomRightTurnTile.setTile(new StaticTiledMapTile(tx.roomBottomRightTurnTexture));
    cr.obstacle1.setTile(new StaticTiledMapTile(tx.obstacle1Texture));
    cr.obstacle2.setTile(new StaticTiledMapTile(tx.obstacle2Texture));
    cr.obstacle3.setTile(new StaticTiledMapTile(tx.obstacle3Texture));

    //initialize map
    TiledMap map = new TiledMap();

    //set map layer dimensions
    //set to 1000 tile layers wide and high but can be changed if required
    layer = new TiledMapTileLayer(1000, 1000, 16, 16);

    levelY = 999;
    currentRow = 0;
    longestRow = 0;
    roomX = 0;
    xOffset = 0;

    layerSizes = new ArrayList();

   // List<Integer> layerSizes = Arrays.asList(0);

}
public List generateLevel(float PLAYER_X, float PLAYER_Y) {

    //sets the total number of rooms to generate
   // int numRooms = (int) (Math.random() * 10 + 3);
    int numRooms = 1;

    System.out.println("Random number of rooms generated: " + numRooms);

    List list = null;
    for (int i = 0; i < numRooms; i++) {
        if (i == 0) {
            int doorDirection = (int) (Math.random() * 4 + 1);
            list = generateRoom(DungeonCrawler.world, true, doorDirection);
            roomX = roomX + xOffset;
        } else {
            int doorDirection = (int) (Math.random() * 4 + 1);
            list = generateRoom(DungeonCrawler.world, false, doorDirection);
        }
    }
    return list;
}

    public List generateRoom(World world, boolean startingRoom, int doorDirection) {

        //randomly pick from available prefabs
        int random = (int)(Math.random() * 3 + 1);

        System.out.println("Random number: " + random);

        try {
            List<List<String>> room = lp.read("Rooms/room"+random+".csv");

            //levelY is what determines the size of the level.
            //When levelY is either 1000 or 0 the map will be outside the TiledMapTileLayer and thus will not render

            int roomSize = room.size();

            for (int columnNum = 0; columnNum < roomSize; columnNum++) {
                currentRow = room.get(columnNum).size();
                if (longestRow < currentRow){
                    longestRow = currentRow;
                }
                xOffset = longestRow;


                List<String> levelTextures = rr.translateSymbols(room, columnNum);
                System.out.println("LEVEL TEX:"+levelTextures);
                //levelTextures.removeAll(Arrays.asList(""));


                int layerSize = levelTextures.size();
                layerSizes.add(layerSize);

                for (int i = 0; i < layerSize; i++) {
                    TiledMapTileLayer.Cell currentCell = new TiledMapTileLayer.Cell();
                    switch (levelTextures.get(i)) {
                        case "middleFloorTile":
                            currentCell = cr.middleFloorTile;
                            break;
                        case "topLeftWallTile":
                            currentCell = cr.topLeftWallTile;
                            Body newTopLeftWall = bf.createWall(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopLeftWall.setUserData("Wall");
                            break;
                        case "topWallTile":
                            currentCell = cr.topWallTile;
                            Body newTopWall = bf.createWall(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopWall.setUserData("Wall");
                            break;
                        case "topRightWallTile":
                            currentCell = cr.topRightWallTile;
                            Body newTopRightWall = bf.createWall(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newTopRightWall.setUserData("Wall");
                            break;
                        case "leftWallTile":
                            currentCell = cr.leftWallTile;
                            Body newLeftWall = bf.createWall(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newLeftWall.setUserData("Wall");
                            break;
                        case "rightWallTile":
                            currentCell = cr.rightWallTile;
                            Body newRightWall = bf.createWall(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newRightWall.setUserData("Wall");
                            break;
                        case "bottomLeftWallTile":
                            currentCell = cr.bottomLeftWallTile;
                            Body newBottomLeftWall = bf.createWall(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomLeftWall.setUserData("Wall");
                            break;
                        case "bottomWallTile":
                            currentCell = cr.bottomWallTile;
                            Body newBottomWall = bf.createWall(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomWall.setUserData("Wall");
                            break;
                        case "bottomRightWallTile":
                            currentCell = cr.bottomRightWallTile;
                            Body newBottomRightWall = bf.createWall(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            newBottomRightWall.setUserData("Wall");
                            break;
                        case "topLeftTurnTile":
                            currentCell = cr.topLeftTurnTile;
                            Body newTopLeftTurn = bf.createWallTurn(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 0.1f);
                            newTopLeftTurn.setUserData("Wall");
                            break;
                        case "topRightTurnTile":
                            currentCell = cr.topRightTurnTile;
                            Body newTopRightTurn = bf.createWallTurn(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 0.1f);
                            newTopRightTurn.setUserData("Wall");
                            break;
                        case "bottomLeftTurnTile":
                            currentCell = cr.bottomLeftTurnTile;
                            Body newBottomLeftTurn = bf.createWallTurn(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
                            newBottomLeftTurn.setUserData("Wall");
                            break;
                        case "bottomRightTurnTile":
                            currentCell = cr.bottomRightTurnTile;
                            Body newBottomRightTurn = bf.createWallTurn(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
                            newBottomRightTurn.setUserData("Wall");
                            break;
                        case "enemy":
                            currentCell = cr.middleFloorTile;
                            Enemy enemy = new Enemy(DungeonCrawler.world, (i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            DungeonCrawler.enemies.add(enemy);
                            break;
                        case "door":
                            //TODO: have doors remain their original tile if not used - not just floor tiles
                            currentCell = cr.middleFloorTile;
                            break;
                        case "obstacle1":
                            currentCell = cr.obstacle1;
                            Body newObstacle1 = bf.createObstacle(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "obstacle2":
                            currentCell = cr.obstacle2;
                            Body newObstacle2 = bf.createObstacle(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "obstacle3":
                            currentCell = cr.obstacle3;
                            Body newObstacle3 = bf.createObstacle(world,(i * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                    }
                    layer.setCell(i + 16, levelY, currentCell);
                }
                levelY--;
            }
            System.out.println("LONGEST ROW: "+ longestRow);
            if (startingRoom){
                //set player starting coordinates according to the position of the first generated room
                PLAYER_X = Gdx.graphics.getWidth() / 30 * 16;
                PLAYER_Y = levelY * 16 + roomSize * 16 - 16;
                //player.createPlayer(world, PLAYER_X, PLAYER_Y);
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
