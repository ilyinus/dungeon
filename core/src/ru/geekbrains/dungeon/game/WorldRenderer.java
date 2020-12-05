package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.screens.ScreenManager;

public class WorldRenderer {
    private GameController gc;
    private SpriteBatch batch;
    private TextureRegion cursorTexture;
    private BitmapFont font18;
    private BitmapFont font24;
    private StringBuilder stringHelper;

    public WorldRenderer(GameController gc, SpriteBatch batch) {
        this.gc = gc;
        this.batch = batch;
        this.cursorTexture = Assets.getInstance().getAtlas().findRegion("cursor");
        this.font18 = Assets.getInstance().getAssetManager().get("fonts/font18.ttf");
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.stringHelper = new StringBuilder();
    }

    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gc.getGameMap().renderGround(batch);

        Color cursorColor = Color.WHITE;
        if (gc.getUnitController().getMonsterController().getMonsterInCell(gc.getCursorX(), gc.getCursorY()) != null) {
            cursorColor = Color.RED;
        }

        batch.setColor(cursorColor.r, cursorColor.g, cursorColor.b, 0.5f + 0.1f * (float) Math.sin(gc.getWorldTimer() * 8.0f));
        batch.draw(cursorTexture, gc.getCursorX() * GameMap.CELL_SIZE, gc.getCursorY() * GameMap.CELL_SIZE);
        batch.setColor(1, 1, 1, 1);

        gc.getGameMap().renderObjects(batch);
        gc.getUnitController().render(batch, font18);
        gc.getProjectileController().render(batch);
        gc.getInfoController().render(batch, font18);
        gc.getEffectController().render(batch);
        batch.end();

        float camX = ScreenManager.getInstance().getCamera().position.x;
        float camY = ScreenManager.getInstance().getCamera().position.y;
        ScreenManager.getInstance().resetCamera();
        gc.getStage().draw();
        ScreenManager.getInstance().pointCameraTo(camX, camY);
    }
}
