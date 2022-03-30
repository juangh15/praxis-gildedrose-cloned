package com.perficient.praxis.gildedrose.model;


public class LegendaryItem extends Item{

    public LegendaryItem(Item item){
        this.setId(item.getId());
        this.name = item.name;
        this.quality = item.quality;
        this.sellIn = item.sellIn;
        this.type = item.type;
    }
    @Override
    public Item updateQuality(){
        this.increaseQualityBy(0);
        return this;
    }
}
