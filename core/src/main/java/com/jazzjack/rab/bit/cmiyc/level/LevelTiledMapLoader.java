package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.XmlReader;

import java.util.function.BiConsumer;

public class LevelTiledMapLoader extends TmxMapLoader {

    public LevelTiledMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public LevelTiledMap loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
        return new LevelTiledMap(super.loadSync(manager, fileName, file, parameter));
    }

    @Override
    protected void loadTileLayer(TiledMap map, MapLayers parentLayers, XmlReader.Element element) {
        if (element.getName().equals("layer")) {
            int width = element.getIntAttribute("width", 0);
            int height = element.getIntAttribute("height", 0);
            int tileWidth = map.getProperties().get("tilewidth", Integer.class);
            int tileHeight = map.getProperties().get("tileheight", Integer.class);
            TiledMapTileLayer layer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);

            loadBasicLayerInfo(layer, element);

            int[] ids = getTileIds(element, width, height);
            TiledMapTileSets tilesets = map.getTileSets();

            forEachCell(width, height, (x, y) -> {
                int id = ids[y * width + x];
                boolean flipHorizontally = ((id & FLAG_FLIP_HORIZONTALLY) != 0);
                boolean flipVertically = ((id & FLAG_FLIP_VERTICALLY) != 0);

                TiledMapTile tile = tilesets.getTile(id & ~MASK_CLEAR);
                if (tile != null) {
                    int correctedY = flipY ? height - 1 - y : y;
                    TiledMapTileLayer.Cell cell = createTileLayerCell(flipHorizontally, flipVertically, x, correctedY);
                    cell.setTile(tile);
                    layer.setCell(x, correctedY, cell);
                }
            });

            XmlReader.Element properties = element.getChildByName("properties");
            if (properties != null) {
                loadProperties(layer.getProperties(), properties);
            }
            parentLayers.add(layer);
        }
    }

    private void forEachCell(double width, double height, BiConsumer<Integer, Integer> xyHandler) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                xyHandler.accept(x, y);
            }
        }
    }

    private LevelCell createTileLayerCell(boolean flipHorizontally, boolean flipVertically, int x, int y) {
        LevelCell cell = new LevelCell(x, y);
        cell.setFlipHorizontally(flipHorizontally);
        cell.setFlipVertically(flipVertically);
        return cell;
    }
}
