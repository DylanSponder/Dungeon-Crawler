package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input.Keys;

public class DungeonCrawler extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerTexture;
	private Sprite player;
	private Sprite playerUp;
	private Sprite playerDown;
	private Sprite playerLeft;
	private Sprite playerRight;

	float playerSpeed = 120.f;
	float playerX = 300;
	float playerY = 240;

	Texture roomBackground;
	int roomSize = 3;
	int tileSize = 64;
	int roomPosX = 220;
	int roomPosY = 160;

	private OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		playerTexture = new Texture(Gdx.files.internal("NinjaAdventure/Actor/Characters/GoldKnight/SpriteSheet.png"));
		roomBackground = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/Interior/TilesetInteriorFloor.png"));

		//outline player Sprites
		player = 				new Sprite(playerTexture, 0, 0, 16, 16);
		playerUp = 				new Sprite(playerTexture, 16, 0, 16, 16);
		playerDown = 			new Sprite(playerTexture, 0, 0, 16, 16);
		playerLeft = 			new Sprite(playerTexture, 32,0,16,16);
		playerRight = 			new Sprite(playerTexture, 48, 0, 16, 16);

		//create camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600, 480);
	}

	@Override
	public void render () {
		camera.update();
		ScreenUtils.clear(1, 1, 1, 1);

		//create each tile of the room texture as a TextureRegion
		TextureRegion roomFloorTexture = new TextureRegion(roomBackground,0,0,16,16);
		TextureRegion roomLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomTopTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomBottomTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomCornerTopLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomCornerTopRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomCornerBottomLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomCornerBottomRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);

		//set TextureRegion source coordinates
		roomFloorTexture.setRegion(16,113,16,16);
		roomLeftTexture.setRegion(0,113,16,16);
		roomRightTexture.setRegion(32,113,16,16);
		roomTopTexture.setRegion(16,96,16,16);
		roomBottomTexture.setRegion(16,129,16,16);
		roomCornerTopLeftTexture.setRegion(0,96,16,16);
		roomCornerTopRightTexture.setRegion(32,96,16,16);
		roomCornerBottomLeftTexture.setRegion(0,129,16,16);
		roomCornerBottomRightTexture.setRegion(32,129,16,16);

		//create drawable tiling methods for each tile texture except for corners which are unique
		TiledDrawable roomFloor = new TiledDrawable(roomFloorTexture);
		TiledDrawable roomLeft = new TiledDrawable(roomLeftTexture);
		TiledDrawable roomRight = new TiledDrawable(roomRightTexture);
		TiledDrawable roomTop = new TiledDrawable(roomTopTexture);
		TiledDrawable roomBottom = new TiledDrawable(roomBottomTexture);

		//draw room textures into a simple square room - each batch can be used to create a room template (modifiable in this example by changing roomSize)
		batch.begin();
		roomTop.draw(batch, roomTopTexture, roomPosX, roomPosY+(tileSize*roomSize),tileSize*roomSize,tileSize,4f,0);
		roomFloor.draw(batch, roomFloorTexture, roomPosX, roomPosY,tileSize*roomSize,tileSize*roomSize,4f,0);
		roomLeft.draw(batch, roomLeftTexture, roomPosX-tileSize, roomPosY,tileSize,tileSize*roomSize,4f,0);
		roomRight.draw(batch, roomRightTexture, roomPosX+tileSize*roomSize, roomPosY,tileSize,tileSize*roomSize,4f,0);
		roomBottom.draw(batch, roomBottomTexture, roomPosX, roomPosY-tileSize,tileSize*roomSize,tileSize,4f,0);
		batch.draw(roomCornerTopLeftTexture,roomPosX-tileSize,roomPosY+(tileSize*roomSize),1,1,tileSize,tileSize,1,1,0);
		batch.draw(roomCornerTopRightTexture,roomPosX+tileSize*roomSize,roomPosY+(tileSize*roomSize),1,1,tileSize,tileSize,1,1,0);
		batch.draw(roomCornerBottomLeftTexture,roomPosX-tileSize,roomPosY-tileSize,1,1,tileSize,tileSize,1,1,0);
		batch.draw(roomCornerBottomRightTexture,roomPosX+tileSize*roomSize,roomPosY-tileSize,1,1,tileSize,tileSize,1,1,0);
		batch.end();

		//move player Sprite by delta speed according to button WASD press
		if(Gdx.input.isKeyPressed(Keys.W)) {
			//change player Sprite to upwards facing playerUp Sprite etc
			player = playerUp;
			playerY += Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			player = playerLeft;
			playerX -= Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			player = playerDown;
			playerY -= Gdx.graphics.getDeltaTime() * playerSpeed;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			player = playerRight;
			playerX += Gdx.graphics.getDeltaTime() * playerSpeed;
		}

		//clear all instances of a sprite from the window - use to delete rooms later on.
		// (except unless we create a map view for the player of the current explored dungeon, then it would be useful to store in memory somehow.)
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//draw player by delta speed at current coordinates
		batch.begin();
		batch.draw(player, (int)playerX, (int)playerY, 32, 32);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
