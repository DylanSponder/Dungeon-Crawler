package com.mygdx.game.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
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
    public ArrayList<Door> doors;
    public ArrayMap<String, Door> doorArrayMap;

    public Room() {
        doorLocations = new HashMap();
        doorFixtures = new ArrayList<Fixture>();
        doorArrayMap = new ArrayMap<String, Door>();
        locks = new ArrayList<Lock>();
        doors = new ArrayList<Door>();
    }

    public void createLocks(World world) {
        for (Door d : doors) {
            if (d.doorName == "TopLeft") {
                Lock lock = new Lock(world, d.doorX, d.doorY, 1);
                lock.createLock();
                locks.add(lock);
            }
            if (d.doorName == "BottomLeft") {
                Lock lock = new Lock(world, d.doorX, d.doorY, 3);
                lock.createLock();
                locks.add(lock);
            }
            if (d.doorName == "UpperLeft") {
                Lock lock = new Lock(world, d.doorX, d.doorY, 4);
                lock.createLock();
                locks.add(lock);
            }
            if (d.doorName == "UpperRight") {
                Lock lock = new Lock(world, d.doorX, d.doorY, 2);
                lock.createLock();
                locks.add(lock);
            }
        }
    }

    public void unlockAllDoors(World world, Room room, boolean startingRoom) {
        System.out.println("Unlocking doors...");

        if (startingRoom) {
            switch (room.directionTaken) {
                case 1:
                    Door topLeftDoor = doorArrayMap.get("TopLeft");
                    topLeftDoor.doorHitbox.setSensor(true);
                    Door topRightDoor = doorArrayMap.get("TopRight");
                    topRightDoor.doorHitbox.setSensor(true);
                    break;
                case 2:
                    Door upperRightDoor = doorArrayMap.get("UpperRight");
                    upperRightDoor.doorHitbox.setSensor(true);
                    Door lowerRightDoor = doorArrayMap.get("LowerRight");
                    lowerRightDoor.doorHitbox.setSensor(true);
                    break;
                case 3:
                    Door bottomLeftDoor = doorArrayMap.get("BottomLeft");
                    bottomLeftDoor.doorHitbox.setSensor(true);
                    Door bottomRightDoor = doorArrayMap.get("BottomRight");
                    bottomRightDoor.doorHitbox.setSensor(true);
                    break;
                case 4:
                    Door upperLeftDoor = doorArrayMap.get("UpperLeft");
                    upperLeftDoor.doorHitbox.setSensor(true);
                    Door lowerLeftDoor = doorArrayMap.get("LowerLeft");
                    lowerLeftDoor.doorHitbox.setSensor(true);
                    break;
            }
        }
        else {
            for (Door d : doors) {
                d.doorHitbox.setSensor(true);
                for (Lock l : locks) {
                    l.visible = false;
                }
            }
        }
    }

    public void unlockDoor(World world, Room room, boolean startingRoom) {
        switch (room.directionTaken) {
            case 1:
                Door bottomLeftDoor = room.doorArrayMap.get("BottomLeft");
                bottomLeftDoor.doorHitbox.setSensor(true);
                Door bottomRightDoor = room.doorArrayMap.get("BottomRight");
                bottomRightDoor.doorHitbox.setSensor(true);
                break;
            case 2:
                Door upperLeftDoor = room.doorArrayMap.get("UpperLeft");
                upperLeftDoor.doorHitbox.setSensor(true);
                Door lowerLeftDoor = room.doorArrayMap.get("LowerLeft");
                lowerLeftDoor.doorHitbox.setSensor(true);
                break;
            case 3:
                Door topLeftDoor = room.doorArrayMap.get("TopLeft");
                topLeftDoor.doorHitbox.setSensor(true);
                Door topRightDoor = room.doorArrayMap.get("TopRight");
                topRightDoor.doorHitbox.setSensor(true);
                break;
            case 4:
                Door upperRightDoor = room.doorArrayMap.get("UpperRight");
                upperRightDoor.doorHitbox.setSensor(true);
                Door lowerRightDoor = room.doorArrayMap.get("LowerRight");
                lowerRightDoor.doorHitbox.setSensor(true);
                break;
        }
        for (Lock l : room.locks) {
            l.visible = false;
        }
    }

    public void lockDoors(World world, Room room, boolean visible) {
        System.out.println("Locking all doors in room " + room.roomNum);
        for (Door d : doors) {
            d.doorHitbox.setSensor(false);
            if (visible) {
                for (Lock l : locks) {
                    l.visible = true;
                }
            }
        }
    }

    public void lockDoor(World world, Room room) {

        System.out.println("Locking door in room " + room.roomNum);
        switch (room.directionTaken) {
            case 1:
                Door bottomLeftDoor = room.doorArrayMap.get("BottomLeft");
                bottomLeftDoor.doorHitbox.setSensor(false);
                Door bottomRightDoor = room.doorArrayMap.get("BottomRight");
                bottomRightDoor.doorHitbox.setSensor(false);
                for (Lock l : locks) {
                    if (l.direction == 3) {
                        l.visible = true;
                    }
                }
                break;
            case 2:
                Door upperLeftDoor = room.doorArrayMap.get("UpperLeft");
                upperLeftDoor.doorHitbox.setSensor(false);
                Door lowerLeftDoor = room.doorArrayMap.get("LowerLeft");
                lowerLeftDoor.doorHitbox.setSensor(false);
                for (Lock l : locks) {
                    if (l.direction == 4) {
                        l.visible = true;
                    }
                }
                break;
            case 3:
                Door topLeftDoor = room.doorArrayMap.get("TopLeft");
                topLeftDoor.doorHitbox.setSensor(false);
                Door topRightDoor = room.doorArrayMap.get("TopRight");
                topRightDoor.doorHitbox.setSensor(false);
                for (Lock l : locks) {
                    if (l.direction == 1) {
                        l.visible = true;
                    }
                }
                break;
            case 4:
                Door upperRightDoor = room.doorArrayMap.get("UpperRight");
                upperRightDoor.doorHitbox.setSensor(false);
                Door lowerRightDoor = room.doorArrayMap.get("LowerRight");
                lowerRightDoor.doorHitbox.setSensor(false);
                for (Lock l : locks) {
                    if (l.direction == 2) {
                        l.visible = true;
                    }
                }
                break;
        }



    }
}
