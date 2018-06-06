package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.google.common.collect.Streams;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.Step;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.StepNames;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.StepResult;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.StepResultCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;
import com.jazzjack.rab.bit.cmiyc.shared.Sense;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

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
    private final DirectionChanceCalculator directionChanceCalculator;
    private final Randomizer randomizer;

    public RouteGenerator(CollisionDetector collisionDetector, DirectionChanceCalculator directionChanceCalculator, Randomizer randomizer) {
        this.collisionDetector = collisionDetector;
        this.directionChanceCalculator = directionChanceCalculator;
        this.randomizer = randomizer;
    }

    public List<Route> generateRoutes(Enemy enemy, int amount, int maxLength) {
        StepResultCollisionDetector routeCollisionDetector = new StepResultCollisionDetector(collisionDetector);
        routeCollisionDetector.addStepResult(enemy);
        List<RouteResult> routeResults = IntStream.range(0, amount)
                .boxed()
                .map(i -> generateRoute(enemy, maxLength, routeCollisionDetector, enemy.getSense()))
                .filter(route -> !route.getSteps().isEmpty())
                .collect(toList());
        List<Integer> percentages = routeResults.isEmpty() ? emptyList() : randomizer.randomPercentages(enemy.getPredictability(), routeResults.size());
        return Streams
                .zip(percentages.stream(), routeResults.stream(), (percentage, routeResult) -> new Route(percentage, routeResult.getSteps()))
                .collect(toList());
    }

    private RouteResult generateRoute(HasPosition startPosition, int maxLength, StepResultCollisionDetector collisionDetector, Sense sense) {
        List<StepResult> stepsResults = new ArrayList<>(maxLength);
        for (int stepIndex = 0; stepIndex < maxLength; stepIndex++) {
            StepResult stepResult = generateStep(
                    stepsResults.isEmpty() ? startPosition : stepsResults.get(stepsResults.size() - 1),
                    Direction.valuesAsSet(),
                    collisionDetector,
                    sense);
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

    private StepResult generateStep(HasPosition previousPosition, Set<Direction> allowedDirections, StepResultCollisionDetector routeCollisionDetector, Sense sense) {
        if (allowedDirections.isEmpty()) {
            return null;
        }
        List<DirectionChance> directionChances = directionChanceCalculator.calculate(previousPosition, allowedDirections, sense);
        DirectionChance directionChance = randomizer.chooseRandomChance(directionChances);
        StepResult stepResult = createStepResultForDirection(previousPosition, directionChance.getDirection());
        if (routeCollisionDetector.collides(stepResult, directionChance.getDirection()).isNoCollision()) {
            return stepResult;
        } else {
            return generateStep(previousPosition, copyWithoutDirection(allowedDirections, directionChance.getDirection()), routeCollisionDetector, sense);
        }
    }

    private StepResult createStepResultForDirection(HasPosition previousStepResult, Direction direction) {
        final int x;
        final int y;
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
        return createStep(StepNames.getBasedOnTwoConsecutiveDirections(previousStepResult.getDirection(), stepResult.getDirection()), previousStepResult);
    }

    private Step createEndingStep(StepResult stepResult) {
        return createStep(StepNames.getEndingForDirection(stepResult.getDirection()), stepResult);
    }

    private Step createStep(String stepName, StepResult stepResult) {
        return new Step(stepName, stepResult);
    }

}
