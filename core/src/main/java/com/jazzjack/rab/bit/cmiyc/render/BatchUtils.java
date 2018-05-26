package com.jazzjack.rab.bit.cmiyc.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

class BatchUtils {

    private BatchUtils() {}

    static void drawWithAlpha(Batch batch, float alpha, Runnable runnable) {
        Color color = batch.getColor();
        batch.setColor(color.r, color.g, color.b, alpha);
        runnable.run();
        batch.setColor(color);
    }
}
