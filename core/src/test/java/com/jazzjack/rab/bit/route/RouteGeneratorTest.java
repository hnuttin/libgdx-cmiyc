package com.jazzjack.rab.bit.route;

import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.collision.Collidable;
import com.jazzjack.rab.bit.collision.CollisionDetector;
import com.jazzjack.rab.bit.common.Direction;
import com.jazzjack.rab.bit.common.Randomizer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.security.UnresolvedPermission;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.jazzjack.rab.bit.collision.CollidableMatcher.matchesCollidable;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RouteGeneratorTest {

    @InjectMocks
    private RouteGenerator routeGenerator;
    @Mock
    private CollisionDetector collisionDetector;
    @Mock
    private Randomizer randomizer;

    @Test
    void expectNoCollidingSteps() {
        when(randomizer.randomFromSet(anySet())).thenReturn(
                Direction.RIGHT,
                Direction.UP,
                Direction.LEFT,
                Direction.DOWN);
        when(collisionDetector.collides(matchesCollidable(2, 1, 1))).thenReturn(true);
        when(collisionDetector.collides(matchesCollidable(1, 2, 1))).thenReturn(true);
        when(collisionDetector.collides(matchesCollidable(0, 1, 1))).thenReturn(true);
        when(collisionDetector.collides(matchesCollidable(1, 0, 1))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(1, 1), 1, 1);

        assertThat(routes).hasSize(1);
        Route foute = routes.iterator().next();
        assertThat(foute.getSteps()).hasSize(1);
        assertStep(foute.getSteps().iterator().next(), 1, 0, StepNames.ENDING_BOTTOM);
    }

    @Test
    void expectNoPreviousSteps() {
        when(randomizer.randomFromSet(anySet())).thenReturn(
                Direction.RIGHT,
                Direction.DOWN,
                Direction.RIGHT,
                Direction.UP,
                Direction.LEFT,
                Direction.UP);
        when(collisionDetector.collides(matchesCollidable(1, 1, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(1, 0, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(2, 0, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(2, 1, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(1, 1, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(2, 2, 1))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(0, 1), 1, 5);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(5);
        Iterator<Actor> steps = route.getSteps().iterator();
        assertStep(steps.next(), 1, 1, StepNames.CORNER_TOP_RIGHT);
        assertStep(steps.next(), 1, 0, StepNames.CORNER_BOTTOM_LEFT);
        assertStep(steps.next(), 2, 0, StepNames.CORNER_BOTTOM_RIGHT);
        assertStep(steps.next(), 2, 1, StepNames.VERTICAL);
        assertStep(steps.next(), 2, 2, StepNames.ENDING_TOP);
    }

    @Test
    void expectNoPreviousRouteSteps() {
        when(randomizer.randomFromSet(anySet())).thenReturn(
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.UP,
                Direction.RIGHT,
                Direction.DOWN,
                Direction.RIGHT);
        when(collisionDetector.collides(matchesCollidable(1, 0, 1))).thenReturn(false, false);
        when(collisionDetector.collides(matchesCollidable(2, 0, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(3, 0, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(0, 1, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(1, 1, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(2, 1, 1))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(0, 0), 2, 3);

        assertThat(routes).hasSize(2);
        Iterator<Route> routeIterator = routes.iterator();
        Route route1 = routeIterator.next();
        assertThat(route1.getSteps()).hasSize(3);
        Iterator<Actor> stepsRoute1 = route1.getSteps().iterator();
        assertStep(stepsRoute1.next(), 1, 0, StepNames.HORIZONTAL);
        assertStep(stepsRoute1.next(), 2, 0, StepNames.HORIZONTAL);
        assertStep(stepsRoute1.next(), 3, 0, StepNames.ENDING_RIGHT);
        Route route2 = routeIterator.next();
        assertThat(route2.getSteps()).hasSize(3);
        Iterator<Actor> stepsroute2 = route2.getSteps().iterator();
        assertStep(stepsroute2.next(), 0, 1, StepNames.CORNER_TOP_LEFT);
        assertStep(stepsroute2.next(), 1, 1, StepNames.HORIZONTAL);
        assertStep(stepsroute2.next(), 2, 1, StepNames.ENDING_RIGHT);
    }

    @Test
    void expectRouteEndedBecauseNoDirectionsAllowedAnymore() {
        when(randomizer.randomFromSet(anySet())).thenReturn(
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.UP,
                Direction.DOWN);
        when(collisionDetector.collides(matchesCollidable(1, 1, 1))).thenReturn(false);
        when(collisionDetector.collides(matchesCollidable(2, 1, 1))).thenReturn(true);
        when(collisionDetector.collides(matchesCollidable(1, 2, 1))).thenReturn(true);
        when(collisionDetector.collides(matchesCollidable(1, 0, 1))).thenReturn(true);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(0, 1), 1, 2);

        assertThat(routes).hasSize(1);
        Route foute = routes.iterator().next();
        assertThat(foute.getSteps()).hasSize(1);
        assertStep(foute.getSteps().iterator().next(), 1, 1, StepNames.ENDING_RIGHT);
    }

    @Test
    void expectAllPossibleStepNames() {
        when(randomizer.randomFromSet(anySet())).thenReturn(
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.UP,
                Direction.UP,
                Direction.LEFT,
                Direction.UP,
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.DOWN,
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.DOWN,
                Direction.DOWN,
                Direction.LEFT,
                Direction.UP,
                Direction.LEFT,
                Direction.DOWN,
                Direction.DOWN);
        when(collisionDetector.collides(any(Collidable.class))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(0, 0), 1, 18);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(18);
        Iterator<Actor> steps = route.getSteps().iterator();
        assertStep(steps.next(), 1, 0, StepNames.HORIZONTAL);
        assertStep(steps.next(), 2, 0, StepNames.CORNER_BOTTOM_RIGHT);
        assertStep(steps.next(), 2, 1, StepNames.VERTICAL);
        assertStep(steps.next(), 2, 2, StepNames.CORNER_TOP_RIGHT);
        assertStep(steps.next(), 1, 2, StepNames.CORNER_BOTTOM_LEFT);
        assertStep(steps.next(), 1, 3, StepNames.CORNER_TOP_LEFT);
        assertStep(steps.next(), 2, 3, StepNames.HORIZONTAL);
        assertStep(steps.next(), 3, 3, StepNames.CORNER_TOP_RIGHT);
        assertStep(steps.next(), 3, 2, StepNames.CORNER_BOTTOM_LEFT);
        assertStep(steps.next(), 4, 2, StepNames.HORIZONTAL);
        assertStep(steps.next(), 5, 2, StepNames.HORIZONTAL);
        assertStep(steps.next(), 6, 2, StepNames.CORNER_TOP_RIGHT);
        assertStep(steps.next(), 6, 1, StepNames.VERTICAL);
        assertStep(steps.next(), 6, 0, StepNames.CORNER_BOTTOM_RIGHT);
        assertStep(steps.next(), 5, 0, StepNames.CORNER_BOTTOM_LEFT);
        assertStep(steps.next(), 5, 1, StepNames.CORNER_TOP_RIGHT);
        assertStep(steps.next(), 4, 1, StepNames.CORNER_TOP_LEFT);
        assertStep(steps.next(), 4, 0, StepNames.ENDING_BOTTOM);
    }

    @Test
    void expectEndingRight() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(any(Collidable.class))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(0, 0), 1, 1);
        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 1, 0, StepNames.ENDING_RIGHT);
    }

    @Test
    void expectEndingLeft() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.LEFT);
        when(collisionDetector.collides(any(Collidable.class))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(1, 0), 1, 1);
        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 0, 0, StepNames.ENDING_LEFT);
    }

    @Test
    void expectEndingTop() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.UP);
        when(collisionDetector.collides(any(Collidable.class))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(0, 0), 1, 1);
        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 0, 1, StepNames.ENDING_TOP);
    }

    @Test
    void expectRandomizerOnlyCalledWithAllowedDirections() {
        when(randomizer.randomFromSet(Direction.valuesAsSet())).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(matchesCollidable(1, 0, 1))).thenReturn(false);
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.RIGHT, Direction.UP, Direction.DOWN)))).thenReturn(Direction.UP);
        when(collisionDetector.collides(matchesCollidable(1, 1, 1))).thenReturn(true);
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.RIGHT, Direction.DOWN)))).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(matchesCollidable(2, 0, 1))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(startActor(0, 0), 1, 2);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(2);
        Iterator<Actor> steps = route.getSteps().iterator();
        assertStep(steps.next(), 1, 0, StepNames.HORIZONTAL);
        assertStep(steps.next(), 2, 0, StepNames.ENDING_RIGHT);
    }

    private void assertStep(Actor step, int x, int y, String name) {
        assertThat(step.getX()).isEqualTo(x);
        assertThat(step.getY()).isEqualTo(y);
        assertThat(step.getName()).isEqualTo(name);
    }

    private SimpleActor startActor(int startX, int startY) {
        return new SimpleActor("start", startX, startY, 1);
    }

}