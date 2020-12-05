package ru.geekbrains.dungeon.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import ru.geekbrains.dungeon.game.Weapon;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.game.GameController;
import ru.geekbrains.dungeon.screens.ScreenManager;

@Getter
public class Hero extends Unit {
    private String name;

    private Group guiGroup;
    private Label hpLabel;
    private Label goldLabel;

    public Hero(GameController gc) {
        super(gc, 1, 1, 10, "Hero");
        this.name = "Sir Lancelot";
        this.textureHp = Assets.getInstance().getAtlas().findRegion("hp");
        this.weapon = new Weapon(Weapon.Type.SPEAR, 2, 2, 0);
        this.createGui();
    }

    public void update(float dt) {
        super.update(dt);
        if (Gdx.input.justTouched() && canIMakeAction()) {
            Monster m = gc.getUnitController().getMonsterController().getMonsterInCell(gc.getCursorX(), gc.getCursorY());
            if (m != null && canIAttackThisTarget(m, 1)) {
                attack(m);
            } else {
                goTo(gc.getCursorX(), gc.getCursorY());
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            tryToEndTurn();
        }
        updateGui();
    }

    public void tryToEndTurn() {
        if (gc.getUnitController().isItMyTurn(this) && isStayStill()) {
            stats.resetPoints();
        }
    }

    public void updateGui() {
        stringHelper.setLength(0);
        stringHelper.append(stats.hp).append(" / ").append(stats.maxHp);
        hpLabel.setText(stringHelper);

        stringHelper.setLength(0);
        stringHelper.append(gold);
        goldLabel.setText(stringHelper);
    }

    public void createGui() {
        this.guiGroup = new Group();
        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        BitmapFont font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        Label.LabelStyle labelStyle = new Label.LabelStyle(font24, Color.WHITE);
        this.hpLabel = new Label("", labelStyle);
        this.goldLabel = new Label("", labelStyle);
        this.hpLabel.setPosition(155, 30);
        this.goldLabel.setPosition(400, 30);
        Image backgroundImage = new Image(Assets.getInstance().getAtlas().findRegion("upperPanel"));
        this.guiGroup.addActor(backgroundImage);
        this.guiGroup.addActor(hpLabel);
        this.guiGroup.addActor(goldLabel);
        this.guiGroup.setPosition(0, ScreenManager.WORLD_HEIGHT - 60);

        skin.dispose();
    }
}
