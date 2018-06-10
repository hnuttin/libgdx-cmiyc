package com.jazzjack.rab.bit.cmiyc.level;

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
    private final List<Supplier<LevelTiledMap>> levelSuppliers;

    private int currentLevelIndex;

    public LevelFactory(LevelContext context, GameAssetManager assetManager) {
        this.context = context;
        this.levelSuppliers = new ArrayList<>();
        this.currentLevelIndex = 1;
        this.levelMetaDataFactory = createLevelMetaDataFactory(assetManager);
        initializeLevelSuppliers(assetManager);
    }

    private LevelMetaDataFactory createLevelMetaDataFactory(GameAssetManager assetManager) {
        return new LevelMetaDataFactory(new ObjectTypeParser(assetManager.getObjectTypesFileHandle()));
    }

    private void initializeLevelSuppliers(GameAssetManager assetManager) {
        levelSuppliers.add(assetManager::getLevelTiledMap1);
        levelSuppliers.add(assetManager::getLevelTiledMap2);
    }

    public Level createLevel(int index) {
        currentLevelIndex = index;
        return createLevel();
    }

    public Level createCurrentLevel() {
        return createLevel();
    }

    public Level createNextLevel() {
        currentLevelIndex++;
        return createLevel();
    }

    private Level createLevel() {
        if (currentLevelIndex - 1 >= levelSuppliers.size()) {
            throw new InvalidLevelException("No new level anymore: finished?");
        } else {
            LevelTiledMap levelTiledMap = levelSuppliers.get(currentLevelIndex - 1).get();
            Level level = new Level(context, levelTiledMap, levelMetaDataFactory.create(levelTiledMap), 9);
            GameEventBus.publishEvent(new NewLevelEvent(level));
            return level;
        }
    }
}
