package com.mygdx.game.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    public int x1, x2, y1, y2;
    public int roomNum, roomSize, longestRow, directionTaken, index;
    public HashMap<String, String> doorLocations;
    public TiledMapTileLayer roomLayer;

    public Room() {
        doorLocations = new HashMap();
    }
}
