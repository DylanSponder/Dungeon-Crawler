package com.mygdx.game.level;

import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenderRules {
    public List<String> translateSymbols(List<List<String>> level,int layer) {

        //current implementation
        List<String> drawableLevelLayer = new ArrayList<>();

        int index = 0;

            List<String> levelLayer = level.get(layer);
            for (String i : levelLayer) {
               // (int i = 0; i < layerSize; i++)
                switch (levelLayer.get(index)) {
                    case "f":
                        drawableLevelLayer.add(index, "middleFloorTile");
                        index++;
                        break;
                    case "tlw":
                        drawableLevelLayer.add(index, "topLeftWallTile");
                        index++;
                        break;
                    case "tw":
                        drawableLevelLayer.add(index, "topWallTile");
                        index++;
                        break;
                    case "trw":
                        drawableLevelLayer.add(index, "topRightWallTile");
                        index++;
                        break;
                    case "lw":
                        drawableLevelLayer.add(index, "leftWallTile");
                        index++;
                        break;
                    case "rw":
                        drawableLevelLayer.add(index, "rightWallTile");
                        index++;
                        break;
                    case "blw":
                        drawableLevelLayer.add(index, "bottomLeftWallTile");
                        index++;
                        break;
                    case "bw":
                        drawableLevelLayer.add(index, "bottomWallTile");
                        index++;
                        break;
                    case "brw":
                        drawableLevelLayer.add(index, "bottomRightWallTile");
                        index++;
                        break;
                    case "tlt":
                        drawableLevelLayer.add(index, "topLeftTurnTile");
                        index++;
                        break;
                    case "trt":
                        drawableLevelLayer.add(index, "topRightTurnTile");
                        index++;
                        break;
                    case "blt":
                        drawableLevelLayer.add(index, "bottomLeftTurnTile");
                        index++;
                        break;
                    case "brt":
                        drawableLevelLayer.add(index, "bottomRightTurnTile");
                        index++;
                        break;
                    case "dtl":
                        drawableLevelLayer.add(index, "doorTopLeftWall");
                        index++;
                        break;
                    case "dtr":
                        drawableLevelLayer.add(index, "doorTopRightWall");
                        index++;
                        break;
                    case "dlu":
                        drawableLevelLayer.add(index, "doorLeftUpperWall");
                        index++;
                        break;
                    case "dll":
                        drawableLevelLayer.add(index, "doorLeftLowerWall");
                        index++;
                        break;
                    case "dru":
                        drawableLevelLayer.add(index, "doorRightUpperWall");
                        index++;
                        break;
                    case "drl":
                        drawableLevelLayer.add(index, "doorRightLowerWall");
                        index++;
                        break;
                    case "dbl":
                        drawableLevelLayer.add(index, "doorBottomLeftWall");
                        index++;
                        break;
                    case "dbr":
                        drawableLevelLayer.add(index, "doorBottomRightWall");
                        index++;
                        break;
                    case "doortl":
                        drawableLevelLayer.add(index, "doorTopLeft");
                        index++;
                        break;
                    case "doortr":
                        drawableLevelLayer.add(index, "doorTopRight");
                        index++;
                        break;
                    case "doorlu":
                        drawableLevelLayer.add(index, "doorLeftUpper");
                        index++;
                        break;
                    case "doorll":
                        drawableLevelLayer.add(index, "doorLeftLower");
                        index++;
                        break;
                    case "doorru":
                        drawableLevelLayer.add(index, "doorRightUpper");
                        index++;
                        break;
                    case "doorrl":
                        drawableLevelLayer.add(index, "doorRightLower");
                        index++;
                        break;
                    case "doorbl":
                        drawableLevelLayer.add(index, "doorBottomLeft");
                        index++;
                        break;
                    case "doorbr":
                        drawableLevelLayer.add(index, "doorBottomRight");
                        index++;
                        break;
                    default:
                        if (i == "" || i == " " || i == null || i.isEmpty()){
                            drawableLevelLayer.add(index, "");
                            index++;
                            break;
                        }
                        else if (levelLayer.get(index).matches("[f].+")) {
                            StringBuffer sb = new StringBuffer(i);
                            sb.delete(0, 1);
                            String str = sb.toString();

                            switch (str) {
                                case "enemy":
                                    drawableLevelLayer.add(index, "enemy");
                                    index++;
                                    break;
                                case "hpot":
                                    //TODO: add potion item
                                    drawableLevelLayer.add(index, "middleFloorTile");
                                    index++;
                                    break;
                                case "ob1":
                                    drawableLevelLayer.add(index, "obstacle1");
                                    index++;
                                    break;
                                case "ob2":
                                    drawableLevelLayer.add(index, "obstacle2");
                                    index++;
                                    break;
                                case "ob3":
                                    drawableLevelLayer.add(index, "obstacle3");
                                    index++;
                                    break;
                                case "trap":
                                    index++;
                                    break;
                             }
                        }
                        else {
                            if (i != ""){
                                System.out.println("UNKNOWN TILE: " + "'" + i + "'");
                                drawableLevelLayer.add(index, "");
                                index++;
                                break;
                            }
                            index++;
                            break;
                        }
                }
            }
        level.add(drawableLevelLayer);
       // System.out.println("Drawable level layer " +(layer+1) + ": "+drawableLevelLayer);
        return drawableLevelLayer;
    }
}