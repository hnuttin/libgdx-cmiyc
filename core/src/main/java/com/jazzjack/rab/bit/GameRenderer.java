package com.jazzjack.rab.bit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.Player;
import com.jazzjack.rab.bit.animation.Animation;
import com.jazzjack.rab.bit.animation.AnimationHandler;
import com.jazzjack.rab.bit.game.GameObjectProvider;

import java.util.List;

public class GameRenderer extends OrthogonalTiledMapRenderer implements AnimationHandler {

    private final GameObjectProvider gameObjectProvider;
    private final GameAssetManager assetManager;

    private final FrameBuffer lightBuffer;

    private boolean rebufferPlayer = true;

    private Animation currentAnimation;
    private Runnable currentOnAnimationFinished;

    public GameRenderer(GameObjectProvider gameObjectProvider, GameAssetManager assetManager) {
        super(null);

        this.gameObjectProvider = gameObjectProvider;
        this.assetManager = assetManager;

        lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    private Player getPlayer() {
        return gameObjectProvider.getPlayer().get();
    }

    private List<Enemy> getEnemies() {
        return gameObjectProvider.getEnemies();
    }

    private Level getLevel() {
        return gameObjectProvider.getLevel().get();
    }

    @Override
    public void render() {
        Gdx.graphics.getDeltaTime();

        bufferSight();
        renderMap();
        renderSight();
    }

    private void bufferSight() {
        if (rebufferPlayer && gameObjectProvider.getPlayer().isPresent()) {
            rebufferPlayer = true;

            lightBuffer.begin();
            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            drawWithBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE, this::drawSight);
            lightBuffer.end();
        }
    }

    private void drawSight() {
        batch.draw(
                assetManager.getLightAtlasRegion(),
                getPlayer().getX() - (getPlayer().getSight() / 2) + (getLevel().getTileWidth() / 2),
                getPlayer().getY() - (getPlayer().getSight() / 2) + (getLevel().getTileHeight() / 2),
                getPlayer().getSight(),
                getPlayer().getSight());
    }

    private void renderMap() {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderMapLayer();
        drawPlayer();
        drawEnemies();
        continueAnimations();
        batch.end();
    }

    private void continueAnimations() {
        if (currentAnimation != null) {
            currentAnimation.continueAnimation(Gdx.graphics.getDeltaTime());
            if (currentAnimation.isFinished()) {
                currentOnAnimationFinished.run();
            }
        }
    }

    private void renderMapLayer() {
        if (gameObjectProvider.getLevel().isPresent()) {
            renderMapLayer(getLevel().getMapLayer());
        }
    }

    private void drawPlayer() {
        if (gameObjectProvider.getPlayer().isPresent()) {
            drawActor(getPlayer());
        }
    }

    private void drawEnemies() {
        getEnemies().forEach(this::drawEnemy);
    }

    private void drawEnemy(Enemy enemy) {
        drawActor(enemy);
        drawWithAlpha(0.5f, () -> drawEnemyRoutes(enemy));
    }

    private void drawEnemyRoutes(Enemy enemy) {
        enemy.getRoutes().stream().flatMap(route -> route.getSteps().stream()).forEach(this::drawActor);
    }

    private void drawWithAlpha(float alpha, Runnable runnable) {
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, alpha);
        runnable.run();
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
    }

    private void drawActor(Actor actor) {
        batch.draw(assetManager.getTextureForActor(actor), actor.getX(), actor.getY());
    }

    private void renderSight() {
        batch.setProjectionMatrix(batch.getProjectionMatrix().idt());
        drawWithBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR, () -> batch.draw(lightBuffer.getColorBufferTexture(), -1, 1, 2, -2));
    }

    private void drawWithBlendFunction(int srcFunc, int destFunc, Runnable runnable) {
        batch.setBlendFunction(srcFunc, destFunc);
        batch.begin();
        runnable.run();
        batch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    @Override
    public void handleAnimation(Animation animation, Runnable onAnimationFinished) {
        currentAnimation = animation;
        currentOnAnimationFinished = onAnimationFinished;
    }
}
