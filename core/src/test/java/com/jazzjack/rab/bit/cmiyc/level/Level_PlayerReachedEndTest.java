package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandleResolver;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaDataFactory;
import com.jazzjack.rab.bit.cmiyc.level.meta.ObjectTypeParser;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class Level_PlayerReachedEndTest {

    private Level level;

    private static Application application;

    // Before running any tests, initialize the application with the headless backend
    @BeforeAll
    public static void init() {
        // Note that we don't need to implement any of the listener's methods
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });

        // Use Mockito to mock the OpenGL methods since we are running headlessly
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    // After we are done, clean up the application
    @AfterAll
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }

    @BeforeEach
    void beforeEach() {
        ObjectTypeParser objectTypeParser = new ObjectTypeParser(new ClasspathFileHandle("objecttypes.xml"));
        LevelMetaDataFactory levelMetaDataFactory = new LevelMetaDataFactory(objectTypeParser);
        TmxMapLoader tmxMapLoader = new TmxMapLoader(new ClasspathFileHandleResolver());
        LevelTiledMap levelTiledMap = new LevelTiledMap(tmxMapLoader.load("level-reach-end.tmx"));
        level = new Level(mock(LevelContext.class), levelTiledMap, levelMetaDataFactory.create(levelTiledMap), 0);
    }

    @Test
    void expectPlayerNotReachedEndWhenNotOnLevelMetaDataEndPosition() {
        assertThat(level.hasPlayerReachedEnd()).isFalse();
    }

    @Test
    void expectPlayerReachedEndWhenOnLevelMetaDataEndPosition() {
        level.getPlayer().moveToDirection(Direction.RIGHT);
        level.getPlayer().moveToDirection(Direction.UP);

        assertThat(level.hasPlayerReachedEnd()).isTrue();
    }

}