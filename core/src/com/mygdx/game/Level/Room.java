package com.mygdx.game.level;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    public int x1, x2, y1, y2;
    public int roomNum, roomSize, longestRow, directionTaken;
    public HashMap doorLocations;

    public Room() {
        doorLocations = new HashMap();
    }
}
