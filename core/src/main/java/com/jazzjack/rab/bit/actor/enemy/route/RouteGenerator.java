package com.jazzjack.rab.bit.actor.enemy.route;

import com.google.common.collect.Streams;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.common.Direction;
import com.jazzjack.rab.bit.common.Randomizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class RouteGenerator {

    private final CollisionDetector collisionDetector;
    private final Randomizer randomizer;

    public RouteGenerator(CollisionDetector collisionDetector, Randomizer randomizer) {
        this.collisionDetector = collisionDetector;
        this.randomizer = randomizer;
    }

    public List<Route> generateRoutes(Enemy enemy, int amount, int maxLength) {
        StepResultCollisionDetector routeCollisionDetector = new StepResultCollisionDetector(collisionDetector);
        List<RouteResult> routeResults = IntStream.range(0, amount)
                .boxed()
                .map(i -> generateRoute(enemy, maxLength, routeCollisionDetector))
                .filter(route -> !route.getSteps().isEmpty())
                .collect(toList());
        List<Integer> percentages = routeResults.isEmpty() ? emptyList() : randomizer.randomPercentages(enemy.getPredictability(), routeResults.size());
        return Streams
                .zip(percentages.stream(), routeResults.stream(), (percentage, routeResult) -> new Route(percentage, routeResult.getSteps()))
                .collect(toList());
    }

    private RouteResult generateRoute(Actor actor, int maxLength, StepResultCollisionDetector collisionDetector) {
        List<StepResult> stepsResults = new ArrayList<>(maxLength);
        for (int stepIndex = 0; stepIndex < maxLength; stepIndex++) {
            StepResult stepResult = generateStep(
                    stepsResults.isEmpty() ? new StepResult(actor.getX(), actor.getY(), null) : stepsResults.get(stepsResults.size() - 1),
                    copyWithoutDirection(Direction.valuesAsSet(), stepsResults.isEmpty() ? null : stepsResults.get(stepsResults.size() - 1).getDirection().getOppositeDirection()),
                    collisionDetector);
            if (stepResult != null) {
                stepsResults.add(stepResult);
                collisionDetector.addStepResult(stepResult);
            } else {
                break;
            }
        }
        List<Step> steps = convertToSteps(stepsResults);
        return new RouteResult(steps);
    }

    private StepResult generateStep(StepResult previousStep, Set<Direction> allowedDirections, StepResultCollisionDetector routeCollisionDetector) {
        if (allowedDirections.isEmpty()) {
            return null;
        }
        Direction direction = randomizer.randomFromSet(allowedDirections);
        StepResult stepResult = createStepResultForDirection(previousStep, direction);
        if (!routeCollisionDetector.collides(stepResult).isCollision()) {
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
                y = previousStepResult.getY() + 1;
                break;
            case DOWN:
                x = previousStepResult.getX();
                y = previousStepResult.getY() - 1;
                break;
            case LEFT:
                x = previousStepResult.getX() - 1;
                y = previousStepResult.getY();
                break;
            case RIGHT:
                x = previousStepResult.getX() + 1;
                y = previousStepResult.getY();
                break;
            default:
                throw new IllegalArgumentException(direction.name());

        }
        return new StepResult(x, y, direction);
    }

    private Set<Direction> copyWithoutDirection(Set<Direction> allowedDirections, Direction direction) {
        return allowedDirections.stream().filter(d -> d != direction).collect(toSet());
    }

    private List<Step> convertToSteps(List<StepResult> stepsResults) {
        List<Step> steps = new LinkedList<>();
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

    private Step createStepForPreviousStepResult(StepResult previousStepResult, StepResult stepResult) {
        return createStep(StepNames.getBasedOnNextDirection(previousStepResult.getDirection(), stepResult.getDirection()), previousStepResult);
    }

    private Step createEndingStep(StepResult stepResult) {
        return createStep(StepNames.getEndingForDirection(stepResult.getDirection()), stepResult);
    }

    private Step createStep(String stepName, StepResult stepResult) {
        return new Step(stepName, stepResult);
    }

}
