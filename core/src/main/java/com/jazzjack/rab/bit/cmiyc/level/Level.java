package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemies;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyAddedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyContext;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyMovementCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.EnemyRouteCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.level.meta.EnemyMarkerObject;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public class Level {

    private final LevelContext context;
    private final LevelTiledMap tiledMap;
    private final LevelMetaData levelMetaData;

    private final Player player;
    private final Enemies enemies;

    private int turnsLeft;

    public Level(LevelContext context, LevelTiledMap tiledMap, LevelMetaData levelMetaData, int maxTurns) {
        this.context = context;
        this.tiledMap = tiledMap;
        this.levelMetaData = levelMetaData;

        LevelCollisionDetector levelCollisionDetector = new LevelCollisionDetector(tiledMap);
        this.player = createPlayer(levelCollisionDetector);
        this.enemies = createEnemies(createEnemyContext(context, levelCollisionDetector));

        this.turnsLeft = maxTurns;
    }

    private Player createPlayer(LevelCollisionDetector levelCollisionDetector) {
        ActorContext actorContext = new ActorContext(new PlayerCollisionDetector(levelCollisionDetector), this.context.getCollisionResolver());
        return new Player(actorContext, levelMetaData.getStartPosition());
    }

    private EnemyContext createEnemyContext(LevelContext context, LevelCollisionDetector levelCollisionDetector) {
        return new EnemyContext(
                new EnemyMovementCollisionDetector(this.player, levelCollisionDetector),
                context.getCollisionResolver(),
                context.getRandomizer(),
                context.getAnimationRegister(),
                new RouteGenerator(new EnemyRouteCollisionDetector(levelCollisionDetector), context.getRandomizer()));
    }

    private Enemies createEnemies(EnemyContext enemyContext) {
        return new Enemies(levelMetaData.getEnemies().stream()
                .map(enemyMarkerObject -> createEnemy(enemyContext, enemyMarkerObject))
                .collect(toList()));
    }

    private Enemy createEnemy(EnemyContext enemyContext, EnemyMarkerObject enemyMarkerObject) {
        Enemy enemy = new Enemy(enemyContext, enemyMarkerObject.getType(), enemyMarkerObject.getPredictability(), enemyMarkerObject);
        GameEventBus.publishEvent(new EnemyAddedEvent(enemy));
        return enemy;
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
        return enemies.get();
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

    public CompletableFuture<Void> moveAllEnemies() {
        return enemies.moveAllEnemies();
    }

    public void generateEnemyRoutes() {
        enemies.generateRoutes();
    }

}
