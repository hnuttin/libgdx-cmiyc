package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.ability.Ability;
import com.jazzjack.rab.bit.cmiyc.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerProfile {

    private int actionPointsPerTurn;

    private int maxHp;
    private int hp;

    private int sight;

    private final List<Item> items;
    private final List<Ability> abilities;

    private PlayerProfile(Builder builder) {
        this.actionPointsPerTurn = builder.actionPointsPerTurn;
        this.maxHp = builder.maxHp;
        this.hp = builder.hp;
        this.sight = builder.sight;

        this.items = new ArrayList<>();
        this.abilities = new ArrayList<>();
        this.abilities.add(Ability.MARK);
    }

    public int getActionPointsPerTurn() {
        return actionPointsPerTurn;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    void incrementHp() {
        if (hp < maxHp) {
            hp++;
        }
    }

    void damage(int damage) {
        hp = Math.max(0, hp - damage);
    }

    public boolean isDead() {
        return hp == 0;
    }

    public int getSight() {
        return sight;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean consumeItem(Item item) {
        return items.remove(item);
    }

    public List<Ability> getAbilities() {
        return Collections.unmodifiableList(abilities);
    }

    public boolean hasAbility(Ability ability) {
        return abilities.contains(ability);
    }

    public static Builder playerProfileBuilder() {
        return new Builder();
    }

    public static class Builder {

        private int actionPointsPerTurn = 5;
        private int maxHp = 5;
        private int hp = 3;
        private int sight = 2;

        public Builder withActionPointsPerTurn(int actionPointsPerTurn) {
            this.actionPointsPerTurn = actionPointsPerTurn;
            return this;
        }

        public Builder withMaxHp(int maxHp) {
            this.maxHp = maxHp;
            return this;
        }

        public Builder withHp(int hp) {
            this.hp = hp;
            return this;
        }

        public Builder withSight(int sight) {
            this.sight = sight;
            return this;
        }

        public PlayerProfile build() {
            return new PlayerProfile(this);
        }
    }
}
