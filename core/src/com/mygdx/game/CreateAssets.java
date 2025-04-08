package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CreateAssets {

    public AssetManager assetManager;

    //TODO Make sure to link all textures to HellasDungeon path
    Texture heartHUDTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Heart.png"));
    Texture potionItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/LifePot.png"));
    Texture potionSlotTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/PotionSlot.png"));
    Texture torchItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Torch.png"));
    Texture torchSlotTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/TorchSlot.png"));
    Texture shieldItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Shield.png"));
    Texture shieldSlotTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/ShieldSlot.png"));
    Texture beltItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Belt.png"));
    Texture beltSlotTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/BeltSlot.png"));
    Texture chiselItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Chisel.png"));
    Texture chiselSlotTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/ChiselSlot.png"));
    Texture greekfireItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Greekfire.png"));
    Texture greekfireSlotTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/GreekfireSlot.png"));

    Texture compassTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Compass.png"));
    Texture compassArrowTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/CompassArrow.png"));

    Texture heartItemTexture = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/HeartItem.png"));
    Texture halfheartItemTexture = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/HalfHeartItem.png"));

    Texture coinItemTexture = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/CoinItem.png"));

    Texture coinHUDTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Coin2Preview.png"));
    Texture playerTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SpriteSheet.png"));
    Texture playerWalkUpAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkUp.png"));
    Texture playerWalkDownAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkDown.png"));
    Texture playerWalkLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkLeft.png"));
    Texture playerWalkRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkRight.png"));
    Texture playerWalkDownRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkDownRight.png"));
    Texture playerWalkDownLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkDownLeft.png"));
    Texture playerWalkUpLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkUpLeft.png"));
    Texture playerWalkUpRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkUpRight.png"));

    Texture playerAttackTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/Attack.png"));

    //Main texture sheet
    Texture roomBackground = new Texture(Gdx.files.internal("HellasDungeon/Level/Level 1/CustomTileset.png"));

    Texture swordTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Sword/SpriteInHand.png"));
    Texture bowTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/Sprite.png"));
    Texture arrowTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/Arrow.png"));
    Texture shieldTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Shield/Sprite.png"));
    Texture chiselTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Chisel/SpriteInHand.png"));
    Texture enemySkullTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySkull/SpriteSheet.png"));
    Texture enemySpiderTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySpider/SpriteSheet.png"));
    Texture enemyGhostTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGhost/SpriteSheet.png"));
    Texture enemyGhostAlertTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGhost/SpriteSheetAlerted.png"));
    Texture enemyEyeTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyCyclops/Eye.png"));
    Texture eyebeamTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyCyclops/Eyebeam.png"));

    Texture bossMinotaurTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/Minotaur.png"));

    Texture minotaurWalkUpAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/MinotaurWalkUp.png"));
    Texture minotaurWalkDownAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/MinotaurWalkDown.png"));
    Texture minotaurWalkLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/MinotaurWalkLeft.png"));
    Texture minotaurWalkRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/MinotaurWalkRight.png"));

    Texture stunAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/HUD/Stun.png"));

    Texture shopkeeperTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Shopkeeper/SpriteSheet.png"));
    Texture tutorialTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Tuto.png"));
    Texture fireAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Fire.png"));
    Texture flameAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Flame.png"));
    Texture blueFireAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FireBlu.png"));
    Texture blueFlameAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FlameBlu.png"));
    Texture smokeAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Smoke.png"));
    Texture flameSmokeAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FlameSmoke.png"));
    Texture fireOutAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FireOut.png"));
    Texture flameOutAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FlameOut.png"));
    Texture arrowAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/ArrowAnimation.png"));
    Texture columnsTextureSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Columns.png"));
    Texture potsSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Pots.png"));
    //Texture webTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySpider/WebSpit.png"));
    Texture arrowTrapTexture = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/ArrowTrap.png"));
    Texture fireArrowTrapTexture = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FireArrowTrap.png"));

    Texture roof3x3 = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Roofs/3x3.png"));
    Texture roof5x5 = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Roofs/5x5.png"));
    Texture roof7x7 = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Roofs/7x7.png"));
    Texture roof11x11 = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Roofs/11x11.png"));
    Texture roof15x15 = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Roofs/15x15.png"));

    Music level1Track = Gdx.audio.newMusic(Gdx.files.internal("HellasDungeon/Music/Level1Track.mp3"));

    public Sound potBreaking = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/potbreaking4.mp3"));
    public Sound skullBreaking = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/skullbreaking.mp3"));
    public Sound boneBreaking = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/bonebreaking3.mp3"));
    public Sound skullDeath = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/skulldeath.mp3"));
    public Sound spiderAttack = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/spiderattack.mp3"));
    public Sound spiderDeath = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/spiderdying2.mp3"));
    public Sound ghostDeath = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/ghostdying.mp3"));
    public Sound cyclopsDeath = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/eyedeath.mp3"));
    public Sound eyebeamAttack = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/eyebeam3.mp3"));

    public Sound playerHurt = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/playerhurt.mp3"));
    public Sound arrowHit = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/arrowhit.mp3"));
    public Sound swordHit = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/swordhit3.mp3"));
    public Sound swordSwing = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/swordswing.mp3"));
    public Sound swordSwing2 = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/swordswing2.mp3"));
    public Sound bowAttack = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/bowattack.mp3"));
    public Sound whoosh = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/whoosh.mp3"));
    public Sound bowAttack3 = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/bowattack3.mp3"));
    public Sound trapOpening = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/trapopens2.mp3"));
    public Sound trapClosing = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/trapcloses2.mp3"));

    public Sound chiselUsed = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/usechisel.mp3"));
    public Sound winePickup = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/pickupwine.mp3"));
    public Sound wineDrink = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/drinkpotion.mp3"));
    public Sound heartPickup = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/pickupheart.mp3"));
    public Sound doorOpening = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/dooropen.mp3"));
    public Sound doorClosing = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/doorclose.mp3"));

    public Sound footstep1 = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/footstep1.mp3"));
    public Sound footstep2 = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/footstep2.mp3"));
    public Sound footstep3 = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/footstep3.mp3"));

    public Sound coin = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/coin.mp3"));
    public Sound buy = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/buy.mp3"));
    public Sound shop = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/shop2.mp3"));

    public Sound fireAmbient = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/Fire.mp3"));

    //Texture font = new Texture(Gdx.files.internal("HellasDungeon/Font/GreekAlphabet.png"));
    //public TextureRegion fontTexture = new TextureRegion(font,0,0,16,16);

    //Floor textures

    public TextureRegion roomFloorTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomFloor2Texture = new TextureRegion(roomBackground, 256, 32, 16, 16);
    public TextureRegion roomFloor3Texture = new TextureRegion(roomBackground, 272, 32, 16, 16);
    public TextureRegion roomDecorativeFloorUpTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorDownTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorTopLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorTopRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorBottomLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorBottomRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);

    //Wall textures

    public TextureRegion roomLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopLeftTurnTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopRightTurnTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftTurnTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomRightTurnTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion obstacle1Texture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion obstacle2Texture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion obstacle3Texture = new TextureRegion(roomBackground, 0,0,16,16);

    //Fence textures

    public TextureRegion roomTopFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomLeftFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomRightFence = new TextureRegion(roomBackground, 0, 0, 16, 16);

    public TextureRegion roomTopLeftCornerFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopRightCornerFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftCornerFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomRightCornerFence = new TextureRegion(roomBackground, 0, 0, 16, 16);

    public TextureRegion roomTopLeftTurnFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopRightTurnFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftTurnFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomRightTurnFence = new TextureRegion(roomBackground, 0, 0, 16, 16);

    public TextureRegion roomTopLeftEndFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopRightEndFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftEndFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomRightEndFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomLeftUpEndFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomLeftDownEndFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomRightUpEndFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomRightDownEndFence = new TextureRegion(roomBackground, 0, 0, 16, 16);


    public TextureRegion amphoraeTexture = new TextureRegion(potsSheet, 0,0,16,16);
    public TextureRegion amphora2Texture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion damagedAmphoraTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion candleTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion candlesTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion cobwebTexture = new TextureRegion(roomBackground,0,0,16,16);

    //animations
    public TextureRegion minotaurWalkUpAnimationTexture = new TextureRegion(minotaurWalkUpAnimationSheet,0,0,32,64);
    public TextureRegion minotaurWalkDownAnimationTexture = new TextureRegion(minotaurWalkDownAnimationSheet,0,0,32,64);
    public TextureRegion minotaurWalkLeftAnimationTexture = new TextureRegion(minotaurWalkLeftAnimationSheet,0,0,32,64);
    public TextureRegion minotaurWalkRightAnimationTexture = new TextureRegion(minotaurWalkRightAnimationSheet,0,0,32,64);

    public Animation<TextureRegion> minotaurWalkUpAnimation = new Animation<TextureRegion>(1.5f, minotaurWalkUpAnimationTexture);
    public Animation<TextureRegion> minotaurWalkDownAnimation = new Animation<TextureRegion>(1.5f, minotaurWalkDownAnimationTexture);
    public Animation<TextureRegion> minotaurWalkLeftAnimation = new Animation<TextureRegion>(1.5f, minotaurWalkLeftAnimationTexture);
    public Animation<TextureRegion> minotaurWalkRightAnimation = new Animation<TextureRegion>(1.5f, minotaurWalkRightAnimationTexture);

    TextureRegion[][] minotaurWalkUpTextureArray = TextureRegion.split(minotaurWalkUpAnimationSheet,
            minotaurWalkUpAnimationSheet.getWidth() / 4,
            minotaurWalkUpAnimationSheet.getHeight() / 1);

    TextureRegion[] minotaurWalkUpFrames = new TextureRegion[4 * 1];

    TextureRegion[][] minotaurWalkDownTextureArray = TextureRegion.split(minotaurWalkDownAnimationSheet,
            minotaurWalkDownAnimationSheet.getWidth() / 4,
            minotaurWalkDownAnimationSheet.getHeight() / 1);

    TextureRegion[] minotaurWalkDownFrames = new TextureRegion[4 * 1];

    TextureRegion[][] minotaurWalkLeftTextureArray = TextureRegion.split(minotaurWalkLeftAnimationSheet,
            minotaurWalkLeftAnimationSheet.getWidth() / 4,
            minotaurWalkLeftAnimationSheet.getHeight() / 1);

    TextureRegion[] minotaurWalkLeftFrames = new TextureRegion[4 * 1];

    TextureRegion[][] minotaurWalkRightTextureArray = TextureRegion.split(minotaurWalkRightAnimationSheet,
            minotaurWalkRightAnimationSheet.getWidth() / 4,
            minotaurWalkRightAnimationSheet.getHeight() / 1);

    TextureRegion[] minotaurWalkRightFrames = new TextureRegion[4 * 1];



    public TextureRegion playerWalkUpAnimationTexture = new TextureRegion(playerWalkUpAnimationSheet,0,0,16,16);
    public TextureRegion playerWalkDownAnimationTexture = new TextureRegion(playerWalkDownAnimationSheet,0,0,16,16);
    public TextureRegion playerWalkLeftAnimationTexture = new TextureRegion(playerWalkLeftAnimationSheet,0,0,16,16);
    public TextureRegion playerWalkRightAnimationTexture = new TextureRegion(playerWalkRightAnimationSheet,0,0,16,16);

    public Animation<TextureRegion> playerWalkUpAnimation = new Animation<TextureRegion>(0.20f, playerWalkUpAnimationTexture);
    public Animation<TextureRegion> playerWalkDownAnimation = new Animation<TextureRegion>(0.20f, playerWalkDownAnimationTexture);
    public Animation<TextureRegion> playerWalkLeftAnimation = new Animation<TextureRegion>(0.20f, playerWalkLeftAnimationTexture);
    public Animation<TextureRegion> playerWalkRightAnimation = new Animation<TextureRegion>(0.20f, playerWalkRightAnimationTexture);

    TextureRegion[][] playerWalkUpTextureArray = TextureRegion.split(playerWalkUpAnimationSheet,
            playerWalkUpAnimationSheet.getWidth() / 1,
            playerWalkUpAnimationSheet.getHeight() / 4);

    TextureRegion[] playerWalkUpFrames = new TextureRegion[1 * 4];

    TextureRegion[][] playerWalkDownTextureArray = TextureRegion.split(playerWalkDownAnimationSheet,
            playerWalkDownAnimationSheet.getWidth() / 1,
            playerWalkDownAnimationSheet.getHeight() / 4);

    TextureRegion[] playerWalkDownFrames = new TextureRegion[1 * 4];

    TextureRegion[][] playerWalkLeftTextureArray = TextureRegion.split(playerWalkLeftAnimationSheet,
            playerWalkLeftAnimationSheet.getWidth() / 1,
            playerWalkLeftAnimationSheet.getHeight() / 4);

    TextureRegion[] playerWalkLeftFrames = new TextureRegion[1 * 4];

    TextureRegion[][] playerWalkRightTextureArray = TextureRegion.split(playerWalkRightAnimationSheet,
            playerWalkRightAnimationSheet.getWidth() / 1,
            playerWalkRightAnimationSheet.getHeight() / 4);

    TextureRegion[] playerWalkRightFrames = new TextureRegion[1 * 4];

    public Animation<TextureRegion> playerWalkUpLeftAnimation = new Animation<TextureRegion>(0.20f, playerWalkUpAnimationTexture);
    public Animation<TextureRegion> playerWalkUpRightAnimation = new Animation<TextureRegion>(0.20f, playerWalkDownAnimationTexture);
    public Animation<TextureRegion> playerWalkDownLeftAnimation = new Animation<TextureRegion>(0.20f, playerWalkLeftAnimationTexture);
    public Animation<TextureRegion> playerWalkDownRightAnimation = new Animation<TextureRegion>(0.20f, playerWalkRightAnimationTexture);

    TextureRegion[][] playerWalkUpLeftTextureArray = TextureRegion.split(playerWalkUpLeftAnimationSheet,
            playerWalkUpLeftAnimationSheet.getWidth() / 1,
            playerWalkUpLeftAnimationSheet.getHeight() / 4);

    TextureRegion[] playerWalkUpLeftFrames = new TextureRegion[1 * 4];

    TextureRegion[][] playerWalkDownLeftTextureArray = TextureRegion.split(playerWalkDownLeftAnimationSheet,
            playerWalkDownLeftAnimationSheet.getWidth() / 1,
            playerWalkDownLeftAnimationSheet.getHeight() / 4);

    TextureRegion[] playerWalkDownLeftFrames = new TextureRegion[1 * 4];

    TextureRegion[][] playerWalkUpRightTextureArray = TextureRegion.split(playerWalkUpRightAnimationSheet,
            playerWalkUpRightAnimationSheet.getWidth() / 1,
            playerWalkUpRightAnimationSheet.getHeight() / 4);

    TextureRegion[] playerWalkUpRightFrames = new TextureRegion[1 * 4];

    TextureRegion[][] playerWalkDownRightTextureArray = TextureRegion.split(playerWalkDownRightAnimationSheet,
            playerWalkDownRightAnimationSheet.getWidth() / 1,
            playerWalkDownRightAnimationSheet.getHeight() / 4);

    TextureRegion[] playerWalkDownRightFrames = new TextureRegion[1 * 4];

    public TextureRegion eyebeamAnimation1Texture = new TextureRegion(eyebeamTexture,0,0,64,8);
    //public TextureRegion eyebeamAnimation2Texture = new TextureRegion(eyebeamTexture,0,8,64,8);
    //public TextureRegion eyebeamAnimation3Texture = new TextureRegion(eyebeamTexture,0,16,64,8);
    //public TextureRegion eyebeamAnimation4Texture = new TextureRegion(eyebeamTexture,0,24,64,8);


    public Animation<TextureRegion> eyebeamAnimation = new Animation<TextureRegion>(0.20f, eyebeamAnimation1Texture);
 //   public Animation<TextureRegion> eyebeam2 = new Animation<TextureRegion>(0.20f, eyebeamAnimation2Texture);
  //  public Animation<TextureRegion> eyebeam3 = new Animation<TextureRegion>(0.20f, eyebeamAnimation3Texture);
  //  public Animation<TextureRegion> eyebeam4 = new Animation<TextureRegion>(0.20f, eyebeamAnimation4Texture);

  //  public Animation<TextureRegion> eyebeamAnimation = new Animation<TextureRegion>(0.25f, eyebeamAnimation1Texture);

    TextureRegion[][] eyebeamTextureArray = TextureRegion.split(eyebeamTexture,
            eyebeamTexture.getWidth() / 1,
            eyebeamTexture.getHeight() / 4);

    TextureRegion[] eyebeamFrames = new TextureRegion[1 * 4];



    public TextureRegion stunAnimationTexture = new TextureRegion(stunAnimationSheet,0,0,15,15);


    public Animation<TextureRegion> stunAnimation = new Animation<TextureRegion>(0.25f, stunAnimationTexture);

    TextureRegion[][] stunTextureArray = TextureRegion.split(stunAnimationSheet,
            stunAnimationSheet.getWidth() / 2,
            stunAnimationSheet.getHeight() / 1);

    TextureRegion[] stunFrames = new TextureRegion[2 * 1];


    public TextureRegion fireAnimationTexture = new TextureRegion(fireAnimationSheet,0,0,16,16);
    public TextureRegion flameAnimationTexture = new TextureRegion(flameAnimationSheet,0,0,16,16);
    public TextureRegion blueFlameAnimationTexture = new TextureRegion(blueFlameAnimationSheet,0,0,16,16);
    public TextureRegion blueFireAnimationTexture = new TextureRegion(blueFireAnimationSheet,0,0,16,16);
    public TextureRegion smokeAnimationTexture = new TextureRegion(smokeAnimationSheet,0,0,16,16);
    public TextureRegion flameSmokeAnimationTexture = new TextureRegion(flameSmokeAnimationSheet,0,0,16,16);
    public TextureRegion arrowAnimationTexture = new TextureRegion(fireAnimationSheet,0,0,16,16);
    public TextureRegion fireOutAnimationTexture = new TextureRegion(fireOutAnimationSheet,0,0,16,16);
    public TextureRegion flameOutAnimationTexture = new TextureRegion(flameOutAnimationSheet,0,0,16,16);

    public Animation<TextureRegion> fireAnimation = new Animation<TextureRegion>(0.25f, fireAnimationTexture);
    public Animation<TextureRegion> flameAnimation = new Animation<TextureRegion>(0.20f, flameAnimationTexture);
    public Animation<TextureRegion> blueFireAnimation = new Animation<TextureRegion>(0.25f, fireAnimationTexture);
    public Animation<TextureRegion> blueFlameAnimation = new Animation<TextureRegion>(0.20f, blueFlameAnimationTexture);

    public Animation<TextureRegion> flameSmokeAnimation = new Animation<TextureRegion>(0.25f, flameSmokeAnimationTexture);
    public Animation<TextureRegion> flameOutAnimation = new Animation<TextureRegion>(0.10f, flameOutAnimationTexture);

    public Animation<TextureRegion> smokeAnimation = new Animation<TextureRegion>(0.25f, smokeAnimationTexture);
    public Animation<TextureRegion> fireOutAnimation = new Animation<TextureRegion>(0.10f, fireOutAnimationTexture);


    TextureRegion[][] flameTextureArray = TextureRegion.split(flameAnimationSheet,
            flameAnimationSheet.getWidth() / 3,
            flameAnimationSheet.getHeight() / 2);

    TextureRegion[] flameFrames = new TextureRegion[3 * 2];

    TextureRegion[][] fireTextureArray = TextureRegion.split(fireAnimationSheet,
            fireAnimationSheet.getWidth() / 5,
            fireAnimationSheet.getHeight() / 2);

    TextureRegion[] fireFrames = new TextureRegion[5 * 2];

    TextureRegion[][] blueFireTextureArray = TextureRegion.split(blueFireAnimationSheet,
            blueFireAnimationSheet.getWidth() / 5,
            blueFireAnimationSheet.getHeight() / 2);

    TextureRegion[] blueFireFrames = new TextureRegion[5 * 2];

    TextureRegion[][] blueFlameTextureArray = TextureRegion.split(blueFlameAnimationSheet,
            blueFlameAnimationSheet.getWidth() / 3,
            blueFlameAnimationSheet.getHeight() / 2);

    TextureRegion[] blueFlameFrames = new TextureRegion[3 * 2];

    public Animation<TextureRegion> arrowAnimation = new Animation<TextureRegion>(1f,arrowAnimationTexture);

    TextureRegion[][] arrowTextureArray = TextureRegion.split(arrowAnimationSheet,
            arrowAnimationSheet.getWidth() / 8,
            arrowAnimationSheet.getHeight() / 1);

    TextureRegion[] arrowFrames = new TextureRegion[8 * 1];

    TextureRegion[][] smokeTextureArray = TextureRegion.split(smokeAnimationSheet,
            smokeAnimationSheet.getWidth() / 5,
            smokeAnimationSheet.getHeight() / 2);

    TextureRegion[] smokeFrames = new TextureRegion[5 * 2];

    TextureRegion[][] fireOutTextureArray = TextureRegion.split(fireOutAnimationSheet,
            fireOutAnimationSheet.getWidth() / 5,
            fireOutAnimationSheet.getHeight() / 4);

    TextureRegion[] fireOutFrames = new TextureRegion[5 * 4];

    TextureRegion[][] flameSmokeTextureArray = TextureRegion.split(flameSmokeAnimationSheet,
            flameSmokeAnimationSheet.getWidth() / 5,
            flameSmokeAnimationSheet.getHeight() / 2);

    TextureRegion[] flameSmokeFrames = new TextureRegion[5 * 2];

    TextureRegion[][] flameOutTextureArray = TextureRegion.split(flameOutAnimationSheet,
            flameOutAnimationSheet.getWidth() / 3,
            flameOutAnimationSheet.getHeight() / 4);

    TextureRegion[] flameOutFrames = new TextureRegion[3 * 4];


    //TODO: this is lazy - refactor
    //edit: my depravity knows no bounds
    int index = 0, index2 = 0, index3 = 0, index4 = 0, index5 = 0, index6 = 0, index7 = 0, index8 = 0, index9 = 0, index10 = 0;
    int index11 = 0, index12 = 0, index13 = 0, index14 = 0, index15 = 0, index16 = 0, index17 = 0, index18 = 0, index19 = 0, index20 = 0, index21 = 0;
    int index22 = 0, index23 = 0;

    //Column textures

    public TextureRegion colTop1 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colTop2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colTop3 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colTop4 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colTop5 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colStem = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colStem2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colStem3 = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion colStemDamaged1 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colStemDamaged2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBaseLower = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase2Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion pedestal1 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal3 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal4 = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    //Roof textures

    public TextureRegion corridorRoofTexture = new TextureRegion(roomBackground,0,0,64,96);
    public TextureRegion ruinedCorridorRoofTexture = new TextureRegion(roomBackground,0,0,64,96);

    public TextureRegion roof3x3UpperTexture = new TextureRegion(roof3x3,0,0,80,32);
    public TextureRegion roof3x3MiddleTexture = new TextureRegion(roof3x3,80,32,80,16);
    public TextureRegion roof3x3LowerTexture = new TextureRegion(roof3x3,0,48,80,32);

    public TextureRegion roof5x5UpperTexture = new TextureRegion(roof5x5,0,0,112,32);
    public TextureRegion roof5x5MiddleTexture = new TextureRegion(roof5x5,96,32,112,16);
    public TextureRegion roof5x5LowerTexture = new TextureRegion(roof5x5,0,48,112,48);

    public TextureRegion roof7x7UpperTexture = new TextureRegion(roof7x7,0,0,144,48);
    public TextureRegion roof7x7MiddleTexture = new TextureRegion(roof7x7,128,48,144,16);
    public TextureRegion roof7x7LowerTexture = new TextureRegion(roof7x7,0,64,144,64);

    public TextureRegion roof11x11UpperTexture = new TextureRegion(roof11x11,0,0,208,64);
    public TextureRegion roof11x11MiddleTexture = new TextureRegion(roof11x11,192,64,208,16);
    public TextureRegion roof11x11LowerTexture = new TextureRegion(roof11x11,0,80,208,80);

    public TextureRegion roof15x15UpperTexture = new TextureRegion(roof15x15,0,0,272,80);
    public TextureRegion roof15x15MiddleTexture = new TextureRegion(roof15x15,272,80,272,16);
    public TextureRegion roof15x15LowerTexture = new TextureRegion(roof15x15,0,96,272,96);

    //Level object textures

    public TextureRegion skullTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion boneTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion tutoTexture = new TextureRegion(tutorialTexture, 0,0,87,57);

    public TextureRegion torchLeftTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion torchRightTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion torchUpTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion torchDownTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion arrowTrap = new TextureRegion(arrowTrapTexture, 0,0,16,16);
    public TextureRegion arrowTrapActivated = new TextureRegion(arrowTrapTexture, 16,0,16,17);

    public TextureRegion fireArrowTrap = new TextureRegion(fireArrowTrapTexture, 0,0,16,21);
    public TextureRegion fireArrowTrapActivated = new TextureRegion(fireArrowTrapTexture, 16,0,16,22);

    public TextureRegion lockUpTexture = new TextureRegion(roomBackground, 160,64,16,16);
    public TextureRegion lockDownTexture = new TextureRegion(roomBackground, 192,32,16,16);
    public TextureRegion lockLeftTexture = new TextureRegion(roomBackground, 192,16,16,16);
    public TextureRegion lockRightTexture = new TextureRegion(roomBackground, 192,0,16,16);

    //Door textures

    public TextureRegion doorTopLeftWallTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorTopRightWallTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorLeftUpperWallTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorLeftLowerWallTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorRightUpperWallTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorRightLowerWallTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorBottomLeftWallTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorBottomRightWallTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion doorTopLeftTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorTopRightTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorTopLeftOpenTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorTopRightOpenTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion doorLeftUpperTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorLeftLowerTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorLeftUpperOpenTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorLeftLowerOpenTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion doorRightUpperTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorRightLowerTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorRightUpperOpenTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorRightLowerOpenTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion doorBottomLeftTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorBottomRightTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorBottomLeftOpenTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion doorBottomRightOpenTexture = new TextureRegion(roomBackground, 0,0,16,16);


    //TextureRegion doorTexture = new TextureRegion(roomDoorTexture, 0, 0, 16, 16);
    //TextureRegion holeTexture = new TextureRegion(roomHoleTexture, 0, 0, 16, 16);

    //outline player sprites
    Sprite playerSprite = new Sprite(playerTexture, 0, 0, 16, 16);
    public TextureRegion playerTextureRegion = new TextureRegion(playerTexture, 0, 0, 16, 16);
    Sprite playerUp = new Sprite(playerTexture, 16, 0, 16, 16);
    Sprite playerDown = new Sprite(playerTexture, 0, 0, 16, 16);
    Sprite playerLeft = new Sprite(playerTexture, 32, 0, 16, 16);
    Sprite playerRight = new Sprite(playerTexture, 48, 0, 16, 16);

    Sprite playerUpRightLean = new Sprite(playerTexture, 0, 64, 16, 16);
    Sprite playerUpLeftLean = new Sprite(playerTexture, 16, 64, 16, 16);
    Sprite playerDownLeftLean = new Sprite(playerTexture, 32, 64, 16, 16);
    Sprite playerDownRightLean = new Sprite(playerTexture, 48, 64, 16, 16);

    Sprite playerAttackUp = new Sprite(playerAttackTexture, 16, 0, 16, 16);
    Sprite playerAttackDown = new Sprite(playerAttackTexture, 0, 0, 16, 16);
    Sprite playerAttackLeft = new Sprite(playerAttackTexture, 32, 0, 16, 16);
    Sprite playerAttackRight = new Sprite(playerAttackTexture, 48, 0, 16, 16);

    //Weapon sprites

    Sprite swordSprite = new Sprite(swordTexture, 0, 0, 7, 14);
    Sprite bowSprite = new Sprite(bowTexture,0,0,19,8);
    Sprite shieldSprite = new Sprite(shieldTexture,0,0, 16, 8);
    Sprite chiselSprite = new Sprite(chiselTexture,0,0, 7, 14);
    Sprite arrowSprite = new Sprite(arrowTexture,0,0,13,5);

    //Enemy sprites

    Sprite enemySkullSprite = new Sprite(enemySkullTexture,0,0,16,16);
    Sprite enemySkullAlertedSprite = new Sprite(enemySkullTexture,16,0,16,16);

    Sprite enemySpiderUpSprite = new Sprite(enemySpiderTexture,0,0,16,16);
    Sprite enemySpiderDownSprite = new Sprite(enemySpiderTexture,16,0,16,16);
    Sprite enemySpiderLeftSprite = new Sprite(enemySpiderTexture,32,0,16,16);
    Sprite enemySpiderRightSprite = new Sprite(enemySpiderTexture,48,0,16,16);

    Sprite enemyGhostDownSprite = new Sprite(enemyGhostTexture,0,0,16,16);
    Sprite enemyGhostUpSprite = new Sprite(enemyGhostTexture,16,0,16,16);
    Sprite enemyGhostLeftSprite = new Sprite(enemyGhostTexture,32,0,16,16);
    Sprite enemyGhostRightSprite = new Sprite(enemyGhostTexture,48,0,16,16);

    Sprite enemyGhostAlertDownSprite = new Sprite(enemyGhostAlertTexture,0,0,16,16);
    Sprite enemyGhostAlertUpSprite = new Sprite(enemyGhostAlertTexture,16,0,16,16);
    Sprite enemyGhostAlertLeftSprite = new Sprite(enemyGhostAlertTexture,32,0,16,16);
    Sprite enemyGhostAlertRightSprite = new Sprite(enemyGhostAlertTexture,48,0,16,16);

    Sprite enemyEyeDownSprite = new Sprite(enemyEyeTexture,0,0,16,16);
    Sprite enemyEyeUpSprite = new Sprite(enemyEyeTexture,16,0,16,16);
    Sprite enemyEyeLeftSprite = new Sprite(enemyEyeTexture,32,0,16,16);
    Sprite enemyEyeRightSprite = new Sprite(enemyEyeTexture,48,0,16,16);

    Sprite enemyEyeSprite = new Sprite(enemyEyeTexture,0,0,16,16);

    public TextureRegion minotaurTextureRegion = new TextureRegion(bossMinotaurTexture, 0, 0, 64, 32);

    //HUD sprites

    public Sprite coinHUDSprite = new Sprite(coinHUDTexture, 10, 10);
    public Sprite heartSprite = new Sprite(heartHUDTexture, 16, 16);

    public Sprite coinItemSprite = new Sprite(coinItemTexture, 7,7);
    public Sprite potionItemSprite = new Sprite(potionItemTexture, 16, 16);

    public Sprite heartItemSprite = new Sprite(heartItemTexture, 9, 8);
    public Sprite halfHeartItemSprite = new Sprite(halfheartItemTexture, 9, 8);

    public Sprite torchItemSprite = new Sprite(torchItemTexture, 10, 15);
    public Sprite torchSlotSprite = new Sprite(torchSlotTexture, 10, 15);

    public Sprite shieldItemSprite = new Sprite(shieldItemTexture, 11, 11);
    public Sprite shieldSlotSprite = new Sprite(shieldSlotTexture, 11, 11);

    public Sprite beltItemSprite = new Sprite(beltItemTexture, 13, 13);
    public Sprite beltSlotSprite = new Sprite(beltSlotTexture, 13, 13);

    public Sprite chiselItemSprite = new Sprite(chiselItemTexture, 13, 13);
    public Sprite chiselSlotSprite = new Sprite(chiselSlotTexture, 13, 13);

    public Sprite greekfireItemSprite = new Sprite(greekfireItemTexture, 14, 15);
    public Sprite greekfireSlotSprite = new Sprite(greekfireSlotTexture, 13, 13);

    public Sprite compassSprite = new Sprite(compassTexture, 27,27);
    public Sprite compassArrowSprite = new Sprite(compassArrowTexture, 13,22);
   // public Sprite emptyCompass = new Sprite()

    Sprite shopkeeperSprite = new Sprite(shopkeeperTexture, 0,0, 16, 16);
    Sprite skullSprite = new Sprite(skullTexture, 176,64, 16, 16);
    Sprite damagedSkullSprite = new Sprite(skullTexture, 192,48, 16, 16);
    Sprite boneSprite = new Sprite(boneTexture, 192,64, 16, 16);

    public Sprite cobwebSprite = new Sprite(cobwebTexture,352,0,16,16);
    public Sprite webSprite = new Sprite(roomBackground,368,0,16,16);

    Sprite obstacle1Sprite = new Sprite(obstacle1Texture,80,48,16,16);
    Sprite obstacle2Sprite = new Sprite(obstacle2Texture,96,48,16,16);
    Sprite obstacle3Sprite = new Sprite(obstacle3Texture,112,48,16,16);

    Sprite candleSprite = new Sprite(candleTexture,384,64,16,16);
    Sprite candlesSprite = new Sprite(candlesTexture,400,64,16,16);

    //Pot sprites

    Sprite pot1Sprite = new Sprite(amphoraeTexture, 16,16, 16, 16);
    Sprite pot2Sprite = new Sprite(amphoraeTexture, 32,16, 16, 16);
    Sprite pot3Sprite = new Sprite(amphoraeTexture, 48,16, 16, 16);
    Sprite pot4Sprite = new Sprite(amphoraeTexture, 64,16, 16, 16);
    Sprite pot5Sprite = new Sprite(amphoraeTexture, 80,16, 16, 16);
    Sprite pot6Sprite = new Sprite(amphoraeTexture, 96,16, 16, 16);
    Sprite pot7Sprite = new Sprite(amphoraeTexture, 112,16, 16, 16);
    Sprite pot8Sprite = new Sprite(amphoraeTexture, 128,16, 16, 16);
    Sprite pot9Sprite = new Sprite(amphoraeTexture, 144,16, 16, 16);
    Sprite pot10Sprite = new Sprite(amphoraeTexture, 160,16, 16, 16);
    Sprite pot11Sprite = new Sprite(amphoraeTexture, 176,16, 16, 16);


    Sprite damagedAmphoraSprite = new Sprite(damagedAmphoraTexture, 224,0, 16, 16);
    Sprite damagedAmphora2Sprite = new Sprite(damagedAmphoraTexture, 224,16, 16, 16);

    Sprite tutorialSprite = new Sprite(tutorialTexture, 0,0, 96, 64);

    private static CreateAssets instance = null;
    public static CreateAssets getInstance(){
        if (instance == null) {
            instance = new CreateAssets();
        }
        return instance;
    }

    public void textureRegionBuilder() {

        assetManager = new AssetManager();
        //assetManager.load("HellasDungeon/Font/HellasFontStylized-extended.fnt", BitmapFont.class);

        //assetManager.load(level1Track.toString(), Music.class);

        //assetManager.load("", Sound.class);
        assetManager.finishLoading();


        //do not use yet - need to understand how assetloading music works properly
        //assetManager.setLoader(MusicLoader.class,);
        //Music music2 = assetManager.get("HellasDungeon/Music/Level1Track.mp3");

        //Music music2 = assetManager.get("HellasDungeon/Music/Level1Track.mp3");
        //music2.play();
        //music2.setVolume(0.5f);
       // music2.setLooping(true);

        //player walk animations

        float playerWalkSpeed = 0.13f;

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkUpFrames[index7++] = playerWalkUpTextureArray[g][w];
            }
        }

        playerWalkUpAnimation = new Animation<TextureRegion>(playerWalkSpeed, playerWalkUpFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkDownFrames[index8++] = playerWalkDownTextureArray[g][w];
            }
        }

        playerWalkDownAnimation = new Animation<TextureRegion>(playerWalkSpeed, playerWalkDownFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkLeftFrames[index9++] = playerWalkLeftTextureArray[g][w];
            }
        }

        playerWalkLeftAnimation = new Animation<TextureRegion>(playerWalkSpeed, playerWalkLeftFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkRightFrames[index10++] = playerWalkRightTextureArray[g][w];
            }
        }

        playerWalkRightAnimation = new Animation<TextureRegion>(playerWalkSpeed, playerWalkRightFrames);


        //player diagonal walk animations

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkUpLeftFrames[index11++] = playerWalkUpLeftTextureArray[g][w];
            }
        }

        playerWalkUpLeftAnimation = new Animation<TextureRegion>(playerWalkSpeed, playerWalkUpLeftFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkDownLeftFrames[index12++] = playerWalkDownLeftTextureArray[g][w];
            }
        }

        playerWalkDownLeftAnimation = new Animation<TextureRegion>(playerWalkSpeed, playerWalkDownLeftFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkDownRightFrames[index13++] = playerWalkDownRightTextureArray[g][w];
            }
        }

        playerWalkDownRightAnimation = new Animation<TextureRegion>(playerWalkSpeed, playerWalkDownRightFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkUpRightFrames[index14++] = playerWalkUpRightTextureArray[g][w];
            }
        }

        playerWalkUpRightAnimation = new Animation<TextureRegion>(playerWalkSpeed, playerWalkUpRightFrames);

        //enemy eye attack animation
        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                eyebeamFrames[index15++] = eyebeamTextureArray[g][w];
            }
        }

        //stun animation
        for (int p = 0; p < 1; p++) {
            for (int j = 0; j < 2; j++) {
                stunFrames[index23++] = stunTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        stunAnimation = new Animation<TextureRegion>(0.25f, stunFrames);

        //initialize minotaur Animations
        for (int g = 0; g < 1; g++) {
            for (int w = 0; w < 4; w++) {
                minotaurWalkUpFrames[index18++] = minotaurWalkUpTextureArray[g][w];
            }
        }

        minotaurWalkUpAnimation = new Animation<TextureRegion>(playerWalkSpeed, minotaurWalkUpFrames);

        for (int g = 0; g < 1; g++) {
            for (int w = 0; w < 4; w++) {
                    minotaurWalkDownFrames[index19++] = minotaurWalkDownTextureArray[g][w];
            }
        }

        minotaurWalkDownAnimation = new Animation<TextureRegion>(playerWalkSpeed, minotaurWalkDownFrames);

        for (int g = 0; g < 1; g++) {
            for (int w = 0; w < 4; w++) {
                minotaurWalkLeftFrames[index20++] = minotaurWalkLeftTextureArray[g][w];
            }
        }

        minotaurWalkLeftAnimation = new Animation<TextureRegion>(playerWalkSpeed, minotaurWalkLeftFrames);

        for (int g = 0; g < 1; g++) {
            for (int w = 0; w < 4; w++) {
                minotaurWalkRightFrames[index21++] = minotaurWalkRightTextureArray[g][w];
            }
        }

        minotaurWalkRightAnimation = new Animation<TextureRegion>(playerWalkSpeed, minotaurWalkRightFrames);

        // Initialize the Animation with the frame interval and array of frames
        eyebeamAnimation = new Animation<TextureRegion>(0.20f, eyebeamFrames);

        //fire animation
        for (int p = 0; p < 2; p++) {
            for (int j = 0; j < 5; j++) {
                fireFrames[index++] = fireTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        fireAnimation = new Animation<TextureRegion>(0.14f, fireFrames);

        //small flame animation
        for (int g = 0; g < 2; g++) {
            for (int w = 0; w < 3; w++) {
                flameFrames[index6++] = flameTextureArray[g][w];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        flameAnimation = new Animation<TextureRegion>(0.14f, flameFrames);


        //blue fire animation
        for (int p = 0; p < 2; p++) {
            for (int j = 0; j < 5; j++) {
                blueFireFrames[index5++] = blueFireTextureArray[p][j];
            }
        }

        blueFireAnimation = new Animation<TextureRegion>(0.07f, blueFireFrames);

        //small blue flame animation

        for (int g = 0; g < 2; g++) {
            for (int w = 0; w < 3; w++) {
                blueFlameFrames[index22++] = blueFlameTextureArray[g][w];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        blueFlameAnimation = new Animation<TextureRegion>(0.14f, blueFlameFrames);

        //smoke animation

        for (int p = 0; p < 2; p++) {
            for (int j = 0; j < 5; j++) {
                smokeFrames[index3++] = smokeTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        smokeAnimation = new Animation<TextureRegion>(0.3f, smokeFrames);

        for (int g = 0; g < 4; g++) {
            for (int t = 0; t < 5; t++) {
                fireOutFrames[index4++] = fireOutTextureArray[g][t];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        fireOutAnimation = new Animation<TextureRegion>(0.3f, fireOutFrames);

        float stateTime = 0f;


        for (int y = 0; y < 1; y++) {
            for (int r = 0; r < 8; r++) {
                arrowFrames[index2++] = arrowTextureArray[y][r];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        arrowAnimation = new Animation<TextureRegion>(0.03f, arrowFrames);

        for (int p = 0; p < 2; p++) {
            for (int j = 0; j < 5; j++) {
                flameSmokeFrames[index17++] = flameSmokeTextureArray[p][j];
            }
        }

        flameSmokeAnimation = new Animation<TextureRegion>(0.3f, flameSmokeFrames);

        for (int g = 0; g < 4; g++) {
            for (int t = 0; t < 3; t++) {
                flameOutFrames[index16++] = flameOutTextureArray[g][t];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        flameOutAnimation = new Animation<TextureRegion>(0.3f, flameOutFrames);




        //0.0651f

        //level1Track.play();
        //level1Track.setLooping(true);
        level1Track.setVolume(0.2f);
        //potBreaking.setPitch(0,0.1f);


        roomFloorTexture.setRegion(96, 16, 16, 16);
        roomFloor2Texture.setRegion(256, 32, 16, 16);
        roomFloor3Texture.setRegion(272, 32, 16, 16);
        roomDecorativeFloorRightTexture.setRegion(240, 48, 16, 16);
        roomDecorativeFloorUpTexture.setRegion(224, 32, 16, 16);
        roomDecorativeFloorDownTexture.setRegion(208, 48, 16, 16);
        roomDecorativeFloorLeftTexture.setRegion(224, 48, 16, 16);

        roomDecorativeFloorTopLeftTexture.setRegion(208, 64, 16, 16);
        roomDecorativeFloorTopRightTexture.setRegion(224, 64, 16, 16);
        roomDecorativeFloorBottomLeftTexture.setRegion(208, 80, 16, 16);
        roomDecorativeFloorBottomRightTexture.setRegion(224, 80, 16, 16);

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

        roomTopFence.setRegion(448, 0, 16, 16);
        roomBottomFence.setRegion(448, 32, 16, 16);
        roomLeftFence.setRegion(448, 16, 16, 16);
        roomRightFence.setRegion(432, 16, 16, 16);

        roomTopLeftCornerFence.setRegion(432, 48, 16, 16);
        roomTopRightCornerFence.setRegion(448, 48, 16, 16);
        roomBottomLeftCornerFence.setRegion(432, 64, 16, 16);
        roomBottomRightCornerFence.setRegion(448, 64, 16, 16);

        roomTopLeftTurnFence.setRegion(432, 80, 16, 16);
        roomTopRightTurnFence.setRegion(448, 80, 16, 16);
        roomBottomLeftTurnFence.setRegion(432, 96, 16, 16);
        roomBottomRightTurnFence.setRegion(448, 96, 16, 16);

        roomTopLeftEndFence.setRegion(384, 16, 16, 16);
        roomTopRightEndFence.setRegion(384, 0, 16, 16);
        roomBottomLeftEndFence.setRegion(400, 32, 16, 16);
        roomBottomRightEndFence.setRegion(384, 32, 16, 16);
        roomLeftUpEndFence.setRegion(416, 16, 16, 16);
        roomLeftDownEndFence.setRegion(400, 0, 16, 16);
        roomRightUpEndFence.setRegion(400, 16, 16, 16);
        roomRightDownEndFence.setRegion(416, 0, 16, 16);


        colTop1.setRegion(0, 0, 16, 16);
        colTop2.setRegion(16, 0, 16, 16);
        colTop3.setRegion(32, 0, 16, 16);
        colTop4.setRegion(48, 0, 16, 16);
        colTop5.setRegion(64, 0, 16, 16);
        colStem.setRegion(0, 16, 16, 16);
        colStemDamaged1.setRegion(16, 16, 16, 16);
        colStemDamaged2.setRegion(32, 16, 16, 16);
        colStem2.setRegion(48, 16, 16, 16);
        colStem3.setRegion(64, 16, 16, 16);
        colBase.setRegion(0, 32, 16, 16);
        colBase2.setRegion(16, 32, 16, 16);
        colBaseLower.setRegion(0,48,16,11);
        colBase2Lower.setRegion(16,48,16,11);
        pedestal1.setRegion(32, 32, 16, 16);
        pedestal2.setRegion(48, 32, 16, 16);
        pedestal3.setRegion(64, 32, 16, 16);
        pedestal4.setRegion(80, 32, 16, 16);



        corridorRoofTexture.setRegion(464, 0, 64, 96);
        //ruinedCorridorRoofTexture.setRegion(528, 0, 64, 96);
        ruinedCorridorRoofTexture.setRegion(464, 0, 64, 96);

        doorTopLeftTexture.setRegion(128, 0, 32, 16);
        doorTopRightTexture.setRegion(144, 0, 16, 16);
        doorTopLeftOpenTexture.setRegion(128, 16, 32, 16);
        doorTopRightOpenTexture.setRegion(144, 16, 16, 16);

        doorLeftUpperTexture.setRegion(160, 32, 16, 16);
        doorLeftLowerTexture.setRegion(160, 48, 16, 16);
        doorLeftUpperOpenTexture.setRegion(160, 0, 16, 32);
        doorLeftLowerOpenTexture.setRegion(160, 16, 16, 16);

        doorRightUpperTexture.setRegion(176, 32, 16, 16);
        doorRightLowerTexture.setRegion(176, 48, 16, 16);
        doorRightUpperOpenTexture.setRegion(176, 0, 16, 32);
        doorRightLowerOpenTexture.setRegion(176, 16, 16, 16);

        doorBottomLeftTexture.setRegion(128, 32, 32, 16);
        doorBottomRightTexture.setRegion(144, 32, 16, 16);
        doorBottomLeftOpenTexture.setRegion(128, 48, 32, 16);
        doorBottomRightOpenTexture.setRegion(144, 48, 16, 16);

        doorTopLeftWallTexture.setRegion(16, 0, 16, 16);
        doorTopRightWallTexture.setRegion(32, 0, 16, 16);
        doorLeftUpperWallTexture.setRegion(0, 32, 16, 16);
        doorLeftLowerWallTexture.setRegion(0, 48, 16, 16);
        doorRightUpperWallTexture.setRegion(64, 32, 16, 16);
        doorRightLowerWallTexture.setRegion(64, 48, 16, 16);
        doorBottomLeftWallTexture.setRegion(16, 64, 16, 16);
        doorBottomRightWallTexture.setRegion(32, 64, 16, 16);

        tutoTexture.setRegion(0, 0, 87, 57);

        potionItemSprite.setRegion(0, 0, 9, 11);

        torchItemSprite.setRegion(0, 0, 10, 15);
        torchSlotSprite.setRegion(0,0,13,15);

        shieldItemSprite.setRegion(0, 0, 11, 11);
        shieldSlotSprite.setRegion(0, 0, 13, 13);

        torchLeftTexture.setRegion(16, 32, 16, 16);
        torchRightTexture.setRegion(16, 48, 16, 16);
        torchUpTexture.setRegion(32, 48, 16, 16);
        torchDownTexture.setRegion(48, 48, 16, 16);
    }
}
