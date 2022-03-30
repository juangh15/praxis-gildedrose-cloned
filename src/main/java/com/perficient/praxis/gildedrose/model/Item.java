package com.perficient.praxis.gildedrose.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public String name;

    public int sellIn;

    public int quality;

    public Type type;

    public Item() {
    }

    public Item(int id, String name, int sellIn, int quality, Type type) {
        this.id = id;
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item updateQuality(){
        this.quality = this.quality - 1;
        return this;
    }

    public void increaseQualityBy(int amount){
        this.quality += amount;
    }

    public void decreaseQualityBy(int amount){
        this.quality -= amount;
    }

    public void decreaseSellIn(){
        this.sellIn -= 1;
    }

    public boolean isPassed(){
        return this.sellIn < 0;
    }



    public enum Type {
        AGED,
        NORMAL,
        LEGENDARY,
        TICKETS
    }

    @Override
    public String toString() {
        return this.id+ ", " +this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
