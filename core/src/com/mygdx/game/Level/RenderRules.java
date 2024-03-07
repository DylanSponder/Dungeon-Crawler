package com.mygdx.game.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenderRules {
    public List<String> translateSymbols(List<List<String>> level,int layer) {

        //TODO: old implementation - remove if unused in future
        List<String> drawableLevelLayer2 = Arrays.asList
                ("","","","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","",""
                        );


        //current implementation
        List<String> drawableLevelLayer = new ArrayList<>();

        int index = 0;

            List<String> levelLayer = level.get(layer);
            for (String i : levelLayer) {
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
                    case "":
                        drawableLevelLayer.add(index, "");
                        index++;
                        break;
                    default:
                        if (levelLayer.get(index).matches("[f].+")) {
                            StringBuffer sb = new StringBuffer(levelLayer.get(index));
                            sb.delete(0, 1);
                            String str = sb.toString();

                            switch (str) {
                                case "door":
                                    drawableLevelLayer.add(index, "door");
                                case "enemy":
                                    drawableLevelLayer.add(index, "enemy");
                                    break;
                                case "hpot":
                                    //TODO: add potion item
                                    drawableLevelLayer.add(index, "middleFloorTile");
                                    break;
                                case "ob1":
                                    drawableLevelLayer.add(index, "obstacle1");
                                    break;
                                case "ob2":
                                    drawableLevelLayer.add(index, "obstacle2");
                                    break;
                                case "ob3":
                                    drawableLevelLayer.add(index, "obstacle3");
                                    break;
                                case "trap":
                                    break;
                             }
                        }
                        index++;
                        break;
                }
            }
        level.add(drawableLevelLayer);
       // System.out.println("Drawable level layer " +(layer+1) + ": "+drawableLevelLayer);
        return drawableLevelLayer;
    }
}