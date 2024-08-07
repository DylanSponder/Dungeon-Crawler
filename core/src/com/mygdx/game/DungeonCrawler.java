package com.mygdx.game;
import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mygdx.game.entity.behaviours.fsm.*;
import com.mygdx.game.entity.Shopkeeper;
import com.mygdx.game.level.GenerateLevel;
import com.mygdx.game.level.InitLevel;

public class DungeonCrawler extends ApplicationAdapter {
	private SpriteBatch batch, arrowBatch, hudBatch, skullBatch, boneBatch;
	public static World world;
	public static boolean debug = true;
	private Box2DDebugRenderer b2dr;
	public static Player player;
	private String playerDirection;
	private boolean playerPaused, playerMeleeAttacking, playerRangedAttacking;
	private Arrow arrow;
	public ArrayList<Arrow> arrows;
	public static ArrayList<Body> arrowBodiesCollided, boneBodiesCollided, skullBodiesDestroyed;
	public ArrayMap<Body, Arrow> arrowArrayMap;
	public ArrayMap<Body, Skull> skullArrayMap;
	public ArrayMap<Body, Bone> boneArrayMap;
	public ArrayMap<String, Lock> lockMap;
	public Body arrayMapSkullBody;
	public boolean reversedArrowMap;
	private Body sword, arrowBody;
	private Fixture swordHitbox, enemyHitbox, arrowHitbox;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Body> deadEnemyBodies;
	public static ArrayList<Skull> enemySkulls;
	public static ArrayList<Body> brokenSkullBodies;
	public static ArrayList<Bone> bones;
	public static ArrayList<Shopkeeper> shopkeepers;
	public static ArrayList<Tutorial> tutorial;
	public float PLAYER_HORIZONTAL_SPEED = 0f, PLAYER_VERTICAL_SPEED = 0f;
	public float PLAYER_X = 0f, PLAYER_Y = 0f;
	private TiledMapTileLayer layer;
	private TiledMapRenderer renderer;
	public static OrthographicCamera camera;
	public static final float DEFAULT_VIEWPORT_WIDTH = 300f;
	public static HUD hud;
	public boolean input, moving;

