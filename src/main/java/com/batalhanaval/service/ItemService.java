package com.batalhanaval.service;

import com.batalhanaval.dtos.ItemListModel;
import com.batalhanaval.dtos.ItemModel;
import com.batalhanaval.entity.Item;
import com.batalhanaval.entity.ItemCategoria;
import com.batalhanaval.repository.ItemRepository;
import com.batalhanaval.util.ImageUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCategoriaService itemCategoriaService;

    public ItemService(ItemRepository itemRepository, ItemCategoriaService itemCategoriaService) {
        this.itemRepository = itemRepository;
        this.itemCategoriaService = itemCategoriaService;
    }

    public Item postItem(ItemModel item) throws IOException {
        ItemCategoria itemCategoria = this.itemCategoriaService.getCategoria(item.getCategoriaId());

        Item itemBuilder = Item.builder()
                .ativo(item.getAtivo())
                .itemCategoria(itemCategoria)
                .descricao(item.getDescricao())
                .nome(item.getNome())
                .tipoPagamento(item.getTipoPagamento())
                .valor(item.getValor())
                .image(ImageUtil.compressImage(item.getImage().getBytes()))
                .build();
        return this.itemRepository.save(itemBuilder);
    }

    public Item putItem(ItemModel item) throws Exception {
        Item itemDb = this.itemRepository.findById(item.getId()).orElseThrow(() -> new Exception("Item Não encontrado"));
        ItemCategoria itemCategoria = this.itemCategoriaService.getCategoria(item.getCategoriaId());

        itemDb.setNome(item.getNome());
        itemDb.setDescricao(item.getDescricao());
        itemDb.setTipoPagamento(item.getTipoPagamento());
        itemDb.setValor(item.getValor());
        itemDb.setImage(ImageUtil.compressImage(item.getImage().getBytes()));
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

            if (i.getImage() != null) {
                byte[] imageData = ImageUtil.decompressImage(i.getImage());
                String base64 = Base64.getEncoder().encodeToString(imageData);

                itemListModel.setImageData(base64);
            }

            itemListModels.add(itemListModel);
        });

        return itemListModels;
    }

    public ItemModel getItem(Long itemId) {
        Item item = this.itemRepository.findById(itemId).orElse(null);

        if (item == null) return null;

        String base64 = "";
        if (item.getImage() != null) {
            byte[] imageData = ImageUtil.decompressImage(item.getImage());
            base64 = Base64.getEncoder().encodeToString(imageData);
        }

        return ItemModel.builder()
                .id(item.getId())
                .tipoPagamento(item.getTipoPagamento())
                .categoriaId(item.getItemCategoria().getId())
                .ativo(item.getAtivo())
                .valor(item.getValor())
                .descricao(item.getDescricao())
                .nome(item.getNome())
                .imageData(base64)
                .build();
    }

    public void delete(Long itemId) {
        this.itemRepository.deleteById(itemId);
    }
}
