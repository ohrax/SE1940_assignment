/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.LeaveRequestDAO;
import jakarta.servlet.ServletException;
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

public class PersonalAgendaServlet extends HttpServlet {
    private final LeaveRequestDAO requestDAO = new LeaveRequestDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }
        String pathInfo = req.getPathInfo(); // e.g., "/1"
        int userId = (pathInfo != null && pathInfo.length() > 1) ? Integer.parseInt(pathInfo.substring(1)) : user.getUserId();
        try {
            List<LeaveRequest> requests = requestDAO.getPersonalRequests(userId);
            req.setAttribute("requests", requests);
            req.setAttribute("startDate", user.getCreatedDate());

            // Compute 10 days from startDate as java.sql.Date
            List<Date> dateRange = new ArrayList<>();
            long startMillis = user.getCreatedDate().getTime();
            for (int i = 0; i < 10; i++) {
                dateRange.add(new Date(startMillis + (i * 24 * 60 * 60 * 1000)));
            }
            req.setAttribute("dateRange", dateRange);

            req.getRequestDispatcher("/personal_agenda.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
