package com.jazzjack.rab.bit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.game.GameController;

public class Game extends ApplicationAdapter {

    private OrthographicCamera camera;

    private GameRenderer gameRenderer;
    private GameAssetManager assetManager;
    private GameController gameController;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(640, 320);

        initCamera();
        initGameObjects();
        startNewGame();
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 320);
        camera.update();
    }

    private void initGameObjects() {
        assetManager = new GameAssetManager();
        gameController = new GameController(assetManager, new Randomizer());
        gameRenderer = new GameRenderer(gameController, assetManager);

        // TODO fix circular dependency
        gameController.setAnimationHandler(gameRenderer);
    }

    private void startNewGame() {
        Gdx.input.setInputProcessor(gameController);
        gameController.startGame();
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

}
