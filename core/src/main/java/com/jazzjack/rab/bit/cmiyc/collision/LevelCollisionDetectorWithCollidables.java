package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.level.Level;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public abstract class LevelCollisionDetectorWithCollidables extends LevelCollisionDetector {

    private final Set<Collidable> collidables;

    protected LevelCollisionDetectorWithCollidables(Level level) {
        super(level);
        this.collidables = new HashSet<>();
    }

    protected void addCollidable(Collidable... collidables) {
        this.collidables.addAll(asList(collidables));
    }

    protected void addCollidable(List<? extends Collidable> collidables) {
        this.collidables.addAll(collidables);
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        CollisionResult levelCollisionResult = super.collides(collidable, direction);
        if (levelCollisionResult.isUnresolved()) {
            return levelCollisionResult;
        } else {
            return collidesWithAnyCollidable(collidable, direction);
        }
    }

    private CollisionResult collidesWithAnyCollidable(final Collidable collidable, Direction direction) {
        return collidables.stream()
                .filter(collidableFromDetector -> collidableFromDetector.willCollideWith(collidable))
                .findFirst()
                .map((targetCollidable) -> CollisionResult.unresolved(collidable, targetCollidable, direction))
                .orElse(CollisionResult.noCollision());
    }

}
