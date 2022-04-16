package com.perficient.praxis.gildedrose.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "items")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name is mandatory")
    public String name;

    @NotNull(message = "sellIn is mandatory")
    public int sellIn;

    @NotNull(message = "quality is mandatory")
    @Min(0)
    @Max(80)
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

    public Item updateQuality() {
        this.quality = this.quality - 1;
        return this;
    }

    public void increaseQualityBy(int amount) {
        if (this.quality + amount > 50) {
            this.quality = 50;
        } else {
            this.quality += amount;
        }

    }

    public void decreaseQualityBy(int amount) {
        if (this.quality - amount < 0) {
            this.quality = 0;
        } else {
            this.quality -= amount;
        }
    }

    public void decreaseSellIn() {
        this.sellIn = this.sellIn - 1;
    }

    public boolean isExpired() {
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
        return this.id + ", " + this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
