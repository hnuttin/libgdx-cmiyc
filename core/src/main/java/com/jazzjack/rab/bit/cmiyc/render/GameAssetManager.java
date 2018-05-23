package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jazzjack.rab.bit.cmiyc.actor.Actor;

public class GameAssetManager extends AssetManager {

    private static final String MAP1 = "pixel-art1.tmx";
    private static final String MAP2 = "cmiyc2.tmx";

    private static final String ATLAS_LIGHTS = "lights.atlas";
    private static final String ATLAS_CMIYC_FONTS = "atlas/cmiyc_fonts.atlas";
    private static final String ATLAS_CMIYC_ACTORS = "atlas/cmiyc_actors.atlas";

    private static final String ATLAS_REGION_LIGHT = "light";
    private static final String ATLAS_REGION_PLAYER_END = "end";
    private static final String ATLAS_REGION_HP_FILLED = "hp-filled";
    private static final String ATLAS_REGION_HP_EMPTY = "hp-empty";

    private static final String FONT_VCR = "fonts/vcr-df.fnt";

    private static final String SHADER_FONT = "shaders/font.vert";

    public GameAssetManager() {
        super();
        configureLoaders();
        loadMaps();
        loadTextures();
        loadFonts();
        finishLoading();
    }

    private void configureLoaders() {
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        setLoader(TiledMap.class, new TmxMapLoader(resolver));
    }

    private void loadMaps() {
        load(MAP1, TiledMap.class);
        load(MAP2, TiledMap.class);
    }

    private void loadTextures() {
        load(ATLAS_LIGHTS, TextureAtlas.class);
        load(ATLAS_CMIYC_FONTS, TextureAtlas.class);
        load(ATLAS_CMIYC_ACTORS, TextureAtlas.class);
    }

    private void loadFonts() {
        BitmapFontLoader.BitmapFontParameter parameter = new BitmapFontLoader.BitmapFontParameter();
        parameter.atlasName = ATLAS_CMIYC_FONTS;
        load(FONT_VCR, BitmapFont.class, parameter);
        load(SHADER_FONT, ShaderProgram.class);
    }

    public TiledMap getTiledMap1() {
        return get(MAP1, TiledMap.class);
    }

    public TiledMap getTiledMap2() {
        return get(MAP2, TiledMap.class);
    }

    public TextureAtlas.AtlasRegion getLightTexture() {
        return get(ATLAS_LIGHTS, TextureAtlas.class).findRegion(ATLAS_REGION_LIGHT);
    }

    public TextureAtlas.AtlasRegion getTextureForActor(Actor actor) {
        return get(ATLAS_CMIYC_ACTORS, TextureAtlas.class).findRegion(actor.getName());
    }

    public TextureAtlas.AtlasRegion getHpFilledTexture() {
        return get(ATLAS_CMIYC_ACTORS, TextureAtlas.class).findRegion(ATLAS_REGION_HP_FILLED);
    }

    public TextureAtlas.AtlasRegion getHpEmptyTexture() {
        return get(ATLAS_CMIYC_ACTORS, TextureAtlas.class).findRegion(ATLAS_REGION_HP_EMPTY);
    }

    public TextureAtlas.AtlasRegion getPlayerEndTexture() {
        return get(ATLAS_CMIYC_ACTORS, TextureAtlas.class).findRegion(ATLAS_REGION_PLAYER_END);
    }

    public BitmapFont getPercentageFont() {
        return get(FONT_VCR, BitmapFont.class);
    }

    public ShaderProgram getFontShaderProgram() {
        return get(SHADER_FONT, ShaderProgram.class);
    }
}
