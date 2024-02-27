package com.mygdx.game;

import com.mygdx.game.entity.behaviours.fsm.Enemy;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import com.mygdx.game.entity.behaviours.fsm.Enemy;

public class RenderRules {
    public List<String> translateSymbols(List<List<String>> level,int layer) {

        List<String> drawableLevelLayer = Arrays.asList
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
        int index = 0;
        //Pattern floorItems = Pattern.compile("f.");


      //  if (floorItems.matches("f.","f+")) {
        //    System.out.println("Huzzah");

            List<String> levelLayer = level.get(layer);
            for (String i : levelLayer) {
                switch (levelLayer.get(index)) {
                    case "f":
                        drawableLevelLayer.set(index, "middleFloorTile");
                        index++;
                        break;
                    case "tlw":
                        drawableLevelLayer.set(index, "topLeftWallTile");
                        index++;
                        break;
                    case "tw":
                        drawableLevelLayer.set(index, "topWallTile");
                        index++;
                        break;
                    case "trw":
                        drawableLevelLayer.set(index, "topRightWallTile");
                        index++;
                        break;
                    case "lw":
                        drawableLevelLayer.set(index, "leftWallTile");
                        index++;
                        break;
                    case "rw":
                        drawableLevelLayer.set(index, "rightWallTile");
                        index++;
                        break;
                    case "blw":
                        drawableLevelLayer.set(index, "bottomLeftWallTile");
                        index++;
                        break;
                    case "bw":
                        drawableLevelLayer.set(index, "bottomWallTile");
                        index++;
                        break;
                    case "brw":
                        drawableLevelLayer.set(index, "bottomRightWallTile");
                        index++;
                        break;
                    case "tlt":
                        drawableLevelLayer.set(index, "topLeftTurnTile");
                        index++;
                        break;
                    case "trt":
                        drawableLevelLayer.set(index, "topRightTurnTile");
                        index++;
                        break;
                    case "blt":
                        drawableLevelLayer.set(index, "bottomLeftTurnTile");
                        index++;
                        break;
                    case "brt":
                        drawableLevelLayer.set(index, "bottomRightTurnTile");
                        index++;
                        break;
                    case "":
                        drawableLevelLayer.set(index, "");
                        index++;
                        break;
                    default:
                        if (levelLayer.get(index).matches("[f].+")) {
                            StringBuffer sb = new StringBuffer(levelLayer.get(index));
                            sb.delete(0, 1);
                            String str = sb.toString();

                            switch (str) {
                                case "door":
                                    drawableLevelLayer.set(index, "door");
                                case "enemy":
                                    drawableLevelLayer.set(index, "enemy");
                                    break;
                                case "hpot":
                                    //TODO: add potion item
                                    drawableLevelLayer.set(index, "middleFloorTile");
                                    break;
                                case "ob1":
                                    drawableLevelLayer.set(index, "obstacle1");
                                    break;
                                case "ob2":
                                    drawableLevelLayer.set(index, "obstacle2");
                                    break;
                                case "ob3":
                                    drawableLevelLayer.set(index, "obstacle3");
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