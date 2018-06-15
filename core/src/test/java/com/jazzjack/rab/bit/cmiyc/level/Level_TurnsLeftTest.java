package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaData;
import com.jazzjack.rab.bit.cmiyc.level.meta.MarkerObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile.playerProfileBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Level_TurnsLeftTest extends LibGdxTest {

    private static final int MAX_TURNS = 9;

    private Level level;

    @BeforeEach
    void beforeEach() {
        LevelMetaData levelMetaData = mock(LevelMetaData.class);
        when(levelMetaData.getStartPosition()).thenReturn(mock(MarkerObject.class));
        LevelContext levelContext = mock(LevelContext.class);
        when(levelContext.getPlayerProfile()).thenReturn(playerProfileBuilder().build());
        level = new Level(levelContext, mock(LevelTiledMap.class), levelMetaData, MAX_TURNS);
    }

    @Test
    void expectNoTurnsLeftWhenMaxTurnsPlayed() {
        for (int i = 0; i < MAX_TURNS; i++) {
            assertThat(level.noTurnsLeft()).isFalse();
            assertThat(level.getTurnsLeft()).isEqualTo(MAX_TURNS - i);
            level.endTurn();
        }
        assertThat(level.getTurnsLeft()).isEqualTo(0);
        assertThat(level.noTurnsLeft()).isTrue();
    }

}