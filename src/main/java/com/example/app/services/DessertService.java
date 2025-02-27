package com.example.app.services;

import com.example.app.models.Dessert;
import com.example.app.models.User;
import com.example.app.repositories.DessertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DessertService {

    @Autowired
    private DessertRepository dessertRepository;

    // Obtener todos los postres
    public List<Dessert> getAllDesserts() {
        return dessertRepository.findAll();
    }

    // Obtener un postre por ID
    public Optional<Dessert> getDessertById(Long id) {
        return dessertRepository.findById(id);
    }

    // Guardar un nuevo postre
    public Dessert createDessert(Dessert dessert) {
        return dessertRepository.save(dessert);
    }

    // Obtener los postres creados por un usuario específico
    public List<Dessert> getDessertsByUser(User user) {
        return dessertRepository.findByCreatorId(user.getId());
    }

    public Dessert updateDessert(Dessert dessert) {
        return dessertRepository.save(dessert);
    }
}
