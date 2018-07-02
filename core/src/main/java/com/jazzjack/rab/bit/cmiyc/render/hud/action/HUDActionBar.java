package com.jazzjack.rab.bit.cmiyc.render.hud.action;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.jazzjack.rab.bit.cmiyc.ability.Ability;
import com.jazzjack.rab.bit.cmiyc.actor.player.Player;
import com.jazzjack.rab.bit.cmiyc.item.Item;
import com.jazzjack.rab.bit.cmiyc.render.GameAssetManager;
import com.jazzjack.rab.bit.cmiyc.shared.position.Position;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class HUDActionBar {

    private final GameAssetManager assetManager;
    private final Player player;
    private final Batch batch;
    private final int xOffset;

    private final List<HUDAction> hudActions;

    public HUDActionBar(GameAssetManager assetManager, Player player, Batch batch, int xOffset) {
        this.assetManager = assetManager;
        this.player = player;
        this.batch = batch;
        this.xOffset = xOffset;
        this.hudActions = initHudActions();
    }

    private List<HUDAction> initHudActions() {
        int x = xOffset;
        IntStream.range(0, player.getAbilities().size())
                .mapToObj(i -> new ItemHUDAction(new Position(i + xOffSet, 0), player.getItems().get(i)))
                .collect(toList());
        List<ItemHUDAction> itemHUDActions = createItemHUDActions(x, player.getItems());
        return null;
    }

    private List<ItemHUDAction> createItemHUDActions(int xOffSet, List<Item> items) {
        return IntStream.range(0, items.size())
                .mapToObj(i -> new ItemHUDAction(new Position(i + xOffSet, 0), player.getItems().get(i)))
                .collect(toList());
    }

    public void renderActions() {
        int x = xOffset;
        for (Ability ability : player.getAbilities()) {
            batch.draw(assetManager.getTextureForName(ability.getName()), x, 0f, 1f, 1f);
            x++;
        }
        for (Item item : player.getItems()) {
            batch.draw(assetManager.getTextureForName(item.getName()), x, 0f, 1f, 1f);
            x++;
        }
    }
}
