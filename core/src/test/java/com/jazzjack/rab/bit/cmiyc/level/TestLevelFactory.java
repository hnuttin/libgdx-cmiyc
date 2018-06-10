package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.animation.AnimationRegister;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandleResolver;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.shared.RandomInteger;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestLevelFactory {

    private TestLevelFactory() {
    }

    static Level createLevel(String levelFile) {
        LevelContext levelContext = new LevelContext(new CollisionResolver(), new Randomizer(new RandomInteger()), mock(AnimationRegister.class));
        LevelTiledMap levelTiledMap = new LevelTiledMap(new LevelTiledMapLoader(new ClasspathFileHandleResolver()).load(levelFile));
        GameAssetManager assetManagerMock = mock(GameAssetManager.class);
        when(assetManagerMock.getObjectTypesFileHandle()).thenReturn(new ClasspathFileHandle("objecttypes.xml"));
        when(assetManagerMock.getLevelTiledMap1()).thenReturn(levelTiledMap);
        return new LevelFactory(levelContext, assetManagerMock).createLevel(1);
    }

}
