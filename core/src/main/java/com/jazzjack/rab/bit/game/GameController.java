package com.jazzjack.rab.bit.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.Map;
import com.jazzjack.rab.bit.actor.enemy.Enemies;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.enemy.EnemyRouteCollisionDetector;
import com.jazzjack.rab.bit.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.actor.player.Player;
import com.jazzjack.rab.bit.animation.AnimationRegister;
import com.jazzjack.rab.bit.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.render.GameAssetManager;

import java.util.List;
import java.util.Optional;

public class GameController implements GameObjectProvider, InputProcessor {

    private final GameAssetManager assetManager;
    private final AnimationRegister animationRegister;
    private final Randomizer randomizer;

    private Enemies enemies;

    private Map map;
    private LevelCollisionDetectorWithCollidables playerMovementColissionDetector;
    private LevelCollisionDetectorWithCollidables enemyMovementColissionDetector;
    private Player player;

    private GamePhase currentGamePhase;

    public GameController(GameAssetManager assetManager, AnimationRegister animationRegister, Randomizer randomizer) {
        this.assetManager = assetManager;
        this.animationRegister = animationRegister;
        this.randomizer = randomizer;
    }

    public void startGame() {
        startFirstLevel();
        startPlayerTurn();
    }

    private void startFirstLevel() {
        map = new Map(this.assetManager.getTiledMap1());
        playerMovementColissionDetector = new LevelCollisionDetectorWithCollidables(map);
        player = new Player(1, 2);
        enemyMovementColissionDetector = new LevelCollisionDetectorWithCollidables(map);
        enemyMovementColissionDetector.addCollidable(player);
        RouteGenerator routeGenerator = new RouteGenerator(new EnemyRouteCollisionDetector(playerMovementColissionDetector, this), randomizer);
        enemies = new Enemies(randomizer, enemyMovementColissionDetector);
        enemies.add(new Enemy(routeGenerator, animationRegister, 6, 7));
        enemies.add(new Enemy(routeGenerator, animationRegister, 6, 1));
        enemies.add(new Enemy(routeGenerator, animationRegister, 8, 4));
        playerMovementColissionDetector.addCollidable(enemies.get());
    }

    private boolean isPlayerTurn() {
        return currentGamePhase == GamePhase.PLAYER_TURN;
    }

    @Override
    public Optional<Player> getPlayer() {
        return Optional.ofNullable(player);
    }

    @Override
    public List<Enemy> getEnemies() {
        return enemies.get();
    }

    @Override
    public Optional<Map> getMap() {
        return Optional.ofNullable(map);
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
