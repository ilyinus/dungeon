package ru.geekbrains.dungeon.game;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Armour {
    private String title;
    private int level;
    private int genericDefence;
    private Map<Weapon.Type, Integer> resistance;

    // title,level,generic,spear,sword,mace,axe,bow
    public Armour(String line) {
        String[] tokens = line.split(",");
        this.title = tokens[0].trim();
        this.level = Integer.parseInt(tokens[1].trim());
        this.genericDefence = Integer.parseInt(tokens[2].trim());
        this.resistance = new HashMap<>();
        this.resistance.put(Weapon.Type.SPEAR, Integer.parseInt(tokens[3].trim()));
        this.resistance.put(Weapon.Type.SWORD, Integer.parseInt(tokens[4].trim()));
        this.resistance.put(Weapon.Type.MACE, Integer.parseInt(tokens[5].trim()));
        this.resistance.put(Weapon.Type.AXE, Integer.parseInt(tokens[6].trim()));
        this.resistance.put(Weapon.Type.BOW, Integer.parseInt(tokens[7].trim()));
    }
}
