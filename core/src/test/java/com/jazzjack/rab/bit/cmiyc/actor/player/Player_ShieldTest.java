package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.jazzjack.rab.bit.cmiyc.actor.player.PlayerProfile.playerProfileBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Player_ShieldTest {

    private PlayerProfile playerProfile;
    private Player player;

    @BeforeEach
    void setup() {
        playerProfile = playerProfileBuilder().withHp(2).withActionPointsPerTurn(2).build();
        player = new Player(mock(ActorContext.class), mock(HasPosition.class), playerProfile);
    }

    @Test
    void expectShieldNotActiveWhenPlayerHasNoShield() {
        player.useItem(Item.SHIELD);

        assertThat(player.isShieldActive()).isFalse();
        assertThat(player.getActionPointsConsumed()).isEqualTo(0);
    }

    @Test
    void expectShieldActiveWhenPlayerHasShield() {
        playerProfile.addItem(Item.SHIELD);
        player.useItem(Item.SHIELD);

        assertThat(player.isShieldActive()).isTrue();
        assertThat(player.getActionPointsConsumed()).isEqualTo(1);
    }

    @Test
    void expectDamageFromEnemyBlockedWhenShieldActive() {
        playerProfile.addItem(Item.SHIELD);
        player.useItem(Item.SHIELD);
        assertThat(player.isShieldActive()).isTrue();

        Enemy enemy = mock(Enemy.class);
        when(enemy.getPower()).thenReturn(1);
        player.damageFromEnemy(enemy);
        assertThat(player.isShieldActive()).isFalse();
        assertThat(player.getHp()).isEqualTo(2);
    }

    @Test
    void expectDamageFromEnemyNotBlockedWhenShieldNotActive() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getPower()).thenReturn(1);
        player.damageFromEnemy(enemy);
        assertThat(player.getHp()).isEqualTo(1);
    }

}