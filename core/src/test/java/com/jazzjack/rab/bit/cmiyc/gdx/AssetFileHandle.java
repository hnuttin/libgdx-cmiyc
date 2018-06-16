package com.jazzjack.rab.bit.cmiyc.gdx;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;

public class AssetFileHandle extends FileHandle {

    private static final String ANDROID_ASSETS_PATH = "../android/assets/";

    public AssetFileHandle(String fileName) {
        super(ANDROID_ASSETS_PATH + fileName, Files.FileType.Absolute);
    }
}
