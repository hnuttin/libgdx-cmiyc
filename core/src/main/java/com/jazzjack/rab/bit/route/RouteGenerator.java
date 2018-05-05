package com.jazzjack.rab.bit.route;

import com.badlogic.gdx.math.Rectangle;
import com.jazzjack.rab.bit.CollisionDetector;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.common.Direction;
import com.jazzjack.rab.bit.common.Randomizer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class RouteGenerator {

    private final CollisionDetector collisionDetector;
    private final Randomizer randomizer;

    public RouteGenerator(CollisionDetector collisionDetector, Randomizer randomizer) {
        this.collisionDetector = collisionDetector;
        this.randomizer = randomizer;
    }

    public Set<Route> generateRoutes(Actor actor, int amount, int maxLength) {
        return IntStream.range(0, amount)
                .boxed()
                .map(i -> generateRoute(actor, maxLength))
                .collect(toSet());
    }

    private Route generateRoute(Actor actor, int maxLength) {
        LinkedHashSet<Actor> steps = new LinkedHashSet<>(maxLength);
        RouteGenerator.StepResult previousStepResult = null;
        for (int stepIndex = 0; stepIndex < maxLength; stepIndex++) {
            RouteGenerator.StepResult stepResult = generateStep(
                    previousStepResult == null ? actor : previousStepResult.step,
                    copyWithoutDirection(asList(Direction.values()), previousStepResult == null ? null : previousStepResult.direction.getOppositeDirection()));
            if (stepResult != null) {
                steps.add(stepResult.step);
                previousStepResult = stepResult;
            } else {
                break;
            }
        }
        return new Route(steps);
    }

    private RouteGenerator.StepResult generateStep(Actor previousStep, List<Direction> allowedDirections) {
        if (allowedDirections.isEmpty()) {
            return null;
        }
        Direction direction = randomizer.randomFromList(allowedDirections);
        Rectangle stepRectangle = createStepRectangleForDirection(previousStep, direction);
        if (!collisionDetector.collides(stepRectangle)) {
            return new RouteGenerator.StepResult(new SimpleActor(direction.getOrientation(), stepRectangle.getX(), stepRectangle.getY(), stepRectangle.getWidth()), direction);
        } else {
            return generateStep(previousStep, copyWithoutDirection(allowedDirections, direction));
        }
    }

    private List<Direction> copyWithoutDirection(List<Direction> allowedDirections, Direction direction) {
        return allowedDirections.stream().filter(d -> d != direction).collect(toList());
    }

    private Rectangle createStepRectangleForDirection(Actor previousStep, Direction direction) {
        switch (direction) {
            case UP:
                return createStepRectangle(previousStep.getX(), previousStep.getY() + previousStep.getSize(), previousStep.getSize());
            case DOWN:
                return createStepRectangle(previousStep.getX(), previousStep.getY() - previousStep.getSize(), previousStep.getSize());
            case LEFT:
                return createStepRectangle(previousStep.getX() - previousStep.getSize(), previousStep.getY(), previousStep.getSize());
            case RIGHT:
                return createStepRectangle(previousStep.getX() + previousStep.getSize(), previousStep.getY(), previousStep.getSize());
        }
        throw new IllegalArgumentException(direction.name());
    }

    private Rectangle createStepRectangle(float x, float y, float size) {
        return new Rectangle(x, y, size, size);
    }

    private static class StepResult {

        private final Actor step;
        private final Direction direction;

        private StepResult(Actor step, Direction direction) {
            this.step = step;
            this.direction = direction;
        }
    }
}
