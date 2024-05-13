package com.batalhanaval.repository;

import com.batalhanaval.entity.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepository extends JpaRepository<Tema, Long> {

    Boolean existsTemaByNome(String nome);
}
