package com.jazzjack.rab.bit.cmiyc.level;

import com.jazzjack.rab.bit.cmiyc.common.Predictability;
import com.jazzjack.rab.bit.cmiyc.gdx.ClasspathFileHandle;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectTypeParserTest {

    private final ObjectTypeParser objectTypeParser = new ObjectTypeParser(new ClasspathFileHandle("objecttypes.xml"));

    @Test
    void getEnemy1Predictability() {
        Predictability predictability = objectTypeParser.getEnemyPredictability("enemy1");

        assertThat(predictability).isEqualTo(Predictability.HIGH);
    }

    @Test
    void getEnemy2Predictability() {
        Predictability predictability = objectTypeParser.getEnemyPredictability("enemy2");

        assertThat(predictability).isEqualTo(Predictability.MEDIUM);
    }

    @Test
    void getEnemy3Predictability() {
        Predictability predictability = objectTypeParser.getEnemyPredictability("enemy3");

        assertThat(predictability).isEqualTo(Predictability.LOW);
    }
}