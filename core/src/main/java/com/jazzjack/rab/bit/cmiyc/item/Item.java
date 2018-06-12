package com.jazzjack.rab.bit.cmiyc.item;

public enum Item {

    HP("item-hp"), SHIELD("item-shield");

    private final String name;

    Item(String name) {
        this.name = name;
    }

    public static Item forName(String name) {
        for (Item item : values()) {
            if (item.name.equalsIgnoreCase(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException("No item found for name: " + name);
    }
}
