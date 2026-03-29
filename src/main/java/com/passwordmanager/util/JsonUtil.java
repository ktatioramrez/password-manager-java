package com.passwordmanager.util;

import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtil {

    public static void ok(HttpServletResponse res, String message) throws IOException {
        send(res, 200, "success", message);
    }

    public static void error(HttpServletResponse res, int status, String message) throws IOException {
        send(res, status, "error", message);
    }

    private static void send(HttpServletResponse res, int status, String type, String message) throws IOException {
        res.setStatus(status);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        JSONObject json = new JSONObject();
        json.put("status", type);
        json.put("message", message);
        out.print(json.toString());
        out.flush();
    }
}
