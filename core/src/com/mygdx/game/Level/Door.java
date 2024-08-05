package com.mygdx.game.level;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Door {

    public float doorX, doorY;
    public String doorName;
    private World world;
    public Body doorBody;
    public Fixture doorHitbox;

    public Door(World world, String doorName, String doorLocation, float x, float y) {
     this.world = world;
     this.doorName = doorName;
     this.doorX = x;
     this.doorY = y;
     /*
          String[] doorXYString = doorLocation.split(",");
     String doorX = doorXYString[0];
     this.doorX = Integer.valueOf(doorX);
     System.out.println(doorX);

     String doorY = doorXYString[1];
     this.doorY = Integer.valueOf(doorY);
        System.out.println(doorY);
      */

    }

    public void createDoor() {

     BodyFactory bodyFactory = new BodyFactory();

     this.doorBody = bodyFactory.createDoorBody(world, doorX, doorY);

     this.doorBody.setUserData(doorName);

     this.doorHitbox = bodyFactory.createDoorHitbox(doorBody);

    }

    //old method for corridor/door alignment
    /*
        public void AlignDoors(boolean startingRoom, Room r, List<Room> rooms, int roomIndex, HashMap<String, String> map, int roomX, int levelY) {

            }
     */
}
