package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CreateTexture {
    Texture heartTexture = new Texture(Gdx.files.internal("NinjaAdventure/HUD/Heart.png"));
    Texture potionTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Potion/LifePot.png"));
    Texture emptySlotTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Potion/Empty.png"));
    Texture coinTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Treasure/Coin2.png"));
    Texture playerTexture = new Texture(Gdx.files.internal("NinjaAdventure/Actor/Characters/GoldKnight/SpriteSheet.png"));
    Texture playerAttackTexture = new Texture(Gdx.files.internal("NinjaAdventure/Actor/Characters/GoldKnight/SeparateAnim/Attack.png"));
    Texture roomBackground = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/Interior/CustomTileset.png"));
    Texture roomDoorTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHouse.png"));
    Texture roomHoleTexture = new Texture(Gdx.files.internal("NinjaAdventure/Backgrounds/Tilesets/TilesetHole.png"));
    Texture swordTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Weapons/BigSword/SpriteInHand.png"));
    Texture bowTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Weapons/Bow/Sprite.png"));
    Texture arrowTexture = new Texture(Gdx.files.internal("NinjaAdventure/Items/Weapons/Bow/Arrow.png"));
    Texture enemyTexture =  new Texture(Gdx.files.internal("NinjaAdventure/Actor/Monsters/Skull/SpriteSheet.png"));

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

    TextureRegion doorTexture = new TextureRegion(roomDoorTexture, 0, 0, 16, 16);
    TextureRegion holeTexture = new TextureRegion(roomHoleTexture, 0, 0, 16, 16);

    //outline player sprites
    Sprite playerSprite = new Sprite(playerTexture, 0, 0, 16, 16);
    Sprite playerUp = new Sprite(playerTexture, 16, 0, 16, 16);
    Sprite playerDown = new Sprite(playerTexture, 0, 0, 16, 16);
    Sprite playerLeft = new Sprite(playerTexture, 32, 0, 16, 16);
    Sprite playerRight = new Sprite(playerTexture, 48, 0, 16, 16);
    Sprite playerAttackUp = new Sprite(playerAttackTexture, 16, 0, 16, 16);
    Sprite playerAttackDown = new Sprite(playerAttackTexture, 0, 0, 16, 16);
    Sprite playerAttackLeft = new Sprite(playerAttackTexture, 32, 0, 16, 16);
    Sprite playerAttackRight = new Sprite(playerAttackTexture, 48, 0, 16, 16);
    //outline weapon sprites
    Sprite swordSprite = new Sprite(swordTexture, 0, 0, 7, 12);
    Sprite bowSprite = new Sprite(bowTexture,0,0,15,7);
    Sprite arrowSprite = new Sprite(arrowTexture,0,0,13,5);
    //outline enemy sprites
    Sprite enemySprite = new Sprite(enemyTexture,0,0,16,16);
    //outline HUD sprites
    Sprite heartSprite = new Sprite(heartTexture, 16, 16);
    Sprite potionSprite = new Sprite(potionTexture, 16, 16);

    private static CreateTexture instance = null;
    public static CreateTexture getInstance(){
        if (instance == null) {
            instance = new CreateTexture();
        }
        return instance;
    }

    public void textureRegionBuilder() {
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
        obstacle1Texture.setRegion(80,64,16,16);
        obstacle2Texture.setRegion(96,64,16,16);
        obstacle3Texture.setRegion(112,64,16,16);
        doorTexture.setRegion(144, 48, 16, 16);
    }
}
