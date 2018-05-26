package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.level.meta.EnemyMarkerObject;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaData;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Level {

    private final LevelTiledMap tiledMap;
    private final LevelMetaData levelMetaData;
    private final Player player;
    private final List<Enemy> enemies;

    private int turnsLeft;

    public Level(LevelTiledMap tiledMap, LevelMetaData levelMetaData, int maxTurns) {
        this.tiledMap = tiledMap;
        this.levelMetaData = levelMetaData;
        this.turnsLeft = maxTurns;

        this.player = createPlayer();
        this.enemies = createEnemies();
    }

    private Player createPlayer() {
        return new Player(levelMetaData.getStartPosition());
    }

    private List<Enemy> createEnemies() {
        return levelMetaData.getEnemies().stream().map(this::createEnemy).collect(toList());
    }

    private Enemy createEnemy(EnemyMarkerObject enemyMarkerObject) {
        return new Enemy(enemyMarkerObject.getType(), enemyMarkerObject.getPredictability(), enemyMarkerObject);
    }

    public LevelTiledMap getTiledMap() {
        return tiledMap;
    }

    public LevelMetaData getLevelMetaData() {
        return levelMetaData;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasPlayerReachedEnd() {
        return levelMetaData.getEndPosition().hasSamePositionAs(player);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public void endTurn() {
        turnsLeft--;
    }

    public boolean noTurnsLeft() {
        return turnsLeft < 1;
    }
}
