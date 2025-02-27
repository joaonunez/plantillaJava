package com.example.app.controllers;

import com.example.app.models.User;
import com.example.app.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("correo") String email,
            @RequestParam("contrasena") String password,
            HttpSession session,
            Model model) {
        User user = userService.authenticateUser(email, password);
        if (user == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }
        session.setAttribute("user", user);
        session.setAttribute("userName", user.getFirstName());

        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
