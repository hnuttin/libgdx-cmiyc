package com.jazzjack.rab.bit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jazzjack.rab.bit.animation.AnimationHandler;
import com.jazzjack.rab.bit.common.RandomInteger;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.game.GameController;

public class Game extends ApplicationAdapter {

    private static final int BAR_HEIGHT = 32;

    private static final int WIDTH = 640;
    private static final int HEIGHT = 320;
    private static final int SCALE = 2;

    private static final int SCALED_WIDTH = WIDTH * SCALE;
    private static final int SCALED_HEIGHT = HEIGHT * SCALE + BAR_HEIGHT;

    private OrthographicCamera camera;

    private GameRenderer gameRenderer;
    private GameAssetManager assetManager;
    private GameController gameController;
    private AnimationHandler animationHandler;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(SCALED_WIDTH, SCALED_HEIGHT);

        initCamera();
        initGameObjects();
        startNewGame();
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, (float) SCALED_WIDTH, (float) SCALED_HEIGHT);
        camera.update();
    }

    private void initGameObjects() {
        assetManager = new GameAssetManager();
        animationHandler = new AnimationHandler();
        gameController = new GameController(assetManager, animationHandler, new Randomizer(new RandomInteger()));
        gameRenderer = new GameRenderer(gameController, assetManager, SCALE);
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
        camera.viewportWidth = SCALED_WIDTH;
        camera.viewportHeight = SCALED_WIDTH * height / width;
        gameRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        assetManager.dispose();
    }

}
