package com.example.app.controllers;

import com.example.app.models.Dessert;
import com.example.app.models.Favorite;
import com.example.app.models.User;
import com.example.app.services.DessertService;
import com.example.app.services.FavoriteService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private DessertService dessertService;

  
    @GetMapping("")
    public String showFavorites(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Favorite> favorites = favoriteService.getFavoritesByUser(user);
        model.addAttribute("favorites", favorites);

        return "favorites";
    }

  
    @PostMapping("/add/{dessertId}")
    public String addFavorite(@PathVariable Long dessertId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Optional<Dessert> optionalDessert = dessertService.getDessertById(dessertId);
        if (optionalDessert.isPresent()) {
            favoriteService.addFavorite(user, optionalDessert.get());
        }

        return "redirect:/dashboard";
    }

    @DeleteMapping("/remove/{dessertId}")
    @Transactional
    public String removeFavorite(@PathVariable Long dessertId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Optional<Dessert> optionalDessert = dessertService.getDessertById(dessertId);
        if (optionalDessert.isPresent()) {
            favoriteService.removeFavorite(user, optionalDessert.get());
        }

        return "redirect:/favorites";
    }
}
