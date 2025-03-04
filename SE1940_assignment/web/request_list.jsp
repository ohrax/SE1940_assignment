<%-- 
    Document   : request_list
    Created on : Mar 3, 2025, 11:05:17 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head><title>Leave Requests</title></head>
    <body>
        <h1>Leave Requests</h1>
        <table border="1">
            <tr><th>Title</th><th>From</th><th>To</th><th>Created By</th><th>Status</th><th>Processed By</th></tr>
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
        <br>
        <a href="/LeaveManagement/home">Back to Home</a>
    </body>
</html>
