package ru.geekbrains.dungeon.game;

import lombok.Data;

@Data
public class Weapon {
    public enum Type {
        SPEAR, SWORD, MACE, AXE, BOW
    }

    Type type;
    int damage;
    int radius;
    int fxIndex;

    public Weapon(Type type, int damage, int radius, int fxIndex) {
        this.type = type;
        this.damage = damage;
        this.radius = radius;
        this.fxIndex = fxIndex;
    }
}
