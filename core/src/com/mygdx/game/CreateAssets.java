package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CreateAssets {

    public AssetManager assetManager;

    //TODO Link all textures to HellasDungeon path
    Texture heartTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Heart.png"));
    Texture potionTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/LifePot.png"));
    Texture emptySlotTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Potion/Empty.png"));
    Texture coinTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Coin2Preview.png"));
    Texture playerTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SpriteSheet.png"));
    Texture playerAttackTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/Attack.png"));
    Texture roomBackground = new Texture(Gdx.files.internal("HellasDungeon/Level/Level 1/CustomTileset.png"));
    //Texture roomDoorTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHouse.png"));
    //Texture roomHoleTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHole.png"));
    Texture swordTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Sword/SpriteInHand.png"));
    Texture bowTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/Sprite.png"));
    Texture arrowTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Weapons/Bow/Arrow.png"));
    Texture enemySkullTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/Enemy/SpriteSheet.png"));
    Texture enemyEyeTexture =  new Texture(Gdx.files.internal("NinjaAdventure/Actor/Monsters/Eye/Eye.png"));
    Texture shopkeeperTexture = new Texture(Gdx.files.internal("NinjaAdventure/Actor/Characters/OldMan3/SpriteSheet.png"));
    Texture tutorialTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Tuto.png"));
    Texture fireAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Fire.png"));
    Texture blueFireAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FireBlu.png"));
    Texture smokeAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Smoke.png"));
    Texture fireOutAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/FireOut.png"));
    Texture arrowAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/ArrowAnimation.png"));
    Texture columnsTextureSheet = new Texture(Gdx.files.internal("HellasDungeon/Level/Objects/Columns.png"));
    Music level1Track = Gdx.audio.newMusic(Gdx.files.internal("HellasDungeon/Music/Level1Track.mp3"));

    //Texture font = new Texture(Gdx.files.internal("HellasDungeon/Font/GreekAlphabet.png"));
    //public TextureRegion fontTexture = new TextureRegion(font,0,0,16,16);

    public TextureRegion roomMiddleFloorTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
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

    public TextureRegion amphoraTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion amphora2Texture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion damagedAmphoraTexture = new TextureRegion(roomBackground, 0,0,16,16);

    //animations

    public TextureRegion fireAnimationTexture = new TextureRegion(fireAnimationSheet,0,0,16,16);
    public TextureRegion blueFireAnimationTexture = new TextureRegion(blueFireAnimationSheet,0,0,16,16);
    public TextureRegion smokeAnimationTexture = new TextureRegion(smokeAnimationSheet,0,0,16,16);
    public TextureRegion arrowAnimationTexture = new TextureRegion(fireAnimationSheet,0,0,16,16);
    public TextureRegion fireOutAnimationTexture = new TextureRegion(fireOutAnimationSheet,0,0,16,16);

    public Animation<TextureRegion> fireAnimation = new Animation<TextureRegion>(0.25f, fireAnimationTexture);
    public Animation<TextureRegion> blueFireAnimation = new Animation<TextureRegion>(0.25f, fireAnimationTexture);

    public Animation<TextureRegion> smokeAnimation = new Animation<TextureRegion>(0.25f, smokeAnimationTexture);
    public Animation<TextureRegion> fireOutAnimation = new Animation<TextureRegion>(0.10f, fireOutAnimationTexture);

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
    public TextureRegion pedestal1 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal3 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal4 = new TextureRegion(columnsTextureSheet, 0,0,16,16);

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
    Sprite arrowSprite = new Sprite(arrowTexture,0,0,13,5);
    //outline enemy sprites
    Sprite enemySprite = new Sprite(enemySkullTexture,0,0,16,16);
    Sprite enemyEyeSprite = new Sprite(enemyEyeTexture,0,0,16,16);
    //outline HUD sprites
    Sprite heartSprite = new Sprite(heartTexture, 16, 16);
    Sprite potionSprite = new Sprite(potionTexture, 16, 16);
    Sprite shopkeeperSprite = new Sprite(shopkeeperTexture, 0,0, 16, 16);
    Sprite skullSprite = new Sprite(skullTexture, 176,64, 16, 16);
    Sprite damagedSkullSprite = new Sprite(skullTexture, 192,48, 16, 16);
    Sprite boneSprite = new Sprite(boneTexture, 192,64, 16, 16);

    Sprite obstacle1Sprite = new Sprite(obstacle1Texture,80,48,16,16);
    Sprite obstacle2Sprite = new Sprite(obstacle2Texture,96,48,16,16);
    Sprite obstacle3Sprite = new Sprite(obstacle3Texture,112,48,16,16);

    Sprite amphoraSprite = new Sprite(amphoraTexture, 208,0, 16, 16);
    Sprite amphora2Sprite = new Sprite(amphoraTexture, 208,16, 16, 16);
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


        //assetManager.load("HellasDungeon/Music/level1Track.mp3", Music.class);
        //assetManager.setLoader(MusicLoader.class,);
        Music music2 = assetManager.get("HellasDungeon/Music/Level1Track.mp3");

        music2.play();
        music2.setVolume(0.5f);
       // music2.setLooping(true);

        //fire animation
        for (int p = 0; p < 2; p++) {
            for (int j = 0; j < 5; j++) {
                fireFrames[index++] = fireTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        fireAnimation = new Animation<TextureRegion>(0.14f, fireFrames);

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

        roomMiddleFloorTexture.setRegion(96, 16, 16, 16);
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
        pedestal1.setRegion(32, 32, 16, 16);
        pedestal2.setRegion(48, 32, 16, 16);
        pedestal3.setRegion(64, 32, 16, 16);
        pedestal4.setRegion(80, 32, 16, 16);

        doorTopLeftTexture.setRegion(128, 0, 16, 16);
        doorTopRightTexture.setRegion(144, 0, 16, 16);
        doorTopLeftOpenTexture.setRegion(128, 16, 16, 16);
        doorTopRightOpenTexture.setRegion(144, 16, 16, 16);

        doorLeftUpperTexture.setRegion(160, 32, 16, 16);
        doorLeftLowerTexture.setRegion(160, 48, 16, 16);
        doorLeftUpperOpenTexture.setRegion(160, 0, 16, 16);
        doorLeftLowerOpenTexture.setRegion(160, 16, 16, 16);

        doorRightUpperTexture.setRegion(176, 32, 16, 16);
        doorRightLowerTexture.setRegion(176, 48, 16, 16);
        doorRightUpperOpenTexture.setRegion(176, 0, 16, 16);
        doorRightLowerOpenTexture.setRegion(176, 16, 16, 16);

        doorBottomLeftTexture.setRegion(128, 32, 16, 16);
        doorBottomRightTexture.setRegion(144, 32, 16, 16);
        doorBottomLeftOpenTexture.setRegion(128, 48, 16, 16);
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

        potionSprite.setRegion(0, 0, 9, 11);

        torchLeftTexture.setRegion(16, 32, 16, 16);
        torchRightTexture.setRegion(16, 48, 16, 16);
        torchUpTexture.setRegion(32, 48, 16, 16);
        torchDownTexture.setRegion(48, 48, 16, 16);
    }
}
