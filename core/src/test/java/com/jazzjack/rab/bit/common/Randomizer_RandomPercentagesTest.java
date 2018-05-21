package com.jazzjack.rab.bit.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Randomizer_RandomPercentagesTest {

    private final Randomizer randomizer = new Randomizer(new RandomInteger());

    @Test
    void expect100PercentWhenOnly1() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.LOW, 1);

        assertThat(percentages).hasSize(1);
        assertThat(percentages.get(0)).isEqualTo(100);
    }

    @Test
    void expect2RandomPercentagesWithLowPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.LOW, 2);

        assertThat(percentages).hasSize(2);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 55);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

    @Test
    void expect2RandomPercentagesWithMediumPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.MEDIUM, 2);

        assertThat(percentages).hasSize(2);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 70);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

    @Test
    void expect2RandomPercentagesWithHighPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.HIGH, 2);

        assertThat(percentages).hasSize(2);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 85);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

    @Test
    void expect3RandomPercentagesWithLowPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.LOW, 3);

        assertThat(percentages).hasSize(3);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 38);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

    @Test
    void expect3RandomPercentagesWithMediumPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.MEDIUM, 3);

        assertThat(percentages).hasSize(3);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 53);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

    @Test
    void expect3RandomPercentagesWithHighPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.HIGH, 3);

        assertThat(percentages).hasSize(3);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 68);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

    @Test
    void expect4RandomPercentagesWithLowPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.LOW, 4);

        assertThat(percentages).hasSize(4);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 30);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

    @Test
    void expect4RandomPercentagesWithMediumPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.MEDIUM, 4);

        assertThat(percentages).hasSize(4);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 45);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

    @Test
    void expect4RandomPercentagesWithHighPredictability() {
        List<Integer> percentages = randomizer.randomPercentages(Predictability.HIGH, 4);

        assertThat(percentages).hasSize(4);
        assertThat(percentages.stream().mapToInt(Integer::intValue).sum()).isEqualTo(Randomizer.HUNDRED_PERCENT);
        assertThat(percentages).anyMatch(percentage -> percentage >= 60);
        assertThat(percentages).noneMatch(percentage -> percentage < 1);
    }

}