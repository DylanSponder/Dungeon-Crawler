package com.mygdx.game.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.behaviours.fsm.Enemy;

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

    public void unlockDoor(World world, Room room) {
        System.out.println("Unlocking doors...");
        String s = room.doorLocations.get("TopLeft");

        //world.getBodies(new Array<Body>());

    }
}
