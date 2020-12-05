package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ArmourController {
    private GameController gc;
    private List<Armour> armours;

    public Armour getArmourByIndex(int index) {
        return armours.get(index);
    }

    public ArmourController(GameController gc) {
        this.gc = gc;
        this.armours = new ArrayList<>();
        try (BufferedReader reader = Gdx.files.internal("data/armours.csv").reader(8192)) {
            reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                this.armours.add(new Armour(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file 'data/armours.csv'");
        }
    }
}
