package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.Gdx;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.EnemyMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.NewEnemyRouteSubscriber;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.NewEnemyRoutesEvent;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.Route;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.route.step.Step;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhase;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhaseEvent;
import com.jazzjack.rab.bit.cmiyc.logic.GamePhaseEventSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.registerSubscriber;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.singletonList;

public class LevelPlayerSight implements PlayerMovedSubscriber, GamePhaseEventSubscriber, EnemyMovedSubscriber, NewEnemyRouteSubscriber {

    private final LevelTiledMap levelTiledMap;
    private final Player player;
    private final Supplier<List<Enemy>> enemySupplier;

    private final Map<Enemy, Boolean> enemiesInSight;

    LevelPlayerSight(LevelTiledMap levelTiledMap, Player player, Supplier<List<Enemy>> enemySupplier) {
        this.levelTiledMap = levelTiledMap;
        this.player = player;
        this.enemySupplier = enemySupplier;
        this.enemiesInSight = new HashMap<>();
        markAllLevelCells();
        registerSubscriber(this);
    }

    @Override
    public void playerMoved(PlayerMovedEvent event) {
        markAllLevelCells();
    }

    @Override
    public void enemyMoved(EnemyMovedEvent event) {
        markAllLevelCellsForEnemies(singletonList(event.getEnemy()));
    }

    @Override
    public void newEnemyRoutes(NewEnemyRoutesEvent event) {
        markAllLevelCellsForEnemies(singletonList(event.getEnemy()));
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

    private void markAllLevelCells() {
        markAllTilesNotInSight();
        forEachLevelCellInSight(cell -> {
            cell.markVisited();
            cell.markInSight();
            for (Enemy enemy : enemySupplier.get()) {
                markEnemy(cell, enemy);
            }
        });
    }

    private void resetEnemiesInSight() {
        enemiesInSight.clear();
        markAllLevelCellsForEnemies(enemySupplier.get());
    }

    private void markAllLevelCellsForEnemies(List<Enemy> enemies) {
        forEachLevelCellInSight(cell -> {
            for (Enemy enemy : enemies) {
                markEnemy(cell, enemy);
            }
        });
    }

    private void markEnemy(LevelCell cell, Enemy enemy) {
        if (enemy.hasSamePositionAs(cell)) {
            enemy.trigger();
            markEnemyInSight(enemy);
            markEnemyRoutesVisited(enemy);
        }
    }

    private void markEnemyRoutesVisited(Enemy enemy) {
        enemy.getRoutes().forEach(this::markEnemyRouteVisited);
    }

    private void markEnemyRouteVisited(Route route) {
        route.getSteps().forEach(this::markEnemyRouteStepVisited);
    }

    private void markEnemyRouteStepVisited(Step step) {
        Gdx.app.debug(getClass().getSimpleName(), String.format("Enemy route step marked visited - x%s - y%s", step.getX(), step.getY()));
        levelTiledMap.getLevelCell(step).markVisited();
    }

    private void markEnemyInSight(Enemy enemy) {
        enemiesInSight.put(enemy, true);
    }

    private void forEachLevelCellInSight(Consumer<LevelCell> handler) {
        for (int cellX = startSightX(player); cellX <= endSightX(player); cellX++) {
            for (int cellY = startSightY(player); cellY <= endSightY(player); cellY++) {
                handler.accept(levelTiledMap.getLevelCell(cellX, cellY));
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

    private void markAllTilesNotInSight() {
        for (int cellX = 0; cellX < levelTiledMap.getWidth(); cellX++) {
            for (int cellY = 0; cellY < levelTiledMap.getHeight(); cellY++) {
                levelTiledMap.getLevelCell(cellX, cellY).markNotInSight();
            }
        }
    }
}
