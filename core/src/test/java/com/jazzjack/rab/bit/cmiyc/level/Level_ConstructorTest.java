package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.shared.Predictability;
import com.jazzjack.rab.bit.cmiyc.shared.Sense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level_ConstructorTest extends LibGdxTest {

    private Level level;

    @BeforeEach
    void beforeEach() {
        level = TestLevelFactory.createLevel("map-with-markers.tmx");
    }

    @Test
    void expectPlayerCreatedOnStartPosition() {
        assertThat(level.getPlayer().getX()).isEqualTo(1);
        assertThat(level.getPlayer().getY()).isEqualTo(2);
    }

    @Test
    void expectEnemiesCreated() {
        assertThat(level.getEnemies()).hasSize(3);
        assertEnemy(level.getEnemies().get(0), "enemy1", Predictability.HIGH, Sense.LOW, 6, 7);
        assertEnemy(level.getEnemies().get(1), "enemy2", Predictability.MEDIUM, Sense.MEDIUM, 10, 4);
        assertEnemy(level.getEnemies().get(2), "enemy3", Predictability.LOW, Sense.HIGH, 16, 1);
    }

    private void assertEnemy(Enemy enemy, String name, Predictability predictability, Sense sense, int x, int y) {
        assertThat(enemy.getName()).isEqualTo(name);
        assertThat(enemy.getPredictability()).isEqualTo(predictability);
        assertThat(enemy.getSense()).isEqualTo(sense);
        assertThat(enemy.getX()).isEqualTo(x);
        assertThat(enemy.getY()).isEqualTo(y);
    }

}