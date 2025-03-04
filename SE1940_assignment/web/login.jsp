<%-- 
    Document   : login
    Created on : Mar 3, 2025, 11:04:52 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head><title>Login</title></head>
    <body>
        <h1>Login</h1>
        <form action="/LeaveManagement/login" method="post">
            <label>Username: <input type="text" name="username"></label><br>
            <label>Password: <input type="password" name="password"></label><br>
            <button type="submit">Login</button>
        </form>
        <% if (request.getAttribute("error") != null) { %>
        <p style="color:red"><%= request.getAttribute("error") %></p>
        <% } %>
    </body>
</html>
