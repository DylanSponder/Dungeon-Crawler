package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.objects.Text;

public class FontController {

    public static void drawFont(SpriteBatch inventoryBatch, BitmapFont font, float x, float y, Text text2) {
        font.getData().setScale(0.8f);
        font.setUseIntegerPositions(false);
        font.draw(inventoryBatch, text2.message, x, y);

        if (text2.hasSprite && text2.sprite == CreateAssets.getInstance().potionSprite){
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-3f,9,11,9,11,0.8f,0.8f,0);
        }
        else if (text2.hasSprite && text2.sprite == CreateAssets.getInstance().coinSprite) {
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-1f,10,10,9,9,1f,1f,0);
        }
    }

    public static void drawFadingFont(SpriteBatch fontBatch, BitmapFont font2, float x, float y, Text text) {

            font2.draw(fontBatch, text.message, x, y);
            if (text.hasSprite){
          //      fontBatch.draw(text.sprite,text.textX-16,text.textY);
            }
            if (text.fade) {
                    if (text.fadeTiming >= 0 ){
                        font2.setColor(1,1,1, text.fadeTiming);
                        text.fadeTiming = text.fadeTiming - 0.0045f;
                } else {
                        text.fadeTiming = text.fadeTime;
                        text.showing = false;
                    }
            }

    }


}
