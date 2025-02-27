package com.example.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "dessert")
public class Dessert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "La URL de la imagen no puede estar vacía")
    @Column(nullable = false)
    private String imageUrl;

    @Min(value = 1, message = "El tiempo de preparación debe ser al menos 1 minuto")
    @Column(nullable = false)
    private int preparationTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "dessert")
    @JsonIgnore
    private List<Like> likes;

    @ElementCollection
    @CollectionTable(name = "dessert_ingredients", joinColumns = @JoinColumn(name = "dessert_id"))
    @MapKeyColumn(name = "ingredient_name")
    @Column(name = "quantity")
    @NotEmpty(message = "Debe agregar al menos un ingrediente")
    private Map<String, String> ingredients;

    // Agregar atributo para favoritos (NO se guarda en la base de datos)
    @Transient
    private boolean isFavorite;

    public Dessert() {
    }

    public Dessert(String name, String description, String imageUrl, int preparationTime, User creator,
            Map<String, String> ingredients) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.preparationTime = preparationTime;
        this.creator = creator;
        this.ingredients = ingredients;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
