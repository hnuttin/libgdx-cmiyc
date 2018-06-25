package com.jazzjack.rab.bit.cmiyc.item;

public enum Item {

    HP, SHIELD;

    public String getName() {
        return "item-" + name().toLowerCase();
    }

    public static Item forName(String name) {
        for (Item item : values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException("No item found for name: " + name);
    }
}
