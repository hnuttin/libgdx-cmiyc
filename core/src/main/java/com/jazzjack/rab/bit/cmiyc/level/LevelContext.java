package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile;
import com.jazzjack.rab.bit.cmiyc.animation.AnimationRegister;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;

public class LevelContext {

    private final CollisionResolver collisionResolver;
    private final Randomizer randomizer;
    private final AnimationRegister animationRegister;
    private final PlayerProfile playerProfile;

    public LevelContext(CollisionResolver collisionResolver, Randomizer randomizer, AnimationRegister animationRegister, PlayerProfile playerProfile) {
        this.collisionResolver = collisionResolver;
        this.randomizer = randomizer;
        this.animationRegister = animationRegister;
        this.playerProfile = playerProfile;
    }

    CollisionResolver getCollisionResolver() {
        return collisionResolver;
    }

    Randomizer getRandomizer() {
        return randomizer;
    }

    AnimationRegister getAnimationRegister() {
        return animationRegister;
    }

    PlayerProfile getPlayerProfile() {
        return playerProfile;
    }
}
