package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.files.FileHandle;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.StepNames;
import com.jazzjack.rab.bit.cmiyc.gdx.AssetFileHandleResolver;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameAssetManagerTest extends LibGdxTest {

    private GameAssetManager assetManager = new GameAssetManager(new AssetFileHandleResolver());

    @Test
    void getObjectTypesFileHandle() {
        FileHandle fileHandle = assetManager.getObjectTypesFileHandle();

        assertThat(fileHandle.exists()).isTrue();
    }

    @Test
    void getLevelTiledMaps() {
        assertThat(assetManager.getLevelTiledMap1()).isNotNull();
        assertThat(assetManager.getLevelTiledMap2()).isNotNull();
    }

    @Test
    void getStaticTextures() {
        assertThat(assetManager.getApMiddleTexture()).isNotNull();
        assertThat(assetManager.getApStartTexture()).isNotNull();
        assertThat(assetManager.getApMiddleTexture()).isNotNull();
        assertThat(assetManager.getApEndTexture()).isNotNull();
        assertThat(assetManager.getHpEmptyTexture()).isNotNull();
        assertThat(assetManager.getHpFilledTexture()).isNotNull();
        assertThat(assetManager.getSightTexture()).isNotNull();
        assertThat(assetManager.getTileVisitedTexture()).isNotNull();
        assertThat(assetManager.getPlayerEndTexture()).isNotNull();
    }

    @Test
    void getActorTextures() {
        assertThat(assetManager.getTextureForName("item-hp")).isNotNull();
        assertThat(assetManager.getTextureForName("item-shield")).isNotNull();
        assertThat(assetManager.getTextureForName("player")).isNotNull();
        assertThat(assetManager.getTextureForName("enemy1")).isNotNull();
        assertThat(assetManager.getTextureForName("enemy2")).isNotNull();
        assertThat(assetManager.getTextureForName("enemy3")).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.HORIZONTAL)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.VERTICAL)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.ENDING_BOTTOM)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.ENDING_LEFT)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.ENDING_RIGHT)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.ENDING_TOP)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.CORNER_BOTTOM_LEFT)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.CORNER_BOTTOM_RIGHT)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.CORNER_TOP_LEFT)).isNotNull();
        assertThat(assetManager.getTextureForName(StepNames.CORNER_TOP_RIGHT)).isNotNull();
    }

    @Test
    void getTurnsLeftTextures() {
        assertThat(assetManager.getTurnsLeftTexture(0)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(1)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(2)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(3)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(4)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(5)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(6)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(7)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(8)).isNotNull();
        assertThat(assetManager.getTurnsLeftTexture(9)).isNotNull();
    }

    @Test
    void getFontAssets() {
        assertThat(assetManager.getFont()).isNotNull();
        assertThat(assetManager.getFontShaderProgram()).isNotNull();
    }

}