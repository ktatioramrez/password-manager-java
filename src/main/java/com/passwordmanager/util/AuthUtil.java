package com.passwordmanager.util;

import org.mindrot.jbcrypt.BCrypt;

public class AuthUtil {
    
    // Convierte "MiClave123" en algo como "$2a$10$..."
    public static String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    // Compara la clave que escribe el usuario con la que está guardada en la DB
    public static boolean checkPassword(String plainText, String hashed) {
        try {
            return BCrypt.checkpw(plainText, hashed);
        } catch (Exception e) {
            return false;
        }
    }
}