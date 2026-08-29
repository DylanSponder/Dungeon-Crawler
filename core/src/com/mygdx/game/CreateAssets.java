package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class CreateAssets {

    public AssetManager assetManager;

    //TODO Make sure to link all textures to HellasDungeon path
    Texture heartHUDTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Heart.png"));
    Texture potionItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/LifePot.png"));
    Texture potionHUDTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/LifePotHUD.png"));
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
    Texture wingsItemTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Wings.png"));
    Texture wingsSlotTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/WingSlot.png"));


    Texture compassTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Compass.png"));
    Texture compassArrowTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/CompassArrow.png"));

    Texture heartItemTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/HeartItem.png"));
    Texture halfheartItemTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/HalfHeartItem.png"));

    Texture coinItemTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/CoinItem.png"));

    Texture coinHUDTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Coin.png"));
    Texture playerTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SpriteSheet.png"));
    Texture playerHeadTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/Head.png"));
    Texture playerWalkUpAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkUp.png"));
    Texture playerWalkDownAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkDown.png"));
    Texture playerWalkLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkLeft.png"));
    Texture playerWalkRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkRight.png"));
    Texture playerWalkDownRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkDownRight.png"));
    Texture playerWalkDownLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkDownLeft.png"));
    Texture playerWalkUpLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkUpLeft.png"));
    Texture playerWalkUpRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/WalkUpRight.png"));

    //Particle texture atlases
    //public TextureAtlas potSherds = new TextureAtlas(Gdx.files.internal("HellasDungeon/Particles/Pot/PotSherds.atlas"));

    Texture playerAttackTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Player/SeparateAnim/Attack.png"));

    //Main texture sheet
    Texture roomBackground = new Texture(Gdx.files.internal("HellasDungeon/level/level 1/CustomTileset.png"));

    Texture fencesTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fences.png"));

    Texture floorsTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Floors.png"));

    Texture doorsTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Doors/Doors.png"));
    Texture doorsOpenTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Doors/DoorsOpen.png"));
    Texture gatesTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Doors/Gates.png"));
    Texture gatesOpenTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Doors/GatesOpen.png"));
    Texture locksTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Doors/Locks.png"));

    Texture skullTextureSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Skull.png"));

    Texture stairsTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Stairs/Stairs.png"));

    Texture firePitTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/FirePit.png"));

    Texture drainTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Drain.png"));

    Texture torchTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Torch.png"));

    Texture oceanWaterTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/OceanWater.png"));
    //Texture waterTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Water.png"));
    Texture waveTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Wave.png"));
    Texture waveDarkTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/WaveDark.png"));

    Texture waveAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/WaveAnimation.png"));

    Texture waveDarkAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/WaveDarkAnimation.png"));

    Texture waterAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/WaterAnimation.png"));

    Texture waterfallAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Waterfall.png"));
    Texture waterfallUpAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/WaterfallUp.png"));

    Texture waterfall1Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Waterfall/Waterfall1.png"));
    Texture waterfall2Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Waterfall/Waterfall2.png"));
    Texture waterfall3Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Waterfall/Waterfall3.png"));;

    Texture swordTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Sword/SpriteInHand.png"));
    Texture bowTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/Sprite.png"));
    Texture arrowTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/Arrow.png"));
    Texture shieldTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Shield/Sprite.png"));
    Texture chiselTexture = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Chisel/SpriteInHand.png"));
    Texture enemySkullTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySkull/SpriteSheet.png"));

    Texture enemySkullUpAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySkull/SeparateAnim/Up.png"));
    Texture enemySkullDownAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySkull/SeparateAnim/Down.png"));
    Texture enemySkullLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySkull/SeparateAnim/Left.png"));
    Texture enemySkullRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySkull/SeparateAnim/Right.png"));

    Texture enemyCrabUpAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyCrab/WalkUp.png"));
    Texture enemyCrabDownAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyCrab/WalkDown.png"));
    Texture enemyCrabLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyCrab/WalkLeft.png"));
    Texture enemyCrabRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyCrab/WalkRight.png"));

    Texture enemyGryphonLeft1Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyLeft/FlyLeft1.png"));
    Texture enemyGryphonLeft2Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyLeft/FlyLeft2.png"));
    Texture enemyGryphonLeft3Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyLeft/FlyLeft3.png"));
    Texture enemyGryphonLeft4Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyLeft/FlyLeft4.png"));
    Texture enemyGryphonLeft5Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyLeft/FlyLeft5.png"));
    Texture enemyGryphonLeft6Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyLeft/FlyLeft6.png"));

    Texture enemyGryphonRight1Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyRight/FlyRight1.png"));
    Texture enemyGryphonRight2Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyRight/FlyRight2.png"));
    Texture enemyGryphonRight3Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyRight/FlyRight3.png"));;
    Texture enemyGryphonRight4Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyRight/FlyRight4.png"));
    Texture enemyGryphonRight5Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyRight/FlyRight5.png"));
    Texture enemyGryphonRight6Texture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGryphon/FlyRight/FlyRight6.png"));;

    Texture enemySpiderTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySpider/SpriteSheet.png"));
    Texture enemyGhostTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGhost/SpriteSheet.png"));
    Texture enemyGhostAlertTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyGhost/SpriteSheetAlerted.png"));
    Texture enemyEyeTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyCyclops/Eye.png"));
    Texture eyebeamTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemyCyclops/Eyebeam.png"));

    Texture bossMinotaurTexture =  new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/Minotaur.png"));

    Texture bossHealthbarTexture =  new Texture(Gdx.files.internal("HellasDungeon/HUD/BossHealthbar.png"));

    Texture minotaurWalkUpAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/MinotaurWalkUp.png"));
    Texture minotaurWalkDownAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/MinotaurWalkDown.png"));
    Texture minotaurWalkLeftAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/MinotaurWalkLeft.png"));
    Texture minotaurWalkRightAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Entity/BossMinotaur/MinotaurWalkRight.png"));

    Texture stunAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/HUD/Stun.png"));

    Texture shopkeeperTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/Shopkeeper/SpriteSheet.png"));
    Texture tutorialTexture = new Texture(Gdx.files.internal("HellasDungeon/HUD/Tuto.png"));

    Texture ripple1Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Water/Ripple1.png"));
    Texture ripple2Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Water/Ripple2.png"));
    Texture ripple3Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Water/Water/Ripple3.png"));;

    Texture fireAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire.png"));

    Texture fire1Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire1.png"));
    Texture fire2Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire2.png"));
    Texture fire3Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire3.png"));;
    Texture fire4Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire4.png"));
    Texture fire5Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire5.png"));
    Texture fire6Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire6.png"));;
    Texture fire7Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire7.png"));
    Texture fire8Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire8.png"));
    Texture fire9Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire9.png"));;
    Texture fire10Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Fire/Fire10.png"));

    Texture flame1Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/Flame1.png"));
    Texture flame2Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/Flame2.png"));
    Texture flame3Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/Flame3.png"));;
    Texture flame4Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/Flame4.png"));
    Texture flame5Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/Flame5.png"));
    Texture flame6Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/Flame6.png"));;

    Texture candleFlame1Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/CandleFlame1.png"));
    Texture candleFlame2Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/CandleFlame2.png"));
    Texture candleFlame3Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/CandleFlame3.png"));;
    Texture candleFlame4Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/CandleFlame4.png"));
    Texture candleFlame5Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/CandleFlame5.png"));
    Texture candleFlame6Texture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Fire/Flame/CandleFlame6.png"));;

    Texture flameAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Flame.png"));
    Texture blueFireAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/FireBlu.png"));
    Texture blueFlameAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/FlameBlu.png"));
    Texture smokeAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Smoke.png"));
    Texture flameSmokeAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/FlameSmoke.png"));
    Texture fireOutAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/FireOut.png"));
    Texture flameOutAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/FlameOut.png"));
    Texture arrowAnimationSheet = new Texture(Gdx.files.internal("HellasDungeon/Weapons/Bow/ArrowAnimation.png"));
    Texture columnsTextureSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Columns.png"));
    Texture potsSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Pots.png"));
    //Texture webTexture = new Texture(Gdx.files.internal("HellasDungeon/Entity/EnemySpider/WebSpit.png"));
    Texture arrowTrapTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/ArrowTrap.png"));
    Texture fireArrowTrapTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/FireArrowTrap.png"));

    Texture flagTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Flag.png"));
    Texture vinesTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Vines.png"));
    Texture blocksTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Blocks.png"));

    Texture wallsTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Walls.png"));

    Texture largeWallsTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/LargeWalls.png"));

    Texture pitsTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Pits.png"));

    Texture oilLampTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/OilLamp.png"));

    Texture pitRubbleTexture = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/PitRubble.png"));

    Texture venusSheet = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/VenusdeMilo.png"));

    //Texture raisedFloorMask = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/RaisedFloorMask.png"));

    Texture roof3x3 = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Roofs/3x3.png"));
    Texture roof5x5 = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Roofs/5x5.png"));
    Texture roof7x7 = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Roofs/7x7-6.png"));
    Texture roof11x11 = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Roofs/11x11.png"));
    Texture roof15x15 = new Texture(Gdx.files.internal("HellasDungeon/level/Objects/Roofs/15x15.png"));

    //particles


    Music level1Track = Gdx.audio.newMusic(Gdx.files.internal("HellasDungeon/Music/Level1Track.mp3"));

    public Sound waterSplash = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/watersplash.mp3"));
    public Sound waterSplosh = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/watersplosh.mp3"));

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

    public Sound minoHurt = Gdx.audio.newSound((Gdx.files.internal("HellasDungeon/Sounds/minohurt2.mp3")));
    public Sound minoHurt2 = Gdx.audio.newSound((Gdx.files.internal("HellasDungeon/Sounds/minohurt3.mp3")));

    public Sound minoCharge = Gdx.audio.newSound((Gdx.files.internal("HellasDungeon/Sounds/minocharge.mp3")));
    public Sound minoCharge2 = Gdx.audio.newSound((Gdx.files.internal("HellasDungeon/Sounds/minocharge3.mp3")));

    public Sound fireAmbient = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/Fire.mp3"));

    public Sound fireWhoosh = Gdx.audio.newSound(Gdx.files.internal("HellasDungeon/Sounds/lightfire6.mp3"));


    //Texture font = new Texture(Gdx.files.internal("HellasDungeon/Font/GreekAlphabet.png"));
    //public TextureRegion fontTexture = new TextureRegion(font,0,0,16,16);

    //Floor textures

    public TextureRegion roomFloorTexture = new TextureRegion(floorsTexture, 2, 2, 16, 16);
    public TextureRegion roomFloorTexture2 = new TextureRegion(floorsTexture, 22, 2, 16, 16);
    public TextureRegion roomFloor2Texture = new TextureRegion(floorsTexture, 2, 22, 16, 16);
    public TextureRegion roomFloor3Texture = new TextureRegion(floorsTexture, 82, 2, 16, 16);
    public TextureRegion roomFloor3Texture2 = new TextureRegion(floorsTexture, 82, 22, 16, 16);
    public TextureRegion roomFloor4Texture = new TextureRegion(floorsTexture, 102, 2, 16, 16);
    public TextureRegion roomFloor4Texture2 = new TextureRegion(floorsTexture, 102, 22, 16, 16);

    public TextureRegion roomDecorativeFloorUpTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorDownTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorTopLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorTopRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorBottomLeftTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomDecorativeFloorBottomRightTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);

    //Water textures
    public TextureRegion oceanWater = new TextureRegion(oceanWaterTexture, 0, 0, 16, 16);
    public TextureRegion water = new TextureRegion(oceanWaterTexture, 0, 0, 16, 16);
    public TextureRegion wave = new TextureRegion(waveTexture, 0, 0, 16, 16);

    public TextureRegion air = new TextureRegion(roomBackground, 0, 0, 16, 16);

    //Pit textures
    public TextureRegion pit = new TextureRegion(pitsTexture, 0, 0, 16, 16);
    public TextureRegion pitFloor1 = new TextureRegion(pitsTexture, 0, 0, 16, 16);
    public TextureRegion pitFloor2 = new TextureRegion(pitsTexture, 0, 0, 16, 16);
    public TextureRegion pitStairs = new TextureRegion(pitsTexture, 0, 0, 16, 16);

    public TextureRegion pitLeft = new TextureRegion(pitsTexture, 0, 0, 16, 16);
    public TextureRegion pitRight = new TextureRegion(pitsTexture, 0, 0, 16, 16);
    public TextureRegion pitBottom = new TextureRegion(pitsTexture, 0, 0, 16, 16);

    public TextureRegion pitBottomLeft = new TextureRegion(pitsTexture, 0, 0, 16, 16);
    public TextureRegion pitBottomRight = new TextureRegion(pitsTexture, 0, 0, 16, 16);

    //Mosaic textures
    public TextureRegion mosaicSpartan = new TextureRegion(floorsTexture, 0, 0, 96, 96);
    public TextureRegion mosaicGryphon = new TextureRegion(floorsTexture, 0, 0, 32, 32);
    public TextureRegion mosaicTrident = new TextureRegion(floorsTexture, 0, 0, 32, 32);
    public TextureRegion mosaicTree = new TextureRegion(floorsTexture, 0, 0, 32, 32);
    public TextureRegion mosaicBull = new TextureRegion(floorsTexture, 0, 0, 32, 32);
    public TextureRegion mosaicLyre = new TextureRegion(floorsTexture, 0, 0, 32, 32);

    //Floor tile textures
    public TextureRegion floorTile = new TextureRegion(floorsTexture, 0, 0, 16, 16);
    public TextureRegion sunkenFloorTile = new TextureRegion(floorsTexture, 0, 0, 16, 16);
    public TextureRegion darkFloorTile = new TextureRegion(floorsTexture, 0, 0, 16, 16);
    public TextureRegion darkSunkenFloorTile = new TextureRegion(floorsTexture, 0, 0, 16, 16);

    public TextureRegion keyTile = new TextureRegion(floorsTexture, 0, 0, 16, 16);
    public TextureRegion key2Tile = new TextureRegion(floorsTexture, 0, 0, 16, 16);

    //Large wall textures
    public TextureRegion largeWallUp = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallDown = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallLeft = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallRight = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);

    public TextureRegion largeWallTopLeft = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallTopRight = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallBottomLeft = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallBottomRight = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);

    public TextureRegion largeWallTopLeftTurn = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallTopRightTurn = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallBottomLeftTurn = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);
    public TextureRegion largeWallBottomRightTurn = new TextureRegion(largeWallsTexture, 0, 0, 16, 16);


    //Inner wall textures
    public TextureRegion innerWallUp = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallDown = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallLeft = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallRight = new TextureRegion(wallsTexture, 0, 0, 16, 16);

    public TextureRegion innerWallTLCorner = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallTRCorner = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallBLCorner = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallBRCorner = new TextureRegion(wallsTexture, 0, 0, 16, 16);

    public TextureRegion innerWallTLTurn = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallTRTurn = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallBLTurn = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallBRTurn = new TextureRegion(wallsTexture, 0, 0, 16, 16);

    public TextureRegion innerWallTLTurn2 = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallTRTurn2 = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallBLTurn2 = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallBRTurn2 = new TextureRegion(wallsTexture, 0, 0, 16, 16);

    /*
    public TextureRegion innerWallUp = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallDown = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallLeft = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion innerWallRight = new TextureRegion(wallsTexture, 0, 0, 16, 16);
     */
    //Wall textures

    public TextureRegion roomLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomRightWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopLeftTurnWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomTopRightTurnWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftTurnWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion roomBottomRightTurnWallTexture = new TextureRegion(roomBackground, 0, 0, 16, 16);
    public TextureRegion obstacle1Texture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion obstacle2Texture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion obstacle3Texture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion vaseTexture = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion obstacle4Texture = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion obstacle5Texture = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion obstacle6Texture = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion obstacle7Texture = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    //Stair textures

    public TextureRegion stairTopLeft = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairTopRight = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairTop = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairTop2 = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairLeftCorner = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairRightCorner = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairLeft = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairRight = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairDown = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairDownLeft = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairDownRight = new TextureRegion(stairsTexture, 0,0,16,16);

    public TextureRegion stairUpTopRight = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairUpTopLeft = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairUpBottomRight = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion stairUpBottomLeft = new TextureRegion(stairsTexture, 0,0,16,16);

    public TextureRegion waterRimTop = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion waterRimBottom = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion waterRimLeft = new TextureRegion(stairsTexture, 0,0,16,16);
    public TextureRegion waterRimRight = new TextureRegion(stairsTexture, 0,0,16,16);

    public TextureRegion drain = new TextureRegion(drainTexture, 0,0,24,28);

    //Fence textures

    public TextureRegion roomTopFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomBottomFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomLeftFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomRightFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);

    public TextureRegion roomTopLeftCornerFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomTopRightCornerFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftCornerFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomBottomRightCornerFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);

    public TextureRegion roomTopLeftTurnFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomTopRightTurnFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftTurnFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomBottomRightTurnFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);

    public TextureRegion roomTopLeftEndFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomTopRightEndFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomBottomLeftEndFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomBottomRightEndFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomLeftUpEndFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomLeftDownEndFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomRightUpEndFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);
    public TextureRegion roomRightDownEndFence = new TextureRegion(fencesTexture, 0, 0, 16, 16);


    //Blocks textures
    public TextureRegion blockWallUp = new TextureRegion(wallsTexture, 0, 0, 16, 16);
    public TextureRegion block = new TextureRegion(blocksTexture, 0, 0, 16, 16);
    public TextureRegion blockOmega = new TextureRegion(blocksTexture, 16, 0, 16, 16);
    public TextureRegion blockPhi = new TextureRegion(blocksTexture, 0, 0, 16, 16);
    public TextureRegion blockDelta = new TextureRegion(blocksTexture, 0, 0, 16, 16);
    public TextureRegion blockSigma = new TextureRegion(blocksTexture, 0, 0, 16, 16);
    public TextureRegion blockLambda = new TextureRegion(blocksTexture, 0, 0, 16, 16);
    public TextureRegion blockGaben = new TextureRegion(blocksTexture, 0, 0, 16, 16);
    public TextureRegion blockPi = new TextureRegion(blocksTexture, 0, 0, 16, 16);

    //Boss healthbar
    public TextureRegion bossHealthbarContainer = new TextureRegion(bossHealthbarTexture, 0,0,92,12);
    public TextureRegion bossHealthbarFull = new TextureRegion(bossHealthbarTexture, 6,13,80,10);
    public TextureRegion bossHealthbarEmpty = new TextureRegion(bossHealthbarTexture, 6,25,80,10);

    public TextureRegion amphoraeTexture = new TextureRegion(potsSheet, 0,0,16,16);
    public TextureRegion amphora2Texture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion damagedAmphoraTexture = new TextureRegion(roomBackground, 0,0,16,16);

    public TextureRegion candleTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion candlesTexture = new TextureRegion(roomBackground, 0,0,16,16);



    public TextureRegion cobwebTexture = new TextureRegion(roomBackground,0,0,16,16);

    //animations
    public TextureRegion enemySkullWalkUpAnimationTexture = new TextureRegion(enemySkullUpAnimationSheet,0,0,15,16);
    public TextureRegion enemySkullWalkDownAnimationTexture = new TextureRegion(enemySkullDownAnimationSheet,0,0,15,16);
    public TextureRegion enemySkullWalkLeftAnimationTexture = new TextureRegion(enemySkullLeftAnimationSheet,0,0,15,16);
    public TextureRegion enemySkullWalkRightAnimationTexture = new TextureRegion(enemySkullRightAnimationSheet,0,0,15,16);

    public Animation<TextureRegion> enemySkullUpAnimation = new Animation<TextureRegion>(0.75f, enemySkullWalkUpAnimationTexture);
    public Animation<TextureRegion> enemySkullDownAnimation = new Animation<TextureRegion>(0.75f, enemySkullWalkDownAnimationTexture);
    public Animation<TextureRegion> enemySkullLeftAnimation = new Animation<TextureRegion>(0.75f, enemySkullWalkLeftAnimationTexture);
    public Animation<TextureRegion> enemySkullRightAnimation = new Animation<TextureRegion>(0.75f, enemySkullWalkRightAnimationTexture);

    TextureRegion[][] enemySkullUpTextureArray = TextureRegion.split(enemySkullUpAnimationSheet,
            enemySkullUpAnimationSheet.getWidth() / 1,
            enemySkullUpAnimationSheet.getHeight() / 13);

    TextureRegion[] enemySkullUpFrames = new TextureRegion[1 * 13];

    TextureRegion[][] enemySkullDownTextureArray = TextureRegion.split(enemySkullDownAnimationSheet,
            enemySkullDownAnimationSheet.getWidth() / 1,
            enemySkullDownAnimationSheet.getHeight() / 13);

    TextureRegion[] enemySkullDownFrames = new TextureRegion[1 * 13];

    TextureRegion[][] enemySkullLeftTextureArray = TextureRegion.split(enemySkullLeftAnimationSheet,
            enemySkullLeftAnimationSheet.getWidth() / 1,
            enemySkullLeftAnimationSheet.getHeight() / 10);

    TextureRegion[] enemySkullLeftFrames = new TextureRegion[1 * 10];

    TextureRegion[][] enemySkullRightTextureArray = TextureRegion.split(enemySkullRightAnimationSheet,
            enemySkullRightAnimationSheet.getWidth() / 1,
            enemySkullRightAnimationSheet.getHeight() / 10);

    TextureRegion[] enemySkullRightFrames = new TextureRegion[1 * 10];

    public TextureRegion enemyCrabWalkUpAnimationTexture = new TextureRegion(enemyCrabUpAnimationSheet,0,0,16,16);
    public TextureRegion enemyCrabWalkDownAnimationTexture = new TextureRegion(enemyCrabDownAnimationSheet,0,0,16,16);
    public TextureRegion enemyCrabWalkLeftAnimationTexture = new TextureRegion(enemyCrabLeftAnimationSheet,0,0,16,16);
    public TextureRegion enemyCrabWalkRightAnimationTexture = new TextureRegion(enemyCrabRightAnimationSheet,0,0,16,16);

    public Animation<TextureRegion> enemyCrabUpAnimation = new Animation<TextureRegion>(0.75f, enemyCrabWalkUpAnimationTexture);
    public Animation<TextureRegion> enemyCrabDownAnimation = new Animation<TextureRegion>(0.75f, enemyCrabWalkDownAnimationTexture);
    public Animation<TextureRegion> enemyCrabLeftAnimation = new Animation<TextureRegion>(0.75f, enemyCrabWalkLeftAnimationTexture);
    public Animation<TextureRegion> enemyCrabRightAnimation = new Animation<TextureRegion>(0.75f, enemyCrabWalkRightAnimationTexture);

    TextureRegion[][] enemyCrabUpTextureArray = TextureRegion.split(enemyCrabUpAnimationSheet,
            enemyCrabUpAnimationSheet.getWidth() / 6,
            enemyCrabUpAnimationSheet.getHeight() / 1);

    TextureRegion[] enemyCrabUpFrames = new TextureRegion[6 * 1];

    TextureRegion[][] enemyCrabDownTextureArray = TextureRegion.split(enemyCrabDownAnimationSheet,
            enemyCrabDownAnimationSheet.getWidth() / 6,
            enemyCrabDownAnimationSheet.getHeight() / 1);

    TextureRegion[] enemyCrabDownFrames = new TextureRegion[6 * 1];

    TextureRegion[][] enemyCrabLeftTextureArray = TextureRegion.split(enemyCrabLeftAnimationSheet,
            enemyCrabLeftAnimationSheet.getWidth() / 6,
            enemyCrabLeftAnimationSheet.getHeight() / 1);

    TextureRegion[] enemyCrabLeftFrames = new TextureRegion[6 * 1];

    TextureRegion[][] enemyCrabRightTextureArray = TextureRegion.split(enemyCrabRightAnimationSheet,
            enemyCrabRightAnimationSheet.getWidth() / 6,
            enemyCrabRightAnimationSheet.getHeight() / 1);

    TextureRegion[] enemyCrabRightFrames = new TextureRegion[6 * 1];



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
    public TextureRegion playerWalkDownAnimationTexture = new TextureRegion(playerWalkDownAnimationSheet,0,0,15,16);
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


    public Animation<TextureRegion> eyebeamAnimation = new Animation<TextureRegion>(0.20f, eyebeamAnimation1Texture);

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

    public TextureRegion waveAnimationTexture = new TextureRegion(waveAnimationSheet,0,0,16,16);
    public Animation<TextureRegion> waveAnimation = new Animation<TextureRegion>(0.25f, waveAnimationTexture);

    TextureRegion[][] waveTextureArray = TextureRegion.split(waveAnimationSheet,
            waveAnimationSheet.getWidth() / 16,
            waveAnimationSheet.getHeight() / 1);

    TextureRegion[] waveFrames = new TextureRegion[16 * 1];



    public TextureRegion waveDarkAnimationTexture = new TextureRegion(waveDarkAnimationSheet,0,0,16,16);
    public Animation<TextureRegion> waveDarkAnimation = new Animation<TextureRegion>(0.25f, waveDarkAnimationTexture);

    TextureRegion[][] waveDarkTextureArray = TextureRegion.split(waveDarkAnimationSheet,
            waveDarkAnimationSheet.getWidth() / 16,
            waveDarkAnimationSheet.getHeight() / 1);

    TextureRegion[] waveDarkFrames = new TextureRegion[16 * 1];



    public TextureRegion waterfall1 = new TextureRegion(waterfall1Texture,0,0,16,16);
    public TextureRegion waterfall2 = new TextureRegion(waterfall2Texture,0,0,16,16);
    public TextureRegion waterfall3 = new TextureRegion(waterfall3Texture,0,0,16,16);
    public Animation<TextureRegion> waterfallAnimation = new Animation<TextureRegion>(0.25f, waterfall1,waterfall2,waterfall3);
