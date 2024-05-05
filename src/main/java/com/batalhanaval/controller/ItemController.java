package com.batalhanaval.controller;

import com.batalhanaval.dtos.ItemListModel;
import com.batalhanaval.dtos.ItemModel;
import com.batalhanaval.entity.Item;
import com.batalhanaval.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Item> Post(@RequestBody ItemModel item) {
        Item itemSalvo = this.itemService.postItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvo);
    }

    @PutMapping("{itemId}")
    public ResponseEntity<Item> Put(@RequestBody Item item, @PathVariable Long itemId) throws Exception {
        item = this.itemService.putItem(item, itemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<ItemListModel>> getAllItens() {
        List<ItemListModel> itemListModels = this.itemService.getAllItens();

        return ResponseEntity.ok(itemListModels);
    }

    @CrossOrigin
    @DeleteMapping("{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        this.itemService.delete(itemId);
    }
}


