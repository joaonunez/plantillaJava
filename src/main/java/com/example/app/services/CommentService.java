package com.example.app.services;

import com.example.app.models.Comment;
import com.example.app.models.Dessert;
import com.example.app.models.User;
import com.example.app.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Obtener comentarios por postre
    public List<Comment> getCommentsByDessert(Dessert dessert) {
        return commentRepository.findByDessert(dessert);
    }

    // Publicar comentario
    public void addComment(User author, String content, Dessert dessert) {
        Comment comment = new Comment(author, content, dessert);
        commentRepository.save(comment);
    }
    
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    // Eliminar comentario
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
