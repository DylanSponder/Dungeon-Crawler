package com.mygdx.game.level;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CreateCell {
    public TiledMapTileLayer.Cell middleFloorTile;
    public TiledMapTileLayer.Cell middleFloorTile2;
    public TiledMapTileLayer.Cell middleFloor2Tile;
    public TiledMapTileLayer.Cell middleFloor3Tile;
    public TiledMapTileLayer.Cell decorFloorUpTile;
    public TiledMapTileLayer.Cell decorFloorDownTile;
    public TiledMapTileLayer.Cell decorFloorLeftTile;
    public TiledMapTileLayer.Cell decorFloorRightTile;
    public TiledMapTileLayer.Cell decorFloorTopLeftTile;
    public TiledMapTileLayer.Cell decorFloorTopRightTile;
    public TiledMapTileLayer.Cell decorFloorBottomLeftTile;
    public TiledMapTileLayer.Cell decorFloorBottomRightTile;
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
    public TiledMapTileLayer.Cell lockUp;
    public TiledMapTileLayer.Cell lockDown;
    public TiledMapTileLayer.Cell lockLeft;
    public TiledMapTileLayer.Cell lockRight;
    public TiledMapTileLayer.Cell tutorialTile;
    public TiledMapTileLayer.Cell torchWallLeftTile;
    public TiledMapTileLayer.Cell torchWallRightTile;
    public TiledMapTileLayer.Cell torchWallUpTile;
    public TiledMapTileLayer.Cell torchWallDownTile;

    public TiledMapTileLayer.Cell blockTile;
    public TiledMapTileLayer.Cell blockWallUpTile;
    public TiledMapTileLayer.Cell blockOmegaTile;
    public TiledMapTileLayer.Cell blockPhiTile;
    public TiledMapTileLayer.Cell blockDeltaTile;
    public TiledMapTileLayer.Cell blockSigmaTile;
    public TiledMapTileLayer.Cell blockLambdaTile;
    public TiledMapTileLayer.Cell blockGabenTile;
    public TiledMapTileLayer.Cell blockPiTile;

    public TiledMapTileLayer.Cell topLeftFenceTile;
    public TiledMapTileLayer.Cell topRightFenceTile;
    public TiledMapTileLayer.Cell bottomLeftFenceTile;
    public TiledMapTileLayer.Cell bottomRightFenceTile;

    public TiledMapTileLayer.Cell topLeftTurnFenceTile;
    public TiledMapTileLayer.Cell topRightTurnFenceTile;
    public TiledMapTileLayer.Cell bottomLeftTurnFenceTile;
    public TiledMapTileLayer.Cell bottomRightTurnFenceTile;

    public TiledMapTileLayer.Cell topFenceTile;
    public TiledMapTileLayer.Cell bottomFenceTile;
    public TiledMapTileLayer.Cell leftFenceTile;
    public TiledMapTileLayer.Cell rightFenceTile;

    public TiledMapTileLayer.Cell topFenceLeftEndTile;
    public TiledMapTileLayer.Cell topFenceRightEndTile;
    public TiledMapTileLayer.Cell bottomFenceLeftEndTile;
    public TiledMapTileLayer.Cell bottomFenceRightEndTile;

    public TiledMapTileLayer.Cell leftFenceTopEndTile;
    public TiledMapTileLayer.Cell leftFenceBottomEndTile;
    public TiledMapTileLayer.Cell rightFenceTopEndTile;
    public TiledMapTileLayer.Cell rightFenceBottomEndTile;

    public TiledMapTileLayer.Cell topLeftStairTile;
    public TiledMapTileLayer.Cell topRightStairTile;
    public TiledMapTileLayer.Cell topStairTile;
    public TiledMapTileLayer.Cell bottomStairTile;
    public TiledMapTileLayer.Cell leftCornerStairTile;
    public TiledMapTileLayer.Cell rightCornerStairTile;
    public TiledMapTileLayer.Cell bottomLeftStairTile;
    public TiledMapTileLayer.Cell bottomRightStairTile;
    public TiledMapTileLayer.Cell leftStairTile;
    public TiledMapTileLayer.Cell rightStairTile;


    public void InitializeCells() {
        middleFloorTile = new TiledMapTileLayer.Cell();
        middleFloorTile2 = new TiledMapTileLayer.Cell();
        middleFloor2Tile = new TiledMapTileLayer.Cell();
        middleFloor3Tile = new TiledMapTileLayer.Cell();
        decorFloorUpTile = new TiledMapTileLayer.Cell();
        decorFloorDownTile = new TiledMapTileLayer.Cell();
        decorFloorLeftTile = new TiledMapTileLayer.Cell();
        decorFloorRightTile = new TiledMapTileLayer.Cell();
        decorFloorTopLeftTile = new TiledMapTileLayer.Cell();
        decorFloorTopRightTile = new TiledMapTileLayer.Cell();
        decorFloorBottomLeftTile = new TiledMapTileLayer.Cell();
        decorFloorBottomRightTile = new TiledMapTileLayer.Cell();
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
        tutorialTile = new TiledMapTileLayer.Cell();

        topFenceTile = new TiledMapTileLayer.Cell();
        bottomFenceTile = new TiledMapTileLayer.Cell();
        leftFenceTile = new TiledMapTileLayer.Cell();
        rightFenceTile = new TiledMapTileLayer.Cell();

        topLeftFenceTile = new TiledMapTileLayer.Cell();
        topRightFenceTile = new TiledMapTileLayer.Cell();
        bottomLeftFenceTile = new TiledMapTileLayer.Cell();
        bottomRightFenceTile = new TiledMapTileLayer.Cell();

        topLeftTurnFenceTile = new TiledMapTileLayer.Cell();
        topRightTurnFenceTile = new TiledMapTileLayer.Cell();
        bottomLeftTurnFenceTile = new TiledMapTileLayer.Cell();
        bottomRightTurnFenceTile = new TiledMapTileLayer.Cell();

        topFenceLeftEndTile = new TiledMapTileLayer.Cell();
        topFenceRightEndTile = new TiledMapTileLayer.Cell();
        bottomFenceLeftEndTile = new TiledMapTileLayer.Cell();
        bottomFenceRightEndTile = new TiledMapTileLayer.Cell();

        leftFenceTopEndTile = new TiledMapTileLayer.Cell();
        leftFenceBottomEndTile = new TiledMapTileLayer.Cell();
        rightFenceTopEndTile = new TiledMapTileLayer.Cell();
        rightFenceBottomEndTile = new TiledMapTileLayer.Cell();

        topLeftStairTile = new TiledMapTileLayer.Cell();
        topRightStairTile = new TiledMapTileLayer.Cell();
        topStairTile = new TiledMapTileLayer.Cell();
        bottomStairTile = new TiledMapTileLayer.Cell();
        leftCornerStairTile = new TiledMapTileLayer.Cell();
        rightCornerStairTile = new TiledMapTileLayer.Cell();
        bottomLeftStairTile = new TiledMapTileLayer.Cell();
        bottomRightStairTile = new TiledMapTileLayer.Cell();
        leftStairTile = new TiledMapTileLayer.Cell();
        rightStairTile = new TiledMapTileLayer.Cell();

        torchWallLeftTile = new TiledMapTileLayer.Cell();
        torchWallRightTile = new TiledMapTileLayer.Cell();
        torchWallUpTile = new TiledMapTileLayer.Cell();
        torchWallDownTile = new TiledMapTileLayer.Cell();

        blockTile = new TiledMapTileLayer.Cell();
        blockWallUpTile = new TiledMapTileLayer.Cell();
        blockOmegaTile = new TiledMapTileLayer.Cell();
        blockPhiTile = new TiledMapTileLayer.Cell();
        blockDeltaTile = new TiledMapTileLayer.Cell();
        blockSigmaTile = new TiledMapTileLayer.Cell();
        blockLambdaTile = new TiledMapTileLayer.Cell();
        blockGabenTile = new TiledMapTileLayer.Cell();
        blockPiTile = new TiledMapTileLayer.Cell();

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
