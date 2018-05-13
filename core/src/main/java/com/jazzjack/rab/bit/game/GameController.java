package com.jazzjack.rab.bit.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.Level;
import com.jazzjack.rab.bit.actor.player.Player;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.enemy.EnemyRouteCollisionDetector;
import com.jazzjack.rab.bit.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.animation.Animation;
import com.jazzjack.rab.bit.animation.AnimationRegister;
import com.jazzjack.rab.bit.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.render.GameAssetManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameController implements GameObjectProvider, InputProcessor {

    private final GameAssetManager assetManager;
    private final AnimationRegister animationRegister;
    private final Randomizer randomizer;

    private final List<Enemy> enemies = new ArrayList<>();
    private int enemyIndexToAnimate;

    private Level level;
    private LevelCollisionDetectorWithCollidables levelCollisionDetectorWithEnemies;
    private LevelCollisionDetectorWithCollidables levelCollisionDetectorWithPlayer;
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
        level = new Level(this.assetManager.getTiledMap1());
        levelCollisionDetectorWithEnemies = new LevelCollisionDetectorWithCollidables(level);
        player = new Player(1, 2);
        levelCollisionDetectorWithPlayer = new LevelCollisionDetectorWithCollidables(level);
        levelCollisionDetectorWithPlayer.addCollidable(player);
        RouteGenerator routeGenerator = new RouteGenerator(new EnemyRouteCollisionDetector(levelCollisionDetectorWithEnemies, this), randomizer);
        enemies.add(new Enemy(routeGenerator, 6, 7));
        enemies.add(new Enemy(routeGenerator, 6, 1));
        enemies.add(new Enemy(routeGenerator, 8, 4));
        levelCollisionDetectorWithEnemies.addCollidable(enemies.toArray(new Enemy[0]));
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
        return enemies;
    }

    @Override
    public Optional<Level> getLevel() {
        return Optional.ofNullable(level);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (isPlayerTurn()) {
            return handlePlayerKeys(keycode);
        }
        return false;
    }

    private boolean handlePlayerKeys(int keycode) {
        if (keycode == Input.Keys.E) {
            startEnemyTurn();
        }
        if (movePlayer(keycode)) {
            // TODO fire player moved event (rebuffer player)
            if (!player.hasMovementsLeft()) {
                startEnemyTurn();
            }
        }
        return true;
    }

    private Boolean movePlayer(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                return player.moveLeft(levelCollisionDetectorWithEnemies).success();
            case Input.Keys.RIGHT:
                return player.moveRight(levelCollisionDetectorWithEnemies).success();
            case Input.Keys.UP:
                return player.moveUp(levelCollisionDetectorWithEnemies).success();
            case Input.Keys.DOWN:
                return player.moveDown(levelCollisionDetectorWithEnemies).success();
            default:
                return false;
        }
    }

    private void startEnemyTurn() {
        currentGamePhase = GamePhase.ENEMY_TURN;
        enemyIndexToAnimate = 0;
        animateEnemyForIndex();
    }

    private void animateEnemyForIndex() {
        if (enemyIndexToAnimate < enemies.size()) {
            Enemy enemyToAnimate = enemies.get(enemyIndexToAnimate);
            Animation animation = enemyToAnimate.createAnimation(levelCollisionDetectorWithPlayer, randomizer);
            enemyIndexToAnimate++;
            animationRegister.registerAnimation(animation, this::animateEnemyForIndex);
        } else {
            startPlayerTurn();
        }
    }

    private void startPlayerTurn() {
        currentGamePhase = GamePhase.PLAYER_TURN;
        enemies.forEach(Enemy::generateRoutes);
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
