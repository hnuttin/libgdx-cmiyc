package com.jazzjack.rab.bit.cmiyc.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.jazzjack.rab.bit.cmiyc.animation.AnimationHandler;
import com.jazzjack.rab.bit.cmiyc.common.RandomInteger;
import com.jazzjack.rab.bit.cmiyc.common.Randomizer;
import com.jazzjack.rab.bit.cmiyc.logic.GameController;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.render.GameRenderer;

public class Game extends ApplicationAdapter {

    private com.jazzjack.rab.bit.cmiyc.render.GameRenderer gameRenderer;
    private com.jazzjack.rab.bit.cmiyc.render.GameAssetManager assetManager;
    private GameController gameController;
    private AnimationHandler animationHandler;

    @Override
    public void create() {
        initGameObjects();
        startNewGame();
    }

    private void initGameObjects() {
        assetManager = new GameAssetManager();
        animationHandler = new AnimationHandler();
        gameController = new GameController(assetManager, animationHandler, new Randomizer(new RandomInteger()));
        gameRenderer = new GameRenderer(assetManager);
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
