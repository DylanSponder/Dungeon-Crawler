package com.mygdx.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.level.objects.Torch;

import static com.mygdx.game.DungeonCrawler.rayHandler;
import static com.mygdx.game.DungeonCrawler.torches;

public class CreateCorridor {
    private BodyFactory bf;
    private CreateCell cr;
    private InitLevel init;

    public void CreateCorridor (TiledMapTileLayer layer, World world, float doorX, float doorY, boolean upDown){
        if (upDown) {
            for(int i=0;i<4;i++) {
                int doorXasInt = (int) doorX;
                int doorYAsInt = (int) doorY;

                if (i == 1) {
                    TiledMapTileLayer.Cell newLeftCorridorWallCell;
                    newLeftCorridorWallCell = GenerateLevel.init.cr.torchWallLeftTile;
                    Body newLeftCorridorWall = GenerateLevel.init.bf.createWall(world, (doorX * 16) + 15*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16);
                    layer.setCell(doorXasInt+15, doorYAsInt-i, newLeftCorridorWallCell);
                    newLeftCorridorWall.setUserData("Wall");

                    TiledMapTileLayer.Cell newLeftCorridorFloorCell;
                    newLeftCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                    layer.setCell(doorXasInt+16, doorYAsInt-i, newLeftCorridorFloorCell);

                    TiledMapTileLayer.Cell newRightCorridorFloorCell;
                    newRightCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                    layer.setCell(doorXasInt+17, doorYAsInt-i, newRightCorridorFloorCell);

                    TiledMapTileLayer.Cell newRightCorridorWallCell;
                    newRightCorridorWallCell = GenerateLevel.init.cr.torchWallRightTile;
                    Body newRightCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX+3) * 16) + 15*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16);
                    layer.setCell(doorXasInt+18, doorYAsInt-i, newRightCorridorWallCell);
                    newRightCorridorWall.setUserData("Wall");
                }
                else {
                    TiledMapTileLayer.Cell newLeftCorridorWallCell;
                    newLeftCorridorWallCell = GenerateLevel.init.cr.leftWallTile;
                    Body newLeftCorridorWall = GenerateLevel.init.bf.createWall(world, (doorX * 16) + 15*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16);
                    layer.setCell(doorXasInt+15, doorYAsInt-i, newLeftCorridorWallCell);
                    newLeftCorridorWall.setUserData("Wall");

                    TiledMapTileLayer.Cell newLeftCorridorFloorCell;
                    newLeftCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                    layer.setCell(doorXasInt+16, doorYAsInt-i, newLeftCorridorFloorCell);

                    TiledMapTileLayer.Cell newRightCorridorFloorCell;
                    newRightCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                    layer.setCell(doorXasInt+17, doorYAsInt-i, newRightCorridorFloorCell);

                    TiledMapTileLayer.Cell newRightCorridorWallCell;
                    newRightCorridorWallCell = GenerateLevel.init.cr.rightWallTile;
                    Body newRightCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX+3) * 16) + 15*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16);
                    layer.setCell(doorXasInt+18, doorYAsInt-i, newRightCorridorWallCell);
                    newRightCorridorWall.setUserData("Wall");
                }
            }
        }
        else if (!upDown) {
            for(int i=0;i<5;i++) {
                int doorXasInt = (int) doorX;
                int doorYAsInt = (int) doorY;
                if (i == 1) {
                    for (int iTop = 0; iTop<4; iTop++) {
                        if (iTop == 1) {
                            TiledMapTileLayer.Cell newTopCorridorWallCell;
                            newTopCorridorWallCell = GenerateLevel.init.cr.torchWallUpTile;
                            Torch torU = new Torch(rayHandler, world,  ((doorX + iTop) * 16) + 15*16 + 8,  ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                            torches.add(torU);
                            torU.createTorch(1);
                            Body newTopCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iTop) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                            layer.setCell(doorXasInt+15+iTop, doorYAsInt-i+1, newTopCorridorWallCell);
                            newTopCorridorWall.setUserData("Wall");
                        } else {
                            TiledMapTileLayer.Cell newTopCorridorWallCell;
                            newTopCorridorWallCell = GenerateLevel.init.cr.topWallTile;
                            Body newTopCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iTop) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                            layer.setCell(doorXasInt+15+iTop, doorYAsInt-i+1, newTopCorridorWallCell);
                            newTopCorridorWall.setUserData("Wall");
                        }
                    }
                }
                if (i == 2){
                    for (int iMiddle = 0; iMiddle<4; iMiddle++) {
                        TiledMapTileLayer.Cell newMiddleFloorCorridorCell;
                        newMiddleFloorCorridorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt+15+iMiddle, doorYAsInt-i+1, newMiddleFloorCorridorCell);
                    }
                }
                if (i == 3){
                    for (int iMiddle = 0; iMiddle<4; iMiddle++) {
                        TiledMapTileLayer.Cell newMiddleFloorCorridorCell;
                        newMiddleFloorCorridorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt+15+iMiddle, doorYAsInt-i+1, newMiddleFloorCorridorCell);
                    }
                }
                if (i == 4){
                    for (int iBottom = 0; iBottom<4; iBottom++) {
                        TiledMapTileLayer.Cell newBottomCorridorWallCell;
                        newBottomCorridorWallCell = GenerateLevel.init.cr.bottomWallTile;
                        Body newBottomCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iBottom) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                        layer.setCell(doorXasInt+15+iBottom, doorYAsInt-i+1, newBottomCorridorWallCell);
                        newBottomCorridorWall.setUserData("Wall");
                    }
                }
            }
        }
    }
}
