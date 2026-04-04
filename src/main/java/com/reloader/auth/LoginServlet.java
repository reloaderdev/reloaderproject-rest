package com.reloader.auth;

import com.reloader.services.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final LoginService service = new LoginService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            JSONObject loginResponse = service.login(username, password);
            int isAuthenticated = loginResponse.optInt("isAuthenticated", 0);

            if (isAuthenticated == 1) {
                HttpSession session = request.getSession();

                JSONObject user = loginResponse.optJSONObject("user");
                JSONObject character = loginResponse.optJSONObject("character");
                JSONObject guild = loginResponse.optJSONObject("guild");

                session.setAttribute("username", user != null ? user.optString("username", username) : username);
                session.setAttribute("displayName", user != null ? user.optString("displayName", "") : "");
                session.setAttribute("email", user != null ? user.optString("email", "") : "");
                session.setAttribute("role", user != null ? user.optString("role", "") : "");
                session.setAttribute("photoUrl", user != null ? user.optString("photoUrl", "") : "");
                session.setAttribute("characterName", character != null ? character.optString("characterName", "") : "");
                session.setAttribute("className", character != null ? character.optString("className", "") : "");
                session.setAttribute("factionName", character != null ? character.optString("factionName", "") : "");
                session.setAttribute("guildName", guild != null ? guild.optString("guildName", "") : "");

                response.sendRedirect("home.jsp");
            } else {
                response.sendRedirect("login.jsp?error=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
