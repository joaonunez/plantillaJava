package com.example.app.repositories;

import com.example.app.models.Comment;
import com.example.app.models.Dessert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByDessert(Dessert dessert);
}
