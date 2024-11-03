package com.mygdx.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Random;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.level.objects.Fire;
import com.mygdx.game.level.objects.Roof;
import com.mygdx.game.level.objects.Torch;

import static com.mygdx.game.DungeonCrawler.*;

public class CreateCorridor {
    private BodyFactory bf;
    private CreateCell cr;
    private InitLevel init;
    private int rand1, rand2, rand3, rand4;

    public void CreateCorridor (TiledMapTileLayer layer, World world, float doorX, float doorY, boolean upDown){
        rand1 = Random.randomInt(2, 1);
        if (upDown) {

            if (rand1 == 2) {
                {
                for(int i=0;i<4;i++) {
                    int doorXasInt = (int) doorX;
                    int doorYAsInt = (int) doorY;

                    if (i == 0) {
                        TiledMapTileLayer.Cell newLeftCorridorWallCell;
                        newLeftCorridorWallCell = GenerateLevel.init.cr.leftFenceTopEndTile;
                        Body newLeftCorridorWall = GenerateLevel.init.bf.createWall(world, (doorX * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i * 16);
                        layer.setCell(doorXasInt + 15, doorYAsInt - i, newLeftCorridorWallCell);
                        newLeftCorridorWall.setUserData("Wall");

                        TiledMapTileLayer.Cell newLeftCorridorFloorCell;
                        newLeftCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 16, doorYAsInt - i, newLeftCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorFloorCell;
                        newRightCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 17, doorYAsInt - i, newRightCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorWallCell;
                        newRightCorridorWallCell = GenerateLevel.init.cr.rightFenceTopEndTile;
                        Body newRightCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + 3) * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i * 16);
                        layer.setCell(doorXasInt + 18, doorYAsInt - i, newRightCorridorWallCell);
                        newRightCorridorWall.setUserData("Wall");
                        }
                    else if (i == 3) {
                        TiledMapTileLayer.Cell newLeftCorridorWallCell;
                        newLeftCorridorWallCell = GenerateLevel.init.cr.leftFenceBottomEndTile;
                        Body newLeftCorridorWall = GenerateLevel.init.bf.createWall(world, (doorX * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i * 16);
                        layer.setCell(doorXasInt + 15, doorYAsInt - i, newLeftCorridorWallCell);
                        newLeftCorridorWall.setUserData("Wall");

                        TiledMapTileLayer.Cell newLeftCorridorFloorCell;
                        newLeftCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 16, doorYAsInt - i, newLeftCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorFloorCell;
                        newRightCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 17, doorYAsInt - i, newRightCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorWallCell;
                        newRightCorridorWallCell = GenerateLevel.init.cr.rightFenceBottomEndTile;
                        Body newRightCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + 3) * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i * 16);
                        layer.setCell(doorXasInt + 18, doorYAsInt - i, newRightCorridorWallCell);
                        newRightCorridorWall.setUserData("Wall");

                        int rand = Random.randomInt(2,1);

                        int rand2 = Random.randomInt(2,1);

                        if (rand == 2) {
                            if (rand2 == 2) {
                                Roof r = new Roof(world, ((doorX+3) * 16) + 12*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16 - 16, upDown,false);
                                roofs.add(r);
                            } else {
                                Roof r = new Roof(world, ((doorX+3) * 16) + 12*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16 - 16, upDown,true);
                                roofs.add(r);
                            }

                            }
                        } else {

                        TiledMapTileLayer.Cell newLeftCorridorWallCell;
                        newLeftCorridorWallCell = GenerateLevel.init.cr.leftFenceTile;
                        Body newLeftCorridorWall = GenerateLevel.init.bf.createWall(world, (doorX * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i * 16);
                        layer.setCell(doorXasInt + 15, doorYAsInt - i, newLeftCorridorWallCell);
                        newLeftCorridorWall.setUserData("Wall");

                        TiledMapTileLayer.Cell newLeftCorridorFloorCell;
                        newLeftCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 16, doorYAsInt - i, newLeftCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorFloorCell;
                        newRightCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 17, doorYAsInt - i, newRightCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorWallCell;
                        newRightCorridorWallCell = GenerateLevel.init.cr.rightFenceTile;
                        Body newRightCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + 3) * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i * 16);
                        layer.setCell(doorXasInt + 18, doorYAsInt - i, newRightCorridorWallCell);
                        newRightCorridorWall.setUserData("Wall");
                        }
                    }
                }

            } else {

                for (int i2 = 0; i2 < 4; i2++) {
                    int doorXasInt = (int) doorX;
                    int doorYAsInt = (int) doorY;

                    if (i2 == 0) {
                        TiledMapTileLayer.Cell newLeftCorridorWallCell;
                        newLeftCorridorWallCell = GenerateLevel.init.cr.torchWallLeftTile;
                        Body newLeftCorridorWall = GenerateLevel.init.bf.createWall(world, (doorX * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i2 * 16);
                        int rand = Random.randomInt(2, 1);

                        if (rand == 2) {
                            Fire fL = new Fire(world, rayHandler, (((doorX) * 16) + 16 * 16) + 6 - 4 - 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 4, false, 0f, 3, false);
                            fires.add(fL);
                            fL.createFire(new Color(0.25f, 0.20f, 0, 0.7f), 60);
                        }

                        layer.setCell(doorXasInt + 15, doorYAsInt - i2, newLeftCorridorWallCell);
                        newLeftCorridorWall.setUserData("Wall");

                        TiledMapTileLayer.Cell newLeftCorridorFloorCell;
                        newLeftCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 16, doorYAsInt - i2, newLeftCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorFloorCell;
                        newRightCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 17, doorYAsInt - i2, newRightCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorWallCell;
                        newRightCorridorWallCell = GenerateLevel.init.cr.torchWallRightTile;
                        Body newRightCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + 3) * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i2 * 16);

                        if (rand == 2) {
                            Fire fR = new Fire(world, rayHandler, (((doorX) * 16) + 16 * 16) + 6 - 4 - 16 + 45, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 4, false, 0f, 3, false);
                            fires.add(fR);
                            fR.createFire(new Color(0.25f, 0.20f, 0, 0.7f), 60);
                        }

                        layer.setCell(doorXasInt + 18, doorYAsInt - i2, newRightCorridorWallCell);
                        newRightCorridorWall.setUserData("Wall");
                        if (i2 == 3) {

                            int randR2 = Random.randomInt(2,1);

                            if (rand == 2) {
                                if (randR2 == 2) {
                                    Roof r = new Roof(world, ((doorX + 3) * 16) + 12 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i2 * 16 - 16, upDown,false);
                                    roofs.add(r);
                                } else {
                                    Roof r = new Roof(world, ((doorX + 3) * 16) + 12 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i2 * 16 - 16, upDown,true);
                                    roofs.add(r);
                                }
                            }
                    }


                    } else {
                        TiledMapTileLayer.Cell newLeftCorridorWallCell;
                        newLeftCorridorWallCell = GenerateLevel.init.cr.leftWallTile;
                        Body newLeftCorridorWall = GenerateLevel.init.bf.createWall(world, (doorX * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i2 * 16);
                        layer.setCell(doorXasInt + 15, doorYAsInt - i2, newLeftCorridorWallCell);
                        newLeftCorridorWall.setUserData("Wall");

                        TiledMapTileLayer.Cell newLeftCorridorFloorCell;
                        newLeftCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 16, doorYAsInt - i2, newLeftCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorFloorCell;
                        newRightCorridorFloorCell = GenerateLevel.init.cr.middleFloorTile;
                        layer.setCell(doorXasInt + 17, doorYAsInt - i2, newRightCorridorFloorCell);

                        TiledMapTileLayer.Cell newRightCorridorWallCell;
                        newRightCorridorWallCell = GenerateLevel.init.cr.rightWallTile;
                        Body newRightCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + 3) * 16) + 15 * 16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i2 * 16);
                        layer.setCell(doorXasInt + 18, doorYAsInt - i2, newRightCorridorWallCell);
                        newRightCorridorWall.setUserData("Wall");
                    }


                }
            }
        }
        else if (!upDown) {
            rand2 = Random.randomInt(2,1);
            rand3 = Random.randomInt(2,1);
            rand4 = Random.randomInt(2,1);
            if (rand4 == 2) {
                for(int i=0;i<5;i++) {

                    int doorXasInt = (int) doorX;
                    int doorYAsInt = (int) doorY;
                    if (i == 1) {
                        for (int iTop = 0; iTop<4; iTop++) {
                            if (iTop == 0) {
                                TiledMapTileLayer.Cell newTopCorridorWallCell;
                                newTopCorridorWallCell = GenerateLevel.init.cr.torchWallUpTile;

                                if (rand2 == 2) {
                                    Torch torU = new Torch(rayHandler, world,  ((doorX + iTop) * 16) + 15*16 + 8,  ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16) + 8);
                                    torches.add(torU);
                                    torU.createTorch(1);
                                    Fire fU = new Fire(world,rayHandler,(((doorX + iTop) * 16) + 16 * 16) + 6 - 4 - 17,(doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) + 8 - 5, false,0f, 3, false);
                                    fires.add(fU);
                                    fU.createFire(new Color(0.25f,0.20f,0,0.7f),60);
                                }

                                Body newTopCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iTop) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                                layer.setCell(doorXasInt+15+iTop, doorYAsInt-i+1, newTopCorridorWallCell);
                                newTopCorridorWall.setUserData("Wall");
                            } else if (iTop == 3) {

                                int randR = Random.randomInt(4,1);
                                if (rand3 == 2) {
                                    if (randR == 4) {
                                        Roof r = new Roof(world, ((doorX+3) * 16) + 17*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16 - 32 + 1, upDown,true);
                                        roofs.add(r);
                                    } else {
                                        Roof r = new Roof(world, ((doorX+3) * 16) + 17*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16 - 32 + 1, upDown,false);
                                        roofs.add(r);
                                    }
                                }
                                TiledMapTileLayer.Cell newTopCorridorWallCell;
                                newTopCorridorWallCell = GenerateLevel.init.cr.topWallTile;
                                Body newTopCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iTop) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                                layer.setCell(doorXasInt+15+iTop, doorYAsInt-i+1, newTopCorridorWallCell);
                                newTopCorridorWall.setUserData("Wall");
                            }


                            else {
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
                            if (iBottom == 0) {


                                if (rand2 == 2) {
                                    Torch torD = new Torch(rayHandler, world,  ((doorX + iBottom) * 16) + 15*16 + 8,  ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16) + 8);
                                    torches.add(torD);
                                    torD.createTorch(1);
                                    Fire fD = new Fire(world,rayHandler, ((doorX + iBottom) * 16) + 15*16 + 16,((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16) + 15, false,0f, 3, true);
                                    fires.add(fD);
                                    fD.createFire(new Color(0.25f,0.20f,0,0.7f),60);
                                }

                                TiledMapTileLayer.Cell newBottomCorridorWallCell;
                                newBottomCorridorWallCell = GenerateLevel.init.cr.torchWallDownTile;
                                Body newBottomCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iBottom) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                                layer.setCell(doorXasInt+15+iBottom, doorYAsInt-i+1, newBottomCorridorWallCell);
                                newBottomCorridorWall.setUserData("Wall");

                            } else {
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
            else {
                for(int i=0;i<5;i++) {

                    int doorXasInt = (int) doorX;
                    int doorYAsInt = (int) doorY;
                    if (i == 1) {
                        for (int iTop = 0; iTop<4; iTop++) {
                            if (iTop == 0) {
                                TiledMapTileLayer.Cell newTopCorridorWallCell;
                                newTopCorridorWallCell = GenerateLevel.init.cr.topFenceLeftEndTile;

                                Body newTopCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iTop) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                                layer.setCell(doorXasInt+15+iTop, doorYAsInt-i+1, newTopCorridorWallCell);
                                newTopCorridorWall.setUserData("Wall");
                            } else if (iTop == 3) {

                                int randR = Random.randomInt(4,1);

                                if (rand3 == 2) {
                                    if (randR == 4) {
                                        Roof r = new Roof(world, ((doorX+3) * 16) + 17*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16 - 32 + 1, upDown,true);
                                        roofs.add(r);
                                    }
                                    else {
                                        Roof r = new Roof(world, ((doorX+3) * 16) + 17*16, (doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - i*16 - 32 + 1, upDown,false);
                                        roofs.add(r);
                                    }
                                }
                                TiledMapTileLayer.Cell newTopCorridorWallCell;
                                newTopCorridorWallCell = GenerateLevel.init.cr.topFenceRightEndTile;
                                Body newTopCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iTop) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                                layer.setCell(doorXasInt+15+iTop, doorYAsInt-i+1, newTopCorridorWallCell);
                                newTopCorridorWall.setUserData("Wall");
                            }


                            else {
                                TiledMapTileLayer.Cell newTopCorridorWallCell;
                                newTopCorridorWallCell = GenerateLevel.init.cr.topFenceTile;
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
                            if (iBottom == 0) {
                                TiledMapTileLayer.Cell newBottomCorridorWallCell;
                                newBottomCorridorWallCell = GenerateLevel.init.cr.bottomFenceLeftEndTile;
                                Body newBottomCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iBottom) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                                layer.setCell(doorXasInt+15+iBottom, doorYAsInt-i+1, newBottomCorridorWallCell);
                                newBottomCorridorWall.setUserData("Wall");

                            } else if (iBottom == 3) {
                                TiledMapTileLayer.Cell newBottomCorridorWallCell;
                                newBottomCorridorWallCell = GenerateLevel.init.cr.bottomFenceRightEndTile;
                                Body newBottomCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iBottom) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                                layer.setCell(doorXasInt+15+iBottom, doorYAsInt-i+1, newBottomCorridorWallCell);
                                newBottomCorridorWall.setUserData("Wall");

                            } else {
                                TiledMapTileLayer.Cell newBottomCorridorWallCell;
                                newBottomCorridorWallCell = GenerateLevel.init.cr.bottomFenceTile;
                                Body newBottomCorridorWall = GenerateLevel.init.bf.createWall(world, ((doorX + iBottom) * 16) + 15*16, ((doorY * 16 + Gdx.graphics.getHeight() / 30 - 16) - (i-1)*16));
                                layer.setCell(doorXasInt+15+iBottom, doorYAsInt-i+1, newBottomCorridorWallCell);
                                newBottomCorridorWall.setUserData("Wall");

                            }
                        }
                    }
                }
            }
        }
    }
}
