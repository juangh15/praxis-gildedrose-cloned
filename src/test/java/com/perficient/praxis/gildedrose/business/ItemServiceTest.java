package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.ResourceDuplicated;
import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceTest {

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;


    @Test
    /**
     * GIVEN a not existing id of an item in the database
     * WHEN findById method is called
     * THEN the service should throw a resource not found exception.
     */
    public void testGetItemByIdWhenItemWasNotFound() {

        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.findById(0));
    }

    @Test
    /**
     * GIVEN a valid id of an item in the database
     * WHEN findById method is called..
     * THEN the service should get the item of the database.
     */
    public void testGetItemByIdSuccess() {

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        Item itemFound = itemService.findById(0);
        assertEquals(item, itemFound);
    }

    @Test
    /**
     * GIVEN a valid normal type item in the database
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * both will be decreased by 1
     */
    public void testUpdateQualityOfNormalTypeItem() {

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Oreo", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(29, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid normal type item in the database with sellin value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality decreasing it by 1
     * and update sellIn decreasing it by 2
     */
    public void testUpdateQualityOfNormalTypeItemSellInPassed() {
        var item = new Item(0, "Cookie", -1, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(28, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid normal type item in the database with quality set in 0
     * WHEN updateQuality method is called
     * THEN the service should not decrease the quality
     */
    public void testUpdateQualityOfNormalTypeItemQualityNeverNegative() {
        var item = new Item(0, "Cookie", 2, 0, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(1, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid AGED type item in the database
     * WHEN updateQuality method is called
     * THEN the service should decrease the sellin by 1 and
     * increase the quality by 1
     */
    public void testUpdateQualityOfAgedTypeItem() {
        var item = new Item(0, "Cookie", 3, 49, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(2, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid AGED type item in the database with max quality of 50
     * WHEN updateQuality method is called
     * THEN the service should decrease the sellin by 1 and
     * should not change the value of quality.
     */
    public void testUpdateQualityOfAgedTypeItemWithQualityFifty() {
        var item = new Item(0, "Cookie", 3, 50, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(2, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid TICKETS type item in the database with sellin greater than 11
     * and a quality of 49
     * WHEN updateQuality method is called
     * THEN the service should decrease the sellin by 1 and
     * increase the quality by 1.
     */
    public void testUpdateQualityOfTicketsTypeItemWithSellInGreaterThanEleven() {
        var item = new Item(0, "Jamming", 13, 49, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(12, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid TICKETS type item in the database with sellin less than 11
     * and a quality of 49
     * WHEN updateQuality method is called
     * THEN the service should decrease the sellin by 1 and
     * increase the quality by 1.
     */
    public void testUpdateQualityOfTicketsTypeItemWithSellInLessThanElevenNearMaxQuality() {
        var item = new Item(0, "Jamming", 10, 49, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid TICKETS type item in the database with sellin less than 6
     * and a quality of 10
     * WHEN updateQuality method is called
     * THEN the service should decrease the sellin by 1 and
     * increase the quality by 3.
     */
    public void testUpdateQualityOfTicketsTypeItemWithSellInLessThanSix() {
        var item = new Item(0, "Jamming", 5, 10, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(4, itemsUpdated.get(0).sellIn);
        assertEquals(13, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid TICKETS type item in the database with sellin less than 6
     * and a quality of 48
     * WHEN updateQuality method is called
     * THEN the service should decrease the sellin by 1 and
     * increase the quality by 2.
     */
    public void testUpdateQualityOfTicketsTypeItemWithSellInLessThanSixNearMaxQuality() {
        var item = new Item(0, "Jamming", 5, 48, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(4, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid TICKETS type item in the database with sellin equal to 0
     * WHEN updateQuality method is called
     * THEN the service should decrease the sellin by 1 and
     * set the quality to 0.
     */
    public void testUpdateQualityOfTicketsTypeItemSellInZero() {
        var item = new Item(0, "Jamming", 0, 10, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid LEGENDARY type item in the database with sellin greater than 0
     * WHEN updateQuality method is called
     * THEN the service should not change quality or sellin values.
     */
    public void testUpdateQualityOfLegendaryTypeItem() {
        var item = new Item(0, "Potion", 5, 80, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Potion", itemsUpdated.get(0).name);
        assertEquals(5, itemsUpdated.get(0).sellIn);
        assertEquals(80, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid LEGENDARY type item in the database with sellin less than 0
     * WHEN updateQuality method is called
     * THEN the service should decrease sellin by 1 and
     * not change the quality
     */
    public void testUpdateQualityOfLegendaryTypeItemSellInLessThanZero() {
        var item = new Item(0, "Potion", -1, 80, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Potion", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(80, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid AGED type item in the database with sellin less than 0
     * and max quality of 50
     * WHEN updateQuality method is called
     * THEN the service should decrease sellin by 1 and
     * not change the quality
     */
    public void testUpdateQualityOfAgedTypeItemSellInLessThanZeroMaxQuality() {
        var item = new Item(0, "Potion", -1, 50, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Potion", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid AGED type item in the database with quality less than 50 and
     * sellin less than 0
     * WHEN updateQuality method is called
     * THEN the service should decrease sellin by 1 and
     * increase the quality by 2.
     */
    public void testUpdateQualityOfAgedTypeItemWithQualityLessThanFifty() {
        var item = new Item(0, "Cookie", -1, 47, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(49, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid item
     * WHEN createItem method is called
     * THEN the service should save the item in the database.
     */
    public void testCreateItem() {

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item itemSaved = itemService.createItem(item);
        assertEquals(item, itemSaved);
    }


    @Test
    /**
     * GIVEN a valid item with id existing in the database
     * WHEN updateItem method is called
     * THEN the service should update the item with id in the database.
     */
    public void testUpdateItemSuccess() {
        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item itemUpdated = itemService.updateItem(0, item);
        assertEquals(item, itemUpdated);
    }

    @Test
    /**
     * GIVEN a valid item with id not existing in the database
     * WHEN updateItem method is called
     * THEN the service should throw a resource not found exception.
     */
    public void testUpdateItemException() {
        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.updateItem(0, item));
    }

    @Test
    /**
     * GIVEN valid items in the database
     * WHEN listItems method is called
     * THEN the service should list all items of the database
     */
    public void testListItemsSuccess() {
        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        List<Item> listExpected = List.of(item);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> listItems = itemService.listItems();
        assertEquals(listExpected, listItems);
    }

    @Test
    /**
     * GIVEN a valid list of items not existing in database
     * WHEN createItems method is called
     * THEN the service should save the items in the database.
     */
    public void testCreateItemsSuccess() {

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        List<Item> listExpected = List.of(item);
        when(itemRepository.exists(any(Example.class))).thenReturn(false);
        when(itemRepository.saveAll(any(List.class))).thenReturn(List.of(item));

        List<Item> listItems = itemService.createItems(List.of(item));
        assertEquals(listExpected, listItems);
    }

    @Test
    /**
     * GIVEN a valid list of items duplicated in database
     * WHEN createItems method is called
     * THEN the service should NOT save the items in the database
     * and throw a resource duplicated exception.
     */
    public void testCreateItemsFail() {

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        List<Item> listExpected = List.of(item);
        when(itemRepository.exists(any(Example.class))).thenReturn(true);

        assertThrows(ResourceDuplicated.class, () ->
                itemService.createItems(List.of(item)));
    }


}
