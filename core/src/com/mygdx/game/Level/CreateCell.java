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
    }
}
