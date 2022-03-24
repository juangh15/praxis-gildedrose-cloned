package com.perficient.praxis.gildedrose.thisIsJustATest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class MockTest {

    @Test
    public void testListGetMock() {
        List<String> moviesList= Mockito.mock(List.class);
        String movie="Lord of the rings";
        Mockito.when(moviesList.get(0)).thenReturn(movie);

        assertEquals(movie,moviesList.get(0));
        Assertions.assertNotEquals(movie,moviesList.get(1));

        verify(moviesList,times(2)).get(anyInt());
    }

    @Test
    public void testListGetMock_2() {
        List<String> moviesList= Mockito.mock(List.class);
        String movie="Lord of the rings";
        Mockito.when(moviesList.get(anyInt())).thenReturn(movie);

        assertEquals(movie,moviesList.get(0));
        assertEquals(movie,moviesList.get(1));

        verify(moviesList,times(2)).get(anyInt());
    }

    @Test
    void testSetContains() {

        Set<String> avengersActors=Mockito.mock(Set.class);

        String name1="Robert Downey Jr";
        String name2="Tobey Maguire";

        when(avengersActors.contains(name1)).thenReturn(true);
        when(avengersActors.contains(name2)).thenReturn(false);

        assertTrue(avengersActors.contains(name1));
        assertFalse(avengersActors.contains(name2));

    }

}
