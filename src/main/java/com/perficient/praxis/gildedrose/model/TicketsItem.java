package com.perficient.praxis.gildedrose.model;

public class TicketsItem extends Item {

    public TicketsItem(Item item) {
        super(item.getId(), item.name, item.sellIn, item.quality, Type.TICKETS);
    }

    @Override
    public Item updateQuality() {
        this.decreaseSellIn();
        if (this.isExpired()) {
            this.decreaseQualityBy(this.quality);
        } else if (remainingDaysForConcert() <= 5) {
            this.increaseQualityBy(3);
        } else if (remainingDaysForConcert() <= 10) {
            this.increaseQualityBy(2);
        } else {
            this.increaseQualityBy(1);
        }
        return this;
    }

    public int remainingDaysForConcert() {
        return this.sellIn;
    }

}
