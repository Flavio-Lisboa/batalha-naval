package com.batalhanaval.repository;

import com.batalhanaval.entity.Tema;
import com.batalhanaval.entity.UserTema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTemaRepository extends JpaRepository<UserTema, Long> {

    @Query("select ut from UserTema ut where ut.user.id = :userId and ut.tema.id = :temaId")
    Optional<UserTema> findByUserIdAndTemaId(Long userId, Long temaId);

    @Query("select t from UserTema ut join Tema t on t.id = ut.tema.id and ut.user.id = :userId")
    List<Tema> findAllTemasByUserId(Long userId);
}