/*
    TextureRegion[][] waterfallTextureArray = TextureRegion.split(waterfallAnimationSheet,
            waterfallAnimationSheet.getWidth() / 3,
            waterfallAnimationSheet.getHeight() / 1);

    TextureRegion[] waterfallFrames = new TextureRegion[3 * 1];


 */



    public TextureRegion waterfallUpAnimationTexture = new TextureRegion(waterfallUpAnimationSheet,0,0,14,16);
    public Animation<TextureRegion> waterfallUpAnimation = new Animation<TextureRegion>(0.25f, waterfallUpAnimationTexture);

    TextureRegion[][] waterfallUpTextureArray = TextureRegion.split(waterfallUpAnimationSheet,
            waterfallUpAnimationSheet.getWidth() / 3,
            waterfallUpAnimationSheet.getHeight() / 1);

    TextureRegion[] waterfallUpFrames = new TextureRegion[3 * 1];

    //water inside rooms

    public TextureRegion waterAnimationTexture = new TextureRegion(waterAnimationSheet,0,0,16,16);
    public Animation<TextureRegion> waterAnimation = new Animation<TextureRegion>(0.25f, waterAnimationTexture);

    TextureRegion[][] waterTextureArray = TextureRegion.split(waterAnimationSheet,
            waterAnimationSheet.getWidth() / 5,
            waterAnimationSheet.getHeight() / 1);

    TextureRegion[] waterFrames = new TextureRegion[5 * 1];

    //public TextureRegion fireAnimationTexture = new TextureRegion(fireAnimationSheet,0,0,16,16);
    public TextureRegion flameAnimationTexture = new TextureRegion(flameAnimationSheet,0,0,16,16);
    public TextureRegion blueFlameAnimationTexture = new TextureRegion(blueFlameAnimationSheet,0,0,16,16);
    public TextureRegion blueFireAnimationTexture = new TextureRegion(blueFireAnimationSheet,0,0,16,16);
    public TextureRegion smokeAnimationTexture = new TextureRegion(smokeAnimationSheet,0,0,16,16);
    public TextureRegion flameSmokeAnimationTexture = new TextureRegion(flameSmokeAnimationSheet,0,0,16,16);
    public TextureRegion arrowAnimationTexture = new TextureRegion(fireAnimationSheet,0,0,16,16);
    public TextureRegion fireOutAnimationTexture = new TextureRegion(fireOutAnimationSheet,0,0,16,16);
    public TextureRegion flameOutAnimationTexture = new TextureRegion(flameOutAnimationSheet,0,0,16,16);

    public TextureRegion ripple1 = new TextureRegion(ripple1Texture,0,0,18,10);
    public TextureRegion ripple2 = new TextureRegion(ripple2Texture,0,0,18,10);
    public TextureRegion ripple3 = new TextureRegion(ripple3Texture,0,0,18,10);

    public Animation<TextureRegion> rippleAnimation = new Animation<TextureRegion>(0.25f, ripple1,ripple2,ripple3);

    public TextureRegion fire1 = new TextureRegion(fire1Texture,0,0,16,16);
    public TextureRegion fire2 = new TextureRegion(fire2Texture,0,0,16,16);
    public TextureRegion fire3 = new TextureRegion(fire3Texture,0,0,16,16);
    public TextureRegion fire4 = new TextureRegion(fire4Texture,0,0,16,16);
    public TextureRegion fire5 = new TextureRegion(fire5Texture,0,0,16,16);
    public TextureRegion fire6 = new TextureRegion(fire6Texture,0,0,16,16);
    public TextureRegion fire7 = new TextureRegion(fire7Texture,0,0,16,16);
    public TextureRegion fire8 = new TextureRegion(fire8Texture,0,0,16,16);
    public TextureRegion fire9 = new TextureRegion(fire9Texture,0,0,16,16);
    public TextureRegion fire10 = new TextureRegion(fire10Texture,0,0,16,16);

    public Animation<TextureRegion> fireAnimation = new Animation<TextureRegion>(0.14f, fire1,fire2,fire3,fire4,fire5,fire6,fire7,fire8,fire9,fire10);

    public TextureRegion flame1 = new TextureRegion(flame1Texture,0,0,16,16);
    public TextureRegion flame2 = new TextureRegion(flame2Texture,0,0,16,16);
    public TextureRegion flame3 = new TextureRegion(flame3Texture,0,0,16,16);
    public TextureRegion flame4 = new TextureRegion(flame4Texture,0,0,16,16);
    public TextureRegion flame5 = new TextureRegion(flame5Texture,0,0,16,16);
    public TextureRegion flame6 = new TextureRegion(flame6Texture,0,0,16,16);

    public TextureRegion candleFlame1 = new TextureRegion(candleFlame1Texture,0,0,16,16);
    public TextureRegion candleFlame2 = new TextureRegion(candleFlame2Texture,0,0,16,16);
    public TextureRegion candleFlame3 = new TextureRegion(candleFlame3Texture,0,0,16,16);
    public TextureRegion candleFlame4 = new TextureRegion(candleFlame4Texture,0,0,16,16);
    public TextureRegion candleFlame5 = new TextureRegion(candleFlame5Texture,0,0,16,16);
    public TextureRegion candleFlame6 = new TextureRegion(candleFlame6Texture,0,0,16,16);


    public Animation<TextureRegion> flameAnimation = new Animation<TextureRegion>(0.14f, flame1,flame2,flame3,flame4,flame5,flame6);

    public Animation<TextureRegion> candleFlameAnimation = new Animation<TextureRegion>(0.28f, candleFlame1,candleFlame2,candleFlame3,candleFlame4,candleFlame5,candleFlame6);
