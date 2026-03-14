<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%
    String user = (String) session.getAttribute("username");

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
</div>

</body>
</html>