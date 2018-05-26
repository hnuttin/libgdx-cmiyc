package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaDataFactory;
import com.jazzjack.rab.bit.cmiyc.level.meta.ObjectTypeParser;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LevelFactory {

    private final LevelMetaDataFactory levelMetaDataFactory;
    private final List<Supplier<TiledMap>> levelSuppliers;

    private int currentLevelIndex;

    public LevelFactory(GameAssetManager assetManager) {
        this.levelSuppliers = new ArrayList<>();
        this.currentLevelIndex = 0;
        this.levelMetaDataFactory = createLevelMetaDataFactory();
        initializeLevelSuppliers(assetManager);
    }

    private LevelMetaDataFactory createLevelMetaDataFactory() {
        return new LevelMetaDataFactory(new ObjectTypeParser(new FileHandle("objecttypes.xml")));
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
            Level level = new Level(levelTiledMap, levelMetaDataFactory.create(levelTiledMap), 9);
            currentLevelIndex++;
            return level;
        }
    }
}
