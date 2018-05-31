package com.jazzjack.rab.bit.cmiyc.actor.enemy.route;

import com.jazzjack.rab.bit.cmiyc.actor.Actor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyContext;
import com.jazzjack.rab.bit.cmiyc.collision.Collidable;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionDetector;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.Predictability;
import com.jazzjack.rab.bit.cmiyc.shared.Randomizer;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.CORNER_BOTTOM_LEFT;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.CORNER_BOTTOM_RIGHT;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.CORNER_TOP_LEFT;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.CORNER_TOP_RIGHT;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.ENDING_BOTTOM;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.ENDING_LEFT;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.ENDING_RIGHT;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.ENDING_TOP;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.HORIZONTAL;
import static com.jazzjack.rab.bit.cmiyc.actor.enemy.route.StepNames.VERTICAL;
import static com.jazzjack.rab.bit.cmiyc.collision.CollidableMatcher.matchesCollidable;
import static com.jazzjack.rab.bit.cmiyc.collision.CollisionResult.collision;
import static com.jazzjack.rab.bit.cmiyc.collision.CollisionResult.noCollision;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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

    @BeforeEach
    void setup() {
        when(randomizer.randomPercentages(any(Predictability.class), anyInt())).thenAnswer((Answer<List<Integer>>) invocation -> range(0, invocation.getArgument(1)).boxed().collect(toList()));
    }

    @Test
    void expectNoCollidingSteps() {
        when(randomizer.randomFromSet(anySet())).thenReturn(
                Direction.RIGHT,
                Direction.UP,
                Direction.LEFT,
                Direction.DOWN);
        when(collisionDetector.collides(matchesCollidable(2, 1), eq(Direction.RIGHT))).thenReturn(collision(mock(Collidable.class), mock(Collidable.class), Direction.RIGHT));
        when(collisionDetector.collides(matchesCollidable(1, 2), eq(Direction.UP))).thenReturn(collision(mock(Collidable.class), mock(Collidable.class), Direction.UP));
        when(collisionDetector.collides(matchesCollidable(0, 1), eq(Direction.LEFT))).thenReturn(collision(mock(Collidable.class), mock(Collidable.class), Direction.LEFT));
        when(collisionDetector.collides(matchesCollidable(1, 0), eq(Direction.DOWN))).thenReturn(noCollision());

        List<Route> routes = routeGenerator.generateRoutes(enemy(1, 1), 1, 1);

        assertThat(routes).hasSize(1);
        Route foute = routes.iterator().next();
        assertThat(foute.getSteps()).hasSize(1);
        assertStep(foute.getSteps().iterator().next(), 1, 0, ENDING_BOTTOM);
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
        when(collisionDetector.collides(matchesCollidable(1, 1), eq(Direction.RIGHT))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(1, 0), eq(Direction.DOWN))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(2, 0), eq(Direction.RIGHT))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(2, 1), eq(Direction.UP))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(1, 1), eq(Direction.LEFT))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(2, 2), eq(Direction.UP))).thenReturn(noCollision());

        List<Route> routes = routeGenerator.generateRoutes(enemy(0, 1), 1, 5);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(5);
        Iterator<com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Step> steps = route.getSteps().iterator();
        assertStep(steps.next(), 1, 1, CORNER_TOP_RIGHT);
        assertStep(steps.next(), 1, 0, CORNER_BOTTOM_LEFT);
        assertStep(steps.next(), 2, 0, CORNER_BOTTOM_RIGHT);
        assertStep(steps.next(), 2, 1, VERTICAL);
        assertStep(steps.next(), 2, 2, ENDING_TOP);
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
        when(collisionDetector.collides(matchesCollidable(1, 0), eq(Direction.RIGHT))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(2, 0), eq(Direction.RIGHT))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(3, 0), eq(Direction.RIGHT))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(0, 1), eq(Direction.UP))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(1, 1), eq(Direction.RIGHT))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(1, 0), eq(Direction.DOWN))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(2, 1), eq(Direction.RIGHT))).thenReturn(noCollision());

        List<Route> routes = routeGenerator.generateRoutes(enemy(0, 0), 2, 3);

        assertThat(routes).hasSize(2);
        Iterator<Route> routeIterator = routes.iterator();
        Route route1 = routeIterator.next();
        assertThat(route1.getSteps()).hasSize(3);
        Iterator<com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Step> stepsRoute1 = route1.getSteps().iterator();
        assertStep(stepsRoute1.next(), 1, 0, HORIZONTAL);
        assertStep(stepsRoute1.next(), 2, 0, HORIZONTAL);
        assertStep(stepsRoute1.next(), 3, 0, ENDING_RIGHT);
        Route route2 = routeIterator.next();
        assertThat(route2.getSteps()).hasSize(3);
        Iterator<com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Step> stepsroute2 = route2.getSteps().iterator();
        assertStep(stepsroute2.next(), 0, 1, CORNER_TOP_LEFT);
        assertStep(stepsroute2.next(), 1, 1, HORIZONTAL);
        assertStep(stepsroute2.next(), 2, 1, ENDING_RIGHT);
    }

    @Test
    void expectRouteEndedBecauseNoDirectionsAllowedAnymore() {
        when(randomizer.randomFromSet(anySet())).thenReturn(
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.UP,
                Direction.DOWN);
        when(collisionDetector.collides(matchesCollidable(1, 1), eq(Direction.RIGHT))).thenReturn(noCollision());
        when(collisionDetector.collides(matchesCollidable(2, 1), eq(Direction.RIGHT))).thenReturn(collision(mock(Collidable.class), mock(Collidable.class), Direction.RIGHT));
        when(collisionDetector.collides(matchesCollidable(1, 2), eq(Direction.UP))).thenReturn(collision(mock(Collidable.class), mock(Collidable.class), Direction.UP));
        when(collisionDetector.collides(matchesCollidable(1, 0), eq(Direction.DOWN))).thenReturn(collision(mock(Collidable.class), mock(Collidable.class), Direction.DOWN));

        List<Route> routes = routeGenerator.generateRoutes(enemy(0, 1), 1, 2);

        assertThat(routes).hasSize(1);
        Route foute = routes.iterator().next();
        assertThat(foute.getSteps()).hasSize(1);
        assertStep(foute.getSteps().iterator().next(), 1, 1, ENDING_RIGHT);
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
        when(collisionDetector.collides(any(Collidable.class), any(Direction.class))).thenReturn(noCollision());

        List<Route> routes = routeGenerator.generateRoutes(enemy(0, 0), 1, 18);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(18);
        Iterator<com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Step> steps = route.getSteps().iterator();
        assertStep(steps.next(), 1, 0, HORIZONTAL);
        assertStep(steps.next(), 2, 0, CORNER_BOTTOM_RIGHT);
        assertStep(steps.next(), 2, 1, VERTICAL);
        assertStep(steps.next(), 2, 2, CORNER_TOP_RIGHT);
        assertStep(steps.next(), 1, 2, CORNER_BOTTOM_LEFT);
        assertStep(steps.next(), 1, 3, CORNER_TOP_LEFT);
        assertStep(steps.next(), 2, 3, HORIZONTAL);
        assertStep(steps.next(), 3, 3, CORNER_TOP_RIGHT);
        assertStep(steps.next(), 3, 2, CORNER_BOTTOM_LEFT);
        assertStep(steps.next(), 4, 2, HORIZONTAL);
        assertStep(steps.next(), 5, 2, HORIZONTAL);
        assertStep(steps.next(), 6, 2, CORNER_TOP_RIGHT);
        assertStep(steps.next(), 6, 1, VERTICAL);
        assertStep(steps.next(), 6, 0, CORNER_BOTTOM_RIGHT);
        assertStep(steps.next(), 5, 0, CORNER_BOTTOM_LEFT);
        assertStep(steps.next(), 5, 1, CORNER_TOP_RIGHT);
        assertStep(steps.next(), 4, 1, CORNER_TOP_LEFT);
        assertStep(steps.next(), 4, 0, ENDING_BOTTOM);
    }

    @Test
    void expectEndingRight() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(any(Collidable.class), any(Direction.class))).thenReturn(noCollision());

        List<Route> routes = routeGenerator.generateRoutes(enemy(0, 0), 1, 1);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 1, 0, ENDING_RIGHT);
    }

    @Test
    void expectEndingLeft() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.LEFT);
        when(collisionDetector.collides(any(Collidable.class), any(Direction.class))).thenReturn(noCollision());

        List<Route> routes = routeGenerator.generateRoutes(enemy(1, 0), 1, 1);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 0, 0, ENDING_LEFT);
    }

    @Test
    void expectEndingTop() {
        when(randomizer.randomFromSet(anySet())).thenReturn(Direction.UP);
        when(collisionDetector.collides(any(Collidable.class), any(Direction.class))).thenReturn(noCollision());

        List<Route> routes = routeGenerator.generateRoutes(enemy(0, 0), 1, 1);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(1);
        assertStep(route.getSteps().iterator().next(), 0, 1, ENDING_TOP);
    }

    @Test
    void expectRandomizerOnlyCalledWithAllowedDirections() {
        when(randomizer.randomFromSet(Direction.valuesAsSet())).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(matchesCollidable(1, 0), eq(Direction.RIGHT))).thenReturn(noCollision());
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.RIGHT, Direction.UP, Direction.DOWN)))).thenReturn(Direction.UP);
        when(collisionDetector.collides(matchesCollidable(1, 1), eq(Direction.UP))).thenReturn(collision(mock(Collidable.class), mock(Collidable.class), Direction.UP));
        when(randomizer.randomFromSet(new HashSet<>(asList(Direction.RIGHT, Direction.DOWN)))).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(matchesCollidable(2, 0), eq(Direction.RIGHT))).thenReturn(noCollision());

        List<Route> routes = routeGenerator.generateRoutes(enemy(0, 0), 1, 2);

        assertThat(routes).hasSize(1);
        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(2);
        Iterator<Step> steps = route.getSteps().iterator();
        assertStep(steps.next(), 1, 0, HORIZONTAL);
        assertStep(steps.next(), 2, 0, ENDING_RIGHT);
    }

    @Test
    void expectEmptyRoutesToBeFilteredOut() {
        when(randomizer.randomFromSet(anySet())).thenReturn(
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT);
        when(collisionDetector.collides(any(Collidable.class), any(Direction.class))).thenReturn(
                noCollision(),
                collision(mock(Collidable.class), mock(Collidable.class), Direction.RIGHT),
                collision(mock(Collidable.class), mock(Collidable.class), Direction.UP),
                collision(mock(Collidable.class), mock(Collidable.class), Direction.DOWN));

        List<Route> routes = routeGenerator.generateRoutes(enemy(0, 0), 2, 1);

        assertThat(routes).hasSize(1);
    }

    private void assertStep(Actor step, int x, int y, String name) {
        assertThat(step.getX()).isEqualTo(x);
        assertThat(step.getY()).isEqualTo(y);
        assertThat(step.getName()).isEqualTo(name);
    }

    private Enemy enemy(int startX, int startY) {
        return new Enemy(mock(EnemyContext.class), "enemy", Predictability.HIGH, new Position(startX, startY));
    }

}