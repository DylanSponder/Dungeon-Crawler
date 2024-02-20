package com.mygdx.game;

import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.Arrow;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.behaviours.fsm.Enemy;
import com.mygdx.game.entity.behaviours.fsm.EnemyState;

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch batch, arrowBatch, hudBatch;
	public static World world;
	private boolean debug;
	private Box2DDebugRenderer b2dr;
	private Body sword, arrowBody;
	public static Player player;
	private Enemy enemy, enemy2;
	private EnemyState enemyState;
	private Arrow arrow;
	public ArrayList<Arrow> arrows;
	public ArrayList<Body> arrowBodiesCollided;
	public ArrayMap<Body, Arrow> arrowArrayMap;
	private Fixture swordHitbox, enemyHitbox, arrowHitbox;
	public boolean reversedArrowMap;
	private String playerDirection;
	private boolean playerPaused, playerMeleeAttacking, playerRangedAttacking;
	public float PLAYER_HORIZONTAL_SPEED = 0f, PLAYER_VERTICAL_SPEED = 0f;
	public float PLAYER_X = 0f, PLAYER_Y = 0f;
	public int ENEMY_HEALTH = 3;
	private TiledMapRenderer renderer;
	public static OrthographicCamera camera;
	public static final float DEFAULT_VIEWPORT_WIDTH = 300f;
	public static HUD hud;

	@Override
	public void create() {
		world = new World(new Vector2(0, 0f), false);
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		arrowBatch = new SpriteBatch();
		reversedArrowMap = false;
		player = new Player();
		final BodyFactory bf = new BodyFactory();
		ListenerClass lc = new ListenerClass();
		final CreateTexture tx = CreateTexture.getInstance();
		tx.textureRegionBuilder();

		//get width and height of the game window
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();

		//create camera and set the viewport
		camera = new OrthographicCamera(1000, 1000);
		camera.setToOrtho(false, w / 3, h / 3);

		Viewport vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);
    // Image healthSymbol = new Image(new Sprite(tx.heartTexture, 16, 16));
    Image moneySymbol = new Image(new Sprite(tx.coinTexture, 10, 10)); 
		hud = new HUD(vp, hudBatch, moneySymbol);

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

		player.createPlayer(world, PLAYER_X, PLAYER_Y);

		enemy = new Enemy( world, PLAYER_X, PLAYER_Y-160);

		//enemy2 = new Enemy( world, PLAYER_X, PLAYER_Y-200);

		//add current layers to the TileMap and assign it a renderer
		layers.add(layer);
		renderer = new OrthogonalTiledMapRenderer(map);
		b2dr = new Box2DDebugRenderer();

		// player = cr.createPlayer(world, PLAYER_X, PLAYER_Y);


		/*
		//enemy = bf.createEnemyBody(world,PLAYER_X,PLAYER_Y-90);
		//enemyHitbox = bf.createEnemyHitbox(enemy, 6, 5);
		enemyAI = new Box2DSteeringEntity(enemy, 10);
		final Arrive<Vector2> arriveSB = new Arrive<Vector2>(enemyAI, playerB2D)
				.setTimeToTarget(0.001f)
				.setArrivalTolerance(0f)
				.setDecelerationRadius(100);
		enemyAI.setBehaviour(arriveSB);

		final Wander<Vector2> wanderB = new Wander<>(enemyAI)
				.setWanderRadius(10f)
				.setWanderOrientation(1f);
		//enemyAI.setBehaviour(wanderB);



		RayConfiguration ray = new RayConfiguration() {
			@Override
			public Ray[] updateRays() {
				return new Ray[0];
			}
		};

		final RaycastObstacleAvoidance raycastB = new RaycastObstacleAvoidance(enemyAI)
				.setRayConfiguration(ray);
		//enemyAI.setBehaviour(raycastB);
	*/

		//set userdata for collision detection

		//

		arrowBodiesCollided = new ArrayList<Body>();
		arrowArrayMap = new ArrayMap<Body, Arrow>();
		arrows = new ArrayList<Arrow>();

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
					float playerMeleeAttackSpeedInSeconds = 0.5f;
					playerMeleeAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						tx.playerSprite = tx.playerAttackDown;
						sword = bf.createSwordBody(world,player.playerBody,-2.5f,-12f);
						sword.setUserData("Sword");
						swordHitbox = bf.createSwordHitbox(sword,false);
					} else if (tx.playerSprite.equals(tx.playerUp)) {
						tx.playerSprite = tx.playerAttackUp;
						sword = bf.createSwordBody(world,player.playerBody,-2.5f,15);
						sword.setUserData("Sword");
						swordHitbox = bf.createSwordHitbox(sword,false);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						sword = bf.createSwordBody(world,player.playerBody,-14f,-2.5f);
						sword.setUserData("Sword");
						swordHitbox = bf.createSwordHitbox(sword,true);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						sword = bf.createSwordBody(world,player.playerBody,14,-2.5f);
						sword.setUserData("Sword");
						swordHitbox = bf.createSwordHitbox(sword,true);
					} else {
						tx.playerSprite = tx.playerAttackDown;
						sword = bf.createSwordBody(world,player.playerBody,-2.5f,-12f);
						sword.setUserData("Sword");
						swordHitbox = bf.createSwordHitbox(sword,false);
					}

					//pause player in place while attacking (attacks must be timed correctly!)
					playerPaused = true;
					PLAYER_HORIZONTAL_SPEED = 0;
					PLAYER_VERTICAL_SPEED = 0;
					player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

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

				if (keycode == 66 && (!playerMeleeAttacking && !playerRangedAttacking)){
					float playerRangedAttackSpeedInSeconds = 0.4f;
					playerRangedAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x-2f,player.playerBody.getPosition().y-16f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,true);
						arrowBody.setLinearVelocity(0, -300f);
					}
					else if (tx.playerSprite.equals(tx.playerUp)) {
						playerDirection = "Up";
						tx.playerSprite = tx.playerAttackUp;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x-2f,player.playerBody.getPosition().y+16f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,true);
						arrowBody.setLinearVelocity(0, 300f);
					}
					else if (tx.playerSprite.equals(tx.playerLeft)) {
						playerDirection = "Left";
						tx.playerSprite = tx.playerAttackLeft;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x-16f,player.playerBody.getPosition().y);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,false);
						arrowBody.setLinearVelocity(-300f, 0);
					}
					else if (tx.playerSprite.equals(tx.playerRight)) {
						playerDirection = "Right";
						tx.playerSprite = tx.playerAttackRight;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x+16f,player.playerBody.getPosition().y);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,false);
						arrowBody.setLinearVelocity(300f, 0);
					}
					//only triggers if the player hasn't moved at all yet - player starts facing down
					else {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x-2f,player.playerBody.getPosition().y-16f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,true);
						arrowBody.setLinearVelocity(0, -300f);
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

		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				Fixture fa = contact.getFixtureA();
				Fixture fb = contact.getFixtureB();

				System.out.println(fa.getBody().getUserData()+" was hit with "+fb.getBody().getUserData());

				if ((fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Enemy")
						||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Arrow")
						||(fa.getBody().getUserData() == "Wall" && fb.getBody().getUserData() == "Arrow")
						||(fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Wall")
				){
					if (fa.getBody().getUserData() == "Enemy" && fa.getUserData() != "Proximity"
					|| fa.getBody().getUserData() == "Wall"){
							if (!arrowBodiesCollided.contains(fb.getBody())) {
								arrowBodiesCollided.add(fb.getBody());
							}
					}
					else if (fb.getBody().getUserData() == "Enemy" && fb.getUserData() != "Proximity"
							|| fb.getBody().getUserData() == "Wall") {
						if (!arrowBodiesCollided.contains(fa.getBody())) {
							arrowBodiesCollided.add(fa.getBody());
						}
					}

					if (fa.getBody().getUserData() == "Arrow" && fb.getUserData() == "EnemyHitbox"
					|| fb.getBody().getUserData() == "Arrow" && fa.getUserData() == "EnemyHitbox")
					{
						enemy.getStateMachine().changeState(EnemyState.ATTACK);
					}

				}
				if ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
						||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player")
				){
					if(fa.getUserData() == "Proximity"||
					fb.getUserData() == "Proximity"){
						enemy.getStateMachine().changeState(EnemyState.ATTACK);
					}
					else {
						if (fa.getBody().getUserData() == "Player"){
                hud.healthBar.LoseHealth(0.5f);
						}
					}
				}
			}
			@Override
			public void endContact(Contact contact) {
				Fixture fa = contact.getFixtureA();
				Fixture fb = contact.getFixtureB();

				if ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
						||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player")
				){
					if(fa.getUserData() == "Proximity"||
							fb.getUserData() == "Proximity"){
						enemy.getStateMachine().changeState(EnemyState.WANDER);
					}
				}
			}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}
		});
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
		camera.position.set(player.playerBody.getPosition().x + tx.playerSprite.getWidth() / 2, player.playerBody.getPosition().y + tx.playerSprite.getHeight() / 2, 0);

		batch.begin();
		//draw playerSprite on player Box2D object
		batch.draw(tx.playerSprite, player.playerBody.getPosition().x - 8f, player.playerBody.getPosition().y - 6f, 16, 16);
		if (playerMeleeAttacking) {
			//add the swordSprite to the corresponding attack playerDirection
			if (tx.playerSprite.equals(tx.playerAttackUp)) {
				batch.draw(tx.swordSprite, player.playerBody.getPosition().x - 13f, player.playerBody.getPosition().y - 3f, 7, 12, 7, 12, 1, 1, 180);
			} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
				batch.draw(tx.swordSprite, player.playerBody.getPosition().x - 6f, player.playerBody.getPosition().y - 18f, 7, 12, 7, 12, 1, 1, 0);
			} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
				batch.draw(tx.swordSprite, player.playerBody.getPosition().x - 15f, player.playerBody.getPosition().y - 18f, 7, 12, 7, 12, 1, 1, 270);
			} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
				batch.draw(tx.swordSprite, player.playerBody.getPosition().x + 1f, player.playerBody.getPosition().y - 11f, 7, 12, 7, 12, 1, 1, 90);
			}
		}
		if (playerRangedAttacking) {
			//add the bowSprite and arrowSprite to the corresponding attack playerDirection
			if (tx.playerSprite.equals(tx.playerAttackUp)) {
				batch.draw(tx.bowSprite, player.playerBody.getPosition().x - 11f, player.playerBody.getPosition().y - 4f, 8, 10, 14, 7, 1, 1, 180);
			} else if (tx.playerSprite.equals(tx.playerAttackDown)) {
				batch.draw(tx.bowSprite, player.playerBody.getPosition().x - 9f, player.playerBody.getPosition().y - 13f, 7, 12, 14, 7, 1, 1, 0);
			} else if (tx.playerSprite.equals(tx.playerAttackLeft)) {
				batch.draw(tx.bowSprite, player.playerBody.getPosition().x - 10f, player.playerBody.getPosition().y - 12f, 7, 12, 14, 7, 1, 1, 270);
			} else if (tx.playerSprite.equals(tx.playerAttackRight)) {
				batch.draw(tx.bowSprite, player.playerBody.getPosition().x - 4f, player.playerBody.getPosition().y - 12f, 7, 12, 14, 7, 1, 1, 90);
			}
		}
		batch.end();

		//render enemy sprite
		batch.begin();
		batch.draw(tx.enemySprite, enemy.enemyBody.getPosition().x - 8f, enemy.enemyBody.getPosition().y - 7f, 16, 16);
		batch.end();

		//check if there are any arrows
		if (!arrowArrayMap.isEmpty()) {
			for (OrderedMap.Entry<Body, Arrow> arrowEntry : arrowArrayMap.entries()) {
				Body key = arrowEntry.key;
				//render each individual arrow
				arrowBatch.begin();
				Arrow.renderArrow(arrowBatch, tx.arrowSprite, arrowEntry.value.direction, key.getPosition().x, key.getPosition().y);
				arrowBatch.end();
			}

			//arraymap order needs to be reversedArrowMap once (Collections.reverseOrder() method is not available with ArrayMaps)
			if (!reversedArrowMap){
				arrowArrayMap.reverse();
				reversedArrowMap = true;
			}

				Iterator<Body> bodyIt = arrowBodiesCollided.iterator();
				//iterate through every collided arrow
				if (bodyIt.hasNext()) {
					Body collidedBody = bodyIt.next();
					//if the arraymap contains the arrow body that collided, remove that arrow from the game
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


		//

		debug = true;

if (debug) {
	//renders all physics objects - for debug only
	b2dr.render(world,camera.combined);

	//renders raycast rays
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
	enemy.shapeRenderer.end();


	camera.update();
}



/*
if(enemy.debug) {
	enemy.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	enemy.shapeRenderer.setColor(Color.CORAL);

	//enemy.shapeRenderer.circle(wanderCenter.x, wanderCenter.y - enemy.enemyAI.getPosition().y, enemy.wanderSB.getWanderRadius(), 12);
	enemy.shapeRenderer.end();
	// Draw wander target
	enemy.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	enemy.shapeRenderer.setColor(Color.CORAL);
	Vector2 wanderTarget = (Vector2) enemy.wanderSB.getInternalTargetPosition();
	enemy.shapeRenderer.circle(wanderTarget.x, wanderTarget.y - enemy.enemyAI.getPosition().y, .1f, 6);
	enemy.shapeRenderer.end();

	enemy.shapeRenderer.setProjectionMatrix(camera.combined);
}

 */

    hud.update();

		batch.setProjectionMatrix(camera.combined);
		arrowBatch.setProjectionMatrix(camera.combined);
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

		enemy.enemyAI.update(GdxAI.getTimepiece().getTime());
		enemy.update(GdxAI.getTimepiece().getTime());
		//GdxAI.getTimepiece().update(delta);

		if (!playerPaused) {
			inputUpdate(delta);
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
    hud.stage.dispose();
		arrowBatch.dispose();
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
			PLAYER_VERTICAL_SPEED = 300f;
		}
		if (Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)) {
			tx.playerSprite = tx.playerLeft;
			PLAYER_HORIZONTAL_SPEED = -300f;
		}
		if (Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN)) {
			tx.playerSprite = tx.playerDown;
			PLAYER_VERTICAL_SPEED = -300f;
		}
		if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
			tx.playerSprite = tx.playerRight;
			PLAYER_HORIZONTAL_SPEED = 300f;
		}
		player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
	}
}
