package com.perficient.praxis.gildedrose.model;


public class NormalItem extends Item {

    public NormalItem(Item item) {
        super(item.getId(), item.name, item.sellIn, item.quality, Type.NORMAL);
    }

    @Override
    public Item updateQuality() {
        this.decreaseSellIn();
        if (this.isExpired()) {
            this.decreaseQualityBy(2);
        } else {
            this.decreaseQualityBy(1);
        }
        return this;
    }
}
