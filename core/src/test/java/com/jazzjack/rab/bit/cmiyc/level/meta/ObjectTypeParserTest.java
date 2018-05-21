package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectTypeParserTest {

    private final ObjectTypeParser objectTypeParser = new ObjectTypeParser(new ClasspathFileHandle("objecttypes.xml"));

    @Test
    void getEnemy1Predictability() {
        Map<String, String> defaultProperties = objectTypeParser.getDefaultProperties("enemy1");

        assertThat(defaultProperties).containsKey("predictability");
        assertThat(defaultProperties).containsValue("HIGH");
    }

    @Test
    void getEnemy2Predictability() {
        Map<String, String> defaultProperties = objectTypeParser.getDefaultProperties("enemy2");

        assertThat(defaultProperties).containsKey("predictability");
        assertThat(defaultProperties).containsValue("MEDIUM");
    }

    @Test
    void getEnemy3Predictability() {
        Map<String, String> defaultProperties = objectTypeParser.getDefaultProperties("enemy3");

        assertThat(defaultProperties).containsKey("predictability");
        assertThat(defaultProperties).containsValue("LOW");
    }
}