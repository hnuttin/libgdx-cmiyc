package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelSightMarkerTest extends LibGdxTest {

    private LevelTiledMap levelTiledMap;

    @BeforeEach
    void setup() {
        levelTiledMap = TestLevelFactory.createLevelTiledMap("level-sight.tmx");
    }

    @Test
    void playerInMiddle() {
        new LevelSightMarker(levelTiledMap, playerOnPosition(2, 2));

        assertVistedAndInSight(0, 0, false, false);
        assertVistedAndInSight(1, 0, false, false);
        assertVistedAndInSight(2, 0, false, false);
        assertVistedAndInSight(3, 0, false, false);
        assertVistedAndInSight(4, 0, false, false);
        assertVistedAndInSight(0, 1, false, false);
        assertVistedAndInSight(1, 1, true, true);
        assertVistedAndInSight(2, 1, true, true);
        assertVistedAndInSight(3, 1, true, true);
        assertVistedAndInSight(4, 1, false, false);
        assertVistedAndInSight(0, 2, false, false);
        assertVistedAndInSight(1, 2, true, true);
        assertVistedAndInSight(2, 2, true, true);
        assertVistedAndInSight(3, 2, true, true);
        assertVistedAndInSight(4, 2, false, false);
        assertVistedAndInSight(0, 3, false, false);
        assertVistedAndInSight(1, 3, true, true);
        assertVistedAndInSight(2, 3, true, true);
        assertVistedAndInSight(3, 3, true, true);
        assertVistedAndInSight(4, 3, false, false);
        assertVistedAndInSight(0, 4, false, false);
        assertVistedAndInSight(1, 4, false, false);
        assertVistedAndInSight(2, 4, false, false);
        assertVistedAndInSight(3, 4, false, false);
        assertVistedAndInSight(4, 4, false, false);
    }

    @Test
    void playerInBottomLeftCorner() {
        new LevelSightMarker(levelTiledMap, playerOnPosition(0, 0));

        assertVistedAndInSight(0, 0, true, true);
        assertVistedAndInSight(1, 0, true, true);
        assertVistedAndInSight(2, 0, false, false);
        assertVistedAndInSight(3, 0, false, false);
        assertVistedAndInSight(4, 0, false, false);
        assertVistedAndInSight(0, 1, true, true);
        assertVistedAndInSight(1, 1, true, true);
        assertVistedAndInSight(2, 1, false, false);
        assertVistedAndInSight(3, 1, false, false);
        assertVistedAndInSight(4, 1, false, false);
        assertVistedAndInSight(0, 2, false, false);
        assertVistedAndInSight(1, 2, false, false);
        assertVistedAndInSight(2, 2, false, false);
        assertVistedAndInSight(3, 2, false, false);
        assertVistedAndInSight(4, 2, false, false);
        assertVistedAndInSight(0, 3, false, false);
        assertVistedAndInSight(1, 3, false, false);
        assertVistedAndInSight(2, 3, false, false);
        assertVistedAndInSight(3, 3, false, false);
        assertVistedAndInSight(4, 3, false, false);
        assertVistedAndInSight(0, 4, false, false);
        assertVistedAndInSight(1, 4, false, false);
        assertVistedAndInSight(2, 4, false, false);
        assertVistedAndInSight(3, 4, false, false);
        assertVistedAndInSight(4, 4, false, false);
    }

    @Test
    void playerInBottomRightCorner() {
        new LevelSightMarker(levelTiledMap, playerOnPosition(4, 0));

        assertVistedAndInSight(0, 0, false, false);
        assertVistedAndInSight(1, 0, false, false);
        assertVistedAndInSight(2, 0, false, false);
        assertVistedAndInSight(3, 0, true, true);
        assertVistedAndInSight(4, 0, true, true);
        assertVistedAndInSight(0, 1, false, false);
        assertVistedAndInSight(1, 1, false, false);
        assertVistedAndInSight(2, 1, false, false);
        assertVistedAndInSight(3, 1, true, true);
        assertVistedAndInSight(4, 1, true, true);
        assertVistedAndInSight(0, 2, false, false);
        assertVistedAndInSight(1, 2, false, false);
        assertVistedAndInSight(2, 2, false, false);
        assertVistedAndInSight(3, 2, false, false);
        assertVistedAndInSight(4, 2, false, false);
        assertVistedAndInSight(0, 3, false, false);
        assertVistedAndInSight(1, 3, false, false);
        assertVistedAndInSight(2, 3, false, false);
        assertVistedAndInSight(3, 3, false, false);
        assertVistedAndInSight(4, 3, false, false);
        assertVistedAndInSight(0, 4, false, false);
        assertVistedAndInSight(1, 4, false, false);
        assertVistedAndInSight(2, 4, false, false);
        assertVistedAndInSight(3, 4, false, false);
        assertVistedAndInSight(4, 4, false, false);
    }

    @Test
    void playerInTopRightCorner() {
        new LevelSightMarker(levelTiledMap, playerOnPosition(4, 4));

        assertVistedAndInSight(0, 0, false, false);
        assertVistedAndInSight(1, 0, false, false);
        assertVistedAndInSight(2, 0, false, false);
        assertVistedAndInSight(3, 0, false, false);
        assertVistedAndInSight(4, 0, false, false);
        assertVistedAndInSight(0, 1, false, false);
        assertVistedAndInSight(1, 1, false, false);
        assertVistedAndInSight(2, 1, false, false);
        assertVistedAndInSight(3, 1, false, false);
        assertVistedAndInSight(4, 1, false, false);
        assertVistedAndInSight(0, 2, false, false);
        assertVistedAndInSight(1, 2, false, false);
        assertVistedAndInSight(2, 2, false, false);
        assertVistedAndInSight(3, 2, false, false);
        assertVistedAndInSight(4, 2, false, false);
        assertVistedAndInSight(0, 3, false, false);
        assertVistedAndInSight(1, 3, false, false);
        assertVistedAndInSight(2, 3, false, false);
        assertVistedAndInSight(3, 3, true, true);
        assertVistedAndInSight(4, 3, true, true);
        assertVistedAndInSight(0, 4, false, false);
        assertVistedAndInSight(1, 4, false, false);
        assertVistedAndInSight(2, 4, false, false);
        assertVistedAndInSight(3, 4, true, true);
        assertVistedAndInSight(4, 4, true, true);
    }

    @Test
    void playerInTopLeftCorner() {
        new LevelSightMarker(levelTiledMap, playerOnPosition(0, 4));

        assertVistedAndInSight(0, 0, false, false);
        assertVistedAndInSight(1, 0, false, false);
        assertVistedAndInSight(2, 0, false, false);
        assertVistedAndInSight(3, 0, false, false);
        assertVistedAndInSight(4, 0, false, false);
        assertVistedAndInSight(0, 1, false, false);
        assertVistedAndInSight(1, 1, false, false);
        assertVistedAndInSight(2, 1, false, false);
        assertVistedAndInSight(3, 1, false, false);
        assertVistedAndInSight(4, 1, false, false);
        assertVistedAndInSight(0, 2, false, false);
        assertVistedAndInSight(1, 2, false, false);
        assertVistedAndInSight(2, 2, false, false);
        assertVistedAndInSight(3, 2, false, false);
        assertVistedAndInSight(4, 2, false, false);
        assertVistedAndInSight(0, 3, true, true);
        assertVistedAndInSight(1, 3, true, true);
        assertVistedAndInSight(2, 3, false, false);
        assertVistedAndInSight(3, 3, false, false);
        assertVistedAndInSight(4, 3, false, false);
        assertVistedAndInSight(0, 4, true, true);
        assertVistedAndInSight(1, 4, true, true);
        assertVistedAndInSight(2, 4, false, false);
        assertVistedAndInSight(3, 4, false, false);
        assertVistedAndInSight(4, 4, false, false);
    }

    @Test
    void movePlayer() {
        LevelSightMarker levelSightMarker = new LevelSightMarker(levelTiledMap, playerOnPosition(0, 0));
        levelSightMarker.playerMoved(new PlayerMovedEvent(playerOnPosition(1, 0)));
        levelSightMarker.playerMoved(new PlayerMovedEvent(playerOnPosition(1, 1)));
        levelSightMarker.playerMoved(new PlayerMovedEvent(playerOnPosition(2, 1)));

        assertVistedAndInSight(0, 0, true, false);
        assertVistedAndInSight(1, 0, true, true);
        assertVistedAndInSight(2, 0, true, true);
        assertVistedAndInSight(3, 0, true, true);
        assertVistedAndInSight(4, 0, false, false);
        assertVistedAndInSight(0, 1, true, false);
        assertVistedAndInSight(1, 1, true, true);
        assertVistedAndInSight(2, 1, true, true);
        assertVistedAndInSight(3, 1, true, true);
        assertVistedAndInSight(4, 1, false, false);
        assertVistedAndInSight(0, 2, true, false);
        assertVistedAndInSight(1, 2, true, true);
        assertVistedAndInSight(2, 2, true, true);
        assertVistedAndInSight(3, 2, true, true);
        assertVistedAndInSight(4, 2, false, false);
        assertVistedAndInSight(0, 3, false, false);
        assertVistedAndInSight(1, 3, false, false);
        assertVistedAndInSight(2, 3, false, false);
        assertVistedAndInSight(3, 3, false, false);
        assertVistedAndInSight(4, 3, false, false);
        assertVistedAndInSight(0, 4, false, false);
        assertVistedAndInSight(1, 4, false, false);
        assertVistedAndInSight(2, 4, false, false);
        assertVistedAndInSight(3, 4, false, false);
        assertVistedAndInSight(4, 4, false, false);
    }

    private Player playerOnPosition(int x, int y) {
        return new Player(null, new Position(x, y), 1);
    }

    private void assertVistedAndInSight(int x, int y, boolean visited, boolean inSight) {
        assertThat(levelTiledMap.getLevelCell(x, y).isVisited()).isEqualTo(visited);
        assertThat(levelTiledMap.getLevelCell(x, y).isInSight()).isEqualTo(inSight);
    }

}