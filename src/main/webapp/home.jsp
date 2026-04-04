<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%
    String user = (String) session.getAttribute("username");
    String displayName = (String) session.getAttribute("displayName");
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");
    String characterName = (String) session.getAttribute("characterName");
    String className = (String) session.getAttribute("className");
    String factionName = (String) session.getAttribute("factionName");
    String guildName = (String) session.getAttribute("guildName");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reloader - Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1e1e2f;
            color: white;
            margin: 0;
            padding: 0;
        }
        .header {
            background-color: #2c2c3e;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .content {
            padding: 40px;
        }
        .logout-btn {
            background-color: #ff4d4d;
            border: none;
            padding: 8px 15px;
            color: white;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }
        .logout-btn:hover {
            background-color: #e60000;
        }
        .grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 16px;
            margin-top: 24px;
        }
        .card {
            background: #2c2c3e;
            border-radius: 8px;
            padding: 20px;
        }
        .label {
            color: #b7b7c9;
            font-size: 13px;
            margin-bottom: 6px;
        }
        .value {
            font-size: 18px;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="header">
    <h2>Reloader</h2>
    <div>
        Usuario: <strong><%= user %></strong>
        &nbsp;&nbsp;
        <a href="logout" class="logout-btn">Cerrar sesión</a>
    </div>
</div>

<div class="content">
    <h3>Bienvenido al panel principal</h3>
    <p>Tu sesión está activa y autenticada correctamente "no me quemes".</p>

    <div class="grid">
        <div class="card">
            <div class="label">Nombre</div>
            <div class="value"><%= displayName != null ? displayName : "" %></div>
        </div>
        <div class="card">
            <div class="label">Correo</div>
            <div class="value"><%= email != null ? email : "" %></div>
        </div>
        <div class="card">
            <div class="label">Rol</div>
            <div class="value"><%= role != null ? role : "" %></div>
        </div>
        <div class="card">
            <div class="label">Personaje</div>
            <div class="value"><%= characterName != null ? characterName : "" %></div>
        </div>
        <div class="card">
            <div class="label">Clase</div>
            <div class="value"><%= className != null ? className : "" %></div>
        </div>
        <div class="card">
            <div class="label">Facción</div>
            <div class="value"><%= factionName != null ? factionName : "" %></div>
        </div>
        <div class="card">
            <div class="label">Gremio</div>
            <div class="value"><%= guildName != null ? guildName : "" %></div>
        </div>
    </div>
</div>

</body>
</html>
