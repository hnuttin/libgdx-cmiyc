package com.jazzjack.rab.bit.game;

import com.jazzjack.rab.bit.Map;
import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.player.Player;

import java.util.List;
import java.util.Optional;

public interface GameObjectProvider {

    Optional<Player> getPlayer();

    List<Enemy> getEnemies();

    Optional<Map> getMap();
}
