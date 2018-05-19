package com.jazzjack.rab.bit.render;

public interface Renderer {
    void render();

    void resize(int width, int height);

    void dispose();
}
