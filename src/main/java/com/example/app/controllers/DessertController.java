package com.example.app.controllers;

import com.example.app.models.Comment;
import com.example.app.models.Dessert;
import com.example.app.models.User;
import com.example.app.services.CommentService;
import com.example.app.services.DessertService;
import com.example.app.services.FavoriteService;
import com.example.app.services.LikeService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class DessertController {

    @Autowired
    private DessertService dessertService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        List<Dessert> desserts = dessertService.getAllDesserts();

        // Verificar favoritos para cada postre
        for (Dessert dessert : desserts) {
            boolean isFav = favoriteService.isFavorite(user, dessert);
            dessert.setFavorite(isFav);
        }

        model.addAttribute("desserts", desserts);
        model.addAttribute("loggedUser", user);

        return "dashboard";
    }

    // Mostrar los postres creados por el usuario
    @GetMapping("/my-desserts")
    public String showMyDesserts(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Dessert> myDesserts = dessertService.getDessertsByUser(user);
        model.addAttribute("myDesserts", myDesserts);

        return "myDesserts";
    }

    // Mostrar formulario para agregar un postre
    @GetMapping("/add-dessert")
    public String showCreateForm(Model model) {
        model.addAttribute("dessert", new Dessert());
        return "dessertForm";
    }

    // Guardar un nuevo postre
    @PostMapping("/add-dessert")
    public String createDessert(
            @RequestParam("name") @NotBlank(message = "El nombre no puede estar vacío") String name,
            @RequestParam("description") @NotBlank(message = "La descripción no puede estar vacía") String description,
            @RequestParam("imageUrl") @NotBlank(message = "Debe ingresar una URL de imagen válida") String imageUrl,
            @RequestParam("preparationTime") @Min(value = 1, message = "El tiempo de preparación debe ser al menos 1 minuto") int preparationTime,
            @RequestParam(value = "ingredientKeys", required = false) List<String> keys,
            @RequestParam(value = "ingredientValues", required = false) List<String> values,
            HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Validar ingredientes
        if (keys == null || values == null || keys.isEmpty() || values.isEmpty()) {
            model.addAttribute("error", "Debe agregar al menos un ingrediente.");
            return "dessertForm";
        }

        Map<String, String> ingredients = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).isBlank() || values.get(i).isBlank()) {
                model.addAttribute("error", "Los ingredientes no pueden estar vacíos.");
                return "dessertForm";
            }
            ingredients.put(keys.get(i), values.get(i));
        }

        Dessert dessert = new Dessert(name, description, imageUrl, preparationTime, user, ingredients);
        dessertService.createDessert(dessert);

        return "redirect:/dashboard";
    }

    // Endpoint para mostrar el formulario de edición de un postre
    @GetMapping("/edit-dessert/{id}")
    public String showEditForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Optional<Dessert> optionalDessert = dessertService.getDessertById(id);
        if (optionalDessert.isEmpty()) {
            return "redirect:/dashboard";
        }

        Dessert dessert = optionalDessert.get();

        // Verificar si el usuario en sesión es el creador del postre
        if (!dessert.getCreator().getId().equals(user.getId())) {
            session.invalidate(); // Cierra la sesión
            return "redirect:/login";
        }

        //  Si los ingredientes son `null`, inicializarlos para evitar errores
        if (dessert.getIngredients() == null) {
            dessert.setIngredients(new HashMap<>());
        }

        // Convertir `Map<String, String>` a `List<Map<String, String>>` para que el
        // JSP pueda iterarlo
        List<Map<String, String>> ingredientList = new ArrayList<>();
        for (Map.Entry<String, String> entry : dessert.getIngredients().entrySet()) {
            Map<String, String> map = new HashMap<>();
            map.put("key", entry.getKey());
            map.put("value", entry.getValue());
            ingredientList.add(map);
        }

        // Pasar los datos a la vista
        model.addAttribute("dessert", dessert);
        model.addAttribute("ingredientList", ingredientList);
        return "editDessert"; // Renderiza la vista de edición
    }

    // Actualizar un postre existente
    @PatchMapping("/edit-dessert/{id}")
    public String updateDessert(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            @RequestParam(value = "preparationTime", required = false) Integer preparationTime,
            @RequestParam(value = "ingredientKeys", required = false) List<String> keys,
            @RequestParam(value = "ingredientValues", required = false) List<String> values,
            HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        Optional<Dessert> optionalDessert = dessertService.getDessertById(id);
        if (optionalDessert.isEmpty())
            return "redirect:/dashboard";

        Dessert dessert = optionalDessert.get();

        if (!dessert.getCreator().getId().equals(user.getId())) {
            session.invalidate();
            return "redirect:/login";
        }

        // Validar ingredientes si se están enviando
        if (keys != null && values != null) {
            if (keys.isEmpty() || values.isEmpty()) {
                model.addAttribute("error", "Debe agregar al menos un ingrediente.");
                model.addAttribute("dessert", dessert);
                model.addAttribute("ingredientList", convertIngredientsToList(dessert.getIngredients()));
                return "editDessert";
            }

            Map<String, String> ingredients = new HashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                if (keys.get(i).isBlank() || values.get(i).isBlank()) {
                    model.addAttribute("error", "Los ingredientes no pueden estar vacíos.");
                    model.addAttribute("dessert", dessert);
                    model.addAttribute("ingredientList", convertIngredientsToList(dessert.getIngredients()));
                    return "editDessert";
                }
                ingredients.put(keys.get(i), values.get(i));
            }
            dessert.setIngredients(ingredients);
        }

        // Solo actualizar si los campos no son nulos y no están vacíos
        if (name != null && !name.isBlank())
            dessert.setName(name);
        if (description != null && !description.isBlank())
            dessert.setDescription(description);
        if (imageUrl != null && !imageUrl.isBlank())
            dessert.setImageUrl(imageUrl);
        if (preparationTime != null && preparationTime >= 1)
            dessert.setPreparationTime(preparationTime);

        dessertService.updateDessert(dessert);
        return "redirect:/dashboard";
    }

    // Método para convertir Map<String, String> a List<Map<String, String>>
    private List<Map<String, String>> convertIngredientsToList(Map<String, String> ingredients) {
        List<Map<String, String>> ingredientList = new ArrayList<>();
        if (ingredients != null) {
            for (Map.Entry<String, String> entry : ingredients.entrySet()) {
                Map<String, String> map = new HashMap<>();
                map.put("key", entry.getKey());
                map.put("value", entry.getValue());
                ingredientList.add(map);
            }
        }
        return ingredientList;
    }

    @GetMapping("/dessert-detail/{id}")
    public String showDessertDetail(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        Optional<Dessert> optionalDessert = dessertService.getDessertById(id);
        if (optionalDessert.isEmpty())
            return "redirect:/dashboard";

        Dessert dessert = optionalDessert.get();

        // Contar likes y verificar si el usuario ya dio like
        long likeCount = likeService.countLikes(dessert);
        boolean hasLiked = likeService.hasUserLiked(user, dessert);

        // Obtener la lista de usuarios que dieron like
        List<User> likedUsers = likeService.getUsersWhoLiked(dessert);

        // Obtener comentarios del postre
        List<Comment> comments = commentService.getCommentsByDessert(dessert);

        model.addAttribute("dessert", dessert);
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("hasLiked", hasLiked);
        model.addAttribute("likedUsers", likedUsers);
        model.addAttribute("comments", comments);
        model.addAttribute("loggedUser", user);

        return "dessertDetail";
    }

    //  Dar like a un postre
    @PostMapping("/dessert/{id}/like")
    public String likeDessert(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        Optional<Dessert> optionalDessert = dessertService.getDessertById(id);
        if (optionalDessert.isEmpty())
            return "redirect:/dashboard";

        Dessert dessert = optionalDessert.get();
        likeService.likeDessert(user, dessert);

        return "redirect:/dessert-detail/" + id;
    }

    @DeleteMapping("/dessert/{id}/unlike")
    public String unlikeDessert(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        Optional<Dessert> optionalDessert = dessertService.getDessertById(id);
        if (optionalDessert.isEmpty())
            return "redirect:/dashboard";

        Dessert dessert = optionalDessert.get();
        likeService.unlikeDessert(user, dessert);

        return "redirect:/dessert-detail/" + id;
    }

    @PostMapping("/dessert/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam("content") String content, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        Optional<Dessert> optionalDessert = dessertService.getDessertById(id);
        if (optionalDessert.isPresent()) {
            commentService.addComment(user, content, optionalDessert.get()); //  Pasamos el objeto User
        }

        return "redirect:/dessert-detail/" + id;
    }

    @DeleteMapping("/dessert/comment/{commentId}/delete")
@Transactional
public String deleteComment(@PathVariable Long commentId, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null)
        return "redirect:/login";

    Optional<Comment> optionalComment = commentService.getCommentById(commentId);

    if (optionalComment.isPresent()) {
        Comment comment = optionalComment.get();
        Long dessertId = comment.getDessert().getId();

        if (comment.getAuthor().getId().equals(user.getId())) {
            commentService.deleteComment(commentId);
        }
        return "redirect:/dessert-detail/" + dessertId;
    }

    // Si el comentario no existe, redirigir al dashboard
    return "redirect:/dashboard";
}


}
