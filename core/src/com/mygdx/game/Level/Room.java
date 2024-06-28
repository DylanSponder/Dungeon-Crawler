package com.mygdx.game.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.entity.behaviours.fsm.Enemy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {
    public int x1, x2, y1, y2;
    public int roomNum, roomSize, longestRow, directionTaken, index;
    public HashMap<String, String> doorLocations;
    public TiledMapTileLayer roomLayer;
    public int enemyCounter;
    public Fixture roomHitbox;

    public Room() {
        doorLocations = new HashMap();
    }

    public void unlockDoors() {


    }
}
