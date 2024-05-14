package com.batalhanaval.repository;

import com.batalhanaval.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Boolean existsAvatarByNome(String nome);
    Boolean existsAvatarByNomeAndTema_Id(String nome, Long temaId);
    Avatar findByTema_Id(Long temaId);
}
