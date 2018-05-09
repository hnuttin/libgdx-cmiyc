package com.jazzjack.rab.bit.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jazzjack.rab.bit.GameAssetManager;
import com.jazzjack.rab.bit.Level;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.Player;
import com.jazzjack.rab.bit.animation.Animation;
import com.jazzjack.rab.bit.animation.AnimationHandler;
import com.jazzjack.rab.bit.collision.TiledMapCollisionDetector;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.route.RouteGenerator;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

public class GameController implements GameObjectProvider, InputProcessor {

    private final GameAssetManager assetManager;
    private final Randomizer randomizer;
    private AnimationHandler animationHandler;

    private Level level;
    private TiledMapCollisionDetector collisionDetector;
    private Player player;
    private Enemy enemy;

    private GamePhase currentGamePhase;

    public GameController(GameAssetManager assetManager, Randomizer randomizer) {
        this.assetManager = assetManager;
        this.randomizer = randomizer;
    }

    public void setAnimationHandler(AnimationHandler animationHandler) {
        this.animationHandler = animationHandler;
    }

    public void startGame() {
        startFirstLevel();
        startPlayerTurn();
    }

    private void startFirstLevel() {
        level = new Level(this.assetManager.getTiledMap1());
        collisionDetector = new TiledMapCollisionDetector(level);
        player = new Player(1 * level.getTileWidth(), 2 * level.getTileHeight(), level.getTileWidth());
        RouteGenerator routeGenerator = new RouteGenerator(collisionDetector, randomizer);
        enemy = new Enemy(routeGenerator, 6 * level.getTileWidth(), 7 * level.getTileHeight(), level.getTileWidth());
        collisionDetector.addActor(enemy);
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
        return singletonList(enemy);
    }

    @Override
    public Optional<Level> getLevel() {
        return Optional.ofNullable(level);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (isPlayerTurn() && movePlayer(keycode)) {
            // TODO fire player moved event (rebuffer player)
            if (!player.hasMovementsLeft()) {
                startEnemyTurn();
            }
            return true;
        }
        return false;
    }

    private void startEnemyTurn() {
        currentGamePhase = GamePhase.ENEMY_TURN;
        Animation animation = enemy.animateEnemy(randomizer);
        animationHandler.handleAnimation(animation, this::startPlayerTurn);
    }

    private void startPlayerTurn() {
        currentGamePhase = GamePhase.PLAYER_TURN;
        enemy.generateRoutes();
        player.resetMovements();
    }

    private Boolean movePlayer(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                return player.moveLeft(collisionDetector);
            case Input.Keys.RIGHT:
                return player.moveRight(collisionDetector);
            case Input.Keys.UP:
                return player.moveUp(collisionDetector);
            case Input.Keys.DOWN:
                return player.moveDown(collisionDetector);
            case Input.Keys.G:
                enemy.generateRoutes();
                return true;
        }
        return false;
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
