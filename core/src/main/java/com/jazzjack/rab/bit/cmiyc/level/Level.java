package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemies;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyContext;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyDestroyedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyRouteCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.collision.LevelCollisionDetectorWithCollidables;
import com.jazzjack.rab.bit.cmiyc.game.EventSubscriber;
import com.jazzjack.rab.bit.cmiyc.level.meta.EnemyMarkerObject;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public class Level implements EventSubscriber<EnemyDestroyedEvent> {

    private final LevelContext context;
    private final LevelTiledMap tiledMap;
    private final LevelMetaData levelMetaData;

    private final LevelCollisionDetectorWithCollidables playerMovementCollisionDetector;
    private final LevelCollisionDetectorWithCollidables enemyMovementCollisionDetector;
    private final EnemyRouteCollisionDetector enemyRouteCollisionDetector;
    private final EnemyContext enemyContext;

    private final Player player;
    private final Enemies enemies;

    private int turnsLeft;

    public Level(LevelContext context, LevelTiledMap tiledMap, LevelMetaData levelMetaData, int maxTurns) {
        this.context = context;
        this.tiledMap = tiledMap;
        this.levelMetaData = levelMetaData;

        this.turnsLeft = maxTurns;

        this.playerMovementCollisionDetector = new LevelCollisionDetectorWithCollidables(tiledMap);
        this.enemyMovementCollisionDetector = new LevelCollisionDetectorWithCollidables(tiledMap);
        this.enemyRouteCollisionDetector = new EnemyRouteCollisionDetector(playerMovementCollisionDetector);

        this.enemyContext = new EnemyContext(
                enemyMovementCollisionDetector,
                context.getCollisionResolver(),
                context.getRandomizer(),
                context.getAnimationRegister(),
                new RouteGenerator(enemyRouteCollisionDetector, context.getRandomizer()));

        this.player = createPlayer();
        this.enemies = createEnemies();

        initializeCollisionDetectors();
    }

    private Player createPlayer() {
        ActorContext actorContext = new ActorContext(playerMovementCollisionDetector, this.context.getCollisionResolver());
        return new Player(actorContext, levelMetaData.getStartPosition());
    }

    private Enemies createEnemies() {
        return new Enemies(levelMetaData.getEnemies().stream().map(this::createEnemy).collect(toList()));
    }

    private Enemy createEnemy(EnemyMarkerObject enemyMarkerObject) {
        return new Enemy(enemyContext, enemyMarkerObject.getType(), enemyMarkerObject.getPredictability(), enemyMarkerObject);
    }

    private void initializeCollisionDetectors() {
        this.playerMovementCollisionDetector.addCollidable(enemies.get());
        this.enemyMovementCollisionDetector.addCollidable(player);
        this.enemyMovementCollisionDetector.addCollidable(enemies.get());
        this.enemyRouteCollisionDetector.addEnemies(enemies.get());
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

    @Override
    public void handleEvent(EnemyDestroyedEvent event) {
        playerMovementCollisionDetector.removeCollidable(event.getEnemy());
        enemyMovementCollisionDetector.removeCollidable(event.getEnemy());
        enemyRouteCollisionDetector.removeEnemy(event.getEnemy());
        enemies.removeEnemy(event.getEnemy());
    }
}
