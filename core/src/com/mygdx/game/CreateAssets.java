package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CreateAssets {

    public AssetManager assetManager;

    //TODO Link all textures to HellasDungeon path
    Texture heartHUDTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Heart.png"));
    Texture potionItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/LifePot.png"));
    Texture torchItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Torch.png"));
    Texture shieldItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Shield.png"));
    Texture emptySlotTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Potion/Empty.png"));
    Texture coinTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Coin2Preview.png"));
    Texture playerTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SpriteSheet.png"));
    Texture playerWalkUpAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkUp.png"));
    Texture playerWalkDownAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkDown.png"));
    Texture playerWalkLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkLeft.png"));
    Texture playerWalkRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkRight.png"));
    Texture playerAttackTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/Attack.png"));
    Texture roomBackground = new Texture(Gdx.files.internal("HellasDungeon/Level/Level 1/CustomTileset.png"));
    //Texture roomDoorTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHouse.png"));
    //Texture roomHoleTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHole.png"));
    Texture swordTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Sword/SpriteInHand.png"));
    Texture bowTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/Sprite.png"));
    Texture arrowTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Weapons/Bow/Arrow.png"));
    Texture shieldTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Shield/Sprite.png"));
    Texture enemySkullTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySkull/SpriteSheet.png"));
    Texture enemySpiderTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySpider/SpriteSheet.png"));
    Texture enemyGhostTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGhost/SpriteSheet.png"));
    Texture enemyEyeTexture =  new Texture(Gdx.files.internal("NinjaAdventure/Actor/Monsters/Eye/Eye.png"));
    Texture shopkeeperTexture = new Texture(Gdx.files.internal("NinjaAdventure/Actor/Characters/OldMan3/SpriteSheet.png"));
    Texture tutorialTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Tuto.png"));
    Texture fireAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Fire.png"));
    Texture flameAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Flame.png"));
    Texture blueFireAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FireBlu.png"));
    Texture smokeAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Smoke.png"));
    Texture fireOutAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FireOut.png"));
    Texture arrowAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/ArrowAnimation.png"));
    Texture columnsTextureSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Columns.png"));
    Texture potsSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Pots.png"));
    Texture webTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySpider/WebSpit.png"));



            Music level1Track = Gdx.audio.newMusic(Gdx.files.internal("HellasDungeon/Music/Level1Track.mp3"));

    public Sound potBreaking = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/potbreaking2.mp3"));
    public Sound skullBreaking = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/skullbreaking.mp3"));
    public Sound boneBreaking = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/bonebreaking.mp3"));
    public Sound spiderAttack = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/spiderattack.mp3"));

    public Sound fireAmbient = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/Fire.mp3"));

    //Texture font = new Texture(Gdx.files.internal("HellasDungeon/Font/GreekAlphabet.png"));
    //public TextureRegion fontTexture = new TextureRegion(font,0,0,16,16);

    public TextureRegion roomFloorTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomFloor2Texture = new TextureRegion(roomBackground, 256, 32, 16, 16);
    public TextureRegion roomFloor3Texture = new TextureRegion(roomBackground, 272, 32, 16, 16);
    public TextureRegion roomDecorativeFloorUpTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorDownTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);

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

    public TextureRegion candleTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion candlesTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion roomTopFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomLeftFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomRightFence = new TextureRegion(roomBackground, 0, 0, 16, 16);

    public TextureRegion roomTopLeftCornerFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopRightCornerFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftCornerFence = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomRightCornerFence = new TextureRegion(roomBackground, 0, 0, 16, 16);

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


    public TextureRegion cobwebTexture = new TextureRegion(roomBackground,0,0,16,16);

    //animations
    public TextureRegion playerWalkUpAnimationTexture = new TextureRegion(playerWalkUpAnimationSheet,0,0,16,16);
    public TextureRegion playerWalkDownAnimationTexture = new TextureRegion(playerWalkDownAnimationSheet,16,0,16,16);
    public TextureRegion playerWalkLeftAnimationTexture = new TextureRegion(playerWalkLeftAnimationSheet,32,0,16,16);
    public TextureRegion playerWalkRightAnimationTexture = new TextureRegion(playerWalkRightAnimationSheet,48,0,16,16);

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

    public TextureRegion fireAnimationTexture = new TextureRegion(fireAnimationSheet,0,0,16,16);
    public TextureRegion flameAnimationTexture = new TextureRegion(flameAnimationSheet,0,0,16,16);
    public TextureRegion blueFireAnimationTexture = new TextureRegion(blueFireAnimationSheet,0,0,16,16);
    public TextureRegion smokeAnimationTexture = new TextureRegion(smokeAnimationSheet,0,0,16,16);
    public TextureRegion arrowAnimationTexture = new TextureRegion(fireAnimationSheet,0,0,16,16);
    public TextureRegion fireOutAnimationTexture = new TextureRegion(fireOutAnimationSheet,0,0,16,16);

    public Animation<TextureRegion> fireAnimation = new Animation<TextureRegion>(0.25f, fireAnimationTexture);
    public Animation<TextureRegion> flameAnimation = new Animation<TextureRegion>(0.20f, flameAnimationTexture);
    public Animation<TextureRegion> blueFireAnimation = new Animation<TextureRegion>(0.25f, fireAnimationTexture);

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

    int index = 0;
    int index2 = 0;
    int index3 = 0;
    int index4 = 0;
    int index5 = 0;
    int index6 = 0;
    int index7 = 0;
    int index8 = 0;
    int index9 = 0;
    int index10 = 0;

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

    public TextureRegion pedestal1 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal3 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal4 = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion roofTexture = new TextureRegion(roomBackground,0,0,64,96);

    public TextureRegion ruinedRoofTexture = new TextureRegion(roomBackground,0,0,64,96);

    public TextureRegion skullTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion boneTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion tutoTexture = new TextureRegion(tutorialTexture, 0,0,87,57);

    public TextureRegion torchLeftTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion torchRightTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion torchUpTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion torchDownTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion lockUpTexture = new TextureRegion(roomBackground, 160,64,16,16);
    public TextureRegion lockDownTexture = new TextureRegion(roomBackground, 192,32,16,16);
    public TextureRegion lockLeftTexture = new TextureRegion(roomBackground, 192,16,16,16);
    public TextureRegion lockRightTexture = new TextureRegion(roomBackground, 192,0,16,16);

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
    //outline weapon sprites
    Sprite swordSprite = new Sprite(swordTexture, 0, 0, 7, 14);
    Sprite bowSprite = new Sprite(bowTexture,0,0,19,8);
    Sprite shieldSprite = new Sprite(shieldTexture,0,0, 16, 8);
    Sprite arrowSprite = new Sprite(arrowTexture,0,0,13,5);
    //outline enemy sprites
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

    Sprite enemyEyeSprite = new Sprite(enemyEyeTexture,0,0,16,16);

    //outline HUD sprites
    public Sprite coinItemSprite = new Sprite(coinTexture, 10, 10);
    public Sprite heartSprite = new Sprite(heartHUDTexture, 16, 16);
    public Sprite potionItemSprite = new Sprite(potionItemTexture, 16, 16);
    public Sprite torchItemSprite = new Sprite(torchItemTexture, 10, 15);
    public Sprite shieldItemSprite = new Sprite(shieldItemTexture, 11, 11);

    Sprite shopkeeperSprite = new Sprite(shopkeeperTexture, 0,0, 16, 16);
    Sprite skullSprite = new Sprite(skullTexture, 176,64, 16, 16);
    Sprite damagedSkullSprite = new Sprite(skullTexture, 192,48, 16, 16);
    Sprite boneSprite = new Sprite(boneTexture, 192,64, 16, 16);

    public Sprite cobwebSprite = new Sprite(cobwebTexture,351,0,16,16);
    public Sprite webSprite = new Sprite(roomBackground,368,0,16,16);

    Sprite obstacle1Sprite = new Sprite(obstacle1Texture,80,48,16,16);
    Sprite obstacle2Sprite = new Sprite(obstacle2Texture,96,48,16,16);
    Sprite obstacle3Sprite = new Sprite(obstacle3Texture,112,48,16,16);

    Sprite candleSprite = new Sprite(candleTexture,384,64,16,16);
    Sprite candlesSprite = new Sprite(candlesTexture,400,64,16,16);

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
        assetManager.load("HellasDungeon/Font/HellasFontStylized-extended.fnt", BitmapFont.class);

        assetManager.load("HellasDungeon/Music/Level1Track.mp3", Music.class);
        //assetManager.finishLoading();


        //do not use yet - need to understand how assetloading music works properly
        //assetManager.setLoader(MusicLoader.class,);
        //Music music2 = assetManager.get("HellasDungeon/Music/Level1Track.mp3");

        //Music music2 = assetManager.get("HellasDungeon/Music/Level1Track.mp3");
        //music2.play();
        //music2.setVolume(0.5f);
       // music2.setLooping(true);

        //player walk up animation

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkUpFrames[index7++] = playerWalkUpTextureArray[g][w];
            }
        }

        playerWalkUpAnimation = new Animation<TextureRegion>(0.15f, playerWalkUpFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkDownFrames[index8++] = playerWalkDownTextureArray[g][w];
            }
        }

        playerWalkDownAnimation = new Animation<TextureRegion>(0.15f, playerWalkDownFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkLeftFrames[index9++] = playerWalkLeftTextureArray[g][w];
            }
        }

        playerWalkLeftAnimation = new Animation<TextureRegion>(0.15f, playerWalkLeftFrames);

        for (int g = 0; g < 4; g++) {
            for (int w = 0; w < 1; w++) {
                playerWalkRightFrames[index10++] = playerWalkRightTextureArray[g][w];
            }
        }

        playerWalkRightAnimation = new Animation<TextureRegion>(0.15f, playerWalkRightFrames);



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

        roomTopLeftEndFence.setRegion(384, 16, 16, 16);
        roomTopRightEndFence.setRegion(384, 0, 16, 16);
        roomBottomLeftEndFence.setRegion(400, 32, 16, 16);
        roomBottomRightEndFence.setRegion(384, 32, 16, 16);
        roomLeftUpEndFence.setRegion(416, 16, 16, 16);
        roomLeftDownEndFence.setRegion(400, 0, 16, 16);
        roomRightUpEndFence.setRegion(400, 16, 16, 16);
        roomRightDownEndFence.setRegion(416, 0, 16, 16);


        //deprecated - changed to sprite
        //obstacle1Texture.setRegion(80,64,16,16);
        //obstacle2Texture.setRegion(96,64,16,16);
        //obstacle3Texture.setRegion(112,64,16,16);

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
        colBaseLower.setRegion(0,48,16,5);
        pedestal1.setRegion(32, 32, 16, 16);
        pedestal2.setRegion(48, 32, 16, 16);
        pedestal3.setRegion(64, 32, 16, 16);
        pedestal4.setRegion(80, 32, 16, 16);

        roofTexture.setRegion(464, 0, 64, 96);
        ruinedRoofTexture.setRegion(528, 0, 64, 96);

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
        shieldItemSprite.setRegion(0, 0, 11, 11);

        torchLeftTexture.setRegion(16, 32, 16, 16);
        torchRightTexture.setRegion(16, 48, 16, 16);
        torchUpTexture.setRegion(32, 48, 16, 16);
        torchDownTexture.setRegion(48, 48, 16, 16);
    }
}
