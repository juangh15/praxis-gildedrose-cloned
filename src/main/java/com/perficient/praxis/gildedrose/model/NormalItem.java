package com.perficient.praxis.gildedrose.model;

public class NormalItem extends Item{
    public NormalItem(Item item){
        this.setId(item.getId());
        this.name = item.name;
        this.quality = item.quality;
        this.sellIn = item.sellIn;
        this.type = item.type;
    }
    @Override
    public Item updateQuality(){
        this.decreaseSellIn();
        this.decreaseQualityBy(1);
        if(this.isPassed()){
            this.decreaseQualityBy(1);
        }
        return this;
    }
}
