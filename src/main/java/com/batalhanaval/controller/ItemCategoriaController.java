package com.batalhanaval.controller;

import com.batalhanaval.entity.ItemCategoria;
import com.batalhanaval.repository.ItemCategoriaRepository;
import com.batalhanaval.service.ItemCategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("itens-categoria")
public class ItemCategoriaController {

    private final ItemCategoriaService itemCategoriaService;
    private final ItemCategoriaRepository itemCategoriaRepository;

    public ItemCategoriaController(ItemCategoriaService itemCategoriaService, ItemCategoriaRepository itemCategoriaRepository) {
        this.itemCategoriaService = itemCategoriaService;
        this.itemCategoriaRepository = itemCategoriaRepository;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<ItemCategoria> post(@RequestBody ItemCategoria itemCategoria) {
        itemCategoria = this.itemCategoriaService.postItemCategoria(itemCategoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemCategoria);
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<ItemCategoria> put(@RequestBody ItemCategoria itemCategoria) {
        itemCategoria = this.itemCategoriaService.putItemCategoria(itemCategoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemCategoria);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<ItemCategoria>> getAllCategoria() {
        List<ItemCategoria> categorias = this.itemCategoriaService.getAllCategorias();

        return ResponseEntity.ok(categorias);
    }

    @CrossOrigin
    @GetMapping("{categoriaId}")
    public ItemCategoria getById(@PathVariable Long categoriaId) {
        return this.itemCategoriaRepository.findById(categoriaId).orElse(null);
    }

    @CrossOrigin
    @DeleteMapping("{categoriaId}")
    public boolean deleteCategoria(@PathVariable Long categoriaId) {
        return this.itemCategoriaService.delete(categoriaId);
    }
}
