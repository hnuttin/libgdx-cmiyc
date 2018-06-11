package com.jazzjack.rab.bit.cmiyc.item;

import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.level.meta.MarkerObject;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import org.junit.jupiter.api.Test;

import static com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile.playerProfileBuilder;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemsTest {

    @Test
    void expectItemToBePickedUpByPlayerWhenOnSamePosition() {
        MarkerObject markerObject = mock(MarkerObject.class);
        when(markerObject.getX()).thenReturn(0);
        when(markerObject.getY()).thenReturn(0);
        Player player = new Player(mock(ActorContext.class), new Position(0, 0), playerProfileBuilder().withHp(1).build());

        Items items = new Items(singletonList(markerObject));
        items.playerMoved(new PlayerMovedEvent(player));

        assertThat(player.getHp()).isEqualTo(2);
        assertThat(items.getItems()).isEmpty();
    }

    @Test
    void expectItemToBePickedUpOnlyOnce() {
        MarkerObject markerObject = mock(MarkerObject.class);
        when(markerObject.getX()).thenReturn(0);
        when(markerObject.getY()).thenReturn(0);
        Player player = new Player(mock(ActorContext.class), new Position(0, 0), playerProfileBuilder().withHp(1).build());

        Items items = new Items(singletonList(markerObject));
        items.playerMoved(new PlayerMovedEvent(player));
        items.playerMoved(new PlayerMovedEvent(player));

        assertThat(player.getHp()).isEqualTo(2);
        assertThat(items.getItems()).isEmpty();
    }

}