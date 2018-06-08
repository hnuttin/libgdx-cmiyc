package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedEvent;
import com.jazzjack.rab.bit.cmiyc.actor.player.PlayerMovedSubscriber;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LevelSight implements PlayerMovedSubscriber {

    private static final String PROPERTY_VISITED = "visited";
    private static final String PROPERTY_IN_SIGHT = "inSight";

    private final TiledMapTileLayer mapLayer;

    public LevelSight(TiledMapTileLayer mapLayer, Player player) {
        this.mapLayer = mapLayer;
        markTiles(player);
    }

    @Override
    public void playerMoved(PlayerMovedEvent event) {
        markTiles(event.getPlayer());
    }

    public boolean isVisited(HasPosition hasPosition) {
        return getCellBooleanProperty(hasPosition, PROPERTY_VISITED);
    }

    public boolean isInSight(HasPosition hasPosition) {
        return getCellBooleanProperty(hasPosition, PROPERTY_IN_SIGHT);
    }

    private boolean getCellBooleanProperty(HasPosition hasPosition, String property) {
        Object propertyValue = mapLayer.getCell(hasPosition.getX(), hasPosition.getY()).getTile().getProperties().get(property);
        if (propertyValue == null) {
            return false;
        } else if (propertyValue instanceof Boolean) {
            return (boolean) propertyValue;
        } else {
            throw new IllegalArgumentException(String.format("Property %s is not of type boolean", property));
        }
    }

    private void markTiles(Player player) {
        markAllTilesNotInSight();
        for (int cellX = startSightX(player); cellX <= endSightX(player); cellX++) {
            for (int cellY = startSightY(player); cellY <= endSightY(player); cellY++) {
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
        cell.getTile().getProperties().put(PROPERTY_VISITED, true);
        cell.getTile().getProperties().put(PROPERTY_IN_SIGHT, true);
    }
}
