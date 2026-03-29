package com.passwordmanager.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabasePool {
    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            
            // CONFIGURACIÓN MANUAL DIRECTA
            config.setJdbcUrl("jdbc:mysql://localhost:3306/password_manager");
            config.setUsername("root"); 
            config.setPassword("cloe2025"); // <--- ¡MUY IMPORTANTE!
            
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setPoolName("PasswordManagerPool");

            // Propiedades de optimización
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);
            System.out.println("✅ Pool de conexiones iniciado manualmente");
        } catch (Exception e) {
            System.err.println("ERROR crítico al iniciar el pool: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("El DataSource no pudo iniciarse. Revisa la contraseña en DatabasePool.java");
        }
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}