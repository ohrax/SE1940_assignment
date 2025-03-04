<%-- 
    Document   : employee_agenda
    Created on : Mar 5, 2025, 2:40:07 AM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head><title>Employee Agenda</title></head>
    <body>
        <h1>Employee Agenda</h1>
        <c:if test="${sessionScope.user.roleName == 'leader' || sessionScope.user.roleName == 'department_manager' || sessionScope.user.roleName == 'admin'}">
            <h3>Select Employee Agenda:</h3>
            <form action="/LeaveManagement/agenda/employee" method="POST">
                <label>User ID: <input type="number" name="userId" required></label>
                <button type="submit">View</button>
            </form>
        </c:if>
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
