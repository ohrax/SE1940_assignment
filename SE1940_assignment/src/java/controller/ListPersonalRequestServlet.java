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
import java.util.List;

public class ListPersonalRequestServlet extends HttpServlet {

    private final LeaveRequestDAO requestDAO = new LeaveRequestDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }

        String pathInfo = req.getPathInfo(); // e.g., "/1" or null
        int userId = (pathInfo != null && pathInfo.length() > 1) ? Integer.parseInt(pathInfo.substring(1)) : user.getUserId();
        try {
            // Only allow users to view their own requests
            if (userId != user.getUserId()) {
                resp.sendRedirect("/LeaveManagement/request/list/personal");
                return;
            }
            List<LeaveRequest> requests = requestDAO.getPersonalRequests(userId);
            req.setAttribute("requests", requests);
            req.getRequestDispatcher("/personal_list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
