package ru.geekbrains.dungeon.game.units;

import com.badlogic.gdx.math.MathUtils;
import lombok.Getter;

@Getter
public class Stats {
    int level;
    int hp, maxHp;
    int attackPoints, minAttackPoints, maxAttackPoints;
    int movePoints, minMovePoints, maxMovePoints;
    int visionRadius;

    public Stats(int level, int maxHp, int minAttackPoints, int maxAttackPoint, int minMovePoints, int maxMovePoint) {
        this.level = level;
        this.maxHp = maxHp;
        this.hp = this.maxHp;
        this.minAttackPoints = minAttackPoints;
        this.maxAttackPoints = maxAttackPoint;
        this.minMovePoints = minMovePoints;
        this.maxMovePoints = maxMovePoint;
        this.visionRadius = 5;
    }

    public void restorePoints() {
        attackPoints = MathUtils.random(minAttackPoints, maxAttackPoints);
        movePoints = MathUtils.random(minMovePoints, maxMovePoints);
    }

    public void restoreHp(int amount) {
        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    public void fullRestoreHp() {
        hp = maxHp;
    }

    public void resetPoints() {
        attackPoints = 0;
        movePoints = 0;
    }

    public boolean doIHaveAnyPoints() {
        return attackPoints > 0 || movePoints > 0;
    }
}
