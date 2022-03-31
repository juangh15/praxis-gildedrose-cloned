package com.perficient.praxis.gildedrose.model;


public class LegendaryItem extends Item {

    public LegendaryItem(Item item) {
        super(item.getId(), item.name, item.sellIn, item.quality, Type.LEGENDARY);
    }

    @Override
    public Item updateQuality() {
        return this;
    }
}
