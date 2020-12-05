package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.helpers.Poolable;


public class Effect implements Poolable {
    private TextureRegion[][] textures;
    private boolean active;
    private Vector2 position;
    private float time;
    private float maxTime;
    private float timePerFrame;
    private int index;

    @Override
    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Effect() {
        this.active = false;
        this.textures = new TextureRegion(Assets.getInstance().getAtlas().findRegion("fxs")).split(60, 60);
        this.position = new Vector2(0.0f, 0.0f);
        this.timePerFrame = 0.05f;
        this.time = 0.0f;
        this.maxTime = 1.5f;
    }

    public void setup(float x, float y, int index) {
        this.position.set(x, y);
        this.active = true;
        this.time = 0.0f;
        this.index = index;
        this.maxTime = textures[index].length * timePerFrame;
    }

    public TextureRegion getCurrentFrame() {
        return textures[index][(int) (time / timePerFrame) % textures[index].length];
    }

    public void update(float dt) {
        time += dt;
        if (time >= maxTime) {
            active = false;
        }
    }
}
