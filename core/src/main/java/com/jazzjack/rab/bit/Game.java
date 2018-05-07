package com.jazzjack.rab.bit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jazzjack.rab.bit.actor.Enemy;
import com.jazzjack.rab.bit.actor.Player;
import com.jazzjack.rab.bit.collision.TiledMapCollisionDetector;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.route.RouteGenerator;

public class Game extends ApplicationAdapter implements InputProcessor {

    private OrthographicCamera camera;

    private GameRenderer gameRenderer;
    private Player player;
    private GameAssetManager assetManager;
    private TiledMapCollisionDetector collisionDetector;
    private Enemy enemy;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(640, 320);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 320);
        camera.update();

        assetManager = new GameAssetManager();

        initGameObjects();

        Gdx.input.setInputProcessor(this);
    }

    private void initGameObjects() {
        Level level = new Level(assetManager.getTiledMap1());
        collisionDetector = new TiledMapCollisionDetector(level);
        player = new Player(1 * level.getTileWidth(), 2 * level.getTileHeight(), level.getTileWidth());
        RouteGenerator routeGenerator = new RouteGenerator(collisionDetector, new Randomizer());
        enemy = new Enemy(routeGenerator, 6 * level.getTileWidth(), 7 * level.getTileHeight(), level.getTileWidth());
        gameRenderer = new GameRenderer(level, assetManager, player, enemy);
        collisionDetector.addActor(enemy);
        enemy.generateRoutes(2, 7);
    }

    @Override
    public void render() {
        camera.update();
        gameRenderer.setView(camera);
        gameRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.getBatch().setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        assetManager.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (movePlayer(keycode)) {
            gameRenderer.rebufferPlayer();
            return true;
        }

        return false;
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
                enemy.generateRoutes(2, 7);
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
