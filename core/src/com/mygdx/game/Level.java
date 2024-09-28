package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.level.objects.Room;
import com.mygdx.game.level.objects.Text;

import java.util.ArrayList;

public class Level {
    private int levelNo;
    private TiledMapTileLayer layer;
    private Text startMessage;
    private int startRoomNo;
    //private Music levelMusic;
    private ArrayList<Room> rooms;
    //we need a GenerateLevel class object that can take parameters
    //available room indexes and room file path
    //number of rooms to generate
    //secret rooms?

    public Level() {



    }
}
