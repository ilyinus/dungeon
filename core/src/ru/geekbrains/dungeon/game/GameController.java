package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;
import lombok.Data;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.screens.ScreenManager;

import java.io.IOException;

@Data
public class GameController {
    public static final int INITIAL_MONSTERS_COUNT = 1;
    public static final int TURNS_COUNT = 5;

    private SpriteBatch batch;
    private Stage stage;
    private ProjectileController projectileController;
    private ArmourController armourController;
    private InfoController infoController;
    private UnitController unitController;
    private EffectController effectController;
    private GameMap gameMap;

//    private Music music;
//    private Sound swordSound;

    private Vector2 mouse;
    private Vector2 pressedMouse;

    private int cursorX, cursorY;
    private int round;
    private float worldTimer;

    public GameController(SpriteBatch batch) {
        this.batch = batch;
        this.stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        this.mouse = new Vector2(0, 0);
        this.pressedMouse = new Vector2(0, 0);
        this.gameMap = new GameMap();
        this.armourController = new ArmourController(this);
        this.effectController = new EffectController();
        this.unitController = new UnitController(this);
        this.projectileController = new ProjectileController();
        this.infoController = new InfoController();
        this.unitController.init(INITIAL_MONSTERS_COUNT);
        this.round = 1;
        this.createGui();
        this.stage.addActor(unitController.getHero().getGuiGroup());
//        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/theme.mp3"));
//        this.music.setLooping(true);
//        this.music.play();
//
//        this.swordSound = Gdx.audio.newSound(Gdx.files.internal("sounds/swordClash.wav"));
//        this.swordSound.play();
    }

    public void roundUp() {
        round++;
        unitController.startRound();
        if (round % 3 == 0) {
            unitController.createMonsterInRandomCell();
        }
    }

    public boolean isCellEmpty(int cx, int cy) {
        return gameMap.isCellPassable(cx, cy) && unitController.isCellFree(cx, cy);
    }

    public void update(float dt) {
        worldTimer += dt;
        checkMouse();
        projectileController.update(dt);
        unitController.update(dt);
        infoController.update(dt);
        effectController.update(dt);

        stage.act(dt);
    }

    public void checkMouse() {
        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        ScreenManager.getInstance().getViewport().unproject(mouse);

        if (Gdx.input.isTouched() && Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            float camX = ScreenManager.getInstance().getCamera().position.x;
            float camY = ScreenManager.getInstance().getCamera().position.y;

            camX += pressedMouse.x - mouse.x;
            camY += pressedMouse.y - mouse.y;

            mouse.x += pressedMouse.x - mouse.x;
            mouse.y += pressedMouse.y - mouse.y;

            ScreenManager.getInstance().pointCameraTo(camX, camY);
        }

        cursorX = (int) (mouse.x / GameMap.CELL_SIZE);
        cursorY = (int) (mouse.y / GameMap.CELL_SIZE);

        pressedMouse.set(mouse);
    }

    public void createGui() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        BitmapFont font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");

        TextButton.TextButtonStyle menuBtnStyle = new TextButton.TextButtonStyle(
                skin.getDrawable("smButton"), null, null, font24);

        final TextButton btnEndTurn = new TextButton("End turn", menuBtnStyle);
        btnEndTurn.setPosition(0, 0);

        final TextButton btnGoToMenu = new TextButton("Menu", menuBtnStyle);
        btnGoToMenu.setPosition(140, 0);

        btnEndTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                unitController.getHero().tryToEndTurn();
            }
        });

        btnGoToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
            }
        });

        Group menuGroup = new Group();
        menuGroup.addActor(btnEndTurn);
        menuGroup.addActor(btnGoToMenu);
        menuGroup.setPosition(20, ScreenManager.WORLD_HEIGHT - 60);

        stage.addActor(menuGroup);
        skin.dispose();
    }
}
