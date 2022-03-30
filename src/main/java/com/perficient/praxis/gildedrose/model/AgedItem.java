package com.perficient.praxis.gildedrose.model;


public class AgedItem extends Item{

    public AgedItem(Item item){
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
        return this;
    }
}
