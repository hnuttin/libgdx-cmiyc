package com.jazzjack.rab.bit.cmiyc.collision;

import com.jazzjack.rab.bit.cmiyc.level.LevelTiledMap;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class LevelCollisionDetectorWithCollidables extends LevelCollisionDetector {

    private final Set<Collidable> collidables;

    public LevelCollisionDetectorWithCollidables(LevelTiledMap levelTiledMap) {
        super(levelTiledMap);
        this.collidables = new HashSet<>();
    }

    public void addCollidable(Collidable... collidables) {
        this.collidables.addAll(asList(collidables));
    }

    public void addCollidable(List<? extends Collidable> collidables) {
        this.collidables.addAll(collidables);
    }

    public void removeCollidable(Collidable collidable) {
        collidables.remove(collidable);
    }

    @Override
    public CollisionResult collides(Collidable collidable, Direction direction) {
        CollisionResult levelCollisionResult = super.collides(collidable, direction);
        if (levelCollisionResult.isCollision()) {
            return levelCollisionResult;
        } else {
            return collidesWithAnyCollidable(collidable, direction);
        }
    }

    private CollisionResult collidesWithAnyCollidable(final Collidable collidable, Direction direction) {
        return collidables.stream()
                .filter(collidableFromDetector -> !collidableFromDetector.equals(collidable))
                .filter(collidableFromDetector -> collidableFromDetector.willCollideWith(collidable))
                .findFirst()
                .map(targetCollidable -> CollisionResult.collision(collidable, targetCollidable, direction))
                .orElse(CollisionResult.noCollision());
    }

}
