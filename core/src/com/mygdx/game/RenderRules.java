package com.mygdx.game;

import java.util.Arrays;
import java.util.List;

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
            List<String> levelLayer = level.get(layer);
            for (String i : levelLayer) {
                switch (levelLayer.get(index)) {
                    case "tlf":
                        drawableLevelLayer.set(index, "topLeftFloorTile");
                        index++;
                        break;
                    case "tf":
                        drawableLevelLayer.set(index, "topFloorTile");
                        index++;
                        break;
                    case "trf":
                        drawableLevelLayer.set(index, "topRightFloorTile");
                        index++;
                        break;
                    case "lf":
                        drawableLevelLayer.set(index, "leftFloorTile");
                        index++;
                        break;
                    case "f":
                        drawableLevelLayer.set(index, "middleFloorTile");
                        index++;
                        break;
                    case "rf":
                        drawableLevelLayer.set(index, "rightFloorTile");
                        index++;
                        break;
                    case "blf":
                        drawableLevelLayer.set(index, "bottomLeftFloorTile");
                        index++;
                        break;
                    case "bf":
                        drawableLevelLayer.set(index, "bottomFloorTile");
                        index++;
                        break;
                    case "brf":
                        drawableLevelLayer.set(index, "bottomRightFloorTile");
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
                }
            }
        level.add(drawableLevelLayer);
        System.out.println("Drawable level layer " +(layer+1) + ": "+drawableLevelLayer);
        return drawableLevelLayer;
    }
}