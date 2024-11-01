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
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
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

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch playerBatch, arrowBatch, enemySkullBatch, enemySpiderBatch, potBatch, hudBatch, tutoBatch, fontBatch, inventoryBatch;
	private SpriteBatch skullBatch, boneBatch, lockBatch, doorBatch, potionBatch, obstacleBatch, fireBatch, flameBatch, cobBatch, candleBatch;
	private SpriteBatch columnBaseBatch, columnStemBatch, columnTopBatch, pedestalBatch, roofBatch;
	public static World world;
	public static boolean debug;
	private Box2DDebugRenderer b2dr;
	public static Player player;
	private String playerDirection;
	private boolean playerPaused, playerMeleeAttacking, playerRangedAttacking, playerShieldAttacking;
	private Body swordBody, arrowBody, shieldBody;
	private Fixture swordHitbox, arrowHitbox, shieldHitbox;
	private Arrow arrow;
	public static ArrayList<Arrow> arrows;
	public static ArrayList<Body> arrowBodiesCollided, boneBodiesCollided, skullBodiesDestroyed, deadEnemyBodies;
	public static ArrayMap<Body, Arrow> arrowArrayMap;
	public static ArrayList<Enemy> enemies;
	public ArrayMap<Body, Skull> skullArrayMap;
	public static ArrayMap<Body, Bone> boneArrayMap;
	public ArrayMap<Body, Potion> potionArrayMap;
	public ArrayMap<Body, Cobweb> cobArrayMap;
	public ArrayMap<Body, Fire> respawnFireMap;
	public ArrayMap<Body, Pot> potArrayMap;
	public boolean reversedArrowMap, reversedSkullMap, reversedPotMap, reversedPotionMap, reversedRespawnFireMap, reversedBoneMap;
	public static ArrayList<EnemySkull> enemySkulls, dyingSkulls;
	public static ArrayList<EnemySpider> enemySpiders, dyingSpiders;
	public static ArrayList<Skull> skulls, brokenSkulls;
	public static ArrayList<Fire> extinguishedRespawnFires;
	public static ArrayList<Bone> bones;
	public static ArrayList<Shopkeeper> shopkeepers;
	public static ArrayList<Lock> locks;
	public static ArrayList<Tutorial> tutorial;
	public static ArrayList<Pot> pots, brokenPots;
	public static ArrayList<Cobweb> cobwebs, burnedCobwebs;
	public static ArrayList<Potion> potions, collectedPotions;
	public static ArrayList<Torch> torches;
	public static ArrayList<Obstacle> obstacles;
	public static ArrayList<Candle> candles;
	public static  ArrayList<Fire> fires;
	public static ArrayList<Column> columns;
	public static ArrayList<Roof> roofs;
	public float PLAYER_HORIZONTAL_SPEED = 0f;
	public float PLAYER_VERTICAL_SPEED = 0f;
	public float PLAYER_X = 0f;
	public float PLAYER_Y = 0f;
	public static float PLAYER_SPEED_MULTI;
	private TiledMapRenderer renderer;
	public static OrthographicCamera camera;
	public static final float DEFAULT_VIEWPORT_WIDTH = 300f;
	public static HUD hud;
	public static Music roomClear, swordSlash, level1Music;
	public static RayHandler rayHandler;
	private PointLight playerTorch;
	private BitmapFont.BitmapFontData bmfData;
	public static BitmapFont defaultFont, defaultFont2;
	public static ArrayList<Text> messages;
	public AssetManager assetManager;
	public float stateTime, stateTime2, stateTime3;
	public int index = 0;
	public TextureRegion currentFrame;
	public Ray playerSightRay;

	private boolean leanDown = false, leanUp = false, leanLeft = false, leanRight = false, leanUpLeft = false, leanUpRight = false;

	@Override
	public void create() {

		debug = false;

		world = new World(new Vector2(0, 0f), false);
		assetManager = new AssetManager();
		playerBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		tutoBatch = new SpriteBatch();
		enemySkullBatch = new SpriteBatch();
		enemySpiderBatch = new SpriteBatch();
		arrowBatch = new SpriteBatch();
		skullBatch = new SpriteBatch();
		boneBatch = new SpriteBatch();
		doorBatch = new SpriteBatch();
		lockBatch = new SpriteBatch();
		potBatch = new SpriteBatch();
		potionBatch = new SpriteBatch();
		obstacleBatch = new SpriteBatch();
		candleBatch = new SpriteBatch();
		columnTopBatch = new SpriteBatch();
		columnStemBatch = new SpriteBatch();
		columnBaseBatch = new SpriteBatch();
		pedestalBatch = new SpriteBatch();
		fireBatch = new SpriteBatch();
		flameBatch = new SpriteBatch();
		fontBatch = new SpriteBatch();
		roofBatch = new SpriteBatch();
		roofs = new ArrayList<>();
		inventoryBatch = new SpriteBatch();
		cobBatch = new SpriteBatch();
		reversedArrowMap = false;
		reversedSkullMap = false;
		reversedPotMap = false;
		player = new Player();
		enemies = new ArrayList<>();
		enemySkulls = new ArrayList<>();
		enemySpiders = new ArrayList<>();
		deadEnemyBodies = new ArrayList<>();
		dyingSkulls = new ArrayList<>();
		dyingSpiders = new ArrayList<>();
		skulls = new ArrayList<>();
		brokenSkulls = new ArrayList<>();
		bones = new ArrayList<>();
		cobwebs = new ArrayList<>();
		burnedCobwebs = new ArrayList<>();
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
		extinguishedRespawnFires = new ArrayList<>();
		//columnTops = new ArrayList<>();
		//columnStems = new ArrayList<>();
		//columnBases = new ArrayList<>();
		collectedPotions = new ArrayList<Potion>();
		obstacles = new ArrayList<Obstacle>();
		candles = new ArrayList<Candle>();
		messages = new ArrayList<Text>();

		Vector2 vec = new Vector2();
		vec.x = PLAYER_X;
		vec.y = PLAYER_Y;
		PLAYER_SPEED_MULTI = 60f;

		//TODO Set player speed here so we can use dynamic speed adjustment e.g entering a cobweb

		//playerSightRay = new Ray<>(vec,);

		//roomClear = Gdx.audio.newMusic(Gdx.files.internal("NinjaAdventure/Sounds/Menu/Accept.wav"));
		//swordSlash = Gdx.audio.newMusic(Gdx.files.internal("Sounds/slash.mp3"));

		final BodyFactory bf = new BodyFactory();
		final CreateAssets tx = CreateAssets.getInstance();
		GameContactListener lc = new GameContactListener();
		tx.textureRegionBuilder();




//		final CreateSound cs = new CreateSound();
//		cs.createSound();

		//FileHandle file = new FileHandle("");
		//bmfData = new BitmapFont.BitmapFontData();
		//bmfData.fontFile = file;
		//defaultFont = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/GreekAlphabet-export.fnt"));

		//defaultFont = new BitmapFont(bmfData.fontFile,tx.fontTexture);

		//defaultFont = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/GreekAlphabetConcise-export.fnt"),
		//		Gdx.files.internal("HellasDungeon/Font/GreekAlphabetConcise-export.png"), false);


		defaultFont = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.png"), false);

		defaultFont2 = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.png"), false);

		Color c = new Color();
		c.set(1,1,1,1);
		//Text t =  new Text(defaultFont, "TEST MESSAGE", c, false);
		//messages.add(t);

		Text level1StartText =  new Text(defaultFont, "CLAY CATACOMBS", c, true, 10f, 1f, true, false, null, 0);
		//messages.add(level1StartText);
		//0.045f

		//Text roomCleared =  new Text(defaultFont, "ROOM CLEARED", c, true, 1f, 0.045f, true);
		//messages.add(roomCleared);

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


		//TODO: Add basic menu/loading screen here

		//create the Box2D ray handler
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0f, 0f, 0f, 0.010f);
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
		playerTorch = new PointLight(rayHandler, 1000, new Color(0.25f, 0.20f, 0, 0.45f), 100, PLAYER_X, PLAYER_Y);
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
		respawnFireMap = new ArrayMap<Body, Fire>();
		cobArrayMap = new ArrayMap<Body, Cobweb>();

		//create an input processor to handle single input events - see inputUpdate() for held down inputs


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



				if (button == 0 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking)) {
					//if player presses left mouse attack with the swordBody
					float playerMeleeAttackSpeedInSeconds = 0.40f;
					playerMeleeAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						tx.playerSprite = tx.playerAttackDown;
						swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);
						player.playerBody.applyForce(0,-100000,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerUp) || leanUp) {
						tx.playerSprite = tx.playerAttackUp;
						swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, 17.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, false);
						swordHitbox.setUserData("UpSword");
						swordHitbox.setSensor(true);
						player.playerBody.applyForce(0,100000,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						swordBody = bf.createSwordBody(world, player.playerBody, -15.5f, -1.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, true);
						swordHitbox.setUserData("LeftSword");
						swordHitbox.setSensor(true);
						player.playerBody.applyForce(-100000,0,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						swordBody = bf.createSwordBody(world, player.playerBody, 15.5f, -1.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, true);
						swordHitbox.setUserData("RightSword");
						swordHitbox.setSensor(true);
						player.playerBody.applyForce(100000,0,0,0,true);
					} else {
						tx.playerSprite = tx.playerAttackDown;
						swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);
						player.playerBody.applyForce(0,-100000,0,0,true);
					}

					swordBody.setUserData("Sword");

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
							//resume player movement after a short delay and remove swordBody hitbox
							playerPaused = false;
							swordBody.destroyFixture(swordHitbox);

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
				if (button == 1 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking)) {
					float playerRangedAttackSpeedInSeconds = 0.50f;
					playerRangedAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -400f);
						player.playerBody.applyForce(0,150000,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerUp) || leanUp) {
						playerDirection = "Up";
						tx.playerSprite = tx.playerAttackUp;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y + 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("UpArrow");
						arrowBody.setLinearVelocity(0, 400f);
						player.playerBody.applyForce(0,-150000,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						playerDirection = "Left";
						tx.playerSprite = tx.playerAttackLeft;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 14f, player.playerBody.getPosition().y+1);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("LeftArrow");
						arrowBody.setLinearVelocity(-400f, 0);
						player.playerBody.applyForce(150000,0,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						playerDirection = "Right";
						tx.playerSprite = tx.playerAttackRight;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x + 14f, player.playerBody.getPosition().y+1);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("RightArrow");
						arrowBody.setLinearVelocity(400f, 0);
						player.playerBody.applyForce(-150000,0,0,0,true);
					}
					//only triggers if the player hasn't moved at all yet - player starts facing down
					else {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -400f);
						player.playerBody.applyForce(0,150000,0,0,true);
					}
					//pause player in place while attacking (attacks must be timed correctly!)
					arrowBody.setUserData("Arrow");
					if (player.hasGreekFire){
						arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, true));
						//player.greekFireUses--;
					} else {
						arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, false));
					}
					arrowArrayMap.put(arrowBody, arrow);

					playerPaused = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay and remove swordBody hitbox
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
					if (keycode == Keys.NUM_9) {
						hud.inventory.addPotion();
					}
				}
				/*

					// (For Debugging) Damage player
					if (keycode == Keys.NUM_0) {
						hud.healthBar.LoseHealth(0.5f);
					}

					if (keycode == Keys.NUM_2) {
						camera.zoom = 1f;
					}

					if (keycode == 10) {
						camera.zoom = 10f;
					}
				}
				else
				*/


				if (Gdx.input.isKeyPressed(Keys.F)) {
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				}

				if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
					Gdx.graphics.setWindowedMode(1280, 720);
				}



				if (player.buyingStock){

					if (keycode == Keys.NUM_1) {
						//String amount = player.shopkeeper.inventory.get(1).toString();
						//int x = Integer.parseInt(amount);
						//System.out.println(x);

						ShopItem item = (ShopItem) player.shopkeeper.inventory.get(0);

						int money = Integer.parseInt(hud.totalGoldAsString);

						if (money >= item.cost && !item.purchased) {

							System.out.println(item.kind + "<- THE TYPE OF ITEM");

							item.purchased = true;
							switch (item.kind) {

								case "KYKEON": {
									//System.out.println("THIS IS A TEST TO SEE IF POTIONS CAN BE ADDED VIA THE HUD");
									//hud.inventory.addPotion();
									Potion potion = new Potion(world, player.shopkeeper.posX, player.shopkeeper.posY+ 16,1);
									potion.createPotion(potionArrayMap,rayHandler);
									potions.add(potion);
									potionArrayMap.put(potion.potionBody, potion);
									break;

								}
								case "SHIELD": {
									player.hasShield = true;

									break;

								}
								case "GREEK FIRE": {
									player.hasGreekFire = true;
									player.greekFireUses = 10;
									//TODO: add fire arrows
									break;
								}
								case "TORCH": {
									playerTorch.remove();
									playerTorch = new PointLight(rayHandler, 1000, new Color(0.25f, 0.20f, 0, 0.85f), 95, PLAYER_X, PLAYER_Y);
									playerTorch.attachToBody(player.playerBody);
									playerTorch.setIgnoreAttachedBody(true);
									playerTorch.setSoftnessLength(100f);
									break;
								}
								case "BELT": {
									//hud.inventory.Capacity = hud.inventory.Capacity + 2;
									hud.inventory.changeCapacity(1, true);
									break;
								}
							}

							//int moneyAfterPurchase = money - item.cost;

							hud.updateGold(item.cost, false);

							System.out.println(item.kind);
							System.out.println(item.index);
							//item.amount--;

							player.shopkeeper.inventoryText.remove(0);
							player.shopkeeper.inventoryText.remove(0);

							Text t1 = player.shopkeeper.Stock(item.kind, item.index);
							Text t2 = player.shopkeeper.DescribeStock(item.kind,item.index,item.cost);
						}
					}

					if (keycode == Keys.NUM_2) {
						//String amount = player.shopkeeper.inventory.get(1).toString();
						//int x = Integer.parseInt(amount);
						//System.out.println(x);
						ShopItem firstItem = (ShopItem) player.shopkeeper.inventory.get(0);
						ShopItem item = (ShopItem) player.shopkeeper.inventory.get(1);

						int money = Integer.parseInt(hud.totalGoldAsString);

						if (money >= item.cost && !item.purchased) {
							item.purchased = true;

							switch (item.kind) {

								case "KYKEON": {
									//System.out.println("THIS IS A TEST TO SEE IF POTIONS CAN BE ADDED VIA THE HUD");
									//hud.inventory.addPotion();
									Potion potion = new Potion(world, player.shopkeeper.posX, player.shopkeeper.posY+ 16,1);
									potion.createPotion(potionArrayMap,rayHandler);
									potions.add(potion);
									potionArrayMap.put(potion.potionBody, potion);
									break;

								}
								case "SHIELD": {
									//TODO: add shield item
									player.hasShield = true;

									break;

								}
								case "GREEK FIRE": {
									player.hasGreekFire = true;
									player.greekFireUses = 10;
									break;
								}
								case "TORCH": {
									playerTorch.remove();
									playerTorch = new PointLight(rayHandler, 1000, new Color(0.25f, 0.20f, 0, 0.85f), 95, PLAYER_X, PLAYER_Y);
									playerTorch.attachToBody(player.playerBody);
									playerTorch.setIgnoreAttachedBody(true);
									playerTorch.setSoftnessLength(100f);
									break;
								}
								case "BELT": {
									//hud.inventory.Capacity = hud.inventory.Capacity + 2;
									hud.inventory.changeCapacity(1, true);
									break;
								}
							}
							//int moneyAfterPurchase = money - item.cost;

							hud.updateGold(item.cost, false);

						System.out.println(item.kind);
						System.out.println(item.index);
						//item.amount--;

					//	x--;
							if (firstItem.purchased) {
								player.shopkeeper.inventoryText.remove(0);
								player.shopkeeper.inventoryText.remove(0);
							}
							else {
								player.shopkeeper.inventoryText.remove(2);
								player.shopkeeper.inventoryText.remove(2);
							}


						Text t1 = player.shopkeeper.Stock(item.kind, item.index);
						Text t2 = player.shopkeeper.DescribeStock(item.kind,item.index,item.cost);
						}
					}

					if (keycode == Keys.NUM_3) {
						//String amount = player.shopkeeper.inventory.get(1).toString();
						//int x = Integer.parseInt(amount);
						//System.out.println(x);
						ShopItem firstItem = (ShopItem) player.shopkeeper.inventory.get(0);
						ShopItem secondItem = (ShopItem) player.shopkeeper.inventory.get(1);
						ShopItem item = (ShopItem) player.shopkeeper.inventory.get(2);

						int money = Integer.parseInt(hud.totalGoldAsString);

						if (money >= item.cost && !item.purchased) {
							item.purchased = true;

							switch (item.kind) {

								case "KYKEON": {
									//System.out.println("THIS IS A TEST TO SEE IF POTIONS CAN BE ADDED VIA THE HUD");
									//hud.inventory.addPotion();
									Potion potion = new Potion(world, player.shopkeeper.posX, player.shopkeeper.posY+ 16,1);
									potion.createPotion(potionArrayMap,rayHandler);
									potions.add(potion);
									potionArrayMap.put(potion.potionBody, potion);
									break;

								}
								case "SHIELD": {
									//TODO: add shield item
									player.hasShield = true;


									break;

								}
								case "GREEK FIRE": {
									player.hasGreekFire = true;
									player.greekFireUses = 10;
									break;
								}
								case "TORCH": {
									playerTorch.remove();
									playerTorch = new PointLight(rayHandler, 1000, new Color(0.25f, 0.20f, 0, 0.85f), 95, PLAYER_X, PLAYER_Y);
									playerTorch.attachToBody(player.playerBody);
									playerTorch.setIgnoreAttachedBody(true);
									playerTorch.setSoftnessLength(100f);
									//playerTorch.setXray(true);
									break;
								}
								case "BELT": {
									//hud.inventory.Capacity = hud.inventory.Capacity + 2;
									hud.inventory.changeCapacity(1, true);
									break;
								}
							}
							//int moneyAfterPurchase = money - item.cost;

							hud.updateGold(item.cost, false);

							System.out.println(item.kind);
							System.out.println(item.index);
							//item.amount--;

							//	x--;
							if (firstItem.purchased && !secondItem.purchased) {
								player.shopkeeper.inventoryText.remove(2);
								player.shopkeeper.inventoryText.remove(2);
							}
							else if (secondItem.purchased && !firstItem.purchased) {
								player.shopkeeper.inventoryText.remove(0);
								player.shopkeeper.inventoryText.remove(0);
							}
							else {
								player.shopkeeper.inventoryText.remove(4);
								player.shopkeeper.inventoryText.remove(4);
							}


							Text t1 = player.shopkeeper.Stock(item.kind, item.index);
							Text t2 = player.shopkeeper.DescribeStock(item.kind,item.index,item.cost);
						}
					}
				}
				// Use potion
				if (keycode == Keys.E) {
					if (hud.inventory.Size > 0) {
						hud.inventory.usePotion(1);
						hud.healthBar.GainHealth(1.5f);
					}
				}

				if (keycode == Keys.P) {
					if (!debug) {
						debug = true;
					} else {
						debug = false;
					}
				}

				if ((keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT) && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking) && player.hasShield) {
					float playerShieldAttackSpeedInSeconds = 0.85f;
					playerShieldAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown) || leanDown) {
						tx.playerSprite = tx.playerAttackDown;
						shieldBody = bf.createShieldBody(world, player.playerBody, -2f, -9.5f);
						shieldHitbox = bf.createShieldHitbox(shieldBody, false);
						shieldHitbox.setUserData("DownShield");
					}
					if (tx.playerSprite.equals(tx.playerUp) || leanUp) {
						tx.playerSprite = tx.playerAttackUp;
						shieldBody = bf.createShieldBody(world, player.playerBody, -3f, 12.5f);
						shieldHitbox = bf.createShieldHitbox(shieldBody, false);
						shieldHitbox.setUserData("UpShield");
					}
					if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						shieldBody = bf.createShieldBody(world, player.playerBody, -11.5f, -2f);
						shieldHitbox = bf.createShieldHitbox(shieldBody, true);
						shieldHitbox.setUserData("LeftShield");
					}
					if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						shieldBody = bf.createShieldBody(world, player.playerBody, 12.5f, -2f);
						shieldHitbox = bf.createShieldHitbox(shieldBody, true);
						shieldHitbox.setUserData("RightShield");
					}

					shieldBody.setUserData("Shield");

					//pause player in place while attacking (attacks must be timed correctly!)
					playerPaused = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay and remove swordBody hitbox
							playerPaused = false;
							shieldBody.destroyFixture(shieldHitbox);

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

							playerShieldAttacking = false;
						}
					}, playerShieldAttackSpeedInSeconds);
				}

				//pressing '6' shows some debug data
				if (keycode == 13) {
					//System.out.println("PLAYER X: " + player.playerBody.getPosition().x);
					//System.out.println(" PLAYER Y: " + player.playerBody.getPosition().y);
					System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).x1);
					System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).y1);
					System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).doorLocations);
					System.out.println(GenerateLevel.init.roomList.get(player.currentRoom + 1).doorLocations);
				}

				//if player presses space attack with the swordBody
				if (((keycode == 62)) && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking)) {
					float playerMeleeAttackSpeedInSeconds = 0.40f;
					playerMeleeAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						tx.playerSprite = tx.playerAttackDown;
						swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);

						player.playerBody.applyForce(0,-100000,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerUp) || leanUp) {
						tx.playerSprite = tx.playerAttackUp;
						swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, 17.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, false);
						swordHitbox.setUserData("UpSword");
						swordHitbox.setSensor(true);

						player.playerBody.applyForce(0,100000,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						swordBody = bf.createSwordBody(world, player.playerBody, -15.5f, -1.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, true);
						swordHitbox.setUserData("LeftSword");
						swordHitbox.setSensor(true);

						player.playerBody.applyForce(-100000,0,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						swordBody = bf.createSwordBody(world, player.playerBody, 15.5f, -1.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, true);
						swordHitbox.setUserData("RightSword");
						swordHitbox.setSensor(true);

						player.playerBody.applyForce(100000,0,0,0,true);
					} else {
						tx.playerSprite = tx.playerAttackDown;
						swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
						swordHitbox = bf.createSwordHitbox(swordBody, false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);

						player.playerBody.applyForce(0,-100000,0,0,true);
					}

					swordBody.setUserData("Sword");

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
							//resume player movement after a short delay and remove swordBody hitbox
							playerPaused = false;
							swordBody.destroyFixture(swordHitbox);

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
				if (keycode == 66 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking)) {
					float playerRangedAttackSpeedInSeconds = 0.50f;
					stateTime2 = 0f;
					playerRangedAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -400f);

						player.playerBody.applyForce(0,150000,0,0,true);
					} else if (tx.playerSprite.equals(tx.playerUp) || leanUp) {
						playerDirection = "Up";
						tx.playerSprite = tx.playerAttackUp;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y + 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("UpArrow");
						arrowBody.setLinearVelocity(0, 400f);

						player.playerBody.applyForce(0,-150000,0,0,true);

					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						playerDirection = "Left";
						tx.playerSprite = tx.playerAttackLeft;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 14f, player.playerBody.getPosition().y+1);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("LeftArrow");
						arrowBody.setLinearVelocity(-400f, 0);

						player.playerBody.applyForce(150000,0,0,0,true);

					} else if (tx.playerSprite.equals(tx.playerRight)) {
						playerDirection = "Right";
						tx.playerSprite = tx.playerAttackRight;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x + 14f, player.playerBody.getPosition().y+1);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("RightArrow");
						arrowBody.setLinearVelocity(400f, 0);

						player.playerBody.applyForce(-150000,0,0,0,true);
					}
					//only triggers if the player hasn't moved at all yet - player starts facing down
					else {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -400f);

						player.playerBody.applyForce(0,150000,0,0,true);
					}
					//pause player in place while attacking (attacks must be timed correctly!)

					arrowBody.setUserData("Arrow");
					if (player.hasGreekFire){
						arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, true));
						player.greekFireUses--;
					} else {
						arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, false));
					}
					arrowArrayMap.put(arrowBody, arrow);

					playerPaused = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay and remove swordBody hitbox
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

		if (!debug) {
			//set the window mode to fullscreen and hide the cursor when in the game window
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
		}
	}

	@Override
	public void render() {

			// kill game when player health is 0
			if (hud.healthBar.currentHealth == 0) {
				System.out.println("YOU DIED IN ROOM " + player.currentRoom);
				Gdx.app.exit();
			}

		//hud.startLevel();

			// win the game if all enemySkulls are dead
			if (enemySkulls.isEmpty()) {
				hud.winLevel();
			}

			final CreateAssets tx = CreateAssets.getInstance();
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

			//	if (!skulls.isEmpty()) {

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

				//render door locks when a player enters a new room with enemySkulls
				for (Lock l : r.locks) {
					if (l.visible) {
						lockBatch.begin();
						Lock.renderLock(lockBatch, l.direction, l.lockBody.getPosition().x, l.lockBody.getPosition().y);
						lockBatch.end();
					}
				}
			}

			//adds all skulls that have been created to the array map for manipulation
			for (Skull s : skulls) {

				if (!s.skullCreated) {
					skullArrayMap.put(s.createSkull(skullArrayMap), s);
					s.room = player.currentRoom;
				}

			}

		//for (Room r : GenerateLevel.init.roomList) {
			for (Skull s : skulls) {
				//TODO Fix - skulls pick the furthest spawner
				if (!GenerateLevel.init.roomList.get(s.room).spawners.isEmpty()) {
					for (Fire f : GenerateLevel.init.roomList.get(player.currentRoom).spawners) {
						if (s.resurrectable) {
						//System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).index);
						if (s.skullCreated) {
							boolean rayResult = s.rayCastSkull(GenerateLevel.init.roomList.get(player.currentRoom), f);
							//System.out.println("ROOM INDEX OF FIRE" + GenerateLevel.init.roomList.get(player.currentRoom).index);
							if (rayResult && !s.resurrecting && f.active) {
								//System.out.println("Resurrecting dead enemy");
								Fire respawnFire = new Fire(world, rayHandler, s.skullX - 4, s.skullY - 8, false, 0f, 2, false);
								respawnFire.createFire(new Color(0,0,1f,0.6f), 15);
								fires.add(respawnFire);
								s.resurrecting = true;
								Timer.schedule(new Timer.Task() {
									@Override
									public void run() {
										//System.out.println("RESPAWNING SKULL AFTER DELAY");
										if (f.active) {
											brokenSkulls.add(s);
											EnemySkull respawnedEnemy = new EnemySkull(world, s.skullX, s.skullY);
											DungeonCrawler.enemySkulls.add(respawnedEnemy);
											respawnedEnemy.rayCastable = true;
										}
									}
								}, 5f);
								extinguishedRespawnFires.add(respawnFire);
								//respawnFire.fireLight = null;
								//respawnFire.fireBody.setActive(false);

								}
							}
						}
					}
				}
			}
			//}

		//respawnFireMap
		//extinguishedRespawnFires
		//GenerateLevel.init.roomList.get(player.currentRoom).spawners




		if (!potionArrayMap.isEmpty()) {
			for (OrderedMap.Entry<Body, Potion> potionEntry : potionArrayMap.entries()) {
				Potion value = potionEntry.value;
				//render each potion
				potionBatch.begin();
				if (value.type == 1) {
					Potion.renderPotion(potionBatch, tx.potionItemSprite, potionEntry.key.getPosition().x, potionEntry.key.getPosition().y);
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

					//if (hud.inventory.Size )
					hud.inventory.addPotion();
					potion.potionLight.remove();
					potions.remove(potion);
					potionArrayMap.removeKey(potion.potionBody);
					world.destroyBody(potion.potionBody);
					potionIt.remove();
				}
			}
		}
		for (Cobweb c : cobwebs) {
			if (!c.cobCreated) {
				cobArrayMap.put(c.createCobweb(cobArrayMap), c);
			}
		}

		if (!cobArrayMap.isEmpty()) {
			for (OrderedMap.Entry<Body, Cobweb> cobEntry : cobArrayMap.entries()) {
				Cobweb value = cobEntry.value;
				//render each cobweb
				cobBatch.begin();
				Cobweb.renderCobweb(cobBatch,tx.cobwebSprite,cobEntry.key.getPosition().x, cobEntry.key.getPosition().y);
				cobBatch.end();
			}

			Iterator<Cobweb> cobIt = burnedCobwebs.iterator();
			if (cobIt.hasNext()) {
				Cobweb cob = cobIt.next();
				if (burnedCobwebs.contains(cob)) {

					cobwebs.remove(cob);
					cobArrayMap.removeKey(cob.cobBody);
					world.destroyBody(cob.cobBody);
					world.destroyBody(cob.innerCobBody);
					cobIt.remove();
				}
			}
		}

			//destructible objects safe removers - Skulls - Arrows - Pots - Potions

			GameObjectDestroyer skullBasher9000 = new GameObjectDestroyer();
			//skullBasher9000.destroyObject(skullArrayMap,brokenSkulls,skulls,"Skull",);

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
						Bone bone = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, false, false, new Vector2());
						bone.createBone();
						bones.add(bone);
						boneArrayMap.put(bone.boneBody, bone);

						Bone bone2 = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, true, false, new Vector2());
						bone2.createBone();
						bones.add(bone2);
						boneArrayMap.put(bone2.boneBody, bone2);

						skulls.remove(skull);
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
						//one in 20 chance to get a potion from a pot - subject to change (was 7)
						int min = 1;
						int max = 12;
						int potionChance = (int) (Math.random() * (max - min + 1)) + min;
						if (potionChance == 12) {
							//create potion object
							Potion potion = new Potion(world, pot.potBody.getPosition().x, pot.potBody.getPosition().y, 1);
							potion.createPotion(potionArrayMap, rayHandler);
							potions.add(potion);
							potionArrayMap.put(potion.potionBody, potion);
						} else if (potionChance == 1) {
							//Bone bone = new Bone(world, pot.potBody, pot.potBody.getPosition().x, pot.potBody.getPosition().y, false, false, new Vector2());
							//bone.createBone();
							//bones.add(bone);
							//boneArrayMap.put(bone.boneBody, bone);

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
						Potion.renderPotion(potionBatch, tx.potionItemSprite, potionEntry.key.getPosition().x, potionEntry.key.getPosition().y);
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
						obstacleBatch.draw(tx.obstacle1Sprite, o.obBody.getPosition().x - 8f, o.obBody.getPosition().y - 8f, 16, 16);
						break;
					case 2:
						obstacleBatch.draw(tx.obstacle2Sprite, o.obBody.getPosition().x - 8f, o.obBody.getPosition().y - 8f, 16, 16);
						break;
					case 3:
						obstacleBatch.draw(tx.obstacle3Sprite, o.obBody.getPosition().x - 8f, o.obBody.getPosition().y - 8f, 16, 16);
						break;
				}
				obstacleBatch.end();
			}

		for (Candle c : candles) {
			candleBatch.begin();
			switch (c.type){
				case 1:
					candleBatch.draw(tx.candleSprite, c.candBody.getPosition().x - 8f, c.candBody.getPosition().y - 8f, 16, 16);
					break;
				case 2:
					candleBatch.draw(tx.candlesSprite, c.candBody.getPosition().x - 8f, c.candBody.getPosition().y - 8f, 16, 16);
					break;
			}
			candleBatch.end();
		}

		for (Fire f : fires) {
			if (f.type == 3) {
				//TODO: Finish small flame
				currentFrame = tx.flameAnimation.getKeyFrame(stateTime, f.active);

				fireBatch.begin();
				if (f.upDown) {
					Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, true);
				} else {
					Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, false);
				}

				fireBatch.end();
			}


		}




			playerBatch.begin();
			//draw playerSprite on player Box2D object
		if (playerShieldAttacking) {
			if (tx.playerSprite.equals(tx.playerAttackUp)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 11f, player.playerBody.getPosition().y + 7f, 16, 8, 16, 8, 1, 1, 0);
			} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 26f, player.playerBody.getPosition().y - 20f, 16, 8, 16, 8, 1, 1, 180);
			} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 30f, player.playerBody.getPosition().y - 2f, 16, 8, 16, 8, 1, 1, 90);
			} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 18f, 16, 8, 16, 8, 1, 1, 270);
			}
		}
			playerBatch.draw(tx.playerSprite, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 6f, 16, 16);
			if (playerMeleeAttacking) {
				//add the lanceSprite to the corresponding attack playerDirection
				if (tx.playerSprite.equals(tx.playerAttackUp)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 13f, player.playerBody.getPosition().y - 4f, 7, 14, 7, 14, 1, 1, 180);
				} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 6f, player.playerBody.getPosition().y - 18f, 7, 14, 7, 14, 1, 1, 0);
				} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 15f, player.playerBody.getPosition().y - 19f, 7, 14, 7, 14, 1, 1, 270);
				} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x + 1f, player.playerBody.getPosition().y - 12f, 7, 14, 7, 14, 1, 1, 90);
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



			//render enemy skull sprites
			for (EnemySkull e : enemySkulls) {
				if (e.rayCastable) {
					e.detectPlayer();
				}
				if ((e.playerSighted && e.playerInRange)){
					//System.out.println(Gdx.graphics.getDeltaTime());
					if (e.timeSinceAlerted > (Gdx.graphics.getDeltaTime() * 110)){
						e.timeSinceAlerted = 0f;
						Vector2 vec1 = new Vector2(e.enemyBody.getPosition());
						Vector2 vec2 = new Vector2(Player.playerBody.getPosition());

						//throw bones directly at the player but add a small random offset
						float x = MathUtils.atan2(vec2.y - vec1.y, vec2.x - vec1.x);
						float randomOffset = Random.randomFloat(1.3f,0.3f);
						randomOffset = randomOffset / 10;
						boolean random = Random.randomBoolean();
						Vector2 finalX = new Vector2((float)Math.cos(x),(float)Math.sin(x));

						if (random) {
							finalX.x = finalX.x + randomOffset;
							finalX.y = finalX.y + randomOffset;
						} else {
							finalX.x = finalX.x - randomOffset;
							finalX.y = finalX.y - randomOffset;
						}
						//float result = (e.enemyAI.getOrientation() / (x * MathUtils.PI));
						//result = result - MathUtils.PI / 2;
						//System.out.println(x);

						Bone bone = new Bone(world, e.enemyBody, e.enemyBody.getPosition().x, e.enemyBody.getPosition().y, false,  true, finalX);
						bone.createBone();
						bones.add(bone);
						boneArrayMap.put(bone.boneBody, bone);


					} else {
						e.timeSinceAlerted = e.timeSinceAlerted + Gdx.graphics.getDeltaTime();
					}

					//System.out.println("TIME SINCE ALERTED" + e.timeSinceAlerted);
					//e.throwBoneAtPlayer();

					if (!e.alerted) {

						//FontController.drawFont(fontBatch,);
						e.alertMessage.showing = true;
						e.alertMessage.fade = true;
						e.alerted = true;

						//System.out.println("LOG");
						e.alertMessage.textX = e.enemyAI.getBody().getPosition().x - 2f;
						e.alertMessage.textY = e.enemyAI.getBody().getPosition().y + 8f;
						messages.add(e.alertMessage);
					}
					e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
					//e.playerSighted = false;
				}
				enemySkullBatch.begin();
				if (!e.alerted) {
					enemySkullBatch.draw(tx.enemySkullSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e.timeSinceAlerted >= 1) {
					enemySkullBatch.draw(tx.enemySkullAlertedSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				} else {
					enemySkullBatch.draw(tx.enemySkullSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				}
				enemySkullBatch.end();
			}

		//if (!dyingSpiders.isEmpty()) {
			for (EnemySkull deadSkull : dyingSkulls) {
				deadSkull.getStateMachine().changeState(EnemySkullState.DIE);
			}
			dyingSkulls.clear();
		//}


		//render enemy skull sprites
		for (EnemySpider e2 : enemySpiders) {
			if (e2.rayCastable) {
				e2.detectPlayer();
			}
			if ((e2.playerSighted && e2.playerInRange)){
				//System.out.println(Gdx.graphics.getDeltaTime());
				if (e2.timeSinceAlerted > (Gdx.graphics.getDeltaTime() * 110)){
					//float result = (e.enemyAI.getOrientation() / (x * MathUtils.PI));
					//result = result - MathUtils.PI / 2;
					//System.out.println(x);



				} else {
					e2.timeSinceAlerted = e2.timeSinceAlerted + Gdx.graphics.getDeltaTime();
				}

				//System.out.println("TIME SINCE ALERTED" + e.timeSinceAlerted);
				//e.throwBoneAtPlayer();

				if (!e2.alerted) {

					//FontController.drawFont(fontBatch,);
					e2.alertMessage.showing = true;
					e2.alertMessage.fade = true;
					e2.alerted = true;

					//System.out.println("LOG");
					e2.alertMessage.textX = e2.enemyAI.getBody().getPosition().x - 2f;
					e2.alertMessage.textY = e2.enemyAI.getBody().getPosition().y + 8f;
					messages.add(e2.alertMessage);
				}
				e2.getStateMachine().changeState(EnemySpiderState.GO_TO_PLAYER);
				//e.playerSighted = false;
			}
			enemySpiderBatch.begin();
			if (!e2.alerted) {
				if (e2.facing == "Up") {
					enemySpiderBatch.draw(tx.enemySpiderUpSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Down") {
					enemySpiderBatch.draw(tx.enemySpiderDownSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Left") {
					enemySpiderBatch.draw(tx.enemySpiderLeftSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Right") {
					enemySpiderBatch.draw(tx.enemySpiderRightSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				}
			}
			enemySpiderBatch.end();
		}

		//if (!dyingSpiders.isEmpty()) {
			for (EnemySpider deadSpider : dyingSpiders) {
				deadSpider.getStateMachine().changeState(EnemySpiderState.DIE);
			}
			dyingSpiders.clear();
		//}

		for (Body body : deadEnemyBodies) {
			world.destroyBody(body);
		}

		deadEnemyBodies.clear();



		if (!boneArrayMap.isEmpty()) {

			if (!reversedBoneMap) {
				boneArrayMap.reverse();
				reversedBoneMap = true;
			}

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
					world.destroyBody(boneBody);
					boneIt.remove();
					bones.remove(boneBody);

				}
			}
		}

			for (Shopkeeper s : shopkeepers) {
				playerBatch.begin();
				playerBatch.draw(tx.shopkeeperSprite, s.shopBody.getPosition().x - 8f, s.shopBody.getPosition().y - 7f, 16, 16);
				playerBatch.end();
			}

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
				case 18:
					columnBaseBatch.draw(tx.colBase2, c.columnX, c.columnY);
					break;
			}
			columnBaseBatch.end();
		}

		for (Column c : columns) {
			pedestalBatch.begin();
			switch (c.type) {
				case 14:
					pedestalBatch.draw(tx.pedestal1,c.columnX,c.columnY);
					break;
				case 15:
					pedestalBatch.draw(tx.pedestal2,c.columnX,c.columnY);
					break;
				case 16:
					pedestalBatch.draw(tx.pedestal3,c.columnX,c.columnY);
					break;
				case 17:
					pedestalBatch.draw(tx.pedestal4,c.columnX,c.columnY);
					break;
			}
			pedestalBatch.end();
		}

			//check if there are any fired arrows
			if (!arrowArrayMap.isEmpty()) {
				for (OrderedMap.Entry<Body, Arrow> arrowEntry : arrowArrayMap.entries()) {
					Arrow value = arrowEntry.value;
					Body key = arrowEntry.key;

					value.stateTime += Gdx.graphics.getDeltaTime();
					value.stateTime2 += Gdx.graphics.getDeltaTime();



					//render each individual arrow
					arrowBatch.begin();
					flameBatch.begin();
					if (value.onFire) {
						TextureRegion flameFrame = tx.flameAnimation.getKeyFrame(value.stateTime2,true);
						TextureRegion currentFrame = tx.arrowAnimation.getKeyFrame(value.stateTime, true);
						Arrow.renderFireArrow(arrowBatch, currentFrame, flameFrame, arrowEntry.value.direction, key.getPosition().x, key.getPosition().y);
						value.createArrowFlameLight(value);
					}else {
						//System.out.println("YESSSSSS");
						TextureRegion currentFrame = tx.arrowAnimation.getKeyFrame(value.stateTime, true);
						Arrow.renderArrow(arrowBatch, currentFrame, arrowEntry.value.direction, key.getPosition().x, key.getPosition().y);
					}

					//arrowBatch.draw(currentFrame, key.getPosition().x, key.getPosition().y);
					flameBatch.end();
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
						if (arrowArrayMap.get(collidedBody).onFire) {
							arrowArrayMap.get(collidedBody).destroyArrowFlameLight(arrowArrayMap.get(collidedBody).flameLight);
							arrowArrayMap.get(collidedBody).onFire = false;
						}
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
			if (f.smoking) {
				f.fireLight.setColor(f.fireLight.getColor().r, f.fireLight.getColor().g, f.fireLight.getColor().b, 0.65f);
				TextureRegion currentFrame = tx.smokeAnimation.getKeyFrame(f.stateTime, false);
				fireBatch.begin();
				//fireBatch.draw(currentFrame, f.fireX, f.fireY);
				Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, false);
				fireBatch.end();
				f.stateTime += Gdx.graphics.getDeltaTime();

				if (tx.smokeAnimation.isAnimationFinished(f.stateTime)) {
						f.active = false;
						f.fireLight.setActive(false);
						f.stateTime = 0;
						f.smoking = false;
				}
			} else {
				if (!f.active) {

					TextureRegion currentFrame2 = tx.fireOutAnimation.getKeyFrame(f.stateTime, true);
					fireBatch.begin();
					Fire.renderFire(fireBatch, currentFrame2, f.fireX, f.fireY, f.smoking, false);
					fireBatch.end();
					f.stateTime += Gdx.graphics.getDeltaTime();

					} else {
					if (f.type == 1) {
						currentFrame = tx.fireAnimation.getKeyFrame(stateTime, f.active);

						fireBatch.begin();
						if (f.upDown) {
							Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, true);
						} else {
							Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, false);
						}

						fireBatch.end();
					} else if (f.type == 2) {

						//we want blue fire to respawn dead Enemies nearby every X number of frames
						//e.timeSinceAlerted = e.timeSinceAlerted + Gdx.graphics.getDeltaTime();


						currentFrame = tx.blueFireAnimation.getKeyFrame(stateTime, f.active);

						fireBatch.begin();
						if (f.upDown) {
							Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, true);
						} else {
							Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, false);
						}

						fireBatch.end();
					}



					}

				}

				//stateTime3 += Gdx.graphics.getDeltaTime();
				//if (tx.smokeAnimation.isAnimationFinished(f.stateTime)) {
				//}
			}

		for (Roof r : roofs) {
			roofBatch.begin();
			roofBatch.draw(tx.roofTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y, 64, 80);
			roofBatch.end();
		}

			fontBatch.begin();
			for (Text t : messages) {
				if (t.showing) {
					//defaultFont.draw(fontBatch,t.message,player.playerBody.getPosition().x, player.playerBody.getPosition().y);
					//defaultFont.draw(fontBatch,t.message,t.textX, t.textY);
					FontController.drawFadingFont(fontBatch, defaultFont, t.textX, t.textY, t, 1f);
				}
			}
			fontBatch.end();

			inventoryBatch.begin();
			for (Shopkeeper s : shopkeepers) {
				for (Text t2 : s.inventoryText) {
					if (t2.showing) {

						//defaultFont.draw(fontBatch,t.message,player.playerBody.getPosition().x, player.playerBody.getPosition().y);
						//defaultFont.draw(fontBatch,t.message,t.textX, t.textY);

						FontController.drawInventoryFont(inventoryBatch, defaultFont2, t2.textX, t2.textY, t2);
					}
				}
			}
			inventoryBatch.end();

			//toggle to enable or disable visible collision boxes
			if (debug) {
				for (EnemySkull enemySkull : enemySkulls) {
					//renders ray cast rays
					Ray<Vector2>[] rays = enemySkull.rayConfigurations[0].getRays();


					enemySkull.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
					enemySkull.shapeRenderer.setProjectionMatrix(camera.combined);
					enemySkull.shapeRenderer.setColor(1, 0, 0, 1);
					// shapeRenderer.setColor(Color.RED);
					//transform.idt();
					//shapeRenderer.setTransformMatrix(transform);
					for (int i = 0; i < rays.length; i++) {
						Ray<Vector2> ray = rays[i];
						enemySkull.tmp.set(ray.start);
						enemySkull.tmp2.set(ray.end);
						enemySkull.shapeRenderer.line(enemySkull.tmp, enemySkull.tmp2);
					}

					//render player rayCasts to Enemies
					if (enemySkull.rayCastable) {
						enemySkull.tmp3.set((Vector2) enemySkull.playerDetectionRay.start);
						enemySkull.tmp4.set((Vector2) enemySkull.playerDetectionRay.end);
						enemySkull.shapeRenderer.line(enemySkull.tmp3, enemySkull.tmp4);
					}
					enemySkull.shapeRenderer.end();
				}
				//raycast skulls within respawner radius
				for (Skull s : skulls) {
					if (s.resurrectable) {
						s.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
						s.shapeRenderer.setProjectionMatrix(camera.combined);
						s.shapeRenderer.setColor(0, 0, 1, 1);

						s.tmp.set((Vector2) s.respawnDetectionRay.start);
						s.tmp2.set((Vector2) s.respawnDetectionRay.end);
						s.shapeRenderer.line(s.tmp, s.tmp2);

						s.shapeRenderer.end();
					}
				}

				for (EnemySpider enemySpider : enemySpiders) {
					//renders ray cast rays
					Ray<Vector2>[] rays = enemySpider.rayConfigurations[0].getRays();


					enemySpider.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
					enemySpider.shapeRenderer.setProjectionMatrix(camera.combined);
					enemySpider.shapeRenderer.setColor(1, 0, 0, 1);
					// shapeRenderer.setColor(Color.RED);
					//transform.idt();
					//shapeRenderer.setTransformMatrix(transform);
					for (int i = 0; i < rays.length; i++) {
						Ray<Vector2> ray = rays[i];
						enemySpider.tmp.set(ray.start);
						enemySpider.tmp2.set(ray.end);
						enemySpider.shapeRenderer.line(enemySpider.tmp, enemySpider.tmp2);
					}

					//render player rayCasts to Enemies
					if (enemySpider.rayCastable) {
						enemySpider.tmp3.set((Vector2) enemySpider.playerDetectionRay.start);
						enemySpider.tmp4.set((Vector2) enemySpider.playerDetectionRay.end);
						enemySpider.shapeRenderer.line(enemySpider.tmp3, enemySpider.tmp4);
					}
					enemySpider.shapeRenderer.end();
				}


				b2dr.render(world, camera.combined);

				//TODO Add debug button to Scene

			}

			camera.update();
			hud.update();
			rayHandler.render();
			rayHandler.setCombinedMatrix(camera);
			obstacleBatch.setProjectionMatrix(camera.combined);
			candleBatch.setProjectionMatrix(camera.combined);
			playerBatch.setProjectionMatrix(camera.combined);
			arrowBatch.setProjectionMatrix(camera.combined);
			skullBatch.setProjectionMatrix(camera.combined);
			tutoBatch.setProjectionMatrix(camera.combined);
			boneBatch.setProjectionMatrix(camera.combined);
			enemySkullBatch.setProjectionMatrix(camera.combined);
			enemySpiderBatch.setProjectionMatrix(camera.combined);
			lockBatch.setProjectionMatrix(camera.combined);
			doorBatch.setProjectionMatrix(camera.combined);
			potBatch.setProjectionMatrix(camera.combined);
			cobBatch.setProjectionMatrix(camera.combined);
			potionBatch.setProjectionMatrix(camera.combined);
			columnBaseBatch.setProjectionMatrix(camera.combined);
			columnStemBatch.setProjectionMatrix(camera.combined);
			columnTopBatch.setProjectionMatrix(camera.combined);
			pedestalBatch.setProjectionMatrix(camera.combined);
			fireBatch.setProjectionMatrix(camera.combined);
			flameBatch.setProjectionMatrix(camera.combined);
			roofBatch.setProjectionMatrix(camera.combined);
			fontBatch.setProjectionMatrix(camera.combined);
			inventoryBatch.setProjectionMatrix(camera.combined);
			hudBatch.setProjectionMatrix(hud.stage.getCamera().combined);
			hud.stage.draw();
			hudBatch.setProjectionMatrix(hud.subStage.getCamera().combined);
			hud.subStage.draw();
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
		hud.subStage.getViewport().update(width, height, true);
	}

	//update method for physics, camera and held down inputs
	public void update(float delta) {
		world.step(1 / 60f, 6, 2);
		//rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.update();
		rayHandler.setCombinedMatrix(camera);

		if (player.floorCleared){
			hud.fadeHUD(hud.winWords);
		} else if (player.roomCleared) {
			hud.winRoom();
			hud.fadeHUD(hud.roomWords);
		}

		if (player.playerInput) {
			hud.fadeHUD(hud.startWords);
		}

		if (!GenerateLevel.init.roomList.get(player.currentRoom).isShop && !debug) {
			camera.zoom = 0.6f;
		}
		else if (!debug){
			camera.zoom = 1f;
		}

		//player.castRay();

		for (EnemySkull e : enemySkulls) {
			e.enemyAI.update(GdxAI.getTimepiece().getTime());
			e.update(GdxAI.getTimepiece().getTime());
			if (e.playerInRange){
				e.detectPlayer();
			}
		}

		for (EnemySpider e2 : enemySpiders) {
			e2.enemyAI.update(GdxAI.getTimepiece().getTime());
			e2.update(GdxAI.getTimepiece().getTime());
			if (e2.playerInRange){
				e2.detectPlayer();
			}
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
		obstacleBatch.dispose();
		flameBatch.dispose();
		enemySkullBatch.dispose();
		enemySpiderBatch.dispose();
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
		final CreateAssets tx = CreateAssets.getInstance();
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

		if (debug) {
			if (Gdx.input.isKeyPressed(Keys.NUM_8)) {
				enemySkulls.clear();
			}
		}

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
		//Player speed starts at 60
		PLAYER_HORIZONTAL_SPEED = vec.x * PLAYER_SPEED_MULTI;
		PLAYER_VERTICAL_SPEED = vec.y * PLAYER_SPEED_MULTI;

		player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

		if (!player.playerInput) {
			hud.startLevel();
			player.playerInput = true;
		}
	}
}
