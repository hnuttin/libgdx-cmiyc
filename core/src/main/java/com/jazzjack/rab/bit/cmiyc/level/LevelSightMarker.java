package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.event.GameEventBus;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LevelSightMarker implements PlayerMovedSubscriber {

    private final LevelTiledMap levelTiledMap;

    LevelSightMarker(LevelTiledMap levelTiledMap, Player player) {
        this.levelTiledMap = levelTiledMap;
        markTiles(player);
        GameEventBus.registerSubscriber(this);
    }

    @Override
    public void playerMoved(PlayerMovedEvent event) {
        markTiles(event.getPlayer());
    }

    private void markTiles(Player player) {
        markAllTilesNotInSight();
        for (int cellX = startSightX(player); cellX <= endSightX(player); cellX++) {
            for (int cellY = startSightY(player); cellY <= endSightY(player); cellY++) {
                LevelCell levelCell = levelTiledMap.getLevelCell(cellX, cellY);
                levelCell.markVisited();
                levelCell.markInSight();
            }
        }
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
