package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level_PlayerCollisionTest extends LibGdxTest {

    private Level level;

    @BeforeEach
    void beforeEach() {
        level = TestLevelFactory.createLevel("player-level-collision.tmx");
    }

    @Test
    void expectPlayerToPushEnemy() {
        CollisionResult collisionResult = level.getPlayer().moveToDirection(Direction.UP);
        assertThat(collisionResult.isCollision()).isFalse();
        assertThat(level.getPlayer().getActionPointsConsumed()).isEqualTo(1);
        assertThat(level.getPlayer().getX()).isEqualTo(0);
        assertThat(level.getPlayer().getY()).isEqualTo(1);

        collisionResult = level.getPlayer().moveToDirection(Direction.RIGHT);
        assertThat(collisionResult.isCollision()).isTrue();
        assertThat(level.getPlayer().getActionPointsConsumed()).isEqualTo(1);
        assertThat(level.getPlayer().getX()).isEqualTo(0);
        assertThat(level.getPlayer().getY()).isEqualTo(1);
    }
}