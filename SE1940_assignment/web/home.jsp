<%-- 
    Document   : home
    Created on : Mar 3, 2025, 11:05:00 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head><title>Home</title></head>
    <body>
        <h1>Welcome, ${sessionScope.user.fullName}</h1>
        <h3>Role: ${sessionScope.user.roleName}</h3>
        <h4>Options:</h4>
        <ul>
            <c:forEach var="option" items="${options}">
                <li>
                    <c:if test="${option == 'request/create'}"><a href="request/create">Create</a></c:if>
                    <c:if test="${option.startsWith('request/list/personal')}"><a href="${option}">Personal List</a></c:if>
                    <c:if test="${option == 'request/list/employee'}"><a href="request/list/employee">Employee List</a></c:if>
                    <c:if test="${option.startsWith('agenda/personal')}"><a href="${option}">Personal Agenda</a></c:if>
                    <c:if test="${option == 'agenda/employee'}"><a href="agenda/employee">Employee Agenda</a></c:if>
                    </li>
            </c:forEach>
            <li><a href="logout">Logout</a></li>
        </ul>
    </body>
</html>

