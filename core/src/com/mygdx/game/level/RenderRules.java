package com.mygdx.game.level;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.level.objects.Coin;
import com.mygdx.game.level.objects.Roof;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.awt.dnd.InvalidDnDOperationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mygdx.game.DungeonCrawler.roofs;

public class RenderRules {
    public List<String> translateSymbols(List<List<String>> level, int layer, int roomsIndex, HashMap<String, String> map, int roomX, int levelY) {

        //current implementation
        List<String> drawableLevelLayer = new ArrayList<>();

        int index = 0;

            List<String> levelLayer = level.get(layer);
            for (String i : levelLayer) {
               // (int i = 0; i < layerSize; i++)
                switch (levelLayer.get(index)) {
                    case "a":
                        drawableLevelLayer.add(index, "airTile");
                        index++;
                        break;
                    case "f":
                        drawableLevelLayer.add(index, "middleFloorTile");
                        index++;
                        break;
                    case "f2":
                        drawableLevelLayer.add(index, "middleFloor2Tile");
                        index++;
                        break;
                    case "raf":
                        drawableLevelLayer.add(index, "raisedFloorTile");
                        index++;
                        break;
                    case "waf":
                        drawableLevelLayer.add(index, "waterFloorTile");
                        index++;
                        break;
                    case "fvdm":
                        drawableLevelLayer.add(index, "venusDeMilo");
                        index++;
                        break;
                    case "stlt":
                        drawableLevelLayer.add(index, "topLeftStairTile");
                        index++;
                        break;
                    case "strt":
                        drawableLevelLayer.add(index, "topRightStairTile");
                        index++;
                        break;
                    case "stlc":
                        drawableLevelLayer.add(index, "leftCornerStairTile");
                        index++;
                        break;
                    case "strc":
                        drawableLevelLayer.add(index, "rightCornerStairTile");
                        index++;
                        break;
                    case "stl":
                        drawableLevelLayer.add(index, "leftStairTile");
                        index++;
                        break;
                    case "str":
                        drawableLevelLayer.add(index, "rightStairTile");
                        index++;
                        break;
                    case "stu":
                        drawableLevelLayer.add(index, "topStairTile");
                        index++;
                        break;
                    case "stu2":
                        drawableLevelLayer.add(index, "topStair2Tile");
                        index++;
                        break;
                    case "std":
                        drawableLevelLayer.add(index, "bottomStairTile");
                        index++;
                        break;
                    case "stdl":
                        drawableLevelLayer.add(index, "bottomLeftStairTile");
                        index++;
                        break;
                    case "stdr":
                        drawableLevelLayer.add(index, "bottomRightStairTile");
                        index++;
                        break;

                    case "stutl":
                        drawableLevelLayer.add(index, "upTopLeftStairTile");
                        index++;
                        break;
                    case "stutr":
                        drawableLevelLayer.add(index, "upTopRightStairTile");
                        index++;
                        break;
                    case "stubl":
                        drawableLevelLayer.add(index, "upBottomLeftStairTile");
                        index++;
                        break;
                    case "stubr":
                        drawableLevelLayer.add(index, "upBottomRightStairTile");
                        index++;
                        break;


                    case "f3":
                        drawableLevelLayer.add(index, "middleFloor3Tile");
                        index++;
                        break;
                    case "dfu":
                        drawableLevelLayer.add(index, "decorFloorUpTile");
                        index++;
                        break;
                    case "dfd":
                        drawableLevelLayer.add(index, "decorFloorDownTile");
                        index++;
                        break;
                    case "dfl":
                        drawableLevelLayer.add(index, "decorFloorLeftTile");
                        index++;
                        break;
                    case "dfr":
                        drawableLevelLayer.add(index, "decorFloorRightTile");
                        index++;
                        break;
                    case "dftl":
                        drawableLevelLayer.add(index, "decorFloorTopLeftTile");
                        index++;
                        break;
                    case "dftr":
                        drawableLevelLayer.add(index, "decorFloorTopRightTile");
                        index++;
                        break;
                    case "dfbl":
                        drawableLevelLayer.add(index, "decorFloorBottomLeftTile");
                        index++;
                        break;
                    case "dfbr":
                        drawableLevelLayer.add(index, "decorFloorBottomRightTile");
                        index++;
                        break;
                    case "mosgrif":
                        drawableLevelLayer.add(index, "mosaicGriffin");
                        index++;
                        break;
                    case "moslyre":
                        drawableLevelLayer.add(index, "mosaicLyre");
                        index++;
                        break;
                    case "mostree":
                        drawableLevelLayer.add(index, "mosaicTree");
                        index++;
                        break;
                    case "mosbull":
                        drawableLevelLayer.add(index, "mosaicBull");
                        index++;
                        break;
                    case "mostrid":
                        drawableLevelLayer.add(index, "mosaicTrident");
                        index++;
                        break;
                    case "p":
                        drawableLevelLayer.add(index, "pit");
                        index++;
                        break;
                    case "pu1":
                        drawableLevelLayer.add(index, "pitFloor1");
                        index++;
                        break;
                    case "pu2":
                        drawableLevelLayer.add(index, "pitFloor2");
                        index++;
                        break;
                    case "pst":
                        drawableLevelLayer.add(index, "pitStairs");
                        index++;
                        break;
                    case "pd":
                        drawableLevelLayer.add(index, "pitDown");
                        index++;
                        break;
                    case "pl":
                        drawableLevelLayer.add(index, "pitLeft");
                        index++;
                        break;
                    case "pr":
                        drawableLevelLayer.add(index, "pitRight");
                        index++;
                        break;

                    case "prub":
                        drawableLevelLayer.add(index, "pitRubble");
                        index++;
                        break;
                    case "prub1":
                        drawableLevelLayer.add(index, "pitRubble1");
                        index++;
                        break;
                    case "prub1r":
                        drawableLevelLayer.add(index, "pitRubble1Random");
                        index++;
                        break;
                    case "prub2":
                        drawableLevelLayer.add(index, "pitRubble2");
                        index++;
                        break;
                    case "prub3":
                        drawableLevelLayer.add(index, "pitRubble3");
                        index++;
                        break;
                    case "prub3r":
                        drawableLevelLayer.add(index, "pitRubble3Random");
                        index++;
                        break;
                    case "pdrub":
                        drawableLevelLayer.add(index, "pitDownRubble");
                        index++;
                        break;
                    case "plrub":
                        drawableLevelLayer.add(index, "pitLeftRubble");
                        index++;
                        break;
                    case "prrub":
                        drawableLevelLayer.add(index, "pitRightRubble");
                        index++;
                        break;

                    case "iwu":
                        drawableLevelLayer.add(index, "innerWallUp");
                        index++;
                        break;
                    case "iwd":
                        drawableLevelLayer.add(index, "innerWallDown");
                        index++;
                        break;
                    case "iwl":
                        drawableLevelLayer.add(index, "innerWallLeft");
                        index++;
                        break;
                    case "iwr":
                        drawableLevelLayer.add(index, "innerWallRight");
                        index++;
                        break;
                    case "iwtlt":
                        drawableLevelLayer.add(index, "innerWallTLTurn");
                        index++;
                        break;
                    case "iwtrt":
                        drawableLevelLayer.add(index, "innerWallTRTurn");
                        index++;
                        break;
                    case "iwblt":
                        drawableLevelLayer.add(index, "innerWallBLTurn");
                        index++;
                        break;
                    case "iwbrt":
                        drawableLevelLayer.add(index, "innerWallBRTurn");
                        index++;
                        break;

                    case "iwtlt2":
                        drawableLevelLayer.add(index, "innerWallTLTurn2");
                        index++;
                        break;
                    case "iwtrt2":
                        drawableLevelLayer.add(index, "innerWallTRTurn2");
                        index++;
                        break;
                    case "iwblt2":
                        drawableLevelLayer.add(index, "innerWallBLTurn2");
                        index++;
                        break;
                    case "iwbrt2":
                        drawableLevelLayer.add(index, "innerWallBRTurn2");
                        index++;
                        break;

                    case "iwtl":
                        drawableLevelLayer.add(index, "innerWallTLCorner");
                        index++;
                        break;
                    case "iwtr":
                        drawableLevelLayer.add(index, "innerWallTRCorner");
                        index++;
                        break;
                    case "iwbl":
                        drawableLevelLayer.add(index, "innerWallBLCorner");
                        index++;
                        break;
                    case "iwbr":
                        drawableLevelLayer.add(index, "innerWallBRCorner");
                        index++;
                        break;

                    case "iwutr":
                        drawableLevelLayer.add(index, "innerWallUpTrap");
                        index++;
                        break;
                    case "iwdtr":
                        drawableLevelLayer.add(index, "innerWallDownTrap");
                        index++;
                        break;
                    case "iwltr":
                        drawableLevelLayer.add(index, "innerWallLeftTrap");
                        index++;
                        break;
                    case "iwrtr":
                        drawableLevelLayer.add(index, "innerWallRightTrap");
                        index++;
                        break;

                    case "tlw":
                        drawableLevelLayer.add(index, "topLeftWallTile");
                        index++;
                        break;

                        //large walls
                    case "ltw":
                        drawableLevelLayer.add(index, "largeTopWallTile");
                        index++;
                        break;
                    case "llw":
                        drawableLevelLayer.add(index, "largeLeftWallTile");
                        index++;
                        break;
                    case "lrw":
                        drawableLevelLayer.add(index, "largeRightWallTile");
                        index++;
                        break;
                    case "lbw":
                        drawableLevelLayer.add(index, "largeBottomWallTile");
                        index++;
                        break;
                        
                    case "lblw":
                        drawableLevelLayer.add(index, "largeBottomLeftWallTile");
                        index++;
                        break;
                    case "lbrw":
                        drawableLevelLayer.add(index, "largeBottomRightWallTile");
                        index++;
                        break;
                    case "ltlw":
                        drawableLevelLayer.add(index, "largeTopLeftWallTile");
                        index++;
                        break;
                    case "ltrw":
                        drawableLevelLayer.add(index, "largeTopRightWallTile");
                        index++;
                        break;
                    case "lblt":
                        drawableLevelLayer.add(index, "largeBottomLeftWallTurnTile");
                        index++;
                        break;
                    case "lbrt":
                        drawableLevelLayer.add(index, "largeBottomRightWallTurnTile");
                        index++;
                        break;
                    case "ltlt":
                        drawableLevelLayer.add(index, "largeTopLeftWallTurnTile");
                        index++;
                        break;
                    case "ltrt":
                        drawableLevelLayer.add(index, "largeTopRightWallTurnTile");
                        index++;
                        break;


                    case "tw":
                        drawableLevelLayer.add(index, "topWallTile");
                        index++;
                        break;
                    case "twtr":
                        drawableLevelLayer.add(index, "topWallTrapTile");
                        index++;
                        break;
                    case "twtr2":
                        drawableLevelLayer.add(index, "topWallFireTrapTile");
                        index++;
                        break;
                    case "ltwtr":
                        drawableLevelLayer.add(index, "largeTopWallTrapTile");
                        index++;
                        break;
                    case "ltwtr2":
                        drawableLevelLayer.add(index, "largeTopWallFireTrapTile");
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
                    case "lwtr":
                        drawableLevelLayer.add(index, "leftWallTrapTile");
                        index++;
                        break;
                    case "lwtr2":
                        drawableLevelLayer.add(index, "leftWallFireTrapTile");
                        index++;
                        break;
                    case "llwtr":
                        drawableLevelLayer.add(index, "largeLeftWallTrapTile");
                        index++;
                        break;
                    case "llwtr2":
                        drawableLevelLayer.add(index, "largeLeftWallFireTrapTile");
                        index++;
                        break;
                    case "rw":
                        drawableLevelLayer.add(index, "rightWallTile");
                        index++;
                        break;
                    case "rwtr":
                        drawableLevelLayer.add(index, "rightWallTrapTile");
                        index++;
                        break;
                    case "rwtr2":
                        drawableLevelLayer.add(index, "rightWallFireTrapTile");
                        index++;
                        break;
                    case "lrwtr":
                        drawableLevelLayer.add(index, "largeRightWallTrapTile");
                        index++;
                        break;
                    case "lrwtr2":
                        drawableLevelLayer.add(index, "largeRightWallFireTrapTile");
                        index++;
                        break;
                    case "bl":
                        drawableLevelLayer.add(index, "block");
                        index++;
                        break;
                    case "blwu":
                        drawableLevelLayer.add(index, "blockWallUp");
                        index++;
                        break;
                    case "bltrd":
                        drawableLevelLayer.add(index, "blockTrapDown");
                        index++;
                        break;
                    case "bltrl":
                        drawableLevelLayer.add(index, "blockTrapLeft");
                        index++;
                        break;
                    case "bltrr":
                        drawableLevelLayer.add(index, "blockTrapRight");
                        index++;
                        break;
                    case "bltru":
                        drawableLevelLayer.add(index, "blockTrapUp");
                        index++;
                        break;
                    case "blomega":
                        drawableLevelLayer.add(index, "blockomega");
                        index++;
                        break;
                    case "blphi":
                        drawableLevelLayer.add(index, "blockphi");
                        index++;
                        break;
                    case "bldelta":
                        drawableLevelLayer.add(index, "blockdelta");
                        index++;
                        break;
                    case "blsigma":
                        drawableLevelLayer.add(index, "blocksigma");
                        index++;
                        break;
                    case "bllambda":
                        drawableLevelLayer.add(index, "blocklambda");
                        index++;
                        break;
                    case "blhl":
                        drawableLevelLayer.add(index, "blockgaben");
                        index++;
                        break;
                    case "blpi":
                        drawableLevelLayer.add(index, "blockpi");
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
                    case "bwtr":
                        drawableLevelLayer.add(index, "bottomWallTrapTile");
                        index++;
                        break;
                    case "bwtr2":
                        drawableLevelLayer.add(index, "bottomWallFireTrapTile");
                        index++;
                        break;
                    case "lbwtr":
                        drawableLevelLayer.add(index, "largeBottomWallTrapTile");
                        index++;
                        break;
                    case "lbwtr2":
                        drawableLevelLayer.add(index, "largeBottomWallFireTrapTile");
                        index++;
                        break;
                    case "brw":
                        drawableLevelLayer.add(index, "bottomRightWallTile");
                        index++;
                        break;
                    case "tlt":
                        drawableLevelLayer.add(index, "topLeftTurnWallTile");
                        index++;
                        break;
                    case "trt":
                        drawableLevelLayer.add(index, "topRightTurnWallTile");
                        index++;
                        break;
                    case "blt":
                        drawableLevelLayer.add(index, "bottomLeftTurnWallTile");
                        index++;
                        break;
                    case "brt":
                        drawableLevelLayer.add(index, "bottomRightTurnWallTile");
                        index++;
                        break;
                    case "tf":
                        drawableLevelLayer.add(index, "topFenceTile");
                        index++;
                        break;
                    case "bf":
                        drawableLevelLayer.add(index, "bottomFenceTile");
                        index++;
                        break;
                    case "lf":
                        drawableLevelLayer.add(index, "leftFenceTile");
                        index++;
                        break;
                    case "rf":
                        drawableLevelLayer.add(index, "rightFenceTile");
                        index++;
                        break;
                    case "trf":
                        drawableLevelLayer.add(index, "topRightFenceTile");
                        index++;
                        break;
                    case "tlf":
                        drawableLevelLayer.add(index, "topLeftFenceTile");
                        index++;
                        break;
                    case "blf":
                        drawableLevelLayer.add(index, "bottomLeftFenceTile");
                        index++;
                        break;
                    case "brf":
                        drawableLevelLayer.add(index, "bottomRightFenceTile");
                        index++;
                        break;
                    case "trft":
                        drawableLevelLayer.add(index, "topRightTurnFenceTile");
                        index++;
                        break;
                    case "tlft":
                        drawableLevelLayer.add(index, "topLeftTurnFenceTile");
                        index++;
                        break;
                    case "blft":
                        drawableLevelLayer.add(index, "bottomLeftTurnFenceTile");
                        index++;
                        break;
                    case "brft":
                        drawableLevelLayer.add(index, "bottomRightTurnFenceTile");
                        index++;
                        break;
                    case "bfle":
                        drawableLevelLayer.add(index, "bottomFenceLeftEndTile");
                        index++;
                        break;
                    case "bfre":
                        drawableLevelLayer.add(index, "bottomFenceRightEndTile");
                        index++;
                        break;
                    case "tfre":
                        drawableLevelLayer.add(index, "topFenceRightEndTile");
                        index++;
                        break;
                    case "tfle":
                        drawableLevelLayer.add(index, "topFenceLeftEndTile");
                        index++;
                        break;
                    case "lfue":
                        drawableLevelLayer.add(index, "leftFenceTopEndTile");
                        index++;
                        break;
                    case "lfbe":
                        drawableLevelLayer.add(index, "leftFenceBottomEndTile");
                        index++;
                        break;
                    case "rfue":
                        drawableLevelLayer.add(index, "rightFenceTopEndTile");
                        index++;
                        break;
                    case "rfbe":
                        drawableLevelLayer.add(index, "rightFenceBottomEndTile");
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
                    case "doorftl":
                        drawableLevelLayer.add(index, "doorTopLeftFence");
                        index++;
                        break;
                    case "doortl":
                        drawableLevelLayer.add(index, "doorTopLeft");
                        index++;
                        break;
                    case "doorftr":
                        drawableLevelLayer.add(index, "doorTopRightFence");
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
                    case "doorful":
                        drawableLevelLayer.add(index, "doorLeftUpperFence");
                        index++;
                        break;
                    case "doorll":
                        drawableLevelLayer.add(index, "doorLeftLower");
                        index++;
                        break;
                    case "doorfll":
                        drawableLevelLayer.add(index, "doorLeftLowerFence");
                        index++;
                        break;
                    case "doorur":
                        drawableLevelLayer.add(index, "doorRightUpper");
                        index++;
                        break;
                    case "doorfur":
                        drawableLevelLayer.add(index, "doorRightUpperFence");
                        index++;
                        break;
                    case "doorlr":
                        drawableLevelLayer.add(index, "doorRightLower");
                        index++;
                        break;
                    case "doorflr":
                        drawableLevelLayer.add(index, "doorRightLowerFence");
                        index++;
                        break;
                    case "doorbl":
                        drawableLevelLayer.add(index, "doorBottomLeft");
                        index++;
                        break;
                    case "doorfbl":
                        drawableLevelLayer.add(index, "doorBottomLeftFence");
                        index++;
                        break;
                    case "doorbr":
                        drawableLevelLayer.add(index, "doorBottomRight");
                        index++;
                        break;
                    case "doorfbr":
                        drawableLevelLayer.add(index, "doorBottomRightFence");
                        index++;
                        break;
                    case "torl":
                        drawableLevelLayer.add(index, "torl");
                        index++;
                        break;
                    case "torlb":
                        drawableLevelLayer.add(index, "torlb");
                        index++;
                        break;
                    case "ltorl":
                        drawableLevelLayer.add(index, "ltorl");
                        index++;
                        break;
                    case "ltorlb":
                        drawableLevelLayer.add(index, "ltorlb");
                        index++;
                        break;
                    case "torloff":
                        drawableLevelLayer.add(index, "torloff");
                        index++;
                        break;
                    case "torr":
                        drawableLevelLayer.add(index, "torr");
                        index++;
                        break;
                    case "torrb":
                        drawableLevelLayer.add(index, "torrb");
                        index++;
                        break;
                    case "ltorr":
                        drawableLevelLayer.add(index, "ltorr");
                        index++;
                        break;
                    case "ltorrb":
                        drawableLevelLayer.add(index, "ltorrb");
                        index++;
                        break;
                    case "torroff":
                        drawableLevelLayer.add(index, "torroff");
                        index++;
                        break;
                    case "toru":
                        drawableLevelLayer.add(index, "toru");
                        index++;
                        break;
                    case "torub":
                        drawableLevelLayer.add(index, "torub");
                        index++;
                        break;
                    case "ltoru":
                        drawableLevelLayer.add(index, "ltoru");
                        index++;
                        break;
                    case "ltorub":
                        drawableLevelLayer.add(index, "ltorub");
                        index++;
                        break;
                    case "toruoff":
                        drawableLevelLayer.add(index, "toruoff");
                        index++;
                        break;
                    case "tord":
                        drawableLevelLayer.add(index, "tord");
                        index++;
                        break;
                    case "tordb":
                        drawableLevelLayer.add(index, "tordb");
                        index++;
                        break;
                    case "ltord":
                        drawableLevelLayer.add(index, "ltord");
                        index++;
                        break;
                    case "ltordb":
                        drawableLevelLayer.add(index, "ltordb");
                        index++;
                        break;
                    case "tordoff":
                        drawableLevelLayer.add(index, "tordoff");
                        index++;
                        break;
                    default:
                        if (i == "" || i == " " || i == null || i.isEmpty()){
                            drawableLevelLayer.add(index, "");
                            index++;
                            break;
                        }
                        else if (levelLayer.get(index).matches("(wf1[0-9]+)")) {
                            StringBuffer sbw = new StringBuffer(i);
                            sbw.delete(0, 3);
                            String strw = sbw.toString();

                            String water = strw;
                            StringBuffer sb3 = new StringBuffer(water);

                            strw = "wf1" + strw;

                            //sb3.delete(1, 3);
                            //String strRoofType = sb3.toString();

                            drawableLevelLayer.add(index, strw);
                            index++;
                            break;
                        }
                        else if (levelLayer.get(index).matches("(wstlt[0-9]+)")) {
                            StringBuffer sbw = new StringBuffer(i);
                            sbw.delete(0, 5);
                            String strw = sbw.toString();

                            String water = strw;
                            StringBuffer sb3 = new StringBuffer(water);

                            strw = "wstlt" + strw;

                            //sb3.delete(1, 3);
                            //String strRoofType = sb3.toString();

                            drawableLevelLayer.add(index, strw);
                            index++;
                            break;
                        }
                        /*
                        else if (levelLayer.get(index).matches("(w[a-z]{2}[0-9]+)")) {
                            StringBuffer sbw = new StringBuffer(i);
                            sbw.delete(0, 3);
                            String strw = sbw.toString();

                            String water = strw;
                            StringBuffer sb3 = new StringBuffer(water);

                            //sb3.delete(1, 3);
                            //String strRoofType = sb3.toString();



                            drawableLevelLayer.add(index, strw);
                            index++;
                            break;
                        }




                        else if (levelLayer.get(index).matches("(w[a-z]{2}[0-9]+)")) {
                            StringBuffer sbw = new StringBuffer(i);
                            sbw.delete(0, 3);
                            String strw = sbw.toString();

                            String water = strw;
                            StringBuffer sb3 = new StringBuffer(water);

                            //sb3.delete(1, 3);
                            //String strRoofType = sb3.toString();



                            drawableLevelLayer.add(index, strw);
                            index++;
                            break;
                        }
                         */

                        else if (levelLayer.get(index).matches("[a].+")) {
                            StringBuffer sbr = new StringBuffer(i);
                            sbr.delete(0, 1);
                            String strr = sbr.toString();
                            //floor + columntop4 + fire

                            if (strr.matches("([0-9])+")) {

                                String roofr = strr;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roofr);
                                sb3.delete(1, 3);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(strr);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "aroof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "aroof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "aroof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "aroof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
                            }
                        }



                        else if (levelLayer.get(index).matches("[f].+")) {
                            StringBuffer sb = new StringBuffer(i);
                            sb.delete(0, 1);
                            String str = sb.toString();
                            //floor + columntop4 + fire

                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 3);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "froof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "froof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "froof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "froof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
                            } else {



                            switch (str) {
                                    case "col10fire":
                                        drawableLevelLayer.add(index, "fcol10fire");
                                        index++;
                                        break;

                            }
                                if (str.matches("(coldo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfldo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfltu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cflio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfldb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfltb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cflib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csldo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSldo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csltu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cslio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSlio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }

                                if (str.matches("(csldb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csltb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cslib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSldb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSltb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSlib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csfdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csftb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csfib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSfdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSftb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSfib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csfdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csftu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csfio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSfdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSftu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSfio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldd[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltd[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colid[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coliB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colts[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colis[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colds[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltS[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coliS[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldS[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltp[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfltp[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coftp[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csftp[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSftp[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colip[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldp[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltP[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coliP[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldP[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csldB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csltB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csliB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coftb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(covib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(covio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coftu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cottu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cotdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cotio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cottb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cotdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cotib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTtb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTtu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coftB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofdB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofiB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfftB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfftu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffdB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffiB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfftb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                            switch (str) {
                                case "cand":
                                    drawableLevelLayer.add(index,"cand");
                                    index++;
                                    break;
                                case "cand2":
                                    drawableLevelLayer.add(index,"cand2");
                                    index++;
                                    break;
                                case "cands":
                                    drawableLevelLayer.add(index,"cands");
                                    index++;
                                    break;
                                case "2cand":
                                    drawableLevelLayer.add(index,"2cand");
                                    index++;
                                    break;
                                case "2cands":
                                    drawableLevelLayer.add(index,"2cands");
                                    index++;
                                    break;
                                case "drain":
                                    drawableLevelLayer.add(index,"drain");
                                    index++;
                                    break;
                                case "drainflow":
                                    drawableLevelLayer.add(index,"drainflow");
                                    index++;
                                    break;
                                case "1drainwf":
                                    drawableLevelLayer.add(index,"f1drainwf");
                                    index++;
                                    break;
                                case "1wdrainwf":
                                    drawableLevelLayer.add(index,"wf1drainwf");
                                    index++;
                                    break;
                                case "vasebl":
                                    drawableLevelLayer.add(index,"vasebl");
                                    index++;
                                    break;
                                case "vaseblomega":
                                    drawableLevelLayer.add(index,"vaseblomega");
                                    index++;
                                    break;
                                case "lamp":
                                    drawableLevelLayer.add(index,"lamp");
                                    index++;
                                    break;
                                case "cob":
                                    drawableLevelLayer.add(index,"cobweb");
                                    index++;
                                    break;
                                    //enemy
                                case "enemy":
                                    drawableLevelLayer.add(index, "enemySkull");
                                    index++;
                                    break;
                                case "enemy2":
                                    drawableLevelLayer.add(index, "enemySpider");
                                    index++;
                                    break;
                                case "enemy3":
                                    drawableLevelLayer.add(index, "enemyGhost");
                                    index++;
                                    break;
                                case "enemy4":
                                    drawableLevelLayer.add(index, "enemyCyclops");
                                    index++;
                                    break;
                                case "enemy5":
                                    drawableLevelLayer.add(index, "enemyCrab");
                                    index++;
                                    break;
                                case "2enemy":
                                    drawableLevelLayer.add(index, "enemySkull2");
                                    index++;
                                    break;
                                case "2enemy2":
                                    drawableLevelLayer.add(index, "enemySpider2");
                                    index++;
                                    break;
                                case "2enemy3":
                                    drawableLevelLayer.add(index, "enemyGhost2");
                                    index++;
                                    break;
                                case "2enemy4":
                                    drawableLevelLayer.add(index, "enemyCyclops2");
                                    index++;
                                    break;
                                case "boss1":
                                    drawableLevelLayer.add(index, "bossMinotaur");
                                    index++;
                                    break;
                                    //shopkeeper entity
                                case "shop":
                                    drawableLevelLayer.add(index, "shop");
                                    index++;
                                    break;
                                    //tutorial text in starting room
                                case "tuto":
                                    drawableLevelLayer.add(index, "tuto");
                                    index++;
                                    break;
                                    //pots
                                case "pot":
                                    drawableLevelLayer.add(index, "pot");
                                    index++;
                                    break;
                                case "pot2":
                                    drawableLevelLayer.add(index, "pot2");
                                    index++;
                                    break;
                                case "coin":
                                    drawableLevelLayer.add(index, "coin");
                                    index++;
                                    break;
                                case "skull":
                                    drawableLevelLayer.add(index, "skull");
                                    index++;
                                    break;
                                    //pedestals
                                case "ped1":
                                    drawableLevelLayer.add(index, "fped1");
                                    index++;
                                    break;
                                case "ped1fire":
                                    drawableLevelLayer.add(index, "fped1fire");
                                    index++;
                                    break;
                                case "ped1statue1":
                                    drawableLevelLayer.add(index, "fped1statue1");
                                    index++;
                                    break;
                                case "ped1statue2":
                                    drawableLevelLayer.add(index, "fped1statue2");
                                    index++;
                                    break;
                                case "ped1fireoff":
                                    drawableLevelLayer.add(index, "fped1fireoff");
                                    index++;
                                    break;
                                case "ped1fireb":
                                    drawableLevelLayer.add(index, "fped1fireB");
                                    index++;
                                    break;
                                case "ped1heal":
                                    drawableLevelLayer.add(index, "fped1heal");
                                    index++;
                                    break;
                                case "ped2":
                                    drawableLevelLayer.add(index, "fped2");
                                    index++;
                                    break;
                                case "ped2heal":
                                    drawableLevelLayer.add(index, "fped2heal");
                                    index++;
                                    break;
                                case "ped2statue1":
                                    drawableLevelLayer.add(index, "fped2statue1");
                                    index++;
                                    break;
                                case "ped2statue2":
                                    drawableLevelLayer.add(index, "fped2statue2");
                                    index++;
                                    break;
                                case "ped2fire":
                                    drawableLevelLayer.add(index, "fped2fire");
                                    index++;
                                    break;
                                case "ped2fireoff":
                                    drawableLevelLayer.add(index, "fped2fireoff");
                                    index++;
                                    break;
                                case "ped3":
                                    drawableLevelLayer.add(index, "fped3");
                                    index++;
                                    break;
                                case "ped3fire":
                                    drawableLevelLayer.add(index, "fped3fire");
                                    index++;
                                    break;
                                case "ped3heal":
                                    drawableLevelLayer.add(index, "fped3heal");
                                    index++;
                                    break;
                                case "ped4":
                                    drawableLevelLayer.add(index, "fped4");
                                    index++;
                                    break;
                                case "ped4fire":
                                    drawableLevelLayer.add(index, "fped4fire");
                                    index++;
                                    break;
                                case "ped4fireoff":
                                    drawableLevelLayer.add(index, "fped4fireoff");
                                    index++;
                                    break;
                                    //obstacles
                                case "ob":
                                    drawableLevelLayer.add(index, "obstacle");
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
                                case "ob4":
                                    drawableLevelLayer.add(index, "obstacle4");
                                    index++;
                                    break;
                                case "ob5":
                                    drawableLevelLayer.add(index, "obstacle5");
                                    index++;
                                    break;
                                case "ob6":
                                    drawableLevelLayer.add(index, "obstacle6");
                                    index++;
                                    break;
                                case "ob7":
                                    drawableLevelLayer.add(index, "obstacle7");
                                    index++;
                                    break;

                                case "brcol1":
                                    drawableLevelLayer.add(index, "brokenColumnBase1");
                                    index++;
                                    break;
                                case "brcol2":
                                    drawableLevelLayer.add(index, "brokenColumnBase2");
                                    index++;
                                    break;
                                case "brcol3":
                                    drawableLevelLayer.add(index, "brokenColumnBase3");
                                    index++;
                                    break;
                                case "wrl":
                                    drawableLevelLayer.add(index,"waterRimLeft");
                                    index++;
                                    break;
                                case "wrr":
                                    drawableLevelLayer.add(index,"waterRimRight");
                                    index++;
                                    break;

                                case "trap":
                                    index++;
                                    break;
                                }
                            }
                        } else if (levelLayer.get(index).matches("2+f+.+")) {
                            StringBuffer sb = new StringBuffer(i);
                            sb.delete(0, 2);
                            String str = sb.toString();
                            //floor + columntop4 + fire

                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 3);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "froof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "froof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "froof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "froof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
                            } else {



                                switch (str) {
                                    case "col10fire":
                                        drawableLevelLayer.add(index, "fcol10fire");
                                        index++;
                                        break;

                                }
                                if (str.matches("(coldo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfldo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfltu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cflio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfldb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfltb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cflib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csldo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csltu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csldb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csltb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cslib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSldb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSltb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSlib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csfdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csftb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csfib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSfdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSftb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSfib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csfdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csftu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csfio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSfdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSftu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cSfio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldd[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltd[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(colid[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coltB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coliB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coldB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csldB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csltB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(csliB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coftb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coftu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cottu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cotdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cotio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cottb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cotdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cotib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTtb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTtu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coTio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(coftB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofdB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cofiB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(covib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfftB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfftu[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffdo[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffio[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffdB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffiB[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cfftb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffdb[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                if (str.matches("(cffib[0-9]+)")) {
                                    drawableLevelLayer.add(index, str);
                                    index++;
                                    break;
                                }
                                switch (str) {
                                    case "cand":
                                        drawableLevelLayer.add(index,"cand");
                                        index++;
                                        break;
                                    case "cands":
                                        drawableLevelLayer.add(index,"cands");
                                        index++;
                                        break;
                                    case "2cand":
                                        drawableLevelLayer.add(index,"2cand");
                                        index++;
                                        break;
                                    case "2cands":
                                        drawableLevelLayer.add(index,"2cands");
                                        index++;
                                        break;
                                    case "cob":
                                        drawableLevelLayer.add(index,"cobweb");
                                        index++;
                                        break;
                                    //enemy
                                    case "enemy":
                                        drawableLevelLayer.add(index, "enemySkull");
                                        index++;
                                        break;
                                    case "enemy2":
                                        drawableLevelLayer.add(index, "enemySpider");
                                        index++;
                                        break;
                                    case "enemy3":
                                        drawableLevelLayer.add(index, "enemyGhost");
                                        index++;
                                        break;
                                    case "enemy4":
                                        drawableLevelLayer.add(index, "enemyCyclops");
                                        index++;
                                        break;
                                    case "2enemy":
                                        drawableLevelLayer.add(index, "enemySkull2");
                                        index++;
                                        break;
                                    case "2enemy2":
                                        drawableLevelLayer.add(index, "enemySpider2");
                                        index++;
                                        break;
                                    case "2enemy3":
                                        drawableLevelLayer.add(index, "enemyGhost2");
                                        index++;
                                        break;
                                    case "2enemy4":
                                        drawableLevelLayer.add(index, "enemyCyclops2");
                                        index++;
                                        break;
                                    case "boss1":
                                        drawableLevelLayer.add(index, "bossMinotaur");
                                        index++;
                                        break;
                                    //shopkeeper entity
                                    case "shop":
                                        drawableLevelLayer.add(index, "shop");
                                        index++;
                                        break;
                                    //tutorial text in starting room
                                    case "tuto":
                                        drawableLevelLayer.add(index, "tuto");
                                        index++;
                                        break;
                                    //pots
                                    case "pot":
                                        drawableLevelLayer.add(index, "pot");
                                        index++;
                                        break;
                                    case "pot2":
                                        drawableLevelLayer.add(index, "pot2");
                                        index++;
                                        break;
                                    case "coin":
                                        drawableLevelLayer.add(index, "coin");
                                        index++;
                                        break;
                                    case "skull":
                                        drawableLevelLayer.add(index, "skull");
                                        index++;
                                        break;
                                    //pedestals
                                    case "ped1":
                                        drawableLevelLayer.add(index, "fped1");
                                        index++;
                                        break;
                                    case "ped1fire":
                                        drawableLevelLayer.add(index, "fped1fire");
                                        index++;
                                        break;
                                    case "ped1fireoff":
                                        drawableLevelLayer.add(index, "fped1fireoff");
                                        index++;
                                        break;
                                    case "ped1fireb":
                                        drawableLevelLayer.add(index, "fped1fireB");
                                        index++;
                                        break;
                                    case "ped1heal":
                                        drawableLevelLayer.add(index, "fped1heal");
                                        index++;
                                        break;
                                    case "ped2":
                                        drawableLevelLayer.add(index, "fped2");
                                        index++;
                                        break;
                                    case "ped2heal":
                                        drawableLevelLayer.add(index, "fped2heal");
                                        index++;
                                        break;
                                    case "ped2fire":
                                        drawableLevelLayer.add(index, "fped2fire");
                                        index++;
                                        break;
                                    case "ped2fireoff":
                                        drawableLevelLayer.add(index, "fped2fireoff");
                                        index++;
                                        break;
                                    case "ped3":
                                        drawableLevelLayer.add(index, "fped3");
                                        index++;
                                        break;
                                    case "ped3fire":
                                        drawableLevelLayer.add(index, "fped3fire");
                                        index++;
                                        break;
                                    case "ped4":
                                        drawableLevelLayer.add(index, "fped4");
                                        index++;
                                        break;
                                    //obstacles
                                    case "ob":
                                        drawableLevelLayer.add(index, "obstacle");
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
                        } else if (levelLayer.get(index).matches("l+w+.+")) {
                            StringBuffer sb2 = new StringBuffer(i);
                            sb2.delete(0, 2);
                            String str = sb2.toString();

                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 2);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "LWroof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "LWroof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "LWroof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "LWroof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
                            }
                        }
                        else if (levelLayer.get(index).matches("r+w+.+")) {
                            StringBuffer sb2 = new StringBuffer(i);
                            sb2.delete(0, 2);
                            String str = sb2.toString();

                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 2);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "RWroof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "RWroof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "RWroof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "RWroof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
                            }
                        }
                        else if (levelLayer.get(index).matches("b+w+.+")) {
                            StringBuffer sb2 = new StringBuffer(i);
                            sb2.delete(0, 2);
                            String str = sb2.toString();

                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 2);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "BWroof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "BWroof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "BWroof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "BWroof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
                            }
                        }

                        else if (levelLayer.get(index).matches("t+w+.+")) {
                        StringBuffer sb2 = new StringBuffer(i);
                        sb2.delete(0, 2);
                        String str = sb2.toString();


                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 2);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "TWroof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "TWroof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "TWroof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "TWroof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                    case "7":
                                        String roofFinal7;
                                        roofFinal7 = "TWroof7" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal7);
                                        break;
                                }
                                index++;
                            }

                            switch (str) {
                                case "col10fire":
                                    drawableLevelLayer.add(index, "twCol10Fire");
                                    index++;
                                    break;
                            }
                        switch (str) {
                            case "col1":
                                drawableLevelLayer.add(index, "twColTop1");
                                index++;
                                break;
                            case "col2":
                                drawableLevelLayer.add(index, "twColTop2");
                                index++;
                                break;
                            case "col3":
                                drawableLevelLayer.add(index, "twColTop3");
                                index++;
                                break;
                            case "col10":
                                drawableLevelLayer.add(index, "twColTop4");
                                index++;
                                break;
                            case "col11":
                                drawableLevelLayer.add(index, "twColTop5");
                                index++;
                                break;
                            case "col4":
                                drawableLevelLayer.add(index, "twColStem");
                                index++;
                                break;
                            case "col5":
                                drawableLevelLayer.add(index, "twColStem2");
                                index++;
                                break;
                            case "col6":
                                drawableLevelLayer.add(index, "twColStem3");
                                index++;
                                break;
                            case "col12":
                                drawableLevelLayer.add(index, "twColStem4");
                                index++;
                                break;
                            case "col13":
                                drawableLevelLayer.add(index, "twColStem5");
                                index++;
                                break;
                        }
                    }
                        else if (levelLayer.get(index).matches("t+o+r+u+.+")) {
                            StringBuffer sb2 = new StringBuffer(i);
                            sb2.delete(0, 4);
                            String str = sb2.toString();
                            switch (str) {
                                case "col1":
                                    drawableLevelLayer.add(index, "toruColTop1");
                                    index++;
                                    break;
                                case "col2":
                                    drawableLevelLayer.add(index, "toruColTop2");
                                    index++;
                                    break;
                                case "col3":
                                    drawableLevelLayer.add(index, "toruColTop3");
                                    index++;
                                    break;
                                case "col10":
                                    drawableLevelLayer.add(index, "toruColTop4");
                                    index++;
                                    break;
                                case "col11":
                                    drawableLevelLayer.add(index, "toruColTop5");
                                    index++;
                                    break;
                                case "col4":
                                    drawableLevelLayer.add(index, "toruColStem1");
                                    index++;
                                    break;
                                case "col5":
                                    drawableLevelLayer.add(index, "toruColStemDamaged1");
                                    index++;
                                    break;
                                case "col6":
                                    drawableLevelLayer.add(index, "toruColStemStemDamaged1");
                                    index++;
                                    break;
                                case "col12":
                                    drawableLevelLayer.add(index, "toruColStem2");
                                    index++;
                                    break;
                                case "col13":
                                    drawableLevelLayer.add(index, "toruColStem3");
                                    index++;
                                    break;
                            }
                        }
                        else if (levelLayer.get(index).matches("b+l+t+.+")) {
                            StringBuffer sb2 = new StringBuffer(i);
                            sb2.delete(0, 3);
                            String str = sb2.toString();
                            switch (str) {
                                case "col1":
                                    drawableLevelLayer.add(index, "bltColTop1");
                                    index++;
                                    break;
                                case "col2":
                                    drawableLevelLayer.add(index, "bltColTop2");
                                    index++;
                                    break;
                                case "col3":
                                    drawableLevelLayer.add(index, "bltColTop3");
                                    index++;
                                    break;
                                case "col10":
                                    drawableLevelLayer.add(index, "bltColTop4");
                                    index++;
                                    break;
                                case "col11":
                                    drawableLevelLayer.add(index, "bltColTop5");
                                    index++;
                                    break;
                                case "col4":
                                    drawableLevelLayer.add(index, "bltColStem1");
                                    index++;
                                    break;
                                case "col5":
                                    drawableLevelLayer.add(index, "bltColStemDamaged1");
                                    index++;
                                    break;
                                case "col6":
                                    drawableLevelLayer.add(index, "bltColStemStemDamaged1");
                                    index++;
                                    break;
                                case "col12":
                                    drawableLevelLayer.add(index, "bltColStem2");
                                    index++;
                                    break;
                                case "col13":
                                    drawableLevelLayer.add(index, "bltColStem3");
                                    index++;
                                    break;
                            }
                        }
                        else if (levelLayer.get(index).matches("b+r+t+.+")) {
                            StringBuffer sb2 = new StringBuffer(i);
                            sb2.delete(0, 3);
                            String str = sb2.toString();
                            switch (str) {
                                case "col1":
                                    drawableLevelLayer.add(index, "brtColTop1");
                                    index++;
                                    break;
                                case "col2":
                                    drawableLevelLayer.add(index, "brtColTop2");
                                    index++;
                                    break;
                                case "col3":
                                    drawableLevelLayer.add(index, "brtColTop3");
                                    index++;
                                    break;
                                case "col10":
                                    drawableLevelLayer.add(index, "brtColTop4");
                                    index++;
                                    break;
                                case "col11":
                                    drawableLevelLayer.add(index, "brtColTop5");
                                    index++;
                                    break;
                                case "col4":
                                    drawableLevelLayer.add(index, "brtColStem1");
                                    index++;
                                    break;
                                case "col5":
                                    drawableLevelLayer.add(index, "brtColStemDamaged1");
                                    index++;
                                    break;
                                case "col6":
                                    drawableLevelLayer.add(index, "brtColStemStemDamaged1");
                                    index++;
                                    break;
                                case "col12":
                                    drawableLevelLayer.add(index, "brtColStem2");
                                    index++;
                                    break;
                                case "col13":
                                    drawableLevelLayer.add(index, "brtColStem3");
                                    index++;
                                    break;
                            }
                        }
                        else if (levelLayer.get(index).matches("t+l+w+.+")) {
                        StringBuffer sb2 = new StringBuffer(i);
                        sb2.delete(0, 3);
                        String str = sb2.toString();

                        //    drawableLevelLayer.add(index, "topLeftWallTile");


                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 3);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "roof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "roof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "roof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "roof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
                            }


                    }
                        else if (levelLayer.get(index).matches("t+l+f+.+")) {
                            StringBuffer sb2 = new StringBuffer(i);
                            sb2.delete(0, 3);
                            String str = sb2.toString();

                            //    drawableLevelLayer.add(index, "topLeftWallTile");


                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 3);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "Froof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "Froof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "Froof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "Froof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
                            }


                        }
                        else if (levelLayer.get(index).matches("t+f+.+")) {
                        StringBuffer sb2 = new StringBuffer(i);
                        sb2.delete(0, 2);
                        String str = sb2.toString();

                        //    drawableLevelLayer.add(index, "topLeftWallTile");


                        if (str.matches("([0-9])+")) {

                            String roof = str;
                            String strRoofExt = "0";

                            StringBuffer sb3 = new StringBuffer(roof);
                            sb3.delete(1, 2);
                            String strRoofType = sb3.toString();

                            StringBuffer sb4 = new StringBuffer(str);
                            sb4.delete(0, 1);
                            if (!sb4.toString().isEmpty()) {
                                strRoofExt = sb4.toString();
                            }

                            switch (strRoofType) {
                                case "1":
                                    String roofFinal1;
                                    roofFinal1 = "TFroof1" + strRoofExt;
                                    drawableLevelLayer.add(index, roofFinal1);
                                    break;
                                case "2":
                                    String roofFinal2;
                                    roofFinal2 = "TFroof2" + strRoofExt;
                                    drawableLevelLayer.add(index, roofFinal2);
                                    break;
                                case "3":
                                    String roofFinal3;
                                    roofFinal3 = "TFroof3" + strRoofExt;
                                    drawableLevelLayer.add(index, roofFinal3);
                                    break;
                                case "4":
                                    String roofFinal4;
                                    roofFinal4 = "TFroof4" + strRoofExt;
                                    drawableLevelLayer.add(index, roofFinal4);
                                    break;
                            }
                            index++;
                        }


                    }
                        else if (levelLayer.get(index).matches("b+f+.+")) {
                            StringBuffer sb2 = new StringBuffer(i);
                            sb2.delete(0, 2);
                            String str = sb2.toString();

                            //    drawableLevelLayer.add(index, "topLeftWallTile");


                            if (str.matches("([0-9])+")) {

                                String roof = str;
                                String strRoofExt = "0";

                                StringBuffer sb3 = new StringBuffer(roof);
                                sb3.delete(1, 2);
                                String strRoofType = sb3.toString();

                                StringBuffer sb4 = new StringBuffer(str);
                                sb4.delete(0, 1);
                                if (!sb4.toString().isEmpty()) {
                                    strRoofExt = sb4.toString();
                                }

                                switch (strRoofType) {
                                    case "1":
                                        String roofFinal1;
                                        roofFinal1 = "BFroof1" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal1);
                                        break;
                                    case "2":
                                        String roofFinal2;
                                        roofFinal2 = "BFroof2" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal2);
                                        break;
                                    case "3":
                                        String roofFinal3;
                                        roofFinal3 = "BFroof3" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal3);
                                        break;
                                    case "4":
                                        String roofFinal4;
                                        roofFinal4 = "BFroof4" + strRoofExt;
                                        drawableLevelLayer.add(index, roofFinal4);
                                        break;
                                }
                                index++;
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

                case "doortl":
                case "doorftl":
                    //TODO: Make function in AlignDoors that takes roomX levelY, current and previous direction
                    //String topLeftX = Integer.toString((roomX + index) + 16);
                    map.remove("TopLeft");
                    String topLeftXF = Integer.toString(roomX);
                    String topLeftYF = Integer.toString(levelY);
                    String topLeftF = topLeftXF + "," + topLeftYF;
                    map.put("TopLeft", topLeftF);
                    //System.out.println(map.get("TopLeft") + " MAP TOPLEFT DOOR X AND Y VALUES");
                    index++;
                    break;
                //System.out.println(map.get("TopLeft") + " MAP TOPLEFT DOOR X AND Y VALUES");
                case "doortr":
                case "doorftr":
                    map.remove("TopRight");
                        String topRightX = Integer.toString(roomX);
                        String topRightY = Integer.toString(levelY);
                        String topRight = topRightX + "," + topRightY;
                        map.put("TopRight", topRight);
                        //System.out.println(map.get("TopRight") + " MAP TOPRIGHT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorul":
                case "doorful":
                    map.remove("UpperLeft");
                        String upperLeftX = Integer.toString(roomX);
                        String upperLeftY = Integer.toString(levelY);
                        String upperLeft = upperLeftX + "," + upperLeftY;
                        map.put("UpperLeft", upperLeft);
                        //System.out.println(map.get("UpperLeft") + " MAP UPPERLEFT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorll":
                case "doorfll":
                    map.remove("LowerLeft");
                        String lowerLeftX = Integer.toString(roomX);
                        String lowerLeftY = Integer.toString(levelY);
                        String lowerLeft = lowerLeftX + "," + lowerLeftY;
                        map.put("LowerLeft", lowerLeft);
                        //System.out.println(map.get("LowerLeft") + " MAP LOWERLEFT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorur":
                case "doorfur":
                    map.remove("UpperRight");
                        String upperRightX = Integer.toString(roomX);
                        String upperRightY = Integer.toString(levelY);
                        String upperRight = upperRightX + "," + upperRightY;
                        map.put("UpperRight", upperRight);
                        //System.out.println(map.get("UpperRight") + " MAP UPPERRIGHT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorlr":
                case "doorflr":
                    map.remove("LowerRight");
                        String lowerRightX = Integer.toString(roomX);
                        String lowerRightY = Integer.toString(levelY);
                        String lowerRight = lowerRightX + "," + lowerRightY;
                        map.put("LowerRight", lowerRight);
                        //System.out.println(map.get("LowerRight") + " MAP LOWERRIGHT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorbl":
                case "doorfbl":
                    map.remove("BottomLeft");
                        String bottomLeftX = Integer.toString(roomX);
                        String bottomLeftY = Integer.toString(levelY);
                        String bottomLeft = bottomLeftX + "," + bottomLeftY;
                        map.put("BottomLeft", bottomLeft);
                        //System.out.println(map.get("BottomLeft") + " MAP BOTTOMLEFT DOOR X AND Y VALUES");
                    index++;
                    break;
                case "doorbr":
                case "doorfbr":
                    map.remove("BottomRight");
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