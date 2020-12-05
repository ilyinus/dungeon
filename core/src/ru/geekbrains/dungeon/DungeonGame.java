package ru.geekbrains.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.dungeon.game.GameController;
import ru.geekbrains.dungeon.game.GameMap;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.screens.ScreenManager;

public class DungeonGame extends Game {
    private SpriteBatch batch;

    // Домашнее задание:
    // 1. Разобраться с кодом
    // 2. На деревьях растут ягоды (не на всех), их можно собирать. Каждый 5 ход,
    // на случайном дереве растет ягода
    // 3. При выполнении действий сытость персонажа падает, если сытости падать некуда,
    // то начинает падать здоровье
    // 4. Если кликнуть на дерево, то персонаж съедает все ягоды с него и заполняет сытость
    // 5. * Когда здоровье падает дод 0, нужно перекинуть игрока на экран Game Over

    // Идея механики:
    // 1. На ваш ход выбатает случайный набор действий (2 шага, 4 атаки, 1 спец действие)
    // 2. На движение тратится по 1 шаг
    // 3. На обычную атаку требуется 1 атака, на усиленную 3 атаки
    // 4. Спец действия тратятся на лечение/приготовление/крафт/...
    // 5. Можно выбирать оружие, на каждого монстра оружие действует по разному, даже в рамках одного класса
    // 6. Добавить туман войны
    // *. Для монстров то же самое

    // Идеи по улучшению игры:
    // 1. Можно добавить конвертацию очков действий (например, 2 очка передвижения
    // можно потратить на 1 атаку и наоборот).
    // 2. Добавить кнопку «Ожидание», при нажатии на которую, герой перед своим ходом
    // будет либо ждать конца хода всех монстров, либо пропускать ход и получать бонусы
    // (ходы, атаки) к следующему ходу.
    // 3. Установить «линии» с увеличением силы монстров. Например, с какой-то
    // координаты по Х и Y (или с определённого количества клеток от героя)
    // монстры становятся сильнее.
    // 4. Сонстры должны стараться не идти на ту клетку с которой пришли
    // А еще, если запилить механику для героя: Если у него мало ХП а на него агрится бот, пусть герой сможет от него откупиться золотом
    // 5. Зонирование карты
    // 6. Квесты
    // 7.

    @Override
    public void create() {
        batch = new SpriteBatch();
        ScreenManager.getInstance().init(this, batch);
        ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();
        getScreen().render(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
