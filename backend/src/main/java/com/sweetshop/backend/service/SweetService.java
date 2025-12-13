package com.sweetshop.backend.service;

import com.sweetshop.backend.model.Sweet;
import com.sweetshop.backend.repository.SweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SweetService {

    @Autowired
    private SweetRepository sweetRepository;

    public Sweet createSweet(Sweet sweet) {
        return sweetRepository.save(sweet);
    }

    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

    public Sweet getSweetById(Long id) {
        return sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));
    }

    public Sweet updateSweet(Long id, Sweet sweetDetails) {
        Sweet sweet = getSweetById(id);
        sweet.setName(sweetDetails.getName());
        sweet.setCategory(sweetDetails.getCategory());
        sweet.setPrice(sweetDetails.getPrice());
        sweet.setQuantity(sweetDetails.getQuantity());
        return sweetRepository.save(sweet);
    }

    public void deleteSweet(Long id) {
        Sweet sweet = getSweetById(id);
        sweetRepository.deleteById(id);
    }

    public Sweet purchaseSweet(Long id, Integer quantity) {
        Sweet sweet = getSweetById(id);
        if (sweet.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity available");
        }
        sweet.setQuantity(sweet.getQuantity() - quantity);
        return sweetRepository.save(sweet);
    }

    public Sweet restockSweet(Long id, Integer quantity) {
        Sweet sweet = getSweetById(id);
        sweet.setQuantity(sweet.getQuantity() + quantity);
        return sweetRepository.save(sweet);
    }

    public List<Sweet> searchSweets(String name, String category, Double minPrice, Double maxPrice) {
        return sweetRepository.searchSweets(name, category, minPrice, maxPrice);
    }
}
