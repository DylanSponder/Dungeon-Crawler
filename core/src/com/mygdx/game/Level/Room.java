package com.mygdx.game.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.entity.behaviours.fsm.Bone;
import com.mygdx.game.entity.behaviours.fsm.Lock;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    public int x1, x2, y1, y2;
    public int roomNum, roomSize, longestRow, directionTaken, index;
    public HashMap<String, String> doorLocations;
    public ArrayList<Fixture> doorFixtures;
    public TiledMapTileLayer roomLayer;
    public int enemyCounter;
    public Fixture roomHitbox;
    public ArrayList<Lock> locks;
    public ArrayMap<String, Door> doorArrayMap;

    public Room() {
        doorLocations = new HashMap();
        doorFixtures = new ArrayList<Fixture>();
        locks = new ArrayList<Lock>();
        doorArrayMap = new ArrayMap<String, Door>();
    }

    public void unlockDoors(World world, Room room) {
        System.out.println("Unlocking doors...");
        switch (room.directionTaken) {
            case 1:
                Door topLeftDoor = doorArrayMap.get("TopLeft");
                topLeftDoor.doorHitbox.setSensor(true);

                Door topRightDoor = doorArrayMap.get("TopRight");
                topRightDoor.doorHitbox.setSensor(true);

                //locks.remove()

                break;
            case 2:
                Door upperLeftDoor = doorArrayMap.get("UpperLeft");
                upperLeftDoor.doorHitbox.setSensor(true);

                Door lowerLeftDoor = doorArrayMap.get("LowerLeft");
                lowerLeftDoor.doorHitbox.setSensor(true);
                break;
            case 3:
                Door bottomLeftDoor = doorArrayMap.get("BottomLeft");
                bottomLeftDoor.doorHitbox.setSensor(true);

                Door bottomRightDoor = doorArrayMap.get("BottomRight");
                bottomRightDoor.doorHitbox.setSensor(true);
                break;
            case 4:
                Door upperRightDoor = doorArrayMap.get("UpperRight");
                upperRightDoor.doorHitbox.setSensor(true);

                Door lowerRightDoor = doorArrayMap.get("LowerRight");
                lowerRightDoor.doorHitbox.setSensor(true);
                break;
        }
    }

    public void lockDoors(World world, Room room) {
        System.out.println("Locking doors...");
        switch (room.directionTaken) {
            case 1:
                String dL = room.doorLocations.get("TopLeft");
                String dR = room.doorLocations.get("TopRight");
                break;
            case 2:
            case 3:
            case 4:

        }
    }
}
