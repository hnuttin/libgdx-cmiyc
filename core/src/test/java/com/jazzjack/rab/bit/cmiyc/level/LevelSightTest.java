package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelSightTest extends LibGdxTest {

    private TiledMapTileLayer mapLayer;

    @BeforeEach
    void setup() {
        mapLayer = TestLevelFactory.createLevelTiledMap("level-sight.tmx").getMapLayer();
    }

    @Test
    void expectPlayerInMiddleOfMapToHaveSightInAllDirections() {
        LevelSight levelSight = new LevelSight(mapLayer, new Player(null, new Position(2, 2), 1));

        assertVistedAndInSight(levelSight, 0, 0, false, false);
        assertVistedAndInSight(levelSight, 1, 0, false, false);
        assertVistedAndInSight(levelSight, 2, 0, false, false);
        assertVistedAndInSight(levelSight, 3, 0, false, false);
        assertVistedAndInSight(levelSight, 4, 0, false, false);
        assertVistedAndInSight(levelSight, 0, 1, false, false);
        assertVistedAndInSight(levelSight, 1, 1, true, true);
        assertVistedAndInSight(levelSight, 2, 1, true, true);
        assertVistedAndInSight(levelSight, 3, 1, true, true);
        assertVistedAndInSight(levelSight, 4, 1, false, false);
        assertVistedAndInSight(levelSight, 0, 2, false, false);
        assertVistedAndInSight(levelSight, 1, 2, true, true);
        assertVistedAndInSight(levelSight, 2, 2, true, true);
        assertVistedAndInSight(levelSight, 3, 2, true, true);
        assertVistedAndInSight(levelSight, 4, 2, false, false);
        assertVistedAndInSight(levelSight, 0, 3, false, false);
        assertVistedAndInSight(levelSight, 1, 3, true, true);
        assertVistedAndInSight(levelSight, 2, 3, true, true);
        assertVistedAndInSight(levelSight, 3, 3, true, true);
        assertVistedAndInSight(levelSight, 4, 3, false, false);
        assertVistedAndInSight(levelSight, 0, 4, false, false);
        assertVistedAndInSight(levelSight, 1, 4, false, false);
        assertVistedAndInSight(levelSight, 2, 4, false, false);
        assertVistedAndInSight(levelSight, 3, 4, false, false);
        assertVistedAndInSight(levelSight, 4, 4, false, false);
    }

    private void assertVistedAndInSight(LevelSight levelSight, int x, int y, boolean visited, boolean inSight) {
        Position position = new Position(y, y);
        assertThat(levelSight.isVisited(position)).isEqualTo(visited);
        assertThat(levelSight.isInSight(position)).isEqualTo(inSight);
    }

}