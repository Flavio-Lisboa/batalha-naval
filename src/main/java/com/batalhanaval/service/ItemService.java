package com.batalhanaval.service;

import com.batalhanaval.entity.Item;
import com.batalhanaval.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ItemService {


    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item postItem(Item item) {
        return this.itemRepository.save(item);
    }

    public Item putItem(Item item, Long itemId) throws Exception {
        Item itemDb = this.itemRepository.findById(itemId).orElseThrow(() ->new Exception("Item Não encontrado"));

        itemDb.setNome(item.getNome());
        itemDb.setDesc(item.getDesc());
        itemDb.setPrecoMoeda(item.getPrecoMoeda());
        itemDb.setPrecoDiamante(item.getPrecoDiamante());
        itemDb.setImageUrl(item.getImageUrl());
        return this.itemRepository.save(itemDb);
    }

}
