package com.sweetshop.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SweetTest {

    @Test
    void testSweetCreation() {
        Sweet sweet = new Sweet();
        sweet.setName("Chocolate Bar");
        sweet.setCategory("Chocolate");
        sweet.setPrice(2.50);
        sweet.setQuantity(100);
        
        assertEquals("Chocolate Bar", sweet.getName());
        assertEquals("Chocolate", sweet.getCategory());
        assertEquals(2.50, sweet.getPrice());
        assertEquals(100, sweet.getQuantity());
    }

    @Test
    void testSweetWithId() {
        Sweet sweet = new Sweet();
        sweet.setId(1L);
        sweet.setName("Gummy Bears");
        
        assertEquals(1L, sweet.getId());
        assertEquals("Gummy Bears", sweet.getName());
    }

    @Test
    void testSweetQuantityUpdate() {
        Sweet sweet = new Sweet();
        sweet.setQuantity(50);
        assertEquals(50, sweet.getQuantity());
        
        sweet.setQuantity(sweet.getQuantity() - 10);
        assertEquals(40, sweet.getQuantity());
    }

    @Test
    void testSweetPriceUpdate() {
        Sweet sweet = new Sweet();
        sweet.setPrice(1.99);
        assertEquals(1.99, sweet.getPrice());
        
        sweet.setPrice(2.49);
        assertEquals(2.49, sweet.getPrice());
    }

    @Test
    void testSweetAllFields() {
        Sweet sweet = new Sweet();
        sweet.setId(1L);
        sweet.setName("Lollipop");
        sweet.setCategory("Hard Candy");
        sweet.setPrice(0.99);
        sweet.setQuantity(200);
        
        assertEquals(1L, sweet.getId());
        assertEquals("Lollipop", sweet.getName());
        assertEquals("Hard Candy", sweet.getCategory());
        assertEquals(0.99, sweet.getPrice());
        assertEquals(200, sweet.getQuantity());
    }
}
