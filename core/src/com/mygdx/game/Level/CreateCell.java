package com.mygdx.game.level;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CreateCell {
    public TiledMapTileLayer.Cell middleFloorTile;
    public TiledMapTileLayer.Cell topLeftWallTile;
    public TiledMapTileLayer.Cell topWallTile;
    public TiledMapTileLayer.Cell topRightWallTile;
    public TiledMapTileLayer.Cell leftWallTile;
    public TiledMapTileLayer.Cell rightWallTile;
    public TiledMapTileLayer.Cell bottomLeftWallTile;
    public TiledMapTileLayer.Cell bottomWallTile;
    public TiledMapTileLayer.Cell bottomRightWallTile;
    public TiledMapTileLayer.Cell topLeftTurnTile;
    public TiledMapTileLayer.Cell topRightTurnTile;
    public TiledMapTileLayer.Cell bottomLeftTurnTile;
    public TiledMapTileLayer.Cell bottomRightTurnTile;
    public TiledMapTileLayer.Cell obstacle1;
    public TiledMapTileLayer.Cell obstacle2;
    public TiledMapTileLayer.Cell obstacle3;
    public TiledMapTileLayer.Cell doorTopLeft;
    public TiledMapTileLayer.Cell doorTopRight;
    public TiledMapTileLayer.Cell doorTopLeftOpen;
    public TiledMapTileLayer.Cell doorTopRightOpen;
    public TiledMapTileLayer.Cell doorLeftUpper;
    public TiledMapTileLayer.Cell doorLeftLower;
    public TiledMapTileLayer.Cell doorLeftUpperOpen;
    public TiledMapTileLayer.Cell doorLeftLowerOpen;
    public TiledMapTileLayer.Cell doorRightUpper;
    public TiledMapTileLayer.Cell doorRightLower;
    public TiledMapTileLayer.Cell doorRightUpperOpen;
    public TiledMapTileLayer.Cell doorRightLowerOpen;
    public TiledMapTileLayer.Cell doorBottomLeft;
    public TiledMapTileLayer.Cell doorBottomRight;
    public TiledMapTileLayer.Cell doorBottomLeftOpen;
    public TiledMapTileLayer.Cell doorBottomRightOpen;
    public TiledMapTileLayer.Cell doorTopLeftWall;
    public TiledMapTileLayer.Cell doorTopRightWall;
    public TiledMapTileLayer.Cell doorLeftUpperWall;
    public TiledMapTileLayer.Cell doorLeftLowerWall;
    public TiledMapTileLayer.Cell doorRightUpperWall;
    public TiledMapTileLayer.Cell doorRightLowerWall;
    public TiledMapTileLayer.Cell doorBottomLeftWall;
    public TiledMapTileLayer.Cell doorBottomRightWall;

    public void InitializeCells() {
        middleFloorTile = new TiledMapTileLayer.Cell();
        topLeftWallTile = new TiledMapTileLayer.Cell();
        topWallTile = new TiledMapTileLayer.Cell();
        topRightWallTile = new TiledMapTileLayer.Cell();
        leftWallTile = new TiledMapTileLayer.Cell();
        rightWallTile = new TiledMapTileLayer.Cell();
        bottomLeftWallTile = new TiledMapTileLayer.Cell();
        bottomWallTile = new TiledMapTileLayer.Cell();
        bottomRightWallTile = new TiledMapTileLayer.Cell();
        topLeftTurnTile = new TiledMapTileLayer.Cell();
        topRightTurnTile = new TiledMapTileLayer.Cell();
        bottomLeftTurnTile = new TiledMapTileLayer.Cell();
        bottomRightTurnTile = new TiledMapTileLayer.Cell();
        obstacle1 = new TiledMapTileLayer.Cell();
        obstacle2 = new TiledMapTileLayer.Cell();
        obstacle3 = new TiledMapTileLayer.Cell();

        doorTopLeftWall = new TiledMapTileLayer.Cell();
        doorTopRightWall = new TiledMapTileLayer.Cell();
        doorLeftUpperWall = new TiledMapTileLayer.Cell();
        doorLeftLowerWall = new TiledMapTileLayer.Cell();
        doorRightUpperWall = new TiledMapTileLayer.Cell();
        doorRightLowerWall = new TiledMapTileLayer.Cell();
        doorBottomLeftWall = new TiledMapTileLayer.Cell();
        doorBottomRightWall = new TiledMapTileLayer.Cell();

        doorTopLeft = new TiledMapTileLayer.Cell();
        doorTopRight = new TiledMapTileLayer.Cell();
        doorTopLeftOpen = new TiledMapTileLayer.Cell();
        doorTopRightOpen = new TiledMapTileLayer.Cell();

        doorLeftUpper = new TiledMapTileLayer.Cell();
        doorLeftLower = new TiledMapTileLayer.Cell();
        doorLeftUpperOpen = new TiledMapTileLayer.Cell();
        doorLeftLowerOpen = new TiledMapTileLayer.Cell();

        doorRightUpper = new TiledMapTileLayer.Cell();
        doorRightLower = new TiledMapTileLayer.Cell();
        doorRightUpperOpen = new TiledMapTileLayer.Cell();
        doorRightLowerOpen = new TiledMapTileLayer.Cell();

        doorBottomLeft = new TiledMapTileLayer.Cell();
        doorBottomRight = new TiledMapTileLayer.Cell();
        doorBottomLeftOpen = new TiledMapTileLayer.Cell();
        doorBottomRightOpen = new TiledMapTileLayer.Cell();
    }
}
