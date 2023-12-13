package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class DungeonCrawler extends ApplicationAdapter {
	SpriteBatch batch;

	Texture roomBackground;

	private OrthographicCamera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		roomBackground = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetFloor.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

	}

	@Override
	public void render () {

		camera.update();
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		batch.draw(roomBackground, 1, 1);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
