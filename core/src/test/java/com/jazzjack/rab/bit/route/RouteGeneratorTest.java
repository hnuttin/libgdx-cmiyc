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

import java.util.Iterator;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
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
    void expectOneRouteWithTwoLength() {
        when(randomizer.randomFromList(asList(Direction.values()))).thenReturn(Direction.RIGHT);
        when(collisionDetector.collides(new Rectangle(1, 1, 1, 1))).thenReturn(false);
        when(randomizer.randomFromList(asList(Direction.RIGHT, Direction.UP, Direction.DOWN))).thenReturn(Direction.UP);
        when(collisionDetector.collides(new Rectangle(1, 2, 1, 1))).thenReturn(true);
        when(randomizer.randomFromList(asList(Direction.RIGHT, Direction.DOWN))).thenReturn(Direction.DOWN);
        when(collisionDetector.collides(new Rectangle(1, 0, 1, 1))).thenReturn(false);

        Set<Route> routes = routeGenerator.generateRoutes(new SimpleActor("start", 0, 1, 1), 1, 2);

        assertThat(routes).hasSize(1);

        Route route = routes.iterator().next();
        assertThat(route.getSteps()).hasSize(2);

        Iterator<Actor> steps = route.getSteps().iterator();

        Actor stepOne = steps.next();
        assertThat(stepOne.getX()).isEqualTo(1);
        assertThat(stepOne.getY()).isEqualTo(1);
        assertThat(stepOne.getName()).isEqualTo("horizontal");

        Actor stepTwoOne = steps.next();
        assertThat(stepTwoOne.getX()).isEqualTo(1);
        assertThat(stepTwoOne.getY()).isEqualTo(0);
        assertThat(stepTwoOne.getName()).isEqualTo("vertical");
    }

}