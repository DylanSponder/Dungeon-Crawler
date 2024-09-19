package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.CreateTexture;
import com.mygdx.game.DungeonCrawler;

public class Text {
    public BitmapFont font;
    public String message;
    public Color color;
    public boolean fade, showing, hasSprite;
    public float fadeTime, fadeTiming, fadeOutSpeed;
    public float textX, textY;
    public Sprite sprite;

    public Text(BitmapFont font, String message, Color color, boolean fade, float fadeTiming, float fadeOutSpeed, boolean showing) {
        this.font = font;
        this.message = message;
        this.color = color;
        this.fade = fade;
        this.fadeTime = fadeTiming;
        this.fadeTiming = fadeTiming;
        this.fadeOutSpeed = fadeOutSpeed;
        this.showing = showing;
        //this.sprite = sprite;
        //this.hasSprite = hasSprite;
    }

    public void createText() {



    }
}
