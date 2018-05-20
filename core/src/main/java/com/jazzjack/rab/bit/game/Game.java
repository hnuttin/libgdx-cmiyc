package com.jazzjack.rab.bit.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.jazzjack.rab.bit.animation.AnimationHandler;
import com.jazzjack.rab.bit.common.RandomInteger;
import com.jazzjack.rab.bit.common.Randomizer;
import com.jazzjack.rab.bit.logic.GameController;
import com.jazzjack.rab.bit.render.GameAssetManager;
import com.jazzjack.rab.bit.render.GameRenderer;

public class Game extends ApplicationAdapter {

    private com.jazzjack.rab.bit.render.GameRenderer gameRenderer;
    private com.jazzjack.rab.bit.render.GameAssetManager assetManager;
    private GameController gameController;
    private AnimationHandler animationHandler;
    private GameEventBus gameEventBus;

    @Override
    public void create() {
        initGameObjects();
        startNewGame();
    }

    private void initGameObjects() {
        assetManager = new GameAssetManager();
        animationHandler = new AnimationHandler();
        gameEventBus = new GameEventBus();
        gameController = new GameController(assetManager, animationHandler, new Randomizer(new RandomInteger()), gameEventBus);
        gameRenderer = new GameRenderer(assetManager);

        gameEventBus.registerNewLevelListener(gameRenderer);
    }

    private void startNewGame() {
        Gdx.input.setInputProcessor(gameController);
        gameController.startGame();
    }

    @Override
    public void render() {
        animationHandler.continueAnimations(Gdx.graphics.getDeltaTime());
        gameRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        assetManager.dispose();
    }

}
