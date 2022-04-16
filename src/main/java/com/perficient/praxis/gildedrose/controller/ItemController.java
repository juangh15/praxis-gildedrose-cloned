package com.perficient.praxis.gildedrose.controller;

import com.perficient.praxis.gildedrose.business.ItemService;
import com.perficient.praxis.gildedrose.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ResponseEntity<List<Item>> listItems(){
        var items = itemService.listItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable int id){
        var item = itemService.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Item>> createItemList(@RequestBody List<Item> items){
        List<Item> created = itemService.createItems(items);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item){
        Item createdItem = itemService.createItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id,
                                           @RequestBody Item item){
        Item createdItem = itemService.updateItem(id, item);
        return new ResponseEntity<>(createdItem, HttpStatus.OK);
    }

    @PostMapping("/quality")
    public ResponseEntity<List<Item>> updateItemsQuality(){
        var items = itemService.updateQuality();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteById(@PathVariable int id){
        itemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
