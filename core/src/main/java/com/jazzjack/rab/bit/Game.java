package com.jazzjack.rab.bit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jazzjack.rab.bit.animation.AnimationHandler;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.game.GameController;

public class Game extends ApplicationAdapter {

    private OrthographicCamera camera;

    private GameRenderer gameRenderer;
    private GameAssetManager assetManager;
    private GameController gameController;
    private AnimationHandler animationHandler;

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
        animationHandler = new AnimationHandler();
        gameController = new GameController(assetManager, animationHandler, new Randomizer());
        gameRenderer = new GameRenderer(gameController, assetManager);
    }

    private void startNewGame() {
        Gdx.input.setInputProcessor(gameController);
        gameController.startGame();
    }

    @Override
    public void render() {
        camera.update();
        animationHandler.continueAnimations(Gdx.graphics.getDeltaTime());
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
