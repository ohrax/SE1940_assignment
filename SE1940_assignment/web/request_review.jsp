<%-- 
    Document   : request_review
    Created on : Mar 3, 2025, 11:05:24 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head><title>Review Leave Request</title></head>
    <body>
        <h1>Review Leave Request</h1>
        <p>
            <label>User: <input type="text" value="${request.createdBy}" readonly></label><br>
            <label>Role: <input type="text" value="${sessionScope.user.roleName}" readonly></label><br>
            <label>Division: <input type="text" value="${sessionScope.user.divisionName}" readonly></label><br>
            <label>Title: <input type="text" value="${request.title}" readonly></label><br>
            <label>From Date: <input type="date" value="${request.fromDate}" readonly></label><br>
            <label>To Date: <input type="date" value="${request.toDate}" readonly></label><br>
            <label>Reason: <textarea readonly>${request.reason}</textarea></label><br>
        <form action="/LeaveManagement/request/review/${request.requestId}" method="post" style="display:inline;">
            <input type="hidden" name="status" value="Approved">
            <button type="submit">Approve</button>
        </form>
        <form action="/LeaveManagement/request/review/${request.requestId}" method="post" style="display:inline;">
            <input type="hidden" name="status" value="Rejected">
            <button type="submit">Reject</button>
        </form>
    </p>
    <br>
    <a href="/LeaveManagement/home">Back to Home</a>
</body>
</html>
