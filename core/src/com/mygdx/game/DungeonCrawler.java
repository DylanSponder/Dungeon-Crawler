package com.mygdx.game;

import java.util.List;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
	public Sprite playerSprite;
	public Sprite playerUp;
	public Sprite playerDown;
	public Sprite playerLeft;
	public Sprite playerRight;
	public Sprite playerAttackUp;
	public Sprite playerAttackDown;
	public Sprite playerAttackLeft;
	public Sprite playerAttackRight;
	public Sprite swordSprite;
	public Sprite enemySprite;
	public Sprite heartSprite;
	private World world;
	private Box2DDebugRenderer b2dr;
	private Body player;
	private Body sword;
	private Fixture swordHitbox;
	private boolean pausePlayer;
	private boolean playerAttacking;
	public float PLAYER_X = 0f;
	public float PLAYER_Y = 0f;
	public float PLAYER_HORIZONTAL_SPEED = 0f;
	public float PLAYER_VERTICAL_SPEED = 0f;
	public int PLAYER_HEALTH = 12;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	public static final float DEFAULT_VIEWPORT_WIDTH = 300f;
	public static HUD hud;
	@Override
	public void create() {
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		world = new World(new Vector2(0, 0f), false);
		final CreateBody cr = new CreateBody();
		final CreateTexture tx = new CreateTexture();
		final CreateCell stc = new CreateCell();
		tx.textureRegionBuilder();

		//get width and height of the game window
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();

		//create camera and set the viewport
		camera = new OrthographicCamera(1000, 1000);
		camera.setToOrtho(false, w / 3, h / 3);

		playerSprite = new Sprite(tx.playerTexture, 0, 0, 16, 16);

		//outline player Sprites
		playerUp = new Sprite(tx.playerTexture, 16, 0, 16, 16);
		playerDown = new Sprite(tx.playerTexture, 0, 0, 16, 16);
		playerLeft = new Sprite(tx.playerTexture, 32, 0, 16, 16);
		playerRight = new Sprite(tx.playerTexture, 48, 0, 16, 16);
		playerAttackUp = new Sprite(tx.playerAttackTexture, 16, 0, 16, 16);
		playerAttackDown = new Sprite(tx.playerAttackTexture, 0, 0, 16, 16);
		playerAttackLeft = new Sprite(tx.playerAttackTexture, 32, 0, 16, 16);
		playerAttackRight = new Sprite(tx.playerAttackTexture, 48, 0, 16, 16);
		swordSprite = new Sprite(tx.swordTexture, 0, 0, 7, 12);
		//outline enemy sprites
		enemySprite = new Sprite(tx.enemyTexture,0,0,16,16);
		//outline HUD sprites
		heartSprite = new Sprite(tx.heartTexture, 16, 16);

		// TODO: Add hearts to HUD
		Viewport vp = new FitViewport(camera.viewportWidth, camera.viewportHeight);
		hud = new HUD(vp, hudBatch);

		//create an input processor to handle single input events - see inputUpdate() for held down inputs
		Gdx.input.setInputProcessor(new GameInputProcessor() {
			@Override
			public boolean scrolled(float amountX, float amountY) {
				if ((camera.zoom >= 0.3f && camera.zoom <= 1.3f)) {
					if (camera.zoom == 1.3f) {
						if (amountY < 0f) {camera.zoom += amountY * 0.02f;}
					} else if (camera.zoom == 0.3f) {
						if (amountY > 0f) {camera.zoom += amountY * 0.02f;}}
					else {camera.zoom += amountY * 0.02f;}
				}
				else if (camera.zoom > 1.3f) {camera.zoom = 1.3f;}
				else if (camera.zoom < 0.3f) {camera.zoom = 0.3f;}
				return true;
			}
			public boolean keyDown(int keycode) {
				if ((keycode == 62 || keycode == 66) && !playerAttacking) {
					float playerAttackSpeedInSeconds = 0.4f;
					playerAttacking = true;

					if (playerSprite.equals(playerDown)) {
						playerSprite = playerAttackDown;
						sword = cr.createSword(world,player,-2.5f,-12f);
						swordHitbox = cr.createSwordHitbox(sword,false);
					} else if (playerSprite.equals(playerUp)) {
						playerSprite = playerAttackUp;
						sword = cr.createSword(world,player,-2.5f,15);
						swordHitbox = cr.createSwordHitbox(sword,false);
					} else if (playerSprite.equals(playerLeft)) {
						playerSprite = playerAttackLeft;
						sword = cr.createSword(world,player,-14f,-2.5f);
						swordHitbox = cr.createSwordHitbox(sword,true);
					} else if (playerSprite.equals(playerRight)) {
						playerSprite = playerAttackRight;
						sword = cr.createSword(world,player,14,-2.5f);
						swordHitbox = cr.createSwordHitbox(sword,true);
					}

					//pause player in place while attacking (attacks must be timed correctly!)
					pausePlayer = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							//resume player movement after a short delay and remove sword hitbox
							pausePlayer = false;
							sword.destroyFixture(swordHitbox);

							//reset playerSprite to before the attack input
							if (playerSprite.equals(playerAttackDown)) {
								playerSprite = playerDown;
							} else if (playerSprite.equals(playerAttackUp)) {
								playerSprite = playerUp;
							} else if (playerSprite.equals(playerAttackLeft)) {
								playerSprite = playerLeft;
							} else if (playerSprite.equals(playerAttackRight)) {
								playerSprite = playerRight;
							}
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

		GenerateLevel level = new GenerateLevel();

		List list = level.generateLevel(world,PLAYER_X,PLAYER_Y);

		layer = (TiledMapTileLayer) list.get(0);
		PLAYER_X = (float) list.get(1);
		PLAYER_Y = (float) list.get(2);
		//add current layers to the TileMap and assign it a renderer
		layers.add(layer);
		renderer = new OrthogonalTiledMapRenderer(map);
		b2dr = new Box2DDebugRenderer();
		player = cr.createPlayer(world, PLAYER_X, PLAYER_Y);
	}

	@Override
	public void render() {
		//clear all assets and replace with background color
		ScreenUtils.clear(1, 1, 1, 1);

		//update game physics, camera and held down inputs
		update(Gdx.graphics.getDeltaTime());

		//clear graphics
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//set the view of the map to the camera and then render the map
		renderer.setView(camera);
		renderer.render();

		//set camera position to always be centred on the playerSprite
		camera.position.set(player.getPosition().x + playerSprite.getWidth() / 2, player.getPosition().y + playerSprite.getHeight() / 2, 0);


		batch.begin();
		//draw playerSprite on player Box2D object
		batch.draw(playerSprite, player.getPosition().x - 8f, player.getPosition().y - 6f, 16, 16);
		if (playerAttacking) {
			//add the swordSprite to the corresponding attack direction
			if (playerSprite.equals(playerAttackUp)) {
				batch.draw(swordSprite, player.getPosition().x - 13f, player.getPosition().y - 3f, 7, 12, 7, 12, 1, 1, 180);
			} else if (playerSprite.equals(playerAttackDown)) {
				batch.draw(swordSprite, player.getPosition().x - 6f, player.getPosition().y - 18f, 7, 12, 7, 12, 1, 1, 0);
			} else if (playerSprite.equals(playerAttackLeft)) {
				batch.draw(swordSprite, player.getPosition().x - 15f, player.getPosition().y - 18f, 7, 12, 7, 12, 1, 1, 270);
			} else if (playerSprite.equals(playerAttackRight)) {
				batch.draw(swordSprite, player.getPosition().x + 1f, player.getPosition().y - 11f, 7, 12, 7, 12, 1, 1, 90);
			}
		}
		batch.end();

		//renders all physics objects - for debug only
		b2dr.render(world,camera.combined);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
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
		// FIXME: For some reason this affects the scaling of the rest of the game...
		// hud.stage.getViewport().update(width, height);
	}

	//update method for physics, camera and held down inputs
	public void update(float delta) {
		world.step(1 / 60f, 6, 2);
		if (!pausePlayer) {
			inputUpdate(delta);
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		world.dispose();
		b2dr.dispose();
	}

	public void inputUpdate(float delta) {
		PLAYER_HORIZONTAL_SPEED = 0;
		PLAYER_VERTICAL_SPEED = 0;

		//move playerSprite Sprite by delta speed according to button WASD press
		if (Gdx.input.isKeyPressed(Keys.W)||Gdx.input.isKeyPressed(Keys.UP)) {
			playerSprite = playerUp;
			PLAYER_VERTICAL_SPEED = 100f;
		}
		if (Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)) {
			playerSprite = playerLeft;
			PLAYER_HORIZONTAL_SPEED = -100f;
		}
		if (Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN)) {
			playerSprite = playerDown;
			PLAYER_VERTICAL_SPEED = -100f;
		}
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
			playerSprite = playerRight;
			PLAYER_HORIZONTAL_SPEED = 100f;
		}
		player.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
	}
}