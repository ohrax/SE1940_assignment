<%-- 
    Document   : request_create
    Created on : Mar 3, 2025, 11:05:08 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head><title>Create Leave Request</title></head>
    <body>
        <h1>Create Leave Request</h1>
        <form action="/LeaveManagement/request/create" method="post">
            <label>User: <input type="text" value="${sessionScope.user.fullName}" readonly></label><br>
            <label>Role: <input type="text" value="${sessionScope.user.roleName}" readonly></label><br>
            <label>Division: <input type="text" value="${sessionScope.user.divisionName}" readonly></label><br>
            <label>Title: <input type="text" name="title" required></label><br>
            <label>From Date: <input type="date" name="from_date" required></label><br>
            <label>To Date: <input type="date" name="to_date" required></label><br>
            <label>Reason: <textarea name="reason" required></textarea></label><br>
            <button type="submit">Send</button>
        </form>
        <br>
        <a href="/LeaveManagement/home">Back to Home</a>
    </body>
</html>
