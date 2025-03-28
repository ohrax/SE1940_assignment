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
        <h3>Logged in as: ${sessionScope.user.fullName} (${sessionScope.user.roleName})</h3>
        <c:if test="${sessionScope.user.roleName == 'leader' || sessionScope.user.roleName == 'department_manager' || sessionScope.user.roleName == 'admin'}">
            <h3>Select Employee Agenda:</h3>
            <form action="/LeaveManagement/agenda/employee" method="POST">
                <label>User ID: <input type="number" name="userId" required></label>
                <button type="submit">View</button>
            </form>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p style="color: red;">${errorMessage}</p>
        </c:if>
        <c:if test="${not empty requests}">
            <h3>Agenda for: ${requests[0].createdBy}</h3>
            <c:forEach var="monthDays" items="${monthlyRanges}" varStatus="monthLoop">
                <h2>
                    <c:choose>
                        <c:when test="${monthLoop.index == 0}">January</c:when>
                        <c:when test="${monthLoop.index == 1}">February</c:when>
                        <c:when test="${monthLoop.index == 2}">March</c:when>
                        <c:when test="${monthLoop.index == 3}">April</c:when>
                        <c:when test="${monthLoop.index == 4}">May</c:when>
                        <c:when test="${monthLoop.index == 5}">June</c:when>
                        <c:when test="${monthLoop.index == 6}">July</c:when>
                        <c:when test="${monthLoop.index == 7}">August</c:when>
                        <c:when test="${monthLoop.index == 8}">September</c:when>
                        <c:when test="${monthLoop.index == 9}">October</c:when>
                        <c:when test="${monthLoop.index == 10}">November</c:when>
                        <c:when test="${monthLoop.index == 11}">December</c:when>
                    </c:choose>
                </h2>
                <table border="1">
                    <tr>
                        <c:forEach var="day" items="${monthDays}">
                            <th style="min-width: 40px; height: 40px;">${day.toString().substring(5)}</th>
                            </c:forEach>
                    </tr>
                    <tr>
                        <c:forEach var="day" items="${monthDays}">
                            <c:set var="isAbsent" value="false"/>
                            <c:if test="${not day.before(startDate) && not empty requests}">
                                <c:forEach var="request" items="${requests}">
                                    <c:if test="${request.status == 'Approved' && request.fromDate <= day && request.toDate >= day}">
                                        <c:set var="isAbsent" value="true"/>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <td style="min-width: 40px; height: 40px; background-color:
                                <c:choose>
                                    <c:when test="${day.before(startDate)}">none</c:when>
                                    <c:when test="${isAbsent}">red</c:when>
                                    <c:otherwise>green</c:otherwise>
                                </c:choose>">
                            </td>
                        </c:forEach>
                    </tr>
                </table>
                <br>
            </c:forEach>
        </c:if>
        <br>
        <a href="/LeaveManagement/home">Back to Home</a>
    </body>
</html>