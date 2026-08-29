package com.mygdx.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.level.objects.Room;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.DungeonCrawler.camera;

public class InitLevel {
    public LevelParser lp;
    public RenderRules rr;
    public CreateCell cr;
    public BodyFactory bf;
    public CreateAssets tx;
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
        tx = new CreateAssets();
        tx.textureRegionBuilder();
        pd = new PickDirection();
        xy = new SetRoomXandY();

        //sets tiles to their appropriate texture
        cr.airTile.setTile(new StaticTiledMapTile(tx.air));


        //load cells
        cr.middleFloorTile.setTile(new StaticTiledMapTile(tx.roomFloorTexture));
        cr.middleFloorTile2.setTile(new StaticTiledMapTile(tx.roomFloorTexture2));
        cr.middleFloor2Tile.setTile(new StaticTiledMapTile(tx.roomFloor2Texture));
        cr.middleFloor3Tile.setTile(new StaticTiledMapTile(tx.roomFloor3Texture));
        cr.middleFloor3Tile2.setTile(new StaticTiledMapTile(tx.roomFloor3Texture2));
        cr.middleFloor4Tile.setTile(new StaticTiledMapTile(tx.roomFloor4Texture));
        cr.middleFloor4Tile2.setTile(new StaticTiledMapTile(tx.roomFloor4Texture2));

        cr.decorFloorUpTile.setTile(new StaticTiledMapTile(tx.roomDecorativeFloorUpTexture));
        cr.decorFloorDownTile.setTile(new StaticTiledMapTile(tx.roomDecorativeFloorDownTexture));
        cr.decorFloorLeftTile.setTile(new StaticTiledMapTile(tx.roomDecorativeFloorLeftTexture));
        cr.decorFloorRightTile.setTile(new StaticTiledMapTile(tx.roomDecorativeFloorRightTexture));
        cr.decorFloorTopLeftTile.setTile(new StaticTiledMapTile(tx.roomDecorativeFloorTopLeftTexture));
        cr.decorFloorTopRightTile.setTile(new StaticTiledMapTile(tx.roomDecorativeFloorTopRightTexture));
        cr.decorFloorBottomLeftTile.setTile(new StaticTiledMapTile(tx.roomDecorativeFloorBottomLeftTexture));
        cr.decorFloorBottomRightTile.setTile(new StaticTiledMapTile(tx.roomDecorativeFloorBottomRightTexture));

        cr.mosaicTridentTile.setTile(new StaticTiledMapTile(tx.mosaicTrident));
        cr.mosaicBullTile.setTile(new StaticTiledMapTile(tx.mosaicBull));
        cr.mosaicGryphonTile.setTile(new StaticTiledMapTile(tx.mosaicGryphon));
        cr.mosaicSpartanTile.setTile(new StaticTiledMapTile(tx.mosaicSpartan));
        cr.mosaicLyreTile.setTile(new StaticTiledMapTile(tx.mosaicLyre));

        cr.floorTile.setTile(new StaticTiledMapTile(tx.floorTile));
        cr.sunkenFloorTile.setTile(new StaticTiledMapTile(tx.sunkenFloorTile));
        cr.darkFloorTile.setTile(new StaticTiledMapTile(tx.darkFloorTile));
        cr.darkSunkenFloorTile.setTile(new StaticTiledMapTile(tx.darkSunkenFloorTile));
        cr.keyTile.setTile(new StaticTiledMapTile(tx.keyTile));
        cr.key2Tile.setTile(new StaticTiledMapTile(tx.key2Tile));

        cr.pitTile.setTile(new StaticTiledMapTile(tx.pit));
        cr.pitFloorTile.setTile(new StaticTiledMapTile(tx.pitFloor1));
        cr.pitFloor2Tile.setTile(new StaticTiledMapTile(tx.pitFloor2));
        cr.pitStairsTile.setTile(new StaticTiledMapTile(tx.pitStairs));
        cr.pitLeftTile.setTile(new StaticTiledMapTile(tx.pitLeft));
        cr.pitRightTile.setTile(new StaticTiledMapTile(tx.pitRight));
        cr.pitBottomTile.setTile(new StaticTiledMapTile(tx.pitBottom));

        cr.pitBottomLeftTile.setTile(new StaticTiledMapTile(tx.pitBottomLeft));
        cr.pitBottomRightTile.setTile(new StaticTiledMapTile(tx.pitBottomRight));

