package com.example.app.repositories;

import com.example.app.models.Favorite;
import com.example.app.models.Dessert;
import com.example.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    Optional<Favorite> findByUserAndDessert(User user, Dessert dessert);

    void deleteByUserAndDessert(User user, Dessert dessert);

    List<Favorite> findByUser(User user);
}
