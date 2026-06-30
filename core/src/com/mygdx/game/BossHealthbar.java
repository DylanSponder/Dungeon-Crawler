package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class BossHealthbar extends Actor {

    public static final float BAR_WIDTH = 80f;
    public static final float BAR_HEIGHT = 10f;

    public float currentHealth;
    public float maxHealth;
    public int type;


    public final Rectangle clipBounds = new Rectangle();
    public final Rectangle scissors = new Rectangle();

    public BossHealthbar(
            float maxHealth,
            int type
    ) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        setSize(BAR_WIDTH, BAR_HEIGHT);
    }

    public void setHealth(float health) {
        this.currentHealth = MathUtils.clamp(health, 0f, maxHealth);
    }

    public float getHealth() {
        return currentHealth;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //validate();

        final CreateAssets tx = CreateAssets.getInstance();

        float x = getX();
        float y = getY();

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        // Draw container
        batch.draw(tx.bossHealthbarContainer, x, y);

        // Draw empty bar
        batch.draw(tx.bossHealthbarEmpty, x + 6, y + 1, BAR_WIDTH, BAR_HEIGHT);

        float healthPercent = currentHealth / maxHealth;
        float visibleWidth = BAR_WIDTH * healthPercent;


        if (visibleWidth > 0f) {
            batch.flush();

            clipBounds.set(x, y, visibleWidth + 6, BAR_HEIGHT + 1);

            Stage stage = getStage();
            stage.calculateScissors(clipBounds, scissors);

            if (ScissorStack.pushScissors(scissors)) {
                batch.draw(tx.bossHealthbarFull, x + 6, y + 1, BAR_WIDTH, BAR_HEIGHT);
                batch.flush();
                ScissorStack.popScissors();
            }
        }
    }
}
