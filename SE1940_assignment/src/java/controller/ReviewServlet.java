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
import java.io.IOException;

public class ReviewServlet extends HttpServlet {
    private final LeaveRequestDAO requestDAO = new LeaveRequestDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || (!user.getRoleName().equals("leader") && !user.getRoleName().equals("admin"))) {
            resp.sendRedirect("login");
            return;
        }
        int requestId = Integer.parseInt(req.getPathInfo().substring(1));
        try {
            LeaveRequest request = requestDAO.getRequestById(requestId);
            req.setAttribute("request", request);
            req.getRequestDispatcher("/request_review.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || (!user.getRoleName().equals("leader") && !user.getRoleName().equals("admin"))) {
            resp.sendRedirect("login");
            return;
        }
        int requestId = Integer.parseInt(req.getPathInfo().substring(1));
        String status = req.getParameter("status");
        try {
            requestDAO.reviewRequest(requestId, status, user.getUserId());
            resp.sendRedirect("/LeaveManagement/request/list/employee");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
