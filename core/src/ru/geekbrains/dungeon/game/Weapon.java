package ru.geekbrains.dungeon.game;

import lombok.Data;

@Data
public class Weapon {
    public enum Type {
        SPEAR("Spear"), SWORD("Sword"), MACE("Mace"), AXE("Axe"), BOW("Bow");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    Type type;
    int damage;
    int radius;

    public Weapon(Type type, int damage, int radius) {
        this.type = type;
        this.damage = damage;
        this.radius = radius;
    }
}