/*
    public TextureRegion waterfall1 = new TextureRegion(waterfall1Texture,0,0,16,16);
    public TextureRegion waterfall2 = new TextureRegion(waterfall2Texture,0,0,16,16);
    public TextureRegion waterfall3 = new TextureRegion(waterfall3Texture,0,0,16,16);
    public Animation<TextureRegion> waterfallAnimation = new Animation<TextureRegion>(0.25f, waterfall1,waterfall2,waterfall3);
 */

    //public Animation<TextureRegion> flameAnimation = new Animation<TextureRegion>(0.20f, flameAnimationTexture);
    public Animation<TextureRegion> blueFireAnimation = new Animation<TextureRegion>(0.25f, blueFireAnimationTexture);
    public Animation<TextureRegion> blueFlameAnimation = new Animation<TextureRegion>(0.20f, blueFlameAnimationTexture);

    public Animation<TextureRegion> flameSmokeAnimation = new Animation<TextureRegion>(0.25f, flameSmokeAnimationTexture);
    public Animation<TextureRegion> flameOutAnimation = new Animation<TextureRegion>(0.10f, flameOutAnimationTexture);

    public Animation<TextureRegion> smokeAnimation = new Animation<TextureRegion>(0.25f, smokeAnimationTexture);
    public Animation<TextureRegion> fireOutAnimation = new Animation<TextureRegion>(0.10f, fireOutAnimationTexture);


    TextureRegion[][] flameTextureArray = TextureRegion.split(flameAnimationSheet,
            flameAnimationSheet.getWidth() / 3,
            flameAnimationSheet.getHeight() / 2);

    TextureRegion[] flameFrames = new TextureRegion[3 * 2];
