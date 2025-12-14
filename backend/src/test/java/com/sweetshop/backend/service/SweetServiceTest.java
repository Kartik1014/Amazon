package com.sweetshop.backend.service;

import com.sweetshop.backend.model.Sweet;
import com.sweetshop.backend.repository.SweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private SweetService sweetService;

    private Sweet testSweet;

    @BeforeEach
    void setUp() {
        testSweet = new Sweet();
        testSweet.setId(1L);
        testSweet.setName("Chocolate Bar");
        testSweet.setCategory("Chocolate");
        testSweet.setPrice(2.50);
        testSweet.setQuantity(100);
    }

    @Test
    void testCreateSweet() {
        when(sweetRepository.save(any(Sweet.class))).thenReturn(testSweet);

        Sweet created = sweetService.createSweet(testSweet);

        assertNotNull(created);
        assertEquals("Chocolate Bar", created.getName());
        verify(sweetRepository, times(1)).save(testSweet);
    }

    @Test
    void testGetAllSweets() {
        List<Sweet> sweets = Arrays.asList(testSweet);
        when(sweetRepository.findAll()).thenReturn(sweets);

        List<Sweet> result = sweetService.getAllSweets();

        assertEquals(1, result.size());
        assertEquals("Chocolate Bar", result.get(0).getName());
    }

    @Test
    void testGetSweetById() {
        when(sweetRepository.findById(anyLong())).thenReturn(Optional.of(testSweet));

        Sweet found = sweetService.getSweetById(1L);

        assertNotNull(found);
        assertEquals("Chocolate Bar", found.getName());
    }

    @Test
    void testGetSweetById_NotFound() {
        when(sweetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> sweetService.getSweetById(1L));
    }

    @Test
    void testUpdateSweet() {
        Sweet updatedSweet = new Sweet();
        updatedSweet.setName("Updated Chocolate");
        updatedSweet.setCategory("Chocolate");
        updatedSweet.setPrice(3.00);
        updatedSweet.setQuantity(150);

        when(sweetRepository.findById(anyLong())).thenReturn(Optional.of(testSweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(updatedSweet);

        Sweet result = sweetService.updateSweet(1L, updatedSweet);

        assertNotNull(result);
        assertEquals("Updated Chocolate", result.getName());
        assertEquals(3.00, result.getPrice());
    }

    @Test
    void testDeleteSweet() {
        when(sweetRepository.findById(anyLong())).thenReturn(Optional.of(testSweet));
        doNothing().when(sweetRepository).deleteById(anyLong());

        sweetService.deleteSweet(1L);

        verify(sweetRepository, times(1)).deleteById(1L);
    }

    @Test
    void testPurchaseSweet() {
        when(sweetRepository.findById(anyLong())).thenReturn(Optional.of(testSweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(testSweet);

        Sweet result = sweetService.purchaseSweet(1L, 10);

        assertNotNull(result);
        assertEquals(90, result.getQuantity());
        verify(sweetRepository, times(1)).save(testSweet);
    }

    @Test
    void testPurchaseSweet_InsufficientQuantity() {
        when(sweetRepository.findById(anyLong())).thenReturn(Optional.of(testSweet));

        assertThrows(RuntimeException.class, () -> sweetService.purchaseSweet(1L, 200));
    }

    @Test
    void testRestockSweet() {
        when(sweetRepository.findById(anyLong())).thenReturn(Optional.of(testSweet));
        when(sweetRepository.save(any(Sweet.class))).thenReturn(testSweet);

        Sweet result = sweetService.restockSweet(1L, 50);

        assertNotNull(result);
        assertEquals(150, result.getQuantity());
        verify(sweetRepository, times(1)).save(testSweet);
    }

    @Test
    void testSearchSweets() {
        List<Sweet> sweets = Arrays.asList(testSweet);
        when(sweetRepository.searchSweets(any(), any(), any(), any())).thenReturn(sweets);

        List<Sweet> result = sweetService.searchSweets("Chocolate", null, null, null);

        assertEquals(1, result.size());
        assertEquals("Chocolate Bar", result.get(0).getName());
    }
}
