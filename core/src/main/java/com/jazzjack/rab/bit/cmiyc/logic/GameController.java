package com.jazzjack.rab.bit.cmiyc.logic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemies;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyMovementCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyMovementContext;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyRouteCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovementCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.animation.AnimationRegister;
import com.jazzjack.rab.bit.cmiyc.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.cmiyc.common.Randomizer;
import com.jazzjack.rab.bit.cmiyc.game.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaDataFactory;
import com.jazzjack.rab.bit.cmiyc.level.meta.ObjectTypeParser;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;

public class GameController implements InputProcessor {

    private final GameAssetManager assetManager;
    private final AnimationRegister animationRegister;
    private final Randomizer randomizer;
    private final LevelMetaDataFactory levelMetaDataFactory;

    private Enemies enemies;

    private Level level;
    private LevelCollisionDetectorWithCollidables playerMovementColissionDetector;

    private GamePhase currentGamePhase;

    public GameController(GameAssetManager assetManager, AnimationRegister animationRegister, Randomizer randomizer) {
        this.assetManager = assetManager;
        this.animationRegister = animationRegister;
        this.randomizer = randomizer;
        this.levelMetaDataFactory = new LevelMetaDataFactory(new ObjectTypeParser(new FileHandle("objecttypes.xml")));
    }

    public void startGame() {
        startFirstLevel();
        startPlayerTurn();

        GameEventBus.publishNewLevelEvent(level);
    }

    private void startFirstLevel() {
        level = new Level(assetManager.getTiledMap1(), levelMetaDataFactory.create(assetManager.getTiledMap1()));
        playerMovementColissionDetector = new PlayerMovementCollisionDetector(level);
        LevelCollisionDetectorWithCollidables enemyMovementColissionDetector = new EnemyMovementCollisionDetector(level);
        EnemyRouteCollisionDetector enemyRouteCollisionDetector = new EnemyRouteCollisionDetector(playerMovementColissionDetector, level.getEnemies());
        RouteGenerator routeGenerator = new RouteGenerator(enemyRouteCollisionDetector, randomizer);
        EnemyMovementContext enemyMovementContext = new EnemyMovementContext(enemyMovementColissionDetector, randomizer, animationRegister);
        enemies = new Enemies(routeGenerator, enemyMovementContext, level.getEnemies());
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
            startEnemyTurn();
            return true;
        }
        if (movePlayer(keycode)) {
            GameEventBus.publishPlayerMovedEvent();
            if (level.hasPlayerReachedEnd()) {
                startGame();
            }
            if (!level.getPlayer().hasMovementsLeft()) {
                startEnemyTurn();
            }
            return true;
        }
        return false;
    }

    private Boolean movePlayer(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                return level.getPlayer().moveLeft(playerMovementColissionDetector).success();
            case Input.Keys.RIGHT:
                return level.getPlayer().moveRight(playerMovementColissionDetector).success();
            case Input.Keys.UP:
                return level.getPlayer().moveUp(playerMovementColissionDetector).success();
            case Input.Keys.DOWN:
                return level.getPlayer().moveDown(playerMovementColissionDetector).success();
            default:
                return false;
        }
    }

    private void startEnemyTurn() {
        currentGamePhase = GamePhase.ENEMY_TURN;
        enemies.moveAllEnemies().thenRun(this::endEnemyTurn);
    }

    private void endEnemyTurn() {
        if (level.getPlayer().isDead()) {
            startGame();
        } else {
            startPlayerTurn();
        }
    }

    private void startPlayerTurn() {
        currentGamePhase = GamePhase.PLAYER_TURN;
        enemies.generateRoutes();
        level.getPlayer().resetMovements();
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
