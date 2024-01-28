package com.naga.howtodoinjava.controllers;

import com.naga.howtodoinjava.exceptions.ErrorResponse;
import com.naga.howtodoinjava.exceptions.ItemNotFoundException;
import com.naga.howtodoinjava.persistences.ItemPersistence;
import com.naga.howtodoinjava.repositories.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping(path = "/items")
    List<ItemPersistence> all(){
        return itemRepository.findAll();
    }

    @PostMapping(path = "/items")
    ItemPersistence createNew(@Valid @RequestBody ItemPersistence newItem){
        return itemRepository.save(newItem);
    }

    @GetMapping(path = "/items/{id}")
    public ResponseEntity<Object> getItem(@PathVariable Long id){
        ItemPersistence item = itemRepository.findById(id).orElse(null);
        if (item == null){
            String error_message = "Item with ID " + id + " not found.";
            ErrorResponse errorResponse = new ErrorResponse(error_message);
            return new ResponseEntity<>(errorResponse ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping(path = "/items/{id}")
    ItemPersistence editItem(@PathVariable Long id, @Valid @RequestBody ItemPersistence newItem){
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    return itemRepository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return itemRepository.save(newItem);
                });
    }

    @DeleteMapping(path = "/items/{id}")
    void deleteItem(@PathVariable Long id){
        itemRepository.deleteById(id);
    }
}
