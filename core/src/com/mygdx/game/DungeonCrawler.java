package com.mygdx.game;

import java.io.IOException;
import java.util.List;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch batch;
	private Stage stage;
	private World world;
	private Box2DDebugRenderer b2dr;
	private Body bodyTest;
	private BodyDef bodyDef;

	private Body player;
	Texture playerTexture;
	private Sprite playerSprite;
	private Sprite playerUp;
	private Sprite playerDown;
	private Sprite playerLeft;
	private Sprite playerRight;

	float playerX = 0;
	float playerY = 0;

	float playerXSpeed = 0;

	float playerYSpeed = 0;


	public LevelParser l;

	public RenderRules r;

	private TiledMap map;

	private TiledMapRenderer renderer;

	Texture roomBackground;
	Texture roomDoorTexture;
	Texture roomHoleTexture;

	private OrthographicCamera camera;

	@Override
	public void create() {
		batch = new SpriteBatch();
		world = new World(new Vector2(0,0f),false);
		stage = new Stage(new ScreenViewport());

		//grab textures
		playerTexture = new Texture(Gdx.files.internal("NinjaAdventure/Actor/Characters/GoldKnight/SpriteSheet.png"));
		roomBackground = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/Interior/CustomTileset.png"));
		roomDoorTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHouse.png"));
		roomHoleTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHole.png"));

		//set starting playerSprite position
		playerX = Gdx.graphics.getWidth()/30*16;
		playerY = Gdx.graphics.getHeight()/30*16;

		//outline playerSprite Sprites
		playerSprite = new Sprite(playerTexture, 0, 0, 16, 16);
		playerUp = new Sprite(playerTexture, 16, 0, 16, 16);
		playerDown = new Sprite(playerTexture, 0, 0, 16, 16);
		playerLeft = new Sprite(playerTexture, 32, 0, 16, 16);
		playerRight = new Sprite(playerTexture, 48, 0, 16, 16);

		//create each tile of the room texture as a TextureRegion
		//-to be moved to another class
		TextureRegion roomMiddleFloorTexture = new TextureRegion(roomBackground,0,0,16,16);

		TextureRegion roomLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomTopWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomBottomWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomTopLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomTopRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomBottomLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomBottomRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);

		TextureRegion roomTopLeftTurnTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomTopRightTurnTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomBottomLeftTurnTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
		TextureRegion roomBottomRightTurnTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);

		TextureRegion doorTexture = new TextureRegion(roomDoorTexture, 0, 0, 16, 16);
		TextureRegion holeTexture = new TextureRegion(roomHoleTexture,0,0,16,16);

		//set TextureRegion source coordinates
		//-to be moved to another class
		roomMiddleFloorTexture.setRegion(96,16,16,16);

		roomTopLeftWallTexture.setRegion(0,0,16,16);
		roomTopWallTexture.setRegion(48,0,16,16);
		roomTopRightWallTexture.setRegion(64,0,16,16);
		roomLeftWallTexture.setRegion(0,16,16,16);
		roomRightWallTexture.setRegion(64,16,16,16);
		roomBottomLeftWallTexture.setRegion(0,64,16,16);
		roomBottomWallTexture.setRegion(48,64,16,16);
		roomBottomRightWallTexture.setRegion(64,64,16,16);

		roomTopLeftTurnTexture.setRegion(32,16,16,16);
		roomTopRightTurnTexture.setRegion(48,16,16,16);
		roomBottomLeftTurnTexture.setRegion(32,32,16,16);
		roomBottomRightTurnTexture.setRegion(48,32,16,16);

		doorTexture.setRegion(144,48,16,16);

		//get width and height of the game window
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();

		//create camera and set the viewport
		camera = new OrthographicCamera(0,0);
		camera.setToOrtho(false, w/3, h/3);

		//create an input processor to handle single button events
		Gdx.input.setInputProcessor(new GameInputProcessor(){
			@Override public boolean scrolled (float amountX, float amountY) {
				if(camera.zoom>=0.5&&camera.zoom<=1){
					camera.zoom+=amountY*0.02f;
				}
				else if(camera.zoom>1){
					camera.zoom=1;
				}
				else if(camera.zoom<0.5f){
					camera.zoom=0.5f;
				}
				return true;
			}
		});

		//initialize map
		TiledMap map = new TiledMap();
		MapLayers layers = map.getLayers();

		//set map layer dimensions, currently the size of the window itself
		TiledMapTileLayer layer = new TiledMapTileLayer(100,100, 16,16);

		//initialize cell types
		//-to be moved to StringToCell
		Cell topLeftFloorTile = new Cell();
		Cell topFloorTile = new Cell();
		Cell topRightFloorTile = new Cell();
		Cell leftFloorTile = new Cell();
		Cell middleFloorTile = new Cell();
		Cell rightFloorTile = new Cell();
		Cell bottomLeftFloorTile = new Cell();
		Cell bottomFloorTile = new Cell();
		Cell bottomRightFloorTile = new Cell();

		Cell topLeftWallTile = new Cell();
		Cell topWallTile = new Cell();
		Cell topRightWallTile = new Cell();
		Cell leftWallTile = new Cell();
		Cell rightWallTile = new Cell();
		Cell bottomLeftWallTile = new Cell();
		Cell bottomWallTile = new Cell();
		Cell bottomRightWallTile = new Cell();

		Cell topLeftTurnTile = new Cell();
		Cell topRightTurnTile = new Cell();
		Cell bottomLeftTurnTile = new Cell();
		Cell bottomRightTurnTile = new Cell();

		//set cells to their corresponding tile
		//-to be moved to StringToCell
		middleFloorTile.setTile(new StaticTiledMapTile(roomMiddleFloorTexture));

		topLeftWallTile.setTile(new StaticTiledMapTile(roomTopLeftWallTexture));
		topWallTile.setTile(new StaticTiledMapTile(roomTopWallTexture));
		topRightWallTile.setTile(new StaticTiledMapTile(roomTopRightWallTexture));
		leftWallTile.setTile(new StaticTiledMapTile(roomLeftWallTexture));
		rightWallTile.setTile(new StaticTiledMapTile(roomRightWallTexture));
		bottomLeftWallTile.setTile(new StaticTiledMapTile(roomBottomLeftWallTexture));
		bottomWallTile.setTile(new StaticTiledMapTile(roomBottomWallTexture));
		bottomRightWallTile.setTile(new StaticTiledMapTile(roomBottomRightWallTexture));

		topLeftTurnTile.setTile(new StaticTiledMapTile(roomTopLeftTurnTexture));
		topRightTurnTile.setTile(new StaticTiledMapTile(roomTopRightTurnTexture));
		bottomLeftTurnTile.setTile(new StaticTiledMapTile(roomBottomLeftTurnTexture));
		bottomRightTurnTile.setTile(new StaticTiledMapTile(roomBottomRightTurnTexture));

		//load level
		LevelParser l = new LevelParser();
		RenderRules r = new RenderRules();

		try {
			List<List<String>> level = l.read("level.csv");
			int levelY = Gdx.graphics.getHeight()/30;
			int levelSize = level.size();
			for (int i = 0; i < levelSize; i++) {
				List<String> levelTextures = r.translateSymbols(level, i);
				//-to be moved to StringToCell
				int layerSize = levelTextures.size();
				for (int i2 = 0; i2 < layerSize; i2++) {
					Cell currentCell = new Cell();
					switch (levelTextures.get(i2)) {
						case "topLeftFloorTile":
							currentCell = topLeftFloorTile;
							break;
						case "topFloorTile":
							currentCell = topFloorTile;
							break;
						case "topRightFloorTile":
							currentCell = topRightFloorTile;
							break;
						case "leftFloorTile":
							currentCell = leftFloorTile;
							break;
						case "middleFloorTile":
							currentCell = middleFloorTile;
							break;
						case "rightFloorTile":
							currentCell = rightFloorTile;
							break;
						case "bottomLeftFloorTile":
							currentCell = bottomLeftFloorTile;
							break;
						case "bottomFloorTile":
							currentCell = bottomFloorTile;
							break;
						case "bottomRightFloorTile":
							currentCell = bottomRightFloorTile;
							break;
						case "topLeftWallTile":
							currentCell = topLeftWallTile;
							break;
						case "topWallTile":
							currentCell = topWallTile;
							break;
						case "topRightWallTile":
							currentCell = topRightWallTile;
							break;
						case "leftWallTile":
							currentCell = leftWallTile;
							break;
						case "rightWallTile":
							currentCell = rightWallTile;
							break;
						case "bottomLeftWallTile":
							currentCell = bottomLeftWallTile;
							break;
						case "bottomWallTile":
							currentCell = bottomWallTile;
							break;
						case "bottomRightWallTile":
							currentCell = bottomRightWallTile;
							break;
						case "topLeftTurnTile":
							currentCell = topLeftTurnTile;
							break;
						case "topRightTurnTile":
							currentCell = topRightTurnTile;
							break;
						case "bottomLeftTurnTile":
							currentCell = bottomLeftTurnTile;
							break;
						case "bottomRightTurnTile":
							currentCell = bottomRightTurnTile;
							break;
					}
					layer.setCell(i2+16, levelY, currentCell);
				}
				levelY--;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		//add current layers to the TileMap and assign it a renderer
		layers.add(layer);
		renderer = new OrthogonalTiledMapRenderer(map);
		b2dr = new Box2DDebugRenderer();
		player = createPlayer();
	}

	@Override
	public void render () {
		//clear all assets and replace with background color
		ScreenUtils.clear(1, 1, 1, 1);

		//update game
		update(Gdx.graphics.getDeltaTime());

		//clear graphics
		Gdx.gl.glClearColor(0,0,0,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//set the view of the map to the camera and then render the map
		renderer.setView(camera);
		renderer.render();

		//set camera position to always be centred on the playerSprite
		camera.position.set(player.getPosition().x+ playerSprite.getWidth()/2, player.getPosition().y+ playerSprite.getHeight()/2, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		//draw playerSprite by delta speed at current coordinates
		batch.begin();
		batch.draw(playerSprite, player.getPosition().x-8f, player.getPosition().y-8f, 16, 16);
		batch.end();

		b2dr.render(world,camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width/3, height/3);
	}

	//update method for physics, camera and input
	public void update(float delta){
		world.step(1/60f,6,2);

			inputUpdate(delta);
	}

	@Override
	public void dispose () {
		batch.dispose();
		world.dispose();
		b2dr.dispose();
	}

	public void inputUpdate(float delta) {

		playerXSpeed = 0;
		playerYSpeed = 0;
		//move playerSprite Sprite by delta speed according to button WASD press
		if (Gdx.input.isKeyPressed(Keys.W)) {
			//change playerSprite Sprite to upwards facing playerUp Sprite etc
			playerSprite = playerUp;
			playerYSpeed = 100f;
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			playerSprite = playerLeft;
			playerXSpeed = -100f;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			playerSprite = playerDown;
			playerYSpeed =-100f;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			playerSprite = playerRight;
			playerXSpeed = 100f;
		}

		player.setLinearVelocity(playerXSpeed,playerYSpeed);
	}

	//create player with physical box2D properties
	public Body createPlayer(){
		Body body;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(playerX+8,playerY+8);
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8,8);
		body.createFixture(shape, 1.0f);
		shape.dispose();
		return body;
	}
	public Body createWall(float x,float y){
		Body body;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x+8,y+8);
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8,8);
		body.createFixture(shape, 1.0f);
		shape.dispose();
		return body;
	}
}
