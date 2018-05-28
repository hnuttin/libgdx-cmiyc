package com.jazzjack.rab.bit.cmiyc.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.jazzjack.rab.bit.cmiyc.animation.AnimationHandler;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.level.LevelContext;
import com.jazzjack.rab.bit.cmiyc.level.LevelFactory;
import com.jazzjack.rab.bit.cmiyc.logic.GameController;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.render.GameRenderer;
import com.jazzjack.rab.bit.cmiyc.shared.RandomInteger;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;

public class Game extends ApplicationAdapter {

    private GameAssetManager assetManager;
    private AnimationHandler animationHandler;
    private GameController gameController;
    private GameRenderer gameRenderer;

    @Override
    public void create() {
        initGameObjects();
        startNewGame();
    }

    private void initGameObjects() {
        assetManager = new GameAssetManager();
        animationHandler = new AnimationHandler();
        gameController = new GameController(new LevelFactory(createLevelContext(), assetManager));
        gameRenderer = new GameRenderer(assetManager);
    }

    private LevelContext createLevelContext() {
        return new LevelContext(new CollisionResolver(), new Randomizer(new RandomInteger()), animationHandler);
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
