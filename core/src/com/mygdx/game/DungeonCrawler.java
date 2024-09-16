package com.mygdx.game;
import java.util.*;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.Arrow;
import com.mygdx.game.entity.Bone;
import com.mygdx.game.entity.Skull;
import com.mygdx.game.entity.Tutorial;
import com.mygdx.game.entity.behaviours.fsm.*;
import com.mygdx.game.level.CreateCell;
import com.mygdx.game.level.objects.*;
import com.mygdx.game.level.GenerateLevel;
import com.mygdx.game.level.InitLevel;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.NORMAL;

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch playerBatch, arrowBatch, enemyBatch, potBatch, hudBatch, tutoBatch, fontBatch;
	private SpriteBatch skullBatch, boneBatch, lockBatch, doorBatch, potionBatch, obstacleBatch, fireBatch;
	private SpriteBatch columnBaseBatch, columnStemBatch, columnTopBatch;
	public static World world;
	public static boolean debug = false;
	private Box2DDebugRenderer b2dr;
	public static Player player;
	private String playerDirection;
	private boolean playerPaused, playerMeleeAttacking, playerRangedAttacking;
	private Body sword, arrowBody;
	private Arrow arrow;
	public ArrayList<Arrow> arrows;
	public static ArrayList<Body> arrowBodiesCollided, boneBodiesCollided, skullBodiesDestroyed, deadEnemyBodies;
	public ArrayMap<Body, Arrow> arrowArrayMap;
	public ArrayMap<Body, Skull> skullArrayMap;
	public ArrayMap<Body, Bone> boneArrayMap;
	public ArrayMap<Body, Potion> potionArrayMap;
	public ArrayMap<Body, Pot> potArrayMap;
	public boolean reversedArrowMap, reversedSkullMap, reversedPotMap, reversedPotionMap;
	private Fixture swordHitbox, arrowHitbox;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Skull> enemySkulls, brokenSkulls;
	public static ArrayList<Bone> bones;
	public static ArrayList<Shopkeeper> shopkeepers;
	public static ArrayList<Lock> locks;
	public static ArrayList<Tutorial> tutorial;
	public static ArrayList<Pot> pots, brokenPots;
	public static ArrayList<Potion> potions, collectedPotions;
	public static ArrayList<Torch> torches;
	public static ArrayList<Obstacle> obstacles;
	public static  ArrayList<Fire> fires;
	//public static ArrayList<ColumnTop> columnTops;
	//public static ArrayList<ColumnStem> columnStems;
	//public static ArrayList<ColumnBase> columnBases;
	public static ArrayList<Column> columns;
	public float PLAYER_HORIZONTAL_SPEED = 0f, PLAYER_VERTICAL_SPEED = 0f, PLAYER_X = 0f, PLAYER_Y = 0f;
	private TiledMapRenderer renderer;
	public static OrthographicCamera camera;
	public static final float DEFAULT_VIEWPORT_WIDTH = 300f;
	public static HUD hud;
	public static Music roomClear, swordSlash;
	public static RayHandler rayHandler;
	private PointLight playerTorch;
	private BitmapFont.BitmapFontData bmfData;
	public static BitmapFont defaultFont;
	public static ArrayList<Text> messages;
	public AssetManager assetManager;
	public float stateTime;
	public int index = 0;

	private boolean leanDown = false, leanUp = false, leanLeft = false, leanRight = false, leanUpLeft = false, leanUpRight = false;

	@Override
	public void create() {
		world = new World(new Vector2(0, 0f), false);
		assetManager = new AssetManager();
		playerBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		tutoBatch = new SpriteBatch();
		enemyBatch = new SpriteBatch();
		arrowBatch = new SpriteBatch();
		skullBatch = new SpriteBatch();
		boneBatch = new SpriteBatch();
		doorBatch = new SpriteBatch();
		lockBatch = new SpriteBatch();
		potBatch = new SpriteBatch();
		potionBatch = new SpriteBatch();
		obstacleBatch = new SpriteBatch();
		columnTopBatch = new SpriteBatch();
		columnStemBatch = new SpriteBatch();
		columnBaseBatch = new SpriteBatch();
		fireBatch = new SpriteBatch();
		fontBatch = new SpriteBatch();
		reversedArrowMap = false;
		reversedSkullMap = false;
		reversedPotMap = false;
		player = new Player();
		enemies = new ArrayList<>();
		deadEnemyBodies = new ArrayList<>();
		enemySkulls = new ArrayList<>();
		brokenSkulls = new ArrayList<>();
		bones = new ArrayList<>();
		shopkeepers = new ArrayList<>();
		tutorial = new ArrayList<>();
		locks = new ArrayList<>();
		pots = new ArrayList<>();
		brokenPots = new ArrayList<>();
		potArrayMap = new ArrayMap<>();
		torches = new ArrayList<>();
		potions = new ArrayList<>();
		columns = new ArrayList<>();
		fires = new ArrayList<>();
		//columnTops = new ArrayList<>();
		//columnStems = new ArrayList<>();
		//columnBases = new ArrayList<>();
		collectedPotions = new ArrayList<Potion>();
		obstacles = new ArrayList<Obstacle>();
		messages = new ArrayList<Text>();

		//roomClear = Gdx.audio.newMusic(Gdx.files.internal("NinjaAdventure/Sounds/Menu/Accept.wav"));
		//swordSlash = Gdx.audio.newMusic(Gdx.files.internal("Sounds/slash.mp3"));

		final BodyFactory bf = new BodyFactory();
		final CreateTexture tx = CreateTexture.getInstance();
		GameContactListener lc = new GameContactListener();
		tx.textureRegionBuilder();
		final CreateSound cs = new CreateSound();
		cs.createSound();

		//FileHandle file = new FileHandle("");
		//bmfData = new BitmapFont.BitmapFontData();
		//bmfData.fontFile = file;
		//defaultFont = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/GreekAlphabet-export.fnt"));

		//defaultFont = new BitmapFont(bmfData.fontFile,tx.fontTexture);

		//defaultFont = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/GreekAlphabetConcise-export.fnt"),
		//		Gdx.files.internal("HellasDungeon/Font/GreekAlphabetConcise-export.png"), false);


		defaultFont = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontStylized-extended.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontStylized-extended.png"), false);

		Color c = new Color();
		c.set(1,1,1,1);
		//Text t =  new Text(defaultFont, "TEST MESSAGE", c, false);
		//messages.add(t);

		Text level1StartText =  new Text(defaultFont, "CLAY CATACOMBS", c, true, 1f, 0.045f, true,false,null);
		messages.add(level1StartText);

		Text roomCleared =  new Text(defaultFont, "ROOM CLEARED", c, true, 1f, 0.045f, true,false,null);
		messages.add(roomCleared);

		//defaultFont.getData();
		//bmfData.setGlyphRegion();

		//get width and height of the game window
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();

		//create camera and set the viewport
		camera = new OrthographicCamera(1000, 1000);
		camera.setToOrtho(false, w / 3, h / 3);

		Viewport vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);
		hud = new HUD(vp, hudBatch);

		//initialize map
		TiledMap map = new TiledMap();
		MapLayers layers = map.getLayers();

		//set map layer dimensions
		//set to 1000 tile layers wide and high but can be changed if required
		TiledMapTileLayer layer = new TiledMapTileLayer(1000, 1000, 16, 16);

		renderer = new OrthogonalTiledMapRenderer(map);
		b2dr = new Box2DDebugRenderer();

		//create the Box2D ray handler
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0f, 0f, 0f, 0.020f);
		if (debug) {
			rayHandler.setAmbientLight(0f, 0f, 0f, 1f);
		}

		//world.setContactListener(rlc);
		GenerateLevel level = new GenerateLevel();
		InitLevel initLevel = new InitLevel();
		initLevel.InitializeLevel();
		//level.initLevel();
		List list = level.generateLevel(0, 0);

		layer = (TiledMapTileLayer) list.get(0);

		PLAYER_X = (float) list.get(1);
		PLAYER_Y = (float) list.get(2);

		player.createPlayer(world, PLAYER_X, PLAYER_Y);

		CreateCell cr = new CreateCell();
		cr.InitializeCells();

		TiledMapTileLayer.Cell testCell2 = layer.getCell((int) player.playerBody.getPosition().x, (int) player.playerBody.getPosition().y);

		TiledMapTileLayer.Cell cell = layer.getCell(1, 1);


		System.out.println("PLAYER DATA");
		System.out.println(player.playerBody.getPosition().x);
		System.out.println(player.playerBody.getPosition().y);

		//TiledMapTileLayer.Cell testCell2 = new TiledMapTileLayer.Cell();
		//testCell2 = cr.leftWallTile;


		//add current layers to the TileMap and assign it a renderer
		layers.add(layer);

		//create a point light and attach it to the player
		playerTorch = new PointLight(rayHandler, 1000, new Color(0.25f, 0.20f, 0, 0.55f), 90, PLAYER_X, PLAYER_Y);
		playerTorch.attachToBody(player.playerBody);
		playerTorch.setSoftnessLength(65);
		//playerTorch.isSoft();
		//playerTorch.setXray(true);

		arrowBodiesCollided = new ArrayList<Body>();
		boneBodiesCollided = new ArrayList<Body>();
		skullBodiesDestroyed = new ArrayList<Body>();
		arrowArrayMap = new ArrayMap<Body, Arrow>();
		skullArrayMap = new ArrayMap<Body, Skull>();
		boneArrayMap = new ArrayMap<Body, Bone>();
		arrows = new ArrayList<Arrow>();
		potionArrayMap = new ArrayMap<Body, Potion>();

		//create an input processor to handle single input events - see inputUpdate() for held down inputs
		camera.zoom = 1f;
		Gdx.input.setInputProcessor(new GameInputProcessor() {
			@Override
			public boolean scrolled(float amountX, float amountY) {
				if (debug) {
					//camera zoom should be between 0.3 and 1.3 - may be changed during testing
					if ((camera.zoom >= 0.3f && camera.zoom <= 24f)) {
						if (camera.zoom == 24f) {
							if (amountY < 0f) {
								camera.zoom += amountY * 0.1f;
							}
						} else if (camera.zoom == 0.3f) {
							if (amountY > 0f) {
								camera.zoom += amountY * 0.1f;
							}
						} else {
							camera.zoom += amountY * 0.1f;
						}
					} else if (camera.zoom > 24f) {
						camera.zoom = 24f;
					} else if (camera.zoom < 0.3f) {
						camera.zoom = 0.3f;
					}
				}
				return true;
			}

			public boolean touchDown(int x, int y, int pointer, int button) {

				if (button == 0 && (!playerMeleeAttacking && !playerRangedAttacking)) {
					//if player presses left mouse attack with the sword
					float playerMeleeAttackSpeedInSeconds = 0.40f;
					playerMeleeAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						tx.playerSprite = tx.playerAttackDown;
						sword = bf.createSwordBody(world, player.playerBody, -2.5f, -12f);
						swordHitbox = bf.createSwordHitbox(sword, false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerUp)) {
						tx.playerSprite = tx.playerAttackUp;
						sword = bf.createSwordBody(world, player.playerBody, -2.5f, 15);
						swordHitbox = bf.createSwordHitbox(sword, false);
						swordHitbox.setUserData("UpSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						sword = bf.createSwordBody(world, player.playerBody, -14f, -2.5f);
						swordHitbox = bf.createSwordHitbox(sword, true);
						swordHitbox.setUserData("LeftSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						sword = bf.createSwordBody(world, player.playerBody, 14, -2.5f);
						swordHitbox = bf.createSwordHitbox(sword, true);
						swordHitbox.setUserData("RightSword");
						swordHitbox.setSensor(true);
					} else {
						tx.playerSprite = tx.playerAttackDown;
						sword = bf.createSwordBody(world, player.playerBody, -2.5f, -12f);
						swordHitbox = bf.createSwordHitbox(sword, false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);
					}

					sword.setUserData("Sword");

					//pause player in place while attacking (attacks must be timed correctly!)
					playerPaused = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
					//CreateSound.slash.play();
					//roomClear.play();
					//swordSlash.setLooping(true);
					//swordSlash.play();
					//swordSlash.setVolume(1f);
					//swordSlash.dispose();

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay and remove sword hitbox
							playerPaused = false;
							sword.destroyFixture(swordHitbox);

							//reset playerSprite to before the attack input
							if (tx.playerSprite.equals(tx.playerAttackDown)) {
								tx.playerSprite = tx.playerDown;
							} else if (tx.playerSprite.equals(tx.playerAttackUp)) {
								tx.playerSprite = tx.playerUp;
							} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
								tx.playerSprite = tx.playerLeft;
							} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
								tx.playerSprite = tx.playerRight;
							}

							playerMeleeAttacking = false;
						}
					}, playerMeleeAttackSpeedInSeconds);
				}

				//if player presses right mouse attack with a bow
				if (button == 1 && (!playerMeleeAttacking && !playerRangedAttacking)) {
					float playerRangedAttackSpeedInSeconds = 0.50f;
					playerRangedAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -500f);
					} else if (tx.playerSprite.equals(tx.playerUp)) {
						playerDirection = "Up";
						tx.playerSprite = tx.playerAttackUp;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y + 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("UpArrow");
						arrowBody.setLinearVelocity(0, 500f);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						playerDirection = "Left";
						tx.playerSprite = tx.playerAttackLeft;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 14f, player.playerBody.getPosition().y+1);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("LeftArrow");
						arrowBody.setLinearVelocity(-500f, 0);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						playerDirection = "Right";
						tx.playerSprite = tx.playerAttackRight;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x + 14f, player.playerBody.getPosition().y+1);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("RightArrow");
						arrowBody.setLinearVelocity(500f, 0);
					}
					//only triggers if the player hasn't moved at all yet - player starts facing down
					else {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -500f);
					}
					//pause player in place while attacking (attacks must be timed correctly!)
					arrowBody.setUserData("Arrow");
					arrows.add(arrow = new Arrow(arrowBody, playerDirection));
					arrowArrayMap.put(arrowBody, arrow);

					playerPaused = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay and remove sword hitbox
							playerPaused = false;
							//reset playerSprite to before the attack input
							if (tx.playerSprite.equals(tx.playerAttackDown)) {
								tx.playerSprite = tx.playerDown;
							} else if (tx.playerSprite.equals(tx.playerAttackUp)) {
								tx.playerSprite = tx.playerUp;
							} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
								tx.playerSprite = tx.playerLeft;
							} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
								tx.playerSprite = tx.playerRight;
							}
							playerRangedAttacking = false;
						}
					}, playerRangedAttackSpeedInSeconds);
				}
				return true;
			}

			public boolean keyDown(int keycode) {

				if (debug) {

					// (For Debugging) Add potion
					if (keycode == 16) {
						hud.inventory.addPotion();
					}

					// (For Debugging) Damage player
					if (keycode == 7) {
						hud.healthBar.LoseHealth(0.5f);
					}

					if (keycode == 9) {
						camera.zoom = 1f;
					}

					if (keycode == 10) {
						camera.zoom = 10f;
					}
				}
				// Use potion
				if (keycode == 8) {
					if (hud.inventory.Size > 0) {
						hud.inventory.usePotion(1);
						hud.healthBar.GainHealth(2);
					}
				}

				//pressing '6' shows debug data
				if (keycode == 13) {
					//System.out.println("PLAYER X: " + player.playerBody.getPosition().x);
					//System.out.println(" PLAYER Y: " + player.playerBody.getPosition().y);
					System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).x1);
					System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).y1);
					System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).doorLocations);
					System.out.println(GenerateLevel.init.roomList.get(player.currentRoom + 1).doorLocations);
				}

				//if player presses space attack with the sword
				if (((keycode == 62)) && (!playerMeleeAttacking && !playerRangedAttacking)) {
					float playerMeleeAttackSpeedInSeconds = 0.40f;
					playerMeleeAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						tx.playerSprite = tx.playerAttackDown;
						sword = bf.createSwordBody(world, player.playerBody, -2.5f, -12f);
						swordHitbox = bf.createSwordHitbox(sword, false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerUp)) {
						tx.playerSprite = tx.playerAttackUp;
						sword = bf.createSwordBody(world, player.playerBody, -2.5f, 15);
						swordHitbox = bf.createSwordHitbox(sword, false);
						swordHitbox.setUserData("UpSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						sword = bf.createSwordBody(world, player.playerBody, -14f, -2.5f);
						swordHitbox = bf.createSwordHitbox(sword, true);
						swordHitbox.setUserData("LeftSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						sword = bf.createSwordBody(world, player.playerBody, 14, -2.5f);
						swordHitbox = bf.createSwordHitbox(sword, true);
						swordHitbox.setUserData("RightSword");
						swordHitbox.setSensor(true);
					} else {
						tx.playerSprite = tx.playerAttackDown;
						sword = bf.createSwordBody(world, player.playerBody, -2.5f, -12f);
						swordHitbox = bf.createSwordHitbox(sword, false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);
					}

					sword.setUserData("Sword");

					//pause player in place while attacking (attacks must be timed correctly!)
					playerPaused = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
					//CreateSound.slash.play();
					//roomClear.play();
					//swordSlash.setLooping(true);
					//swordSlash.play();
					//swordSlash.setVolume(1f);
					//swordSlash.dispose();

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay and remove sword hitbox
							playerPaused = false;
							sword.destroyFixture(swordHitbox);

							//reset playerSprite to before the attack input
							if (tx.playerSprite.equals(tx.playerAttackDown)) {
								tx.playerSprite = tx.playerDown;
							} else if (tx.playerSprite.equals(tx.playerAttackUp)) {
								tx.playerSprite = tx.playerUp;
							} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
								tx.playerSprite = tx.playerLeft;
							} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
								tx.playerSprite = tx.playerRight;
							}

							playerMeleeAttacking = false;
						}
					}, playerMeleeAttackSpeedInSeconds);
				}

				//if player presses enter attack with a bow
				if (keycode == 66 && (!playerMeleeAttacking && !playerRangedAttacking)) {
					float playerRangedAttackSpeedInSeconds = 0.50f;
					playerRangedAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -500f);
					} else if (tx.playerSprite.equals(tx.playerUp)) {
						playerDirection = "Up";
						tx.playerSprite = tx.playerAttackUp;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y + 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("UpArrow");
						arrowBody.setLinearVelocity(0, 500f);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						playerDirection = "Left";
						tx.playerSprite = tx.playerAttackLeft;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 14f, player.playerBody.getPosition().y+1);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("LeftArrow");
						arrowBody.setLinearVelocity(-500f, 0);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						playerDirection = "Right";
						tx.playerSprite = tx.playerAttackRight;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x + 14f, player.playerBody.getPosition().y+1);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("RightArrow");
						arrowBody.setLinearVelocity(500f, 0);
					}
					//only triggers if the player hasn't moved at all yet - player starts facing down
					else {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -500f);
					}
					//pause player in place while attacking (attacks must be timed correctly!)
					arrowBody.setUserData("Arrow");
					arrows.add(arrow = new Arrow(arrowBody, playerDirection));
					arrowArrayMap.put(arrowBody, arrow);

					playerPaused = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay and remove sword hitbox
							playerPaused = false;
							//reset playerSprite to before the attack input
							if (tx.playerSprite.equals(tx.playerAttackDown)) {
								tx.playerSprite = tx.playerDown;
							} else if (tx.playerSprite.equals(tx.playerAttackUp)) {
								tx.playerSprite = tx.playerUp;
							} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
								tx.playerSprite = tx.playerLeft;
							} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
								tx.playerSprite = tx.playerRight;
							}
							playerRangedAttacking = false;
						}
					}, playerRangedAttackSpeedInSeconds);
				}
				return false;
			}
		});
		world.setContactListener(lc);
	}

	@Override
	public void render() {

			// kill game when player health is 0
			if (hud.healthBar.currentHealth == 0) {
				Gdx.app.exit();
			}

			// win the game if all enemies are dead
			if (enemies.isEmpty()) {
				hud.winGame();
			}

			final CreateTexture tx = CreateTexture.getInstance();
			//clear all assets and replace with background color
			ScreenUtils.clear(1, 1, 1, 1);

			//update game physics, camera and held down inputs
			update(Gdx.graphics.getDeltaTime());

			rayHandler.render();

			//clear graphics
			Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			//rayHandler.render();

			//set the view of the map to the camera and then render the map
			renderer.setView(camera);
			renderer.render();

			//set camera position to always be centred on the playerSprite
			camera.position.set(player.playerBody.getPosition().x + tx.playerSprite.getWidth() / 2 - 8, player.playerBody.getPosition().y + tx.playerSprite.getHeight() / 2 - 8, 0);

			//tutorial texture in the starting room
			for (Tutorial t : tutorial) {
				tutoBatch.begin();
				tutoBatch.draw(tx.tutorialTexture, t.tutorialBody.getPosition().x - 16f, t.tutorialBody.getPosition().y + 7f, 96, 64);
				tutoBatch.end();
			}


			double radians = Math.PI / 180;
			String stringR = String.valueOf(radians);
			float radiansF = Float.parseFloat(stringR);

			//	if (!enemySkulls.isEmpty()) {

			//	}

			for (Room r : GenerateLevel.init.roomList) {
				for (Door d : r.doors) {
					//render open doors here
					if (d.open) {
						doorBatch.begin();
						d.renderOpen(doorBatch, r.directionTaken, d.doorX, d.doorY);
						doorBatch.end();
					}
				}

				//render door locks when a player enters a new room with enemies
				for (Lock l : r.locks) {
					if (l.visible) {
						lockBatch.begin();
						Lock.renderLock(lockBatch, l.direction, l.lockBody.getPosition().x, l.lockBody.getPosition().y);
						lockBatch.end();
					}
				}
			}

			//adds all skulls that have been created to the array map for manipulation
			for (Skull s : enemySkulls) {
				if (!s.skullCreated) {
					skullArrayMap.put(s.createSkull(skullArrayMap), s);
				}
			}

			//destructible objects safe removers - Skulls - Arrows - Pots - Potions

			GameObjectDestroyer skullBasher9000 = new GameObjectDestroyer();
			//skullBasher9000.destroyObject(skullArrayMap,brokenSkulls,enemySkulls,Skull.class,);

			if (!skullArrayMap.isEmpty()) {
				for (OrderedMap.Entry<Body, Skull> skullEntry : skullArrayMap.entries()) {
					Skull value = skullEntry.value;
					//render each skull
					skullBatch.begin();
					if (value.SKULL_HEALTH < 1.5f) {
						Skull.renderSkull(skullBatch, tx.damagedSkullSprite, skullEntry.key.getPosition().x, skullEntry.key.getPosition().y);
					} else {
						Skull.renderSkull(skullBatch, tx.skullSprite, skullEntry.key.getPosition().x, skullEntry.key.getPosition().y);
					}
					skullBatch.end();
				}

				if (!reversedSkullMap) {
					skullArrayMap.reverse();
					reversedSkullMap = true;
				}

				Iterator<Skull> skullIt = brokenSkulls.iterator();
				if (skullIt.hasNext()) {
					Skull skull = skullIt.next();
					if (brokenSkulls.contains(skull)) {
						Bone bone = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, false, 0);
						bone.createBone();
						bones.add(bone);
						boneArrayMap.put(bone.boneBody, bone);

						Bone bone2 = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, true, bone.orientation);
						bone2.createBone();
						bones.add(bone2);
						boneArrayMap.put(bone2.boneBody, bone2);

						enemySkulls.remove(skull);
						skullArrayMap.removeKey(skull.skullBody);
						world.destroyBody(skull.skullBody);
						skullIt.remove();
					}
				}
			}

			for (Pot p : pots) {
				if (!p.potCreated) {
					potArrayMap.put(p.createPot(potArrayMap), p);
				}
			}

			if (!potArrayMap.isEmpty()) {
				for (OrderedMap.Entry<Body, Pot> potEntry : potArrayMap.entries()) {
					Pot value = potEntry.value;
					//render each pot
					potBatch.begin();
					if (value.POT_HEALTH < 1.5f) {
						if (value.type == 1) {
							Pot.renderPot(potBatch, tx.damagedAmphoraSprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
						} else {
							Pot.renderPot(potBatch, tx.damagedAmphora2Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
						}
					} else {
						if (value.type == 1) {
							Pot.renderPot(potBatch, tx.amphoraSprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
						} else {
							Pot.renderPot(potBatch, tx.amphora2Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
						}

						if (!reversedPotMap) {
							potArrayMap.reverse();
							reversedPotMap = true;
						}

					}
					potBatch.end();
				}

				Iterator<Pot> potIt = brokenPots.iterator();
				if (potIt.hasNext()) {
					Pot pot = potIt.next();
					if (brokenPots.contains(pot)) {
						//one in 7 chance to get a potion from a pot - subject to change
						int min = 1;
						int max = 10;
						int potionChance = (int) (Math.random() * (max - min + 1)) + min;
						if (potionChance == 10) {
							//create potion object
							Potion potion = new Potion(world, pot.potBody.getPosition().x, pot.potBody.getPosition().y, 1);
							potion.createPotion(potionArrayMap, rayHandler);
							potions.add(potion);
							potionArrayMap.put(potion.potionBody, potion);
						} else if (potionChance == 1) {
							Bone bone = new Bone(world, pot.potBody, pot.potBody.getPosition().x, pot.potBody.getPosition().y, false, 0);
							bone.createBone();
							bones.add(bone);
							boneArrayMap.put(bone.boneBody, bone);

							//create heart object
							//Heart heart = new Heart(world, heart.heartBody.getPosition().x, heart.heartBody.getPosition().y, 1);
							//heart.createHeart(heartArrayMap, rayHandler);
							//hearts.add(heart);
							//heartArrayMap.put(heart.heartBody, heart);
						}


						pots.remove(pot);
						potArrayMap.removeKey(pot.potBody);
						world.destroyBody(pot.potBody);
						potIt.remove();
					}
				}
			}

			//THE GREAT POT VS POTION LINE ---------------------------------------------------------------------------------

			if (!potionArrayMap.isEmpty()) {
				for (OrderedMap.Entry<Body, Potion> potionEntry : potionArrayMap.entries()) {
					Potion value = potionEntry.value;
					//render each potion
					potionBatch.begin();
					if (value.type == 1) {
						Potion.renderPotion(potionBatch, tx.potionSprite, potionEntry.key.getPosition().x, potionEntry.key.getPosition().y);
					}
					potionBatch.end();
				}

				if (!reversedPotionMap) {
					potionArrayMap.reverse();
					reversedPotionMap = true;
				}

				Iterator<Potion> potionIt = collectedPotions.iterator();
				if (potionIt.hasNext()) {
					Potion potion = potionIt.next();
					if (collectedPotions.contains(potion)) {

						hud.inventory.addPotion();
						potion.potionLight.remove();
						potions.remove(potion);
						potionArrayMap.removeKey(potion.potionBody);
						world.destroyBody(potion.potionBody);
						potionIt.remove();
					}
				}
			}

			for (Obstacle o : obstacles) {
				obstacleBatch.begin();
				switch (o.type){
					case 1:
						obstacleBatch.draw(tx.obstacle1Sprite, o.obBody.getPosition().x - 8f, o.obBody.getPosition().y - 7f, 16, 16);
						break;
					case 2:
						obstacleBatch.draw(tx.obstacle2Sprite, o.obBody.getPosition().x - 8f, o.obBody.getPosition().y - 7f, 16, 16);
						break;
					case 3:
						obstacleBatch.draw(tx.obstacle3Sprite, o.obBody.getPosition().x - 8f, o.obBody.getPosition().y - 7f, 16, 16);
						break;
				}
				obstacleBatch.end();
			}

			if (!boneArrayMap.isEmpty()) {
				for (OrderedMap.Entry<Body, Bone> boneEntry : boneArrayMap.entries()) {
					Body key = boneEntry.key;
					//render each bone
					boneBatch.begin();
					Bone.renderBone(boneBatch, tx.boneSprite, key.getPosition().x, key.getPosition().y, key.getAngle());
					boneBatch.end();
				}

				Iterator<Body> boneIt = boneBodiesCollided.iterator();
				if (boneIt.hasNext()) {
					Body boneBody = boneIt.next();
					if (boneArrayMap.containsKey(boneBody)) {
						boneArrayMap.removeKey(boneBody);
						boneIt.remove();
						world.destroyBody(boneBody);
						bones.remove(boneBody);
					}
				}
			}

			playerBatch.begin();
			//draw playerSprite on player Box2D object
			playerBatch.draw(tx.playerSprite, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 6f, 16, 16);
			if (playerMeleeAttacking) {
				//add the lanceSprite to the corresponding attack playerDirection
				if (tx.playerSprite.equals(tx.playerAttackUp)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 13f, player.playerBody.getPosition().y - 3f, 7, 12, 7, 12, 1, 1, 180);
				} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 6f, player.playerBody.getPosition().y - 18f, 7, 12, 7, 12, 1, 1, 0);
				} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 15f, player.playerBody.getPosition().y - 18f, 7, 12, 7, 12, 1, 1, 270);
				} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x + 1f, player.playerBody.getPosition().y - 11f, 7, 12, 7, 12, 1, 1, 90);
				}
			}
			if (playerRangedAttacking) {
				//add the bowSprite and arrowSprite to the corresponding attack playerDirection
				if (tx.playerSprite.equals(tx.playerAttackUp)) {
					playerBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 2f, 8, 10, 18, 8, 1, 1, 180);
				} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
					playerBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 10f, player.playerBody.getPosition().y - 14f, 7, 12, 18, 8, 1, 1, 0);
				} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
					playerBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 11f, player.playerBody.getPosition().y - 9f, 7, 12, 18, 8, 1, 1, 270);
				} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
					playerBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 3f, player.playerBody.getPosition().y - 13f, 7, 12, 18, 8, 1, 1, 90);
				}
			}
			playerBatch.end();

			//render enemy sprite
			for (Enemy e : enemies) {
				if (e.playerSighted){
					e.getStateMachine().changeState(EnemyState.ATTACK);
					e.playerSighted = false;
				}
				enemyBatch.begin();
				enemyBatch.draw(tx.enemySprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				enemyBatch.end();
			}

			for (Shopkeeper s : shopkeepers) {
				playerBatch.begin();
				playerBatch.draw(tx.shopkeeperSprite, s.shopBody.getPosition().x - 8f, s.shopBody.getPosition().y - 7f, 16, 16);
				playerBatch.end();
			}

			//check if there are any fired arrows
			if (!arrowArrayMap.isEmpty()) {
				for (OrderedMap.Entry<Body, Arrow> arrowEntry : arrowArrayMap.entries()) {
					Body key = arrowEntry.key;
					//render each individual arrow
					arrowBatch.begin();
					Arrow.renderArrow(arrowBatch, tx.arrowSprite, arrowEntry.value.direction, key.getPosition().x, key.getPosition().y);
					arrowBatch.end();
				}

				//array map order needs to be reversedArrowMap once (Collections.reverseOrder() method is not available with ArrayMaps)
				if (!reversedArrowMap) {
					arrowArrayMap.reverse();
					reversedArrowMap = true;
				}

				Iterator<Body> bodyIt = arrowBodiesCollided.iterator();
				//iterate through every collided arrow
				if (bodyIt.hasNext()) {
					Body collidedBody = bodyIt.next();
					//if the array map contains the arrow body that collided, remove that arrow from the game
					if (arrowArrayMap.containsKey(collidedBody)) {
						arrowArrayMap.removeKey(collidedBody);
						//remove the arrow Box2D body
						world.destroyBody(collidedBody);
						//remove body from arrowBodiesCollided
						bodyIt.remove();
						//remove the sprite by removing the Arrow class object
						arrows.remove(arrowArrayMap.get(collidedBody));
					}
				}
			}
			for (Body body : deadEnemyBodies) {
				world.destroyBody(body);
			}

			deadEnemyBodies.clear();


			for (Column c : columns) {
				columnBaseBatch.begin();
				switch (c.type) {
					case 7:
						columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
						break;
					case 8:
						columnBaseBatch.draw(tx.pedestal1, c.columnX, c.columnY);
						break;
					case 9:
						columnBaseBatch.draw(tx.pedestal2, c.columnX, c.columnY);
						break;
				}
				columnBaseBatch.end();
			}
		for (Column c : columns) {
			columnStemBatch.begin();
			switch (c.type) {
				case 4:
					columnStemBatch.draw(tx.colStem, c.columnX, c.columnY);
					break;
				case 5:
					columnStemBatch.draw(tx.colStemDamaged1, c.columnX, c.columnY);
					break;
				case 6:
					columnStemBatch.draw(tx.colStemDamaged2, c.columnX, c.columnY);
					break;
			}
			columnStemBatch.end();
		}
		for (Column c : columns) {
				columnTopBatch.begin();
				switch (c.type) {
					case 1:
						columnTopBatch.draw(tx.colTop1,c.columnX,c.columnY);
						break;
					case 2:
						columnTopBatch.draw(tx.colTop2,c.columnX,c.columnY);
						break;
					case 3:
						columnTopBatch.draw(tx.colTop3,c.columnX,c.columnY);
						break;
					case 10:
						columnTopBatch.draw(tx.colTop4,c.columnX,c.columnY);
						break;
					case 11:
						columnTopBatch.draw(tx.colTop5,c.columnX,c.columnY);
						break;
				}
				columnTopBatch.end();
			}


		stateTime += Gdx.graphics.getDeltaTime();

		for (Fire f : fires) {

			TextureRegion currentFrame = tx.fireAnimation.getKeyFrame(stateTime, true);
			fireBatch.begin();
			fireBatch.draw(currentFrame, f.fireX, f.fireY);
			fireBatch.end();
		}


			fontBatch.begin();
			for (Text t : messages) {
					FontController.drawFont(fontBatch, defaultFont, t.textX, t.textY, t);
			}
			fontBatch.end();

			//toggle to enable or disable visible collision boxes
			if (debug) {
				for (Enemy enemy : enemies) {
					//renders ray cast rays
					Ray<Vector2>[] rays = enemy.rayConfigurations[0].getRays();
					enemy.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
					enemy.shapeRenderer.setProjectionMatrix(camera.combined);
					enemy.shapeRenderer.setColor(1, 0, 0, 1);
					// shapeRenderer.setColor(Color.RED);
					//transform.idt();
					//shapeRenderer.setTransformMatrix(transform);
					for (int i = 0; i < rays.length; i++) {
						Ray<Vector2> ray = rays[i];
						enemy.tmp.set(ray.start);
						enemy.tmp2.set(ray.end);
						enemy.shapeRenderer.line(enemy.tmp, enemy.tmp2);
					}

					/*
					if (playerSighted) {
						Ray<Vector2>[] rays2 = enemy.rayConfigurations2[0].getRays();
						for (int i = 0; i < rays2.length; i++) {
							Ray<Vector2> ray = rays2[i];
							enemy.tmp.set(ray.start);
							enemy.tmp2.set(ray.end);
							enemy.shapeRenderer.line(enemy.tmp, enemy.tmp2);
						}
					}

					 */
					enemy.shapeRenderer.end();
				}
				b2dr.render(world, camera.combined);

				//TODO Add debug button to Scene

			}

			camera.update();
			hud.update();
			rayHandler.render();
			rayHandler.setCombinedMatrix(camera);
			obstacleBatch.setProjectionMatrix(camera.combined);
			playerBatch.setProjectionMatrix(camera.combined);
			arrowBatch.setProjectionMatrix(camera.combined);
			skullBatch.setProjectionMatrix(camera.combined);
			boneBatch.setProjectionMatrix(camera.combined);
			tutoBatch.setProjectionMatrix(camera.combined);
			enemyBatch.setProjectionMatrix(camera.combined);
			lockBatch.setProjectionMatrix(camera.combined);
			doorBatch.setProjectionMatrix(camera.combined);
			potBatch.setProjectionMatrix(camera.combined);
			potionBatch.setProjectionMatrix(camera.combined);
			columnBaseBatch.setProjectionMatrix(camera.combined);
			columnStemBatch.setProjectionMatrix(camera.combined);
			columnTopBatch.setProjectionMatrix(camera.combined);
			fireBatch.setProjectionMatrix(camera.combined);
			fontBatch.setProjectionMatrix(camera.combined);
			hudBatch.setProjectionMatrix(hud.stage.getCamera().combined);
			hud.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// We multiply the viewport height by the aspect ratio to maintain
		// correct proportions for objects when drawn.
		float aspectRatio = (float) height / width;
		camera.viewportHeight = DEFAULT_VIEWPORT_WIDTH * aspectRatio;
		camera.viewportWidth = DEFAULT_VIEWPORT_WIDTH;
		camera.update();
		hud.stage.getViewport().update(width, height, true);
	}

	//update method for physics, camera and held down inputs
	public void update(float delta) {
		world.step(1 / 60f, 6, 2);
		//rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.update();
		rayHandler.setCombinedMatrix(camera);

		for (Enemy e : enemies) {
			e.enemyAI.update(GdxAI.getTimepiece().getTime());
			e.update(GdxAI.getTimepiece().getTime());
		}

		if (!playerPaused) {
			inputUpdate();
		}
	}

	@Override
	public void dispose() {
		playerBatch.dispose();
		hud.stage.dispose();
		arrowBatch.dispose();
		skullBatch.dispose();
		boneBatch.dispose();
		lockBatch.dispose();
		doorBatch.dispose();
		potBatch.dispose();
		potionBatch.dispose();
		columnTopBatch.dispose();
		columnStemBatch.dispose();
		columnBaseBatch.dispose();
		fireBatch.dispose();
		rayHandler.dispose();
		world.dispose();
		b2dr.dispose();
	}

	public void inputUpdate() {
		final CreateTexture tx = CreateTexture.getInstance();
		leanUp = false;
		leanDown = false;
		leanLeft = false;
		leanRight = false;
		leanUpLeft = false;
		leanUpRight = false;

		PLAYER_HORIZONTAL_SPEED = 0;
		PLAYER_VERTICAL_SPEED = 0;

		//slighter slower speed than the fastest human reaction times
		float movementDelay = 0.013f;

		//move playerSprite Sprite by delta speed according to button WASD press
		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
			PLAYER_VERTICAL_SPEED = 1f;
			leanUp = true;
			if (leanLeft){
				PLAYER_HORIZONTAL_SPEED = -1f;
				tx.playerSprite = tx.playerUpLeftLean;
			} else if (leanRight) {
				PLAYER_HORIZONTAL_SPEED = 1f;
				tx.playerSprite = tx.playerUpRightLean;
			}
			else {
				tx.playerSprite = tx.playerUp;
			}
		}


		if (Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)) {
			PLAYER_HORIZONTAL_SPEED = -1f;
			leanLeft = true;
			if (leanDown) {
				PLAYER_VERTICAL_SPEED = -1f;
				tx.playerSprite = tx.playerDownLeftLean;
			} else if (leanUp) {
				PLAYER_VERTICAL_SPEED = 1f;
				tx.playerSprite = tx.playerUpLeftLean;
			} else {
				tx.playerSprite = tx.playerLeft;
			}
		}


		if (Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN)) {
			PLAYER_VERTICAL_SPEED = -1f;
			leanDown = true;
			if (leanLeft) {
				PLAYER_HORIZONTAL_SPEED = -1f;
				tx.playerSprite = tx.playerDownLeftLean;
			} else if (leanRight) {
				PLAYER_HORIZONTAL_SPEED = 1f;
				tx.playerSprite = tx.playerDownRightLean;
			} else {
				tx.playerSprite = tx.playerDown;
			}
		}
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
			PLAYER_HORIZONTAL_SPEED = 1f;
			leanRight = true;
			if (leanDown) {
				tx.playerSprite = tx.playerDownRightLean;
			} else if (leanUp) {
				tx.playerSprite = tx.playerUpRightLean;
			} else {
				tx.playerSprite = tx.playerRight;
			}
		}

		//create a movement vector and normalize it for diagonal movement
		Vector2 vec = new Vector2(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
		vec.nor();

		//multiply to get desired speed
		PLAYER_HORIZONTAL_SPEED = vec.x * 70f;
		PLAYER_VERTICAL_SPEED = vec.y * 70f;

		player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
	}
}