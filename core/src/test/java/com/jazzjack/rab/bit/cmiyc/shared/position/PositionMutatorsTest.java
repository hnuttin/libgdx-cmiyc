package com.jazzjack.rab.bit.cmiyc.shared.position;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.position.PositionMutators.PositionMutator;

import org.junit.jupiter.api.Test;

import static com.jazzjack.rab.bit.cmiyc.shared.position.PositionMutators.forDirection;
import static org.assertj.core.api.Assertions.assertThat;

class PositionMutatorsTest {

    @Test
    void expectLeftDirectionToMutateLeft() {
        PositionMutator positionMutator = forDirection(Direction.LEFT);

        assertThat(positionMutator.mutateX(1)).isEqualTo(0);
        assertThat(positionMutator.mutateY(0)).isEqualTo(0);
    }

    @Test
    void expectRightDirectionToMutateRight() {
        PositionMutator positionMutator = forDirection(Direction.RIGHT);

        assertThat(positionMutator.mutateX(0)).isEqualTo(1);
        assertThat(positionMutator.mutateY(0)).isEqualTo(0);
    }

    @Test
    void expectUpDirectionToMutateUp() {
        PositionMutator positionMutator = forDirection(Direction.UP);

        assertThat(positionMutator.mutateX(0)).isEqualTo(0);
        assertThat(positionMutator.mutateY(0)).isEqualTo(1);
    }

    @Test
    void expectDownDirectionToMutateDown() {
        PositionMutator positionMutator = forDirection(Direction.DOWN);

        assertThat(positionMutator.mutateX(0)).isEqualTo(0);
        assertThat(positionMutator.mutateY(1)).isEqualTo(0);
    }

}