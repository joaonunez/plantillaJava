package com.example.app.services;

import com.example.app.models.Favorite;
import com.example.app.models.Dessert;
import com.example.app.models.User;
import com.example.app.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    // Verificar si un usuario ya ha agregado un postre como favorito
    public boolean isFavorite(User user, Dessert dessert) {
        return favoriteRepository.findByUserAndDessert(user, dessert).isPresent();
    }

    // Agregar un postre a favoritos
    public void addFavorite(User user, Dessert dessert) {
        if (!isFavorite(user, dessert)) {
            Favorite favorite = new Favorite(user, dessert);
            favoriteRepository.save(favorite);
        }
    }

    // Eliminar un postre de favoritos
    public void removeFavorite(User user, Dessert dessert) {
        favoriteRepository.deleteByUserAndDessert(user, dessert);
    }

    // Obtener todos los postres favoritos de un usuario
    public List<Favorite> getFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user);
    }
}
