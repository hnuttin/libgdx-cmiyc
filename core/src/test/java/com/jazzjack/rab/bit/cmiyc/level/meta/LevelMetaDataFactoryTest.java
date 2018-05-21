package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jazzjack.rab.bit.cmiyc.common.Predictability;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandleResolver;
import com.jazzjack.rab.bit.cmiyc.level.LevelTiledMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelMetaDataFactoryTest {

    private final ObjectTypeParser objectTypeParser = new ObjectTypeParser(new ClasspathFileHandle("objecttypes.xml"));
    private final LevelMetaDataFactory levelMetaDataFactory = new LevelMetaDataFactory(objectTypeParser);

    private LevelTiledMap tiledMap;

    @BeforeEach
    void setup() {
        TmxMapLoader tmxMapLoader = new TmxMapLoader(new ClasspathFileHandleResolver());
        tiledMap = new LevelTiledMap(tmxMapLoader.load("map-with-markers.tmx"));
    }

    @Test
    void expectStartPositionCorrectlyCreated() {
        LevelMetaData levelMetaData = levelMetaDataFactory.create(tiledMap);

        assertThat(levelMetaData.getStartPosition()).isNotNull();
        assertThat(levelMetaData.getStartPosition().getX()).isEqualTo(1);
        assertThat(levelMetaData.getStartPosition().getY()).isEqualTo(2);
    }

    @Test
    void expectEndPositionCorrectlyCreated() {
        LevelMetaData levelMetaData = levelMetaDataFactory.create(tiledMap);

        assertThat(levelMetaData.getEndPosition()).isNotNull();
        assertThat(levelMetaData.getEndPosition().getX()).isEqualTo(18);
        assertThat(levelMetaData.getEndPosition().getY()).isEqualTo(4);
    }

    @Test
    void expectEnemiesCorrectlyCreated() {
        LevelMetaData levelMetaData = levelMetaDataFactory.create(tiledMap);

        assertThat(levelMetaData.getEnemies()).hasSize(3);
        assertEnemy(levelMetaData.getEnemies().get(0), "enemy1", Predictability.HIGH, 6, 7);
        assertEnemy(levelMetaData.getEnemies().get(1), "enemy2", Predictability.MEDIUM, 10, 4);
        assertEnemy(levelMetaData.getEnemies().get(2), "enemy3", Predictability.LOW, 16, 1);
    }

    private void assertEnemy(EnemyMarkerObject enemy1, String name, Predictability predictability, int x, int y) {
        assertThat(enemy1.getType()).isEqualTo(name);
        assertThat(enemy1.getPredictability()).isEqualTo(predictability);
        assertThat(enemy1.getX()).isEqualTo(x);
        assertThat(enemy1.getY()).isEqualTo(y);
    }
}