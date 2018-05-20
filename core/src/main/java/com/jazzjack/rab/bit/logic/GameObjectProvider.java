package com.jazzjack.rab.bit.logic;

import com.jazzjack.rab.bit.actor.enemy.Enemy;
import com.jazzjack.rab.bit.actor.player.Player;
import com.jazzjack.rab.bit.level.Level;

import java.util.List;
import java.util.Optional;

public interface GameObjectProvider {

    Optional<Player> getPlayer();

    List<Enemy> getEnemies();

    Optional<Level> getMap();
}
