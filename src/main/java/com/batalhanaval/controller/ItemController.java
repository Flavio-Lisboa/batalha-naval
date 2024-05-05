package com.batalhanaval.controller;

import com.batalhanaval.dtos.ItemListModel;
import com.batalhanaval.dtos.ItemModel;
import com.batalhanaval.entity.Item;
import com.batalhanaval.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
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
    public ResponseEntity<Item> Post(
            @RequestParam("image") MultipartFile file,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam BigDecimal valor,
            @RequestParam Boolean ativo,
            @RequestParam Long categoriaId,
            @RequestParam String tipoPagamento) throws IOException {

        ItemModel model = ItemModel.builder()
                .tipoPagamento(tipoPagamento)
                .categoriaId(categoriaId)
                .ativo(ativo)
                .valor(valor)
                .descricao(descricao)
                .nome(nome)
                .image(file)
                .build();

        Item itemSalvo = this.itemService.postItem(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvo);
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<Item> Put(
            @RequestParam("image") MultipartFile file,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam BigDecimal valor,
            @RequestParam Boolean ativo,
            @RequestParam Long categoriaId,
            @RequestParam String tipoPagamento,
            @RequestParam Long id) throws Exception {

        ItemModel model = ItemModel.builder()
                .tipoPagamento(tipoPagamento)
                .categoriaId(categoriaId)
                .ativo(ativo)
                .valor(valor)
                .descricao(descricao)
                .nome(nome)
                .image(file)
                .id(id)
                .build();

        Item itemSalvo = this.itemService.putItem(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvo);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<ItemListModel>> getAllItens() {
        List<ItemListModel> itemListModels = this.itemService.getAllItens();

        return ResponseEntity.ok(itemListModels);
    }

    @CrossOrigin
    @GetMapping("{itemId}")
    public ResponseEntity<ItemModel> getItem(@PathVariable Long itemId) {
        ItemModel itemModel = this.itemService.getItem(itemId);

        return ResponseEntity.ok(itemModel);
    }

    @CrossOrigin
    @DeleteMapping("{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        this.itemService.delete(itemId);
    }
}


