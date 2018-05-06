package com.jazzjack.rab.bit.route;

import com.badlogic.gdx.math.Rectangle;
import com.jazzjack.rab.bit.CollisionDetector;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.common.Direction;
import com.jazzjack.rab.bit.common.Randomizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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
        List<StepResult> stepsResults = new ArrayList<>(maxLength);
        for (int stepIndex = 0; stepIndex < maxLength; stepIndex++) {
            StepResult stepResult = generateStep(
                    stepsResults.isEmpty() ? new StepResult(actor.getX(), actor.getY(), actor.getSize(), null) : stepsResults.get(stepsResults.size() - 1),
                    copyWithoutDirection(Direction.valuesAsSet(), stepsResults.isEmpty() ? null : stepsResults.get(stepsResults.size() - 1).direction.getOppositeDirection()));
            if (stepResult != null) {
                stepsResults.add(stepResult);
            } else {
                break;
            }
        }
        List<Actor> steps = convertToSteps(stepsResults);
        return new Route(steps);
    }

    private List<Actor> convertToSteps(List<StepResult> stepsResults) {
        List<Actor> steps = new LinkedList<>();
        int index = 0;
        for (StepResult stepResult : stepsResults) {
            if (index > 0) {
                steps.add(createStepForPreviousStepResult(stepsResults.get(index - 1), stepResult));
            }
            if (index == stepsResults.size() - 1) {
                steps.add(createEndingStep(stepResult));
            }
            index++;
        }
        return steps;
    }

    private Actor createEndingStep(StepResult stepResult) {
        return createStep(StepNames.getEndingForDirection(stepResult.direction), stepResult);
    }

    private Actor createStepForPreviousStepResult(StepResult previousStepResult, StepResult stepResult) {
        return createStep(StepNames.getBasedOnNextDirection(previousStepResult.direction, stepResult.direction), previousStepResult);
    }

    private Actor createStep(String stepName, StepResult stepResult) {
        return new SimpleActor(stepName, stepResult.x, stepResult.y, stepResult.size);
    }

    private StepResult generateStep(StepResult previousStep, Set<Direction> allowedDirections) {
        if (allowedDirections.isEmpty()) {
            return null;
        }
        Direction direction = randomizer.randomFromSet(allowedDirections);
        Rectangle stepRectangle = createStepRectangleForDirection(previousStep, direction);
        if (!collisionDetector.collides(stepRectangle)) {
            return new StepResult(stepRectangle.getX(), stepRectangle.getY(), stepRectangle.getWidth(), direction);
        } else {
            return generateStep(previousStep, copyWithoutDirection(allowedDirections, direction));
        }
    }

    private Set<Direction> copyWithoutDirection(Set<Direction> allowedDirections, Direction direction) {
        return allowedDirections.stream().filter(d -> d != direction).collect(toSet());
    }

    private Rectangle createStepRectangleForDirection(StepResult previousStepResult, Direction direction) {
        switch (direction) {
            case UP:
                return createStepRectangle(previousStepResult.x, previousStepResult.y + previousStepResult.size, previousStepResult.size);
            case DOWN:
                return createStepRectangle(previousStepResult.x, previousStepResult.y - previousStepResult.size, previousStepResult.size);
            case LEFT:
                return createStepRectangle(previousStepResult.x - previousStepResult.size, previousStepResult.y, previousStepResult.size);
            case RIGHT:
                return createStepRectangle(previousStepResult.x + previousStepResult.size, previousStepResult.y, previousStepResult.size);
        }
        throw new IllegalArgumentException(direction.name());
    }

    private Rectangle createStepRectangle(float x, float y, float size) {
        return new Rectangle(x, y, size, size);
    }

    private static class StepResult {

        private final float x;
        private final float y;
        private final float size;
        private final Direction direction;

        private StepResult(float x, float y, float size, Direction direction) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.direction = direction;
        }
    }
}
