package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.box2D.BodyFactory;

public class Door {

    public float doorX, doorY;
    public String doorName;
    private World world;
    public Body doorBody;
    public Fixture doorHitbox;
    public boolean open, opened, locked, upDown, createHitbox;
    public int roomNum;

    public Door(World world, String doorName, String doorLocation, float x, float y, boolean upDown, boolean createHitbox, int roomNum) {
     this.world = world;
     this.doorName = doorName;
     this.doorX = x;
     this.doorY = y;
     this.open = false;
     this.opened = false;
     this.locked = false;
     this.upDown = upDown;
     this.createHitbox = createHitbox;
     this.roomNum = roomNum;
    }

    public void createDoor() {

     BodyFactory bodyFactory = new BodyFactory();

     if (this.createHitbox) {
         this.doorBody = bodyFactory.createDoorBody(world, doorX, doorY, this.upDown);

         this.doorHitbox = bodyFactory.createDoorHitbox(doorBody, this.upDown);

         this.doorHitbox.setUserData(doorName);

     }



    }
    //renders the open door texture for the respective door on top of the door cell but below the player
    public void renderOpen(SpriteBatch batch, int direction, float x, float y) {

        final CreateAssets tx = CreateAssets.getInstance();

            if (doorName == "BottomLeft") {
                batch.draw(tx.doorBottomLeftOpenTexture,x,y,0,0,32,16,1,1,0);
            }
            //if (doorName == "BottomRight") {
            //    batch.draw(tx.doorBottomRightOpenTexture,x,y,0,0,16,16,1,1,0);
            //}
            if (doorName == "TopLeft") {
                batch.draw(tx.doorTopLeftOpenTexture,x,y,0,0,32,16,1,1,0);
            }
            //if (doorName == "TopRight") {
            //    batch.draw(tx.doorTopRightOpenTexture,x,y,0,0,16,16,1,1,0);
            //}
            if (doorName == "UpperRight") {
                batch.draw(tx.doorRightUpperOpenTexture,x,y-16,0,0,16,32,1,1,0);
            }
            //if (doorName == "LowerRight") {
            //    batch.draw(tx.doorRightLowerOpenTexture,x,y,0,0,16,16,1,1,0);
            //}
            if (doorName == "UpperLeft") {
                batch.draw(tx.doorLeftUpperOpenTexture,x,y-16,0,0,16,32,1,1,0);
            }
            //if (doorName == "LowerLeft") {
            //    batch.draw(tx.doorLeftLowerOpenTexture,x,y,0,0,16,16,1,1,0);
            //}
    }

    //old method for corridor/door alignment
    /*
        public void AlignDoors(boolean startingRoom, Room r, List<Room> rooms, int roomIndex, HashMap<String, String> map, int roomX, int levelY) {

            }
     */
}
