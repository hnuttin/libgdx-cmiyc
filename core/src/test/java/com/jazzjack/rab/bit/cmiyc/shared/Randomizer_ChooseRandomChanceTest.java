package com.jazzjack.rab.bit.cmiyc.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Randomizer_ChooseRandomChanceTest {

    private static final int FIRST_CHANCE = 25;
    private static final int SECOND_CHANCE = 30;
    private static final int THIRD_CHANCE = 45;

    private static final List<Chance> CHANCES = asList(() -> FIRST_CHANCE, () -> SECOND_CHANCE, () -> THIRD_CHANCE);

    @InjectMocks
    private Randomizer randomizer;
    @Mock
    private RandomInteger randomInteger;

    @Test
    void expectFirstChanceWhen1Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(0);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(FIRST_CHANCE);
    }

    @Test
    void expectFirstChanceWhen10Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(9);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(FIRST_CHANCE);
    }

    @Test
    void expectFirstChanceWhen25Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(24);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(FIRST_CHANCE);
    }

    @Test
    void expectSecondChanceWhen26Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(25);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(SECOND_CHANCE);
    }

    @Test
    void expectSecondChanceWhen50Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(50);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(SECOND_CHANCE);
    }

    @Test
    void expectSecondChanceWhen55Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(54);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(SECOND_CHANCE);
    }

    @Test
    void expectThirdChanceWhen56Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(55);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(THIRD_CHANCE);
    }

    @Test
    void expectThirdChanceWhen90Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(89);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(THIRD_CHANCE);
    }

    @Test
    void expectThirdChanceWhen100Percent() {
        when(randomInteger.randomInteger(Randomizer.HUNDRED_PERCENT)).thenReturn(99);
        Chance randomChance = randomizer.chooseRandomChance(CHANCES);
        assertThat(randomChance.getPercentage()).isEqualTo(THIRD_CHANCE);
    }

    @Test
    void expectNoExceptionWhenTotalPercentageNot100Percent() {
        when(randomInteger.randomInteger(FIRST_CHANCE + SECOND_CHANCE)).thenReturn(1);
        Chance randomChance = randomizer.chooseRandomChance(asList(() -> FIRST_CHANCE, () -> SECOND_CHANCE));
        assertThat(randomChance.getPercentage()).isEqualTo(FIRST_CHANCE);
    }

}