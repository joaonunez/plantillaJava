package com.example.app.controllers;

import com.example.app.models.User;
import com.example.app.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // Agregar objeto vacio para el formulario
        return "register"; // Renderiza la vista JSP
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
            BindingResult result,
            HttpSession session,
            Model model) {

        if (result.hasErrors()) {
            return "register"; // Si hay errores, vuelve al formulario
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Las contraseñas no coinciden");
            return "register";
        }

        userService.registerUser(user);
        session.setAttribute("user", user);
        session.setAttribute("userName", user.getFirstName());
        return "redirect:/dashboard"; // Redirige al dashboard tras registro exitoso
    }
}