        cr.largeWallUpTile.setTile(new StaticTiledMapTile(tx.largeWallUp));
        cr.largeWallDownTile.setTile(new StaticTiledMapTile(tx.largeWallDown));
        cr.largeWallLeftTile.setTile(new StaticTiledMapTile(tx.largeWallLeft));
        cr.largeWallRightTile.setTile(new StaticTiledMapTile(tx.largeWallRight));

        cr.largeWallTopLeftTile.setTile(new StaticTiledMapTile(tx.largeWallTopLeft));
        cr.largeWallTopRightTile.setTile(new StaticTiledMapTile(tx.largeWallTopRight));
        cr.largeWallBottomLeftTile.setTile(new StaticTiledMapTile(tx.largeWallBottomLeft));
        cr.largeWallBottomRightTile.setTile(new StaticTiledMapTile(tx.largeWallBottomRight));

        cr.largeWallTopLeftTurnTile.setTile(new StaticTiledMapTile(tx.largeWallTopLeftTurn));
        cr.largeWallTopRightTurnTile.setTile(new StaticTiledMapTile(tx.largeWallTopRightTurn));
        cr.largeWallBottomLeftTurnTile.setTile(new StaticTiledMapTile(tx.largeWallBottomLeftTurn));
        cr.largeWallBottomRightTurnTile.setTile(new StaticTiledMapTile(tx.largeWallBottomRightTurn));

        cr.innerWallUpTile.setTile(new StaticTiledMapTile(tx.innerWallUp));
        cr.innerWallDownTile.setTile(new StaticTiledMapTile(tx.innerWallDown));
        cr.innerWallLeftTile.setTile(new StaticTiledMapTile(tx.innerWallLeft));
        cr.innerWallRightTile.setTile(new StaticTiledMapTile(tx.innerWallRight));

        cr.innerWallTLTurnTile.setTile(new StaticTiledMapTile(tx.innerWallTLTurn));
        cr.innerWallTRTurnTile.setTile(new StaticTiledMapTile(tx.innerWallTRTurn));
        cr.innerWallBLTurnTile.setTile(new StaticTiledMapTile(tx.innerWallBLTurn));
        cr.innerWallBRTurnTile.setTile(new StaticTiledMapTile(tx.innerWallBRTurn));

        cr.innerWallTLTurn2Tile.setTile(new StaticTiledMapTile(tx.innerWallTLTurn2));
        cr.innerWallTRTurn2Tile.setTile(new StaticTiledMapTile(tx.innerWallTRTurn2));
        cr.innerWallBLTurn2Tile.setTile(new StaticTiledMapTile(tx.innerWallBLTurn2));
        cr.innerWallBRTurn2Tile.setTile(new StaticTiledMapTile(tx.innerWallBRTurn2));

