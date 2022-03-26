package com.perficient.praxis.gildedrose.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class ItemTest {

    @Test
    public void testSetId(){
        int expectedId = 1;
        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        item.setId(expectedId);

        assertEquals(expectedId, item.getId());
    }

    @Test
    public void testToString(){
        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        String expectedString = "0, Oreo, 10, 30";

        assertEquals(expectedString, item.toString());
    }



}
