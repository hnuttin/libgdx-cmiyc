package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LevelSight {

    private static final String PROPERTY_VISISTED = "visited";
    private static final String PROPERTY_IN_SIGHT = "inSight";

    private final TiledMapTileLayer mapLayer;

    public LevelSight(TiledMapTileLayer mapLayer) {
        this.mapLayer = mapLayer;
    }

    private void markTiles(Player player) {
        markAllTilesNotInSight();
        for (int cellX = startSightX(player); cellX < endSightX(player); cellX++) {
            for (int cellY = startSightY(player); cellY < endSightY(player); cellY++) {
                markTileVisitedAndInSight(mapLayer.getCell(cellX, cellY));
            }
        }
    }

    private void markAllTilesNotInSight() {
        for (int cellX = 0; cellX < mapLayer.getWidth(); cellX++) {
            for (int cellY = 0; cellY < mapLayer.getHeight(); cellY++) {
                markTileNotInSight(mapLayer.getCell(cellX, cellY));
            }
        }
    }

    private int startSightX(Player player) {
        return max(0, player.getX() - player.getSight());
    }

    private int endSightX(Player player) {
        return min(mapLayer.getWidth(), player.getX() + player.getSight());
    }

    private int startSightY(Player player) {
        return max(0, player.getY() - player.getSight());
    }

    private int endSightY(Player player) {
        return min(mapLayer.getHeight(), player.getY() + player.getSight());
    }

    private void markTileNotInSight(TiledMapTileLayer.Cell cell) {
        cell.getTile().getProperties().put(PROPERTY_IN_SIGHT, false);
    }

    private void markTileVisitedAndInSight(TiledMapTileLayer.Cell cell) {
        cell.getTile().getProperties().put(PROPERTY_VISISTED, true);
        cell.getTile().getProperties().put(PROPERTY_IN_SIGHT, true);
    }
}
