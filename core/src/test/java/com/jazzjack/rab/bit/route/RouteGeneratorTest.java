package com.jazzjack.rab.bit.route;

import com.badlogic.gdx.math.Rectangle;
import com.jazzjack.rab.bit.CollisionDetector;
import com.jazzjack.rab.bit.actor.Actor;
import com.jazzjack.rab.bit.actor.SimpleActor;
import com.jazzjack.rab.bit.common.Direction;
import com.jazzjack.rab.bit.common.Randomizer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
        when(randomizer.randomFromSet(Direction.valuesAsSet())).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(new Rectangle(2, 1, 1, 1))).thenReturn(true);
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.LEFT, Direction.UP, Direction.DOWN)))).thenReturn(Direction.UP);
        when(collisionDetector.collides(new Rectangle(1, 2, 1, 1))).thenReturn(true);
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.LEFT, Direction.DOWN)))).thenReturn(Direction.LEFT);
        when(collisionDetector.collides(new Rectangle(0, 1, 1, 1))).thenReturn(true);
        when(randomizer.randomFromSet(new HashSet<>(singletonList(Direction.DOWN)))).thenReturn(Direction.DOWN);
        when(collisionDetector.collides(new Rectangle(1, 0, 1, 1))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(new SimpleActor("start", 1, 1, 1), 1, 1);

        assertThat(routes).hasSize(1);
        Route foute = routes.iterator().next();
        assertThat(foute.getSteps()).hasSize(1);
        assertStep(foute.getSteps().iterator().next(), 1, 0, "ending-bottom");
    }

    @Test
    void expectNoCollidingStepsOrPreviousSteps() {
        when(randomizer.randomFromSet(Direction.valuesAsSet())).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(new Rectangle(1, 1, 1, 1))).thenReturn(false);
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.RIGHT, Direction.UP, Direction.DOWN)))).thenReturn(Direction.UP);
        when(collisionDetector.collides(new Rectangle(1, 2, 1, 1))).thenReturn(true);
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.RIGHT, Direction.DOWN)))).thenReturn(Direction.DOWN);
        when(collisionDetector.collides(new Rectangle(1, 0, 1, 1))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(new SimpleActor("start", 0, 1, 1), 1, 2);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(2);
        Iterator<Actor> steps = route.getSteps().iterator();
        assertStep(steps.next(), 1, 1, "corner-top-right");
        assertStep(steps.next(), 1, 0, "ending-bottom");
    }

    @Test
    void expectRouteEndedBecauseNoDirectionsAllowedAnymore() {
        when(randomizer.randomFromSet(Direction.valuesAsSet())).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(new Rectangle(1, 1, 1, 1))).thenReturn(false);
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.RIGHT, Direction.UP, Direction.DOWN)))).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(new Rectangle(2, 1, 1, 1))).thenReturn(true);
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.UP, Direction.DOWN)))).thenReturn(Direction.UP);
        when(collisionDetector.collides(new Rectangle(1, 2, 1, 1))).thenReturn(true);
        when(randomizer.randomFromSet(new HashSet<>(singletonList(Direction.DOWN)))).thenReturn(Direction.DOWN);
        when(collisionDetector.collides(new Rectangle(1, 0, 1, 1))).thenReturn(true);

        Set<Route> routes = routeGenerator.generateRoutes(new SimpleActor("start", 0, 1, 1), 1, 2);

        assertThat(routes).hasSize(1);
        Route foute = routes.iterator().next();
        assertThat(foute.getSteps()).hasSize(1);
        assertStep(foute.getSteps().iterator().next(), 1, 1, "ending-right");
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
        when(collisionDetector.collides(any(Rectangle.class))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(new SimpleActor("start", 0, 0, 1), 1, 18);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(18);
        Iterator<Actor> steps = route.getSteps().iterator();
        assertStep(steps.next(), 1, 0, "horizontal");
        assertStep(steps.next(), 2, 0, "corner-bottom-right");
        assertStep(steps.next(), 2, 1, "vertical");
        assertStep(steps.next(), 2, 2, "corner-top-right");
        assertStep(steps.next(), 1, 2, "corner-bottom-left");
        assertStep(steps.next(), 1, 3, "corner-top-left");
        assertStep(steps.next(), 2, 3, "horizontal");
        assertStep(steps.next(), 3, 3, "corner-top-right");
        assertStep(steps.next(), 3, 2, "corner-bottom-left");
        assertStep(steps.next(), 4, 2, "horizontal");
        assertStep(steps.next(), 5, 2, "horizontal");
        assertStep(steps.next(), 6, 2, "corner-top-right");
        assertStep(steps.next(), 6, 1, "vertical");
        assertStep(steps.next(), 6, 0, "corner-bottom-right");
        assertStep(steps.next(), 5, 0, "corner-bottom-left");
        assertStep(steps.next(), 5, 1, "corner-top-right");
        assertStep(steps.next(), 4, 1, "corner-top-left");
        assertStep(steps.next(), 4, 0, "ending-bottom");
    }

    @Test
    void expectEndingRight() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(any(Rectangle.class))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(new SimpleActor("start", 0, 0, 1), 1, 1);
        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 1, 0, "ending-right");
    }

    @Test
    void expectEndingLeft() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.LEFT);
        when(collisionDetector.collides(any(Rectangle.class))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(new SimpleActor("start", 1, 0, 1), 1, 1);
        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 0, 0, "ending-left");
    }

    @Test
    void expectEndingTop() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.UP);
        when(collisionDetector.collides(any(Rectangle.class))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(new SimpleActor("start", 0, 0, 1), 1, 1);
        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 0, 1, "ending-top");
    }

    private void assertStep(Actor step, int x, int y, String name) {
        assertThat(step.getX()).isEqualTo(x);
        assertThat(step.getY()).isEqualTo(y);
        assertThat(step.getName()).isEqualTo(name);
    }

}