	@Override
	public void create() {
		world = new World(new Vector2(0, 0f), false);
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		arrowBatch = new SpriteBatch();
		skullBatch = new SpriteBatch();
		boneBatch = new SpriteBatch();
		reversedArrowMap = false;
		player = new Player();
		enemies = new ArrayList<>();
		deadEnemyBodies = new ArrayList<Body>();
		enemySkulls = new ArrayList<Skull>();
		brokenSkullBodies = new ArrayList<Body>();
		bones = new ArrayList<Bone>();
		shopkeepers = new ArrayList<>();
		tutorial = new ArrayList<>();

		final BodyFactory bf = new BodyFactory();
		final CreateTexture tx = CreateTexture.getInstance();
		GameContactListener lc = new GameContactListener();
		tx.textureRegionBuilder();

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

		//world.setContactListener(rlc);
		GenerateLevel level = new GenerateLevel();
		InitLevel initLevel = new InitLevel();
		initLevel.InitializeLevel();
		//level.initLevel();
		List list = level.generateLevel(0, 0);

		//List list = level.generateRoom(world, );

		layer = (TiledMapTileLayer) list.get(0);
		PLAYER_X = (float) list.get(1);
		PLAYER_Y = (float) list.get(2);
		player.createPlayer(world, PLAYER_X, PLAYER_Y);

		//add current layers to the TileMap and assign it a renderer
		layers.add(layer);
		renderer = new OrthogonalTiledMapRenderer(map);
		b2dr = new Box2DDebugRenderer();

		arrowBodiesCollided = new ArrayList<Body>();
		boneBodiesCollided = new ArrayList<Body>();
		skullBodiesDestroyed = new ArrayList<Body>();
		arrowArrayMap = new ArrayMap<Body, Arrow>();
		skullArrayMap = new ArrayMap<Body, Skull>();
		boneArrayMap = new ArrayMap<Body, Bone>();
		arrows = new ArrayList<Arrow>();

		//create an input processor to handle single input events - see inputUpdate() for held down inputs
		camera.zoom= 1f;
		Gdx.input.setInputProcessor(new GameInputProcessor() {
			@Override
			public boolean scrolled(float amountX, float amountY) {
				//camera zoom should be between 0.3 and 1.3 - may be changed during testing
				if ((camera.zoom >= 0.3f && camera.zoom <= 12f)) {
					if (camera.zoom == 12f) {
						if (amountY < 0f) {camera.zoom += amountY * 0.1f;}
					} else if (camera.zoom == 0.3f) {
						if (amountY > 0f) {camera.zoom += amountY * 0.1f;}}
					else {camera.zoom += amountY * 0.1f;}
				}
				else if (camera.zoom > 12f) {camera.zoom = 12f;}
				else if (camera.zoom < 0.3f) {camera.zoom = 0.3f;}
				return true;
			}
			public boolean keyDown(int keycode) {
				//TODO: make it 1
				//System.out.println("Keycode: "+ keycode);
				if (keycode == 8){

				}

				if ((keycode == 62) && (!playerMeleeAttacking && !playerRangedAttacking)) {
					float playerMeleeAttackSpeedInSeconds = 0.4f;
					playerMeleeAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						tx.playerSprite = tx.playerAttackDown;
						sword = bf.createSwordBody(world,player.playerBody,-2.5f,-12f);
						swordHitbox = bf.createSwordHitbox(sword,false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerUp)) {
						tx.playerSprite = tx.playerAttackUp;
						sword = bf.createSwordBody(world,player.playerBody,-2.5f,15);
						swordHitbox = bf.createSwordHitbox(sword,false);
						swordHitbox.setUserData("UpSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerLeft)) {
						tx.playerSprite = tx.playerAttackLeft;
						sword = bf.createSwordBody(world,player.playerBody,-14f,-2.5f);
						swordHitbox = bf.createSwordHitbox(sword,true);
						swordHitbox.setUserData("LeftSword");
						swordHitbox.setSensor(true);
					} else if (tx.playerSprite.equals(tx.playerRight)) {
						tx.playerSprite = tx.playerAttackRight;
						sword = bf.createSwordBody(world,player.playerBody,14,-2.5f);
						swordHitbox = bf.createSwordHitbox(sword,true);
						swordHitbox.setUserData("RightSword");
						swordHitbox.setSensor(true);
					} else {
						tx.playerSprite = tx.playerAttackDown;
						sword = bf.createSwordBody(world,player.playerBody,-2.5f,-12f);
						swordHitbox = bf.createSwordHitbox(sword,false);
						swordHitbox.setUserData("DownSword");
						swordHitbox.setSensor(true);
					}

					sword.setUserData("Sword");

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
					float playerRangedAttackSpeedInSeconds = 0.35f;
					playerRangedAttacking = true;

					if (tx.playerSprite.equals(tx.playerDown)) {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x-2f,player.playerBody.getPosition().y-16f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,true);
						arrowHitbox.setUserData("DownArrow");
						arrowBody.setLinearVelocity(0, -500f);
					}
					else if (tx.playerSprite.equals(tx.playerUp)) {
						playerDirection = "Up";
						tx.playerSprite = tx.playerAttackUp;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x-2f,player.playerBody.getPosition().y+16f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,true);
						arrowHitbox.setUserData("UpArrow");
						arrowBody.setLinearVelocity(0, 500f);
					}
					else if (tx.playerSprite.equals(tx.playerLeft)) {
						playerDirection = "Left";
						tx.playerSprite = tx.playerAttackLeft;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x-16f,player.playerBody.getPosition().y);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,false);
						arrowHitbox.setUserData("LeftArrow");
						arrowBody.setLinearVelocity(-500f, 0);
					}
					else if (tx.playerSprite.equals(tx.playerRight)) {
						playerDirection = "Right";
						tx.playerSprite = tx.playerAttackRight;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x+16f,player.playerBody.getPosition().y);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,false);
						arrowHitbox.setUserData("RightArrow");
						arrowBody.setLinearVelocity(500f, 0);
					}
					//only triggers if the player hasn't moved at all yet - player starts facing down
					else {
						playerDirection = "Down";
						tx.playerSprite = tx.playerAttackDown;
						arrowBody = Arrow.createArrowBody(world,player.playerBody.getPosition().x-2f,player.playerBody.getPosition().y-16f);
						arrowHitbox = Arrow.createArrowHitbox(arrowBody,true);
						arrowHitbox.setUserData("DownArrow");
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
		world.setContactListener(lc);
	}

	@Override
	public void render() {
    // kill game when playerhealth is 0
    if (hud.healthBar.currentHealth == 0) {
      Gdx.app.exit();
    }

    // WIN IF KILL
    if (enemies.isEmpty()) {
      hud.winnerWinnerChickenDinner();
    }
    
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

		for (Tutorial t : tutorial) {
			batch.begin();
			batch.draw(tx.tutorialTexture, t.tutorialBody.getPosition().x - 16f, t.tutorialBody.getPosition().y+7f, 96, 64);
			batch.end();
		}

			for (Skull s : enemySkulls) {
				if (!s.skullCreated) {
					arrayMapSkullBody = s.createSkull(skullArrayMap);
				}
				skullArrayMap.put(arrayMapSkullBody,s);
		}

		if (!skullArrayMap.isEmpty()) {
			for (OrderedMap.Entry<Body, Skull> skullEntry : skullArrayMap.entries()) {
				Body key = skullEntry.key;
				Skull value = skullEntry.value;
				//render each individual skull
				skullBatch.begin();
				if (value.SKULL_HEALTH < 1.5f){
					Skull.renderSkull(skullBatch, tx.damagedSkullSprite, key.getPosition().x, key.getPosition().y);
				}else {
					Skull.renderSkull(skullBatch, tx.skullSprite, key.getPosition().x, key.getPosition().y);
				}

				skullBatch.end();
			}

			Iterator<Skull> skullIt = enemySkulls.iterator();
			if (skullIt.hasNext()) {
				Skull skull = skullIt.next();
				if (brokenSkullBodies.contains(skull.skullBody)) {
					Bone bone = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, false, 0);
					bone.createBone();
					bones.add(bone);
					boneArrayMap.put(bone.boneBody, bone);

					Bone bone2 = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, true, bone.orientation);
					bone2.createBone();
					bones.add(bone2);
					boneArrayMap.put(bone2.boneBody, bone2);

					/* turns out 3 is just one too many bones - functionality still useful for other purposes
					Bone bone3 = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, true, bone2.orientation);
					bone3.createBone();
					bones.add(bone3);
					boneArrayMap.put(bone3.boneBody, bone3);
					 */

					world.destroyBody(skull.skullBody);
					skullIt.remove();
					enemySkulls.remove(skull);
					brokenSkullBodies.remove(skull);
					skullArrayMap.removeKey(skull.skullBody);
				}
			}
		}

		if (!boneArrayMap.isEmpty()) {
			for (OrderedMap.Entry<Body, Bone> boneEntry : boneArrayMap.entries()) {
				Body key = boneEntry.key;
				//Bone value = boneEntry.value;
				//render each individual bone
				boneBatch.begin();
				Bone.renderBone(boneBatch, tx.boneSprite, key.getPosition().x, key.getPosition().y, key.getAngle());
				boneBatch.end();

				//boneBatch.draw(tx.boneSprite, b.boneBody.getPosition().x - 7f, b.boneBody.getPosition().y - 8.5f, 7f, 8.5f, 16, 16, 1, 1, b.boneBody.getAngle() * 57.3f);
				//Skull.renderSkull(skullBatch, tx.skullSprite, key.getPosition().x, key.getPosition().y);
			}

			Iterator<Body> boneIt = boneBodiesCollided.iterator();
			if (boneIt.hasNext()) {
				Body boneBody = boneIt.next();
				if (boneArrayMap.containsKey(boneBody)) {
					boneArrayMap.removeKey(boneBody);
					boneIt.remove();
					world.destroyBody(boneBody);
				//	skullIt.remove();
				//	enemySkulls.remove(skull);
				//	brokenSkullBodies.remove(skull);
				//	skullArrayMap.removeKey(skull.skullBody);
					bones.remove(boneBody);
				}


			}
		}

		double radians = Math.PI/180;
		String stringR = String.valueOf(radians);
		float radiansF = Float.parseFloat(stringR);

	//	if (!enemySkulls.isEmpty()) {

	//	}


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
		for (Enemy e : enemies) {
			batch.begin();
			batch.draw(tx.enemySprite, e.enemyBody.getPosition().x - 8f, e.enemyBody.getPosition().y - 7f, 16, 16);
			batch.end();
		}

		for (Shopkeeper s : shopkeepers) {
			batch.begin();
			batch.draw(tx.shopkeeperSprite, s.shopBody.getPosition().x - 8f, s.shopBody.getPosition().y - 7f, 16, 16);
			batch.end();
		}

		//check if there are any arrows
		if (!arrowArrayMap.isEmpty()) {
			for (OrderedMap.Entry<Body, Arrow> arrowEntry : arrowArrayMap.entries()) {
				Body key = arrowEntry.key;
				//render each individual arrow
				arrowBatch.begin();
				Arrow.renderArrow(arrowBatch, tx.arrowSprite, arrowEntry.value.direction, key.getPosition().x, key.getPosition().y);
				arrowBatch.end();
			}

			//array map order needs to be reversedArrowMap once (Collections.reverseOrder() method is not available with ArrayMaps)
			if (!reversedArrowMap){
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
		for (Body body : deadEnemyBodies)
		{
			world.destroyBody(body);
		}



		deadEnemyBodies.clear();
		//brokenSkullBodies.clear();


		/*
		for (Enemy e: enemies){
			Iterator<Body> enemyIt = deadEnemyBodies.iterator();

			if (enemyIt.hasNext()) {
				//enemyIt.remove();
				//deadEnemyBodies.remove(enemy.enemyBody);
				enemyIt.remove();
				enemies.remove(e);

				//e.enemyBody.destroyFixture(e.enemyHitbox);
				break;
			}
		}

		 */

		//toggle to enable or disable collision boxes

		if (debug){
			for (Enemy enemy: enemies){
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
				enemy.shapeRenderer.end();
			}
			b2dr.render(world,camera.combined);

			//TODO Add debug button to Scene

		}

		camera.update();
		hud.update();

		batch.setProjectionMatrix(camera.combined);
		arrowBatch.setProjectionMatrix(camera.combined);
		skullBatch.setProjectionMatrix(camera.combined);
		boneBatch.setProjectionMatrix(camera.combined);
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

		for (Enemy e : enemies){
			e.enemyAI.update(GdxAI.getTimepiece().getTime());
			e.update(GdxAI.getTimepiece().getTime());
		}

		if (Gdx.input.isKeyPressed(Keys.ANY_KEY) && (!Gdx.input.getInputProcessor().keyUp(-1))) {
			input = true;
		}
		else {
			input = false;
		}

			if (input && ((!playerMeleeAttacking) && (!playerRangedAttacking))) {
		if (	   Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)
				|| Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)
				|| Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)
				|| Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))  {
					inputUpdate();
					moving = true;
				}
				else {
					moving = false;
				}

			}
		//input = true;
	}

	@Override
	public void dispose() {
		batch.dispose();
		hud.stage.dispose();
		arrowBatch.dispose();
		skullBatch.dispose();
		boneBatch.dispose();
		world.dispose();
		b2dr.dispose();
	}

	public void inputUpdate() {
		final CreateTexture tx = CreateTexture.getInstance();
		float inputDelay = 0.05f;
		if (moving && (!playerRangedAttacking || !playerMeleeAttacking)
		&&(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)
		|| Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)
		|| Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)
		|| Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))) {

			//move playerSprite Sprite by delta speed according to button WASD press
			if (Gdx.input.isKeyPressed(Keys.W)||Gdx.input.isKeyPressed(Keys.UP)) {
				tx.playerSprite = tx.playerUp;
			}
			if (Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)) {
				tx.playerSprite = tx.playerLeft;
			}
			if (Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN)) {
				tx.playerSprite = tx.playerDown;
			}
			if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)) {
				tx.playerSprite = tx.playerRight;
			}

				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						//resume player movement after a short delay and remove sword hitbox
						playerPaused = true;
						PLAYER_HORIZONTAL_SPEED = 0;
						PLAYER_VERTICAL_SPEED = 0;
					}
				}, inputDelay);
				playerPaused = false;

			//playerPaused = false;

			player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

			//move playerSprite Sprite by delta speed according to button WASD press
			if (Gdx.input.isKeyPressed(Keys.W)||Gdx.input.isKeyPressed(Keys.UP)
			&& Gdx.input.isKeyPressed(Keys.ANY_KEY) && moving) {
				PLAYER_VERTICAL_SPEED = 80f;
			}
			if (Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)
					&& Gdx.input.isKeyPressed(Keys.ANY_KEY) && moving) {
				PLAYER_HORIZONTAL_SPEED = -80f;
			}
			if (Gdx.input.isKeyPressed(Keys.S)||Gdx.input.isKeyPressed(Keys.DOWN)
					&& Gdx.input.isKeyPressed(Keys.ANY_KEY) && moving) {
				PLAYER_VERTICAL_SPEED = -80f;
			}
			if (Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)
					&& Gdx.input.isKeyPressed(Keys.ANY_KEY) && moving) {
				PLAYER_HORIZONTAL_SPEED = 80f;
			}

		}
		else if (playerRangedAttacking || playerMeleeAttacking) {
			PLAYER_HORIZONTAL_SPEED = 0;
			PLAYER_VERTICAL_SPEED = 0;
		}

		player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

    // Use potion 
    if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
      if (hud.inventory.Size > 0) {
        hud.inventory.usePotion(1);
        hud.healthBar.GainHealth(3);
      }
    }
 
    // (For Debugging) Add potion
    if (Gdx.input.isKeyPressed(Keys.NUM_9)) {
      hud.inventory.addPotion();
    }

    // (For Debugging) Damage player
    if (Gdx.input.isKeyPressed(Keys.NUM_0)) {
      hud.healthBar.LoseHealth(0.5f);
    }

    if (Gdx.input.isKeyPressed(Keys.NUM_8)) {
		for (Enemy e : enemies)
		if (!deadEnemyBodies.contains(e.enemyBody)) {
			deadEnemyBodies.add(e.enemyBody);
		}
		enemies.clear();
	}
	}
}
