package com.batalhanaval.service;

import com.batalhanaval.entity.ItemCategoria;
import com.batalhanaval.repository.ItemCategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemCategoriaService {

    private final ItemCategoriaRepository itemCategoriaRepository;

    public ItemCategoriaService(ItemCategoriaRepository itemCategoriaRepository) {
        this.itemCategoriaRepository = itemCategoriaRepository;
    }

    public ItemCategoria postItemCategoria(ItemCategoria itemCategoria) {
        return this.itemCategoriaRepository.save(itemCategoria);
    }
}
