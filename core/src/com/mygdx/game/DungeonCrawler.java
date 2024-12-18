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
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Arrow;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Bone;
import com.mygdx.game.entity.behaviours.fsm.drops.Skull;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Web;
import com.mygdx.game.level.objects.Tutorial;
import com.mygdx.game.entity.behaviours.fsm.*;
import com.mygdx.game.level.CreateCell;
import com.mygdx.game.level.objects.*;
import com.mygdx.game.level.GenerateLevel;
import com.mygdx.game.level.InitLevel;

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch playerBatch, arrowBatch, enemySkullBatch, enemySpiderBatch, enemyGhostBatch, potBatch, hudBatch, tutoBatch, fontBatch, inventoryBatch;
	private SpriteBatch skullBatch, boneBatch, lockBatch, doorBatch, potionBatch, obstacleBatch, fireBatch, flameBatch, webBatch, cobBatch, candleBatch;
	private SpriteBatch columnBaseBatch, columnStemBatch, columnTopBatch, pedestalBatch, roofBatch, columnBaseLowerBatch;
	public static World world;
	public Viewport vp;
	public static Stage menuStage;
	public static Table menuContainer;
	public static VerticalGroup menuGroup;
	public ShapeRenderer menuRenderer;
	public static boolean debug, menuClosed, allowPlayerInput;
	public GameInputProcessor gip;
	private Box2DDebugRenderer b2dr;
	public static Player player;
	public static String playerDirection;
	public static boolean playerPaused, playerMeleeAttacking, playerRangedAttacking, playerShieldAttacking;
	public static Body swordBody, arrowBody, shieldBody;
	public static Fixture swordHitbox, arrowHitbox, shieldHitbox;
	public static Arrow arrow;
	public static ArrayList<Arrow> arrows;
	public static ArrayList<Body> arrowBodiesCollided, boneBodiesCollided, skullBodiesDestroyed, deadEnemyBodies, webBodiesCollected;
	public static ArrayMap<Body, Arrow> arrowArrayMap;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Light> lights;
	public ArrayMap<Body, Skull> skullArrayMap;
	public static ArrayMap<Body, Bone> boneArrayMap;
	public static ArrayMap<Body, Web> webArrayMap;
	public static ArrayMap<Body, Potion> potionArrayMap;
	public static ArrayMap<Body, Cobweb> cobArrayMap;
	public static ArrayMap<Body, Fire> respawnFireMap;
	public static ArrayMap<Body, Pot> potArrayMap;
	public boolean reversedArrowMap, reversedSkullMap, reversedPotMap, reversedPotionMap, reversedRespawnFireMap, reversedBoneMap, reversedWebMap;
	public static ArrayList<EnemySkull> enemySkulls, dyingSkulls;
	public static ArrayList<EnemySpider> enemySpiders, dyingSpiders;
	public static ArrayList<EnemyGhost> enemyGhosts, dyingGhosts;
	public static ArrayList<Skull> skulls, brokenSkulls;
	public static ArrayList<Fire> extinguishedRespawnFires;
	public static ArrayList<Bone> bones;
	public static ArrayList<Web> webs;
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
	public static float PLAYER_HORIZONTAL_SPEED = 0f;
	public static float PLAYER_VERTICAL_SPEED = 0f;
	public static float PLAYER_X = 0f;
	public static float PLAYER_Y = 0f;
	public static float PLAYER_SPEED_MULTI;
	private TiledMapRenderer renderer;
	public static OrthographicCamera camera;
	public static final float DEFAULT_VIEWPORT_WIDTH = 300f;
	public static HUD hud;
	public static Music roomClear, swordSlash, level1Music;
	public static SoundController soundController;
	public static RayHandler rayHandler;
	public static LightController lightController;
	private PointLight playerLight;
	private BitmapFont.BitmapFontData bmfData;
	public static BitmapFont defaultFont, defaultFont2;
	public static ArrayList<Text> messages;
	public AssetManager assetManager;
	public static float stateTime, stateTime2, stateTime3;
	public int index = 0;
	public TextureRegion currentFrame;
	public Ray playerSightRay;

	public static boolean leanDown = false, leanUp = false, leanLeft = false, leanRight = false, leanUpLeft = false, leanUpRight = false;
	public static boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false;

	@Override
	public void create() {

		debug = false;

		world = new World(new Vector2(0, 0f), false);
		assetManager = new AssetManager();
		soundController = new SoundController();
		lightController = new LightController();
		menuRenderer = new ShapeRenderer();
		playerBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		tutoBatch = new SpriteBatch();
		enemySkullBatch = new SpriteBatch();
		enemySpiderBatch = new SpriteBatch();
		enemyGhostBatch = new SpriteBatch();
		arrowBatch = new SpriteBatch();
		skullBatch = new SpriteBatch();
		boneBatch = new SpriteBatch();
		webBatch = new SpriteBatch();
		doorBatch = new SpriteBatch();
		lockBatch = new SpriteBatch();
		potBatch = new SpriteBatch();
		potionBatch = new SpriteBatch();
		obstacleBatch = new SpriteBatch();
		candleBatch = new SpriteBatch();
		columnTopBatch = new SpriteBatch();
		columnStemBatch = new SpriteBatch();
		columnBaseBatch = new SpriteBatch();
		columnBaseLowerBatch = new SpriteBatch();
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
		lights = new ArrayList<>();
		enemySkulls = new ArrayList<>();
		enemySpiders = new ArrayList<>();
		enemyGhosts = new ArrayList<>();
		deadEnemyBodies = new ArrayList<>();
		dyingSkulls = new ArrayList<>();
		dyingSpiders = new ArrayList<>();
		dyingGhosts = new ArrayList<>();
		skulls = new ArrayList<>();
		brokenSkulls = new ArrayList<>();
		bones = new ArrayList<>();
		webs = new ArrayList<>();
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
		PLAYER_SPEED_MULTI = 50f;

		//TODO Set player speed here so we can use dynamic speed adjustment e.g entering a cobweb

		//playerSightRay = new Ray<>(vec,);

		//roomClear = Gdx.audio.newMusic(Gdx.files.internal("NinjaAdventure/Sounds/Menu/Accept.wav"));
		//swordSlash = Gdx.audio.newMusic(Gdx.files.internal("Sounds/slash.mp3"));

		final BodyFactory bf = new BodyFactory();
		final CreateAssets tx = CreateAssets.getInstance();
		GameContactListener lc = new GameContactListener();
		tx.textureRegionBuilder();

		defaultFont = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.png"), false);

		defaultFont2 = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.png"), false);


		Color c = new Color();
		c.set(1,1,1,1);

		Text level1StartText =  new Text(defaultFont, "CLAY CATACOMBS", c, true, 10f, 1f, true, false, null, 0);

		//get width and height of the game window
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();

		//create camera and set the viewport
		camera = new OrthographicCamera(1000, 1000);
		camera.setToOrtho(false, w / 3, h / 3);

		vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);
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
		rayHandler.setAmbientLight(0f, 0f, 0f, 0.065f);
		if (debug) {
			rayHandler.setAmbientLight(0f, 0f, 0f, 1f);
		}

		menuStage = new Stage(vp);

		//Menu code
		//TODO Move to separate class
		Skin skin = new Skin();
		skin.add("default-font",defaultFont, BitmapFont.class);
		FileHandle fileHandle = Gdx.files.internal("HellasDungeon/HUD");
		FileHandle atlasFile = fileHandle.sibling("HUD/uiskin.atlas");
		skin.addRegions(new TextureAtlas(atlasFile));
		skin.load(Gdx.files.internal("HellasDungeon/HUD/uiskin.json"));

		// https://libgdx.com/wiki/graphics/2d/scene2d/skin
		//uiskin.atlas, uiskin.json, uiskin.png, default.png and default.fnt all required

		menuContainer = new Table();
		//menuContainer.padLeft(275);
		//menuContainer.padBottom(150);
		menuGroup = new VerticalGroup();
		menuContainer.setFillParent(true);

		TextButton playButton = new TextButton("CONTINUE", skin, "default");
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				menuClosed = true;
				// Called when player clicks on Play button
			}
		});
		playButton.getLabelCell().align(Align.right);
		playButton.padLeft(6.5f);

		TextButton settingsButton = new TextButton("SETTINGS", skin, "default");
		settingsButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				menuClosed = true;
				// Called when player clicks on Play button
			}
		});
		settingsButton.padLeft(6.5f);

		TextButton exitButton = new TextButton("EXIT", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				Gdx.app.exit();
				// Called when player clicks on Exit button
			}
		});
		exitButton.padLeft(6.5f);
		//menuContainer.add(exitButton).expandY();


		menuGroup.addActor(playButton);
		menuGroup.addActor(settingsButton);
		menuGroup.addActor(exitButton);


		menuContainer.add(menuGroup);

		menuStage.addActor(menuContainer);
		menuClosed = true;


		GenerateLevel level = new GenerateLevel();
		InitLevel initLevel = new InitLevel();
		initLevel.InitializeLevel();
		List list = level.generateLevel(0, 0);

		layer = (TiledMapTileLayer) list.get(0);

		PLAYER_X = (float) list.get(1);
		PLAYER_Y = (float) list.get(2);

		player.createPlayer(world, PLAYER_X, PLAYER_Y, rayHandler);

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

		//playerLight.isSoft();
		//playerLight.setXray(true);

		arrowBodiesCollided = new ArrayList<Body>();
		webBodiesCollected = new ArrayList<Body>();
		boneBodiesCollided = new ArrayList<Body>();
		skullBodiesDestroyed = new ArrayList<Body>();
		arrowArrayMap = new ArrayMap<Body, Arrow>();
		skullArrayMap = new ArrayMap<Body, Skull>();
		boneArrayMap = new ArrayMap<Body, Bone>();
		webArrayMap = new ArrayMap<Body, Web>();
		arrows = new ArrayList<Arrow>();
		potionArrayMap = new ArrayMap<Body, Potion>();
		respawnFireMap = new ArrayMap<Body, Fire>();
		cobArrayMap = new ArrayMap<Body, Cobweb>();

		//create an input processor to handle single input events - see inputUpdate() for held down inputs

		gip = new GameInputProcessor();

		//TODO Set only to menu on game start
		Gdx.input.setInputProcessor(menuStage);
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

			ScreenUtils.clear(0.15f, 0, 0.4f, 1);

			//update game physics, camera and held down inputs
			update(Gdx.graphics.getDeltaTime());

			rayHandler.render();

			//clear graphics
			Gdx.gl.glClearColor(0.15f, 0.1f, 0.40f, 1f);
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

			if (menuClosed) {

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
								respawnFire.createFire(new Color(0.3f,0,1f,0.6f), 15);
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
											enemies.add(respawnedEnemy);
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
					if (cob.impassable) {
						world.destroyBody(cob.innerCobBody);
					}
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
							//Pot.renderPot(potBatch, tx.damagedAmphoraSprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
						} else {
							//Pot.renderPot(potBatch, tx.damagedAmphora2Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
						}
					} else {
						switch (value.type) {
							case 1:
								Pot.renderPot(potBatch, tx.pot1Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 2:
								Pot.renderPot(potBatch, tx.pot2Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 3:
								Pot.renderPot(potBatch, tx.pot3Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 4:
								Pot.renderPot(potBatch, tx.pot4Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 5:
								Pot.renderPot(potBatch, tx.pot5Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 6:
								Pot.renderPot(potBatch, tx.pot6Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 7:
								Pot.renderPot(potBatch, tx.pot7Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 8:
								Pot.renderPot(potBatch, tx.pot8Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 9:
								Pot.renderPot(potBatch, tx.pot9Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 10:
								Pot.renderPot(potBatch, tx.pot10Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
							case 11:
								Pot.renderPot(potBatch, tx.pot11Sprite, potEntry.key.getPosition().x, potEntry.key.getPosition().y);
								break;
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
						int max = 15;
						int potionChance = (int) (Math.random() * (max - min + 1)) + min;
						if (potionChance == 15) {
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


		for (Column c : columns) {
			columnBaseLowerBatch.begin();
			if (c.type == 70) {
				columnBaseLowerBatch.draw(tx.colBaseLower,c.columnX,c.columnY);

			}
			columnBaseLowerBatch.end();
		}


			playerBatch.begin();
			//draw playerSprite on player Box2D object
		if (playerShieldAttacking) {
			if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 11f, player.playerBody.getPosition().y + 7f, 16, 8, 16, 8, 1, 1, 0);
			} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 26f, player.playerBody.getPosition().y - 20f, 16, 8, 16, 8, 1, 1, 180);
			} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 30f, player.playerBody.getPosition().y - 2f, 16, 8, 16, 8, 1, 1, 90);
			} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 18f, 16, 8, 16, 8, 1, 1, 270);
			}
		}
		//	playerBatch.draw(tx.playerSprite, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 6f, 16, 16);
			Player.renderPlayer(playerBatch, tx.playerTextureRegion, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 6f);


			if (playerMeleeAttacking) {
				//add the lanceSprite to the corresponding attack playerDirection
				if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 13f, player.playerBody.getPosition().y - 4f, 7, 14, 7, 14, 1, 1, 180);
				} else if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 6f, player.playerBody.getPosition().y - 20f, 7, 14, 7, 14, 1, 1, 0);
				} else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 15f, player.playerBody.getPosition().y - 19f, 7, 14, 7, 14, 1, 1, 270);
				} else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
					playerBatch.draw(tx.swordSprite, player.playerBody.getPosition().x + 1f, player.playerBody.getPosition().y - 12f, 7, 14, 7, 14, 1, 1, 90);
				}
			}

			if (playerRangedAttacking) {
				//add the bowSprite and arrowSprite to the corresponding attack playerDirection
				if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
					playerBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 2f, 8, 10, 18, 8, 1, 1, 180);
				} else if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
					playerBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 10f, player.playerBody.getPosition().y - 14f, 7, 12, 18, 8, 1, 1, 0);
				} else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
					playerBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 11f, player.playerBody.getPosition().y - 9f, 7, 12, 18, 8, 1, 1, 270);
				} else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
					playerBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 3f, player.playerBody.getPosition().y - 13f, 7, 12, 18, 8, 1, 1, 90);
				}
			}
			playerBatch.end();



			//render enemy skull sprites
			for (EnemySkull e : enemySkulls) {
				if (e.rayCastable) {
					e.detectPlayer();
				}
				if ((e.playerSighted && e.playerInRange) && menuClosed){
					//System.out.println(Gdx.graphics.getDeltaTime());
					if (e.timeSinceAlerted > (Gdx.graphics.getDeltaTime() * 140) ){
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
					e.skullLight.setActive(false);
					enemySkullBatch.draw(tx.enemySkullSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e.timeSinceAlerted >= 1) {
					e.skullLight.setActive(true);
					enemySkullBatch.draw(tx.enemySkullAlertedSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				} else {
					e.skullLight.setActive(false);
					enemySkullBatch.draw(tx.enemySkullSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				}
				enemySkullBatch.end();
			}

		//if (!dyingSpiders.isEmpty()) {
			for (EnemySkull deadSkull : dyingSkulls) {
				deadSkull.skullLight.setActive(false);
				deadSkull.getStateMachine().changeState(EnemySkullState.DIE);
				enemies.remove(deadSkull);
			}
			dyingSkulls.clear();
		//}


		//render enemy spider sprites
		for (EnemySpider e2 : enemySpiders) {
			if (e2.rayCastable) {
				e2.detectPlayer();
			}
			if ((e2.playerSighted && e2.playerInRange) && menuClosed){
				//System.out.println(Gdx.graphics.getDeltaTime());
				if (e2.timeSinceAlerted > (Gdx.graphics.getDeltaTime() * 120) ){
						e2.timeSinceAlerted = 0f;
						Vector2 vec1 = new Vector2(e2.enemyBody.getPosition());
						Vector2 vec2 = new Vector2(Player.playerBody.getPosition());

						//throw bones directly at the player but add a small random offset
						float x = MathUtils.atan2(vec2.y - vec1.y, vec2.x - vec1.x);

						e2.exitAngle = MathUtils.atan2(vec2.y - vec1.y, vec2.x - vec1.x);


						float randomOffset = Random.randomFloat(1.3f,0.3f);
						randomOffset = randomOffset / 10;
						boolean random = Random.randomBoolean();
						Vector2 finalX = new Vector2((float)Math.cos(e2.exitAngle),(float)Math.sin(e2.exitAngle));

						if (random) {
							//finalX.x = finalX.x + randomOffset;
							//finalX.y = finalX.y + randomOffset;
						} else {
							//finalX.x = finalX.x - randomOffset;
							//finalX.y = finalX.y - randomOffset;
						}
						//float result = (e.enemyAI.getOrientation() / (x * MathUtils.PI));
						//result = result - MathUtils.PI / 2;
						//System.out.println(x);

						Web web = new Web(world, e2.enemyBody, e2.enemyBody.getPosition().x, e2.enemyBody.getPosition().y, false,  true, finalX);
						web.exitAngle = e2.exitAngle;
						web.createWeb(web.exitAngle);
						webs.add(web);
						webArrayMap.put(web.webBody, web);
						soundController.playSound("SpiderAttack",6f,5f,0.2f);

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
				if (e2.facing == "Up") {
					enemySpiderBatch.draw(tx.enemySpiderUpSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Down") {
					enemySpiderBatch.draw(tx.enemySpiderDownSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Left") {
					enemySpiderBatch.draw(tx.enemySpiderLeftSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Right") {
					enemySpiderBatch.draw(tx.enemySpiderRightSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				}
			enemySpiderBatch.end();
		}
		//if (!dyingSpiders.isEmpty()) {
		for (EnemySpider deadSpider : dyingSpiders) {
			deadSpider.getStateMachine().changeState(EnemySpiderState.DIE);
			enemies.remove(deadSpider);
		}
		dyingSpiders.clear();

		for (EnemyGhost e2 : enemyGhosts) {
			if (e2.rayCastable) {
				e2.detectPlayer();
			}
			if ((e2.playerSighted && e2.playerInRange) && menuClosed){
				//System.out.println(Gdx.graphics.getDeltaTime());
				if (e2.timeSinceAlerted > (Gdx.graphics.getDeltaTime() * 130) ){
					e2.timeSinceAlerted = 0f;
					//e2.enemyHitbox.setSensor(true);
					Vector2 vec1 = new Vector2(e2.enemyBody.getPosition());
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
				e2.getStateMachine().changeState(EnemyGhostState.GO_TO_PLAYER);
				//e.playerSighted = false;
			}
			enemyGhostBatch.begin();
			if (!e2.alerted) {
				e2.enemyHitbox.setSensor(false);
				if (e2.facing == "Up") {
					enemyGhostBatch.draw(tx.enemyGhostUpSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Down") {
					enemyGhostBatch.draw(tx.enemyGhostDownSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Left") {
					enemyGhostBatch.draw(tx.enemyGhostLeftSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Right") {
					enemyGhostBatch.draw(tx.enemyGhostRightSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				}
			} else {
				//e2.enemyHitbox.setSensor(true);
				if (e2.facing == "Up") {
					enemyGhostBatch.draw(tx.enemyGhostAlertUpSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Down") {
					enemyGhostBatch.draw(tx.enemyGhostAlertDownSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Left") {
					enemyGhostBatch.draw(tx.enemyGhostAlertLeftSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Right") {
					enemyGhostBatch.draw(tx.enemyGhostAlertRightSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				}
			}
			enemyGhostBatch.end();
		}

		//if (!dyingSpiders.isEmpty()) {
			for (EnemyGhost deadGhost : dyingGhosts) {
				deadGhost.getStateMachine().changeState(EnemyGhostState.DIE);
				enemies.remove(deadGhost);
			}
			dyingGhosts.clear();
		//}

		for (Body body : deadEnemyBodies) {
			world.destroyBody(body);
		}

		deadEnemyBodies.clear();

		if (!webArrayMap.isEmpty()) {

			if (!reversedWebMap) {
				webArrayMap.reverse();
				reversedWebMap = true;
			}

			for (OrderedMap.Entry<Body, Web> webEntry : webArrayMap.entries()) {
				Body key = webEntry.key;
				Web value = webEntry.value;
				//render each web spit attack sprite
				webBatch.begin();
				Web.renderWeb(webBatch, tx.webSprite, key.getPosition().x, key.getPosition().y,value.exitAngle + 2.35f);
				webBatch.end();
			}

			Iterator<Body> webIt = webBodiesCollected.iterator();
			if (webIt.hasNext()) {
				Body webBody = webIt.next();
				if (webArrayMap.containsKey(webBody)) {

					webArrayMap.removeKey(webBody);
					world.destroyBody(webBody);
					webIt.remove();
					webs.remove(webBody);
				}
			}
		}

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
					if (!c.lowerCreated) {
						c.lowerCreated = true;
					}
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
				f.light.setColor(f.light.getColor().r, f.light.getColor().g, f.light.getColor().b, 0.65f);
				TextureRegion currentFrame = tx.smokeAnimation.getKeyFrame(f.stateTime, false);
				fireBatch.begin();
				//fireBatch.draw(currentFrame, f.fireX, f.fireY);
				Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, false);
				fireBatch.end();
				f.stateTime += Gdx.graphics.getDeltaTime();

				if (tx.smokeAnimation.isAnimationFinished(f.stateTime)) {
						f.active = false;
						f.light.setActive(false);
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
			if (r.upDown) {
				if (r.ruined) {
					roofBatch.draw(tx.ruinedRoofTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y, 64, 96);
				} else {
					roofBatch.draw(tx.roofTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y, 64, 96);
				}
			} else {
				if (r.ruined) {
					roofBatch.draw(tx.ruinedRoofTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y, 0,0,64, 96,1,1,90);
				} else {
					roofBatch.draw(tx.roofTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y, 0,0,64, 96,1,1,90);
				}
			}


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

				for (EnemyGhost enemyGhost : enemyGhosts) {
					//renders ray cast rays
					Ray<Vector2>[] rays = enemyGhost.rayConfigurations[0].getRays();


					enemyGhost.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
					enemyGhost.shapeRenderer.setProjectionMatrix(camera.combined);
					enemyGhost.shapeRenderer.setColor(1, 0, 0, 1);
					// shapeRenderer.setColor(Color.RED);
					//transform.idt();
					//shapeRenderer.setTransformMatrix(transform);
					for (int i = 0; i < rays.length; i++) {
						Ray<Vector2> ray = rays[i];
						enemyGhost.tmp.set(ray.start);
						enemyGhost.tmp2.set(ray.end);
						enemyGhost.shapeRenderer.line(enemyGhost.tmp, enemyGhost.tmp2);
					}

					//render player rayCasts to Enemies
					if (enemyGhost.rayCastable) {
						enemyGhost.tmp3.set((Vector2) enemyGhost.playerDetectionRay.start);
						enemyGhost.tmp4.set((Vector2) enemyGhost.playerDetectionRay.end);
						enemyGhost.shapeRenderer.line(enemyGhost.tmp3, enemyGhost.tmp4);
					}
					enemyGhost.shapeRenderer.end();
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
			columnBaseLowerBatch.setProjectionMatrix(camera.combined);
			pedestalBatch.setProjectionMatrix(camera.combined);
			playerBatch.setProjectionMatrix(camera.combined);
			arrowBatch.setProjectionMatrix(camera.combined);
			skullBatch.setProjectionMatrix(camera.combined);
			tutoBatch.setProjectionMatrix(camera.combined);
			webBatch.setProjectionMatrix(camera.combined);
			boneBatch.setProjectionMatrix(camera.combined);
			enemySkullBatch.setProjectionMatrix(camera.combined);
			enemySpiderBatch.setProjectionMatrix(camera.combined);
			enemyGhostBatch.setProjectionMatrix(camera.combined);
			lockBatch.setProjectionMatrix(camera.combined);
			doorBatch.setProjectionMatrix(camera.combined);
			potBatch.setProjectionMatrix(camera.combined);
			cobBatch.setProjectionMatrix(camera.combined);
			potionBatch.setProjectionMatrix(camera.combined);
			columnBaseBatch.setProjectionMatrix(camera.combined);
			columnStemBatch.setProjectionMatrix(camera.combined);
			columnTopBatch.setProjectionMatrix(camera.combined);

			fireBatch.setProjectionMatrix(camera.combined);
			flameBatch.setProjectionMatrix(camera.combined);
			roofBatch.setProjectionMatrix(camera.combined);
			fontBatch.setProjectionMatrix(camera.combined);
			inventoryBatch.setProjectionMatrix(camera.combined);

			hudBatch.setProjectionMatrix(hud.stage.getCamera().combined);
			hud.stage.draw();
			hudBatch.setProjectionMatrix(hud.subStage.getCamera().combined);
			hud.subStage.draw();
			menuRenderer.setProjectionMatrix(camera.combined);
			menuStage.draw();
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

		//rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.setCombinedMatrix(camera);
		rayHandler.update();


		if (menuClosed) {
			//rayHandler.setAmbientLight(0f, 0f, 0f, 0.010f);
			lightController.fadeLight(fires);
			world.step(1 / 60f, 6, 2);
			allowPlayerInput = true;
			Gdx.input.setInputProcessor(gip);
			menuContainer.setVisible(false);
			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);

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

			for (EnemyGhost e3 : enemyGhosts) {
				e3.enemyAI.update(GdxAI.getTimepiece().getTime());
				e3.update(GdxAI.getTimepiece().getTime());
				if (e3.playerInRange){
					e3.detectPlayer();
				}
			}
		} else {
			//rayHandler.setAmbientLight(0f, 0f, 0f, 0f);

			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			menuRenderer.begin(ShapeRenderer.ShapeType.Filled);
			menuRenderer.setColor(new Color(0, 0, 0, 0.5f));
			menuRenderer.rect(vp.getScreenX(),vp.getScreenY(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			menuRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
			allowPlayerInput = false;
			Gdx.input.setInputProcessor(menuStage);
			menuContainer.setVisible(true);
			if (debug) {
				menuStage.setDebugAll(true);
			} else {
				menuStage.setDebugAll(false);
			}
		}

		if (player.floorCleared){
			hud.fadeHUD(hud.winWords);
		} else if (player.roomCleared) {
			hud.winRoom();
			hud.fadeHUD(hud.roomWords);
		}

		if (player.playerInput) {
			hud.fadeHUD(hud.startWords);
		}

		if (player.hasTorch && !player.torchApplied) {
			playerLight.remove();
			playerLight = new PointLight(rayHandler, 1000, new Color(0.25f, 0.20f, 0, 0.85f), 95, PLAYER_X, PLAYER_Y);
			playerLight.attachToBody(player.playerBody);
			playerLight.setIgnoreAttachedBody(true);
			playerLight.setSoftnessLength(100f);
			player.torchApplied = true;
		}

		if (!GenerateLevel.init.roomList.get(player.currentRoom).isShop && !debug) {
			camera.zoom = 0.6f;
		}
		else if (!debug){
			camera.zoom = 0.8f;
		} else {

		}

		//player.castRay();

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
		//menuRenderer.dispose();
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

		//revert to standing sprite when input is released
		if (player.facing == 1) {
			tx.playerTextureRegion = tx.playerUp;
		} else if (player.facing == 3) {
			tx.playerTextureRegion = tx.playerDown;
		} else if (player.facing == 4) {
			tx.playerTextureRegion = tx.playerLeft;
		} else if (player.facing == 2) {
			tx.playerTextureRegion = tx.playerRight;
		}

		moveUp = false;
		moveDown = false;
		moveLeft = false;
		moveRight = false;


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
		if (allowPlayerInput) {
			if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
				PLAYER_VERTICAL_SPEED = 1f;
				leanUp = true;
				moveUp = true;
				player.facing = 1;
				if (leanLeft){
					PLAYER_HORIZONTAL_SPEED = -1f;
					currentFrame = tx.playerWalkUpLeftAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
					//tx.playerSprite = tx.playerUpLeftLean;
				} else if (leanRight) {
					PLAYER_HORIZONTAL_SPEED = 1f;
					currentFrame = tx.playerWalkUpRightAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
					//tx.playerSprite = tx.playerUpRightLean;

				}
				else {
					currentFrame = tx.playerWalkUpAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
				}
			}

			if (Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)) {
				PLAYER_HORIZONTAL_SPEED = -1f;
				leanLeft = true;
				moveLeft = true;
				player.facing = 4;
				if (leanDown) {
					PLAYER_VERTICAL_SPEED = -1f;
					currentFrame = tx.playerWalkDownLeftAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
					//tx.playerTextureRegion = tx.playerDownLeftLean;
				} else if (leanUp) {
					PLAYER_VERTICAL_SPEED = 1f;
					//tx.playerTextureRegion = tx.playerUpLeftLean;
					currentFrame = tx.playerWalkUpLeftAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
				} else {
					currentFrame = tx.playerWalkLeftAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
				}
			}

			if (Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN)) {
				PLAYER_VERTICAL_SPEED = -1f;
				leanDown = true;
				moveDown = true;
				player.facing = 3;
				if (leanLeft) {
					PLAYER_HORIZONTAL_SPEED = -1f;
					currentFrame = tx.playerWalkDownLeftAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
					//tx.playerTextureRegion = tx.playerDownLeftLean;
				} else if (leanRight) {
					PLAYER_HORIZONTAL_SPEED = 1f;
					currentFrame = tx.playerWalkDownRightAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
					//tx.playerTextureRegion = tx.playerDownRightLean;
				} else {
					currentFrame = tx.playerWalkDownAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
				}
			}
			if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
				PLAYER_HORIZONTAL_SPEED = 1f;
				leanRight = true;
				moveRight = true;
				player.facing = 2;
				if (leanDown) {
					//tx.playerTextureRegion = tx.playerDownRightLean;
					currentFrame = tx.playerWalkDownRightAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
				} else if (leanUp) {
					//tx.playerTextureRegion = tx.playerUpRightLean;
					currentFrame = tx.playerWalkUpRightAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
				} else {
					currentFrame = tx.playerWalkRightAnimation.getKeyFrame(stateTime3, true);
					tx.playerTextureRegion = currentFrame;
				}
			}
			stateTime3 += Gdx.graphics.getDeltaTime();
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
