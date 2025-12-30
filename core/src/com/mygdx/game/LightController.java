package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.level.objects.Fire;

import java.util.ArrayList;

public class LightController {

    public LightController() {


    }

    public void fadeLight(ArrayList<Fire> lights) {
        //lights fade randomly - may be better to have them slowly dim in and out using limits but this solution is OK
        int randLight = 0;
        for (Fire light : lights) {

            //random light fade timing
            randLight = Random.randomInt(55,35);
            if (light.lightTimeElapsed > Gdx.graphics.getDeltaTime() * randLight) {
                light.lightTimeElapsed = 0;
                if (light.type == 1) {
                    //fires
                    light.randLightLevel = Random.randomInt(700,550);
                }
                if (light.type == 2) {
                    //spawner flames
                    light.randLightLevel = Random.randomInt(700,550);
                }
                if (light.type == 3) {
                    //small flames - candles, torches and fire arrows
                    light.randLightLevel = Random.randomInt(600,450);//550
                }
                if (light.type == 5) {
                    //blue flame
                    light.randLightLevel = Random.randomInt(500,400);//550
                }
                if (light.type == 6) {
                    light.randLightLevel = Random.randomInt(500,400);//550
                }
                //convert to float proper
                light.randLightLevel = light.randLightLevel/1000;

                light.light.setColor(light.light.getColor().r,light.light.getColor().g, light.light.getColor().b,light.randLightLevel);

            } else {
                light.lightTimeElapsed = light.lightTimeElapsed + Gdx.graphics.getDeltaTime();
            }
        }
    }
}
