package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

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

    void draw(Runnable toDraw) {
        if (alpha != 1f) {
            Color color = batch.getColor();
            batch.setColor(color.r, color.g, color.b, alpha);
            toDraw.run();
            batch.setColor(color);
        } else {
            toDraw.run();
        }
    }

    static AlphaDrawer alphaDrawer(Batch batch) {
        return new AlphaDrawer(batch);
    }

}