/*
    TextureRegion[][] fireTextureArray = TextureRegion.split(fireAnimationSheet,
            fireAnimationSheet.getWidth() / 5,
            fireAnimationSheet.getHeight() / 2);

    TextureRegion[] fireFrames = new TextureRegion[5 * 2];
 */
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


    int index = 0, index2 = 0, index3 = 0, index4 = 0, index5 = 0, index6 = 0, index7 = 0, index8 = 0, index9 = 0, index10 = 0;
    int index11 = 0, index12 = 0, index13 = 0, index14 = 0, index15 = 0, index16 = 0, index17 = 0, index18 = 0, index19 = 0, index20 = 0, index21 = 0;
    int index22 = 0, index23 = 0, index24 = 0, index25 = 0, index26 = 0, index27 = 0, index28 = 0, index29 = 0, index30 = 0, index31 = 0, index32 = 0, index33 = 0, index34 = 0, index35 = 0, index36 = 0;

    //Venus de Milo

    public TextureRegion venus = new TextureRegion(venusSheet,16,0,16,48);

    public TextureRegion venusTop = new TextureRegion(venusSheet,0,0,16,32);
    public TextureRegion venusBottom = new TextureRegion(venusSheet,0,0,16,32);

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
    public TextureRegion colBase3 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase4 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase5 = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion colBase6 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase7 = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion colBaseDamaged1 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBaseDamaged1Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBaseDamaged2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBaseDamaged2Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBaseDamaged3 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBaseDamaged3Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion colBaseLower = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase2Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase3Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase4Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase5Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion colBase6Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion colBase7Lower = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion pedestal1 = new TextureRegion(columnsTextureSheet, 0,0,16,12);
    public TextureRegion pedestal1upper = new TextureRegion(columnsTextureSheet, 0,0,16,5);
    public TextureRegion pedestal2 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal2upper = new TextureRegion(columnsTextureSheet, 0,0,16,4);
    public TextureRegion pedestal3 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal3upper = new TextureRegion(columnsTextureSheet, 0,0,16,4);
    public TextureRegion pedestal4 = new TextureRegion(columnsTextureSheet, 0,0,16,16);
    public TextureRegion pedestal4upper = new TextureRegion(columnsTextureSheet, 0,0,16,4);

    public TextureRegion brazier = new TextureRegion(columnsTextureSheet, 0,0,16,16);

    public TextureRegion statue1 = new TextureRegion(columnsTextureSheet, 0,0,15,19);
    public TextureRegion statue2 = new TextureRegion(columnsTextureSheet, 0,0,15,19);

    public TextureRegion flag1 = new TextureRegion(flagTexture, 0,0,11,16);
    public TextureRegion vine1 = new TextureRegion(vinesTexture, 0,0,16,14);

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

    //level object textures

    public TextureRegion skullTexture = new TextureRegion(skullTextureSheet, 0,0,16,16);

    public TextureRegion boneTexture = new TextureRegion(roomBackground, 0,0,16,16);
    public TextureRegion tutoTexture = new TextureRegion(tutorialTexture, 0,0,87,57);

    public TextureRegion torchLeftTexture = new TextureRegion(torchTexture, 0,0,12,9);
    public TextureRegion torchRightTexture = new TextureRegion(torchTexture, 0,0,12,9);
    public TextureRegion torchUpTexture = new TextureRegion(torchTexture, 0,0,9,12);
    public TextureRegion torchDownTexture = new TextureRegion(torchTexture, 0,0,9,12);

    public TextureRegion arrowTrap = new TextureRegion(arrowTrapTexture, 0,0,16,16);
    public TextureRegion arrowTrapActivated = new TextureRegion(arrowTrapTexture, 16,0,16,17);

    public TextureRegion fireArrowTrap = new TextureRegion(fireArrowTrapTexture, 0,0,16,21);
    public TextureRegion fireArrowTrapActivated = new TextureRegion(fireArrowTrapTexture, 16,0,16,22);

    /*
    public TextureRegion lockUpTexture = new TextureRegion(roomBackground, 160,64,16,16);
    public TextureRegion lockDownTexture = new TextureRegion(roomBackground, 192,32,16,16);
    public TextureRegion lockLeftTexture = new TextureRegion(roomBackground, 192,16,16,16);
    public TextureRegion lockRightTexture = new TextureRegion(roomBackground, 192,0,16,16);
     */

    public TextureRegion lockUpTexture = new TextureRegion(locksTexture, 0,0,32,22);
    public TextureRegion lockDownTexture = new TextureRegion(locksTexture, 0,0,32,22);
    public TextureRegion lockLeftTexture = new TextureRegion(locksTexture, 32,0,22,32);
    public TextureRegion lockRightTexture = new TextureRegion(locksTexture, 32,0,22,32);

    //Door textures

    public TextureRegion doorTopLeftWallTexture = new TextureRegion(largeWallsTexture, 0,0,16,16);
    public TextureRegion doorTopRightWallTexture = new TextureRegion(largeWallsTexture, 0,0,16,16);
    public TextureRegion doorLeftUpperWallTexture = new TextureRegion(largeWallsTexture, 0,0,16,16);
    public TextureRegion doorLeftLowerWallTexture = new TextureRegion(largeWallsTexture, 0,0,16,16);
    public TextureRegion doorRightUpperWallTexture = new TextureRegion(largeWallsTexture, 0,0,16,16);
    public TextureRegion doorRightLowerWallTexture = new TextureRegion(largeWallsTexture, 0,0,16,16);
    public TextureRegion doorBottomLeftWallTexture = new TextureRegion(largeWallsTexture, 0,0,16,16);
    public TextureRegion doorBottomRightWallTexture = new TextureRegion(largeWallsTexture, 0,0,16,16);

    /*
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
     */

    public TextureRegion doorTopLeftTexture = new TextureRegion(doorsTexture, 0,0,16,32);
    public TextureRegion doorTopRightTexture = new TextureRegion(doorsTexture, 0,0,16,32);
    public TextureRegion doorTopLeftOpenTexture = new TextureRegion(doorsOpenTexture, 0,0,16,32);
    public TextureRegion doorTopRightOpenTexture = new TextureRegion(doorsOpenTexture, 0,0,16,32);

    public TextureRegion doorLeftUpperTexture = new TextureRegion(doorsTexture, 0,0,16,32);
    public TextureRegion doorLeftLowerTexture = new TextureRegion(doorsTexture, 0,0,16,32);
    public TextureRegion doorLeftUpperOpenTexture = new TextureRegion(doorsOpenTexture, 0,0,16,32);
    public TextureRegion doorLeftLowerOpenTexture = new TextureRegion(doorsOpenTexture, 0,0,16,32);

    public TextureRegion doorRightUpperTexture = new TextureRegion(doorsTexture, 0,0,16,32);
    public TextureRegion doorRightLowerTexture = new TextureRegion(doorsTexture, 0,0,16,32);
    public TextureRegion doorRightUpperOpenTexture = new TextureRegion(doorsOpenTexture, 0,0,16,32);
    public TextureRegion doorRightLowerOpenTexture = new TextureRegion(doorsOpenTexture, 0,0,16,32);

    public TextureRegion doorBottomLeftTexture = new TextureRegion(doorsTexture, 0,0,16,32);
    public TextureRegion doorBottomRightTexture = new TextureRegion(doorsTexture, 0,0,16,32);
    public TextureRegion doorBottomLeftOpenTexture = new TextureRegion(doorsOpenTexture, 0,0,16,32);
    public TextureRegion doorBottomRightOpenTexture = new TextureRegion(doorsOpenTexture, 0,0,16,32);


    public TextureRegion gateTopLeftTexture = new TextureRegion(gatesTexture, 0,0,16,32);
    public TextureRegion gateTopRightTexture = new TextureRegion(gatesTexture, 0,0,16,32);
    public TextureRegion gateTopLeftOpenTexture = new TextureRegion(gatesOpenTexture, 0,0,16,32);
    public TextureRegion gateTopRightOpenTexture = new TextureRegion(gatesOpenTexture, 0,0,16,32);

    public TextureRegion gateLeftUpperTexture = new TextureRegion(gatesTexture, 0,0,16,32);
    public TextureRegion gateLeftLowerTexture = new TextureRegion(gatesTexture, 0,0,16,32);
    public TextureRegion gateLeftUpperOpenTexture = new TextureRegion(gatesOpenTexture, 0,0,16,32);
    public TextureRegion gateLeftLowerOpenTexture = new TextureRegion(gatesOpenTexture, 0,0,16,32);

    public TextureRegion gateRightUpperTexture = new TextureRegion(gatesTexture, 0,0,16,32);
    public TextureRegion gateRightLowerTexture = new TextureRegion(gatesTexture, 0,0,16,32);
    public TextureRegion gateRightUpperOpenTexture = new TextureRegion(gatesOpenTexture, 0,0,16,32);
    public TextureRegion gateRightLowerOpenTexture = new TextureRegion(gatesOpenTexture, 0,0,16,32);

    public TextureRegion gateBottomLeftTexture = new TextureRegion(gatesTexture, 0,0,16,32);
    public TextureRegion gateBottomRightTexture = new TextureRegion(gatesTexture, 0,0,16,32);
    public TextureRegion gateBottomLeftOpenTexture = new TextureRegion(gatesOpenTexture, 0,0,16,32);
    public TextureRegion gateBottomRightOpenTexture = new TextureRegion(gatesOpenTexture, 0,0,16,32);



    //TextureRegion doorTexture = new TextureRegion(roomDoorTexture, 0, 0, 16, 16);
    //TextureRegion holeTexture = new TextureRegion(roomHoleTexture, 0, 0, 16, 16);

    //outline player sprites
    Sprite playerSprite = new Sprite(playerTexture, 0, 0, 16, 16);
    public TextureRegion playerTextureRegion = new TextureRegion(playerTexture, 0, 0, 16, 16);
    public TextureRegion playerHead = new TextureRegion(playerHeadTexture, 0, 0, 16, 16);

    Sprite playerHeadUp = new Sprite(playerHeadTexture, 24, 0, 16, 16);
    Sprite playerHeadDown = new Sprite(playerHeadTexture, 24, 32, 16, 16);
    Sprite playerHeadLeft = new Sprite(playerHeadTexture, 0, 16, 16, 16);
    Sprite playerHeadRight = new Sprite(playerHeadTexture, 48, 16, 16, 16);
    Sprite playerHeadUpLeft = new Sprite(playerHeadTexture, 0, 0, 16, 16);
    Sprite playerHeadUpRight = new Sprite(playerHeadTexture, 48, 0, 16, 16);
    Sprite playerHeadDownLeft = new Sprite(playerHeadTexture, 0, 32, 16, 16);
    Sprite playerHeadDownRight = new Sprite(playerHeadTexture, 48, 32, 16, 16);

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

    Sprite enemySkullSprite = new Sprite(enemySkullTexture,0,0,15,16);
    Sprite enemySkullAlertedSprite = new Sprite(enemySkullTexture,15,0,15,16);

    Sprite enemySkullDownSprite = new Sprite(enemySkullTexture,0,0,16,16);
    Sprite enemySkullUpSprite = new Sprite(enemySkullTexture,15,0,16,16);
    Sprite enemySkullLeftSprite = new Sprite(enemySkullTexture,0,16,16,16);
    Sprite enemySkullRightSprite = new Sprite(enemySkullTexture,15,16,16,16);

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


    public TextureRegion enemyGryphonLeft1 = new TextureRegion(enemyGryphonLeft1Texture,0,0,16,16);
    public TextureRegion enemyGryphonLeft2 = new TextureRegion(enemyGryphonLeft2Texture,0,0,16,16);
    public TextureRegion enemyGryphonLeft3 = new TextureRegion(enemyGryphonLeft3Texture,0,0,16,16);
    public TextureRegion enemyGryphonLeft4 = new TextureRegion(enemyGryphonLeft4Texture,0,0,16,16);
    public TextureRegion enemyGryphonLeft5 = new TextureRegion(enemyGryphonLeft5Texture,0,0,16,16);
    public TextureRegion enemyGryphonLeft6 = new TextureRegion(enemyGryphonLeft6Texture,0,0,16,16);
    public TextureRegion enemyGryphonRight1 = new TextureRegion(enemyGryphonRight1Texture,0,0,16,16);
    public TextureRegion enemyGryphonRight2 = new TextureRegion(enemyGryphonRight2Texture,0,0,16,16);
    public TextureRegion enemyGryphonRight3 = new TextureRegion(enemyGryphonRight3Texture,0,0,16,16);
    public TextureRegion enemyGryphonRight4 = new TextureRegion(enemyGryphonRight4Texture,0,0,16,16);
    public TextureRegion enemyGryphonRight5 = new TextureRegion(enemyGryphonRight5Texture,0,0,16,16);
    public TextureRegion enemyGryphonRight6 = new TextureRegion(enemyGryphonRight6Texture,0,0,16,16);

    public Animation<TextureRegion> enemyGryphonLeftAnimation = new Animation<TextureRegion>(0.20f, enemyGryphonLeft1,enemyGryphonLeft2,enemyGryphonLeft3,enemyGryphonLeft4,enemyGryphonLeft5,enemyGryphonLeft6);
    public Animation<TextureRegion> enemyGryphonRightAnimation = new Animation<TextureRegion>(0.20f, enemyGryphonRight1,enemyGryphonRight2,enemyGryphonRight3,enemyGryphonRight4,enemyGryphonRight5,enemyGryphonRight6);


    public TextureRegion minotaurTextureRegion = new TextureRegion(bossMinotaurTexture, 0, 0, 64, 32);

    //HUD sprites

    public Sprite coinHUDSprite = new Sprite(coinHUDTexture, 12, 12);
    public Sprite heartSprite = new Sprite(heartHUDTexture, 16, 16);

    public Sprite coinItemSprite = new Sprite(coinItemTexture, 7,7);
    public Sprite potionItemSprite = new Sprite(potionItemTexture, 9, 11);
    public Sprite potionHUDSprite = new Sprite(potionHUDTexture, 11, 13);

    public Sprite heartItemSprite = new Sprite(heartItemTexture, 9, 8);
    public Sprite halfHeartItemSprite = new Sprite(halfheartItemTexture, 9, 8);

    public Sprite torchItemSprite = new Sprite(torchItemTexture, 12, 17);
    public Sprite torchSlotSprite = new Sprite(torchSlotTexture, 10, 15);

    public Sprite shieldItemSprite = new Sprite(shieldItemTexture, 13, 13);
    public Sprite shieldSlotSprite = new Sprite(shieldSlotTexture, 11, 11);

    public Sprite beltItemSprite = new Sprite(beltItemTexture, 15, 15);
    public Sprite beltSlotSprite = new Sprite(beltSlotTexture, 13, 13);

    public Sprite chiselItemSprite = new Sprite(chiselItemTexture, 15, 15);
    public Sprite chiselSlotSprite = new Sprite(chiselSlotTexture, 13, 13);

    public Sprite greekfireItemSprite = new Sprite(greekfireItemTexture, 14, 15);
    public Sprite greekfireSlotSprite = new Sprite(greekfireSlotTexture, 13, 13);

    public Sprite wingsItemSprite = new Sprite(wingsItemTexture, 14, 13);
    public Sprite wingsSlotSprite = new Sprite(wingsSlotTexture, 12, 11);

    public Sprite capeSprite = new Sprite(flagTexture, 11, 15);
    public Sprite capeSlotSprite = new Sprite(flagTexture, 11, 15);

    public Sprite compassSprite = new Sprite(compassTexture, 27,28);
    public Sprite compassArrowSprite = new Sprite(compassArrowTexture, 13,22);
   // public Sprite emptyCompass = new Sprite()

    Sprite shopkeeperSprite = new Sprite(shopkeeperTexture, 0,0, 16, 16);
    Sprite skullSprite = new Sprite(skullTexture, 0,0, 16, 16);
    Sprite damagedSkullSprite = new Sprite(skullTexture, 0,16, 16, 16);
    Sprite boneSprite = new Sprite(skullTexture, 16,0, 16, 16);

    public Sprite cobwebSprite = new Sprite(cobwebTexture,352,0,16,16);
    public Sprite webSprite = new Sprite(roomBackground,368,0,16,16);

    public Sprite firePitSprite = new Sprite(firePitTexture,0,0,16,16);

    public Sprite vaseSprite = new Sprite(vaseTexture,96,32,16,16);

    Sprite obstacle1Sprite = new Sprite(obstacle1Texture,80,48,16,16);
    Sprite obstacle2Sprite = new Sprite(obstacle2Texture,96,48,16,16);
    Sprite obstacle3Sprite = new Sprite(obstacle3Texture,112,48,16,16);

    Sprite obstacle4Sprite = new Sprite(obstacle4Texture,0,78,16,16);
    Sprite obstacle5Sprite = new Sprite(obstacle5Texture,16,78,16,16);
    Sprite obstacle6Sprite = new Sprite(obstacle6Texture,32,78,16,16);

    Sprite obstacle7Sprite = new Sprite(obstacle7Texture,48,78,16,16);

    Sprite candleSprite = new Sprite(candleTexture,384,64,16,16);
    Sprite candlesSprite = new Sprite(candlesTexture,400,64,16,16);

    public Sprite raisedFloorSprite = new Sprite(roomBackground,82,94,16,16);
    //ublic Sprite raisedFloorMaskSprite = new Sprite(raisedFloorMask,82,94,16,8);

    public Sprite pitPot = new Sprite(pitRubbleTexture, 0, 0, 16, 16);
    public Sprite pitColumn = new Sprite(pitRubbleTexture, 0, 0, 16, 16);
    public Sprite pitSkull = new Sprite(pitRubbleTexture, 0, 0, 16, 16);

    public Sprite oilLamp = new Sprite(oilLampTexture, 0,0,16,16);

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

        //wave animation
        for (int p = 0; p < 1; p++) {
            for (int j = 0; j < 16; j++) {
                waveFrames[index24++] = waveTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        waveAnimation = new Animation<TextureRegion>(0.30f, waveFrames);


        //wave animation
        for (int p = 0; p < 1; p++) {
            for (int j = 0; j < 16; j++) {
                waveDarkFrames[index36++] = waveDarkTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        waveDarkAnimation = new Animation<TextureRegion>(0.30f, waveDarkFrames);




/*
        //waterfall animation
        for (int p = 0; p < 1; p++) {
            for (int j = 0; j < 3; j++) {
                waterfallFrames[index25++] = waterfallTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        waterfallAnimation = new Animation<TextureRegion>(0.25f, waterfallFrames);
 */

        //water animation
        for (int p = 0; p < 1; p++) {
            for (int j = 0; j < 5; j++) {
                waterFrames[index27++] = waterTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        waterAnimation = new Animation<TextureRegion>(0.25f, waterFrames);


/*
        for (int p = 0; p < 1; p++) {
            for (int j = 0; j < 3; j++) {
                waterfallUpFrames[index26++] = waterfallUpTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        //waterfallAnimation = new Animation<TextureRegion>(0.25f, waterfallFrames);


 */
        //stun animation
        for (int p = 0; p < 1; p++) {
            for (int j = 0; j < 2; j++) {
                stunFrames[index23++] = stunTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        stunAnimation = new Animation<TextureRegion>(0.25f, stunFrames);

        //initialize enemy skull Animations
        for (int g = 0; g < 13; g++) {
            for (int w = 0; w < 1; w++) {
                enemySkullUpFrames[index28++] = enemySkullUpTextureArray[g][w];
            }
        }

        enemySkullUpAnimation = new Animation<TextureRegion>(playerWalkSpeed, enemySkullUpFrames);

        for (int g = 0; g < 13; g++) {
            for (int w = 0; w < 1; w++) {
                enemySkullDownFrames[index29++] = enemySkullDownTextureArray[g][w];
            }
        }

        enemySkullDownAnimation = new Animation<TextureRegion>(playerWalkSpeed, enemySkullDownFrames);


        for (int g = 0; g < 10; g++) {
            for (int w = 0; w < 1; w++) {
                enemySkullLeftFrames[index30++] = enemySkullLeftTextureArray[g][w];
            }
        }

        enemySkullLeftAnimation = new Animation<TextureRegion>(playerWalkSpeed, enemySkullLeftFrames);

        for (int g = 0; g < 10; g++) {
            for (int w = 0; w < 1; w++) {
                enemySkullRightFrames[index31++] = enemySkullRightTextureArray[g][w];
            }
        }

        enemySkullRightAnimation = new Animation<TextureRegion>(playerWalkSpeed, enemySkullRightFrames);


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

        //initialize enemy crab Animations
        for (int g = 0; g < 1; g++) {
            for (int w = 0; w < 6; w++) {
                enemyCrabUpFrames[index32++] = enemyCrabUpTextureArray[g][w];
            }
        }

        enemyCrabUpAnimation = new Animation<TextureRegion>(playerWalkSpeed, enemyCrabUpFrames);

        for (int g = 0; g < 1; g++) {
            for (int w = 0; w < 6; w++) {
                enemyCrabDownFrames[index33++] = enemyCrabDownTextureArray[g][w];
            }
        }

        enemyCrabDownAnimation = new Animation<TextureRegion>(playerWalkSpeed, enemyCrabDownFrames);


        for (int g = 0; g < 1; g++) {
            for (int w = 0; w < 6; w++) {
                enemyCrabLeftFrames[index34++] = enemyCrabLeftTextureArray[g][w];
            }
        }

        enemyCrabLeftAnimation = new Animation<TextureRegion>(playerWalkSpeed, enemyCrabLeftFrames);

        for (int g = 0; g < 1; g++) {
            for (int w = 0; w < 6; w++) {
                enemyCrabRightFrames[index35++] = enemyCrabRightTextureArray[g][w];
            }
        }

        enemyCrabRightAnimation = new Animation<TextureRegion>(playerWalkSpeed, enemyCrabRightFrames);








/*
        //fire animation
        for (int p = 0; p < 2; p++) {
            for (int j = 0; j < 5; j++) {
                fireFrames[index++] = fireTextureArray[p][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        fireAnimation = new Animation<TextureRegion>(0.14f, fireFrames);


 */
        //small flame animation
        /*
                for (int g = 0; g < 2; g++) {
            for (int w = 0; w < 3; w++) {
                flameFrames[index6++] = flameTextureArray[g][w];
            }
        }
         */


        // Initialize the Animation with the frame interval and array of frames
        //flameAnimation = new Animation<TextureRegion>(0.14f, flameFrames);


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

        air.setRegion(80, 64, 16, 16);

        roomFloorTexture.setRegion(2, 2, 16, 16);
        roomFloorTexture2.setRegion(22, 2, 16, 16);
        roomFloor2Texture.setRegion(2, 22, 16, 16);
        roomFloor3Texture.setRegion(82, 2, 16, 16);
        roomFloor3Texture2.setRegion(82, 22, 16, 16);
        roomFloor4Texture.setRegion(102, 2, 16, 16);
        roomFloor4Texture2.setRegion(102, 22, 16, 16);
        roomDecorativeFloorRightTexture.setRegion(240, 48, 16, 16);
        roomDecorativeFloorUpTexture.setRegion(224, 32, 16, 16);
        roomDecorativeFloorDownTexture.setRegion(208, 48, 16, 16);
        roomDecorativeFloorLeftTexture.setRegion(224, 48, 16, 16);

        roomDecorativeFloorTopLeftTexture.setRegion(208, 64, 16, 16);
        roomDecorativeFloorTopRightTexture.setRegion(224, 64, 16, 16);
        roomDecorativeFloorBottomLeftTexture.setRegion(208, 80, 16, 16);
        roomDecorativeFloorBottomLeftTexture.setRegion(208, 80, 16, 16);
        roomDecorativeFloorBottomRightTexture.setRegion(224, 80, 16, 16);

        //raisedFloor.setRegion(82, 94, 16, 16);

        pit.setRegion(53, 0, 16, 16);
        pitFloor1.setRegion(0, 0, 16, 16);
        pitFloor2.setRegion(16, 0, 16, 16);
        pitStairs.setRegion(32, 0, 16, 16);

        pitLeft.setRegion(48, 0, 16, 16);
        pitRight.setRegion(64, 0, 16, 16);
        pitBottom.setRegion(0, 16, 16, 16);

        pitBottomLeft.setRegion(48, 16, 16, 16);
        pitBottomRight.setRegion(64, 16, 16, 16);

        pitPot.setRegion(0, 0, 11, 11);
        pitColumn.setRegion(30, 0, 13, 16);
        pitSkull.setRegion(20, 0, 10, 11);

        mosaicSpartan.setRegion(0, 80, 96, 96);
        mosaicTrident.setRegion(97, 146, 32, 32);
        mosaicGryphon.setRegion(97, 80, 32, 32);
        mosaicTree.setRegion(97, 113, 32, 32);
        mosaicBull.setRegion(130, 80, 32, 32);
        mosaicLyre.setRegion(130, 113, 32, 32);

        floorTile.setRegion(82, 2, 16, 16);
        sunkenFloorTile.setRegion(82, 22, 16, 16);
        darkFloorTile.setRegion(102, 2, 16, 16);
        darkSunkenFloorTile.setRegion(102, 22, 16, 16);
        keyTile.setRegion(22, 22, 16, 16);
        key2Tile.setRegion(42, 22, 16, 16);

        oilLamp.setRegion(0,0,16,9);

        largeWallUp.setRegion(32, 0, 16, 32);
        largeWallDown.setRegion(32, 48, 16, 32);
        largeWallLeft.setRegion(0,32,32,16);
        largeWallRight.setRegion(48,32,32,16);

        largeWallTopLeft.setRegion(0, 0, 32, 32);
        largeWallTopRight.setRegion(48, 0, 32, 32);
        largeWallBottomLeft.setRegion(0,48,32,32);
        largeWallBottomRight.setRegion(48,48,32,32);

        largeWallTopLeftTurn.setRegion(81, 1, 32, 32);
        largeWallTopRightTurn.setRegion(128, 0, 32, 32);
        largeWallBottomLeftTurn.setRegion(81,47,32,32);
        largeWallBottomRightTurn.setRegion(128,48,32,32);

        innerWallUp.setRegion(16, 0, 16, 16);
        innerWallDown.setRegion(32, 0, 16, 16);
        innerWallLeft.setRegion(16,16,16,16);
        innerWallRight.setRegion(32,16,16,16);

        innerWallTLCorner.setRegion(16, 0, 16, 16);
        innerWallTRCorner.setRegion(32, 0, 16, 16);
        innerWallBLCorner.setRegion(16,16,16,16);
        innerWallBRCorner.setRegion(32,16,16,16);

        innerWallTLTurn.setRegion(33, 52, 16, 16);
        innerWallTRTurn.setRegion(49, 52, 16, 16);
        innerWallBLTurn.setRegion(33,68,16,16);
        innerWallBRTurn.setRegion(49,68,16,16);

        innerWallTLTurn2.setRegion(66, 52, 16, 16);
        innerWallTRTurn2.setRegion(82, 52, 16, 16);
        innerWallBLTurn2.setRegion(66,68,16,16);
        innerWallBRTurn2.setRegion(82,68,16,16);

        roomTopLeftWallTexture.setRegion(0, 0, 16, 16);
        roomTopWallTexture.setRegion(48, 0, 16, 16);
        roomTopRightWallTexture.setRegion(64, 0, 16, 16);
        roomLeftWallTexture.setRegion(0, 16, 16, 16);
        roomRightWallTexture.setRegion(64, 16, 16, 16);
        roomBottomLeftWallTexture.setRegion(0, 64, 16, 16);
        roomBottomWallTexture.setRegion(48, 64, 16, 16);
        roomBottomRightWallTexture.setRegion(64, 64, 16, 16);
        roomTopLeftTurnWallTexture.setRegion(32, 16, 16, 16);
        roomTopRightTurnWallTexture.setRegion(48, 16, 16, 16);
        roomBottomLeftTurnWallTexture.setRegion(32, 32, 16, 16);
        roomBottomRightTurnWallTexture.setRegion(48, 32, 16, 16);

        roomTopFence.setRegion(16, 0, 16, 16);
        roomBottomFence.setRegion(16, 32, 16, 16);
        roomLeftFence.setRegion(0, 16, 16, 16);
        roomRightFence.setRegion(32, 16, 16, 16);

        roomTopLeftCornerFence.setRegion(0, 0, 16, 16);
        roomTopRightCornerFence.setRegion(32, 0, 16, 16);
        roomBottomLeftCornerFence.setRegion(0, 32, 16, 16);
        roomBottomRightCornerFence.setRegion(32, 32, 16, 16);

        roomTopLeftTurnFence.setRegion(80, 0, 16, 16);
        roomTopRightTurnFence.setRegion(96, 0, 16, 16);
        roomBottomLeftTurnFence.setRegion(80, 16, 16, 16);
        roomBottomRightTurnFence.setRegion(96, 16, 16, 16);

        roomTopLeftEndFence.setRegion(48, 0, 16, 16);
        roomTopRightEndFence.setRegion(64, 0, 16, 16);
        roomBottomLeftEndFence.setRegion(48, 48, 16, 16);
        roomBottomRightEndFence.setRegion(64, 48, 16, 16);
        roomLeftUpEndFence.setRegion(48, 16, 16, 16);
        roomLeftDownEndFence.setRegion(48, 32, 16, 16);
        roomRightUpEndFence.setRegion(64, 16, 16, 16);
        roomRightDownEndFence.setRegion(64, 32, 16, 16);

        stairTopLeft.setRegion(0,0,16,16);
        stairTopRight.setRegion(32,0,16,16);
        stairTop.setRegion(16,0,16,16);
        stairTop2.setRegion(112,112,16,16);
        stairLeftCorner.setRegion(0,48,16,16);
        stairRightCorner.setRegion(32,48,16,16);
        stairLeft.setRegion(0,16,16,16);
        stairRight.setRegion(32,16,16,16);
        stairDown.setRegion(16,64,16,16);
        stairDownLeft.setRegion(0,64,16,16);
        stairDownRight.setRegion(32,64,16,16);

        stairUpTopLeft.setRegion(0,80,16,16);
        stairUpTopRight.setRegion(32,80,16,16);
        stairUpBottomLeft.setRegion(0,112,16,16);
        stairUpBottomRight.setRegion(32,112,16,16);

        waterRimTop.setRegion(64,80,16,16);
        waterRimBottom.setRegion(64,112,16,16);
        waterRimLeft.setRegion(48,96,16,16);
        waterRimRight.setRegion(80,96,16,16);

        drain.setRegion(0,0,24,28);

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
        colBase3.setRegion(112, 32, 16, 16);
        colBase4.setRegion(128, 32, 16, 16);
        colBaseLower.setRegion(0,48,16,11);
        colBase2Lower.setRegion(16,48,16,11);
        colBase3Lower.setRegion(112,48,16,11);
        colBase4Lower.setRegion(128,55,16,4);
        colBase5.setRegion(112, 32, 16, 16);
        colBase5Lower.setRegion(112,48,16,11);
        colBase6.setRegion(112, 0, 16, 16);
        colBase6Lower.setRegion(112,21,16,11);
        colBase7.setRegion(128, 0, 16, 16);
        colBase7Lower.setRegion(128,21,16,11);

        colBaseDamaged1.setRegion(48, 0, 16, 12);
        colBaseDamaged1Lower.setRegion(48, 28, 16, 4);
        colBaseDamaged2.setRegion(64, 0, 16, 10);
        colBaseDamaged2Lower.setRegion(64, 21, 16, 11);
        colBaseDamaged3.setRegion(80, 0, 16, 10);
        colBaseDamaged3Lower.setRegion(80, 21, 16, 11);

        pedestal1.setRegion(32, 36, 16, 12);
        pedestal1upper.setRegion(32, 32, 16, 5);
        pedestal2upper.setRegion(48, 32, 16, 4);
        pedestal2.setRegion(48, 32, 16, 16);
        pedestal3.setRegion(64, 32, 16, 16);
        pedestal3upper.setRegion(64, 32, 16, 4);
        pedestal4.setRegion(80, 32, 16, 16);
        pedestal4upper.setRegion(80, 32, 16, 4);
        statue1.setRegion(0, 59, 15, 19);
        statue2.setRegion(17, 59, 15, 19);

        brazier.setRegion(80,48,16,16);

        blockWallUp.setRegion(2,34,16,16);

        corridorRoofTexture.setRegion(464, 0, 64, 96);
        //ruinedCorridorRoofTexture.setRegion(528, 0, 64, 96);
        ruinedCorridorRoofTexture.setRegion(464, 0, 64, 96);
/*
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

 */
        doorTopLeftTexture.setRegion(31, 0, 32, 32);
        doorTopRightTexture.setRegion(44, 0, 32, 32);
        doorTopLeftOpenTexture.setRegion(32, 0, 32, 32);
        doorTopRightOpenTexture.setRegion(44, 0, 32, 32);

        doorLeftUpperTexture.setRegion(0, 31, 32, 32);
        doorLeftLowerTexture.setRegion(0, 44, 32, 32);
        doorLeftUpperOpenTexture.setRegion(0, 32, 32, 32);
        doorLeftLowerOpenTexture.setRegion(0, 44, 32, 32);

        doorRightUpperTexture.setRegion(63, 31, 31, 32);
        doorRightLowerTexture.setRegion(64, 44, 32, 32);
        doorRightUpperOpenTexture.setRegion(64, 32, 32, 32);
        doorRightLowerOpenTexture.setRegion(64, 44, 32, 32);

        doorBottomLeftTexture.setRegion(31, 63, 32, 32);
        doorBottomRightTexture.setRegion(44, 60, 32, 32);
        doorBottomLeftOpenTexture.setRegion(32, 64, 32, 32);
        doorBottomRightOpenTexture.setRegion(44, 60, 32, 32);


        doorTopLeftWallTexture.setRegion(33, 80, 16, 32);
        doorTopRightWallTexture.setRegion(49, 80, 16, 32);
        doorLeftUpperWallTexture.setRegion(1, 112, 32, 16);
        doorLeftLowerWallTexture.setRegion(1, 128, 32, 16);
        doorRightUpperWallTexture.setRegion(65, 112, 32, 16);
        doorRightLowerWallTexture.setRegion(65, 128, 32, 16);
        doorBottomLeftWallTexture.setRegion(33, 144, 16, 32);
        doorBottomRightWallTexture.setRegion(49, 144, 16, 32);


        gateTopLeftTexture.setRegion(31, 0, 32, 32);
        gateTopRightTexture.setRegion(44, 0, 32, 32);
        gateTopLeftOpenTexture.setRegion(32, 0, 32, 32);
        gateTopRightOpenTexture.setRegion(44, 0, 32, 32);

        gateLeftUpperTexture.setRegion(0, 31, 32, 32);
        gateLeftLowerTexture.setRegion(0, 44, 32, 32);
        gateLeftUpperOpenTexture.setRegion(0, 32, 32, 32);
        gateLeftLowerOpenTexture.setRegion(0, 44, 32, 32);

        gateRightUpperTexture.setRegion(63, 31, 32, 32);
        gateRightLowerTexture.setRegion(64, 44, 32, 32);
        gateRightUpperOpenTexture.setRegion(64, 32, 32, 32);
        gateRightLowerOpenTexture.setRegion(64, 44, 32, 32);

        gateBottomLeftTexture.setRegion(31, 63, 32, 32);
        gateBottomRightTexture.setRegion(44, 60, 32, 32);
        gateBottomLeftOpenTexture.setRegion(32, 64, 32, 32);
        gateBottomRightOpenTexture.setRegion(44, 60, 32, 32);



        tutoTexture.setRegion(0, 0, 87, 57);

        potionItemSprite.setRegion(0, 0, 11, 13);

        torchItemSprite.setRegion(0, 0, 10, 15);
        torchSlotSprite.setRegion(0,0,13,15);

        shieldItemSprite.setRegion(0, 0, 11, 11);
        shieldSlotSprite.setRegion(0, 0, 13, 13);

        torchLeftTexture.setRegion(0, 12, 12, 9);
        torchRightTexture.setRegion(15, 12, 12, 9);
        torchUpTexture.setRegion(9, 0, 9, 12);
        torchDownTexture.setRegion(9, 21, 9, 12);
    }
}
