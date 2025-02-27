package com.example.app.services;

import com.example.app.models.Like;
import com.example.app.models.Dessert;
import com.example.app.models.User;
import com.example.app.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    // Contar likes de un postre
    public long countLikes(Dessert dessert) {
        return likeRepository.countByDessert(dessert);
    }

    // Verificar si un usuario ya dio like a un postre
    public boolean hasUserLiked(User user, Dessert dessert) {
        return likeRepository.findByUserAndDessert(user, dessert).isPresent();
    }

    // Dar like a un postre
    public void likeDessert(User user, Dessert dessert) {
        if (!hasUserLiked(user, dessert)) {
            Like like = new Like(user, dessert);
            likeRepository.save(like);
        }
    }

    @Transactional
    public void unlikeDessert(User user, Dessert dessert) {
        likeRepository.deleteByUserAndDessert(user, dessert);
    }

    public List<User> getUsersWhoLiked(Dessert dessert) {
        return likeRepository.findUsersByDessert(dessert);
    }
}
