package com.passwordmanager.model;

/**
 * Representa un usuario en el sistema.
 * Es un POJO (Plain Old Java Object): solo datos, sin lógica compleja.
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password; // Esta será la contraseña hasheada

    // Constructor vacío (necesario para algunas librerías)
    public User() {}

    // Constructor para crear un usuario con datos
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters (los "permisos" para leer y escribir los datos)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
