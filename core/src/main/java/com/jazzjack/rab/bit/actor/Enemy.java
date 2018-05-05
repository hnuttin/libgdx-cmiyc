package com.jazzjack.rab.bit.actor;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashSet;
import java.util.Set;

public class Enemy implements Actor {

    private final Texture texture;

    private float x;
    private float y;
    
    private Set<Route> routes;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        texture = new Texture("pixel-art/enemy/enemy1.png");
        routes = new HashSet<>();
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
    
    public void generateRoutes(int amount) {
        routes.clear();
        for (int i = 0; i < amount; i++) {
            routes.add(generateRoute());
        }
    }

    private Route generateRoute() {
        Direction direction = Direction.random();
        return null;
    }
}
