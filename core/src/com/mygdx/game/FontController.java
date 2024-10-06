package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.objects.Text;

public class FontController {

    public static void drawFont(SpriteBatch inventoryBatch, BitmapFont font, float x, float y, Text text2) {

        System.out.println("HELLO I AM DRAWING FONT");
        font.draw(inventoryBatch, text2.message, x, y);

        if (text2.hasSprite){
            inventoryBatch.draw(text2.sprite,text2.textX-16,text2.textY);
        }
    }

    public static void drawFadingFont(SpriteBatch fontBatch, BitmapFont font, float x, float y, Text text) {

        System.out.println("HELLO I SUCK");

            font.draw(fontBatch, text.message, x, y);
            if (text.hasSprite){
                fontBatch.draw(text.sprite,text.textX-16,text.textY);
            }
            if (text.fade) {
                    if (text.fadeTiming >= 0 ){
                        font.setColor(1,1,1, text.fadeTiming);
                        text.fadeTiming = text.fadeTiming - 0.0045f;
                } else {
                        text.fadeTiming = text.fadeTime;
                        text.showing = false;
                    }
            }

    }


}
