package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.Sense;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class DirectionChanceCalculatorTest {

    private final DirectionChanceCalculator calculator = new DirectionChanceCalculator(new Position(2, 2));

    @Test
    void expectEqualChancesWhenAllDirectionsAreNotInDirectionOfTargetPosition() {
        List<DirectionChance> directionChances = calculator.calculate(new Position(3, 3), asList(Direction.UP, Direction.RIGHT), Sense.LOW);

        assertThat(directionChances).hasSize(2);
        assertThat(directionChances.get(0).getDirection()).isEqualTo(Direction.UP);
        assertThat(directionChances.get(0).getPercentage()).isEqualTo(50);
        assertThat(directionChances.get(1).getDirection()).isEqualTo(Direction.RIGHT);
        assertThat(directionChances.get(1).getPercentage()).isEqualTo(50);
    }

    @Test
    void expectEqualChancesWhenAllDirectionsAreInDirectionOfTargetPosition() {
        List<DirectionChance> directionChances = calculator.calculate(new Position(3, 3), asList(Direction.DOWN, Direction.LEFT), Sense.LOW);

        assertThat(directionChances).hasSize(2);
        assertThat(directionChances.get(0).getDirection()).isEqualTo(Direction.DOWN);
        assertThat(directionChances.get(0).getPercentage()).isEqualTo(50);
        assertThat(directionChances.get(1).getDirection()).isEqualTo(Direction.LEFT);
        assertThat(directionChances.get(1).getPercentage()).isEqualTo(50);
    }

    @Test
    void expectSenseWeightAddedWhenOneDirectionIsInDirectionOfTargetPosition() {
        List<DirectionChance> directionChances = calculator.calculate(new Position(3, 3), asList(Direction.DOWN, Direction.RIGHT), Sense.LOW);

        assertThat(directionChances).hasSize(2);
        assertThat(directionChances.get(0).getDirection()).isEqualTo(Direction.DOWN);
        assertThat(directionChances.get(0).getPercentage()).isEqualTo(62);
        assertThat(directionChances.get(1).getDirection()).isEqualTo(Direction.RIGHT);
        assertThat(directionChances.get(1).getPercentage()).isEqualTo(37);
    }

    @Test
    void expectSenseWeightAddedWhenMultipleDirectionsAreInDirectionOfTargetPosition() {
        List<DirectionChance> directionChances = calculator.calculate(new Position(1, 1), asList(Direction.UP, Direction.RIGHT, Direction.DOWN), Sense.MEDIUM);

        assertThat(directionChances).hasSize(3);
        assertThat(directionChances.get(0).getDirection()).isEqualTo(Direction.UP);
        assertThat(directionChances.get(0).getPercentage()).isEqualTo(45);
        assertThat(directionChances.get(1).getDirection()).isEqualTo(Direction.RIGHT);
        assertThat(directionChances.get(1).getPercentage()).isEqualTo(45);
        assertThat(directionChances.get(2).getDirection()).isEqualTo(Direction.DOWN);
        assertThat(directionChances.get(2).getPercentage()).isEqualTo(10);
    }

}