<%-- 
    Document   : personal_list
    Created on : Mar 23, 2025, 6:48:38 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head><title>My Leave Requests</title></head>
    <body>
        <h1>My Leave Requests</h1>
        <c:if test="${empty requests}">
            <p>No leave requests found.</p>
        </c:if>
        <c:if test="${not empty requests}">
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
                        <td>${request.title}</td> <!-- Not a hyperlink -->
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
