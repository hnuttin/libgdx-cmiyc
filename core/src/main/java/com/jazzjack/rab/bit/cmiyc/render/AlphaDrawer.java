package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.function.Consumer;

class AlphaDrawer {

    private final Batch batch;
    private float alpha;

    private AlphaDrawer(Batch batch) {
        this.batch = batch;
    }

    AlphaDrawer withAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    void draw(Consumer<Batch> toDraw) {
        if (alpha != 1f) {
            Color color = batch.getColor();
            batch.setColor(color.r, color.g, color.b, alpha);
            toDraw.accept(batch);
            batch.setColor(color);
        } else {
            toDraw.accept(batch);
        }
    }

    static AlphaDrawer alphaDrawer(Batch batch) {
        return new AlphaDrawer(batch);
    }

}
