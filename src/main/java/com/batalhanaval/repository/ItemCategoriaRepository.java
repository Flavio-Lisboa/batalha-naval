package com.batalhanaval.repository;

import com.batalhanaval.entity.ItemCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCategoriaRepository extends JpaRepository<ItemCategoria, Long> {
}

