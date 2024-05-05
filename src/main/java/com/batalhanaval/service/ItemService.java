package com.batalhanaval.service;

import com.batalhanaval.dtos.ItemListModel;
import com.batalhanaval.dtos.ItemModel;
import com.batalhanaval.entity.Item;
import com.batalhanaval.entity.ItemCategoria;
import com.batalhanaval.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCategoriaService itemCategoriaService;

    public ItemService(ItemRepository itemRepository, ItemCategoriaService itemCategoriaService) {
        this.itemRepository = itemRepository;
        this.itemCategoriaService = itemCategoriaService;
    }

    public Item postItem(ItemModel item) {
        ItemCategoria itemCategoria = this.itemCategoriaService.getCategoria(item.getCategoriaId());

        Item itemBuilder = Item.builder()
                .ativo(item.getAtivo())
                .itemCategoria(itemCategoria)
                .descricao(item.getDescricao())
                .nome(item.getNome())
                .tipoPagamento(item.getTipoPagamento())
                .valor(item.getValor())
                .build();
        return this.itemRepository.save(itemBuilder);
    }

    public Item putItem(Item item, Long itemId) throws Exception {
        Item itemDb = this.itemRepository.findById(itemId).orElseThrow(() -> new Exception("Item Não encontrado"));
        ItemCategoria itemCategoria = this.itemCategoriaService.getCategoria(item.getItemCategoria().getId());

        itemDb.setNome(item.getNome());
        itemDb.setDescricao(item.getDescricao());
        itemDb.setTipoPagamento(item.getTipoPagamento());
        itemDb.setValor(item.getValor());
        //itemDb.setImageUrl(item.getImageUrl());
        itemDb.setItemCategoria(itemCategoria);
        itemDb.setAtivo(item.getAtivo());
        return this.itemRepository.save(itemDb);
    }

    public List<ItemListModel> getAllItens() {
        List<Item> itens = this.itemRepository.findAll();
        List<ItemListModel> itemListModels = new ArrayList<>();

        itens.forEach(i -> {
            ItemListModel itemListModel = new ItemListModel();
            itemListModel.setId(i.getId());
            itemListModel.setTitulo(i.getNome());
            itemListModel.setCategoria(i.getItemCategoria().getTitulo());
            itemListModels.add(itemListModel);
        });

        return itemListModels;
    }


    public void delete(Long itemId) {
        this.itemRepository.deleteById(itemId);
    }
}
