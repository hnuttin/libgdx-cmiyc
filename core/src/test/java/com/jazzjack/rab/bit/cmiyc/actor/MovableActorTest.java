package com.jazzjack.rab.bit.cmiyc.actor;

import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResolver;
import com.jazzjack.rab.bit.cmiyc.collision.NeverCollideCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class MovableActorTest {

    @Test
    void expectPositionCorrectWhenMovingRight() {
        MovableActor movableActor = movableActor(-1, 0);

        movableActor.moveToDirection(Direction.RIGHT);

        assertThat(movableActor.getX()).isEqualTo(0);
        assertThat(movableActor.getY()).isEqualTo(0);
    }

    @Test
    void expectPositionCorrectWhenMovingLeft() {
        MovableActor movableActor = movableActor(1, 0);

        movableActor.moveToDirection(Direction.LEFT);

        assertThat(movableActor.getX()).isEqualTo(0);
        assertThat(movableActor.getX()).isEqualTo(0);
    }

    @Test
    void expectPositionCorrectWhenMovingUp() {
        MovableActor movableActor = movableActor(0, -1);

        movableActor.moveToDirection(Direction.UP);

        assertThat(movableActor.getX()).isEqualTo(0);
        assertThat(movableActor.getX()).isEqualTo(0);
    }

    @Test
    void expectPositionCorrectWhenMovingDown() {
        MovableActor movableActor = movableActor(0, 1);

        movableActor.moveToDirection(Direction.DOWN);

        assertThat(movableActor.getX()).isEqualTo(0);
        assertThat(movableActor.getX()).isEqualTo(0);
    }

    private MovableActor movableActor(int x, int y) {
        ActorContext context = new ActorContext(NeverCollideCollisionDetector.TEST_INSTANCE, Mockito.mock(CollisionResolver.class));
        return new MovableActor(context, "actor", new Position(x, y));
    }

}