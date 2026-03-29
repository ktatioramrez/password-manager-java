package com.passwordmanager.dao;

import com.passwordmanager.model.Password;
import com.passwordmanager.util.DatabasePool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordDAO {

    // Crear una nueva credencial
    public boolean save(Password p) throws SQLException {
        String sql = "INSERT INTO passwords (site_name, site_url, username, encrypted_password, category, notes, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getSiteName());
            stmt.setString(2, p.getSiteUrl());
            stmt.setString(3, p.getUsername());
            stmt.setString(4, p.getEncryptedPassword());
            stmt.setString(5, p.getCategory());
            stmt.setString(6, p.getNotes());
            stmt.setInt(7, p.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Listar todas las contraseñas de UN usuario específico
    public List<Password> findAllByUser(int userId) throws SQLException {
        List<Password> list = new ArrayList<>();
        String sql = "SELECT * FROM passwords WHERE user_id = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Password p = new Password();
                p.setId(rs.getInt("id"));
                p.setSiteName(rs.getString("site_name"));
                p.setSiteUrl(rs.getString("site_url"));
                p.setUsername(rs.getString("username"));
                p.setEncryptedPassword(rs.getString("encrypted_password"));
                p.setCategory(rs.getString("category"));
                p.setUserId(userId);
                list.add(p);
            }
        }
        return list;
    }

    // Borrar una credencial (Verificando que pertenezca al usuario)
    public boolean delete(int id, int userId) throws SQLException {
        String sql = "DELETE FROM passwords WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabasePool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }
    }
}