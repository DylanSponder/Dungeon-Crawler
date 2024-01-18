package com.mygdx.game;

import java.io.IOException;
import java.util.List;
import java.util.Random;

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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch batch;
  	private SpriteBatch hudBatch;
	private World world;
	private Box2DDebugRenderer b2dr;
	private Body player;
	private Fixture playerHitbox;
	private boolean pausePlayer;
	private boolean playerAttacking;
	private Texture playerTexture;
	private Texture playerAttackTexture;
	private Sprite playerSprite;
	private Sprite playerUp;
	private Sprite playerDown;
	private Sprite playerLeft;
	private Sprite playerRight;
	private Sprite playerAttackUp;
	private Sprite playerAttackDown;
	private Sprite playerAttackLeft;
	private Sprite playerAttackRight;
	float playerX = 0;
	float playerY = 0;
	float playerXSpeed = 0;
	float playerYSpeed = 0;
	public PlayerAttack PlayerAttack;
	public LevelParser l;
	public RenderRules r;
	private TiledMap map;
	private TiledMapRenderer renderer;
	private Texture roomBackground;
	private Texture roomDoorTexture;
	private Texture roomHoleTexture;
	private OrthographicCamera camera;
  public static final float DEFAULT_VIEWPORT_WIDTH = 300f;
  public static HUD hud;

	@Override
	public void create() {
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		world = new World(new Vector2(0, 0f), false);

		//grab textures
		playerTexture = new Texture(Gdx.files.internal("NinjaAdventure/Actor/Characters/GoldKnight/SpriteSheet.png"));
		playerAttackTexture = new Texture(Gdx.files.internal("NinjaAdventure/Actor/Characters/GoldKnight/SeparateAnim/Attack.png"));
		roomBackground = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/Interior/CustomTileset.png"));
		roomDoorTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHouse.png"));
		roomHoleTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHole.png"));

		//get width and height of the game window
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();

		//create camera and set the viewport
		camera = new OrthographicCamera(1000, 1000);
		camera.setToOrtho(false, w / 3, h / 3);

		// TODO: Add hearts to HUD
		Viewport vp = new FitViewport(camera.viewportWidth, camera.viewportHeight);
		hud = new HUD(vp, hudBatch);
		Texture heartTexture = new Texture(Gdx.files.internal("NinjaAdventure/HUD/Heart.png"));
		Sprite heartSprite = new Sprite(heartTexture, 16, 16);

		//outline playerSprite Sprites
		playerSprite = new Sprite(playerTexture, 0, 0, 16, 16);
		playerUp = new Sprite(playerTexture, 16, 0, 16, 16);
		playerDown = new Sprite(playerTexture, 0, 0, 16, 16);
		playerLeft = new Sprite(playerTexture, 32, 0, 16, 16);
		playerRight = new Sprite(playerTexture, 48, 0, 16, 16);
		playerAttackUp = new Sprite(playerAttackTexture,16,0,16,16);
		playerAttackDown = new Sprite(playerAttackTexture,0,0,16,16);
		playerAttackLeft = new Sprite(playerAttackTexture,32,0,16,16);
		playerAttackRight = new Sprite(playerAttackTexture,48,0,16,16);

		//create each tile of the room texture as a TextureRegion
		//TODO move to separate class
		TextureRegion roomMiddleFloorTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);

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
		TextureRegion holeTexture = new TextureRegion(roomHoleTexture, 0, 0, 16, 16);

		//set TextureRegion source coordinates
		//TODO move to separate class
		roomMiddleFloorTexture.setRegion(96, 16, 16, 16);

		roomTopLeftWallTexture.setRegion(0, 0, 16, 16);
		roomTopWallTexture.setRegion(48, 0, 16, 16);
		roomTopRightWallTexture.setRegion(64, 0, 16, 16);
		roomLeftWallTexture.setRegion(0, 16, 16, 16);
		roomRightWallTexture.setRegion(64, 16, 16, 16);
		roomBottomLeftWallTexture.setRegion(0, 64, 16, 16);
		roomBottomWallTexture.setRegion(48, 64, 16, 16);
		roomBottomRightWallTexture.setRegion(64, 64, 16, 16);

		roomTopLeftTurnTexture.setRegion(32, 16, 16, 16);
		roomTopRightTurnTexture.setRegion(48, 16, 16, 16);
		roomBottomLeftTurnTexture.setRegion(32, 32, 16, 16);
		roomBottomRightTurnTexture.setRegion(48, 32, 16, 16);

		doorTexture.setRegion(144, 48, 16, 16);

		//create an input processor to handle single button press events
		Gdx.input.setInputProcessor(new GameInputProcessor() {
			@Override
			public boolean scrolled(float amountX, float amountY) {
				if ((camera.zoom >= 0.3f && camera.zoom <= 1f)) {
					if (camera.zoom == 1f) {
						if (amountY < 0f) {
							camera.zoom += amountY * 0.02f;
						}
					} else if (camera.zoom == 0.3f) {
						if (amountY > 0f) {
							camera.zoom += amountY * 0.02f;
						}
					} else {
						camera.zoom += amountY * 0.02f;
					}
				} else if (camera.zoom > 1f) {
					camera.zoom = 1f;
				} else if (camera.zoom < 0.3f) {
					camera.zoom = 0.3f;
				}
				return true;
			}
			public boolean keyDown (int keycode) {
				if ((keycode == 62 || keycode == 66) && playerAttacking == false){
					float playerAttackSpeedInSeconds = 0.4f;
					playerAttacking = true;

					if (playerSprite.equals(playerDown)) {
						playerSprite = playerAttackDown;
					} else 	if (playerSprite.equals(playerUp)) {
						playerSprite = playerAttackUp;
					} else 	if (playerSprite.equals(playerLeft)) {
						playerSprite = playerAttackLeft;
					} else 	if (playerSprite.equals(playerRight)) {
						playerSprite = playerAttackRight;
					}

					//pause player in place while attacking (attacks must be timed correctly!)
					pausePlayer = true;
					playerXSpeed = 0;
					playerYSpeed = 0;
					player.setLinearVelocity(playerXSpeed,playerYSpeed);

					//PlayerAttack.playerAttack(2);
					// write method to detect whether an enemy is within
					// melee distance of the player,
					// if so attack with melee,
					// if not, launch an arrow in player direction

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay
							pausePlayer = false;
							playerSprite = playerDown;
							playerAttacking = false;
						}
					}, playerAttackSpeedInSeconds);
				}
				return false;
			}
		});

		//initialize map
		TiledMap map = new TiledMap();
		MapLayers layers = map.getLayers();

		//set map layer dimensions
		//set to 1000 tile layers wide and high but can be changed if required
		TiledMapTileLayer layer = new TiledMapTileLayer(1000, 1000, 16, 16);

		//initialize cell types
		//TODO move to StringToCell
		Cell middleFloorTile = new Cell();

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
		//TODO move to StringToCell
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
			Random rand = new Random();
			int roomRand = rand.nextInt(1, 4);
			//List<List<String>> level = l.read("room"+roomRand+".csv");
			List<List<String>> level = l.read("room.csv");

			//levelY is what determines the size of the level.
			//When levelY is either 1000 or 0 the map will be outside the TiledMapTileLayer and thus will not render
			int levelY = 999;
			int levelSize = level.size();
			for (int i = 0; i < levelSize; i++) {
				List<String> levelTextures = r.translateSymbols(level, i);
				//-to be moved to StringToCell
				int layerSize = levelTextures.size();
				for (int i2 = 0; i2 < layerSize; i2++) {
					Cell currentCell = new Cell();
					switch (levelTextures.get(i2)) {
						case "middleFloorTile":
							currentCell = middleFloorTile;
							break;
						case "topLeftWallTile":
							currentCell = topLeftWallTile;
							Body newTopLeftWall = createWall((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
							break;
						case "topWallTile":
							currentCell = topWallTile;
							Body newTopWall = createWall((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
							break;
						case "topRightWallTile":
							currentCell = topRightWallTile;
							Body newTopRightWall = createWall((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
							break;
						case "leftWallTile":
							currentCell = leftWallTile;
							Body newLeftWall = createWall((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
							break;
						case "rightWallTile":
							currentCell = rightWallTile;
							Body newRightWall = createWall((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
							break;
						case "bottomLeftWallTile":
							currentCell = bottomLeftWallTile;
							Body newBottomLeftWall = createWall((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
							break;
						case "bottomWallTile":
							currentCell = bottomWallTile;
							Body newBottomWall = createWall((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
							break;
						case "bottomRightWallTile":
							currentCell = bottomRightWallTile;
							Body newBottomRightWall = createWall((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
							break;
						case "topLeftTurnTile":
							currentCell = topLeftTurnTile;
							//Body newTopLeftTurnWall = createWall((i2*16)+16*16,levelY*16+Gdx.graphics.getHeight()/30-16);
							Body newTopLeftTurn = createWallTurn((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 0.1f);
							break;
						case "topRightTurnTile":
							currentCell = topRightTurnTile;
							//Body newTopRightTurnWall = createWall((i2*16)+16*16,levelY*16+Gdx.graphics.getHeight()/30-16);
							Body newTopRightTurn = createWallTurn((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 0.1f);
							break;
						case "bottomLeftTurnTile":
							currentCell = bottomLeftTurnTile;
							//Body newBottomLeftTurnWall = createWall((i2*16)+16*16,levelY*16+Gdx.graphics.getHeight()/30-16);
							Body newBottomLeftTurn = createWallTurn((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
							break;
						case "bottomRightTurnTile":
							currentCell = bottomRightTurnTile;
							Body newBottomRightTurn = createWallTurn((i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
							break;
					}
					layer.setCell(i2 + 16, levelY, currentCell);
				}
				levelY--;
			}
			//set player starting coordinates according to the position of the level
			//TODO change levelY to something like room[0].levelY when multiple room support is added so the player spawns in the first generated room of the level.
			playerX = Gdx.graphics.getWidth()/30 * 16;
			playerY = levelY*16+levelSize*16-16;

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

		//draw playerSprite on player Box2D object
		batch.begin();
		batch.draw(playerSprite, player.getPosition().x-8f, player.getPosition().y-7f, 16, 16);
		batch.end();

		//renders all physics object - for debug only
		//b2dr.render(world,camera.combined);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		hudBatch.setProjectionMatrix(hud.stage.getCamera().combined);
    	hud.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
    // We multiply the viewport height by the aspect ratio to maintain
    // correct proportions for objects when drawn.
    float aspectRatio = (float) height/width;
    camera.viewportHeight = DEFAULT_VIEWPORT_WIDTH * aspectRatio;
    camera.viewportWidth = DEFAULT_VIEWPORT_WIDTH;
    camera.update();
    // FIXME: For some reason this affects the scaling of the rest of the game...
    // hud.stage.getViewport().update(width, height);
	}

	//update method for physics, camera and input
	public void update(float delta){
		world.step(1/60f,6,2);
		if (pausePlayer==false){
			inputUpdate(delta);
		}
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

			//code example to change the player hitbox - previously tested but currently unused
			/*
			player.destroyFixture(playerHitbox);
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(7,7);
			player.createFixture(shape,1.0f);
			*/
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

	//create player with physical Box2D properties
	public Body createPlayer(){
		Body body;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(playerX+8,playerY+8);
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		PolygonShape playerShape = new PolygonShape();
		playerShape.setAsBox(5.5f,5.5f);
		playerHitbox = body.createFixture(playerShape, 1.0f);
		playerShape.dispose();
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

	public Body createWallTurn(float x, float y, float offsetX, float offsetY){
		Body body;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x+offsetX,y+offsetY);
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		CircleShape shape = new CircleShape();
		shape.setRadius(16f);
		body.createFixture(shape, 1.0f);
		shape.dispose();
		return body;
	}
}
