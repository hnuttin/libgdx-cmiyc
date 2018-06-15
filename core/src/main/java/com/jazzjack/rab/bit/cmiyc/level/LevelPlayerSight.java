package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Route;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhase;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhaseEvent;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhaseEventSubscriber;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.registerSubscriber;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class LevelPlayerSight implements PlayerMovedSubscriber, GamePhaseEventSubscriber, EnemyMovedSubscriber {

    private final LevelTiledMap levelTiledMap;
    private final Player player;
    private final Supplier<List<Enemy>> enemySupplier;

    private final Map<Enemy, Boolean> enemiesInSight;

    LevelPlayerSight(LevelTiledMap levelTiledMap, Player player, Supplier<List<Enemy>> enemySupplier) {
        this.levelTiledMap = levelTiledMap;
        this.player = player;
        this.enemySupplier = enemySupplier;
        this.enemiesInSight = new HashMap<>();
        markTiles();
        registerSubscriber(this);
    }

    @Override
    public void playerMoved(PlayerMovedEvent event) {
        markTiles();
    }

    @Override
    public void enemyMoved(EnemyMovedEvent event) {
        markTiles();
    }

    @Override
    public void newGamePhase(GamePhaseEvent event) {
        if (event.getGamePhase() == GamePhase.PLAYER_TURN) {
            resetEnemiesInSight();
        }
    }

    public boolean isTileInSight(int x, int y) {
        return levelTiledMap.getLevelCell(x, y).isInSight();
    }

    public boolean isTileVisited(int x, int y) {
        return levelTiledMap.getLevelCell(x, y).isVisited();
    }

    public boolean isEnemyInSight(Enemy enemy) {
        Boolean inSight = enemiesInSight.get(enemy);
        return inSight != null && inSight;
    }

    private void resetEnemiesInSight() {
        enemiesInSight.clear();
        markTiles();
    }

    private void markTiles() {
        markAllTilesNotInSight();
        for (int cellX = startSightX(player); cellX <= endSightX(player); cellX++) {
            for (int cellY = startSightY(player); cellY <= endSightY(player); cellY++) {
                LevelCell levelCell = levelTiledMap.getLevelCell(cellX, cellY);
                levelCell.markVisited();
                levelCell.markInSight();
                for (Enemy enemy : enemySupplier.get()) {
                    if (enemy.hasSamePositionAs(new Position(cellX, cellY))) {
                        enemy.trigger();
                        markEnemyInSight(enemy);
                        markEnemyRoutesVisited(enemy);
                    }
                }
            }
        }
    }

    private void markEnemyRoutesVisited(Enemy enemy) {
        enemy.getRoutes().forEach(this::markEnemyRouteVisited);
    }

    private void markEnemyRouteVisited(Route route) {
        route.getSteps().forEach(step -> levelTiledMap.getLevelCell(step).markVisited());
    }

    private void markEnemyInSight(Enemy enemy) {
        enemiesInSight.put(enemy, true);
    }

    private void markAllTilesNotInSight() {
        for (int cellX = 0; cellX < levelTiledMap.getWidth(); cellX++) {
            for (int cellY = 0; cellY < levelTiledMap.getHeight(); cellY++) {
                levelTiledMap.getLevelCell(cellX, cellY).markNotInSight();
            }
        }
    }

    private int startSightX(Player player) {
        return max(0, player.getX() - player.getSight());
    }

    private int endSightX(Player player) {
        return min(levelTiledMap.getWidth() - 1, player.getX() + player.getSight());
    }

    private int startSightY(Player player) {
        return max(0, player.getY() - player.getSight());
    }

    private int endSightY(Player player) {
        return min(levelTiledMap.getHeight() - 1, player.getY() + player.getSight());
    }
}
