package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.level.objects.Text;

public class FontController {

    public static void drawInventoryFont(SpriteBatch inventoryBatch, BitmapFont font, float x, float y, Text text2) {
        font.getData().setScale(0.6f);
        font.setUseIntegerPositions(false);
        font.draw(inventoryBatch, text2.message, x, y);

        if (text2.hasSprite && text2.sprite == CreateAssets.getInstance().potionItemSprite){
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-10f,9,11,9,11,0.6f,0.6f,0);
        }
        else if((text2.hasSprite && text2.sprite == CreateAssets.getInstance().torchItemSprite)) {
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-11.5f,10,15,10,15,0.6f,0.6f,0);
        }
        else if((text2.hasSprite && text2.sprite == CreateAssets.getInstance().shieldItemSprite)) {
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-10f,11,11,11,11,0.6f,0.6f,0);
        }
        else if((text2.hasSprite && text2.sprite == CreateAssets.getInstance().beltItemSprite)) {
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-10f,13,12,13,12,0.6f,0.6f,0);
        }
        else if((text2.hasSprite && text2.sprite == CreateAssets.getInstance().chiselItemSprite)) {
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-10f,13,13,13,13,0.6f,0.6f,0);
        }
        else if((text2.hasSprite && text2.sprite == CreateAssets.getInstance().greekfireItemSprite)) {
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-13f,14,15,14,15,0.6f,0.6f,0);
        }
        else if (text2.hasSprite && text2.sprite == CreateAssets.getInstance().coinHUDSprite) {
            inventoryBatch.draw(text2.sprite,text2.textX+text2.offset,text2.textY-9.3f,10,10,9,9,0.6f,0.6f,0);
        }

    }

    public static void drawFadingFont(SpriteBatch fontBatch, BitmapFont font2, float x, float y, Text text, float fontScale) {
            font2.getData().setScale(fontScale);

            font2.setColor(text.color);


            if (text.hasSprite){
          //      fontBatch.draw(text.sprite,text.textX-16,text.textY);
            }
         //   if (text.fade) {

                    if (text.fadeTiming >= 0 ){
                        System.out.println(text.fadeTiming);
                        font2.setColor(text.color);
                        text.color.a = text.fadeTiming;
                        //font2.setColor(1,1,1, text.fadeTiming);
                        text.fadeTiming = text.fadeTiming - 0.0045f;
                } else {
                        text.fadeTiming = text.fadeTime;
                        text.showing = false;
                    }
           // }
        font2.draw(fontBatch, text.message, x, y);

    }

    public static void drawDriftingFont(SpriteBatch fontBatch, BitmapFont font2, float x, float y, Text text, int speed) {
        //TODO: Finish implementation - this is meant for 'floating money leaving the players total money and disappearing after a shop purchase'
        int x2;
        int y2;

        font2.draw(fontBatch, text.message, x, y);

        x = x + speed;
        y = y + speed;

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
