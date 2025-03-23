/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.LeaveRequestDAO;
import model.LeaveRequest;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAgendaServlet extends HttpServlet {
    private LeaveRequestDAO requestDAO = new LeaveRequestDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || (!user.getRoleName().equals("leader") && !user.getRoleName().equals("department_manager") && !user.getRoleName().equals("admin"))) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }
        try {
            String pathInfo = req.getPathInfo(); // e.g., "/1" or null
            if (pathInfo != null && pathInfo.length() > 1) {
                int userId = Integer.parseInt(pathInfo.substring(1));
                List<LeaveRequest> requests = requestDAO.getPersonalRequests(userId);
                req.setAttribute("requests", requests);

                // Fetch the correct startDate for the userId
                Date startDate = userId == user.getUserId() ? user.getCreatedDate() : requestDAO.getUserStartDate(userId);
                req.setAttribute("startDate", startDate);

                // Compute monthly ranges for 2025 with leap year check
                int year = 2025;
                List<List<Date>> monthlyRanges = new ArrayList<>();
                int[] daysInMonth = {31, isLeapYear(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                long startMillis = Date.valueOf(year + "-01-01").getTime();
                for (int month = 0; month < 12; month++) {
                    List<Date> monthDays = new ArrayList<>();
                    for (int day = 0; day < daysInMonth[month]; day++) {
                        monthDays.add(new Date(startMillis + (long) (day + getDaysBeforeMonth(month, daysInMonth)) * 24 * 60 * 60 * 1000));
                    }
                    monthlyRanges.add(monthDays);
                }
                req.setAttribute("monthlyRanges", monthlyRanges);
            }
            req.getRequestDispatcher("/employee_agenda.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || (!user.getRoleName().equals("leader") && !user.getRoleName().equals("department_manager") && !user.getRoleName().equals("admin"))) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }
        String userIdStr = req.getParameter("userId");
        if (userIdStr != null && !userIdStr.isEmpty()) {
            int userId = Integer.parseInt(userIdStr);
            session.setAttribute("userID", userId);
            resp.sendRedirect("/LeaveManagement/agenda/employee/" + userId);
        } else {
            resp.sendRedirect("/LeaveManagement/agenda/employee");
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private int getDaysBeforeMonth(int month, int[] daysInMonth) {
        int days = 0;
        for (int i = 0; i < month; i++) {
            days += daysInMonth[i];
        }
        return days;
    }
}
