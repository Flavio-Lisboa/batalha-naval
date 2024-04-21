package com.batalhanaval.controller;

import com.batalhanaval.entity.ItemCategoria;
import com.batalhanaval.service.ItemCategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("itens-categoria")
public class ItemCategoriaController {

    private final ItemCategoriaService itemCategoriaService;


    public ItemCategoriaController(ItemCategoriaService itemCategoriaService) {
        this.itemCategoriaService = itemCategoriaService;
    }

    @PostMapping
    public ResponseEntity<ItemCategoria> post(@RequestBody ItemCategoria itemCategoria) {
        itemCategoria = this.itemCategoriaService.postItemCategoria(itemCategoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemCategoria);
    }
}
