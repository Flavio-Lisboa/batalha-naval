package com.batalhanaval.repository;

import com.batalhanaval.entity.Embarcacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmbarcacaoRepository extends JpaRepository<Embarcacao, Long> {

    Boolean existsEmbarcacaoByNome(String nome);
    Boolean existsEmbarcacaoByNomeAndTema_Id(String nome, Long temaId);
    Embarcacao findByTema_Id(Long temaId);
}
