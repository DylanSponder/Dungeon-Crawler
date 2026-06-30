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
    public TiledMapTileLayer.Cell innerWallUpTile;
    public TiledMapTileLayer.Cell innerWallDownTile;
    public TiledMapTileLayer.Cell innerWallLeftTile;
    public TiledMapTileLayer.Cell innerWallRightTile;

    public TiledMapTileLayer.Cell largeWallUpTile;
    public TiledMapTileLayer.Cell largeWallDownTile;
    public TiledMapTileLayer.Cell largeWallLeftTile;
    public TiledMapTileLayer.Cell largeWallRightTile;

    public TiledMapTileLayer.Cell largeWallTopLeftTile;
    public TiledMapTileLayer.Cell largeWallTopRightTile;
    public TiledMapTileLayer.Cell largeWallBottomLeftTile;
    public TiledMapTileLayer.Cell largeWallBottomRightTile;

    public TiledMapTileLayer.Cell largeWallTopLeftTurnTile;
    public TiledMapTileLayer.Cell largeWallTopRightTurnTile;
    public TiledMapTileLayer.Cell largeWallBottomLeftTurnTile;
    public TiledMapTileLayer.Cell largeWallBottomRightTurnTile;

    public TiledMapTileLayer.Cell airTile;

    public TiledMapTileLayer.Cell mosaicTridentTile;
    public TiledMapTileLayer.Cell mosaicBullTile;
    //
    //
    //
    //

    public TiledMapTileLayer.Cell pitTile;
    public TiledMapTileLayer.Cell pitFloorTile;
    public TiledMapTileLayer.Cell pitFloor2Tile;
    public TiledMapTileLayer.Cell pitStairsTile;
    public TiledMapTileLayer.Cell pitLeftTile;
    public TiledMapTileLayer.Cell pitRightTile;
    public TiledMapTileLayer.Cell pitBottomTile;

    public TiledMapTileLayer.Cell innerWallTLCornerTile;
    public TiledMapTileLayer.Cell innerWallTRCornerTile;
    public TiledMapTileLayer.Cell innerWallBLCornerTile;
    public TiledMapTileLayer.Cell innerWallBRCornerTile;

    public TiledMapTileLayer.Cell innerWallTLTurnTile;
    public TiledMapTileLayer.Cell innerWallTRTurnTile;
    public TiledMapTileLayer.Cell innerWallBLTurnTile;
    public TiledMapTileLayer.Cell innerWallBRTurnTile;

    public TiledMapTileLayer.Cell innerWallTLTurn2Tile;
    public TiledMapTileLayer.Cell innerWallTRTurn2Tile;
    public TiledMapTileLayer.Cell innerWallBLTurn2Tile;
    public TiledMapTileLayer.Cell innerWallBRTurn2Tile;

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
    public TiledMapTileLayer.Cell gateTopLeft;
    public TiledMapTileLayer.Cell gateTopRight;
    public TiledMapTileLayer.Cell gateTopLeftOpen;
    public TiledMapTileLayer.Cell gateTopRightOpen;
    public TiledMapTileLayer.Cell gateLeftUpper;
    public TiledMapTileLayer.Cell gateLeftLower;
    public TiledMapTileLayer.Cell gateLeftUpperOpen;
    public TiledMapTileLayer.Cell gateLeftLowerOpen;
    public TiledMapTileLayer.Cell gateRightUpper;
    public TiledMapTileLayer.Cell gateRightLower;
    public TiledMapTileLayer.Cell gateRightUpperOpen;
    public TiledMapTileLayer.Cell gateRightLowerOpen;
    public TiledMapTileLayer.Cell gateBottomLeft;
    public TiledMapTileLayer.Cell gateBottomRight;
    public TiledMapTileLayer.Cell gateBottomLeftOpen;
    public TiledMapTileLayer.Cell gateBottomRightOpen;
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
    public TiledMapTileLayer.Cell topStair2Tile;
    public TiledMapTileLayer.Cell bottomStairTile;
    public TiledMapTileLayer.Cell leftCornerStairTile;
    public TiledMapTileLayer.Cell rightCornerStairTile;
    public TiledMapTileLayer.Cell bottomLeftStairTile;
    public TiledMapTileLayer.Cell bottomRightStairTile;
    public TiledMapTileLayer.Cell leftStairTile;
    public TiledMapTileLayer.Cell rightStairTile;

    public TiledMapTileLayer.Cell upTopLeftStairTile;
    public TiledMapTileLayer.Cell upTopRightStairTile;
    public TiledMapTileLayer.Cell upBottomLeftStairTile;
    public TiledMapTileLayer.Cell upBottomRightStairTile;

    public TiledMapTileLayer.Cell waterRimTop;
    public TiledMapTileLayer.Cell waterRimBottom;
    public TiledMapTileLayer.Cell waterRimLeft;
    public TiledMapTileLayer.Cell waterRimRight;

    public void InitializeCells() {
        airTile = new TiledMapTileLayer.Cell();

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

        mosaicTridentTile = new TiledMapTileLayer.Cell();
        mosaicBullTile = new TiledMapTileLayer.Cell();

        pitTile = new TiledMapTileLayer.Cell();
        pitFloorTile = new TiledMapTileLayer.Cell();
        pitFloor2Tile = new TiledMapTileLayer.Cell();
        pitStairsTile = new TiledMapTileLayer.Cell();
        pitLeftTile = new TiledMapTileLayer.Cell();
        pitRightTile = new TiledMapTileLayer.Cell();
        pitBottomTile = new TiledMapTileLayer.Cell();

        largeWallUpTile = new TiledMapTileLayer.Cell();
        largeWallDownTile = new TiledMapTileLayer.Cell();
        largeWallLeftTile = new TiledMapTileLayer.Cell();
        largeWallRightTile = new TiledMapTileLayer.Cell();

        largeWallTopLeftTile = new TiledMapTileLayer.Cell();
        largeWallTopRightTile = new TiledMapTileLayer.Cell();
        largeWallBottomLeftTile = new TiledMapTileLayer.Cell();
        largeWallBottomRightTile = new TiledMapTileLayer.Cell();

        largeWallTopLeftTurnTile = new TiledMapTileLayer.Cell();
        largeWallTopRightTurnTile = new TiledMapTileLayer.Cell();
        largeWallBottomLeftTurnTile = new TiledMapTileLayer.Cell();
        largeWallBottomRightTurnTile = new TiledMapTileLayer.Cell();

        innerWallUpTile = new TiledMapTileLayer.Cell();
        innerWallDownTile = new TiledMapTileLayer.Cell();
        innerWallLeftTile = new TiledMapTileLayer.Cell();
        innerWallRightTile = new TiledMapTileLayer.Cell();

        innerWallTLCornerTile = new TiledMapTileLayer.Cell();
        innerWallTRCornerTile = new TiledMapTileLayer.Cell();
        innerWallBLCornerTile = new TiledMapTileLayer.Cell();
        innerWallBRCornerTile = new TiledMapTileLayer.Cell();

        innerWallTLTurnTile = new TiledMapTileLayer.Cell();
        innerWallTRTurnTile = new TiledMapTileLayer.Cell();
        innerWallBLTurnTile = new TiledMapTileLayer.Cell();
        innerWallBRTurnTile = new TiledMapTileLayer.Cell();

        innerWallTLTurn2Tile = new TiledMapTileLayer.Cell();
        innerWallTRTurn2Tile = new TiledMapTileLayer.Cell();
        innerWallBLTurn2Tile = new TiledMapTileLayer.Cell();
        innerWallBRTurn2Tile = new TiledMapTileLayer.Cell();

        innerWallUpTile = new TiledMapTileLayer.Cell();
        innerWallDownTile = new TiledMapTileLayer.Cell();
        innerWallLeftTile = new TiledMapTileLayer.Cell();
        innerWallRightTile = new TiledMapTileLayer.Cell();

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
        topStair2Tile = new TiledMapTileLayer.Cell();
        bottomStairTile = new TiledMapTileLayer.Cell();
        leftCornerStairTile = new TiledMapTileLayer.Cell();
        rightCornerStairTile = new TiledMapTileLayer.Cell();
        bottomLeftStairTile = new TiledMapTileLayer.Cell();
        bottomRightStairTile = new TiledMapTileLayer.Cell();
        leftStairTile = new TiledMapTileLayer.Cell();
        rightStairTile = new TiledMapTileLayer.Cell();

        upTopLeftStairTile = new TiledMapTileLayer.Cell();
        upTopRightStairTile = new TiledMapTileLayer.Cell();
        upBottomLeftStairTile = new TiledMapTileLayer.Cell();
        upBottomRightStairTile = new TiledMapTileLayer.Cell();

        waterRimTop = new TiledMapTileLayer.Cell();
        waterRimBottom = new TiledMapTileLayer.Cell();
        waterRimLeft = new TiledMapTileLayer.Cell();
        waterRimRight = new TiledMapTileLayer.Cell();

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

        gateTopLeft = new TiledMapTileLayer.Cell();
        gateTopRight = new TiledMapTileLayer.Cell();
        gateTopLeftOpen = new TiledMapTileLayer.Cell();
        gateTopRightOpen = new TiledMapTileLayer.Cell();

        gateLeftUpper = new TiledMapTileLayer.Cell();
        gateLeftLower = new TiledMapTileLayer.Cell();
        gateLeftUpperOpen = new TiledMapTileLayer.Cell();
        gateLeftLowerOpen = new TiledMapTileLayer.Cell();

        gateRightUpper = new TiledMapTileLayer.Cell();
        gateRightLower = new TiledMapTileLayer.Cell();
        gateRightUpperOpen = new TiledMapTileLayer.Cell();
        gateRightLowerOpen = new TiledMapTileLayer.Cell();

        gateBottomLeft = new TiledMapTileLayer.Cell();
        gateBottomRight = new TiledMapTileLayer.Cell();
        gateBottomLeftOpen = new TiledMapTileLayer.Cell();
        gateBottomRightOpen = new TiledMapTileLayer.Cell();

    }
}
