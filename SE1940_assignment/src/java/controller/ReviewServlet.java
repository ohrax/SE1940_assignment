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
        if (user == null) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }

        String role = user.getRoleName();
        if (!role.equals("leader") && !role.equals("department_manager") && !role.equals("admin")) {
            resp.sendRedirect("/LeaveManagement/home");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendRedirect("/LeaveManagement/request/list/employee");
            return;
        }
        int requestId = Integer.parseInt(pathInfo.substring(1));

        try {
            LeaveRequest request = requestDAO.getRequestById(requestId);
            if (request == null) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            if (request.getUserId() == user.getUserId()) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            String requesterRole = request.getRequesterRole();
            if (requesterRole == null) {
                // Handle case where requester's role is not defined
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            boolean canReview = false;
            switch (role) {
                case "leader":
                    if (requesterRole.equals("employee")) {
                        canReview = true;
                    }
                    break;
                case "department_manager":
                    if (requesterRole.equals("employee") || requesterRole.equals("leader")) {
                        canReview = true;
                    }
                    break;
                case "admin":
                    if (requesterRole.equals("employee") || requesterRole.equals("leader") || requesterRole.equals("department_manager")) {
                        canReview = true;
                    }
                    break;
            }

            if (!canReview) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            req.setAttribute("request", request);
            req.getRequestDispatcher("/request_review.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }

        String role = user.getRoleName();
        if (!role.equals("leader") && !role.equals("department_manager") && !role.equals("admin")) {
            resp.sendRedirect("/LeaveManagement/home");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendRedirect("/LeaveManagement/request/list/employee");
            return;
        }
        int requestId = Integer.parseInt(pathInfo.substring(1));

        try {
            LeaveRequest request = requestDAO.getRequestById(requestId);
            if (request == null) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            if (request.getUserId() == user.getUserId()) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            String requesterRole = request.getRequesterRole();
            if (requesterRole == null) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            boolean canReview = false;
            switch (role) {
                case "leader":
                    if (requesterRole.equals("employee")) {
                        canReview = true;
                    }
                    break;
                case "department_manager":
                    if (requesterRole.equals("employee") || requesterRole.equals("leader")) {
                        canReview = true;
                    }
                    break;
                case "admin":
                    if (requesterRole.equals("employee") || requesterRole.equals("leader") || requesterRole.equals("department_manager")) {
                        canReview = true;
                    }
                    break;
            }

            if (!canReview) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            String status = req.getParameter("status");
            if (status != null && (status.equals("Approved") || status.equals("Rejected"))) {
                requestDAO.reviewRequest(requestId, status, user.getUserId());
            }

            resp.sendRedirect("/LeaveManagement/request/list/employee");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
