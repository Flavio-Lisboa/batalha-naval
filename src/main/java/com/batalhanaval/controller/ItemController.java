package com.batalhanaval.controller;

import com.batalhanaval.entity.Item;
import com.batalhanaval.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Item> Post(@RequestBody Item item) {
        item = this.itemService.postItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("{itemId}")
    public ResponseEntity<Item> Put(@RequestBody Item item, @PathVariable Long itemId) throws Exception {
        item = this.itemService.putItem(item, itemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }







}


