package com.mygdx.game.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RenderRules {
    public List<String> translateSymbols(List<List<String>> level, int layer, int roomsIndex, HashMap<String, String> map, int roomX, int levelY) {

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
                    case "doorul":
                        drawableLevelLayer.add(index, "doorLeftUpper");
                        index++;
                        break;
                    case "doorll":
                        drawableLevelLayer.add(index, "doorLeftLower");
                        index++;
                        break;
                    case "doorur":
                        drawableLevelLayer.add(index, "doorRightUpper");
                        index++;
                        break;
                    case "doorlr":
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
                                case "shop":
                                    drawableLevelLayer.add(index, "shop");
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
                            if (!i.equals("")){
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



    public HashMap<String, String> translateSymbolsToFindDoors(List<List<String>> level, int layer, int roomsIndex, int doorDirection, int previousDoorDirection, HashMap<String, String> map, int roomX, int levelY) {
        //System.out.println("TEST WORKED");
        //current implementation
        List<String> drawableLevelLayer = new ArrayList<>();

        int index = 0;

        List<String> levelLayer = level.get(layer);
        for (String i : levelLayer) {
            // (int i = 0; i < layerSize; i++)
            switch (levelLayer.get(index)) {
                case "f":
                    index++;
                    break;
                case "tlw":
                    index++;
                    break;
                case "tw":
                    index++;
                    break;
                case "trw":
                    index++;
                    break;
                case "lw":
                    index++;
                    break;
                case "rw":
                    index++;
                    break;
                case "blw":
                    index++;
                    break;
                case "bw":
                    index++;
                    break;
                case "brw":
                    index++;
                    break;
                case "tlt":
                    index++;
                    break;
                case "trt":
                    index++;
                    break;
                case "blt":
                    index++;
                    break;
                case "brt":
                    index++;
                    break;
                case "dtl":
                    index++;
                    break;
                case "dtr":
                    index++;
                    break;
                case "dlu":
                    index++;
                    break;
                case "dll":
                    index++;
                    break;
                case "dru":
                    index++;
                    break;
                case "drl":
                    index++;
                    break;
                case "dbl":
                    index++;
                    break;
                case "dbr":
                    index++;
                    break;
                case "doortl":
                        //TODO: Make function in AlignDoors that takes roomX levelY, current and previous direction
                        //String topLeftX = Integer.toString((roomX + index) + 16);
                        String topLeftX = Integer.toString(roomX);
                        String topLeftY = Integer.toString(levelY);
                        String topLeft = topLeftX + "," + topLeftY;
                        map.put("TopLeft", topLeft);
                        //System.out.println(map.get("TopLeft") + " MAP TOPLEFT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doortr":
                        String topRightX = Integer.toString(roomX);
                        String topRightY = Integer.toString(levelY);
                        String topRight = topRightX + "," + topRightY;
                        map.put("TopRight", topRight);
                        //System.out.println(map.get("TopRight") + " MAP TOPRIGHT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorul":
                        String upperLeftX = Integer.toString(roomX);
                        String upperLeftY = Integer.toString(levelY);
                        String upperLeft = upperLeftX + "," + upperLeftY;
                        map.put("UpperLeft", upperLeft);
                        //System.out.println(map.get("UpperLeft") + " MAP UPPERLEFT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorll":
                        String lowerLeftX = Integer.toString(roomX);
                        String lowerLeftY = Integer.toString(levelY);
                        String lowerLeft = lowerLeftX + "," + lowerLeftY;
                        map.put("LowerLeft", lowerLeft);
                        //System.out.println(map.get("LowerLeft") + " MAP LOWERLEFT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorur":
                        String upperRightX = Integer.toString(roomX);
                        String upperRightY = Integer.toString(levelY);
                        String upperRight = upperRightX + "," + upperRightY;
                        map.put("UpperRight", upperRight);
                        //System.out.println(map.get("UpperRight") + " MAP UPPERRIGHT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorlr":
                        String lowerRightX = Integer.toString(roomX);
                        String lowerRightY = Integer.toString(levelY);
                        String lowerRight = lowerRightX + "," + lowerRightY;
                        map.put("LowerRight", lowerRight);
                        //System.out.println(map.get("LowerRight") + " MAP LOWERRIGHT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorbl":
                        String bottomLeftX = Integer.toString(roomX);
                        String bottomLeftY = Integer.toString(levelY);
                        String bottomLeft = bottomLeftX + "," + bottomLeftY;
                        map.put("BottomLeft", bottomLeft);
                        //System.out.println(map.get("BottomLeft") + " MAP BOTTOMLEFT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorbr":
                        String bottomRightX = Integer.toString(roomX);
                        String bottomRightY = Integer.toString(levelY);
                        String bottomRight = bottomRightX + "," + bottomRightY;
                        map.put("BottomRight", bottomRight);
                        //System.out.println(map.get("BottomRight") + " MAP BOTTOMRIGHT DOOR X AND Y VALUES");
                    index++;
                    break;
                default:
                    index++;
                    break;
            }
            roomX++;
        }
        //level.add(drawableLevelLayer);
        // System.out.println("Drawable level layer " +(layer+1) + ": "+drawableLevelLayer);
        return map;
    }
}