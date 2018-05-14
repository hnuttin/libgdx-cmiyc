package com.jazzjack.rab.bit.collision;

import com.jazzjack.rab.bit.Level;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class LevelCollisionDetectorWithCollidables extends LevelCollisionDetector {

    private final Set<Collidable> collidables;

    public LevelCollisionDetectorWithCollidables(Level level) {
        super(level);
        this.collidables = new HashSet<>();
    }

    public void addCollidable(Collidable... collidables) {
        this.collidables.addAll(asList(collidables));
    }

    public void addCollidable(List<? extends Collidable> collidables) {
        this.collidables.addAll(collidables);
    }

    @Override
    public CollisionResult collides(Collidable collidable) {
        CollisionResult levelCollisionResult = super.collides(collidable);
        if (levelCollisionResult.isCollision()) {
            return levelCollisionResult;
        } else {
            return collidesWithAnyCollidable(collidable);
        }
    }

    private CollisionResult collidesWithAnyCollidable(final Collidable collidable) {
        return collidables.stream()
                .filter(collidableFromDetector -> collidableFromDetector.collidesWith(collidable))
                .findFirst()
                .map(CollisionResult::collision)
                .orElse(CollisionResult.noCollision());
    }

}
