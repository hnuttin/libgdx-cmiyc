package com.jazzjack.rab.bit.cmiyc.actor.player;

import com.jazzjack.rab.bit.cmiyc.ability.Ability;
import com.jazzjack.rab.bit.cmiyc.actor.HasPower;
import com.jazzjack.rab.bit.cmiyc.actor.MovableActor;
import com.jazzjack.rab.bit.cmiyc.actor.enemy.Enemy;
import com.jazzjack.rab.bit.cmiyc.collision.CollisionResult;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.shared.Direction;
import com.jazzjack.rab.bit.cmiyc.shared.HasCost;
import com.jazzjack.rab.bit.cmiyc.shared.position.HasPosition;

import java.util.List;

import static com.jazzjack.rab.bit.cmiyc.event.GameEventBus.publishEvent;

public class Player extends MovableActor implements HasPower {

    private final PlayerProfile playerProfile;
    private int actionPointsConsumed;

    private boolean shieldActive;

    public Player(ActorContext context, HasPosition hasPosition, PlayerProfile playerProfile) {
        super(context, "player", hasPosition);
        this.playerProfile = playerProfile;
        this.actionPointsConsumed = 0;
        this.shieldActive = false;
    }

    public void damageFromEnemy(Enemy enemy) {
        if (shieldActive) {
            shieldActive = false;
        } else {
            playerProfile.damage(enemy.getPower());
        }
    }

    @Override
    public CollisionResult moveToDirection(Direction direction) {
        if (hasActionPointsLeft()) {
            CollisionResult collisionResult = super.moveToDirection(direction);
            if (collisionResult.isNoCollision()) {
                actionPointsConsumed++;
                publishEvent(new PlayerMovedEvent(this));
            }
            return collisionResult;
        } else {
            return CollisionResult.noCollision();
        }
    }

    public int getActionPointsPerTurn() {
        return playerProfile.getActionPointsPerTurn();
    }

    public int getActionPointsConsumed() {
        return actionPointsConsumed;
    }

    public int getActionPointsLeft() {
        return playerProfile.getActionPointsPerTurn() - actionPointsConsumed;
    }

    public boolean hasActionPointsLeft() {
        return actionPointsConsumed < playerProfile.getActionPointsPerTurn();
    }

    public void resetActionPoints() {
        actionPointsConsumed = 0;
    }

    public void incrementHp() {
        playerProfile.incrementHp();
    }

    public int getSight() {
        return playerProfile.getSight();
    }

    public int getHp() {
        return playerProfile.getHp();
    }

    public int getMaxHp() {
        return playerProfile.getMaxHp();
    }

    public boolean isShieldActive() {
        return shieldActive;
    }

    public List<Item> getItems() {
        return playerProfile.getItems();
    }

    public void pickupItem(Item item) {
        playerProfile.addItem(item);
    }

    public void useItem(Item item) {
        if (playerProfile.consumeItem(item)) {
            actionPointsConsumed++;
            if (item == Item.SHIELD) {
                shieldActive = true;
            }
        }
    }

    public boolean useAbility(Ability ability) {
        if (playerProfile.hasAbility(ability) && hasEnoughActionPointsFor(ability)) {
            actionPointsConsumed += ability.getCost();
            return true;
        } else {
            return false;
        }
    }

    private boolean hasEnoughActionPointsFor(HasCost cost) {
        return cost.getCost() <= getActionPointsLeft();
    }
}
