<%-- 
    Document   : employee_request
    Created on : Mar 23, 2025, 6:48:58 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head><title>Employee Leave Requests</title></head>
    <body>
        <h1>Employee Leave Requests</h1>
        <h3>Logged in as: ${sessionScope.user.fullName} (${sessionScope.user.roleName})</h3>
        <c:if test="${sessionScope.user.roleName == 'leader' || sessionScope.user.roleName == 'department_manager' || sessionScope.user.roleName == 'admin'}">
            <h3>Select Employee to View Requests:</h3>
            <form action="/LeaveManagement/request/list/employee" method="POST">
                <label>User ID: <input type="number" name="userId" required></label>
                <button type="submit">View</button>
            </form>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p style="color: red;">${errorMessage}</p>
        </c:if>
        <c:if test="${not empty requests}">
            <c:if test="${not empty targetUserId}">
                <h3>Requests for: ${requests[0].createdBy}</h3>
            </c:if>
            <table border="1">
                <tr>
                    <th>Title</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Created By</th>
                    <th>Status</th>
                    <th>Processed By</th>
                </tr>
                <c:forEach var="request" items="${requests}">
                    <tr>
                        <td><a href="/LeaveManagement/request/review/${request.requestId}">${request.title}</a></td>
                        <td>${request.fromDate}</td>
                        <td>${request.toDate}</td>
                        <td>${request.createdBy}</td>
                        <td>${request.status}</td>
                        <td>${request.processedByUsername}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <br>
        <a href="/LeaveManagement/home">Back to Home</a>
    </body>
</html>