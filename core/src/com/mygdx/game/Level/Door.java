package com.mygdx.game.level;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.CreateTexture;
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

     this.doorHitbox = bodyFactory.createDoorHitbox(doorBody);

     this.doorHitbox.setUserData(doorName);

    }

    public static void renderOpen(SpriteBatch batch, int direction, float x, float y) {

        final CreateTexture tx = CreateTexture.getInstance();

        if (direction == 3){
            batch.draw(tx.doorBottomLeftOpenTexture,x,y-8,0,0,16,16,1,1,0);
            batch.draw(tx.doorBottomRightOpenTexture,x,y-8,0,0,16,16,1,1,0);
            //repeat method from here - make sure sprite batch is above player
        }
        else if (direction == 1){
            batch.draw(tx.lockUpTexture,x,y-8,0,0,16,16,1,1,0);
        }
        else if (direction == 4){
            batch.draw(tx.lockLeftTexture,x-8,y-16,0,0,16,16,1,1,0);
        }
        else if (direction == 2){
            batch.draw(tx.lockRightTexture,x-8,y-16,0,0,16,16,1,1,0);
        }
    }



    //old method for corridor/door alignment
    /*
        public void AlignDoors(boolean startingRoom, Room r, List<Room> rooms, int roomIndex, HashMap<String, String> map, int roomX, int levelY) {

            }
     */
}
