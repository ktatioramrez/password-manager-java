package com.passwordmanager.servlet;

import com.passwordmanager.dao.UserDAO;
import com.passwordmanager.model.User;
import com.passwordmanager.util.AuthUtil;
import com.passwordmanager.util.JsonUtil;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/api/auth/*")
public class AuthServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getPathInfo();
        
        // Si el path es nulo, le asignamos vacío para evitar errores
        if (path == null) path = "";

        // Usamos contains para que sea flexible con las barras diagonales (Evita el Error 405)
        if (path.contains("register")) {
            handleRegister(req, res);
        } else if (path.contains("login")) {
            handleLogin(req, res);
        } else {
            JsonUtil.error(res, 404, "Ruta no encontrada: " + path);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            // Leer el cuerpo de la petición JSON
            String body = req.getReader().lines().collect(Collectors.joining());
            if (body.isEmpty()) {
                JsonUtil.error(res, 400, "El cuerpo de la solicitud está vacío");
                return;
            }
            
            JSONObject json = new JSONObject(body);

            String username = json.optString("username", "");
            String email = json.optString("email", "");
            String password = json.optString("password", "");

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JsonUtil.error(res, 400, "Todos los campos son obligatorios");
                return;
            }

            // Hasheo de seguridad para el usuario (BCrypt) - Requerimiento RNF06
            String hashedPassword = AuthUtil.hashPassword(password);
            User newUser = new User(0, username, email, hashedPassword);
            
            if (userDAO.save(newUser)) {
                JsonUtil.ok(res, "¡Usuario registrado exitosamente!");
            } else {
                JsonUtil.error(res, 400, "Error: El correo ya existe en el sistema.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.error(res, 500, "Error en el registro: " + e.getMessage());
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            String body = req.getReader().lines().collect(Collectors.joining());
            if (body.isEmpty()) {
                JsonUtil.error(res, 400, "Datos de login no proporcionados");
                return;
            }

            JSONObject json = new JSONObject(body);
            String email = json.optString("email", "");
            String password = json.optString("password", "");

            // Buscar usuario en la base de datos
            User user = userDAO.findByEmail(email);

            // Verificar si el usuario existe y la contraseña coincide con el hash
            if (user != null && AuthUtil.checkPassword(password, user.getPassword())) {
                JSONObject responseData = new JSONObject();
                responseData.put("message", "Bienvenido " + user.getUsername());
                responseData.put("userId", user.getId());
                
res.setStatus(200);
res.setContentType("application/json");
res.getWriter().write(responseData.toString());
            } else {
                JsonUtil.error(res, 401, "Correo o contraseña incorrectos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtil.error(res, 500, "Error durante el inicio de sesión");
        }
    }
}