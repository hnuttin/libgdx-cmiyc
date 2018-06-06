package com.jazzjack.rab.bit.cmiyc.shared;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;

public class Randomizer {

    public static final int HUNDRED_PERCENT = 100;

    private final RandomInteger randomInteger;

    public Randomizer(RandomInteger randomInteger) {
        this.randomInteger = randomInteger;
    }

    public <T> T randomFromSet(Set<T> set) {
        Iterator<T> iterator = set.iterator();
        T randomFromSet = null;
        for (int i = 0; i < randomInteger.randomInteger(set.size()) + 1; i++) {
            randomFromSet = iterator.next();
        }
        return randomFromSet;
    }

    public List<Integer> randomPercentages(Predictability predictability, int amount) {
        if (amount == 1) {
            return singletonList(HUNDRED_PERCENT);
        } else {
            int percentagePart = HUNDRED_PERCENT / amount;
            int predictabilityDelta = randomPredictabilityDelta(predictability);
            List<Integer> percentages = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                final int percentage;
                if (i == 0) {
                    percentage = percentagePart + predictabilityDelta;
                } else if (i == 1 && i < amount - 1) {
                    percentage = Math.max(1, percentagePart - predictabilityDelta);
                } else if (i == amount - 1) {
                    percentage = HUNDRED_PERCENT - percentages.stream().mapToInt(Integer::intValue).sum();
                } else {
                    percentage = percentagePart;
                }
                percentages.add(percentage);
            }
            return percentages;
        }
    }

    private int randomPredictabilityDelta(Predictability predictability) {
        int gap = predictability.getMaxPercentage() - predictability.getMinPercentage();
        return randomInteger.randomInteger(gap) + predictability.getMinPercentage();
    }

    @SuppressWarnings("unchecked")
    public <T extends Chance> T chooseRandomChance(List<T> chances) {
        int totalPercentage = chances.stream().mapToInt(Chance::getPercentage).sum();
        int randomPercentage = randomInteger.randomInteger(totalPercentage) + 1;
        List<PercentageInterval> percentageStack = createPercentageStack(chances);
        return (T) percentageStack.stream()
                .filter(percentageInterval -> matchesPercentageInterval(randomPercentage, percentageInterval))
                .map(percentageInterval -> percentageInterval.chance)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("This should not happen if percentageStack is correctly created: this is a bug!"));
    }

    private <T extends Chance> List<PercentageInterval> createPercentageStack(List<T> chances) {
        List<PercentageInterval> percentageStack = new ArrayList<>(chances.size());
        for (int i = 0; i < chances.size(); i++) {
            Chance chance = chances.get(i);
            if (percentageStack.isEmpty()) {
                percentageStack.add(new PercentageInterval(chance, 1, chance.getPercentage()));
            } else {
                int endPercentageFromPrevious = percentageStack.get(percentageStack.size() - 1).endPercentage;
                percentageStack.add(new PercentageInterval(chance, endPercentageFromPrevious + 1, endPercentageFromPrevious + chance.getPercentage()));
            }
        }
        return percentageStack;
    }

    private boolean matchesPercentageInterval(int randomPercentage, PercentageInterval percentageInterval) {
        return randomPercentage >= percentageInterval.startPercentage && randomPercentage <= percentageInterval.endPercentage;
    }

    private static class PercentageInterval {

        private final Chance chance;
        private final int startPercentage;
        private final int endPercentage;

        private PercentageInterval(Chance chance, int startPercentage, int endPercentage) {
            this.chance = chance;
            this.startPercentage = startPercentage;
            this.endPercentage = endPercentage;
        }
    }

}
