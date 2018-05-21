package com.jazzjack.rab.bit.cmiyc.gdx;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;

public class ClasspathFileHandle extends FileHandle {

    public ClasspathFileHandle(String fileName) {
        super(fileName, Files.FileType.Classpath);
    }
}
