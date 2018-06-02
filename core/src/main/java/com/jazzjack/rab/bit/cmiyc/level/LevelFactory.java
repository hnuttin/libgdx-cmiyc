package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaDataFactory;
import com.jazzjack.rab.bit.cmiyc.level.meta.ObjectTypeParser;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LevelFactory {

    private final LevelContext context;
    private final LevelMetaDataFactory levelMetaDataFactory;
    private final List<Supplier<TiledMap>> levelSuppliers;

    private int currentLevelIndex;

    public LevelFactory(LevelContext context, GameAssetManager assetManager) {
        this.context = context;
        this.levelSuppliers = new ArrayList<>();
        this.currentLevelIndex = 0;
        this.levelMetaDataFactory = createLevelMetaDataFactory(assetManager);
        initializeLevelSuppliers(assetManager);
    }

    private LevelMetaDataFactory createLevelMetaDataFactory(GameAssetManager assetManager) {
        return new LevelMetaDataFactory(new ObjectTypeParser(assetManager.getObjectTypesFileHandle()));
    }

    private void initializeLevelSuppliers(GameAssetManager assetManager) {
        levelSuppliers.add(assetManager::getTiledMap1);
        levelSuppliers.add(assetManager::getTiledMap2);
    }

    public Level createCurrentLevel() {
        currentLevelIndex--;
        return createNextLevel();
    }

    public Level createNextLevel() {
        if (currentLevelIndex >= levelSuppliers.size()) {
            throw new InvalidLevelException("No new level anymore: finished?");
        } else {
            LevelTiledMap levelTiledMap = new LevelTiledMap(levelSuppliers.get(currentLevelIndex).get());
            Level level = new Level(context, levelTiledMap, levelMetaDataFactory.create(levelTiledMap), 9);
            currentLevelIndex++;
            GameEventBus.publishEvent(new NewLevelEvent(level));
            return level;
        }
    }
}
