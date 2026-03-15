<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reloader - Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1e1e2f;
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .top-left-refresh {
            position: fixed;
            top: 16px;
            left: 16px;
            font-size: 14px;
            color: rgba(255,255,255,0.85);
            text-decoration: none;
            padding: 6px 10px;
            border-radius: 6px;
            background: rgba(255,255,255,0.06);
            border: 1px solid rgba(255,255,255,0.10);
        }

        .top-left-refresh:hover {
            background: rgba(255,255,255,0.10);
        }

        .login-box {
            background: #2c2c3e;
            padding: 30px;
            border-radius: 8px;
            width: 320px;
            box-shadow: 0 0 15px rgba(0,0,0,0.6);
        }
        input {
            width: 100%;
            padding: 8px;
            margin: 8px 0;
            border-radius: 4px;
            border: none;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            border: none;
            color: white;
            cursor: pointer;
            border-radius: 4px;
        }
        button:hover {
            background-color: #45a049;
        }
        .error {
            color: #ff6b6b;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<a class="top-left-refresh" href="<%= request.getContextPath() %>/login.jsp">↻ Volver al login</a>

<div class="login-box">
    <h2>Reloader</h2>

    <form action="login" method="post">
        <label>Usuario</label>
        <input type="text" name="username" required>

        <label>Contraseña</label>
        <input type="password" name="password" required>

        <button type="submit">Ingresar</button>
    </form>

    <% if (request.getParameter("error") != null) { %>
        <div class="error">Usuario o contraseña incorrectos</div>
    <% } %>
</div>

</body>
</html>