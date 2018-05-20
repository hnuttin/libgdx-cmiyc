package com.jazzjack.rab.bit.logic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.actor.enemy.Enemies;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.enemy.EnemyRouteCollisionDetector;
import com.jazzjack.rab.bit.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.actor.player.Player;
import com.jazzjack.rab.bit.animation.AnimationRegister;
import com.jazzjack.rab.bit.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.game.GameEventBus;
import com.jazzjack.rab.bit.level.Level;
import com.jazzjack.rab.bit.render.GameAssetManager;

import static java.util.Arrays.asList;

public class GameController implements InputProcessor {

    private final GameAssetManager assetManager;
    private final AnimationRegister animationRegister;
    private final Randomizer randomizer;
    private final GameEventBus gameEventBus;

    private Enemies enemies;

    private Level level;
    private LevelCollisionDetectorWithCollidables playerMovementColissionDetector;
    private LevelCollisionDetectorWithCollidables enemyMovementColissionDetector;
    private Player player;

    private GamePhase currentGamePhase;

    public GameController(GameAssetManager assetManager, AnimationRegister animationRegister, Randomizer randomizer, GameEventBus gameEventBus) {
        this.assetManager = assetManager;
        this.animationRegister = animationRegister;
        this.randomizer = randomizer;
        this.gameEventBus = gameEventBus;
    }

    public void startGame() {
        startFirstLevel();
        startPlayerTurn();

        gameEventBus.publishNewLevelEvent(level);
    }

    private void startFirstLevel() {
        player = new Player(1, 2);
        Enemy enemy1 = new Enemy(animationRegister, 6, 7);
        Enemy enemy2 = new Enemy(animationRegister, 6, 1);
        Enemy enemy3 = new Enemy(animationRegister, 8, 4);
        level = new Level(this.assetManager.getTiledMap1(), player, asList(enemy1, enemy2, enemy3));
        playerMovementColissionDetector = new LevelCollisionDetectorWithCollidables(level);
        enemyMovementColissionDetector = new LevelCollisionDetectorWithCollidables(level);
        enemyMovementColissionDetector.addCollidable(player);
        EnemyRouteCollisionDetector enemyRouteCollisionDetector = new EnemyRouteCollisionDetector(playerMovementColissionDetector, asList(enemy1, enemy2, enemy3));
        RouteGenerator routeGenerator = new RouteGenerator(enemyRouteCollisionDetector, randomizer);
        enemies = new Enemies(routeGenerator, randomizer, enemyMovementColissionDetector);
        enemies.add(enemy1);
        enemies.add(enemy2);
        enemies.add(enemy3);
        playerMovementColissionDetector.addCollidable(enemies.get());
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
            // TODO fire player moved event (rebuffer player)
            if (!player.hasMovementsLeft()) {
                startEnemyTurn();
            }
            return true;
        }
        return false;
    }

    private Boolean movePlayer(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                return player.moveLeft(playerMovementColissionDetector).success();
            case Input.Keys.RIGHT:
                return player.moveRight(playerMovementColissionDetector).success();
            case Input.Keys.UP:
                return player.moveUp(playerMovementColissionDetector).success();
            case Input.Keys.DOWN:
                return player.moveDown(playerMovementColissionDetector).success();
            default:
                return false;
        }
    }

    private void startEnemyTurn() {
        currentGamePhase = GamePhase.ENEMY_TURN;
        enemies.moveAllEnemies().thenRun(this::startPlayerTurn);
    }

    private void startPlayerTurn() {
        currentGamePhase = GamePhase.PLAYER_TURN;
        enemies.generateRoutes();
        player.resetMovements();
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
