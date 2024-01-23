package com.mygdx.game;

import java.util.List;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.mygdx.game.entity.CreateBody;

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	private World world;
	private Box2DDebugRenderer b2dr;
	private Body player;
	private Body sword;
	private Body bow;
	private Body enemy;
	private Body arrow;
	private Fixture swordHitbox;
	private Fixture bowHitbox;
	private Fixture enemyHitbox;
	private Fixture arrowHitbox;
	private boolean pausePlayer;
	private boolean playerMeleeAttacking;
	private boolean playerRangedAttacking;
	public float PLAYER_X = 0f;
	public float PLAYER_Y = 0f;
	public float PLAYER_HORIZONTAL_SPEED = 0f;
	public float PLAYER_VERTICAL_SPEED = 0f;
	public int PLAYER_HEALTH = 12;
	public int ENEMY_HEALTH = 3;
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
		ListenerClass lc = new ListenerClass();

		final CreateTexture tx = CreateTexture.getInstance();
		tx.textureRegionBuilder();

		//listen for Box2D collisions between entities
		world.setContactListener(lc);

		//get width and height of the game window
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();

		//create camera and set the viewport
		camera = new OrthographicCamera(1000, 1000);
		camera.setToOrtho(false, w / 3, h / 3);

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
				if ((keycode == 62) && (!playerMeleeAttacking && !playerRangedAttacking)) {
					float playerMeleeAttackSpeedInSeconds = 0.4f;
					playerMeleeAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						tx.playerSprite = tx.playerAttackDown;
						sword = cr.createSword(world,player,-2.5f,-12f);
						sword.setUserData("Sword");
						swordHitbox = cr.createSwordHitbox(sword,false);
					} else if (tx.playerSprite.equals(tx.playerUp)) {
						tx.playerSprite = tx.playerAttackUp;
						sword = cr.createSword(world,player,-2.5f,15);
						sword.setUserData("Sword");
						swordHitbox = cr.createSwordHitbox(sword,false);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						sword = cr.createSword(world,player,-14f,-2.5f);
						sword.setUserData("Sword");
						swordHitbox = cr.createSwordHitbox(sword,true);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						sword = cr.createSword(world,player,14,-2.5f);
						sword.setUserData("Sword");
						swordHitbox = cr.createSwordHitbox(sword,true);
					}
					else {
						tx.playerSprite = tx.playerAttackDown;
						sword = cr.createSword(world,player,-2.5f,-12f);
						sword.setUserData("Sword");
						swordHitbox = cr.createSwordHitbox(sword,false);
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

				if (keycode == 66 && (!playerMeleeAttacking && !playerRangedAttacking)){
					float playerRangedAttackSpeedInSeconds = 0.6f;
					playerRangedAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						tx.playerSprite = tx.playerAttackDown;
						bow = cr.createBow(world,player,-2f,-9.5f);
						bow.setUserData("Bow");
						bowHitbox = cr.createBowHitbox(bow,true);
						arrow = cr.createArrow(world,player.getPosition().x-2f,player.getPosition().y-16f);
						arrowHitbox = cr.createArrowHitbox(arrow,true);
						arrow.setLinearVelocity(0, -140f);
					}
					else if (tx.playerSprite.equals(tx.playerUp)) {
						tx.playerSprite = tx.playerAttackUp;
						bow = cr.createBow(world,player,-2f,12.5f);
						bow.setUserData("Bow");
						bowHitbox = cr.createBowHitbox(bow,true);
						arrow = cr.createArrow(world,player.getPosition().x-2f,player.getPosition().y+16f);
						arrowHitbox = cr.createArrowHitbox(arrow,true);
						arrow.setLinearVelocity(0, 140f);
					}
					else if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						bow = cr.createBow(world,player,-11.5f,0f);
						bow.setUserData("Bow");
						bowHitbox = cr.createBowHitbox(bow,false);
						arrow = cr.createArrow(world,player.getPosition().x-16f,player.getPosition().y);
						arrowHitbox = cr.createArrowHitbox(arrow,false);
						arrow.setLinearVelocity(-140f, 0);
					}
					else if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						bow = cr.createBow(world,player,11.5f,0f);
						bow.setUserData("Bow");
						bowHitbox = cr.createBowHitbox(bow,false);
						arrow = cr.createArrow(world,player.getPosition().x+16f,player.getPosition().y);
						arrowHitbox = cr.createArrowHitbox(arrow,false);
						arrow.setLinearVelocity(140f, 0);
					}
					else {
						tx.playerSprite = tx.playerAttackDown;
						bow = cr.createBow(world,player,-2f,-9.5f);
						bow.setUserData("Bow");
						bowHitbox = cr.createBowHitbox(bow,true);
						arrow = cr.createArrow(world,player.getPosition().x-2f,player.getPosition().y-16f);
						arrowHitbox = cr.createArrowHitbox(arrow,true);
						arrow.setLinearVelocity(0, -140f);
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
							bow.destroyFixture(bowHitbox);
							arrow.destroyFixture(arrowHitbox);

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
		PLAYER_HEALTH = 12;

		enemy = cr.createEnemy(world,PLAYER_X,PLAYER_Y-100);
		enemyHitbox = cr.createEnemyHitbox(enemy);

		//set userdata for collision detection
		player.setUserData("Player");
		enemy.setUserData("Enemy");
	}

	@Override
	public void render() {
		final CreateTexture tx = CreateTexture.getInstance();
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
		camera.position.set(player.getPosition().x + tx.playerSprite.getWidth() / 2, player.getPosition().y + tx.playerSprite.getHeight() / 2, 0);

		batch.begin();
		//draw playerSprite on player Box2D object
		batch.draw(tx.playerSprite, player.getPosition().x - 8f, player.getPosition().y - 6f, 16, 16);
		if (playerMeleeAttacking) {
			//add the swordSprite to the corresponding attack direction
			if (tx.playerSprite.equals(tx.playerAttackUp)) {
				batch.draw(tx.swordSprite, player.getPosition().x - 13f, player.getPosition().y - 3f, 7, 12, 7, 12, 1, 1, 180);
			} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
				batch.draw(tx.swordSprite, player.getPosition().x - 6f, player.getPosition().y - 18f, 7, 12, 7, 12, 1, 1, 0);
			} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
				batch.draw(tx.swordSprite, player.getPosition().x - 15f, player.getPosition().y - 18f, 7, 12, 7, 12, 1, 1, 270);
			} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
				batch.draw(tx.swordSprite, player.getPosition().x + 1f, player.getPosition().y - 11f, 7, 12, 7, 12, 1, 1, 90);
			}
		}
		if (playerRangedAttacking) {
			//add the bowSprite and arrowSprite to the corresponding attack direction
			if (tx.playerSprite.equals(tx.playerAttackUp)) {
				batch.draw(tx.bowSprite, player.getPosition().x - 11f, player.getPosition().y - 4f, 8, 10, 14, 7, 1, 1, 180);
				batch.draw(tx.arrowSprite,arrow.getPosition().x-10.5f,arrow.getPosition().y-3.5f,8,5,13,5,1,1,90);
			} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
				batch.draw(tx.bowSprite, player.getPosition().x - 9f, player.getPosition().y - 13f, 7, 12, 14, 7, 1, 1, 0);
				batch.draw(tx.arrowSprite,arrow.getPosition().x-10.5f,arrow.getPosition().y-11.5f,13,5,13,5,1,1,270);
			} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
				batch.draw(tx.bowSprite, player.getPosition().x - 10f, player.getPosition().y - 12f, 7, 12, 14, 7, 1, 1, 270);
				batch.draw(tx.arrowSprite,arrow.getPosition().x-19.5f,arrow.getPosition().y-7.5f,13,5,13,5,1,1,180);
			} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
				batch.draw(tx.bowSprite, player.getPosition().x - 4f, player.getPosition().y - 12f, 7, 12, 14, 7, 1, 1, 90);
				batch.draw(tx.arrowSprite,arrow.getPosition().x-6.5f,arrow.getPosition().y-2.5f,13,5,13,5,1,1,0);
			}
		}
		batch.end();

		//render enemy sprite
		batch.begin();
		batch.draw(tx.enemySprite,enemy.getPosition().x-8f,enemy.getPosition().y-7f,16,16);
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
		final CreateTexture tx = CreateTexture.getInstance();
		PLAYER_HORIZONTAL_SPEED = 0;
		PLAYER_VERTICAL_SPEED = 0;

		//move playerSprite Sprite by delta speed according to button WASD press
		if (Gdx.input.isKeyPressed(Keys.W)||Gdx.input.isKeyPressed(Keys.UP)) {
			tx.playerSprite = tx.playerUp;
			PLAYER_VERTICAL_SPEED = 100f;
		}
		if (Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)) {
			tx.playerSprite = tx.playerLeft;
			PLAYER_HORIZONTAL_SPEED = -100f;
		}
		if (Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN)) {
			tx.playerSprite = tx.playerDown;
			PLAYER_VERTICAL_SPEED = -100f;
		}
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
			tx.playerSprite = tx.playerRight;
			PLAYER_HORIZONTAL_SPEED = 100f;
		}
		player.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
	}
}