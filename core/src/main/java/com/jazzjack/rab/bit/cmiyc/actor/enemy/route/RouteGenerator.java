package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.google.common.collect.Streams;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyConfig;
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

    public List<Route> generateRoutes(Enemy enemy) {
        EnemyConfig enemyConfig = enemy.getConfig();
        StepResultCollisionDetector routeCollisionDetector = new StepResultCollisionDetector(collisionDetector);
        routeCollisionDetector.addStepResult(enemy);
        Set<RouteResult> routes = IntStream.range(0, enemyConfig.getNumberOfRoutesToGenerate())
                .boxed()
                .map(i -> generateRoute(enemy, routeCollisionDetector))
                .filter(route -> !route.getSteps().isEmpty())
                .collect(toSet());
        filterOutRoutesThatEndOnOtherRoute(routes);
        List<Integer> percentages = routes.isEmpty() ? emptyList() : randomizer.randomPercentages(enemyConfig.getPredictability(), routes.size());
        return Streams
                .zip(percentages.stream(), routes.stream(), (percentage, routeResult) -> new Route(percentage, routeResult.getSteps()))
                .collect(toList());
    }

    private void filterOutRoutesThatEndOnOtherRoute(Set<RouteResult> routes) {
        routes.removeIf(routeResult -> routeEndsOnOtherRoute(routeResult, routes));
    }

    private boolean routeEndsOnOtherRoute(RouteResult route, Set<RouteResult> allRoutes) {
        return allRoutes.stream()
                .filter(otherRoute -> otherRoute != route)
                .flatMap(otherRoute -> otherRoute.getSteps().stream())
                .anyMatch(step -> step.hasSamePositionAs(route.getEndingStep()));
    }

    private RouteResult generateRoute(Enemy enemy, StepResultCollisionDetector collisionDetector) {
        EnemyConfig enemyConfig = enemy.getConfig();
        List<StepResult> stepsResults = new ArrayList<>(enemyConfig.getMaxRouteLength());
        for (int stepIndex = 0; stepIndex < enemyConfig.getMaxRouteLength(); stepIndex++) {
            StepResult stepResult = generateStep(
                    stepsResults.isEmpty() ? enemy : stepsResults.get(stepsResults.size() - 1),
                    Direction.valuesAsSet(),
                    collisionDetector,
                    enemyConfig.getSense());
            if (stepResult != null) {
                stepsResults.add(stepResult);
                collisionDetector.addStepResult(stepResult);
            } else {
                break;
            }
        }
        collisionDetector.clearStepResults();
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
