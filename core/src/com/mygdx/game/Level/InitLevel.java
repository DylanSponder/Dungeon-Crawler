package com.mygdx.game.level;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.game.CreateTexture;
import com.mygdx.game.box2D.BodyFactory;

import java.util.ArrayList;
import java.util.List;

public class InitLevel {
    public LevelParser lp;
    public RenderRules rr;
    public CreateCell cr;
    public BodyFactory bf;
    public CreateTexture tx;
    public PickDirection pd;
    public SetRoomXandY xy;
    public TiledMapTileLayer layer;
    public int roomX, levelY;
    public int shortestRow, longestRow, currentRow, previousLongestRow;
    public int doorDirection, previousDoorDirection, roomSize, currentRoomSize, previousRoomSize;
    public int testRoomX, testLevelY;
    public int[] doorDirections;
    public List<Room> rooms, roomList;
    public List<Integer> layerSizes;
    public ArrayList list;

    public void InitializeLevel() {
        lp = new LevelParser();
        rr = new RenderRules();
        cr = new CreateCell();
        cr.InitializeCells();
        bf = new BodyFactory();
        tx = new CreateTexture();
        tx.textureRegionBuilder();
        pd = new PickDirection();
        xy = new SetRoomXandY();

        //sets tiles to their appropriate texture
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
        cr.tutorialTile.setTile(new StaticTiledMapTile(tx.tutoTexture));

        cr.doorTopLeftWall.setTile(new StaticTiledMapTile(tx.doorTopLeftWallTexture));
        cr.doorTopRightWall.setTile(new StaticTiledMapTile(tx.doorTopRightWallTexture));
        cr.doorLeftUpperWall.setTile(new StaticTiledMapTile(tx.doorLeftUpperWallTexture));
        cr.doorLeftLowerWall.setTile(new StaticTiledMapTile(tx.doorLeftLowerWallTexture));
        cr.doorRightUpperWall.setTile(new StaticTiledMapTile(tx.doorRightUpperWallTexture));
        cr.doorRightLowerWall.setTile(new StaticTiledMapTile(tx.doorRightLowerWallTexture));
        cr.doorBottomLeftWall.setTile(new StaticTiledMapTile(tx.doorBottomLeftWallTexture));
        cr.doorBottomRightWall.setTile(new StaticTiledMapTile(tx.doorBottomRightWallTexture));

        cr.doorTopLeft.setTile(new StaticTiledMapTile(tx.doorTopLeftTexture));
        cr.doorTopRight.setTile(new StaticTiledMapTile(tx.doorTopRightTexture));
        cr.doorTopLeftOpen.setTile(new StaticTiledMapTile(tx.doorTopLeftOpenTexture));
        cr.doorTopRightOpen.setTile(new StaticTiledMapTile(tx.doorTopRightOpenTexture));

        cr.doorLeftUpper.setTile(new StaticTiledMapTile(tx.doorLeftUpperTexture));
        cr.doorLeftLower.setTile(new StaticTiledMapTile(tx.doorLeftLowerTexture));
        cr.doorLeftUpperOpen.setTile(new StaticTiledMapTile(tx.doorLeftUpperOpenTexture));
        cr.doorLeftLowerOpen.setTile(new StaticTiledMapTile(tx.doorLeftLowerOpenTexture));

        cr.doorRightUpper.setTile(new StaticTiledMapTile(tx.doorRightUpperTexture));
        cr.doorRightLower.setTile(new StaticTiledMapTile(tx.doorRightLowerTexture));
        cr.doorRightUpperOpen.setTile(new StaticTiledMapTile(tx.doorRightUpperOpenTexture));
        cr.doorRightLowerOpen.setTile(new StaticTiledMapTile(tx.doorRightLowerOpenTexture));

        cr.doorBottomLeft.setTile(new StaticTiledMapTile(tx.doorBottomLeftTexture));
        cr.doorBottomRight.setTile(new StaticTiledMapTile(tx.doorBottomRightTexture));
        cr.doorBottomLeftOpen.setTile(new StaticTiledMapTile(tx.doorBottomLeftOpenTexture));
        cr.doorBottomRightOpen.setTile(new StaticTiledMapTile(tx.doorBottomRightOpenTexture));


        //initialize map
        TiledMap map = new TiledMap();

        //set map layer dimensions
        //set to 1000 tile layers wide and high but can be changed if required
        layer = new TiledMapTileLayer(1000, 1000, 16, 16);

        //changed to 499 from 999 to allow rooms to spawn above and below instead of just downwards
        //list = new ArrayList();
        testRoomX = 500;
        testLevelY = 500;
        roomX = 500;
        levelY = 500;
        roomList = new ArrayList<>();
        rooms = new ArrayList<>();
        currentRow = 0;
        longestRow = 0;
        doorDirections = new int[] {1,2,3,4};
        layerSizes = new ArrayList<>();
        //doorLocations = new HashMap();
    }
}
