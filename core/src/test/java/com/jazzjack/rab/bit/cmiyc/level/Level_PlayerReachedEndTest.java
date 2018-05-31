package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level_PlayerReachedEndTest extends LibGdxTest {

    private Level level;

    @BeforeEach
    void beforeEach() {
        level = TestLevelFactory.createLevel("level-reach-end.tmx");
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