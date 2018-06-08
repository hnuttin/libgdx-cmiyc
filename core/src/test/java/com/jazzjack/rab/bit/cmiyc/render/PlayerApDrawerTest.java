package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.collision.NeverCollideCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.gdx.LibGdxAlphaDrawerSupport;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class PlayerApDrawerTest extends LibGdxAlphaDrawerSupport {

    @InjectMocks
    private PlayerApDrawer playerApDrawer;
    @Mock
    private GameAssetManager assetManager;
    @Mock
    private GameCamera camera;

    @Mock
    private TextureAtlas.AtlasRegion apStartTexture;
    @Mock
    private TextureAtlas.AtlasRegion apMiddleTexture;
    @Mock
    private TextureAtlas.AtlasRegion apEndTexture;

    @BeforeEach
    void beforeEach() {
        when(assetManager.getApStartTexture()).thenReturn(apStartTexture);
        when(assetManager.getApMiddleTexture()).thenReturn(apMiddleTexture);
        when(assetManager.getApEndTexture()).thenReturn(apEndTexture);

        when(camera.getViewportWidth()).thenReturn(10f);
    }

    @Test
    void expectConsumedApDrawnCorrectly() {
        ActorContext actorContext = new ActorContext(NeverCollideCollisionDetector.TEST_INSTANCE, mock(CollisionResolver.class));
        Player player = new Player(actorContext, new Position(0, 0), 5);
        player.moveToDirection(Direction.RIGHT);
        player.moveToDirection(Direction.RIGHT);

        playerApDrawer.draw(player);

        InOrder inOrder = inOrder(batch);
        alphaAsserter(inOrder)
                .withAlpha(0.3f)
                .doAssert(() -> inOrder.verify(batch).draw(apStartTexture, 5f, 0f, 1f, 1f));
        alphaAsserter(inOrder)
                .withAlpha(0.3f)
                .doAssert(() -> inOrder.verify(batch).draw(apMiddleTexture, 6f, 0f, 1f, 1f));
        inOrder.verify(batch).draw(apMiddleTexture, 7f, 0f, 1f, 1f);
        inOrder.verify(batch).draw(apMiddleTexture, 8f, 0f, 1f, 1f);
        inOrder.verify(batch).draw(apEndTexture, 9f, 0f, 1f, 1f);
        inOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(batch);
    }

}