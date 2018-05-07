package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.collision.CollisionDetector;
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
        RouteCollisionDetector routeCollisionDetector = new RouteCollisionDetector(collisionDetector);
        return IntStream.range(0, amount)
                .boxed()
                .map(i -> generateRoute(actor, maxLength, routeCollisionDetector))
                .collect(toSet());
    }

    private Route generateRoute(Actor actor, int maxLength, RouteCollisionDetector routeCollisionDetector) {
        List<StepResult> stepsResults = new ArrayList<>(maxLength);
        for (int stepIndex = 0; stepIndex < maxLength; stepIndex++) {
            StepResult stepResult = generateStep(
                    stepsResults.isEmpty() ? new StepResult(actor.getX(), actor.getY(), actor.getSize(), null) : stepsResults.get(stepsResults.size() - 1),
                    copyWithoutDirection(Direction.valuesAsSet(), stepsResults.isEmpty() ? null : stepsResults.get(stepsResults.size() - 1).getDirection().getOppositeDirection()),
                    routeCollisionDetector);
            if (stepResult != null) {
                stepsResults.add(stepResult);
                routeCollisionDetector.addStepResult(stepResult);
            } else {
                break;
            }
        }
        List<Actor> steps = convertToSteps(stepsResults);
        return new Route(steps);
    }

    private StepResult generateStep(StepResult previousStep, Set<Direction> allowedDirections, RouteCollisionDetector routeCollisionDetector) {
        if (allowedDirections.isEmpty()) {
            return null;
        }
        Direction direction = randomizer.randomFromSet(allowedDirections);
        StepResult stepResult = createStepResultForDirection(previousStep, direction);
        if (!routeCollisionDetector.collides(stepResult)) {
            return stepResult;
        } else {
            return generateStep(previousStep, copyWithoutDirection(allowedDirections, direction), routeCollisionDetector);
        }
    }

    private StepResult createStepResultForDirection(StepResult previousStepResult, Direction direction) {
        final float x;
        final float y;
        switch (direction) {
            case UP:
                x = previousStepResult.getX();
                y = previousStepResult.getY() + previousStepResult.getSize();
                break;
            case DOWN:
                x = previousStepResult.getX();
                y = previousStepResult.getY() - previousStepResult.getSize();
                break;
            case LEFT:
                x = previousStepResult.getX() - previousStepResult.getSize();
                y = previousStepResult.getY();
                break;
            case RIGHT:
                x = previousStepResult.getX() + previousStepResult.getSize();
                y = previousStepResult.getY();
                break;
            default:
                throw new IllegalArgumentException(direction.name());

        }
        return new StepResult(x, y, previousStepResult.getSize(), direction);
    }

    private Set<Direction> copyWithoutDirection(Set<Direction> allowedDirections, Direction direction) {
        return allowedDirections.stream().filter(d -> d != direction).collect(toSet());
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

    private Actor createStepForPreviousStepResult(StepResult previousStepResult, StepResult stepResult) {
        return createStep(StepNames.getBasedOnNextDirection(previousStepResult.getDirection(), stepResult.getDirection()), previousStepResult);
    }

    private Actor createEndingStep(StepResult stepResult) {
        return createStep(StepNames.getEndingForDirection(stepResult.getDirection()), stepResult);
    }

    private Actor createStep(String stepName, StepResult stepResult) {
        return new SimpleActor(stepName, stepResult.getX(), stepResult.getY(), stepResult.getSize());
    }

}
