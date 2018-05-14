package com.jazzjack.rab.bit.animation;

import java.util.concurrent.CompletableFuture;

public interface AnimationRegister {
    CompletableFuture<Void> registerAnimation(Animation animation);
}
