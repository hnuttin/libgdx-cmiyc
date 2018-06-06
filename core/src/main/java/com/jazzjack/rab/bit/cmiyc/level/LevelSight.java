package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;

public class LevelSight {

    private static final String PROPERTY_VISISTED = "visited";
    private static final String PROPERTY_IN_SIGHT = "inSight";

    private final TiledMapTileLayer mapLayer;

    public LevelSight(TiledMapTileLayer mapLayer) {
        this.mapLayer = mapLayer;
    }

    private void markTiles(Player player) {
        markAllTilesNotInSight();
        TiledMapTileLayer.Cell playerCell = mapLayer.getCell(player.getX(), player.getY());
        markTileVisitedAndInSight(playerCell);
    }

    private void markAllTilesNotInSight() {
        for (int cellX = 0; cellX < mapLayer.getWidth(); cellX++) {
            for (int cellY = 0; cellY < mapLayer.getHeight(); cellY++) {
                markTileNotInSight(mapLayer.getCell(cellX, cellY));
            }
        }
    }

    private void markTileNotInSight(TiledMapTileLayer.Cell cell) {
        cell.getTile().getProperties().put(PROPERTY_IN_SIGHT, false);
    }

    private void markTileVisitedAndInSight(TiledMapTileLayer.Cell cell) {
        cell.getTile().getProperties().put(PROPERTY_VISISTED, true);
        cell.getTile().getProperties().put(PROPERTY_IN_SIGHT, true);
    }
}
