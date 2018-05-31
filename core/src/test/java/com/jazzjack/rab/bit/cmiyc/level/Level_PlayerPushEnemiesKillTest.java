package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level_PlayerPushEnemiesKillTest extends LibGdxTest {

    private Level level;

    @BeforeEach
    void beforeEach() {
        level = TestLevelFactory.createLevel("player-push-enemies-kill.tmx");
    }

    @Test
    void expectPushedEnemyKilled() {
        CollisionResult collisionResult = level.getPlayer().moveToDirection(Direction.UP);
        assertThat(collisionResult.isCollision()).isFalse();
        assertThat(level.getPlayer().getActionPointsConsumed()).isEqualTo(1);
        assertThat(level.getPlayer().getX()).isEqualTo(0);
        assertThat(level.getPlayer().getY()).isEqualTo(1);

        collisionResult = level.getPlayer().moveToDirection(Direction.RIGHT);
        assertThat(collisionResult.isCollision()).isFalse();
        assertThat(level.getPlayer().getActionPointsConsumed()).isEqualTo(2);

        assertThat(level.getPlayer().getX()).isEqualTo(1);
        assertThat(level.getPlayer().getY()).isEqualTo(1);
        assertThat(level.getEnemies()).hasSize(1);
        assertThat(level.getEnemies().get(0).getX()).isEqualTo(2);
        assertThat(level.getEnemies().get(0).getY()).isEqualTo(1);
    }
}