package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;
import com.jazzjack.rab.bit.cmiyc.shared.Sense;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DirectionChanceCalculator {

    private final HasPosition targetPosition;

    public DirectionChanceCalculator(HasPosition targetPosition) {
        this.targetPosition = targetPosition;
    }

    public List<DirectionChance> calculate(HasPosition startPosition, Collection<Direction> directions, Sense sense) {
        List<Direction> directionsTowardsTargetPosition = determineDirectionsTowardsTargetPosition(startPosition);
        int numberOfDirectionsTowardsPosition = (int) directions.stream().filter(directionsTowardsTargetPosition::contains).count();
        int restPercentage = (Randomizer.HUNDRED_PERCENT - (numberOfDirectionsTowardsPosition * sense.getPercentageWeight())) / directions.size();
        return directions.stream()
                .map(direction -> new DirectionChance(direction, calculatePercentage(directionsTowardsTargetPosition, direction, sense, restPercentage)))
                .collect(toList());
    }

    private int calculatePercentage(List<Direction> directionsTowardsTargetPosition, Direction direction, Sense sense, int restPercentage) {
        if (directionsTowardsTargetPosition.contains(direction)) {
            return sense.getPercentageWeight() + restPercentage;
        } else {
            return restPercentage;
        }
    }

    private List<Direction> determineDirectionsTowardsTargetPosition(HasPosition startPosition) {
        List<Direction> directions = new ArrayList<>();
        if (startPosition.getX() < targetPosition.getX()) {
            directions.add(Direction.RIGHT);
        } else if (startPosition.getX() > targetPosition.getY()) {
            directions.add(Direction.LEFT);
        }
        if (startPosition.getY() < targetPosition.getY()) {
            directions.add(Direction.UP);
        } else if (startPosition.getY() > targetPosition.getY()) {
            directions.add(Direction.DOWN);
        }
        return directions;
    }
}
