<%-- 
    Document   : personal_agenda
    Created on : Mar 5, 2025, 2:40:00 AM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head><title>Personal Agenda</title></head>
    <body>
        <h1>Personal Agenda</h1>
        <table border="1">
            <tr>
                <th>Personnel</th>
                    <c:forEach var="date" items="${dateRange}">
                    <th>${date}</th>
                    </c:forEach>
            </tr>
            <c:forEach var="request" items="${requests}">
                <tr>
                    <td>${request.createdBy}</td>
                    <c:forEach var="date" items="${dateRange}">
                        <td style="background-color: ${request.status == 'Approved' && request.fromDate <= date && request.toDate >= date ? 'red' : 'green'}"></td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        <br>
        <a href="/LeaveManagement/home">Back to Home</a>
    </body>
</html>
