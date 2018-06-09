package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LevelTiledMapLoader extends TmxMapLoader {

    public LevelTiledMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public LevelTiledMap loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
        return new LevelTiledMap(super.loadSync(manager, fileName, file, parameter));
    }

    @Override
    protected LevelCell createTileLayerCell(boolean flipHorizontally, boolean flipVertically, boolean flipDiagonally) {
        LevelCell cell = new LevelCell();
        if (flipDiagonally) {
            if (flipHorizontally && flipVertically) {
                cell.setFlipHorizontally(true);
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_270);
            } else if (flipHorizontally) {
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_270);
            } else if (flipVertically) {
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_90);
            } else {
                cell.setFlipVertically(true);
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_270);
            }
        } else {
            cell.setFlipHorizontally(flipHorizontally);
            cell.setFlipVertically(flipVertically);
        }
        return cell;
    }
}
