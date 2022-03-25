package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    public void testGetItemByIdWhenItemWasNotFound(){

        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.findById(0));
    }

    @Test
    public void testGetItemByIdSuccess(){

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
    public void testUpdateQualityOfNormalTypeItem(){

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Oreo", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(29, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid normal type item in the database with sellin value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality decreasing it by 1
     * and update sellIn decreasing it by 2
     */
    public void testUpdateQualityOfNormalTypeItemSellInPassed(){
        var item = new Item(0, "Cookie", -1, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(28, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid normal type item in the database with quality set in 0
     * WHEN updateQuality method is called
     * THEN the service should not decrease the quality
     */
    public void testUpdateQualityOfNormalTypeItemQualityNeverNegative(){
        var item = new Item(0, "Cookie", 2, 0, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(1, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid AGED type item in the database
     * WHEN updateQuality method is called
     * THEN the service should decrease the sellin by 1 and
     * increase the quality by 1
     */
    public void testUpdateQualityOfAgedTypeItem(){
        var item = new Item(0, "Cookie", 3, 49, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(2, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfAgedTypeItemWithQualityFifty(){
        var item = new Item(0, "Cookie", 3, 50, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(2, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfAgedTypeItemWithSellInGreaterThanEleven(){
        var item = new Item(0, "Jamming", 13, 50, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(12, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfTicketTypeItemWithSellInLessThanElevenMaxQuality(){
        var item = new Item(0, "Jamming", 10, 49, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfTicketTypeItemWithSellInLessThanSix(){
        var item = new Item(0, "Jamming", 5, 10, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(4, itemsUpdated.get(0).sellIn);
        assertEquals(13, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfTicketTypeItemWithSellInLessThanSixMaxQuality(){
        var item = new Item(0, "Jamming", 5, 48, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(4, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfTicketTypeItemSellInZero(){
        var item = new Item(0, "Jamming", 0, 10, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfLegendaryTypeItem(){
        var item = new Item(0, "Potion", 5, 50, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Potion", itemsUpdated.get(0).name);
        assertEquals(5, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfLegendaryTypeItemSellInLessThanZero(){
        var item = new Item(0, "Potion", -1, 0, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Potion", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfAgedTypeItemSellInLessThanZeroMaxQuality(){
        var item = new Item(0, "Potion", -1, 50, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Potion", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfAgedTypeItemWithQualityLessThanFifty(){
        var item = new Item(0, "Cookie", -1, 47, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cookie", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(49, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testCreateItem(){

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item itemSaved = itemService.createItem(item);
        assertEquals(item, itemSaved);
    }


    @Test
    public void testUpdateItemSuccess(){
        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item itemUpdated = itemService.updateItem(0, item);
        assertEquals(item, itemUpdated);
    }

    @Test
    public void testUpdateItemException(){
        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.updateItem(0, item));
    }

    @Test
    public void testListItemsSuccess(){
        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        List<Item> listExpected = List.of(item);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> listItems = itemService.listItems();
        assertEquals(listExpected, listItems);
    }











}
