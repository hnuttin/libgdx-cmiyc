package com.jazzjack.rab.bit;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.route.StepNames;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager extends AssetManager {

    private static final String MAP1 = "pixel-art1.tmx";
    private static final String LIGHTS = "lights.atlas";

    private static final Map<String, String> actorTextureMapping;

    private static final String ROMANTICS10 = "romantics10.ttf";

    static {
        actorTextureMapping = new HashMap<>();
        actorTextureMapping.put("player", "pixel-art/player.png");
        actorTextureMapping.put("enemy1", "pixel-art/enemy/enemy1.png");
        actorTextureMapping.put(StepNames.HORIZONTAL, "pixel-art/enemy/route-horizontal.png");
        actorTextureMapping.put(StepNames.VERTICAL, "pixel-art/enemy/route-vertical.png");
        actorTextureMapping.put(StepNames.ENDING_BOTTOM, "pixel-art/enemy/route-ending-bottom.png");
        actorTextureMapping.put(StepNames.ENDING_LEFT, "pixel-art/enemy/route-ending-left.png");
        actorTextureMapping.put(StepNames.ENDING_RIGHT, "pixel-art/enemy/route-ending-right.png");
        actorTextureMapping.put(StepNames.ENDING_TOP, "pixel-art/enemy/route-ending-top.png");
        actorTextureMapping.put(StepNames.CORNER_BOTTOM_LEFT, "pixel-art/enemy/route-corner-bottom-left.png");
        actorTextureMapping.put(StepNames.CORNER_BOTTOM_RIGHT, "pixel-art/enemy/route-corner-bottom-right.png");
        actorTextureMapping.put(StepNames.CORNER_TOP_LEFT, "pixel-art/enemy/route-corner-top-left.png");
        actorTextureMapping.put(StepNames.CORNER_TOP_RIGHT, "pixel-art/enemy/route-corner-top-right.png");
    }

    public GameAssetManager() {
        super();
        configureLoaders();
        load(MAP1, TiledMap.class);
        load(LIGHTS, TextureAtlas.class);
        loadActorTextures();
        loadFonts();
        finishLoading();
    }

    private void configureLoaders() {
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        setLoader(TiledMap.class, new TmxMapLoader(resolver));
        setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    private void loadActorTextures() {
        for (String texturePath : actorTextureMapping.values()) {
            load(texturePath, Texture.class);
        }
    }

    private void loadFonts() {
        FreetypeFontLoader.FreeTypeFontLoaderParameter romantics10 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        romantics10.fontFileName = "fonts/romantics.ttf";
        romantics10.fontParameters.size = 10;
        load(ROMANTICS10, BitmapFont.class, romantics10);
    }

    public TiledMap getTiledMap1() {
        return get(MAP1, TiledMap.class);
    }

    public TextureAtlas.AtlasRegion getLightAtlasRegion() {
        return get(LIGHTS, TextureAtlas.class).findRegion("light");
    }

    public Texture getTextureForActor(Actor actor) {
        return get(actorTextureMapping.get(actor.getName()), Texture.class);
    }

    public BitmapFont getPercentageFont() {
        return get(ROMANTICS10, BitmapFont.class);
    }
}
