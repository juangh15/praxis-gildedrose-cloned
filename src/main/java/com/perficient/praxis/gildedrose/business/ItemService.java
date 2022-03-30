package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.ResourceDuplicated;
import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.model.ItemFactory;
import com.perficient.praxis.gildedrose.model.LegendaryItem;
import com.perficient.praxis.gildedrose.repository.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    Item[] items;

    public ItemService(ItemRepository itemRepository, Item[] items) {
        this.itemRepository = itemRepository;
        this.items = items;
    }

    public List<Item> updateQuality() {
        var items = itemRepository.findAll();
        items.forEach(item -> {
            Item typedItem = ItemFactory.createTypedItem(item);
            LegendaryItem item2 = new LegendaryItem(item);
            typedItem.updateQuality();
            item = ItemFactory.createUntypedItem(typedItem);
            itemRepository.save(item);

        });
        return items;
    }


    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> createItems(List<Item> items){
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase())
                .withMatcher("quality", ignoreCase())
                .withMatcher("sellin", ignoreCase())
                .withMatcher("type", ignoreCase());
        for (int i = 0; i<items.size(); i++){
            Item probe = items.get(i);
            Example<Item> example = Example.of(probe, modelMatcher);
            boolean exists = itemRepository.exists(example);
            if(exists){
                throw new ResourceDuplicated("");
            }
        }
        return itemRepository.saveAll(items);
    }

    public Item updateItem(int id, Item item) {
        if(itemRepository.findById(id).isPresent()){
            return itemRepository.save(new Item(id, item.name, item.sellIn, item.quality, item.type));
        }else{
            throw new ResourceNotFoundException("");
        }
    }

    public List<Item> listItems(){
        return itemRepository.findAll();
    }

    public Item findById(int id) {
        return itemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(""));
    }
}
