package com.example.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El Nombre no puede ir vacio")
    @Size(min = 2, max = 50, message = "Por lo menos necesitas 2 caracteres en nombre ")
    private String firstName;

    @NotNull(message = "Apellido no puede ir vacio")
    @Size(min = 2, max = 50, message = "Por lo menos necesitas 2 caracteres en apellido")
    private String lastName;

    @NotNull(message = "El email no puede ir vacio")
    @Email(message = "Formato invalido de correo")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Contraseña requerida")
    @Size(min = 8, message = "como minimo necesitas 8 caracteres")
    private String password;

    @Transient 
    @NotNull(message = "Por favor confirma tu contraseña")
    private String confirmPassword;

    public User() {}

    public User(String firstName, String lastName, String email, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