        cr.topLeftWallTile.setTile(new StaticTiledMapTile(tx.roomTopLeftWallTexture));
        cr.topWallTile.setTile(new StaticTiledMapTile(tx.roomTopWallTexture));
        cr.topRightWallTile.setTile(new StaticTiledMapTile(tx.roomTopRightWallTexture));
        cr.leftWallTile.setTile(new StaticTiledMapTile(tx.roomLeftWallTexture));
        cr.rightWallTile.setTile(new StaticTiledMapTile(tx.roomRightWallTexture));
        cr.bottomLeftWallTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftWallTexture));
        cr.bottomWallTile.setTile(new StaticTiledMapTile(tx.roomBottomWallTexture));
        cr.bottomRightWallTile.setTile(new StaticTiledMapTile(tx.roomBottomRightWallTexture));
        cr.topLeftTurnTile.setTile(new StaticTiledMapTile(tx.roomTopLeftTurnWallTexture));
        cr.topRightTurnTile.setTile(new StaticTiledMapTile(tx.roomTopRightTurnWallTexture));
        cr.bottomLeftTurnTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftTurnWallTexture));
        cr.bottomRightTurnTile.setTile(new StaticTiledMapTile(tx.roomBottomRightTurnWallTexture));
        //cr.obstacle1.setTile(new StaticTiledMapTile(tx.obstacle1Texture));
        //cr.obstacle2.setTile(new StaticTiledMapTile(tx.obstacle2Texture));
        //cr.obstacle3.setTile(new StaticTiledMapTile(tx.obstacle3Texture));
        cr.tutorialTile.setTile(new StaticTiledMapTile(tx.tutoTexture));

        cr.topFenceTile.setTile(new StaticTiledMapTile(tx.roomTopFence));
        cr.bottomFenceTile.setTile(new StaticTiledMapTile(tx.roomBottomFence));
        cr.leftFenceTile.setTile(new StaticTiledMapTile(tx.roomLeftFence));
        cr.rightFenceTile.setTile(new StaticTiledMapTile(tx.roomRightFence));

        cr.topLeftFenceTile.setTile(new StaticTiledMapTile(tx.roomTopLeftCornerFence));
        cr.topRightFenceTile.setTile(new StaticTiledMapTile(tx.roomTopRightCornerFence));
        cr.bottomLeftFenceTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftCornerFence));
        cr.bottomRightFenceTile.setTile(new StaticTiledMapTile(tx.roomBottomRightCornerFence));

        cr.topLeftTurnFenceTile.setTile(new StaticTiledMapTile(tx.roomTopLeftTurnFence));
        cr.topRightTurnFenceTile.setTile(new StaticTiledMapTile(tx.roomTopRightTurnFence));
        cr.bottomLeftTurnFenceTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftTurnFence));
        cr.bottomRightTurnFenceTile.setTile(new StaticTiledMapTile(tx.roomBottomRightTurnFence));

        cr.leftFenceTopEndTile.setTile(new StaticTiledMapTile(tx.roomLeftUpEndFence));
        cr.leftFenceBottomEndTile.setTile(new StaticTiledMapTile(tx.roomLeftDownEndFence));
        cr.rightFenceTopEndTile.setTile(new StaticTiledMapTile(tx.roomRightUpEndFence));
        cr.rightFenceBottomEndTile.setTile(new StaticTiledMapTile(tx.roomRightDownEndFence));

        cr.topFenceLeftEndTile.setTile(new StaticTiledMapTile(tx.roomTopLeftEndFence));
        cr.topFenceRightEndTile.setTile(new StaticTiledMapTile(tx.roomTopRightEndFence));
        cr.bottomFenceLeftEndTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftEndFence));
        cr.bottomFenceRightEndTile.setTile(new StaticTiledMapTile(tx.roomBottomRightEndFence));

        cr.topLeftStairTile.setTile(new StaticTiledMapTile(tx.stairTopLeft));
        cr.topRightStairTile.setTile(new StaticTiledMapTile(tx.stairTopRight));
        cr.topStairTile.setTile(new StaticTiledMapTile(tx.stairTop));
        cr.topStair2Tile.setTile(new StaticTiledMapTile(tx.stairTop2));
        cr.bottomStairTile.setTile(new StaticTiledMapTile(tx.stairDown));
        cr.leftCornerStairTile.setTile(new StaticTiledMapTile(tx.stairLeftCorner));
        cr.rightCornerStairTile.setTile(new StaticTiledMapTile(tx.stairRightCorner));
        cr.bottomLeftStairTile.setTile(new StaticTiledMapTile(tx.stairDownLeft));
        cr.bottomRightStairTile.setTile(new StaticTiledMapTile(tx.stairDownRight));
        cr.leftStairTile.setTile(new StaticTiledMapTile(tx.stairLeft));
        cr.rightStairTile.setTile(new StaticTiledMapTile(tx.stairRight));

        cr.upTopLeftStairTile.setTile(new StaticTiledMapTile(tx.stairUpTopLeft));
        cr.upTopRightStairTile.setTile(new StaticTiledMapTile(tx.stairUpTopRight));
        cr.upBottomLeftStairTile.setTile(new StaticTiledMapTile(tx.stairUpBottomLeft));
        cr.upBottomRightStairTile.setTile(new StaticTiledMapTile(tx.stairUpBottomRight));

        cr.waterRimTop.setTile(new StaticTiledMapTile(tx.waterRimTop));
        cr.waterRimBottom.setTile(new StaticTiledMapTile(tx.waterRimBottom));
        cr.waterRimLeft.setTile(new StaticTiledMapTile(tx.waterRimLeft));
        cr.waterRimRight.setTile(new StaticTiledMapTile(tx.waterRimRight));

        cr.blockTile.setTile(new StaticTiledMapTile(tx.block));
        cr.blockWallUpTile.setTile(new StaticTiledMapTile(tx.blockWallUp));
        cr.blockOmegaTile.setTile(new StaticTiledMapTile(tx.blockOmega));
        cr.blockPhiTile.setTile(new StaticTiledMapTile(tx.blockPhi));
        cr.blockDeltaTile.setTile(new StaticTiledMapTile(tx.blockDelta));
        cr.blockSigmaTile.setTile(new StaticTiledMapTile(tx.blockSigma));
        cr.blockLambdaTile.setTile(new StaticTiledMapTile(tx.blockLambda));
        cr.blockGabenTile.setTile(new StaticTiledMapTile(tx.blockGaben));
        cr.blockPiTile.setTile(new StaticTiledMapTile(tx.blockPi));

        cr.doorTopLeftWall.setTile(new StaticTiledMapTile(tx.doorTopLeftWallTexture));
        cr.doorTopRightWall.setTile(new StaticTiledMapTile(tx.doorTopRightWallTexture));
        cr.doorLeftUpperWall.setTile(new StaticTiledMapTile(tx.doorLeftUpperWallTexture));
        cr.doorLeftLowerWall.setTile(new StaticTiledMapTile(tx.doorLeftLowerWallTexture));
        cr.doorRightUpperWall.setTile(new StaticTiledMapTile(tx.doorRightUpperWallTexture));
        cr.doorRightLowerWall.setTile(new StaticTiledMapTile(tx.doorRightLowerWallTexture));
        cr.doorBottomLeftWall.setTile(new StaticTiledMapTile(tx.doorBottomLeftWallTexture));
        cr.doorBottomRightWall.setTile(new StaticTiledMapTile(tx.doorBottomRightWallTexture));

        cr.torchWallLeftTile.setTile(new StaticTiledMapTile(tx.torchLeftTexture));
        cr.torchWallRightTile.setTile(new StaticTiledMapTile(tx.torchRightTexture));
        cr.torchWallUpTile.setTile(new StaticTiledMapTile(tx.torchUpTexture));
        cr.torchWallDownTile.setTile(new StaticTiledMapTile(tx.torchDownTexture));

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


        cr.gateTopLeft.setTile(new StaticTiledMapTile(tx.gateTopLeftTexture));
        cr.gateTopRight.setTile(new StaticTiledMapTile(tx.gateTopRightTexture));
        cr.gateTopLeftOpen.setTile(new StaticTiledMapTile(tx.gateTopLeftOpenTexture));
        cr.gateTopRightOpen.setTile(new StaticTiledMapTile(tx.gateTopRightOpenTexture));

        cr.gateLeftUpper.setTile(new StaticTiledMapTile(tx.gateLeftUpperTexture));
        cr.gateLeftLower.setTile(new StaticTiledMapTile(tx.gateLeftLowerTexture));
        cr.gateLeftUpperOpen.setTile(new StaticTiledMapTile(tx.gateLeftUpperOpenTexture));
        cr.gateLeftLowerOpen.setTile(new StaticTiledMapTile(tx.gateLeftLowerOpenTexture));

        cr.gateRightUpper.setTile(new StaticTiledMapTile(tx.gateRightUpperTexture));
        cr.gateRightLower.setTile(new StaticTiledMapTile(tx.gateRightLowerTexture));
        cr.gateRightUpperOpen.setTile(new StaticTiledMapTile(tx.gateRightUpperOpenTexture));
        cr.gateRightLowerOpen.setTile(new StaticTiledMapTile(tx.gateRightLowerOpenTexture));

        cr.gateBottomLeft.setTile(new StaticTiledMapTile(tx.gateBottomLeftTexture));
        cr.gateBottomRight.setTile(new StaticTiledMapTile(tx.gateBottomRightTexture));
        cr.gateBottomLeftOpen.setTile(new StaticTiledMapTile(tx.gateBottomLeftOpenTexture));
        cr.gateBottomRightOpen.setTile(new StaticTiledMapTile(tx.gateBottomRightOpenTexture));


        /*
        cr.lockUp.setTile(new StaticTiledMapTile(tx.lockUpTexture));
        cr.lockDown.setTile(new StaticTiledMapTile(tx.lockDownTexture));
        cr.lockLeft.setTile(new StaticTiledMapTile(tx.lockLeftTexture));
        cr.lockRight.setTile(new StaticTiledMapTile(tx.lockRightTexture));
         */

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
