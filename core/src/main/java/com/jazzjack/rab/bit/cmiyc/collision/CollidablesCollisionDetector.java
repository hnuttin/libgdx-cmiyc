package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class CollidablesCollisionDetector implements CollisionDetector {

    private final Set<Collidable> collidables;

    public CollidablesCollisionDetector() {
        this.collidables = new HashSet<>();
    }

    public void addCollidable(Collidable... collidables) {
        this.collidables.addAll(asList(collidables));
    }

    public void removeCollidable(Collidable collidable) {
        collidables.remove(collidable);
    }

    public void clearCollidables() {
        collidables.clear();
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        return collidables.stream()
                .filter(collidableFromDetector -> !collidableFromDetector.equals(collidable))
                .filter(collidableFromDetector -> collidableFromDetector.willCollideWith(collidable))
                .findFirst()
                .map(targetCollidable -> CollisionResult.collision(collidable, targetCollidable, direction))
                .orElse(CollisionResult.noCollision());
    }
}
