package com.jazzjack.rab.bit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jazzjack.rab.bit.actor.Enemy;
import com.jazzjack.rab.bit.actor.Player;

public class MyFirstGame extends ApplicationAdapter implements InputProcessor {

    private OrthographicCamera camera;

    private GameRenderer gameRenderer;
    private Player player;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(640, 320);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 320);
        camera.update();

        TacticalMap tacticalMap = new TacticalMap("pixel-art1.tmx");
        TiledMapCollisionDetector collisionDetector = new TiledMapCollisionDetector(tacticalMap);
        player = new Player(collisionDetector, 1 * tacticalMap.getTileWidth(), 2 * tacticalMap.getTileHeight());
        Enemy enemy = new Enemy(6 * tacticalMap.getTileWidth(), 7 * tacticalMap.getTileHeight());
        gameRenderer = new GameRenderer(tacticalMap, player, enemy);

        collisionDetector.addActor(player, enemy);

        Gdx.input.setInputProcessor(this);
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
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT)
            player.movePlayerLeft();
        if(keycode == Input.Keys.RIGHT)
            player.movePlayerRight();
        if(keycode == Input.Keys.UP)
            player.movePlayerUp();
        if(keycode == Input.Keys.DOWN)
            player.movePlayerDown();

        gameRenderer.rebufferPlayer();

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
