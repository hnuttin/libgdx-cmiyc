package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level_PlayerPushEnemiesTest extends LibGdxTest {

    private Level level;

    @BeforeEach
    void beforeEach() {
        level = TestLevelFactory.createLevel("player-push-enemies.tmx");
    }

    @Test
    void expectPlayerToPushTwoEnemies() {
        Player player = level.getPlayer();

        CollisionResult collisionResult = player.moveToDirection(Direction.UP);
        assertThat(collisionResult.isCollision()).isFalse();

        collisionResult = player.moveToDirection(Direction.RIGHT);
        assertThat(collisionResult.isCollision()).isFalse();
        assertThat(player.getHp()).isEqualTo(3);
        assertThat(player.getX()).isEqualTo(1);
        assertThat(player.getY()).isEqualTo(1);

        assertThat(level.getEnemies().get(0).getX()).isEqualTo(2);
        assertThat(level.getEnemies().get(0).getY()).isEqualTo(1);
        assertThat(level.getEnemies().get(1).getX()).isEqualTo(3);
        assertThat(level.getEnemies().get(1).getY()).isEqualTo(1);
    }
}