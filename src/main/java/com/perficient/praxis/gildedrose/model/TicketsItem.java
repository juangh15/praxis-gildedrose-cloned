package com.perficient.praxis.gildedrose.model;

public class TicketsItem extends Item{

    public TicketsItem(Item item){
        this.setId(item.getId());
        this.name = item.name;
        this.quality = item.quality;
        this.sellIn = item.sellIn;
        this.type = item.type;
    }
    @Override
    public Item updateQuality(){
        this.decreaseSellIn();
        this.increaseQualityBy(1);
        if(remainingDaysForConcert() < 11){
            this.increaseQualityBy(1);
        }
        return this;
    }
    public int remainingDaysForConcert(){
        return this.sellIn;
    }
}
