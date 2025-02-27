package com.example.app.repositories;

import com.example.app.models.Dessert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DessertRepository extends JpaRepository<Dessert, Long> {
    
    // Obtener postres por el ID del creador
    List<Dessert> findByCreatorId(Long userId);
    
    // Obtener todos los postres
    List<Dessert> findAll();
}
