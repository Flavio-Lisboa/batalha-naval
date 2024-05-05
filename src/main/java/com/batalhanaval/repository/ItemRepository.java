package com.batalhanaval.repository;

import com.batalhanaval.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByItemCategoria_Id(Long categoriaId);
}