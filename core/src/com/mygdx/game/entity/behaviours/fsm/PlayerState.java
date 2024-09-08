package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum PlayerState implements State<Player> {

DETECT_ENEMY_LOS() {
    @Override
    public void enter(Player entity) {

    }

    @Override
    public void update(Player entity) {

    }

    @Override
    public void exit(Player entity) {

    }

    @Override
    public boolean onMessage(Player entity, Telegram telegram) {
        return false;
    }
}

}
