package com.jazzjack.rab.bit.cmiyc.logic;

import com.badlogic.gdx.Input;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile;
import com.jazzjack.rab.bit.cmiyc.animation.AnimationHandler;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;
import com.jazzjack.rab.bit.cmiyc.level.LevelFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile.playerProfileBuilder;
import static com.jazzjack.rab.bit.cmiyc.level.TestLevelFactory.createLevelFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class GameControllerTest extends LibGdxTest {

    private GameController gameController;
    private AnimationHandler animationRegister;

    @BeforeEach
    void setup() {
        PlayerProfile playerProfile = playerProfileBuilder()
                .withHp(10)
                .withMaxHp(10)
                .withActionPointsPerTurn(3)
                .build();
        animationRegister = new AnimationHandler();
        LevelFactory levelFactory = createLevelFactory(playerProfile, animationRegister, "gamecontroller-map.tmx");
        gameController = new GameController(levelFactory, playerProfile);
        gameController.startGame();
    }

    @Test
    void expectPlayerTurnEndedWhenPlayerMoved3Times() {
        gameController.keyDown(Input.Keys.RIGHT);
        gameController.keyDown(Input.Keys.RIGHT);
        gameController.keyDown(Input.Keys.RIGHT);

        assertThat(gameController.getCurrentGamePhase()).isEqualTo(GamePhase.ENEMY_TURN);
    }

    @Test
    void expectPlayerTurnEndedWhenPlayerMoved2TimesAndShieldUsed() {
        gameController.keyDown(Input.Keys.RIGHT);
        gameController.keyDown(Input.Keys.RIGHT);
        gameController.keyDown(Input.Keys.S);

        assertThat(gameController.getCurrentGamePhase()).isEqualTo(GamePhase.ENEMY_TURN);
    }

    @Test
    void expectEnemyTurnEndedWhenAllEnemyAnimationEnded() {
        gameController.keyDown(Input.Keys.RIGHT);
        gameController.keyDown(Input.Keys.RIGHT);
        gameController.keyDown(Input.Keys.RIGHT);
        assertThat(gameController.getCurrentGamePhase()).isEqualTo(GamePhase.ENEMY_TURN);

        assertTimeout(Duration.ofSeconds(1), this::waitUntilEnemyRouteAnimationsEnded);
    }

    private void waitUntilEnemyRouteAnimationsEnded() {
        while (gameController.getCurrentGamePhase() == GamePhase.ENEMY_TURN) {
            animationRegister.continueAnimations(1f);
        }
        assertThat(gameController.getCurrentGamePhase()).isEqualTo(GamePhase.PLAYER_TURN);
        assertThat(gameController.getCurrentLevel().getPlayer().getActionPointsConsumed()).isEqualTo(0);
    }

}