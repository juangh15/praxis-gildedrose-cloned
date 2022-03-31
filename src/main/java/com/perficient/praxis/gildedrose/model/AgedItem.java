package com.perficient.praxis.gildedrose.model;


public class AgedItem extends Item {

    public AgedItem(Item item) {
        super(item.getId(), item.name, item.sellIn, item.quality, Type.AGED);
    }

    @Override
    public Item updateQuality() {
        this.decreaseSellIn();
        if (this.isExpired()) {
            this.increaseQualityBy(2);
        } else {
            this.increaseQualityBy(1);
        }
        return this;
    }
}
