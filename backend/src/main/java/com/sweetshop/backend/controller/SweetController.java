package com.sweetshop.backend.controller;

import com.sweetshop.backend.model.Sweet;
import com.sweetshop.backend.service.SweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sweets")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class SweetController {

    @Autowired
    private SweetService sweetService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSweet(@Valid @RequestBody Sweet sweet) {
        try {
            Sweet created = sweetService.createSweet(sweet);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Sweet>> getAllSweets() {
        List<Sweet> sweets = sweetService.getAllSweets();
        return ResponseEntity.ok(sweets);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Sweet>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        List<Sweet> sweets = sweetService.searchSweets(name, category, minPrice, maxPrice);
        return ResponseEntity.ok(sweets);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getSweetById(@PathVariable Long id) {
        try {
            Sweet sweet = sweetService.getSweetById(id);
            return ResponseEntity.ok(sweet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSweet(@PathVariable Long id, @Valid @RequestBody Sweet sweet) {
        try {
            Sweet updated = sweetService.updateSweet(id, sweet);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSweet(@PathVariable Long id) {
        try {
            sweetService.deleteSweet(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> purchaseSweet(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            if (quantity == null || quantity <= 0) {
                return ResponseEntity.badRequest().body("Invalid quantity");
            }
            Sweet sweet = sweetService.purchaseSweet(id, quantity);
            return ResponseEntity.ok(sweet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> restockSweet(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            if (quantity == null || quantity <= 0) {
                return ResponseEntity.badRequest().body("Invalid quantity");
            }
            Sweet sweet = sweetService.restockSweet(id, quantity);
            return ResponseEntity.ok(sweet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
