package com.jazzjack.rab.bit.cmiyc.item;

import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile;
import com.jazzjack.rab.bit.cmiyc.level.meta.ItemMarkerObject;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import org.junit.jupiter.api.Test;

import static com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile.playerProfileBuilder;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemsTest {

    @Test
    void expectHpToBePickedUpByPlayer() {
        ItemMarkerObject item = mock(ItemMarkerObject.class);
        when(item.getX()).thenReturn(0);
        when(item.getY()).thenReturn(0);
        when(item.getItem()).thenReturn(Item.HP);

        Player player = new Player(mock(ActorContext.class), new Position(0, 0), playerProfileBuilder().withHp(1).build());

        Items items = new Items(singletonList(item));

        items.playerMoved(new PlayerMovedEvent(player));
        assertThat(player.getHp()).isEqualTo(2);
        assertThat(items.getItems()).isEmpty();

        items.playerMoved(new PlayerMovedEvent(player));
        assertThat(player.getHp()).isEqualTo(2);
    }

    @Test
    void expectShieldToBePickedUpByPlayer() {
        ItemMarkerObject item = mock(ItemMarkerObject.class);
        when(item.getX()).thenReturn(0);
        when(item.getY()).thenReturn(0);
        when(item.getItem()).thenReturn(Item.SHIELD);

        PlayerProfile playerProfile = playerProfileBuilder().build();
        Player player = new Player(mock(ActorContext.class), new Position(0, 0), playerProfile);

        Items items = new Items(singletonList(item));

        items.playerMoved(new PlayerMovedEvent(player));
        assertThat(playerProfile.getItems()).containsExactly(Item.SHIELD);
        assertThat(items.getItems()).isEmpty();

        items.playerMoved(new PlayerMovedEvent(player));
        assertThat(playerProfile.getItems()).containsExactly(Item.SHIELD);
    }

}