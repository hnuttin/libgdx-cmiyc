package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectTypeParserTest {

    private static final String PREDICTABILITY = "predictability";
    public static final String SENSE = "sense";

    private final ObjectTypeParser objectTypeParser = new ObjectTypeParser(new ClasspathFileHandle("objecttypes.xml"));

    @Test
    void getEnemy1Predictability() {
        Map<String, String> defaultProperties = objectTypeParser.getDefaultProperties("enemy1");

        assertThat(defaultProperties).containsKey(PREDICTABILITY);
        assertThat(defaultProperties.get(PREDICTABILITY)).isEqualTo("HIGH");
    }

    @Test
    void getEnemy1Sense() {
        Map<String, String> defaultProperties = objectTypeParser.getDefaultProperties("enemy1");

        assertThat(defaultProperties).containsKey(SENSE);
        assertThat(defaultProperties.get(SENSE)).isEqualTo("LOW");
    }

    @Test
    void getEnemy2Predictability() {
        Map<String, String> defaultProperties = objectTypeParser.getDefaultProperties("enemy2");

        assertThat(defaultProperties).containsKey(PREDICTABILITY);
        assertThat(defaultProperties.get(PREDICTABILITY)).isEqualTo("MEDIUM");
    }

    @Test
    void getEnemy3Predictability() {
        Map<String, String> defaultProperties = objectTypeParser.getDefaultProperties("enemy3");

        assertThat(defaultProperties).containsKey(PREDICTABILITY);
        assertThat(defaultProperties.get(PREDICTABILITY)).isEqualTo("LOW");
    }

    @Test
    void epectEmptyDefautPropertiesIfObjectTypeNotFound() {
        Map<String, String> defaultProperties = objectTypeParser.getDefaultProperties("unknown");

        assertThat(defaultProperties).isEmpty();
    }
}