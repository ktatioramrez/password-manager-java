package com.passwordmanager.servlet;

import com.passwordmanager.dao.PasswordDAO;
import com.passwordmanager.model.Password;
import com.passwordmanager.util.CryptoUtil;
import com.passwordmanager.util.JsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/passwords/*")
public class PasswordServlet extends HttpServlet {

    private PasswordDAO passwordDAO = new PasswordDAO();

    // 1. LISTAR CREDENCIALES (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // Por ahora usamos el ID 1 de Pepito Pérez (Etapa 6 dice que luego se saca de la sesión)
            int userId = 1; 
            List<Password> passwords = passwordDAO.findAllByUser(userId);

            JSONArray jsonArray = new JSONArray();
            for (Password p : passwords) {
                JSONObject obj = new JSONObject();
                obj.put("id", p.getId());
                obj.put("siteName", p.getSiteName());
                obj.put("siteUrl", p.getSiteUrl());
                obj.put("username", p.getUsername());
                // IMPORTANTE: Aquí podrías descifrarla si quieres mostrarla, 
                // pero por seguridad en la lista solemos mandarla vacía o oculta.
                obj.put("category", p.getCategory());
                jsonArray.put(obj);
            }

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(jsonArray.toString());

        } catch (Exception e) {
            JsonUtil.error(res, 500, "Error al obtener credenciales: " + e.getMessage());
        }
    }

    // 2. GUARDAR CREDENCIAL CIFRADA (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String body = req.getReader().lines().collect(Collectors.joining());
            JSONObject json = new JSONObject(body);

            String siteName = json.optString("siteName", "Desconocido");
            String siteUrl = json.optString("siteUrl", "");
            String username = json.optString("username", "");
            String passwordPlana = json.optString("password", "");
            int userId = json.optInt("userId", 1);

            // APLICAMOS CIFRADO AES-256 (Requerimiento RNF06)
            String passwordCifrada = CryptoUtil.encrypt(passwordPlana);

            Password newPass = new Password(
                0, 
                siteName, 
                siteUrl, 
                username, 
                passwordCifrada, // Guardamos la versión secreta
                "General", 
                "", 
                userId
            );

            if (passwordDAO.save(newPass)) {
                JsonUtil.ok(res, "¡Credencial guardada con éxito y cifrada con AES-256!");
            } else {
                JsonUtil.error(res, 400, "Error: No se pudo guardar en la base de datos.");
            }

        } catch (Exception e) {
            JsonUtil.error(res, 500, "Error en el servidor: " + e.getMessage());
        }
    }

    // 3. ELIMINAR CREDENCIAL (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // Extraemos el ID de la URL: /api/passwords/5
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                JsonUtil.error(res, 400, "ID de credencial no proporcionado.");
                return;
            }

            int id = Integer.parseInt(pathInfo.substring(1));
            int userId = 1; // ID de Pepito

            if (passwordDAO.delete(id, userId)) {
                JsonUtil.ok(res, "Credencial eliminada correctamente.");
            } else {
                JsonUtil.error(res, 404, "No se encontró la credencial o no tienes permiso.");
            }

        } catch (Exception e) {
            JsonUtil.error(res, 500, "Error al eliminar: " + e.getMessage());
        }
    }
}