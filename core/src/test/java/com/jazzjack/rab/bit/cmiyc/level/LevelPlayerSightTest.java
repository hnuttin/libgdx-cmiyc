package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhase;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhaseEvent;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelPlayerSightTest extends LibGdxTest {

    private Level level;

    @BeforeEach
    void setup() {
        level = TestLevelFactory.createLevel("level-sight.tmx");
    }

    @Test
    void expectTilesMarkedAndEnemiesMarkedAndTriggered() {
        Player player = level.getPlayer();

        player.moveToDirection(Direction.UP);
        assertThat(level.getEnemies().get(0).isTriggered()).isTrue();

        player.moveToDirection(Direction.RIGHT);
        assertThat(level.getEnemies().get(1).isTriggered()).isTrue();

        player.moveToDirection(Direction.RIGHT);
        player.moveToDirection(Direction.RIGHT);

        assertVistedAndInSight(0, 0, true, false);
        assertVistedAndInSight(1, 0, true, true);
        assertVistedAndInSight(2, 0, true, true);
        assertVistedAndInSight(3, 0, true, true);
        assertVistedAndInSight(4, 0, true, true);
        assertVistedAndInSight(0, 1, true, false);
        assertVistedAndInSight(1, 1, true, true);
        assertVistedAndInSight(2, 1, true, true);
        assertVistedAndInSight(3, 1, true, true);
        assertVistedAndInSight(4, 1, true, true);
        assertVistedAndInSight(0, 2, true, false);
        assertVistedAndInSight(1, 2, true, true);
        assertVistedAndInSight(2, 2, true, true);
        assertVistedAndInSight(3, 2, true, true);
        assertVistedAndInSight(4, 2, true, true);
        assertVistedAndInSight(0, 3, true, false);
        assertVistedAndInSight(1, 3, true, true);
        assertVistedAndInSight(2, 3, true, true);
        assertVistedAndInSight(3, 3, true, true);
        assertVistedAndInSight(4, 3, true, true);
        assertVistedAndInSight(0, 4, false, false);
        assertVistedAndInSight(1, 4, false, false);
        assertVistedAndInSight(2, 4, false, false);
        assertVistedAndInSight(3, 4, false, false);
        assertVistedAndInSight(4, 4, false, false);
        assertThat(level.getLevelPlayerSight().isEnemyInSight(level.getEnemies().get(0))).isTrue();
        assertThat(level.getLevelPlayerSight().isEnemyInSight(level.getEnemies().get(1))).isTrue();

        level.getLevelPlayerSight().newGamePhase(new GamePhaseEvent(GamePhase.PLAYER_TURN));
        assertThat(level.getLevelPlayerSight().isEnemyInSight(level.getEnemies().get(0))).isFalse();
        assertThat(level.getLevelPlayerSight().isEnemyInSight(level.getEnemies().get(1))).isTrue();

        level.getEnemies().get(0).moveToDirection(Direction.RIGHT);
        assertThat(level.getLevelPlayerSight().isEnemyInSight(level.getEnemies().get(0))).isTrue();
    }

    private void assertVistedAndInSight(int x, int y, boolean visited, boolean inSight) {
        assertThat(level.getLevelPlayerSight().isTileVisited(x, y)).isEqualTo(visited);
        assertThat(level.getLevelPlayerSight().isTileInSight(x, y)).isEqualTo(inSight);
    }

}