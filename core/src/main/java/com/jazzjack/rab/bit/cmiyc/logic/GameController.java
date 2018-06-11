package com.jazzjack.rab.bit.cmiyc.logic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.level.LevelFactory;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.HashMap;
import java.util.Map;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.publishEvent;

public class GameController implements InputProcessor {

    private static final Map<Integer, Direction> KEY_TO_DIRECTION_MAPPING = new HashMap<>();

    static {
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.LEFT, Direction.LEFT);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.RIGHT, Direction.RIGHT);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.UP, Direction.UP);
        KEY_TO_DIRECTION_MAPPING.put(Input.Keys.DOWN, Direction.DOWN);
    }

    private final LevelFactory levelFactory;
    private final PlayerProfile playerProfile;

    private Level currentLevel;

    private GamePhase currentGamePhase;

    public GameController(LevelFactory levelFactory, PlayerProfile playerProfile) {
        this.levelFactory = levelFactory;
        this.playerProfile = playerProfile;
    }

    public void startGame() {
        startLevel(levelFactory.createLevel(1));
    }

    private void startNextLevel() {
        startLevel(levelFactory.createNextLevel());
    }

    private void restartLevel() {
        startLevel(levelFactory.createCurrentLevel());
    }

    private void startLevel(Level level) {
        currentLevel = level;
        startPlayerTurn();
    }

    private boolean isPlayerTurn() {
        return currentGamePhase == GamePhase.PLAYER_TURN;
    }

    @Override
    public boolean keyDown(int keycode) {
        return isPlayerTurn() && handlePlayerKeys(keycode);
    }

    private boolean handlePlayerKeys(int keycode) {
        if (keycode == Input.Keys.E) {
            endPlayerTurn();
            return true;
        }
        if (keycode == Input.Keys.NUM_1) {
            startLevel(levelFactory.createLevel(1));
            return true;
        }
        if (keycode == Input.Keys.NUM_2) {
            startLevel(levelFactory.createLevel(2));
            return true;
        }
        if (movePlayer(keycode)) {
            if (currentLevel.hasPlayerReachedEnd()) {
                startNextLevel();
            }
            if (!currentLevel.getPlayer().hasActionPointsLeft()) {
                endPlayerTurn();
            }
            return true;
        }
        return false;
    }

    private Boolean movePlayer(int keycode) {
        Direction direction = KEY_TO_DIRECTION_MAPPING.get(keycode);
        return direction != null && currentLevel.getPlayer().moveToDirection(direction).isNoCollision();
    }

    private void endPlayerTurn() {
        currentLevel.endTurn();
        if (currentLevel.noTurnsLeft()) {
            restartLevel();
        }
        startEnemyTurn();
    }

    private void startEnemyTurn() {
        currentGamePhase = GamePhase.ENEMY_TURN;
        currentLevel.moveAllEnemies().thenRun(this::endEnemyTurn);
    }

    private void endEnemyTurn() {
        if (playerProfile.isDead()) {
            restartLevel();
        } else {
            startPlayerTurn();
        }
    }

    private void startPlayerTurn() {
        currentGamePhase = GamePhase.PLAYER_TURN;
        publishEvent(new GamePhaseEvent(currentGamePhase));
        currentLevel.generateEnemyRoutes();
        currentLevel.getPlayer().resetActionPoints();
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
