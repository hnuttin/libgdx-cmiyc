package com.jazzjack.rab.bit.cmiyc.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class LibGdxAlphaDrawerSupport extends LibGdxTest {

    @Mock
    protected Batch batch;

    private Color originalColor;

    @BeforeEach
    void beforeEachRenderTest() {
        originalColor = new Color();
        when(batch.getColor()).thenReturn(originalColor);
    }

    protected AlphaAsserter alphaAsserter(InOrder inOrder) {
        return new AlphaAsserter(inOrder);
    }

    protected class AlphaAsserter {

        private final InOrder inOrder;
        private float alpha;

        private AlphaAsserter(InOrder inOrder) {
            this.inOrder = inOrder;
        }

        public AlphaAsserter withAlpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public void doAssert(Runnable toAssert) {
            inOrder.verify(batch).setColor(0f, 0f, 0f, alpha);
            toAssert.run();
            inOrder.verify(batch).setColor(originalColor);
        }
    }
}
