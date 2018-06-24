package com.jazzjack.rab.bit.cmiyc.actor.enemy;

import com.jazzjack.rab.bit.cmiyc.shared.Predictability;
import com.jazzjack.rab.bit.cmiyc.shared.Sense;

public class EnemyConfig {

    private final String name;
    private final Predictability predictability;
    private final Sense sense;
    private final int numberOfRoutesToGenerate;
    private final int maxRouteLength;

    private EnemyConfig(Builder builder) {
        this.name = builder.name;
        this.predictability = builder.predictability;
        this.sense = builder.sense;
        this.numberOfRoutesToGenerate = builder.numberOfRoutesToGenerate;
        this.maxRouteLength = builder.maxRouteLength;
    }

    public String getName() {
        return name;
    }

    public Predictability getPredictability() {
        return predictability;
    }

    public Sense getSense() {
        return sense;
    }

    public int getNumberOfRoutesToGenerate() {
        return numberOfRoutesToGenerate;
    }

    public int getMaxRouteLength() {
        return maxRouteLength;
    }

    public static Builder enemyConfig(String name) {
        return new Builder(name);
    }

    public static class Builder {

        private final String name;
        private Predictability predictability = Predictability.HIGH;
        private Sense sense = Sense.LOW;
        private int numberOfRoutesToGenerate = 1;
        private int maxRouteLength = 1;

        private Builder(String name) {
            this.name = name;
        }

        public Builder withPredictability(Predictability predictability) {
            this.predictability = predictability;
            return this;
        }

        public Builder withSense(Sense sense) {
            this.sense = sense;
            return this;
        }

        public Builder withNumberOfRoutesToGenerate(int numberOfRoutesToGenerate) {
            this.numberOfRoutesToGenerate = numberOfRoutesToGenerate;
            return this;
        }

        public Builder withMaxRouteLength(int maxRouteLength) {
            this.maxRouteLength = maxRouteLength;
            return this;
        }

        public EnemyConfig build() {
            return new EnemyConfig(this);
        }
    }
}
