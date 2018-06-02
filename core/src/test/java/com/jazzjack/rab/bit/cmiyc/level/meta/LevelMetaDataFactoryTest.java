package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandleResolver;
import com.jazzjack.rab.bit.cmiyc.level.LevelTiledMap;
import com.jazzjack.rab.bit.cmiyc.shared.Predictability;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelMetaDataFactoryTest {

    private final ObjectTypeParser objectTypeParser = new ObjectTypeParser(new ClasspathFileHandle("objecttypes.xml"));
    private final LevelMetaDataFactory levelMetaDataFactory = new LevelMetaDataFactory(objectTypeParser);

    private LevelTiledMap levelTiledMap;

    @BeforeEach
    void beforeEach() {
        TmxMapLoader tmxMapLoader = new TmxMapLoader(new ClasspathFileHandleResolver());
        levelTiledMap = new LevelTiledMap(tmxMapLoader.load("map-with-markers.tmx"));
    }

    @Test
    void expectStartPositionCorrectlyCreated() {
        LevelMetaData levelMetaData = levelMetaDataFactory.create(levelTiledMap);

        assertThat(levelMetaData.getStartPosition()).isNotNull();
        assertThat(levelMetaData.getStartPosition().getX()).isEqualTo(1);
        assertThat(levelMetaData.getStartPosition().getY()).isEqualTo(2);
    }

    @Test
    void expectEndPositionCorrectlyCreated() {
        LevelMetaData levelMetaData = levelMetaDataFactory.create(levelTiledMap);

        assertThat(levelMetaData.getEndPosition()).isNotNull();
        assertThat(levelMetaData.getEndPosition().getX()).isEqualTo(18);
        assertThat(levelMetaData.getEndPosition().getY()).isEqualTo(4);
    }

    @Test
    void expectEnemiesCorrectlyCreated() {
        LevelMetaData levelMetaData = levelMetaDataFactory.create(levelTiledMap);

        assertThat(levelMetaData.getEnemies()).hasSize(3);
        assertEnemy(levelMetaData.getEnemies().get(0), "enemy1", Predictability.HIGH, 6, 7);
        assertEnemy(levelMetaData.getEnemies().get(1), "enemy2", Predictability.MEDIUM, 10, 4);
        assertEnemy(levelMetaData.getEnemies().get(2), "enemy3", Predictability.LOW, 16, 1);
    }

    @Test
    void expectItemsCorrectlyCreated() {
        LevelMetaData levelMetaData = levelMetaDataFactory.create(levelTiledMap);

        assertThat(levelMetaData.getItems()).hasSize(2);
        assertItem(levelMetaData.getItems().get(0), "item-hp", 0, 0);
        assertItem(levelMetaData.getItems().get(1), "item-hp", 1, 0);
    }

    private void assertItem(MarkerObject item, String name, int x, int y) {
        assertThat(item.getType()).isEqualTo(name);
        assertThat(item.getX()).isEqualTo(x);
        assertThat(item.getY()).isEqualTo(y);
    }

    private void assertEnemy(EnemyMarkerObject enemy, String name, Predictability predictability, int x, int y) {
        assertThat(enemy.getType()).isEqualTo(name);
        assertThat(enemy.getPredictability()).isEqualTo(predictability);
        assertThat(enemy.getX()).isEqualTo(x);
        assertThat(enemy.getY()).isEqualTo(y);
    }
}