package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameController {
    private SpriteBatch batch;
    private ProjectileController projectileController;
    private UnitController unitController;
    private GameMap gameMap;
    private int round;

    private int cursorX, cursorY;

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public ProjectileController getProjectileController() {
        return projectileController;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public UnitController getUnitController() {
        return unitController;
    }

    public GameController(SpriteBatch batch) {
        this.batch = batch;
        this.gameMap = new GameMap();
        this.unitController = new UnitController(this);
        this.projectileController = new ProjectileController();
        this.unitController.init();
        this.round = 1;
    }

    public void update(float dt) {
        cursorX = (Gdx.input.getX() / GameMap.CELL_SIZE);
        cursorY = ((720 - Gdx.input.getY()) / GameMap.CELL_SIZE);
        projectileController.update(dt);
        unitController.update(dt);
    }
}
