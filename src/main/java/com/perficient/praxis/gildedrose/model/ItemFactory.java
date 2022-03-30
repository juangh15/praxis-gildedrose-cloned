package com.perficient.praxis.gildedrose.model;

import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;

public class ItemFactory {
    public static Item createTypedItem(Item baseItem) {
        Item typedItem;
        switch (baseItem.type){
            case NORMAL:
                typedItem = new NormalItem(baseItem);
                break;
            case AGED:
                typedItem = new AgedItem(baseItem);
                break;
            case LEGENDARY:
                typedItem = new LegendaryItem(baseItem);
                break;
            case TICKETS:
                typedItem = new TicketsItem(baseItem);
                break;
            default:
                throw new ResourceNotFoundException("a");

        }
        return typedItem;
    }

    public static Item createUntypedItem(Item typed){
        Item untypedItem = new Item(typed.getId(), typed.name, typed.sellIn, typed.quality, typed.type);
        return untypedItem;
    }
}
