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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> updateQuality() {
        ArrayList<Item> items = new ArrayList<>(itemRepository.findAll());
        ListIterator<Item> listPosition = items.listIterator();
        while (listPosition.hasNext()) {
            Item actualItem = listPosition.next();
            actualItem = ItemFactory.createTypedItem(actualItem);
            actualItem.updateQuality();
            actualItem = ItemFactory.createUntypedItem(actualItem);
            listPosition.set(actualItem);
            itemRepository.save(actualItem);
        }
        return items.stream().toList();
    }


    public Item createItem(Item item) {
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase())
                .withMatcher("quality", ignoreCase())
                .withMatcher("sellin", ignoreCase())
                .withMatcher("type", ignoreCase());
        Example<Item> example = Example.of(item, modelMatcher);
        boolean exists = itemRepository.exists(example);
        if (exists) {
            throw new ResourceDuplicated("");
        }
        return itemRepository.save(item);
    }

    public List<Item> createItems(List<Item> items) {
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("name", ignoreCase())
                .withMatcher("quality", ignoreCase())
                .withMatcher("sellin", ignoreCase())
                .withMatcher("type", ignoreCase());
        for (int i = 0; i < items.size(); i++) {
            Item probe = items.get(i);
            Example<Item> example = Example.of(probe, modelMatcher);
            boolean exists = itemRepository.exists(example);
            if (exists) {
                throw new ResourceDuplicated("");
            }
        }
        return itemRepository.saveAll(items);
    }

    public Item updateItem(int id, Item item) {
        if (itemRepository.findById(id).isPresent()) {
            return itemRepository.save(new Item(id, item.name, item.sellIn, item.quality, item.type));
        } else {
            throw new ResourceNotFoundException("");
        }
    }

    public List<Item> listItems() {
        return itemRepository.findAll();
    }

    public Item findById(int id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(""));
    }

    public void deleteById(int id) {
        Item item = findById(id);
        itemRepository.delete(item);
    }
}
