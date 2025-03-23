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

public class ListEmployeeRequestServlet extends HttpServlet {

    private final LeaveRequestDAO requestDAO = new LeaveRequestDAO();

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
            List<LeaveRequest> requests;
            if (pathInfo != null && pathInfo.length() > 1) {
                int targetUserId = Integer.parseInt(pathInfo.substring(1));

                if (targetUserId == user.getUserId()) {
                    req.setAttribute("errorMessage", "You cannot view your own list in the Employee List view. Please use the Personal List view.");
                    req.getRequestDispatcher("/employee_list.jsp").forward(req, resp);
                    return;
                }

                // Fetch requests for the specific user, ensuring the logged-in user has permission
                requests = requestDAO.getEmployeeRequests(user.getUserId(), user.getRoleName(), targetUserId);
                req.setAttribute("requests", requests);
                req.setAttribute("targetUserId", targetUserId);
            } else {
                // Fetch all requests the logged-in user can see
                requests = requestDAO.getEmployeeRequests(user.getUserId(), user.getRoleName(), null);
            }
            req.setAttribute("requests", requests);
            req.getRequestDispatcher("/employee_list.jsp").forward(req, resp);
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
            resp.sendRedirect("/LeaveManagement/request/list/employee/" + userId);
        } else {
            resp.sendRedirect("/LeaveManagement/request/list/employee");
        }
    }
}
