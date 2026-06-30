package com.mygdx.game;
import java.util.*;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
import com.mygdx.game.entity.behaviours.fsm.projectiles.Eyebeam;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Web;
import com.mygdx.game.entity.behaviours.fsm.*;
import com.mygdx.game.level.*;
import com.mygdx.game.level.objects.*;
import com.mygdx.game.level.objects.Trap;


import static com.mygdx.game.HUD.compassArrowImage;
import static com.mygdx.game.OptionsMenu.optionsMenuContainer;
import static com.mygdx.game.PauseMenu.pauseMenuContainer;

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch arrowBatch, enemySkullBatch, enemySpiderBatch, enemyGhostBatch, enemyEyeBatch, enemyCrabBatch, potBatch, hudBatch, tutoBatch, alertFontBatch, inventoryBatch, sightFontBatch;
	private SpriteBatch skullBatch, boneBatch, lockBatch, doorBatch, potionBatch, coinBatch, obstacleBatch, fireBatch, flameBatch, webBatch, cobBatch, candleBatch, lampBatch, brazierBatch, eyebeamBatch;
	private SpriteBatch bossMinotaurBatch, statueBatch, flagBatch, vineBatch, waveBatch, waterBatch, waterfallBatch, raisedFloorBatch, rubbleBatch, drainBatch, rippleBatch;
	private SpriteBatch columnBaseBatch, columnStemBatch, columnTopBatch, pedestalBatch, roofBatch, columnBaseLowerBatch, heartBatch, pedestalUpperBatch, firePitBatch, vaseBatch;
	public static SpriteBatch trapBatch, playerBatch, weaponBatch, torchBatch;
	public SpriteBatch potParticlesBatch, boneParticlesBatch;
	public static ParticleEffect potParticleEffect, boneParticleEffect;
	public static World world;
	public static Viewport vp;
	public static Skin skin;
	public static PauseMenu pauseMenu;
	public static OptionsMenu optionsMenu;
	public static Stage pauseMenuStage, optionsMenuStage, bossHealthbarStage;
    public static int difficulty;
    public static LevelModifier curse;
	public ShapeRenderer menuRenderer, cameraShapeRenderer;
	public static ShapeRenderer maskRenderer;
    public float cameraShapeAlpha;
    public static boolean roomTransition, cameraFading, loweredCameraAlpha;
	public static boolean debug, pauseMenuClosed, optionsMenuClosed, allowPlayerInput, modifiersAllowed, shadersEnabled;
	public FPSLogger fpsLogger;
	public GameInputProcessor gip;
	private Box2DDebugRenderer b2dr;
	public static Player player;
	public static String playerDirection, arrowDirection;
	public static boolean playerPaused, playerMeleeAttacking, playerRangedAttacking, playerShieldAttacking, playerUsingChisel;
	public static Body swordBody, arrowBody, shieldBody, chiselBody;
	public static Fixture swordHitbox, arrowHitbox, shieldHitbox, chiselHitbox;
	public static Arrow arrow;
	public static ArrayList<Arrow> arrows;
	public static ArrayList<Body> arrowBodiesCollided, boneBodiesCollided, eyebeamBodiesCollected, skullBodiesDestroyed, deadEnemyBodies, webBodiesCollided, obstacleBodiesCollected;
	public static ArrayMap<Body, Arrow> arrowArrayMap;
	public static ArrayMap<Trap, Integer> arrowsToBeFired;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Light> lights;
	public static ArrayList<Trap> traps;
	public ArrayMap<Body, Skull> skullArrayMap;
	public static ArrayMap<Body, Bone> boneArrayMap;
	public static ArrayMap<Body, Eyebeam> eyebeamArrayMap;
	public static ArrayMap<Body, Web> webArrayMap;
	public static ArrayMap<Body, Potion> potionArrayMap;
	public static ArrayMap<Body, Coin> coinArrayMap;
	public static ArrayMap<Body, Heart> heartArrayMap;
	public static ArrayMap<Body, Cobweb> cobArrayMap;
	public static ArrayMap<Body, Fire> respawnFireMap;
	public static ArrayMap<Body, Pot> potArrayMap;
	public static ArrayMap<Body, Obstacle> obArrayMap;
	public static boolean reversedArrowMap, reversedSkullMap, reversedPotMap, reversedRespawnFireMap, reversedBoneMap, reversedWebMap, reversedObMap, reversedEyebeamMap;
	public boolean reversedCoinMap, reversedPotionMap, reversedHeartMap;
	public static ArrayList<EnemySkull> enemySkulls, dyingSkulls;
    public static int boneThrowTime;
	public static ArrayList<EnemySpider> enemySpiders, dyingSpiders;
	public static ArrayList<EnemyGhost> enemyGhosts, dyingGhosts;
	public static ArrayList<EnemyCyclops> enemyEyes, dyingEyes;
    public static ArrayList<EnemyCrab> enemyCrabs, dyingCrabs;
	public static ArrayList<BossMinotaur> bossMinotaurs, dyingMinotaurs;
	public static ArrayList<Skull> skulls, brokenSkulls;
	public static ArrayList<Fire> extinguishedRespawnFires;
	public static ArrayList<Bone> bones;
	public static ArrayList<Eyebeam> eyebeams;
	public static ArrayList<Web> webs;
	public static ArrayList<Shopkeeper> shopkeepers;
	public static ArrayList<Lock> locks;
	public static ArrayList<Tutorial> tutorial;
	public static ArrayList<Rubble> rubble;
	public static ArrayList<Pot> pots, brokenPots;
	public static ArrayList<Cobweb> cobwebs, burnedCobwebs;
	public static ArrayList<Potion> potions, collectedPotions;
	public static ArrayList<Coin> coins, collectedCoins;
	public static ArrayList<Heart> hearts, collectedHearts;
	public static ArrayList<Torch> torches;
	public static ArrayList<Obstacle> obstacles;
	public static ArrayList<Candle> candles;
    public static ArrayList<OilLamp> lamps;
    public static ArrayList<Brazier> braziers;
    public static ArrayList<Vase> vases;
    public static ArrayList<FirePit> firepits;
    public static ArrayList<Drain> drains;
	public static ArrayList<Fire> fires;
    public static ArrayList<Waterfall> waterfalls;
	public static ArrayList<ColumnPiece> columnPieces;
	public static ArrayList<Column> columns;
	public static ArrayList<RaisedFloor> raisedFloors;
	public static ArrayList<Flag> flags;
    public static ArrayList<Vine> vines;
	public static ArrayList<Wave> waves;
	public static ArrayList<Water> ocean;
	public static ArrayList<Water> water;
	public static ArrayList<Statue> statues;
	public static ArrayList<Roof> roofs;
	public static float PLAYER_HORIZONTAL_SPEED = 0f;
	public static float PLAYER_VERTICAL_SPEED = 0f;
	public static float PLAYER_X = 0f;
	public static float PLAYER_Y = 0f;
	public static float PLAYER_SPEED_MULTI;
	public static float PLAYER_DEFAULT_SPEED;
	private TiledMapRenderer renderer;
	public static OrthographicCamera camera, camera2;
	public static final float DEFAULT_VIEWPORT_WIDTH = 300f;
    public static float TIME_SCALE;
    public static float ARROW_SPEED;
	public static HUD hud;
	public static SoundController soundController;
	public static RayHandler rayHandler;
	public static LightController lightController;
	public static BitmapFont defaultFont, defaultFont2, defaultFont3, defaultFont4;
	public static ArrayList<DisplayText> susMessages, alertMessages;
	public AssetManager assetManager;
	public static float stateTime, stateTime2, stateTime3, stateTime4, stateTime5, potTime;
	public TextureRegion currentFrame;
	public ShaderProgram flagShader, waveShader, vineShader, fireShader, waterfallShader;
	public float flag_time, ocean_time, ocean2_time, vine_time, fire_time, waterfall_time;


	public static boolean leanDown = false, leanUp = false, leanLeft = false, leanRight = false, leanUpLeft = false, leanUpRight = false;
	public static boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false;

	public boolean potReset;
    public boolean potTimeUpdate;
    public static boolean trapsActive;

	@Override
	public void create() {

		//the game is not in debug mode by default
		debug = false;
		fpsLogger = new FPSLogger();

		//instantiate the game world, all sprite batches, world objects,
		//collectible items and light sources
		world = new World(new Vector2(0, 0f), false);
		assetManager = new AssetManager();
		soundController = new SoundController();
		lightController = new LightController();
        TIME_SCALE = 1.0f;
        ARROW_SPEED = 60.0f;

		maskRenderer = new ShapeRenderer();
		maskRenderer.setAutoShapeType(true);

		menuRenderer = new ShapeRenderer();
        cameraShapeRenderer = new ShapeRenderer();
        cameraShapeAlpha = 0;
		playerBatch = new SpriteBatch();
		weaponBatch = new SpriteBatch();
        torchBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		tutoBatch = new SpriteBatch();
		enemySkullBatch = new SpriteBatch();
		enemySpiderBatch = new SpriteBatch();
		enemyGhostBatch = new SpriteBatch();
		enemyEyeBatch = new SpriteBatch();
        enemyCrabBatch = new SpriteBatch();
		arrowBatch = new SpriteBatch();
		skullBatch = new SpriteBatch();
		boneBatch = new SpriteBatch();
		bossMinotaurBatch = new SpriteBatch();
		statueBatch = new SpriteBatch();
		raisedFloorBatch = new SpriteBatch();
		rubbleBatch = new SpriteBatch();
        drainBatch = new SpriteBatch();
        rippleBatch = new SpriteBatch();
        waterfallBatch = new SpriteBatch();
		flagBatch = new SpriteBatch();
        vineBatch = new SpriteBatch();
		waterBatch = new SpriteBatch();
		waveBatch = new SpriteBatch();
        fireBatch = new SpriteBatch();
        flameBatch = new SpriteBatch();
		//flagBatch.enableBlending();

        potReset = false;
        potTimeUpdate = false;

		flag_time = 0;
        fire_time = 0;
		ocean_time = 0;
		ocean2_time = 0;
        waterfall_time = 0;

		flagShader = new ShaderProgram(flagBatch.getShader().getVertexShaderSource(), Gdx.files.internal("HellasDungeon/level/Shaders/flag.frag").readString());

        vineShader = new ShaderProgram(vineBatch.getShader().getVertexShaderSource(), Gdx.files.internal("HellasDungeon/level/Shaders/vine.frag").readString());

		waveShader = new ShaderProgram(flagBatch.getShader().getVertexShaderSource(), Gdx.files.internal("HellasDungeon/level/Shaders/waveright.frag").readString());

        fireShader = new ShaderProgram(fireBatch.getShader().getVertexShaderSource(), Gdx.files.internal("HellasDungeon/level/Shaders/fire.frag").readString());

        waterfallShader = new ShaderProgram(waterfallBatch.getShader().getVertexShaderSource(), Gdx.files.internal("HellasDungeon/level/Shaders/waterfall.frag").readString());

		//flagShader.setUniform("v_texCoords", );
		flagShader.setUniformf("u_fixedBasePosY", 0.0f);
        waterfallShader.setUniformf("u_fixedBasePosY", 0.0f);
        //fireShader.setUniformf("u_fixedBasePosY", 0.0f);

		//flagShader.setUniformf("u_texture", );
		//flagShader.setUniformf("u_offsetX", 1.0f);
		//flagShader.setUniformf("uv", 0f);

        fireShader.pedantic = false;
		flagShader.pedantic = false;
		waveShader.pedantic = false;
        vineShader.pedantic = false;
        waterfallShader.pedantic = false;

		rubble = new ArrayList<>();
		ocean = new ArrayList<>();
		waves = new ArrayList<>();
		water = new ArrayList<>();
		eyebeamBatch = new SpriteBatch();
		webBatch = new SpriteBatch();
		doorBatch = new SpriteBatch();
		lockBatch = new SpriteBatch();
		potBatch = new SpriteBatch();
		trapBatch = new SpriteBatch();
		potionBatch = new SpriteBatch();
		coinBatch = new SpriteBatch();
		heartBatch = new SpriteBatch();
		obstacleBatch = new SpriteBatch();
		candleBatch = new SpriteBatch();
        lampBatch = new SpriteBatch();
        brazierBatch = new SpriteBatch();
        firePitBatch = new SpriteBatch();
        vaseBatch = new SpriteBatch();
		columnTopBatch = new SpriteBatch();
		columnStemBatch = new SpriteBatch();
		columnBaseBatch = new SpriteBatch();
		columnBaseLowerBatch = new SpriteBatch();
		pedestalBatch = new SpriteBatch();
		alertFontBatch = new SpriteBatch();
		sightFontBatch = new SpriteBatch();
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
		traps = new ArrayList<>();
		enemySkulls = new ArrayList<>();
		enemySpiders = new ArrayList<>();
		enemyGhosts = new ArrayList<>();
		enemyEyes = new ArrayList<>();
        enemyCrabs = new ArrayList<>();
		bossMinotaurs = new ArrayList<>();
		deadEnemyBodies = new ArrayList<>();
		dyingSkulls = new ArrayList<>();
		dyingSpiders = new ArrayList<>();
		dyingGhosts = new ArrayList<>();
		dyingEyes = new ArrayList<>();
        dyingCrabs = new ArrayList<>();
		dyingMinotaurs = new ArrayList<>();
		skulls = new ArrayList<>();
		brokenSkulls = new ArrayList<>();
		bones = new ArrayList<>();
		eyebeams = new ArrayList<>();
		webs = new ArrayList<>();
		cobwebs = new ArrayList<>();
		burnedCobwebs = new ArrayList<>();
		shopkeepers = new ArrayList<>();
		tutorial = new ArrayList<>();
		locks = new ArrayList<>();
		pots = new ArrayList<>();
		potParticlesBatch = new SpriteBatch();
		potParticleEffect = new ParticleEffect();
		boneParticlesBatch = new SpriteBatch();
		boneParticleEffect = new ParticleEffect();
		brokenPots = new ArrayList<>();
		potArrayMap = new ArrayMap<>();
		torches = new ArrayList<>();
		potions = new ArrayList<>();
		coins = new ArrayList<>();
		hearts = new ArrayList<>();
		columns = new ArrayList<>();
		columnPieces = new ArrayList<>();
		statues = new ArrayList<>();
		raisedFloors = new ArrayList<>();
		fires = new ArrayList<>();
		flags = new ArrayList<>();
        vines = new ArrayList<>();
		extinguishedRespawnFires = new ArrayList<>();
		collectedPotions = new ArrayList<Potion>();
		collectedCoins = new ArrayList<Coin>();
		collectedHearts = new ArrayList<Heart>();
		obstacles = new ArrayList<Obstacle>();
		candles = new ArrayList<Candle>();
        lamps = new ArrayList<OilLamp>();
        vases = new ArrayList<Vase>();
        braziers = new ArrayList<Brazier>();
        firepits = new ArrayList<FirePit>();
        drains = new ArrayList<Drain>();
        waterfalls = new ArrayList<>();
		susMessages = new ArrayList<DisplayText>();
		alertMessages = new ArrayList<DisplayText>();


		//initialize all removable level objects
		arrowBodiesCollided = new ArrayList<Body>();
		webBodiesCollided = new ArrayList<Body>();
		obstacleBodiesCollected = new ArrayList<Body>();
		boneBodiesCollided = new ArrayList<Body>();
		eyebeamBodiesCollected = new ArrayList<Body>();
		skullBodiesDestroyed = new ArrayList<Body>();
		arrowArrayMap = new ArrayMap<Body, Arrow>();
		arrowsToBeFired = new ArrayMap<Trap, Integer>();
		skullArrayMap = new ArrayMap<Body, Skull>();
		boneArrayMap = new ArrayMap<Body, Bone>();
		eyebeamArrayMap = new ArrayMap<Body, Eyebeam>();
		webArrayMap = new ArrayMap<Body, Web>();
		arrows = new ArrayList<Arrow>();
		potionArrayMap = new ArrayMap<Body, Potion>();
		coinArrayMap = new ArrayMap<Body, Coin>();
		heartArrayMap = new ArrayMap<Body, Heart>();
		obArrayMap = new ArrayMap<Body, Obstacle>();
		respawnFireMap = new ArrayMap<Body, Fire>();
		cobArrayMap = new ArrayMap<Body, Cobweb>();

		//set player speed multiplier
		Vector2 vec = new Vector2();
		vec.x = PLAYER_X;
		vec.y = PLAYER_Y;
		PLAYER_DEFAULT_SPEED = 25f;//38 //40 //45
		PLAYER_SPEED_MULTI = PLAYER_DEFAULT_SPEED;

		//initialize the Box2D body factory, asset instance
		//and collision listener
		final BodyFactory bf = new BodyFactory();
		final CreateAssets tx = CreateAssets.getInstance();
		GameContactListener lc = new GameContactListener();
		tx.textureRegionBuilder();

		//TODO: Merge Upper and Lower cases and use proper Greek letters instead of lower case  as placeholder for Greek alphabet glyphs

		//instantiate the font used in the game
		defaultFont = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontFull-export.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontFull-export.png"), false);

		//used for drawing the inventory text in shops
		defaultFont2 = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.png"), false);

		defaultFont3 = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.png"), false);

		defaultFont4 = new BitmapFont(Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.fnt"),
				Gdx.files.internal("HellasDungeon/Font/HellasFontStylizedFinal.png"), false);

		//get width and height of the game window
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();

		//create camera and set the viewport
		camera = new OrthographicCamera(100, 100);
		camera.setToOrtho(false, w / 3, h / 3);

       // camera2 = new OrthographicCamera(1000, 1000);
       // camera2.setToOrtho(false, w / 2, h / 2);

		vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);

		//create a menu stage which uses the same viewport as the camera
		pauseMenuStage = new Stage(DungeonCrawler.vp);
		optionsMenuStage = new Stage(DungeonCrawler.vp);

        bossHealthbarStage = new Stage(DungeonCrawler.vp);

		//initialize map
		TiledMap map = new TiledMap();
		MapLayers layers = map.getLayers();

		//set map layer dimensions
		//set to 1000 tile layers wide and high but can be changed if required
		TiledMapTileLayer layer = new TiledMapTileLayer(1000, 1000, 16, 16);

		//create the renderer and debug renderer
		renderer = new OrthogonalTiledMapRenderer(map);
		b2dr = new Box2DDebugRenderer();



		//create the Box2D ray handler
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0f, 0f, 0f, 0.1f);//0.013
		//0.013
		//fullbright if in debug mode
		if (debug) {
			rayHandler.setAmbientLight(0f, 0f, 0f, 1f);
		}

		//menu code
		skin = new Skin();
		skin.add("default-font",defaultFont2, BitmapFont.class);
		FileHandle fileHandle = Gdx.files.internal("HellasDungeon/HUD");
		FileHandle atlasFile = fileHandle.sibling("HUD/uiskin.atlas");
		skin.addRegions(new TextureAtlas(atlasFile));
		skin.load(Gdx.files.internal("HellasDungeon/HUD/uiskin.json"));

		pauseMenuClosed = true;
		optionsMenuClosed = true;

        difficulty = 2;

        shadersEnabled = true;

		//for whatever reason, the first sprite in a batch with a shader can't become transparent, so we create one instance of any that change alpha at (0,0) far out of sight
		Flag flag = new Flag(world, 0,0);
		flag.createFlagHitbox(0,0, world);
		flags.add(flag);

        Vine vine = new Vine(world, 0,0);
        vine.createVineHitbox(0,0, world);
        vines.add(vine);

        Waterfall waterfall = new Waterfall(world, 0,0);
        waterfall.createWaterfall();
        waterfalls.add(waterfall);

        Fire fire = new Fire(world,rayHandler,0,0,false,0,1,true,1);
        fire.createFire(Color.PURPLE,0,null);
        fires.add(fire);

		//generate the level
		GenerateLevel level = new GenerateLevel();
		InitLevel initLevel = new InitLevel();
		initLevel.InitializeLevel();
		List list = level.generateLevel(0, 0);
		layer = (TiledMapTileLayer) list.get(0);

        //generate the ocean
        int startX = GenerateLevel.init.roomList.get(0).x1;
        startX = startX - 10;
        int startY = GenerateLevel.init.roomList.get(0).y2 - 10;

        int finalX = GenerateLevel.init.roomList.get(GenerateLevel.init.roomList.size()-1).x1 + 10;
        int finalY = GenerateLevel.init.roomList.get(GenerateLevel.init.roomList.size()-1).y1 + 10;

        int distanceX = finalX - startX;
        int distanceY = finalY - startY;

        int finalRoomWidth = GenerateLevel.init.roomList.get(GenerateLevel.init.roomList.size()-1).longestRow;

        for (int travelY = 0; travelY < distanceY; travelY++) {
            for (int travelX = 0; travelX < distanceX + finalRoomWidth + 4; travelX++) {
                if (travelY % 2 == 0) {
                    Wave wave = new Wave(world, ((startX + travelX) * 16) + 16 * 16 + 32,startY * 16 + Gdx.graphics.getHeight() / 30 - 16 - 16 - 16 + 3 + (16 * travelY), 0, true);
                    waves.add(wave);

                } else {
                    Wave wave = new Wave(world, ((startX + travelX) * 16) + 16 * 16 + 32,startY * 16 + Gdx.graphics.getHeight() / 30 - 16 - 16 - 16 + 3 + (16 * travelY), 0, false);
                    waves.add(wave);
                }
            }
        }

        //initialize HUD and hide the compass
        hud = new HUD(vp, hudBatch);
        Compass.hideCompass();

		//place player
		PLAYER_X = (float) list.get(1);
		PLAYER_Y = (float) list.get(2);
		player.createPlayer(world, PLAYER_X, PLAYER_Y, rayHandler);
        modifiersAllowed = false;
        if (modifiersAllowed) {
            int curseChance = Random.randomInt(5,5);

            if (curseChance == 5) {
                curse = new LevelModifier();
                curse.enabled = true;

                curse.type = Random.randomInt(1,1);
                switch (curse.type) {
                    case 1:
                        DisplayText cronosMessage = new DisplayText(defaultFont2,"CURSE OF CRONOS",Color.RED,true,3.0f,1.0f,true,false,null,0.0f);
                        curse.message = cronosMessage;
                        TIME_SCALE = 1.50f;
                        break;
                }
            }
        }

		CreateCell cr = new CreateCell();
		cr.InitializeCells();

		//add current layers to the TileMap and assign it a renderer
		layers.add(layer);

		//create an input processor to handle single input events - see inputUpdate() for held down inputs
		gip = new GameInputProcessor();

		pauseMenu = new PauseMenu();
		optionsMenu = new OptionsMenu();

		optionsMenuContainer.setVisible(false);
		optionsMenu.slider.setVisible(false);

		//allow button presses on the menu

		//enable the collision listener to listen to world collision events
		world.setContactListener(lc);

		potParticleEffect.load(Gdx.files.internal("HellasDungeon/Particles/Pot/Pot.p"),Gdx.files.internal("HellasDungeon/Particles/Pot/"));

		boneParticleEffect.load(Gdx.files.internal("HellasDungeon/Particles/Bone/Bone.p"),Gdx.files.internal("HellasDungeon/Particles/Bone/"));


		if (!debug) {
			//set the window mode to fullscreen and hide the cursor when in the game window
			//Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			//Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth()*2,Gdx.graphics.getHeight()*2);
			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
		}

		//phone screen style resolution
		Gdx.graphics.setWindowedMode(600,900);
	}

	@Override
	public void render() {

		//



			fpsLogger.log();

			Rectangle cameraBounds = new Rectangle(
				camera.position.x - camera.viewportWidth * camera.zoom / 2 - 32,
				camera.position.y - camera.viewportHeight * camera.zoom / 2 - 32,
				camera.viewportWidth * camera.zoom + 32,
				camera.viewportHeight * camera.zoom + 32
			);

			if (!optionsMenuClosed) {
				optionsMenu.volume = optionsMenu.slider.getValue();
				System.out.println(optionsMenu.volume);
			}

			// exit game when player health is 0
			//TODO Add death screen
			if (hud.healthBar.currentHealth == 0) {
				System.out.println("YOU DIED IN ROOM " + player.currentRoom);
				Gdx.app.exit();
			}

			// win the game if all enemies are dead
			if (enemies.isEmpty()) {
				hud.winLevel();
			}

			final CreateAssets tx = CreateAssets.getInstance();
			//clear all assets and replace with background color

			ScreenUtils.clear(0.15f, 0, 0.4f, 1);

            ARROW_SPEED = 60.0f * TIME_SCALE;

			//update game physics, camera and held down inputs
			update((Gdx.graphics.getDeltaTime() * TIME_SCALE));

			//render lighting
			rayHandler.render();

			//clear graphics
			Gdx.gl.glClearColor(0.15f, 0.1f, 0.40f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Rectangle objectBounds = new Rectangle();

		for (Water w : ocean) {

			//GenerateLevel.init.roomList.get(r.index).roomHitbox.getBody().getPosition().x;

			//shader variables passed into flag fragment shader
			/*
			waveBatch.setShader(waveShader);
			waveShader.setUniformf("u_swayIntensity", 0.05f);//0.03
			waveShader.setUniformf("u_verticalDensity", 1f);
			waveShader.setUniformf("u_time", flag_time + w.time);
			waveShader.setUniformf("u_speed", 1.6f);
			 */
			objectBounds.set(
				w.waterX,
				w.waterY,
				16,
				16
			);

			if (cameraBounds.overlaps(objectBounds)) {
				waterBatch.begin();
				Water.renderWater(waterBatch, tx.oceanWater, w.waterX, w.waterY, 16, 16, 2);
				waterBatch.end();
			}
		}

		ocean_time += (Gdx.graphics.getDeltaTime() * TIME_SCALE);
		ocean2_time += (Gdx.graphics.getDeltaTime() * TIME_SCALE);
		for (Wave w : waves) {
            if (w.dark) {
                currentFrame = tx.waveDarkAnimation.getKeyFrame(w.stateTime, true);
            } else {
                currentFrame = tx.waveAnimation.getKeyFrame(w.stateTime, true);
            }

			w.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

			objectBounds.set(
					w.waveX,
					w.waveY,
					16,
					16
			);

			//GenerateLevel.init.roomList.get(r.index).roomHitbox.getBody().getPosition().x;

			//shader variables passed into flag fragment shader
			/*
			waveBatch.setShader(waveShader);
			waveShader.setUniformf("u_swayIntensity", 0.05f);//0.03
			waveShader.setUniformf("u_verticalDensity", 1f);
			waveShader.setUniformf("u_time", flag_time + w.time);
			waveShader.setUniformf("u_speed", 1.6f);

										Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, true);
			 */
			if (cameraBounds.overlaps(objectBounds)) {
				waveBatch.begin();
				Wave.renderWave(waveBatch, currentFrame, w.waveX, w.waveY, 16, 16);
				waveBatch.end();
			}
		}


			//set the view of the map to the camera and then render the map

            //determines how much of the tilemap is visible in an area around the player/camera
            if (!debug) {
                renderer.setView(camera.combined,player.playerBody.getPosition().x - 150,player.playerBody.getPosition().y - 200,375,375);
            } else {
                renderer.setView(camera);
            }
            
			renderer.render();

			//set camera position to always be centred on the player body
			camera.position.set(player.playerBody.getPosition().x + tx.playerSprite.getWidth() / 2 - 8, player.playerBody.getPosition().y + tx.playerSprite.getHeight() / 2 - 8, 0);

			rubbleBatch.begin();
				for (Rubble r : rubble) {
					//Rubble.renderRubble(rubbleBatch, tx.pitPot,r.rubBody.getPosition().x - 8, r.rubBody.getPosition().y, 1);
					if (r.type == 1) {
						Rubble.renderRubble(rubbleBatch, tx.pitPot,r.rubBody.getPosition().x - 12, r.rubBody.getPosition().y + 6, 1);
					} else if (r.type == 2) {
						Rubble.renderRubble(rubbleBatch, tx.pitColumn,r.rubBody.getPosition().x - 12, r.rubBody.getPosition().y + 6,2);
					} else if (r.type == 3) {
						Rubble.renderRubble(rubbleBatch, tx.pitSkull,r.rubBody.getPosition().x - 11, r.rubBody.getPosition().y + 6,3);
					}
				}
			rubbleBatch.end();

		//tutorial texture in the starting room
			for (Tutorial t : tutorial) {
				tutoBatch.begin();
				tutoBatch.draw(tx.tutorialTexture, t.tutorialBody.getPosition().x - 16f, t.tutorialBody.getPosition().y + 7f, 96, 64);
				tutoBatch.end();
			}

            for (Torch tor : torches) {
                objectBounds.set(
                        tor.torchX,
                        tor.torchY,
                        16,
                        16
                );
                torchBatch.begin();
                    Torch.renderTorch(torchBatch, tor.direction, tor.torchX, tor.torchY);
                torchBatch.end();

            }

			//wall arrow traps
			for (Trap tr : traps) {
				objectBounds.set(
						tr.trapX,
						tr.trapY,
						16,
						16
				);
				if (cameraBounds.overlaps(objectBounds)) {
					trapBatch.begin();
					if (!tr.active) {
						Trap.renderTrap(trapBatch, tr.direction, tr.trapBody.getPosition().x, tr.trapBody.getPosition().y, tr.type);
					} else {
						Trap.renderTrapActive(trapBatch, tr.direction, tr.trapBody.getPosition().x, tr.trapBody.getPosition().y, tr.type);
					}
					trapBatch.end();
				}
			}

        for (Drain d : drains) {
            drainBatch.begin();
            drainBatch.draw(tx.drain, d.drainBody.getPosition().x - 10.5f, d.drainBody.getPosition().y + 11, 24, 28);
            drainBatch.end();
        }

			//render doors
			for (Room r : GenerateLevel.init.roomList) {
				for (Door d : r.doors) {
					if (d.open) {
						doorBatch.begin();
							d.renderOpen(doorBatch, r.directionTaken, d.doorX, d.doorY, d.type);
						doorBatch.end();
					} else if (!d.open) {
                        doorBatch.begin();
                        d.renderClosed(doorBatch, r.directionTaken, d.doorX, d.doorY, d.type);
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
			for (Skull s : skulls) {
				if (!s.skullCreated) {
					skullArrayMap.put(s.createSkull(skullArrayMap), s);
					s.room = player.currentRoom;
				}
			}


			//render skull level objects that drop from enemySkulls - unfinished
			for (Skull s : skulls) {
				//TODO Fix - skulls pick the furthest spawner
				if (!GenerateLevel.init.roomList.get(s.room).spawners.isEmpty()) {
					for (Fire f : GenerateLevel.init.roomList.get(player.currentRoom).spawners) {
						if (s.resurrectable) {
						if (s.skullCreated) {
							boolean rayResult = s.rayCastSkull(GenerateLevel.init.roomList.get(player.currentRoom), f);
                            System.out.println(rayResult);
							if (rayResult && !s.resurrecting && f.active) {
								Fire respawnFire = new Fire(world, rayHandler, s.skullX - 8, s.skullY - 7, false, 0f, 6, false, 0);
								respawnFire.createFire(new Color(0.3f,0,1f,0.6f), 15, null);
								fires.add(respawnFire);
                                respawnFire.blue = true;
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        respawnFire.type = 2;
                                        respawnFire.fireY -= 2;
                                        Timer.schedule(new Timer.Task() {
                                            @Override
                                            public void run() {
                                                respawnFire.smoking = true;
                                                Timer.schedule(new Timer.Task() {
                                                    @Override
                                                    public void run() {
                                                        respawnFire.type = 6;
                                                        respawnFire.fireY += 2;
                                                        Timer.schedule(new Timer.Task() {
                                                            @Override
                                                            public void run() {
                                                                respawnFire.type = 0;
                                                            }
                                                        }, 3f);
                                                    }
                                                }, 3f);
                                            }
                                        }, 3f);
                                    }
                                }, 2f);
								s.resurrecting = true;
								Timer.schedule(new Timer.Task() {
									@Override
									public void run() {
                                        if (!s.broken) {
                                            if (f.active) {
                                                brokenSkulls.add(s);
                                                EnemySkull respawnedEnemy = new EnemySkull(world, s.skullX, s.skullY);
                                                DungeonCrawler.enemySkulls.add(respawnedEnemy);
                                                respawnedEnemy.createEnemy(1, 11);
                                                respawnedEnemy.rayCastable = true;
                                                enemies.add(respawnedEnemy);

                                                respawnedEnemy.enemyAI.setMaxLinearSpeed(respawnedEnemy.defaultSpeed);
                                                //respawnedEnemy.stateMachine.changeState(EnemySkullState.WANDER);
                                                respawnedEnemy.active = true;

                                                //respawnedEnemy.detectPlayer();
                                            }
                                        }
									}
								}, 5f);
								extinguishedRespawnFires.add(respawnFire);
								}
							}
						}
					}
				}
			}



			/*
						if (raf.time < raf.raiseTime) {
				raf.time = raf.time + Gdx.graphics.getDeltaTime();
				System.out.println(raf.time);

			} else if (raf.raising) {

				if (!(raf.topY >= raf.rafBody.getPosition().y)) {
					raf.topY += Gdx.graphics.getDeltaTime() * 8;
				} else {
					raf.raising = false;
					raf.lowering = true;
				}
			} else if (raf.lowering) {
				if (!(raf.topY <= raf.rafBody.getPosition().y - 8)) {
					raf.topY -= Gdx.graphics.getDeltaTime() * 8;
				} else {

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							raf.lowering = false;
							raf.raising = true;
						}
					}, 0.5f);

				}
			 */

		for (RaisedFloor raf : raisedFloors) {

			raisedFloorBatch.begin();

			if (raf.time < raf.raiseTime) {
				raf.time = raf.time + (Gdx.graphics.getDeltaTime() * TIME_SCALE);
				//System.out.println(raf.time);

			} else if (raf.raising) {

				if (!(raf.rafBody.getPosition().y >= raf.rafY + 12)) {
					//raf.topY += Gdx.graphics.getDeltaTime() * 8;
					raf.rafBody.setLinearVelocity(0, 10);
				} else {
					raf.rafBody.setLinearVelocity(0, 0);
					raf.lowering = false;
					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
								raf.lowering = true;
								raf.raising = false;

						}
					}, 0.5f);
				}
			} else if (raf.lowering) {
				if (!(raf.rafBody.getPosition().y <= raf.rafY + 4)) {
					//raf.topY -= Gdx.graphics.getDeltaTime() * 8;
					raf.rafBody.setLinearVelocity(0, -10);
				} else {
					raf.rafBody.setLinearVelocity(0, 0);
					raf.lowered = true;
					raf.lowering = false;
					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							if (!raf.entityColliding) {
								raf.lowered = false;
								raf.raising = true;
							}
						}
					}, 1f);

				}
			}

			Rectangle clipBounds = new Rectangle(raf.rafX, raf.rafY, 16, 16);
			RaisedFloor.renderRaisedFloor(raisedFloorBatch, clipBounds, tx.raisedFloorSprite, raf.rafBody.getPosition().x - 8, raf.rafBody.getPosition().y - 12);

			raisedFloorBatch.end();
		}

		if (!heartArrayMap.isEmpty()) {
			for (OrderedMap.Entry<Body, Heart> heartEntry : heartArrayMap.entries()) {
				Heart value = heartEntry.value;
				//render each heart
				heartBatch.begin();
				if (value.type == 1) {
					Heart.renderHeart(heartBatch, tx.heartItemSprite, heartEntry.key.getPosition().x, heartEntry.key.getPosition().y, 1);
				} else if (value.type == 2) {
					Heart.renderHeart(heartBatch, tx.halfHeartItemSprite, heartEntry.key.getPosition().x, heartEntry.key.getPosition().y, 2);
				}
				heartBatch.end();
			}

			if (!reversedHeartMap) {
				heartArrayMap.reverse();
				reversedHeartMap = true;
			}

			//remove hearts collected by the player
			Iterator<Heart> heartIt = collectedHearts.iterator();
			if (heartIt.hasNext()) {
				Heart heart = heartIt.next();
				if (collectedHearts.contains(heart)) {

					if (heart.type == 1) {
						hud.healthBar.gainHealth(1f);
					} else if (heart.type == 2) {
						hud.healthBar.gainHealth(0.5f);
					}

					heart.heartLight.setActive(false);
					hearts.remove(heart);
					heartArrayMap.removeKey(heart.heartBody);
					world.destroyBody(heart.heartBody);
					heartIt.remove();
					collectedHearts.remove(heart);
				}
			}
		}

		if (!coinArrayMap.isEmpty()) {
			for (OrderedMap.Entry<Body, Coin> coinEntry : coinArrayMap.entries()) {
				Coin value = coinEntry.value;
				//render each coin
				coinBatch.begin();
					Coin.renderCoin(coinBatch, tx.coinItemSprite, coinEntry.key.getPosition().x, coinEntry.key.getPosition().y);
				coinBatch.end();
			}

			if (!reversedCoinMap) {
				coinArrayMap.reverse();
				reversedCoinMap = true;
			}

			Iterator<Coin> coinIt = collectedCoins.iterator();
			if (coinIt.hasNext()) {
				Coin coin = coinIt.next();
				if (collectedCoins.contains(coin)) {
					soundController.playSound("Coin",10,8,0.2f);
					hud.updateGold(1,true);
					coin.coinLight.setActive(false);
					coins.remove(coin);
					coinArrayMap.removeKey(coin.coinBody);
					world.destroyBody(coin.coinBody);
					coinIt.remove();
					collectedCoins.remove(coin);
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
				Cobweb.renderCobweb(cobBatch, cobEntry.key.getPosition().x, cobEntry.key.getPosition().y);
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

			if (!skullArrayMap.isEmpty()) {

				if (!reversedSkullMap) {
					skullArrayMap.reverse();
					reversedSkullMap = true;
				}

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

				if (!reversedPotMap) {
					potArrayMap.reverse();
					reversedPotMap = true;
				}

				for (Pot p : pots) {
					//p.particleEffect.setPosition(p.potBody.getPosition().x, p.potBody.getPosition().y);
				}

				for (OrderedMap.Entry<Body, Pot> potEntry : potArrayMap.entries()) {
					Pot value = potEntry.value;

					//value.particleEffect.setPosition(potEntry.key.getPosition().x, potEntry.key.getPosition().y);
/*
					//value.particleEffect.start();
					potParticlesBatch.begin();
					value.particleEffect.draw(potParticlesBatch, value.particleTime);
					//value.renderParticles(potParticlesBatch, potEntry.key.getPosition().x, potEntry.key.getPosition().y,value.particleEffect, value.particleTime);
					potParticlesBatch.end();
						value.particleEffect.update(value.particleTime);
						value.particleTime += Gdx.graphics.getDeltaTime();

						//p.particleEffect.draw(potParticlesBatch, p.particleTime);



						//

						if (value.particleEffect.isComplete()){
							System.out.println("VBDSDFGF");
							value.particleEffect.reset();
							//
							value.particleTime = 0;
						} else {
							System.out.println("DSASDFDSDF");

						}

 */




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



					}
					potBatch.end();
				}

				Iterator<Pot> potIt = brokenPots.iterator();
				if (potIt.hasNext()) {
					Pot pot = potIt.next();
					if (brokenPots.contains(pot)) {
						//one in 20 chance to get a potion from a pot - subject to change (was 7)
						int min = 1;
						int max = 100;
						int lootChance = Random.randomInt(100,1);

						//5% chance to get a Potion
						if (lootChance >= 97 && lootChance <= 100) {
							//create potion object
							Potion potion = new Potion(world, pot.potBody.getPosition().x, pot.potBody.getPosition().y, 1);
							potion.createPotion(potionArrayMap, rayHandler);
							potions.add(potion);
							potionArrayMap.put(potion.potionBody, potion);

						//25% chance to get a Coin
						} else if (lootChance >= 1 && lootChance <= 25) {
							Coin coin = new Coin(world, pot.potBody.getPosition().x, pot.potBody.getPosition().y);
							coin.createCoin(coinArrayMap, rayHandler);
							coins.add(coin);
							coinArrayMap.put(coin.coinBody, coin);

						//10% chance to get a Heart
						} else if (lootChance >= 26 && lootChance <= 36) {
							//create heart object
							int heartType = Random.randomInt(2,1);
							Heart heart = new Heart(world, pot.potBody.getPosition().x, pot.potBody.getPosition().y, heartType);
							heart.createHeart(heartArrayMap, rayHandler);
							hearts.add(heart);
							heartArrayMap.put(heart.heartBody, heart);
						}
						pots.remove(pot);
						potArrayMap.removeKey(pot.potBody);
						world.destroyBody(pot.potBody);
						potIt.remove();
					}
				}
			}





		for (Obstacle ob : obstacles) {
			if (!ob.obCreated) {
				obArrayMap.put(ob.createObstacle(obArrayMap, ob.type), ob);
			}
		}

		if (!obArrayMap.isEmpty()) {

			if (!reversedObMap) {
				obArrayMap.reverse();
				reversedObMap = true;
			}

			for (OrderedMap.Entry<Body, Obstacle> obEntry : obArrayMap.entries()) {
				Body key = obEntry.key;
				Obstacle value = obEntry.value;
				//render each web spit attack sprite
				obstacleBatch.begin();
				switch (value.type){
					case 1:
						obstacleBatch.draw(tx.obstacle1Sprite, value.obBody.getPosition().x - 8f, value.obBody.getPosition().y - 8f, 16, 16);
						break;
					case 2:
						obstacleBatch.draw(tx.obstacle2Sprite, value.obBody.getPosition().x - 8f, value.obBody.getPosition().y - 8f, 16, 16);
						break;
					case 3:
						obstacleBatch.draw(tx.obstacle3Sprite, value.obBody.getPosition().x - 8f, value.obBody.getPosition().y - 8f, 16, 16);
						break;
                    case 4:
                        obstacleBatch.draw(tx.obstacle4Sprite, value.obBody.getPosition().x - 8f, value.obBody.getPosition().y - 8.5f, 16, 16);
                        break;
                    case 5:
                        obstacleBatch.draw(tx.obstacle5Sprite, value.obBody.getPosition().x - 8f, value.obBody.getPosition().y - 8.5f, 16, 16);
                        break;
                    case 6:
                        obstacleBatch.draw(tx.obstacle6Sprite, value.obBody.getPosition().x - 8f, value.obBody.getPosition().y - 8.5f, 16, 16);
                        break;
                    case 7:
                        obstacleBatch.draw(tx.obstacle7Sprite, value.obBody.getPosition().x - 8f, value.obBody.getPosition().y - 8.5f, 16, 16);
                        break;
				}
				obstacleBatch.end();
			}

			Iterator<Body> obIt = obstacleBodiesCollected.iterator();
			if (obIt.hasNext()) {
				Body obBody = obIt.next();
				if (obArrayMap.containsKey(obBody)) {

					obArrayMap.removeKey(obBody);
					world.destroyBody(obBody);
					obIt.remove();
					obstacles.remove(obBody);
				}
			}
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

        for (OilLamp l : lamps) {
            lampBatch.begin();
            switch (l.type){
                case 1:
                    lampBatch.draw(tx.oilLamp, l.lampBody.getPosition().x - 8f, l.lampBody.getPosition().y - 8f, 16, 9);
                    break;
            }
            lampBatch.end();
        }


		for (Fire f : fires) {
			if (f.type == 3 || f.type == 5 || f.type == 6) {
				if (!f.blue) {
					currentFrame = tx.flameAnimation.getKeyFrame(stateTime, f.active);
				} else {
					currentFrame = tx.blueFlameAnimation.getKeyFrame(stateTime, f.active);
				}


				if (f.smoking) {
					f.torchLight.setColor(f.light.getColor().r, f.light.getColor().g, f.light.getColor().b, 0.40f);
					TextureRegion currentFrame = tx.flameSmokeAnimation.getKeyFrame(f.stateTime, false);
					flameBatch.begin();



						Fire.renderFire(flameBatch, currentFrame, f.fireX, f.fireY, 16,16,f.smoking, f.direction,f.visible,f,f.alpha);


					flameBatch.end();
					f.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

					if (tx.flameSmokeAnimation.isAnimationFinished(f.stateTime)) {
						f.stateTime = 0;
						f.active = false;
						f.torchLight.setActive(false);
						f.light.setActive(false);
						//f.light.setColor(f.light.getColor().r, f.light.getColor().g, f.light.getColor().b, 0f);
						f.smoking = false;
					}
				} else {
					if (!f.active) {

						TextureRegion currentFrame2 = tx.flameOutAnimation.getKeyFrame(f.stateTime, true);
						flameBatch.begin();
							Fire.renderFire(flameBatch, currentFrame2, f.fireX, f.fireY, 16,16,f.smoking, f.direction,f.visible,f,f.alpha);

						flameBatch.end();
						f.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);
					}
					else {
						flameBatch.begin();
							Fire.renderFire(flameBatch, currentFrame, f.fireX, f.fireY, 16,16,f.smoking, f.direction,f.visible,f,f.alpha);
						flameBatch.end();
					}
				}
			}


		}

		for (ColumnPiece c : columnPieces) {
			objectBounds.set(
					c.columnX,
					c.columnY,
					16,
					16
			);
			if (cameraBounds.overlaps(objectBounds)) {
				pedestalBatch.begin();
				switch (c.type) {
					case 14:
						pedestalBatch.draw(tx.pedestal1, c.columnX, c.columnY + 2);
						break;
					case 15:
						pedestalBatch.draw(tx.pedestal2, c.columnX, c.columnY + 2);
						break;
					case 16:
						pedestalBatch.draw(tx.pedestal3, c.columnX, c.columnY + 2);
						break;
					case 17:
						pedestalBatch.draw(tx.pedestal4, c.columnX, c.columnY + 2);
						break;
				}
				pedestalBatch.end();
			}
		}






		for (ColumnPiece c : columnPieces) {
			objectBounds.set(
					c.columnX,
					c.columnY,
					16,
					16
			);
			if (cameraBounds.overlaps(objectBounds)) {
				columnBaseLowerBatch.begin();
				if (c.type == 70) {
					columnBaseLowerBatch.draw(tx.colBaseLower, c.columnX, c.columnY);
				} else if (c.type == 71) {
					columnBaseLowerBatch.draw(tx.colBase2Lower, c.columnX, c.columnY);
				} else if (c.type == 730) {
					columnBaseLowerBatch.draw(tx.colBase3Lower, c.columnX, c.columnY);
				} else if (c.type == 740) {
					columnBaseLowerBatch.draw(tx.colBase4Lower, c.columnX, c.columnY);
				} else if (c.type == 750) {
					columnBaseLowerBatch.draw(tx.colBase5Lower, c.columnX, c.columnY);
				} else if (c.type == 760) {
					columnBaseLowerBatch.draw(tx.colBase6Lower, c.columnX, c.columnY);
				} else if (c.type == 770) {
					columnBaseLowerBatch.draw(tx.colBase7Lower, c.columnX, c.columnY);
				} else if (c.type == 780) {
                    columnBaseLowerBatch.draw(tx.colBaseDamaged1Lower, c.columnX, c.columnY);
                } else if (c.type == 790) {
                    columnBaseLowerBatch.draw(tx.colBaseDamaged2Lower, c.columnX, c.columnY);
                } else if (c.type == 800) {
                    columnBaseLowerBatch.draw(tx.colBaseDamaged3Lower, c.columnX, c.columnY);
        }

				columnBaseLowerBatch.end();
			}
		}



			playerBatch.begin();
			//draw playerSprite on player Box2D object
		if (playerShieldAttacking) {
			if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 11f, player.playerBody.getPosition().y + 7f, 16, 8, 16, 8, 1, 1, 0);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 26f, player.playerBody.getPosition().y - 20f, 16, 8, 16, 8, 1, 1, 180);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 30f, player.playerBody.getPosition().y - 2f, 16, 8, 16, 8, 1, 1, 90);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
				playerBatch.draw(tx.shieldSprite, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 18f, 16, 8, 16, 8, 1, 1, 270);
			}
		}
			//render the player sprite on the player body
			Player.renderPlayer(playerBatch, tx.playerTextureRegion, player.playerBody.getPosition().x - 7.5f, player.playerBody.getPosition().y - 6f);

			playerBatch.end();


		for (Water w : water) {

			currentFrame = tx.waterAnimation.getKeyFrame(w.stateTime, true);
			w.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

			//GenerateLevel.init.roomList.get(r.index).roomHitbox.getBody().getPosition().x;

			//shader variables passed into flag fragment shader
			/*
			waveBatch.setShader(waveShader);
			waveShader.setUniformf("u_swayIntensity", 0.05f);//0.03
			waveShader.setUniformf("u_verticalDensity", 1f);
			waveShader.setUniformf("u_time", flag_time + w.time);
			waveShader.setUniformf("u_speed", 1.6f);

										Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, f.smoking, true);
			 */
			waterBatch.begin();
			Water.renderWater(waterBatch, currentFrame, w.waterX, w.waterY, 16, 16, 1);
			waterBatch.end();
		}

		//headBatch.begin();


			playerBatch.begin();

			if (!player.swimming) {
				Player.renderPlayer(playerBatch, tx.playerTextureRegion, player.playerBody.getPosition().x - 7.5f, player.playerBody.getPosition().y - 6f);
			}


			if (player.facing == 3) {
				//Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 8f);

                if (leanLeft) {
                    Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 6.5f, player.playerBody.getPosition().y - 6f);
                } else {
                    Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 7.5f, player.playerBody.getPosition().y - 4f);
                }
            }
            else if (player.facing == 1) {
            //Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 8f);
                Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 7.5f, player.playerBody.getPosition().y - 6f);
            }
            else if (player.facing == 2) {
                //Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 8f);
                Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 8.5f, player.playerBody.getPosition().y - 6f);
            }
            else if (player.facing == 4) {
                if (leanLeft) {
                    Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 7.5f, player.playerBody.getPosition().y - 7f);
                } else {
                    Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 6.5f, player.playerBody.getPosition().y - 5f);
                }
                //Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 8f);

            }

            // && (playerMeleeAttacking || playerUsingChisel || playerRangedAttacking || playerShieldAttacking)

            else {
				//Player.renderPlayer(playerBatch, tx.playerHead, player.playerBody.getPosition().x - 7.5f, player.playerBody.getPosition().y - 5f);
			}

			playerBatch.end();

		//headBatch.end();


		for (ColumnPiece c : columnPieces) {
			objectBounds.set(
					c.columnX,
					c.columnY,
					16,
					16
			);
			if (cameraBounds.overlaps(objectBounds)) {
				pedestalBatch.begin();
				switch (c.type) {
					case 14:
						pedestalBatch.draw(tx.pedestal1upper, c.columnX, c.columnY + 13);
						break;
					case 15:
						pedestalBatch.draw(tx.pedestal2upper, c.columnX, c.columnY + 14);
						break;
					case 16:
						pedestalBatch.draw(tx.pedestal3upper, c.columnX, c.columnY + 14);
						break;
					case 17:
						pedestalBatch.draw(tx.pedestal4upper, c.columnX, c.columnY + 14);
						break;
				}
				pedestalBatch.end();
			}
		}

        for (FirePit fp : firepits) {
            firePitBatch.begin();
            FirePit.renderFirepit(firePitBatch,tx.firePitSprite,fp.firepitX,fp.firepitY);
            firePitBatch.end();
        }

        for (Brazier b : braziers) {
            if (b.type == 2) {
                brazierBatch.begin();
                brazierBatch.draw(tx.brazier, b.brazierBody.getPosition().x - 8f, b.brazierBody.getPosition().y - 8f, 16, 16);
                brazierBatch.end();
            }
        }



		if (!potionArrayMap.isEmpty()) {
			if (!reversedPotionMap) {
				potionArrayMap.reverse();
				reversedPotionMap = true;
			}

			for (OrderedMap.Entry<Body, Potion> potionEntry : potionArrayMap.entries()) {
				Potion value = potionEntry.value;
				//render each potion
				potionBatch.begin();
				if (value.type == 1) {
					Potion.renderPotion(potionBatch, tx.potionItemSprite, potionEntry.key.getPosition().x, potionEntry.key.getPosition().y);
				}
				potionBatch.end();
			}



			Iterator<Potion> potionIt = collectedPotions.iterator();
			if (potionIt.hasNext()) {
				Potion potion = potionIt.next();
				if (collectedPotions.contains(potion)) {

					hud.inventory.addPotion();
					potion.potionLight.setActive(false);
					potions.remove(potion);
					potionArrayMap.removeKey(potion.potionBody);
					world.destroyBody(potion.potionBody);
					potionIt.remove();
				}
			}
		}



		for (Fire f : fires) {
			if (f.smoking && (f.type == 1 || f.type == 2)) {
				f.light.setColor(f.light.getColor().r, f.light.getColor().g, f.light.getColor().b, 0.65f);
				TextureRegion currentFrame = tx.smokeAnimation.getKeyFrame(f.stateTime, false);
				fireBatch.begin();

                if (!f.loweredAlpha) {
                    f.loweredAlpha = true;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (!f.visible) {
                                if (f.alpha > 50) {
                                    f.loweredAlpha = false;
                                }
                                //r.loweredAlpha = true;

                                if (f.alpha >= 0)
                                    f.alpha--;
                                f.alpha--;
                                f.alpha--;
                            } else {
                                if (f.alpha < 100 && f.alpha > 0) {
                                    f.loweredAlpha = false;
                                    f.alpha++;
                                    f.alpha++;
                                } else if (f.alpha < 0) {
                                    f.alpha = 15;
                                    f.loweredAlpha = true;
                                }
                            }
                        }
                    }, 0.001f);
                }

                if (f.visible) {
                    f.loweredAlpha = false;
                }

				Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, 16,16,f.smoking, f.direction,f.visible,f,f.alpha);
				fireBatch.end();
				f.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

				if (tx.smokeAnimation.isAnimationFinished(f.stateTime)) {
					f.active = false;
					f.light.setActive(false);
					f.stateTime = 0;
					f.smoking = false;
				}
			} else if (f.type == 1 || f.type == 2) {
				if (!f.active) {

					TextureRegion currentFrame2 = tx.fireOutAnimation.getKeyFrame(f.stateTime, true);
					fireBatch.begin();
                    if (!f.loweredAlpha) {
                        f.loweredAlpha = true;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if (!f.visible) {
                                    if (f.alpha > 50) {
                                        f.loweredAlpha = false;
                                    }
                                    //r.loweredAlpha = true;

                                    if (f.alpha >= 0)
                                        f.alpha--;
                                    f.alpha--;
                                    f.alpha--;
                                } else {
                                    if (f.alpha < 100 && f.alpha > 0) {
                                        f.loweredAlpha = false;
                                        f.alpha++;
                                        f.alpha++;
                                    } else if (f.alpha < 0) {
                                        f.alpha = 15;
                                        f.loweredAlpha = true;
                                    }
                                }
                            }
                        }, 0.001f);
                    }

                    if (f.visible) {
                        f.loweredAlpha = false;
                    }

					Fire.renderFire(fireBatch, currentFrame2, f.fireX, f.fireY,16,16, f.smoking, f.direction,f.visible,f,f.alpha);
					fireBatch.end();
					f.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

				}
			}
		}

			weaponBatch.begin();
		if (playerMeleeAttacking) {
			//add the sword sprite to the corresponding attack playerDirection
			if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
				weaponBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 13f, player.playerBody.getPosition().y - 4f, 7, 14, 7, 14, 1, 1, 180);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
				weaponBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 6f, player.playerBody.getPosition().y - 20f, 7, 14, 7, 14, 1, 1, 0);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
				weaponBatch.draw(tx.swordSprite, player.playerBody.getPosition().x - 15f, player.playerBody.getPosition().y - 19f, 7, 14, 7, 14, 1, 1, 270);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
				weaponBatch.draw(tx.swordSprite, player.playerBody.getPosition().x + 1f, player.playerBody.getPosition().y - 12f, 7, 14, 7, 14, 1, 1, 90);
			}
		}

		if (playerRangedAttacking) {
			//add the bowSprite and arrowSprite to the corresponding attack playerDirection
			if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
				weaponBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 2f, 8, 10, 18, 8, 1, 1, 180);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
				weaponBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 10f, player.playerBody.getPosition().y - 14f, 7, 12, 18, 8, 1, 1, 0);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
				weaponBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 11f, player.playerBody.getPosition().y - 9f, 7, 12, 18, 8, 1, 1, 270);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
				weaponBatch.draw(tx.bowSprite, player.playerBody.getPosition().x - 3f, player.playerBody.getPosition().y - 13f, 7, 12, 18, 8, 1, 1, 90);
			}
		}

		if (playerUsingChisel) {
			//add the chisel sprite to the corresponding attack playerDirection
			if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
				weaponBatch.draw(tx.chiselSprite, player.playerBody.getPosition().x - 13f, player.playerBody.getPosition().y - 4f, 7, 14, 7, 14, 1, 1, 180);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
				weaponBatch.draw(tx.chiselSprite, player.playerBody.getPosition().x - 6f, player.playerBody.getPosition().y - 20f, 7, 14, 7, 14, 1, 1, 0);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
				weaponBatch.draw(tx.chiselSprite, player.playerBody.getPosition().x - 15f, player.playerBody.getPosition().y - 19f, 7, 14, 7, 14, 1, 1, 270);
			} else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
				weaponBatch.draw(tx.chiselSprite, player.playerBody.getPosition().x + 1f, player.playerBody.getPosition().y - 12f, 7, 14, 7, 14, 1, 1, 90);
			}
		}
			weaponBatch.end();

            if (difficulty == 1) {
                boneThrowTime = 300;
            } else if (difficulty == 2) {
                boneThrowTime = 230;
            } else if (difficulty == 3) {
                boneThrowTime = 150;
            }

            for (Enemy E : enemies) {

                if (E.swimming) {
                    currentFrame = tx.rippleAnimation.getKeyFrame(stateTime, true);
                    rippleBatch.begin();
                    rippleBatch.setColor(1, 1, 1, 0.25f);
                    rippleBatch.draw(currentFrame, E.enemyBody.getPosition().x - 9, E.enemyBody.getPosition().y - 10, 18, 10);
                    rippleBatch.end();

                }

            }

			//render enemy skull sprites
			for (EnemySkull e : enemySkulls) {
				if (e.rayCastable) {
					e.detectPlayer();
				}
				//check to see if the player is both in sight and in range
				if ((e.playerSighted && e.playerInRange) && (pauseMenuClosed && optionsMenuClosed)){
					//after a specified delay once the player has been spotted, shoot a projectile with some offset at the player
					if (e.timeSinceAlerted > ((Gdx.graphics.getDeltaTime() * TIME_SCALE) * boneThrowTime) ){
						e.timeSinceAlerted = 0f;
						e.lostSight = false;

						//susMessages.remove(e.lostSightMessage);

						e.enemyAI.setMaxLinearSpeed(10);

						Vector2 vec1 = new Vector2(e.enemyBody.getPosition());
						Vector2 vec2 = new Vector2(Player.playerBody.getPosition());

						float x = MathUtils.atan2(vec2.y - vec1.y, vec2.x - vec1.x);
						float randomOffset = Random.randomFloat(1.5f,0.5f);
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

						Bone bone = new Bone(world, e.enemyBody, e.enemyBody.getPosition().x, e.enemyBody.getPosition().y, false,  true, finalX);
						bone.createBone();
						bones.add(bone);
						boneArrayMap.put(bone.boneBody, bone);

						soundController.playSound("Whoosh", 7, 6,0.1f);

					} else {
						Timer.schedule(new Timer.Task() {
							@Override
							public void run() {
								e.enemyAI.setMaxLinearSpeed(e.defaultSpeed);
							}
						}, 0.5f);
						e.timeSinceAlerted = e.timeSinceAlerted + (Gdx.graphics.getDeltaTime() * TIME_SCALE);

						if (!e.alerted) {
							e.alertMessage.fadeTiming = 1f;
							e.alertMessage.showing = true;
							e.alertMessage.fade = true;


							e.alertMessage.textX = e.enemyAI.getBody().getPosition().x - 2f;
							e.alertMessage.textY = e.enemyAI.getBody().getPosition().y + 8f;
							alertMessages.add(e.alertMessage);
							e.alerted = true;
						}
					}


					e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);

				} else if (!e.playerSighted) {

					e.alerted = false;
					susMessages.remove(e.alertMessage);
					if (!e.lostSight) {
						e.lostSightMessage.fadeTiming = 1f;
						e.lostSight = true;
						e.lostSightMessage.showing = true;
						//e.lostSightMessage.fade = true;
						e.lostSightMessage.textX = e.enemyAI.getBody().getPosition().x - 2f;
						e.lostSightMessage.textY = e.enemyAI.getBody().getPosition().y + 8f;
						susMessages.add(e.lostSightMessage);
					}
					if (e.timeSinceAlerted > 50) {
						e.timeSinceAlerted = e.timeSinceAlerted - 50;
					}

				}
				enemySkullBatch.begin();
				if (!e.alerted) {
					e.skullLight.setActive(false);
					//enemySkullBatch.draw(tx.enemySkullSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);

					if (e.facing == "Up") {
					//	currentFrame = tx.enemySkullUpAlertedAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(tx.enemySkullUpSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					} else if (e.facing == "Down") {
					//	currentFrame = tx.enemySkullDownAlertedAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(tx.enemySkullDownSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					} else if (e.facing == "Left") {
					//	currentFrame = tx.enemySkullLeftAlertedAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(tx.enemySkullLeftSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					} else if (e.facing == "Right") {
					//	currentFrame = tx.enemySkullUpAlertedAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(tx.enemySkullRightSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					} else {
					//	currentFrame = tx.enemySkullDownAlertedAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(tx.enemySkullDownSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					}

				} else if (e.timeSinceAlerted >= 1) {
					e.skullLight.setActive(true);

					if (e.facing == "Up") {
						currentFrame = tx.enemySkullUpAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(currentFrame, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					} else if (e.facing == "Down") {
						currentFrame = tx.enemySkullDownAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(currentFrame, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					} else if (e.facing == "Left") {
						currentFrame = tx.enemySkullLeftAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(currentFrame, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					} else if (e.facing == "Right") {
						currentFrame = tx.enemySkullRightAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(currentFrame, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					} else {
						currentFrame = tx.enemySkullDownAnimation.getKeyFrame(stateTime, true);

						enemySkullBatch.draw(currentFrame, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
					}


					//enemySkullBatch.draw(tx.enemySkullAlertedSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				} else {
					e.skullLight.setActive(false);
					enemySkullBatch.draw(tx.enemySkullDownSprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
				}
				/*
								if (e2.facing == "Up") {
					enemySpiderBatch.draw(tx.enemySpiderUpSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Down") {
					enemySpiderBatch.draw(tx.enemySpiderDownSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Left") {
					enemySpiderBatch.draw(tx.enemySpiderLeftSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e2.facing == "Right") {
					enemySpiderBatch.draw(tx.enemySpiderRightSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				} else {
					enemySpiderBatch.draw(tx.enemySpiderDownSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				}


				 */


				enemySkullBatch.end();
			}

			for (EnemySkull deadSkull : dyingSkulls) {
				deadSkull.skullLight.setActive(false);
				deadSkull.getStateMachine().changeState(EnemySkullState.DIE);
				enemies.remove(deadSkull);
			}
			dyingSkulls.clear();


		//render enemy spider sprites
		for (EnemySpider e2 : enemySpiders) {
			if (e2.rayCastable) {
				e2.detectPlayer();
			}
			//check to see if the player is both in sight and in range
			if ((e2.playerSighted && e2.playerInRange) && (pauseMenuClosed && optionsMenuClosed)){
				//after a specified delay once the player has been spotted, shoot a projectile with some offset at the player
				if (e2.timeSinceAlerted > ((Gdx.graphics.getDeltaTime() * TIME_SCALE) * 130) ){
						e2.timeSinceAlerted = 0f;
						e2.lostSight = false;
						susMessages.remove(e2.lostSightMessage);

						e2.enemyAI.setMaxLinearSpeed(25);

						Vector2 vec1 = new Vector2(e2.enemyBody.getPosition());
						Vector2 vec2 = new Vector2(Player.playerBody.getPosition());

						float x = MathUtils.atan2(vec2.y - vec1.y, vec2.x - vec1.x);

						e2.exitAngle = MathUtils.atan2(vec2.y - vec1.y, vec2.x - vec1.x);

						float randomOffset = Random.randomFloat(1.3f,0.3f);
						randomOffset = randomOffset / 10;
						boolean random = Random.randomBoolean();
						Vector2 finalX = new Vector2((float)Math.cos(e2.exitAngle),(float)Math.sin(e2.exitAngle));

						Web web = new Web(world, e2.enemyBody, e2.enemyBody.getPosition().x, e2.enemyBody.getPosition().y, false,  true, finalX);
						web.exitAngle = e2.exitAngle;
						web.createWeb(web.exitAngle);
						webs.add(web);
						webArrayMap.put(web.webBody, web);
						soundController.playSound("SpiderAttack",6f,5f,0.1f);

				} else {
					e2.enemyAI.setMaxLinearSpeed(e2.defaultSpeed);
					e2.timeSinceAlerted = e2.timeSinceAlerted + (Gdx.graphics.getDeltaTime() * TIME_SCALE);
				}

				if (!e2.alerted) {

					//FontController.drawFont(alertFontBatch,);
					e2.alertMessage.showing = true;
					e2.alertMessage.fade = true;
					e2.alerted = true;

					e2.alertMessage.textX = e2.enemyAI.getBody().getPosition().x - 2f;
					e2.alertMessage.textY = e2.enemyAI.getBody().getPosition().y + 8f;
					susMessages.add(e2.alertMessage);
				}
				e2.getStateMachine().changeState(EnemySpiderState.GO_TO_PLAYER);
			} else if (!e2.playerSighted) {

				if (e2.timeSinceAlerted > 50) {
					e2.timeSinceAlerted = e2.timeSinceAlerted - 20;
				}
				susMessages.remove(e2.alertMessage);
				if (!e2.lostSight) {
					e2.lostSight = true;
					e2.lostSightMessage.showing = true;
					e2.lostSightMessage.fade = true;
					e2.lostSightMessage.textX = e2.enemyAI.getBody().getPosition().x - 2f;
					e2.lostSightMessage.textY = e2.enemyAI.getBody().getPosition().y + 8f;
					susMessages.add(e2.lostSightMessage);
				}
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
				} else {
					enemySpiderBatch.draw(tx.enemySpiderDownSprite, e2.enemyBody.getPosition().x - 8f, e2.enemyBody.getPosition().y - 7f, 16, 16);
				}
			enemySpiderBatch.end();
		}

		for (EnemySpider deadSpider : dyingSpiders) {
			deadSpider.getStateMachine().changeState(EnemySpiderState.DIE);
			enemies.remove(deadSpider);
		}
		dyingSpiders.clear();

		for (EnemyGhost e3 : enemyGhosts) {
			if (e3.rayCastable) {
				e3.detectPlayer();
			}
			if ((e3.playerSighted && e3.playerInRange) && (pauseMenuClosed && optionsMenuClosed)){
				if (e3.timeSinceAlerted > ((Gdx.graphics.getDeltaTime() * TIME_SCALE) * 130) ){
					e3.timeSinceAlerted = 0f;
					e3.lostSight = false;
					susMessages.remove(e3.lostSightMessage);

				} else {
					e3.timeSinceAlerted = e3.timeSinceAlerted + (Gdx.graphics.getDeltaTime() * TIME_SCALE);
				}

				if (!e3.alerted) {

					e3.alertMessage.showing = true;
					e3.alertMessage.fade = true;
					e3.alerted = true;

					e3.alertMessage.textX = e3.enemyAI.getBody().getPosition().x - 2f;
					e3.alertMessage.textY = e3.enemyAI.getBody().getPosition().y + 8f;
					susMessages.add(e3.alertMessage);
				}
				e3.getStateMachine().changeState(EnemyGhostState.GO_TO_PLAYER);

			} else if (!e3.playerSighted) {

				susMessages.remove(e3.alertMessage);
				if (!e3.lostSight) {
					e3.lostSight = true;
					e3.lostSightMessage.showing = true;
					e3.lostSightMessage.fade = true;
					e3.lostSightMessage.textX = e3.enemyAI.getBody().getPosition().x - 2f;
					e3.lostSightMessage.textY = e3.enemyAI.getBody().getPosition().y + 8f;
					susMessages.add(e3.lostSightMessage);
				}

			}
			enemyGhostBatch.begin();
			if (!e3.alerted) {
				e3.enemyHitbox.setSensor(false);
				if (e3.facing == "Up") {
					enemyGhostBatch.draw(tx.enemyGhostUpSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e3.facing == "Down") {
					enemyGhostBatch.draw(tx.enemyGhostDownSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e3.facing == "Left") {
					enemyGhostBatch.draw(tx.enemyGhostLeftSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e3.facing == "Right") {
					enemyGhostBatch.draw(tx.enemyGhostRightSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				} else {
					enemyGhostBatch.draw(tx.enemyGhostDownSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				}
			} else {
				if (e3.facing == "Up") {
					enemyGhostBatch.draw(tx.enemyGhostAlertUpSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e3.facing == "Down") {
					enemyGhostBatch.draw(tx.enemyGhostAlertDownSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e3.facing == "Left") {
					enemyGhostBatch.draw(tx.enemyGhostAlertLeftSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				} else if (e3.facing == "Right") {
					enemyGhostBatch.draw(tx.enemyGhostAlertRightSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				} else {
					enemyGhostBatch.draw(tx.enemyGhostDownSprite, e3.enemyBody.getPosition().x - 8f, e3.enemyBody.getPosition().y - 7f, 16, 16);
				}
			}
			enemyGhostBatch.end();
		}

			for (EnemyGhost deadGhost : dyingGhosts) {
				deadGhost.getStateMachine().changeState(EnemyGhostState.DIE);
				enemies.remove(deadGhost);
			}
			dyingGhosts.clear();

		//render enemy eye sprites
		for (EnemyCyclops e4 : enemyEyes) {
			if (e4.rayCastable) {
				e4.detectPlayer();
			}
			//check to see if the player is both in sight and in range
			if ((e4.playerSighted && e4.playerInRange) && (pauseMenuClosed && optionsMenuClosed)){
				//after a specified delay once the player has been spotted, fire a beam in the direction the enemy is facing
				if (e4.timeSinceAlerted > ((Gdx.graphics.getDeltaTime() * TIME_SCALE) * 140) ){
					e4.timeSinceAlerted = 0f;
					e4.lostSight = false;
					susMessages.remove(e4.lostSightMessage);

					e4.getStateMachine().changeState(EnemyCyclopsState.STOP);

					e4.firingBeam = true;
					e4.turnDelay = 1.2f;
				//	e4.canTurn = false;
				//	e4.turnOff = true;
					e4.locked = true;
					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							if ((e4.playerSighted && e4.playerInRange) && (pauseMenuClosed && optionsMenuClosed)) {
								e4.getStateMachine().changeState(EnemyCyclopsState.GO_TO_PLAYER);
							} else {
								e4.getStateMachine().changeState(EnemyCyclopsState.WANDER);
							}

							e4.enemyAI.setMaxLinearSpeed(e4.defaultSpeed);
							e4.firingBeam = false;
						}
					}, 1.2f);

					switch (e4.facing) {
						case "Up":
							Eyebeam eyebeamUp = new Eyebeam(world, e4.enemyBody, e4.enemyAI.getBody().getPosition().x, e4.enemyAI.getBody().getPosition().y, e4.facing, true);
							eyebeamUp.createEyebeam(e4.enemyBody, e4, eyebeamArrayMap,rayHandler);
							e4.enemyBody.setFixedRotation(true);
							e4.enemyBody.setTransform(e4.enemyBody.getPosition(),90);
							eyebeamUp.beamLight.setActive(true);
							break;
						case "Down":
							Eyebeam eyebeamDown = new Eyebeam(world, e4.enemyBody, e4.enemyAI.getBody().getPosition().x, e4.enemyAI.getBody().getPosition().y, e4.facing, true);
							eyebeamDown.createEyebeam(e4.enemyBody, e4, eyebeamArrayMap,rayHandler);
							e4.enemyBody.setFixedRotation(true);
							e4.enemyBody.setTransform(e4.enemyBody.getPosition(),270);
							eyebeamDown.beamLight.setActive(true);
							break;
						case "Left":
							Eyebeam eyebeamLeft = new Eyebeam(world, e4.enemyBody, e4.enemyAI.getBody().getPosition().x, e4.enemyAI.getBody().getPosition().y, e4.facing, false);
							eyebeamLeft.createEyebeam(e4.enemyBody, e4, eyebeamArrayMap,rayHandler);
							e4.enemyBody.setFixedRotation(true);
							e4.enemyBody.setTransform(e4.enemyBody.getPosition(),180);
							eyebeamLeft.beamLight.setActive(true);
							break;
						case "Right":
							Eyebeam eyebeamRight = new Eyebeam(world, e4.enemyBody, e4.enemyAI.getBody().getPosition().x, e4.enemyAI.getBody().getPosition().y, e4.facing, false);
							eyebeamRight.createEyebeam(e4.enemyBody, e4, eyebeamArrayMap,rayHandler);
							e4.enemyBody.setFixedRotation(true);
							e4.enemyBody.setTransform(e4.enemyBody.getPosition(),0);
							eyebeamRight.beamLight.setActive(true);
							break;
					}
					soundController.playSound("EyebeamAttack",10f,8.5f,0.1f);
				} else {

					if (!e4.firingBeam) {
						e4.locked = false;
						//e4.eyeLight.setActive(false);
						e4.timeSinceAlerted = e4.timeSinceAlerted + (Gdx.graphics.getDeltaTime() * TIME_SCALE);
					}
				}

				if (!e4.alerted) {
					e4.alertMessage.showing = true;
					e4.alertMessage.fade = true;
					e4.alerted = true;

					e4.alertMessage.textX = e4.enemyAI.getBody().getPosition().x - 2f;
					e4.alertMessage.textY = e4.enemyAI.getBody().getPosition().y + 8f;
					susMessages.add(e4.alertMessage);
				}
				if (e4.firingBeam) {
					e4.getStateMachine().changeState(EnemyCyclopsState.STOP);
				} else {
					e4.getStateMachine().changeState(EnemyCyclopsState.GO_TO_PLAYER);
				}

			} else if (!e4.playerSighted) {
				e4.turnDelay = 0.1f;
				susMessages.remove(e4.alertMessage);
				if (e4.timeSinceAlerted > 50) {
					e4.timeSinceAlerted = e4.timeSinceAlerted - 20;
				}
				if (!e4.lostSight) {
					e4.lostSight = true;
					e4.lostSightMessage.showing = true;
					e4.lostSightMessage.fade = true;
					e4.lostSightMessage.textX = e4.enemyAI.getBody().getPosition().x - 2f;
					e4.lostSightMessage.textY = e4.enemyAI.getBody().getPosition().y + 8f;
					susMessages.add(e4.lostSightMessage);
				}
			}
			enemyEyeBatch.begin();
			if (e4.facing == "Up") {
				enemyEyeBatch.draw(tx.enemyEyeUpSprite, e4.enemyBody.getPosition().x - 8f, e4.enemyBody.getPosition().y - 7f, 16, 16);
			} else if (e4.facing == "Down") {
				enemyEyeBatch.draw(tx.enemyEyeDownSprite, e4.enemyBody.getPosition().x - 8f, e4.enemyBody.getPosition().y - 7f, 16, 16);
			} else if (e4.facing == "Left") {
				enemyEyeBatch.draw(tx.enemyEyeLeftSprite, e4.enemyBody.getPosition().x - 8f, e4.enemyBody.getPosition().y - 7f, 16, 16);
			} else if (e4.facing == "Right") {
				enemyEyeBatch.draw(tx.enemyEyeRightSprite, e4.enemyBody.getPosition().x - 8f, e4.enemyBody.getPosition().y - 7f, 16, 16);
			} else {
				enemyEyeBatch.draw(tx.enemyEyeDownSprite, e4.enemyBody.getPosition().x - 8f, e4.enemyBody.getPosition().y - 7f, 16, 16);
			}
			enemyEyeBatch.end();
		}

		for (EnemyCyclops deadEye : dyingEyes) {
			for (Fixture fixture : deadEye.enemyBody.getFixtureList()) {

				if (fixture.getUserData() == "Eyebeam") {
					//this is all just to delete the eyebeam light if the enemy eye is firing a beam mid-death
					if (!eyebeamArrayMap.isEmpty()) {

						if (!reversedEyebeamMap) {
							eyebeamArrayMap.reverse();
							reversedEyebeamMap = true;
						}

						for (OrderedMap.Entry<Body, Eyebeam> beamEntry : eyebeamArrayMap.entries()) {
							Body key = beamEntry.key;
							Eyebeam value = beamEntry.value;

							if (key == fixture.getBody()) {
								value.beamLight.setActive(false);
							}
						}
					}
					eyebeamBodiesCollected.add(fixture.getBody());

				}
			}
			deadEye.getStateMachine().changeState(EnemyCyclopsState.DIE);
			enemies.remove(deadEye);
		}
		dyingEyes.clear();

		if (!eyebeamArrayMap.isEmpty()) {

			if (!reversedEyebeamMap) {
				eyebeamArrayMap.reverse();
				reversedEyebeamMap = true;
			}

			for (OrderedMap.Entry<Body, Eyebeam> beamEntry : eyebeamArrayMap.entries()) {
				Body key = beamEntry.key;
				Eyebeam value = beamEntry.value;

				TextureRegion currentFrame = tx.eyebeamAnimation.getKeyFrame(value.stateTime, false);
				value.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

				value.beamLightDistance += 0.15f;
				value.beamLightAlpha += 0.01f;

				value.beamLight.setDistance(value.beamLightDistance);
				value.beamLight.setColor(0.1f,0,1f,value.beamLightAlpha);
				value.beamLight.attachToBody(key);

				//render each eyebeam
				eyebeamBatch.begin();
				Eyebeam.renderEyebeam(eyebeamBatch, currentFrame, value.facing, key.getPosition().x, key.getPosition().y);
				eyebeamBatch.end();

				if (tx.eyebeamAnimation.isAnimationFinished(value.stateTime)) {
					eyebeamBodiesCollected.add(value.beamBody);
					value.beamLight.setActive(false);
				}
			}

			Iterator<Body> beamIt = eyebeamBodiesCollected.iterator();
			if (beamIt.hasNext()) {
				Body beamBody = beamIt.next();
				if (eyebeamArrayMap.containsKey(beamBody)) {
					for (Fixture fixture : beamBody.getFixtureList()) {
						if (fixture.getUserData() == "Eyebeam") {
							beamBody.destroyFixture(fixture);
						}
					}
					eyebeamArrayMap.removeKey(beamBody);
					beamIt.remove();
					eyebeams.remove(beamBody);
				}
			}
		}



        //render enemy crab sprites
        for (EnemyCrab e5 : enemyCrabs) {
            if (e5.rayCastable) {
                e5.detectPlayer();
            }
            //check to see if the player is both in sight and in range
            if ((e5.playerSighted && e5.playerInRange) && (pauseMenuClosed && optionsMenuClosed)){
                //after a specified delay once the player has been spotted, shoot a projectile with some offset at the player



                e5.getStateMachine().changeState(EnemyCrabState.GO_TO_PLAYER);

            } else if (!e5.playerSighted) {

                e5.alerted = false;
                susMessages.remove(e5.alertMessage);
                if (!e5.lostSight) {
                    e5.lostSightMessage.fadeTiming = 1f;
                    e5.lostSight = true;
                    e5.lostSightMessage.showing = true;
                    //e.lostSightMessage.fade = true;
                    e5.lostSightMessage.textX = e5.enemyAI.getBody().getPosition().x - 2f;
                    e5.lostSightMessage.textY = e5.enemyAI.getBody().getPosition().y + 8f;
                    susMessages.add(e5.lostSightMessage);
                }
                if (e5.timeSinceAlerted > 50) {
                    e5.timeSinceAlerted = e5.timeSinceAlerted - 50;
                }

            }
            enemyCrabBatch.begin();

            if (e5.facing == "Up") {
                currentFrame = tx.enemyCrabUpAnimation.getKeyFrame(stateTime, true);

                enemyCrabBatch.draw(currentFrame, e5.enemyBody.getPosition().x - 8f, e5.enemyBody.getPosition().y - 7f, 16, 16);
            } else if (e5.facing == "Down") {
                currentFrame = tx.enemyCrabDownAnimation.getKeyFrame(stateTime, true);

                enemyCrabBatch.draw(currentFrame, e5.enemyBody.getPosition().x - 8f, e5.enemyBody.getPosition().y - 7f, 16, 16);
            } else if (e5.facing == "Left") {
                currentFrame = tx.enemyCrabLeftAnimation.getKeyFrame(stateTime, true);

                enemyCrabBatch.draw(currentFrame, e5.enemyBody.getPosition().x - 8f, e5.enemyBody.getPosition().y - 7f, 16, 16);
            } else if (e5.facing == "Right") {
                currentFrame = tx.enemyCrabRightAnimation.getKeyFrame(stateTime, true);

                enemyCrabBatch.draw(currentFrame, e5.enemyBody.getPosition().x - 8f, e5.enemyBody.getPosition().y - 7f, 16, 16);
            } else {
                currentFrame = tx.enemyCrabDownAnimation.getKeyFrame(stateTime, true);

                enemyCrabBatch.draw(currentFrame, e5.enemyBody.getPosition().x - 8f, e5.enemyBody.getPosition().y - 7f, 16, 16);
            }

            enemyCrabBatch.end();
        }

        for (EnemyCrab deadCrab : dyingCrabs) {
            deadCrab.getStateMachine().changeState(EnemyCrabState.DIE);
            enemies.remove(deadCrab);
        }
        dyingCrabs.clear();


        for (Vase v : vases) {

            vaseBatch.begin();

            if (!v.loweredAlpha) {
                v.loweredAlpha = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (!v.visible) {
                            if (v.alpha > 50) {
                                v.loweredAlpha = false;
                            }
                            //r.loweredAlpha = true;

                            if (v.alpha >= 0)
                                v.alpha--;
                            v.alpha--;
                            v.alpha--;
                        } else {
                            if (v.alpha < 100 && v.alpha > 0) {
                                v.loweredAlpha = false;
                                v.alpha++;
                                v.alpha++;
                            } else if (v.alpha < 0) {
                                v.alpha = 15;
                                v.loweredAlpha = true;
                            }
                        }
                    }
                }, 0.001f);
            }

            if (v.visible) {
                v.loweredAlpha = false;
            }

            //vaseBatch.draw(tx.vaseSprite, v.vaseBody.getPosition().x - 8f, v.vaseBody.getPosition().y - 8f, 16, 16);
            Vase.renderVase(vaseBatch, tx.vaseSprite, v.vaseBody.getPosition().x - 8f, v.vaseBody.getPosition().y - 8f, 16, 16,v.visible,v,v.alpha);
            vaseBatch.end();
        }



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

			Iterator<Body> webIt = webBodiesCollided.iterator();
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
				//render each bone projectile
				boneBatch.begin();
				Bone.renderBone(boneBatch, tx.boneSprite, key.getPosition().x, key.getPosition().y, key.getAngle());
				boneBatch.end();
			}

			Iterator<Body> boneIt = boneBodiesCollided.iterator();
			if (boneIt.hasNext()) {
				Body boneBody = boneIt.next();
				if (boneArrayMap.containsKey(boneBody)) {
					Bone b = boneArrayMap.get(boneBody);
					b.particleEffect.setPosition(b.boneBody.getPosition().x, b.boneBody.getPosition().y);
					b.particleEffect.reset();
					b.particleEffect.scaleEffect(0.16f);
					b.particleEffect.start();

					soundController.playSound("Bone", 9, 8, 0.1f);
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



		for (Column C : columns) {
			for (ColumnPiece c : C.columnPieces) {
				//for each BASE instead - revise - columnpieces is too broad

					columnBaseBatch.begin();

						if (!c.loweredAlpha) {
							c.loweredAlpha = true;
							Timer.schedule(new Timer.Task() {
								@Override
								public void run() {
									if (!C.visible) {
										if (c.alpha > 35) {
											c.loweredAlpha = false;
										}
										//r.loweredAlpha = true;

										if (c.alpha >= 0)
											c.alpha--;
										c.alpha--;
									} else {
										if (c.alpha < 100 && c.alpha > 0) {
											c.loweredAlpha = false;
											c.alpha++;
											c.alpha++;
										} else if (c.alpha < 0) {

											c.alpha = 15;
											c.loweredAlpha = true;
										}
									}
								}
							}, 0.001f);
						} else {
							if (c.alpha < 100 && c.alpha > 0) {
								c.loweredAlpha = false;
								c.alpha++;
								c.alpha++;
								c.alpha++;
							} else if (c.alpha < 0) {
								//c.alpha = 100;
								c.alpha = 0;
								//c.loweredAlpha = true;
							}
						}

							switch (c.type) {
								case 30:
									c.loweredAlpha = false;
									Column.renderPiece(columnBaseBatch,tx.colBase3, c.columnX, c.columnY, C.visible, c.alpha, C);

									//columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
								case 40:
									c.loweredAlpha = false;
									Column.renderPiece(columnBaseBatch,tx.colBase4, c.columnX, c.columnY, C.visible, c.alpha, C);

									//columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
									if (!c.lowerCreated) {
										c.lowerCreated = true;
									}
									break;
								case 50:
									c.loweredAlpha = false;
									Column.renderPiece(columnBaseBatch,tx.colBase5, c.columnX, c.columnY, C.visible, c.alpha, C);

									//columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
									if (!c.lowerCreated) {
										c.lowerCreated = true;
									}
									break;
								case 60:
									c.loweredAlpha = false;
									Column.renderPiece(columnBaseBatch,tx.colBase6, c.columnX, c.columnY, C.visible, c.alpha, C);

									//columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
									if (!c.lowerCreated) {
										c.lowerCreated = true;
									}
									break;
								case 70:
									c.loweredAlpha = false;
									Column.renderPiece(columnBaseBatch,tx.colBase7, c.columnX, c.columnY, C.visible, c.alpha, C);

									//columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
									if (!c.lowerCreated) {
										c.lowerCreated = true;
									}
									break;
                                case 80:
                                    c.loweredAlpha = false;
                                    Column.renderPiece(columnBaseBatch,tx.colBaseDamaged1, c.columnX, c.columnY, C.visible, c.alpha, C);

                                    //columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
                                    if (!c.lowerCreated) {
                                        c.lowerCreated = true;
                                    }
                                    break;
                                case 90:
                                    c.loweredAlpha = false;
                                    Column.renderPiece(columnBaseBatch,tx.colBaseDamaged2, c.columnX, c.columnY, C.visible, c.alpha, C);

                                    //columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
                                    if (!c.lowerCreated) {
                                        c.lowerCreated = true;
                                    }
                                    break;
                                case 100:
                                    c.loweredAlpha = false;
                                    Column.renderPiece(columnBaseBatch,tx.colBaseDamaged3, c.columnX, c.columnY, C.visible, c.alpha, C);

                                    //columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
                                    if (!c.lowerCreated) {
                                        c.lowerCreated = true;
                                    }
                                    break;
								case 7:

									c.loweredAlpha = false;
									Column.renderPiece(columnBaseBatch,tx.colBase, c.columnX, c.columnY, C.visible, c.alpha, C);

									//columnBaseBatch.draw(tx.colBase, c.columnX, c.columnY);
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

									c.loweredAlpha = false;
									Column.renderPiece(columnBaseBatch,tx.colBase2, c.columnX, c.columnY, C.visible, c.alpha, C);
									//columnBaseBatch.draw(tx.colBase2, c.columnX, c.columnY);
									break;
							}



					columnBaseBatch.end();

			}
		}

		/*
				for (Column C : columns) {
			for (ColumnPiece c : C.columnPieces) {

				columnStemBatch.begin();
					switch (c.type) {
						case 4:
							Column.renderPiece(columnStemBatch,tx.colStem, c.columnX, c.columnY, C.visible);
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


		}
		 */

		if (!arrowsToBeFired.isEmpty()) {
			for (OrderedMap.Entry<Trap, Integer> firedArrowEntry : arrowsToBeFired.entries()) {
				Integer value = firedArrowEntry.value;
				Trap key = firedArrowEntry.key;
				switch (value) {
					case 1:
						arrowBody = Arrow.createArrowBody(DungeonCrawler.world, key.trapBody.getPosition().x + 8f, key.trapBody.getPosition().y - 7f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(0, -ARROW_SPEED);
						arrowDirection = "Down";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, false));
						break;
					case 2:
						arrowBody = Arrow.createArrowBody(DungeonCrawler.world, key.trapBody.getPosition().x - 7f, key.trapBody.getPosition().y - 8f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("LeftArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(-ARROW_SPEED, 0);
						arrowDirection = "Left";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, false));
						break;
					case 3:
						arrowBody = Arrow.createArrowBody(DungeonCrawler.world, key.trapBody.getPosition().x - 8f, key.trapBody.getPosition().y + 23f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("UpArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(0, ARROW_SPEED);
						arrowDirection = "Up";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, false));
						break;
					case 4:
						arrowBody = Arrow.createArrowBody(world, key.trapBody.getPosition().x + 23f, key.trapBody.getPosition().y + 8f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("RightArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(ARROW_SPEED, 0);
						arrowDirection = "Right";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, false));
						break;
					case 5:
						arrowBody = Arrow.createArrowBody(DungeonCrawler.world, key.trapBody.getPosition().x + 8f, key.trapBody.getPosition().y - 7f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(0, -ARROW_SPEED);
						arrowDirection = "Down";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, true));
						break;
					case 6:
						arrowBody = Arrow.createArrowBody(DungeonCrawler.world, key.trapBody.getPosition().x - 7f, key.trapBody.getPosition().y - 8f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("LeftArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(-ARROW_SPEED, 0);
						arrowDirection = "Left";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, true));
						break;
					case 7:
						arrowBody = Arrow.createArrowBody(DungeonCrawler.world, key.trapBody.getPosition().x - 8f, key.trapBody.getPosition().y + 23f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("UpArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(0, ARROW_SPEED);
						arrowDirection = "Up";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, true));
						break;
					case 8:
						arrowBody = Arrow.createArrowBody(world, key.trapBody.getPosition().x + 23f, key.trapBody.getPosition().y + 8f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
						arrowHitbox.setUserData("RightArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(ARROW_SPEED, 0);
						arrowDirection = "Right";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, true));
						break;
					case 9:
						arrowBody = Arrow.createArrowBody(DungeonCrawler.world, key.trapBody.getPosition().x + 8f, key.trapBody.getPosition().y - 10.75f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(0, -ARROW_SPEED);
						arrowDirection = "Down";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, false));
						break;
					case 10:
						arrowBody = Arrow.createArrowBody(DungeonCrawler.world, key.trapBody.getPosition().x + 8f, key.trapBody.getPosition().y - 10.75f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setUserData("Arrow");
						arrowBody.setLinearVelocity(0, -ARROW_SPEED);
						arrowDirection = "Down";
						arrows.add(arrow = new Arrow(arrowBody, arrowDirection , 0f, true));
						break;

				}

				arrowArrayMap.put(arrowBody, arrow);
				arrowsToBeFired.removeKey(key);
			}
		}

			//check if there are any fired arrows
			if (!arrowArrayMap.isEmpty()) {
				for (OrderedMap.Entry<Body, Arrow> arrowEntry : arrowArrayMap.entries()) {
					Arrow value = arrowEntry.value;
					Body key = arrowEntry.key;

					value.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);
					value.stateTime2 += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

					//render each individual arrow
					arrowBatch.begin();
					flameBatch.begin();
					if (value.onFire) {
						TextureRegion flameFrame = tx.flameAnimation.getKeyFrame(value.stateTime2,true);
						TextureRegion currentFrame = tx.arrowAnimation.getKeyFrame(value.stateTime, true);
						Arrow.renderFireArrow(arrowBatch, currentFrame, flameFrame, arrowEntry.value.direction, key.getPosition().x, key.getPosition().y);
						value.createArrowFlameLight(value);
					}else {
						TextureRegion currentFrame = tx.arrowAnimation.getKeyFrame(value.stateTime, true);
						Arrow.renderArrow(arrowBatch, currentFrame, arrowEntry.value.direction, key.getPosition().x, key.getPosition().y);
					}

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
					//if the array map contains the arrow body that collided, remove that arrow from the game world
					if (arrowArrayMap.containsKey(collidedBody)) {
						//if the arrow is on fire we also need to remove the light before it is removed
						if (arrowArrayMap.get(collidedBody).onFire) {
							arrowArrayMap.get(collidedBody).destroyArrowFlameLight(arrowArrayMap.get(collidedBody).flameLight);
							arrowArrayMap.get(collidedBody).onFire = false;
						}
						//remove the sprite by removing the Arrow class object
						arrows.remove(arrowArrayMap.get(collidedBody));
						arrowArrayMap.removeKey(collidedBody);

						//remove the arrow Box2D body
						world.destroyBody(collidedBody);
						//remove body from arrowBodiesCollided
						bodyIt.remove();

					}
				}
			}


		//potParticleEffect.setPosition(camera.position.x, camera.position.y - 20);
		//potParticleEffect.update(potTime);
		boneParticlesBatch.begin();
		boneParticleEffect.draw(boneParticlesBatch,(Gdx.graphics.getDeltaTime() * TIME_SCALE));
		boneParticlesBatch.end();

		potParticlesBatch.begin();
		potParticleEffect.draw(potParticlesBatch, (Gdx.graphics.getDeltaTime() * TIME_SCALE));
		potParticlesBatch.end();


		/*
		potParticlesBatch.begin();

		for (Pot p : pots) {
			Pot.renderParticles(potParticlesBatch, p.potBody.getPosition().x, p.potBody.getPosition().y, p.particleEffect, p.particleTime);
		}

		potParticlesBatch.end();

		 */

		//potParticleEffect.reset();

		if (!potTimeUpdate) {
			potTimeUpdate = true;
			Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					//potTime += Gdx.graphics.getDeltaTime();
					//potTime = Gdx.graphics.getDeltaTime();
					//potParticleEffect.update(potTime);
					//potTimeUpdate = false;

				}
			}, 0.1f);
		}

			if (!potReset && potParticleEffect.isComplete()) {
				potReset = true;
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {

						potTime = 0;
						potReset = false;

						//potParticleEffect.reset();



					}
				}, 0.4f);
			}




		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {

			}
		}, 0.5f);

		/*
		if (potTime > Gdx.graphics.getDeltaTime() + 2f) {
			//potParticleEffect.allowCompletion();
			potTime = 0;

		}

		 */





		//render columnPieces based on their type - these could also be enums

		for (Column C : columns) {
			for (ColumnPiece c : C.columnPieces) {
				objectBounds.set(
						c.columnX,
						c.columnY,
						16,
						16
				);
				if (cameraBounds.overlaps(objectBounds)) {
					columnStemBatch.begin();
					switch (c.type) {
						case 4:

							//if (C.visible) {
							c.loweredAlpha = false;
							//}
							Column.renderPiece(columnStemBatch, tx.colStem, c.columnX, c.columnY, C.visible, c.alpha, C);
							break;
						case 5:
							if (C.visible) {
								c.loweredAlpha = false;
							}
							//columnStemBatch.draw(tx.colStemDamaged1, c.columnX, c.columnY);
							break;
						case 6:
							if (C.visible) {
								c.loweredAlpha = false;
							}
							//columnStemBatch.draw(tx.colStemDamaged2, c.columnX, c.columnY);
							break;
					}
					if (!c.loweredAlpha) {
						c.loweredAlpha = true;
						Timer.schedule(new Timer.Task() {
							@Override
							public void run() {
								if (!C.visible) {
									if (c.alpha > 35) {
										c.loweredAlpha = false;
									}
									//r.loweredAlpha = true;

									if (c.alpha >= 0)
										c.alpha--;
									c.alpha--;
								} else {
									if (c.alpha < 100 && c.alpha > 0) {
										c.loweredAlpha = false;
										c.alpha++;
										c.alpha++;
									} else if (c.alpha < 0) {

										c.alpha = 15;
										c.loweredAlpha = true;
									}
								}
							}
						}, 0.001f);
					}


					columnStemBatch.end();
					}
				}

		}
		for (Column C : columns) {
			for (ColumnPiece c : C.columnPieces) {
				objectBounds.set(
						c.columnX,
						c.columnY,
						16,
						16
				);
				if (cameraBounds.overlaps(objectBounds)) {
					columnTopBatch.begin();
					if (!c.loweredAlpha) {
						c.loweredAlpha = true;
						Timer.schedule(new Timer.Task() {
							@Override
							public void run() {
								if (!C.visible) {
									if (c.alpha > 35) {
										c.loweredAlpha = false;
									}
									//r.loweredAlpha = true;

									if (c.alpha >= 0)
										c.alpha--;
									c.alpha--;
									c.alpha--;
								} else {
									if (c.alpha < 100 && c.alpha > 0) {
										c.loweredAlpha = false;
										c.alpha++;
										c.alpha++;
									} else if (c.alpha < 0) {

										c.alpha = 15;
										c.loweredAlpha = true;
									}
								}
							}
						}, 0.001f);
					}

					switch (c.type) {
						case 1:
							if (C.visible) {
								c.loweredAlpha = false;
							}
							Column.renderPiece(columnTopBatch, tx.colTop1, c.columnX, c.columnY, C.visible, c.alpha, C);
							break;
						case 2:
							if (C.visible) {
								c.loweredAlpha = false;
							}

							Column.renderPiece(columnTopBatch, tx.colTop2, c.columnX, c.columnY, C.visible, c.alpha, C);
							break;
						case 3:
							if (C.visible) {
								c.loweredAlpha = false;
							}

							Column.renderPiece(columnTopBatch, tx.colTop3, c.columnX, c.columnY, C.visible, c.alpha, C);
							break;
						case 10:
							if (C.visible) {
								c.loweredAlpha = false;
							}

							Column.renderPiece(columnTopBatch, tx.colTop4, c.columnX, c.columnY, C.visible, c.alpha, C);
							break;
						case 11:
							if (C.visible) {
								c.loweredAlpha = false;
							}

							Column.renderPiece(columnTopBatch, tx.colTop5, c.columnX, c.columnY, C.visible, c.alpha, C);
							break;
					}

					columnTopBatch.end();
						}
					}

			}

		//wall arrow traps
		for (Trap tr : traps) {
			trapBatch.begin();
			if (!tr.active && tr.type == 3) {
				Trap.renderTrap(trapBatch, tr.direction, tr.trapBody.getPosition().x, tr.trapBody.getPosition().y, tr.type);
			} else if (tr.type == 3) {
				Trap.renderTrapActive(trapBatch, tr.direction, tr.trapBody.getPosition().x, tr.trapBody.getPosition().y, tr.type);
			} else if (!tr.active && tr.type == 4) {
				Trap.renderTrap(trapBatch, tr.direction, tr.trapBody.getPosition().x, tr.trapBody.getPosition().y -1, tr.type);
			} else if (tr.type == 4) {
				Trap.renderTrapActive(trapBatch, tr.direction, tr.trapBody.getPosition().x, tr.trapBody.getPosition().y-1, tr.type);
			}
			trapBatch.end();
		}

				flag_time += (Gdx.graphics.getDeltaTime() * TIME_SCALE);
				for (Flag f : flags) {

					objectBounds.set(
							f.flagX,
							f.flagY,
							16,
							16
					);
					//if (cameraBounds.overlaps(objectBounds)) {

						//shader variables passed into flag fragment shader
                    if (shadersEnabled) {
                        flagBatch.setShader(flagShader);
                        flagShader.setUniformf("u_swayIntensity", 0.05f);//0.03
                        flagShader.setUniformf("u_verticalDensity", 1f);
                        flagShader.setUniformf("u_time", flag_time + f.time);
                        flagShader.setUniformf("u_speed", 3.0f);
                        flagShader.setUniformf("u_alpha", f.alpha / 90);
                    } else {
                        flagBatch.setShader(null);
                    }



						flagBatch.begin();


						if (!f.loweredAlpha) {
							f.loweredAlpha = true;
							Timer.schedule(new Timer.Task() {
								@Override
								public void run() {
									if (!f.visible) {
										if (f.alpha > 50) {
											f.loweredAlpha = false;
										}
										//r.loweredAlpha = true;

										if (f.alpha >= 0)
											f.alpha--;
										f.alpha--;
										f.alpha--;
									} else {
										if (f.alpha < 100 && f.alpha > 0) {
											f.loweredAlpha = false;
											f.alpha++;
											f.alpha++;
										} else if (f.alpha < 0) {
											f.alpha = 15;
											f.loweredAlpha = true;
										}
									}
								}
							}, 0.001f);
						}

						if (f.visible) {
							f.loweredAlpha = false;
						}


						Flag.renderFlag(flagBatch, tx.flag1, f.flagBody.getPosition().x - 4.5f, f.flagBody.getPosition().y - 8, 9, 16, f.visible, f, f.alpha);
						flagBatch.end();
					//}
				}


				vine_time += (Gdx.graphics.getDeltaTime() * TIME_SCALE);
				for (Vine v : vines) {

					objectBounds.set(
							v.vineX,
							v.vineY,
							16,
							16
					);
					if (cameraBounds.overlaps(objectBounds)) {

						//shader variables passed into vine fragment shader
                        if (shadersEnabled) {
                            vineBatch.setShader(vineShader);
                            //vineShader.setUniformf("u_swayIntensity", 0.05f);//0.03
                            //vineShader.setUniformf("u_verticalDensity", 1f);
                            vineShader.setUniformf("u_time", vine_time + v.time);
                            //vineShader.setUniformf("u_speed", 1.6f);
                            vineShader.setUniformf("u_alpha", v.alpha / 90);
                        }
                        else {
                            vineBatch.setShader(null);
                        }


						vineBatch.begin();


						if (!v.loweredAlpha) {
							v.loweredAlpha = true;
							Timer.schedule(new Timer.Task() {
								@Override
								public void run() {
									if (!v.visible) {
										if (v.alpha > 50) {
											v.loweredAlpha = false;
										}

										if (v.alpha >= 0)
											v.alpha--;
										v.alpha--;
										v.alpha--;
									} else {
										if (v.alpha < 100 && v.alpha > 0) {
											v.loweredAlpha = false;
											v.alpha++;
											v.alpha++;
										} else if (v.alpha < 0) {
											v.alpha = 15;
											v.loweredAlpha = true;
										}
									}
								}
							}, 0.001f);
						}

						if (v.visible) {
							v.loweredAlpha = false;
						}


						Vine.renderVine(vineBatch, tx.vine1, v.vineBody.getPosition().x - 8f, v.vineBody.getPosition().y - 8, 16, 14, v.visible, v, v.alpha);
						vineBatch.end();
					}
				}




		for (Statue s : statues) {
			statueBatch.begin();

			if (!s.loweredAlpha) {
				s.loweredAlpha = true;
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						if (!s.visible) {
							if (s.alpha > 50) {
								s.loweredAlpha = false;
							}
							//r.loweredAlpha = true;

							if (s.alpha >= 0)
								s.alpha--;
							s.alpha--;
							s.alpha--;
						} else {
							if (s.alpha < 100 && s.alpha > 0) {
								s.loweredAlpha = false;
								s.alpha++;
								s.alpha++;
							} else if (s.alpha < 0) {
								s.alpha = 15;
								s.loweredAlpha = true;
							}
						}
					}
				}, 0.001f);
			}

			if (s.visible) {
				s.loweredAlpha = false;
			}

			switch  (s.type) {
				case 1:
					Statue.renderStatue(statueBatch, tx.statue1, s.statueBody.getPosition().x - 7.5f, s.statueBody.getPosition().y - 9.5f, 15, 19, s.visible, s, s.alpha);
					break;
				case 2:
					Statue.renderStatue(statueBatch, tx.statue2, s.statueBody.getPosition().x - 7.5f, s.statueBody.getPosition().y - 9.5f, 15, 19, s.visible, s, s.alpha);
					break;
                case 3:
                    Statue.renderStatue(statueBatch, tx.venus, s.statueBody.getPosition().x - 8.5f, s.statueBody.getPosition().y - 8f - 16, 16, 48, s.visible, s, s.alpha);
                    break;
			}


