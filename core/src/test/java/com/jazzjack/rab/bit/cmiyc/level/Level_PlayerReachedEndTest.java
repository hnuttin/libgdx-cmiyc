package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jazzjack.rab.bit.cmiyc.collision.NeverCollideCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandleResolver;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaDataFactory;
import com.jazzjack.rab.bit.cmiyc.level.meta.ObjectTypeParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level_PlayerReachedEndTest {

    private final NeverCollideCollisionDetector collisionDetector = new NeverCollideCollisionDetector();

    private Level level;

    @BeforeEach
    void beforeEach() {
        ObjectTypeParser objectTypeParser = new ObjectTypeParser(new ClasspathFileHandle("objecttypes.xml"));
        LevelMetaDataFactory levelMetaDataFactory = new LevelMetaDataFactory(objectTypeParser);
        TmxMapLoader tmxMapLoader = new TmxMapLoader(new ClasspathFileHandleResolver());
        LevelTiledMap levelTiledMap = new LevelTiledMap(tmxMapLoader.load("level-reach-end.tmx"));
        level = new Level(levelTiledMap, levelMetaDataFactory.create(levelTiledMap), 0);
    }

    @Test
    void expectPlayerNotReachedEndWhenNotOnLevelMetaDataEndPosition() {
        assertThat(level.hasPlayerReachedEnd()).isFalse();
    }

    @Test
    void expectPlayerReachedEndWhenOnLevelMetaDataEndPosition() {
        level.getPlayer().moveUp(collisionDetector);
        level.getPlayer().moveRight(collisionDetector);

        assertThat(level.hasPlayerReachedEnd()).isTrue();
    }

}