package com.mygdx.game.level.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.entity.behaviours.fsm.EnemyGhost;
import com.mygdx.game.entity.behaviours.fsm.EnemySkull;
import com.mygdx.game.entity.behaviours.fsm.EnemySpider;

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
    public ArrayList<EnemySkull> enemySkulls;
    public ArrayList<EnemySpider> enemySpiders;
    public ArrayList<EnemyGhost> enemyGhosts;
    public ArrayList<Fire> spawners;

    public boolean isShop;

    public Room() {
        doorLocations = new HashMap();
        doorFixtures = new ArrayList<Fixture>();
        doorArrayMap = new ArrayMap<String, Door>();
        locks = new ArrayList<Lock>();
        doors = new ArrayList<Door>();
        enemySkulls = new ArrayList<EnemySkull>();
        enemySpiders = new ArrayList<EnemySpider>();
        enemyGhosts = new ArrayList<EnemyGhost>();
        spawners = new ArrayList<Fire>();
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

        if (startingRoom) {
            switch (room.directionTaken) {
                case 1:
                    Door topLeftDoor = room.doorArrayMap.get("TopLeft");
                    topLeftDoor.doorHitbox.setSensor(true);
                    topLeftDoor.locked = false;
                    Door topRightDoor = room.doorArrayMap.get("TopRight");
                    //topRightDoor.doorHitbox.setSensor(true);
                    topRightDoor.locked = false;
                    break;
                case 2:
                    Door upperRightDoor = room.doorArrayMap.get("UpperRight");
                    upperRightDoor.doorHitbox.setSensor(true);
                    upperRightDoor.locked = false;
                    Door lowerRightDoor = room.doorArrayMap.get("LowerRight");
                    //lowerRightDoor.doorHitbox.setSensor(true);
                    lowerRightDoor.locked = false;
                    break;
                case 3:
                    Door bottomLeftDoor = room.doorArrayMap.get("BottomLeft");
                    bottomLeftDoor.doorHitbox.setSensor(true);
                    bottomLeftDoor.locked = false;
                    Door bottomRightDoor = room.doorArrayMap.get("BottomRight");
                    //bottomRightDoor.doorHitbox.setSensor(true);
                    bottomRightDoor.locked = false;
                    break;
                case 4:
                    Door upperLeftDoor = room.doorArrayMap.get("UpperLeft");
                    upperLeftDoor.doorHitbox.setSensor(true);
                    upperLeftDoor.locked = false;
                    Door lowerLeftDoor = room.doorArrayMap.get("LowerLeft");
                    //lowerLeftDoor.doorHitbox.setSensor(true);
                    lowerLeftDoor.locked = false;
                    break;
            }
        }
        else {
            for (Door d : room.doors) {
                if (d.createHitbox) {
                    d.locked = false;
                    d.doorHitbox.setSensor(true);
                    for (Lock l : room.locks) {
                        l.visible = false;
                    }
                }
            }
        }
    }

    public void unlockDoor(World world, Room room, boolean startingRoom) {
        switch (room.directionTaken) {
            case 1:
                Door bottomLeftDoor = room.doorArrayMap.get("BottomLeft");
                bottomLeftDoor.doorHitbox.setSensor(true);
                bottomLeftDoor.locked = false;
                Door bottomRightDoor = room.doorArrayMap.get("BottomRight");
                //bottomRightDoor.doorHitbox.setSensor(true);
                bottomRightDoor.locked = false;
                break;
            case 2:
                Door upperLeftDoor = room.doorArrayMap.get("UpperLeft");
                upperLeftDoor.doorHitbox.setSensor(true);
                upperLeftDoor.locked = false;
                Door lowerLeftDoor = room.doorArrayMap.get("LowerLeft");
                //lowerLeftDoor.doorHitbox.setSensor(true);
                lowerLeftDoor.locked = false;
                break;
            case 3:
                Door topLeftDoor = room.doorArrayMap.get("TopLeft");
                topLeftDoor.doorHitbox.setSensor(true);
                topLeftDoor.locked = false;
                Door topRightDoor = room.doorArrayMap.get("TopRight");
                //topRightDoor.doorHitbox.setSensor(true);
                topRightDoor.locked = false;
                break;
            case 4:
                Door upperRightDoor = room.doorArrayMap.get("UpperRight");
                upperRightDoor.doorHitbox.setSensor(true);
                upperRightDoor.locked = false;
                Door lowerRightDoor = room.doorArrayMap.get("LowerRight");
                //lowerRightDoor.doorHitbox.setSensor(true);
                lowerRightDoor.locked = false;
                break;
        }
        for (Lock l : room.locks) {
            l.visible = false;
        }
    }

    public void lockAllDoors(World world, Room room, boolean visible) {
        for (Door d : room.doors) {
            if (d.createHitbox) {
                d.doorHitbox.setSensor(false);
            }
            if (visible) {
                for (Lock l : room.locks) {
                    d.locked = true;
                    l.visible = true;
                }
            }
        }
    }

    public void lockDoor(World world, Room room) {
        switch (room.directionTaken) {
            case 1:
                Door bottomLeftDoor = room.doorArrayMap.get("BottomLeft");
                bottomLeftDoor.doorHitbox.setSensor(false);
                bottomLeftDoor.locked = true;
                Door bottomRightDoor = room.doorArrayMap.get("BottomRight");
                //bottomRightDoor.doorHitbox.setSensor(false);
                bottomRightDoor.locked = true;
                for (Lock l : locks) {
                    if (l.direction == 3) {
                        l.visible = true;
                    }
                }
                break;
            case 2:
                Door upperLeftDoor = room.doorArrayMap.get("UpperLeft");
                upperLeftDoor.doorHitbox.setSensor(false);
                upperLeftDoor.locked = true;
                Door lowerLeftDoor = room.doorArrayMap.get("LowerLeft");
                //lowerLeftDoor.doorHitbox.setSensor(false);
                lowerLeftDoor.locked = true;
                for (Lock l : locks) {
                    if (l.direction == 4) {
                        l.visible = true;
                    }
                }
                break;
            case 3:
                Door topLeftDoor = room.doorArrayMap.get("TopLeft");
                topLeftDoor.doorHitbox.setSensor(false);
                topLeftDoor.locked = true;
                Door topRightDoor = room.doorArrayMap.get("TopRight");
                //topRightDoor.doorHitbox.setSensor(false);
                topRightDoor.locked = true;
                for (Lock l : locks) {
                    if (l.direction == 1) {
                        l.visible = true;
                    }
                }
                break;
            case 4:
                Door upperRightDoor = room.doorArrayMap.get("UpperRight");
                upperRightDoor.doorHitbox.setSensor(false);
                upperRightDoor.locked = true;
                Door lowerRightDoor = room.doorArrayMap.get("LowerRight");
               // lowerRightDoor.doorHitbox.setSensor(false);
                lowerRightDoor.locked = true;
                for (Lock l : locks) {
                    if (l.direction == 2) {
                        l.visible = true;
                    }
                }
                break;
        }
    }
}
