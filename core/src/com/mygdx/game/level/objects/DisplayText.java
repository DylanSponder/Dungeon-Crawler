package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DisplayText {
    public BitmapFont font;
    public String message;
    public Color color;
    public boolean fade, showing, hasSprite;
    public float fadeTime, fadeTiming, fadeOutSpeed, offset;
    public float textX, textY;
    public Sprite sprite;

    public DisplayText(BitmapFont font, String message, Color color, boolean fade, float fadeTiming, float fadeOutSpeed, boolean showing, boolean hasSprite, Sprite sprite, float spriteOffset) {
        this.font = font;
        this.message = message;
        this.color = color;
        this.fade = fade;
        this.fadeTime = fadeTiming;
        this.fadeTiming = fadeTiming;
        this.fadeOutSpeed = fadeOutSpeed;
        this.showing = showing;

        if (hasSprite) {
            this.hasSprite = hasSprite;
            this.sprite = sprite;
            this.offset = spriteOffset;
        }


    }

    public void createText(SpriteBatch fontBatch) {
        //fontBatch.draw();


    }
}
