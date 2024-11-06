package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.OrderedMap;
import com.mygdx.game.entity.behaviours.fsm.drops.Skull;

import java.util.ArrayList;
import java.util.Iterator;

import static com.mygdx.game.DungeonCrawler.*;

public class GameObjectDestroyer {

    public void destroyObject(ArrayMap<Object, Object> map,
                              ArrayList brokenList,
                              ArrayList objectList,
                              String objectList2,
                              Object obj,
                              Body objBody,
                              SpriteBatch batch,
                              boolean reversed) {

        final CreateAssets tx = CreateAssets.getInstance();

        if (!map.isEmpty()) {
            for (OrderedMap.Entry<Object, Object> entry : map.entries()) {
                Object value = entry.value;
                Object key = entry.key;

                batch.begin();
                switch (objBody.getUserData().toString()) {
                    case "Arrow":
                        break;
                    case "Skull":
                        for (Skull s : skulls){
                            if (value == s) {
                                if (s.SKULL_HEALTH < 1.5f){
                                    Skull.renderSkull(batch, tx.damagedSkullSprite, s.skullBody.getPosition().x, s.skullBody.getPosition().y);
                                }else {
                                    Skull.renderSkull(batch, tx.skullSprite, s.skullBody.getPosition().x, s.skullBody.getPosition().y);
                                }
                            }
                        }

                        break;
                    case "Bone":
                        break;
                    case "Pot":
                        break;
                }


                //switch statement needs to go here
                /*


                 */
                batch.end();
            }

            if (!reversed){
                map.reverse();
                reversed = true;
            }

            Iterator<Object> iterator = brokenList.iterator();
            if (iterator.hasNext()) {
                Object objIt = iterator.next();
                if (brokenList.contains(objIt)) {
                    //needs another switch statement for drops

                    /*
                    Bone bone = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, false, 0);
                    bone.createBone();
                    objectList.add(bone);
                    map.put(bone.boneBody, bone);

                    Bone bone2 = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, true, bone.orientation);
                    bone2.createBone();
                    objectList.add(bone2);
                    map.put(bone2.boneBody, bone2);

                     */

                    /*
                    objectList.remove(skull);
                    skullArrayMap.removeKey(skull.skullBody);
                    world.destroyBody(skull.skullBody);
                    skullIt.remove();

                     */
                }
            }
        }
    }
}
