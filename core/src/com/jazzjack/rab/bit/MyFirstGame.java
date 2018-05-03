package com.jazzjack.rab.bit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

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

        TiledMap tiledMap = new TmxMapLoader().load("pixel-art1.tmx");
        player = new Player();
        gameRenderer = new GameRenderer(tiledMap, player);

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
