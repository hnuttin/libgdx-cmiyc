package com.jazzjack.rab.bit.cmiyc.gdx;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class ClasspathFileHandleResolver implements FileHandleResolver {
    @Override
    public FileHandle resolve(String fileName) {
        return new ClasspathFileHandle(fileName);
    }
}
