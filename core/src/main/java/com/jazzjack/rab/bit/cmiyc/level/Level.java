package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemies;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyAddedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyContext;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyMovementCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.DirectionChanceCalculator;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.EnemyRouteCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.RouteGenerator;
import com.jazzjack.rab.bit.cmiyc.actor.player.ActorContext;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerCollisionDetector;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;
import com.jazzjack.rab.bit.cmiyc.item.Items;
import com.jazzjack.rab.bit.cmiyc.level.meta.EnemyMarkerObject;
import com.jazzjack.rab.bit.cmiyc.level.meta.LevelMetaData;
import com.jazzjack.rab.bit.cmiyc.level.meta.MarkerObject;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public class Level {

    private final LevelContext context;
    private final LevelTiledMap levelTiledMap;
    private final LevelMetaData levelMetaData;

    private final Player player;
    private final Enemies enemies;
    private final Items items;

    private final LevelSight levelSight;

    private int turnsLeft;

    public Level(LevelContext context, LevelTiledMap levelTiledMap, LevelMetaData levelMetaData, int maxTurns) {
        this.context = context;
        this.levelTiledMap = levelTiledMap;
        this.levelMetaData = levelMetaData;

        LevelCollisionDetector levelCollisionDetector = new LevelCollisionDetector(levelTiledMap.getMapLayer());

        this.player = createPlayer(levelCollisionDetector);
        this.enemies = createEnemies(createEnemyContext(context, levelCollisionDetector));
        this.items = new Items(levelMetaData.getItems());

        this.turnsLeft = maxTurns;

        levelSight = new LevelSight(levelTiledMap, this.player, enemies);
    }

    private Player createPlayer(LevelCollisionDetector levelCollisionDetector) {
        ActorContext actorContext = new ActorContext(new PlayerCollisionDetector(levelCollisionDetector), this.context.getCollisionResolver());
        return new Player(actorContext, levelMetaData.getStartPosition(), 2);
    }

    private EnemyContext createEnemyContext(LevelContext context, LevelCollisionDetector levelCollisionDetector) {
        return new EnemyContext(
                new EnemyMovementCollisionDetector(player, levelCollisionDetector),
                context.getCollisionResolver(),
                context.getRandomizer(),
                context.getAnimationRegister(),
                new RouteGenerator(new EnemyRouteCollisionDetector(levelCollisionDetector), new DirectionChanceCalculator(player), context.getRandomizer()));
    }

    private Enemies createEnemies(EnemyContext enemyContext) {
        return new Enemies(levelMetaData.getEnemies().stream()
                .map(enemyMarkerObject -> createEnemy(enemyContext, enemyMarkerObject))
                .collect(toList()));
    }

    private Enemy createEnemy(EnemyContext enemyContext, EnemyMarkerObject enemyMarkerObject) {
        Enemy enemy = new Enemy(enemyContext, enemyMarkerObject.getType(), enemyMarkerObject.getPredictability(), enemyMarkerObject.getSense(), enemyMarkerObject);
        GameEventBus.publishEvent(new EnemyAddedEvent(enemy));
        return enemy;
    }

    public LevelTiledMap getLevelTiledMap() {
        return levelTiledMap;
    }

    public HasPosition getLevelEndPosition() {
        return levelMetaData.getEndPosition();
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

    public List<MarkerObject> getItems() {
        return items.getItems();
    }

    public LevelSight getLevelSight() {
        return levelSight;
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
