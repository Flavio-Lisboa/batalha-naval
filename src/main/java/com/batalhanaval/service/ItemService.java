package com.batalhanaval.service;

import com.batalhanaval.entity.Item;
import com.batalhanaval.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item saveItem(Item item) {
        return this.itemRepository.save(item);
    }

    public Item getItem(Long itemId) {
        return this.itemRepository.findById(itemId).orElse(null);
    }

    public List<Item> getItens() {
        return this.itemRepository.findAll();
    }

    public void deleteItem(Long itemId) {
        this.itemRepository.deleteById(itemId);
    }
}
