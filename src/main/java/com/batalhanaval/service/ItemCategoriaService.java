package com.batalhanaval.service;

import com.batalhanaval.entity.Item;
import com.batalhanaval.entity.ItemCategoria;
import com.batalhanaval.repository.ItemCategoriaRepository;
import com.batalhanaval.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCategoriaService {

    private final ItemCategoriaRepository itemCategoriaRepository;
    private final ItemRepository itemRepository;

    public ItemCategoriaService(ItemCategoriaRepository itemCategoriaRepository, ItemRepository itemRepository) {
        this.itemCategoriaRepository = itemCategoriaRepository;
        this.itemRepository = itemRepository;
    }

    public ItemCategoria postItemCategoria(ItemCategoria itemCategoria) {
        return this.itemCategoriaRepository.save(itemCategoria);
    }

    public ItemCategoria putItemCategoria(ItemCategoria itemCategoria) {
        ItemCategoria itemCategoriaSalvo = this.itemCategoriaRepository.findById(itemCategoria.getId()).orElse(null);

        if (itemCategoriaSalvo != null) {
            itemCategoriaSalvo.setCategoria(itemCategoria.getCategoria());
            itemCategoriaSalvo.setTitulo(itemCategoria.getTitulo());

            return  this.itemCategoriaRepository.save(itemCategoriaSalvo);
        }

        return null;
    }

    public ItemCategoria getCategoria(Long categoriaId) {
        return this.itemCategoriaRepository.findById(categoriaId).orElse(null);
    }

    public List<ItemCategoria> getAllCategorias() {
        return this.itemCategoriaRepository.findAll();
    }

    public boolean delete(Long categoriaId) {
        Item item = this.itemRepository.findByItemCategoria_Id(categoriaId);

        if (item != null) {
            return false;
        }

        this.itemCategoriaRepository.deleteById(categoriaId);
        return true;
    }
}
