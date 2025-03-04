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
            if ("leader".equals(user.getRoleName()) && pathInfo != null && pathInfo.length() > 1) {
                int userId = Integer.parseInt(pathInfo.substring(1));
                requests = requestDAO.getEmployeeRequests(userId, user.getRoleName());
            } else {
                requests = requestDAO.getEmployeeRequests(user.getUserId(), user.getRoleName());
            }
            req.setAttribute("requests", requests);
            req.getRequestDispatcher("/request_list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
