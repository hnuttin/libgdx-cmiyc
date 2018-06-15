package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile;
import com.jazzjack.rab.bit.cmiyc.animation.AnimationHandler;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandleResolver;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.shared.RandomInteger;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;

import static com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile.playerProfileBuilder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestLevelFactory {

    private TestLevelFactory() {
    }

    public static LevelFactory createLevelFactory(String levelFile) {
        return createLevelFactory(playerProfileBuilder().build(), levelFile);
    }

    public static LevelFactory createLevelFactory(PlayerProfile playerProfile, String levelFile) {
        return createLevelFactory(playerProfile, new AnimationHandler(), levelFile);
    }

    public static LevelFactory createLevelFactory(PlayerProfile playerProfile, AnimationHandler animationRegister, String levelFile) {
        LevelContext levelContext = new LevelContext(new CollisionResolver(), new Randomizer(new RandomInteger()), animationRegister, playerProfile);
        LevelTiledMap levelTiledMap = new LevelTiledMap(new LevelTiledMapLoader(new ClasspathFileHandleResolver()).load(levelFile));
        GameAssetManager assetManagerMock = mock(GameAssetManager.class);
        when(assetManagerMock.getObjectTypesFileHandle()).thenReturn(new ClasspathFileHandle("objecttypes.xml"));
        when(assetManagerMock.getLevelTiledMap1()).thenReturn(levelTiledMap);
        return new LevelFactory(levelContext, assetManagerMock);
    }

    public static Level createLevel(String levelFile) {
        return createLevelFactory(levelFile).createLevel(1);
    }

}
