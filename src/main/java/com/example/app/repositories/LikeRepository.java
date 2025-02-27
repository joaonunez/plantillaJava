package com.example.app.repositories;

import com.example.app.models.Like;
import com.example.app.models.Dessert;
import com.example.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    
    // Contar el número de likes en un postre específico
    long countByDessert(Dessert dessert);

    // Buscar si un usuario ya le ha dado like a un postre
    Optional<Like> findByUserAndDessert(User user, Dessert dessert);

    @Modifying
    @Transactional
    @Query("DELETE FROM Like l WHERE l.user = :user AND l.dessert = :dessert")
    void deleteByUserAndDessert(@Param("user") User user, @Param("dessert") Dessert dessert);

    //  Metodo para obtener los usuarios que dieron like a un postre
    @Query("SELECT l.user FROM Like l WHERE l.dessert = :dessert")
    List<User> findUsersByDessert(Dessert dessert);
}