//statueBatch.draw(tx.statue1,s.statueX,s.statueY);
			statueBatch.end();


		}

        for (Brazier b : braziers) {
            if (b.type == 1) {
                brazierBatch.begin();

                if (!b.loweredAlpha) {
                    b.loweredAlpha = true;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (!b.visible) {
                                if (b.alpha > 50) {
                                    b.loweredAlpha = false;
                                }
                                //r.loweredAlpha = true;

                                if (b.alpha >= 0)
                                    b.alpha--;
                                b.alpha--;
                                b.alpha--;
                            } else {
                                if (b.alpha < 100 && b.alpha > 0) {
                                    b.loweredAlpha = false;
                                    b.alpha++;
                                    b.alpha++;
                                } else if (b.alpha < 0) {
                                    b.alpha = 15;
                                    b.loweredAlpha = true;
                                }
                            }
                        }
                    }, 0.001f);
                }

                if (b.visible) {
                    b.loweredAlpha = false;
                }

                Brazier.renderBrazier(brazierBatch,tx.brazier,b.brazierBody.getPosition().x - 8f, b.brazierBody.getPosition().y - 8f, 16, 16,b.visible,b,b.alpha);
                //brazierBatch.draw(tx.brazier, b.brazierBody.getPosition().x - 8f, b.brazierBody.getPosition().y - 8f, 16, 16);
                brazierBatch.end();
            }
        }

		stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

        fire_time += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

		//render fires and their animations based on their type and color
		for (Fire f : fires) {
            if (shadersEnabled) {
                fireBatch.setShader(fireShader);
                fireShader.setUniformf("u_swayIntensity", 0.1f);//0.03
                fireShader.setUniformf("u_verticalDensity", 10.000f);//10
                fireShader.setUniformf("u_time", fire_time + f.time);
                fireShader.setUniformf("u_speed", 6.0f);
                fireShader.setUniformf("u_alpha", f.alpha / 90f);
                fireShader.setUniformf("u_fixedBasePosY",0.4f);
            }
            else {
                fireBatch.setShader(null);
            }

					if ((f.type == 1 || f.type == 4) && (!f.smoking && f.active)) {
						currentFrame = tx.fireAnimation.getKeyFrame(stateTime, f.active);

						fireBatch.begin();

                        if (!f.loweredAlpha) {
                            f.loweredAlpha = true;
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    if (!f.visible) {
                                        if (f.alpha > 40) {
                                            f.loweredAlpha = false;
                                        }
                                        //r.loweredAlpha = true;

                                        if (f.alpha >= 0)
                                            f.alpha--;
                                        f.alpha--;
                                        f.alpha--;
                                    } else {
                                        if (f.alpha < 100 && f.alpha > 0) {
                                            f.loweredAlpha = false;
                                            f.alpha++;
                                            f.alpha++;
                                        } else if (f.alpha < 0) {
                                            f.alpha = 15;
                                            f.loweredAlpha = true;
                                        }
                                    }
                                }
                            }, 0.001f);
                        }

                        if (f.visible) {
                            f.loweredAlpha = false;
                        }

						if (f.upDown) {
							Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, 16,16,f.smoking, f.direction,f.visible,f,f.alpha);
						} else {
							Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, 16,16,f.smoking, f.direction,f.visible,f,f.alpha);
						}
						fireBatch.end();
					} else if (f.type == 2 && (!f.smoking && f.active)) {

						currentFrame = tx.blueFireAnimation.getKeyFrame(stateTime, f.active);

						fireBatch.begin();

                        if (!f.loweredAlpha) {
                            f.loweredAlpha = true;
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    if (!f.visible) {
                                        if (f.alpha > 50) {
                                            f.loweredAlpha = false;
                                        }
                                        //r.loweredAlpha = true;

                                        if (f.alpha >= 0)
                                            f.alpha--;
                                        f.alpha--;
                                        f.alpha--;
                                    } else {
                                        if (f.alpha < 100 && f.alpha > 0) {
                                            f.loweredAlpha = false;
                                            f.alpha++;
                                            f.alpha++;
                                        } else if (f.alpha < 0) {
                                            f.alpha = 15;
                                            f.loweredAlpha = true;
                                        }
                                    }
                                }
                            }, 0.001f);
                        }

                        if (f.visible) {
                            f.loweredAlpha = false;
                        }

						if (f.upDown) {
							Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY, 16,16, f.smoking, f.direction,f.visible,f,f.alpha);
						} else {
							Fire.renderFire(fireBatch, currentFrame, f.fireX, f.fireY,16,16, f.smoking, f.direction,f.visible,f,f.alpha);
						}
						fireBatch.end();
						}

			}
        waterfall_time += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

        for (Waterfall wf : waterfalls) {
            currentFrame = tx.waterfallAnimation.getKeyFrame(stateTime, true);

            if (shadersEnabled) {
                waterfallBatch.setShader(waterfallShader);

                waterfallShader.setUniformf("u_swayIntensity", 0.05f);//0.03 0.06
                waterfallShader.setUniformf("u_verticalDensity", 5.000f);//3
                waterfallShader.setUniformf("u_time", waterfall_time + wf.time);
                waterfallShader.setUniformf("u_speed", 8.0f);
                waterfallShader.setUniformf("u_alpha", wf.alpha / 90);
            } else {
                waterfallBatch.setShader(null);
            }

            waterfallBatch.begin();
            Waterfall.renderWater(waterfallBatch,currentFrame,wf.waterfallX,wf.waterfallY,16,16, wf.alpha);
            waterfallBatch.end();
        }

		for (BossMinotaur b1 : bossMinotaurs) {
			//if (b1.enteredBossRoom) {
			//	b1.detectPlayer();
			//}
			if (b1.facing == "Up") {
				currentFrame = tx.minotaurWalkUpAnimation.getKeyFrame(b1.stateTime, true);
				tx.minotaurTextureRegion = currentFrame;

			} else if (b1.facing == "Down") {
				currentFrame = tx.minotaurWalkDownAnimation.getKeyFrame(b1.stateTime, true);
				tx.minotaurTextureRegion = currentFrame;

			} else if (b1.facing == "Left") {
				currentFrame = tx.minotaurWalkLeftAnimation.getKeyFrame(b1.stateTime, true);
				tx.minotaurTextureRegion = currentFrame;

			} else if (b1.facing == "Right") {
				currentFrame = tx.minotaurWalkRightAnimation.getKeyFrame(b1.stateTime, true);
				tx.minotaurTextureRegion = currentFrame;

			} else if (!b1.active){
				currentFrame = tx.minotaurWalkDownAnimation.getKeyFrame(b1.stateTime, true);
				tx.minotaurTextureRegion = currentFrame;
			}
			if (pauseMenuClosed && optionsMenuClosed){

				bossMinotaurBatch.begin();

				BossMinotaur.renderMinotaur(bossMinotaurBatch, tx.minotaurTextureRegion, b1.enemyBody.getPosition().x - 16f, b1.enemyBody.getPosition().y - 8f);
				if (b1.stateTime < 1 && b1.active) {

					//get the minotaur's percentage of their total speed
					float tempX = b1.enemyAI.getLinearVelocity().x;
					tempX = tempX / b1.defaultSpeed * 100;
					tempX = Math.abs(tempX);

					float tempY = b1.enemyAI.getLinearVelocity().y;
					tempY = tempY / b1.defaultSpeed * 100;
					tempY = Math.abs(tempY);

					if (b1.facing == "Left" || b1.facing == "Right") {
						b1.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE) * ((tempX / 175));
					} else {
						b1.stateTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE) * ((tempY / 175));
					}

					if (b1.stunned) {

						currentFrame = tx.stunAnimation.getKeyFrame(stateTime4, true);

						Enemy.renderStatusEffect(bossMinotaurBatch, currentFrame, b1.enemyBody.getPosition().x - 7.5f, b1.enemyBody.getPosition().y + 45);

						stateTime4 += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

					} else {
						stateTime4 = 0;
					}

				} else {
					b1.stateTime = 0;
				}

				if (b1.active && !b1.charging) {
					if (!(b1.chargeTime > b1.chargeThreshold)) {
						b1.chargeTime += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

					} else {
						soundController.playSound("MinoCharge2",10f,10f,0.3f);

						b1.getStateMachine().changeState(BossMinotaurState.CHARGE_ATTACK);
						b1.chargeTime = 0;
					}
				}


				bossMinotaurBatch.end();

			} else {
				bossMinotaurBatch.begin();
                BossMinotaur.renderMinotaur(bossMinotaurBatch, tx.minotaurTextureRegion, b1.enemyBody.getPosition().x - 16f, b1.enemyBody.getPosition().y - 8f);
				bossMinotaurBatch.end();
			}
		}

		for (BossMinotaur deadMinotaur : dyingMinotaurs) {
			deadMinotaur.getStateMachine().changeState(BossMinotaurState.DIE);
			enemies.remove(deadMinotaur);
		}
		dyingMinotaurs.clear();

		//render corridor roofs - if they are touched by the player make them transparent
		for (Roof r : roofs) {

			if (r.type == 0) {
				roofBatch.begin();
				if (r.visible) {
					//roofBatch.setColor(1,1,1,1);
					if (r.upDown) {
						roofBatch.draw(tx.corridorRoofTexture, r.roofBody.getPosition().x - 32, r.roofBody.getPosition().y - 48, 64, 96);
					} else {
						roofBatch.draw(tx.corridorRoofTexture, r.roofBody.getPosition().x + 48, r.roofBody.getPosition().y - 32, 0, 0, 64, 96, 1, 1, 90);
					}
				} else {
					//roofBatch.setColor(1,1,1,0.30f);
					if (r.upDown) {
						roofBatch.draw(tx.corridorRoofTexture, r.roofBody.getPosition().x - 32, r.roofBody.getPosition().y - 48, 64, 96);
					} else {
						roofBatch.draw(tx.corridorRoofTexture, r.roofBody.getPosition().x + 48, r.roofBody.getPosition().y - 32, 0, 0, 64, 96, 1, 1, 90);
					}
				}
				roofBatch.end();
			}
		}

		//render roofs - if they are touched by the player make them transparent
		for (Room room : GenerateLevel.init.roomList) {
			for (Roof r : room.roofs) {
					roofBatch.begin();
				if (!r.loweredAlpha) {
					r.loweredAlpha = true;
					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							if (!r.visible) {
								if (r.alpha > 35) {
									r.loweredAlpha = false;
								}

								if (r.alpha >= 0)
								r.alpha--;
								r.alpha--;
							} else {
								if (r.alpha < 100 && r.alpha > 0) {
										r.loweredAlpha = false;
										r.alpha++;
										r.alpha++;
									} else if (r.alpha < 0) {

										r.alpha = 15;
										r.loweredAlpha = true;
									}
								}
						}
					}, 0.001f);
				}
				/*
				if (r.visible) {
					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
								if (r.alpha < 100) {
									r.alpha++;
									r.alpha++;
									r.alpha++;
								} else {
								}
						}
					}, 0.001f);

				}

				 */
					switch (r.type) {
						case 1:
							if (r.visible) {
								//r.alpha = 100;
								r.loweredAlpha = false;

								r.renderRoof(roofBatch, tx.roof3x3UpperTexture, r.roofBody.getPosition().x - 40, r.roofBody.getPosition().y + (r.ext * 8), 80, 32, r.visible, r.alpha, r);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {
										r.renderRoof(roofBatch, tx.roof3x3MiddleTexture, r.roofBody.getPosition().x - 40, r.roofBody.getPosition().y - 16 - (i * 16) + (r.ext * 8), 80, 16, r.visible, r.alpha, r);
									}
									r.renderRoof(roofBatch, tx.roof3x3LowerTexture, r.roofBody.getPosition().x - 40, r.roofBody.getPosition().y - (32 + (r.ext * 16)) + (r.ext * 8), 80, 32, r.visible, r.alpha, r);
								} else {
									r.renderRoof(roofBatch, tx.roof3x3LowerTexture, r.roofBody.getPosition().x - 40, r.roofBody.getPosition().y - (32 + (r.ext * 8)), 80, 32, r.visible, r.alpha, r);
								}
							} else {
								r.renderRoof(roofBatch, tx.roof3x3UpperTexture, r.roofBody.getPosition().x - 40, r.roofBody.getPosition().y + (r.ext * 8), 80, 32, r.visible, r.alpha, r);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {
										r.renderRoof(roofBatch, tx.roof3x3MiddleTexture, r.roofBody.getPosition().x - 40, r.roofBody.getPosition().y - 16 - (i * 16) + (r.ext * 8), 80, 16, r.visible, r.alpha, r);
									}
									r.renderRoof(roofBatch, tx.roof3x3LowerTexture, r.roofBody.getPosition().x - 40, r.roofBody.getPosition().y - (32 + (r.ext * 16)) + (r.ext * 8), 80, 32, r.visible, r.alpha, r);
								} else {
									r.renderRoof(roofBatch, tx.roof3x3LowerTexture, r.roofBody.getPosition().x - 40, r.roofBody.getPosition().y - (32 + (r.ext * 8)), 80, 32, r.visible, r.alpha, r);
								}
							}


							break;
						case 2:
							if (r.visible) {
								//r.alpha = 100;
								r.loweredAlpha = false;
								//roofBatch.setColor(1,1,1,1);
								r.renderRoof(roofBatch, tx.roof5x5UpperTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y + (r.ext * 8) + 8, 112, 32, r.visible, r.alpha, r);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {
										r.renderRoof(roofBatch, tx.roof5x5MiddleTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (32 + (i * 16)) + 24 + (r.ext * 8), 112, 16, r.visible, r.alpha, r);
									}
									r.renderRoof(roofBatch, tx.roof5x5LowerTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (64 + (r.ext * 16)) + 24 + (r.ext * 8), 112, 48, r.visible, r.alpha, r);
								} else {
									r.renderRoof(roofBatch, tx.roof5x5LowerTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (64 + (r.ext * 16)) + 24 + (r.ext * 8), 112, 48, r.visible, r.alpha, r);
								}
							} else {

								r.renderRoof(roofBatch, tx.roof5x5UpperTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y + (r.ext * 8) + 8, 112, 32, r.visible, r.alpha, r);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {
										r.renderRoof(roofBatch, tx.roof5x5MiddleTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (32 + (i * 16)) + 24 + (r.ext * 8), 112, 16, r.visible, r.alpha, r);
									}
									r.renderRoof(roofBatch, tx.roof5x5LowerTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (64 + (r.ext * 16)) + 24 + (r.ext * 8), 112, 48, r.visible, r.alpha, r);

								} else {
									r.renderRoof(roofBatch, tx.roof5x5LowerTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - 64 + 24 + (r.ext * 8), 112, 48, r.visible, r.alpha, r);
								}
							}

							break;
						case 3:
							if (r.visible) {
								//r.alpha = 100;
								r.loweredAlpha = false;

								r.renderRoof(roofBatch, tx.roof7x7UpperTexture, r.roofBody.getPosition().x - 72, r.roofBody.getPosition().y + (r.ext * 8) + 8, 144, 48, r.visible, r.alpha, r);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {

										r.renderRoof(roofBatch, tx.roof7x7MiddleTexture, r.roofBody.getPosition().x - 72, r.roofBody.getPosition().y - (72 + (i * 16)) + 64 + (r.ext * 8), 144,16, r.visible, r.alpha, r);
									}
									r.renderRoof(roofBatch, tx.roof7x7LowerTexture, r.roofBody.getPosition().x - 72, r.roofBody.getPosition().y - (80 + 40 + (r.ext * 16)) + 48 + 16 + (r.ext * 8), 144,64, r.visible, r.alpha, r);

								} else {
									r.renderRoof(roofBatch, tx.roof7x7LowerTexture, r.roofBody.getPosition().x - 72, r.roofBody.getPosition().y - 96 + 40 + (r.ext * 8), 144,64, r.visible, r.alpha, r);
								}
							} else {
								r.renderRoof(roofBatch, tx.roof7x7UpperTexture, r.roofBody.getPosition().x - 72, r.roofBody.getPosition().y + (r.ext * 8) + 8, 144, 48, r.visible, r.alpha, r);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {

										r.renderRoof(roofBatch, tx.roof7x7MiddleTexture, r.roofBody.getPosition().x - 72, r.roofBody.getPosition().y - (72 + (i * 16)) + 64 + (r.ext * 8), 144,16, r.visible, r.alpha, r);
									}
									r.renderRoof(roofBatch, tx.roof7x7LowerTexture, r.roofBody.getPosition().x - 72, r.roofBody.getPosition().y - (80 + 40 + (r.ext * 16)) + 48 + 16 + (r.ext * 8), 144,64, r.visible, r.alpha, r);
								} else {
										r.renderRoof(roofBatch, tx.roof7x7LowerTexture, r.roofBody.getPosition().x - 72, r.roofBody.getPosition().y - 96 + 40 + (r.ext * 8), 144,64, r.visible, r.alpha, r);
									}
							}
							break;
						case 4:
							if (r.visible) {
								//r.alpha = 100;
								r.loweredAlpha = false;
								roofBatch.draw(tx.roof11x11UpperTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y - 48, 208, 64);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {
										roofBatch.draw(tx.roof11x11MiddleTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y - (64 + (i * 16)), 208, 16);
									}
									roofBatch.draw(tx.roof11x11LowerTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y - (128 + (r.ext * 16)), 208, 80);
								} else {
									roofBatch.draw(tx.roof11x11LowerTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y - 128, 208, 80);
								}
							}
							break;
						case 5:
							if (r.visible) {
								//r.alpha = 100;
								r.loweredAlpha = false;
								roofBatch.draw(tx.roof15x15UpperTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y - 64, 272, 80);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {
										roofBatch.draw(tx.roof15x15MiddleTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y - (80 + (i * 16)), 272, 16);
									}
									roofBatch.draw(tx.roof15x15LowerTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y - (160 + (r.ext * 16)), 272, 96);
								} else {
									roofBatch.draw(tx.roof15x15LowerTexture, r.roofBody.getPosition().x, r.roofBody.getPosition().y - 160, 272, 96);
								}
							}
							break;
						case 7:
							if (r.visible) {
								//r.alpha = 100;
								r.loweredAlpha = false;
								//roofBatch.setColor(1,1,1,1);
								roofBatch.draw(tx.roof5x5UpperTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y + (r.ext * 8) + 8, 112, 32);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {
										roofBatch.draw(tx.roof5x5MiddleTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (32 + (i * 16)) + 24 + (r.ext * 8), 112, 16);
									}
									roofBatch.draw(tx.roof5x5LowerTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (64 + (r.ext * 16)) + 24 + (r.ext * 8), 112, 48);
								} else {
									roofBatch.draw(tx.roof5x5LowerTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - 64 + 24 + (r.ext * 8), 112, 48);
								}
							} else {
								//roofBatch.setColor(1,1,1,0.30f);
								roofBatch.draw(tx.roof5x5UpperTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y + (r.ext * 8) + 8, 112, 32);
								if (r.ext != 0) {
									for (int i = 0; i < r.ext; i++) {
										roofBatch.draw(tx.roof5x5MiddleTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (32 + (i * 16)) + 24 + (r.ext * 8), 112, 16);
									}
									roofBatch.draw(tx.roof5x5LowerTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - (64 + (r.ext * 16)) + 24 + (r.ext * 8), 112, 48);
								} else {
									roofBatch.draw(tx.roof5x5LowerTexture, r.roofBody.getPosition().x - 56, r.roofBody.getPosition().y - 64 + 24 + (r.ext * 8), 112, 48);
								}
							}

							break;
					}
					roofBatch.end();
			}
		}

			sightFontBatch.begin();
			for (DisplayText t : susMessages) {
				if (t.showing && t.fade) {
					FontController.drawFadingFont(sightFontBatch, defaultFont3, t.textX, t.textY, t, 1f);
				}
			}
			sightFontBatch.end();

			alertFontBatch.begin();
			for (DisplayText t2 : alertMessages) {
				if (t2.showing && t2.fade) {
					FontController.drawFadingFont(alertFontBatch, defaultFont4, t2.textX, t2.textY, t2, 1f);
				}
			}
			alertFontBatch.end();

			//render the inventory text for shops
			inventoryBatch.begin();
			for (Shopkeeper s : shopkeepers) {
				for (DisplayText t2 : s.inventoryText) {
					if (t2.showing) {
						FontController.drawInventoryFont(inventoryBatch, defaultFont2, t2.textX, t2.textY, t2);
					}
				}
			}
			inventoryBatch.end();







			//toggle to enable or disable visible collision boxes
			if (debug) {

				for (EnemySkull enemySkull : enemySkulls) {
					//renders ray cast rays
					if (enemySkull.rayCastable) {
						Ray<Vector2>[] rays = enemySkull.rayConfigurations[0].getRays();

						enemySkull.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
						enemySkull.shapeRenderer.setProjectionMatrix(camera.combined);
						enemySkull.shapeRenderer.setColor(1, 0, 0, 1);

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
					if (enemySpider.rayCastable) {
						//renders ray cast rays
						Ray<Vector2>[] rays = enemySpider.rayConfigurations[0].getRays();

						enemySpider.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
						enemySpider.shapeRenderer.setProjectionMatrix(camera.combined);
						enemySpider.shapeRenderer.setColor(1, 0, 0, 1);

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
				}

				for (EnemyGhost enemyGhost : enemyGhosts) {
					if (enemyGhost.rayCastable) {
						//renders ray cast rays
						Ray<Vector2>[] rays = enemyGhost.rayConfigurations[0].getRays();

						enemyGhost.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
						enemyGhost.shapeRenderer.setProjectionMatrix(camera.combined);
						enemyGhost.shapeRenderer.setColor(1, 0, 0, 1);

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
				}

				for (EnemyCyclops enemyEye : enemyEyes) {
					if (enemyEye.rayCastable) {
						//renders ray cast rays
						Ray<Vector2>[] rays = enemyEye.rayConfigurations[0].getRays();

						enemyEye.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
						enemyEye.shapeRenderer.setProjectionMatrix(camera.combined);
						enemyEye.shapeRenderer.setColor(1, 0, 0, 1);

						for (int i = 0; i < rays.length; i++) {
							Ray<Vector2> ray = rays[i];
							enemyEye.tmp.set(ray.start);
							enemyEye.tmp2.set(ray.end);
							enemyEye.shapeRenderer.line(enemyEye.tmp, enemyEye.tmp2);
						}

						//render player rayCasts to Enemies
						if (enemyEye.rayCastable) {
							enemyEye.tmp3.set((Vector2) enemyEye.playerDetectionRay.start);
							enemyEye.tmp4.set((Vector2) enemyEye.playerDetectionRay.end);
							enemyEye.shapeRenderer.line(enemyEye.tmp3, enemyEye.tmp4);
						}
						enemyEye.shapeRenderer.end();
					}
				}



                        /*
                        				if (!r.loweredAlpha) {
					r.loweredAlpha = true;
					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							if (!r.visible) {
								if (r.alpha > 35) {
									r.loweredAlpha = false;
								}

								if (r.alpha >= 0)
								r.alpha--;
								r.alpha--;
							} else {
								if (r.alpha < 100 && r.alpha > 0) {
										r.loweredAlpha = false;
										r.alpha++;
										r.alpha++;
									} else if (r.alpha < 0) {

										r.alpha = 15;
										r.loweredAlpha = true;
									}
								}
						}
					}, 0.001f);
				}
                         */



/*
                    if (cameraShapeAlpha == 0) {
                        cameraFading = false;

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {

                                for (int i = 0; i < 100; i++) {
                                    cameraShapeAlpha = i;
                                    cameraShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                                    cameraShapeRenderer.setColor(0f, 0f, 0f, cameraShapeAlpha);
                                    cameraShapeRenderer.rect(
                                            camera.position.x - camera.viewportWidth / 2f,
                                            camera.position.y - camera.viewportHeight / 2f,
                                            camera.viewportWidth,
                                            camera.viewportHeight
                                    );
                                    cameraShapeRenderer.end();
                                }

                        }, 0.5f);



                       }
                    }

 */





				//render all box2d debug collision fixtures
				b2dr.render(world, camera.combined);
			}





			//update the camera, HUD and render rays for lighting
			camera.update();
			hud.update();
			rayHandler.render();
			rayHandler.setCombinedMatrix(camera);



        if (roomTransition) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


            cameraShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            cameraShapeRenderer.setColor(0f, 0f, 0f, cameraShapeAlpha / 100);

            cameraShapeRenderer.rect(
                    camera.position.x - camera.viewportWidth / 2f,
                    camera.position.y - camera.viewportHeight / 2f,
                    camera.viewportWidth,
                    camera.viewportHeight
            );
        } else {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        if (cameraFading && roomTransition && !loweredCameraAlpha) {
            loweredCameraAlpha = true;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    cameraShapeAlpha++;
                    cameraShapeAlpha++;
                    cameraShapeAlpha++;
                    loweredCameraAlpha = false;
                }
            }, 0.001f);
            if (cameraShapeAlpha >= 100) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        String doorUpperLeft = (GenerateLevel.init.roomList.get(0).doorLocations.get("TopLeft"));
                        String[] doorUpperLeftXY = doorUpperLeft.split(",");
                        String doorUpperLeftX = doorUpperLeftXY[0].toString();

                        double doorUpperLeftXAsDouble = Float.parseFloat(doorUpperLeftX) * 1.65;
                        String test = String.valueOf(doorUpperLeftXAsDouble);
                        float doorUpperLeftXAsFloat = Float.parseFloat(test);
                        //player.playerBody.setTransform(player.playerBody.getPosition().x+20,player.playerBody.getPosition().y,0);

                        System.out.println("PLAYER ROOM FOR TRANSITION: " + player.currentRoom);

                        /*

                        go through the path list, get the room type, eg. 12
                        check the last collision, was it a up or right door
                        if up, find the next room's bottom door coords and move the player 8 units to the right



                         */

                        //player.playerBody.setTransform((doorUpperLeftXAsFloat * 10) + 20,player.playerBody.getPosition().y,0);

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                cameraFading = false;
                                loweredCameraAlpha = false;
                            }
                        },0.1f);

                    }
                }, 0.01f);
            }

        } else if (!cameraFading && roomTransition && !loweredCameraAlpha){
            loweredCameraAlpha = true;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    cameraShapeAlpha--;
                    cameraShapeAlpha--;
                    cameraShapeAlpha--;
                    loweredCameraAlpha = false;
                }
            }, 0.0001f);

            if (cameraShapeAlpha <= 0) {
                roomTransition = false;
            }
        }

        cameraShapeRenderer.end();








			//render all spritebatches in order
			rubbleBatch.setProjectionMatrix(camera.combined);
			waterBatch.setProjectionMatrix(camera.combined);
			waveBatch.setProjectionMatrix(camera.combined);
			trapBatch.setProjectionMatrix(camera.combined);
            drainBatch.setProjectionMatrix(camera.combined);
            rippleBatch.setProjectionMatrix(camera.combined);
            waterfallBatch.setProjectionMatrix(camera.combined);
			raisedFloorBatch.setProjectionMatrix(camera.combined);
			maskRenderer.setProjectionMatrix(camera.combined);
			obstacleBatch.setProjectionMatrix(camera.combined);
			candleBatch.setProjectionMatrix(camera.combined);
            lampBatch.setProjectionMatrix(camera.combined);
            brazierBatch.setProjectionMatrix(camera.combined);
            firePitBatch.setProjectionMatrix(camera.combined);
            vaseBatch.setProjectionMatrix(camera.combined);
			columnBaseLowerBatch.setProjectionMatrix(camera.combined);
			pedestalBatch.setProjectionMatrix(camera.combined);
			weaponBatch.setProjectionMatrix(camera.combined);
            torchBatch.setProjectionMatrix(camera.combined);
			playerBatch.setProjectionMatrix(camera.combined);
			arrowBatch.setProjectionMatrix(camera.combined);
			skullBatch.setProjectionMatrix(camera.combined);
			tutoBatch.setProjectionMatrix(camera.combined);
			webBatch.setProjectionMatrix(camera.combined);
			boneBatch.setProjectionMatrix(camera.combined);
			statueBatch.setProjectionMatrix(camera.combined);
			flagBatch.setProjectionMatrix(camera.combined);
            vineBatch.setProjectionMatrix(camera.combined);
			eyebeamBatch.setProjectionMatrix(camera.combined);
			enemySkullBatch.setProjectionMatrix(camera.combined);
			enemySpiderBatch.setProjectionMatrix(camera.combined);
			enemyGhostBatch.setProjectionMatrix(camera.combined);
			enemyEyeBatch.setProjectionMatrix(camera.combined);
            enemyCrabBatch.setProjectionMatrix(camera.combined);

			potParticlesBatch.setProjectionMatrix(camera.combined);
			boneParticlesBatch.setProjectionMatrix(camera.combined);

			lockBatch.setProjectionMatrix(camera.combined);
			doorBatch.setProjectionMatrix(camera.combined);
			potBatch.setProjectionMatrix(camera.combined);
			cobBatch.setProjectionMatrix(camera.combined);
			potionBatch.setProjectionMatrix(camera.combined);
			coinBatch.setProjectionMatrix(camera.combined);
			heartBatch.setProjectionMatrix(camera.combined);
			columnBaseBatch.setProjectionMatrix(camera.combined);
			columnStemBatch.setProjectionMatrix(camera.combined);
			columnTopBatch.setProjectionMatrix(camera.combined);
			fireBatch.setProjectionMatrix(camera.combined);
			flameBatch.setProjectionMatrix(camera.combined);
			bossMinotaurBatch.setProjectionMatrix(camera.combined);
			roofBatch.setProjectionMatrix(camera.combined);
			sightFontBatch.setProjectionMatrix(camera.combined);
			alertFontBatch.setProjectionMatrix(camera.combined);
			inventoryBatch.setProjectionMatrix(camera.combined);

			//render the HUD and menus last in order to display over everything else
			hudBatch.setProjectionMatrix(hud.stage.getCamera().combined);
			hud.stage.draw();
			hudBatch.setProjectionMatrix(hud.subStage.getCamera().combined);
			hud.subStage.draw();
			menuRenderer.setProjectionMatrix(camera.combined);
            cameraShapeRenderer.setProjectionMatrix(camera.combined);
			pauseMenuStage.draw();
            bossHealthbarStage.draw();
			//sliderBatch.draw();
			optionsMenuStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// We multiply the viewport height by the aspect ratio to maintain
		// correct proportions for objects when drawn
		float aspectRatio = (float) height / width;
		camera.viewportHeight = DEFAULT_VIEWPORT_WIDTH * aspectRatio;
		camera.viewportWidth = DEFAULT_VIEWPORT_WIDTH;
		camera.update();
		hud.stage.getViewport().update(width, height, true);
		hud.subStage.getViewport().update(width, height, true);
	}

	//update method for physics, camera and held down inputs
	public void update(float delta) {

		//update lighting
		rayHandler.setCombinedMatrix(camera);
		rayHandler.update();

		if (pauseMenuClosed && optionsMenuClosed) {
			//subtle fading in and out of light sources for added realism
			lightController.fadeLight(fires);
			//update the world
            //if (curse.cronos) {

            //} else {
                world.step(1 / (60f / TIME_SCALE), 6, 2);
                world.step(1 / (60f / TIME_SCALE), 6, 2);
            //}

			//allow player inputs
			allowPlayerInput = true;
			Gdx.input.setInputProcessor(gip);
			//make the menu invisible and remove the cursor
			pauseMenuContainer.setVisible(false);
			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);

			//move the compass to the correct position on the HUD and set rotation to door
			if (Compass.moving) {
				compassArrowImage.act((Gdx.graphics.getDeltaTime() * TIME_SCALE));
			}

			//each enemy updates their AI and attempts to detect the player
			//if in range

			for (EnemySkull e : enemySkulls) {
				if (e.active){
					e.enemyAI.update(GdxAI.getTimepiece().getTime());
                    if (e.rotateTime <= 0) {
                        e.update(GdxAI.getTimepiece().getTime());
                    } else {
                        e.rotateTime -= (Gdx.graphics.getDeltaTime() * TIME_SCALE);
                    }

				}
				if (e.playerInRange){
					e.detectPlayer();
				}
			}

			for (EnemySpider e2 : enemySpiders) {
				if (e2.active) {
					e2.enemyAI.update(GdxAI.getTimepiece().getTime());
					e2.update(GdxAI.getTimepiece().getTime());
				}
				if (e2.playerInRange){
					e2.detectPlayer();
				}
			}

			for (EnemyGhost e3 : enemyGhosts) {
				if (e3.active) {
					e3.enemyAI.update(GdxAI.getTimepiece().getTime());
					e3.update(GdxAI.getTimepiece().getTime());
				}
				if (e3.playerInRange){
					e3.detectPlayer();
				}
			}

			for (EnemyCyclops e4 : enemyEyes) {

                if (e4.active){
                    e4.enemyAI.update(GdxAI.getTimepiece().getTime());
                    if (e4.rotateTime <= 0) {
                        e4.update(GdxAI.getTimepiece().getTime());
                    } else {
                        e4.rotateTime -= (Gdx.graphics.getDeltaTime() * TIME_SCALE);
                    }

                }
                if (e4.playerInRange){
                    e4.detectPlayer();
                }
			}

            for (EnemyCrab e5 : enemyCrabs) {
                if (e5.active){
                    e5.enemyAI.update(GdxAI.getTimepiece().getTime());
                    if (e5.rotateTime <= 0) {
                        e5.update(GdxAI.getTimepiece().getTime());
                    } else {
                        e5.rotateTime -= (Gdx.graphics.getDeltaTime() * TIME_SCALE);
                    }

                }
                if (e5.playerInRange){
                    e5.detectPlayer();
                }
            }

			for (BossMinotaur b1 : bossMinotaurs) {
				b1.enemyAI.update(GdxAI.getTimepiece().getTime());
				b1.update(GdxAI.getTimepiece().getTime());
			}
		} else if (!pauseMenuClosed){
			//render menus with wireframes when in debug mode
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			menuRenderer.begin(ShapeRenderer.ShapeType.Filled);
			menuRenderer.setColor(new Color(0, 0, 0, 0.5f));
			menuRenderer.rect(vp.getScreenX(),vp.getScreenY(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			menuRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
			allowPlayerInput = false;
			Gdx.input.setInputProcessor(pauseMenuStage);
			pauseMenuContainer.setVisible(true);

			if (debug) {
				pauseMenuStage.setDebugAll(true);
			} else {
				pauseMenuStage.setDebugAll(false);
			}
		} else {
			//render menus with wireframes when in debug mode
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			menuRenderer.begin(ShapeRenderer.ShapeType.Filled);
			menuRenderer.setColor(new Color(0, 0, 0, 0.5f));
			menuRenderer.rect(vp.getScreenX(),vp.getScreenY(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			menuRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

			Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
			allowPlayerInput = false;
			Gdx.input.setInputProcessor(optionsMenuStage);
			optionsMenuContainer.setVisible(true);

			if (debug) {
				optionsMenuStage.setDebugAll(true);
			} else {
				optionsMenuStage.setDebugAll(false);
			}
		}

		//display susMessages when player starts a level, clears rooms, and finishes a level
		if (player.floorCleared){
			hud.fadeHUD(hud.winWords);
		} else if (player.roomCleared) {
            trapsActive = false;
			hud.winRoom();
			hud.fadeHUD(hud.roomWords);
		}
		if (player.playerInput) {
			hud.fadeHUD(hud.startWords);
		}

		//give the player a brighter light when the torch is bought in the shop
		if (player.hasTorch && !player.torchApplied) {
			player.playerLight.remove();
			player.playerLight = new PointLight(rayHandler, 1000, new Color(0.25f, 0.20f, 0, 0.85f), 80, PLAYER_X, PLAYER_Y);
			player.playerLight.attachToBody(player.playerBody);
			player.playerLight.setIgnoreAttachedBody(true);
			player.playerLight.setSoftnessLength(105f);
			player.torchApplied = true;
		}

		//set camera zoom
		if (!GenerateLevel.init.roomList.get(player.currentRoom).isShop && !debug && !optionsMenu.fullscreen) {
			//0.60
			camera.zoom = 0.65f;//0.60f
		} else if (optionsMenu.fullscreen && !debug) {
			camera.zoom = 0.85f;
		}
		else if (!debug){
			//camera.zoom = 0.8f;
			//0.9
			camera.zoom = 0.55f;
		} else {

		}

		//allow player movement when not paused, for example attacking with a weapon
		if (!playerPaused) {
			inputUpdate();
		}
	}

	@Override
	public void dispose() {

		playerBatch.dispose();
		hud.stage.dispose();
		inventoryBatch.dispose();
		arrowBatch.dispose();
		skullBatch.dispose();
		boneBatch.dispose();
		obstacleBatch.dispose();
		flameBatch.dispose();
		enemySkullBatch.dispose();
		enemySpiderBatch.dispose();
		enemyGhostBatch.dispose();
		enemyEyeBatch.dispose();
        enemyCrabBatch.dispose();
		bossMinotaurBatch.dispose();
		heartBatch.dispose();
		trapBatch.dispose();
		candleBatch.dispose();
        lampBatch.dispose();
		tutoBatch.dispose();
		webBatch.dispose();
		cobBatch.dispose();
		coinBatch.dispose();
		lockBatch.dispose();
		waterBatch.dispose();
		sightFontBatch.dispose();
		alertFontBatch.dispose();
		raisedFloorBatch.dispose();
		waveBatch.dispose();
		flagBatch.dispose();
		roofBatch.dispose();
		doorBatch.dispose();
		potBatch.dispose();
		statueBatch.dispose();
		potionBatch.dispose();
		columnTopBatch.dispose();
		columnStemBatch.dispose();
		columnBaseBatch.dispose();
		//potParticlesBatch.dispose();
		columnBaseLowerBatch.dispose();
		weaponBatch.dispose();
		pedestalBatch.dispose();
		rubbleBatch.dispose();
		eyebeamBatch.dispose();
		fireBatch.dispose();
		rayHandler.dispose();
		world.dispose();
		b2dr.dispose();
	}

	public void inputUpdate() {
		//manages player movement
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
			tx.playerHead = tx.playerHeadUp;
		} else if (player.facing == 3) {
			tx.playerTextureRegion = tx.playerDown;
			tx.playerHead = tx.playerHeadDown;
		} else if (player.facing == 4) {
			tx.playerTextureRegion = tx.playerLeft;
			tx.playerHead = tx.playerHeadLeft;
		} else if (player.facing == 2) {
			tx.playerTextureRegion = tx.playerRight;
			tx.playerHead = tx.playerHeadRight;
		}

		moveUp = false;
		moveDown = false;
		moveLeft = false;
		moveRight = false;


		PLAYER_HORIZONTAL_SPEED = 0;
		PLAYER_VERTICAL_SPEED = 0;

		if (debug) {
			if (Gdx.input.isKeyPressed(Keys.NUM_8)) {
				//enemySkulls.clear();
			}
		}

		//move playerSprite Sprite by delta speed according to button WASD press
		if (allowPlayerInput) {

			if (player.playerBody.getLinearVelocity().x > 0 ||
			player.playerBody.getLinearVelocity().y > 0
			|| player.playerBody.getLinearVelocity().x < 0
			|| player.playerBody.getLinearVelocity().y < 0) {


				if (!Compass.moving && Compass.showing) {
					Compass.fixRotation();
				}

				//create footstep sounds after set delays
				player.timeSinceMoved += (Gdx.graphics.getDeltaTime() * TIME_SCALE);

				float randomFootstepTime = Random.randomFloat(0.14f,0.14f);
				float randomSploshTime = Random.randomFloat(1.4f,0.4f);
				int randomFootstep = Random.randomInt(3,1);

				if (player.timeSinceMoved > randomSploshTime & player.sploshing) {
					player.sploshing = false;
				}

				String footstep = "Footstep" + randomFootstep;

				if (player.timeSinceMoved > randomFootstepTime && !player.swimming) {
					soundController.playSound(footstep,10,8,0.015f);
					player.timeSinceMoved = 0;
				} else if (player.swimming && !player.sploshing) {
					player.sploshing = true;
					soundController.playSound("WaterSplosh",8,6,0.03f);
					player.timeSinceMoved = 0;
				}
			} else {
				player.timeSinceMoved = 0;
			}

			//player movement controls and animation walk cycles

			if ((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP))) {
				PLAYER_VERTICAL_SPEED = 1f;
				leanUp = true;
				moveUp = true;
				player.facing = 1;
				if (player.playerBody.getLinearVelocity().x > 0.01 ||
						player.playerBody.getLinearVelocity().y > 0.01
						|| player.playerBody.getLinearVelocity().x < -0.01
						|| player.playerBody.getLinearVelocity().y < -0.01) {
					if (leanLeft){
						PLAYER_HORIZONTAL_SPEED = -1f;
						currentFrame = tx.playerWalkUpLeftAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUpLeft;
					} else if (leanRight) {
						PLAYER_HORIZONTAL_SPEED = 1f;
						currentFrame = tx.playerWalkUpRightAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUpRight;

					}
					else {
						currentFrame = tx.playerWalkUpAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUp;
					}

				} else {
					if (leanLeft){
						PLAYER_HORIZONTAL_SPEED = -1f;
						currentFrame = tx.playerUpLeftLean;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUpLeft;

					} else if (leanRight) {
						PLAYER_HORIZONTAL_SPEED = 1f;
						currentFrame = tx.playerUpRightLean;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUpRight;
					}
					else {
						currentFrame = tx.playerUp;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUp;
					}
				}
			}

			if ((Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT))) {
				PLAYER_HORIZONTAL_SPEED = -1f;
				leanLeft = true;
				moveLeft = true;
				player.facing = 4;
				if (player.playerBody.getLinearVelocity().x > 0.01 ||
						player.playerBody.getLinearVelocity().y > 0.01
						|| player.playerBody.getLinearVelocity().x < -0.01
						|| player.playerBody.getLinearVelocity().y < -0.01) {
					if (leanDown) {
						PLAYER_VERTICAL_SPEED = -1f;
						currentFrame = tx.playerWalkDownLeftAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDownLeft;
					} else if (leanUp) {
						PLAYER_VERTICAL_SPEED = 1f;
						currentFrame = tx.playerWalkUpLeftAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUpLeft;
					} else {
						currentFrame = tx.playerWalkLeftAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadLeft;
					}
				} else {
					if (leanDown) {
						PLAYER_VERTICAL_SPEED = -1f;
						currentFrame = tx.playerDownLeftLean;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDownLeft;
					} else if (leanUp) {
						PLAYER_VERTICAL_SPEED = 1f;
						currentFrame = tx.playerUpLeftLean;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUpLeft;
					} else {
						currentFrame = tx.playerLeft;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadLeft;
					}
				}
			}

			if ((Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN))) {
				PLAYER_VERTICAL_SPEED = -1f;
				leanDown = true;
				moveDown = true;
				player.facing = 3;
				if (player.playerBody.getLinearVelocity().x > 0.01 ||
						player.playerBody.getLinearVelocity().y > 0.01
						|| player.playerBody.getLinearVelocity().x < -0.01
						|| player.playerBody.getLinearVelocity().y < -0.01) {
					if (leanLeft) {
						PLAYER_HORIZONTAL_SPEED = -1f;
						currentFrame = tx.playerWalkDownLeftAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDownLeft;
					} else if (leanRight) {
						PLAYER_HORIZONTAL_SPEED = 1f;
						currentFrame = tx.playerWalkDownRightAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDownRight;
					} else {
						currentFrame = tx.playerWalkDownAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDown;
					}
				} else {
					if (leanLeft) {
						PLAYER_HORIZONTAL_SPEED = -1f;
						currentFrame = tx.playerDownLeftLean;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDownLeft;
					} else if (leanRight) {
						PLAYER_HORIZONTAL_SPEED = 1f;
						currentFrame = tx.playerDownRightLean;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDownRight;
					} else {
						currentFrame = tx.playerDown;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDown;
					}
				}
			}
			if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
				PLAYER_HORIZONTAL_SPEED = 1f;
				leanRight = true;
				moveRight = true;
				player.facing = 2;
				if (player.playerBody.getLinearVelocity().x > 0.01 ||
						player.playerBody.getLinearVelocity().y > 0.01
						|| player.playerBody.getLinearVelocity().x < -0.01
						|| player.playerBody.getLinearVelocity().y < -0.01) {
					if (leanDown) {
						PLAYER_VERTICAL_SPEED = -1f;
						currentFrame = tx.playerWalkDownRightAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDownRight;
					} else if (leanUp) {
						PLAYER_VERTICAL_SPEED = 1f;
						currentFrame = tx.playerWalkUpRightAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUpRight;
					} else {
						currentFrame = tx.playerWalkRightAnimation.getKeyFrame(stateTime3, true);
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadRight;
					}
				} else {
					if (leanDown) {
						PLAYER_VERTICAL_SPEED = -1f;
						currentFrame = tx.playerDownRightLean;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadDownRight;
					} else if (leanUp) {
						PLAYER_VERTICAL_SPEED = 1f;
						currentFrame = tx.playerUpRightLean;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadUpRight;
					} else {
						currentFrame = tx.playerRight;
						tx.playerTextureRegion = currentFrame;
						tx.playerHead = tx.playerHeadRight;
					}
				}

			}
			stateTime3 += (Gdx.graphics.getDeltaTime() * TIME_SCALE);
		}

		//create a player movement vector and normalize it for diagonal movement
		Vector2 vec = new Vector2(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
		vec.nor();

		//multiply to get desired speed
		PLAYER_HORIZONTAL_SPEED = vec.x * PLAYER_SPEED_MULTI;
		PLAYER_VERTICAL_SPEED = vec.y * PLAYER_SPEED_MULTI;

		player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

		//start the level by showing the level start message
		if (!player.playerInput) {
			hud.startLevel();
			player.playerInput = true;

		}
	}
}
