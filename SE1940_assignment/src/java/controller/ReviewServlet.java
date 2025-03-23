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
            resp.sendRedirect("login");
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

            // Check if the user can review or re-review this request
            boolean canReview = false;
            boolean canReReview = false;

            // Check if the request is already processed and the user is the one who processed it
            if (request.getProcessedBy() != null && request.getProcessedBy() == user.getUserId()
                    && (request.getStatus().equals("Approved") || request.getStatus().equals("Rejected"))) {
                canReReview = true;
            }

            // Prevent users from reviewing their own requests
            if (request.getUserId() == user.getUserId()) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            String requesterRole = request.getRequesterRole();
            if (requesterRole == null) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            // Check if the user can review the request (if it's still "Inprogress")
            if (request.getStatus().equals("Inprogress")) {
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
            }

            // Allow access if the user can either review or re-review
            if (!canReview && !canReReview) {
                resp.sendRedirect("/LeaveManagement/request/list/employee");
                return;
            }

            req.setAttribute("request", request);
            req.setAttribute("canReReview", canReReview);
            req.getRequestDispatcher("/request_review.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login");
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

            // Prevent users from reviewing their own requests
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
            boolean canReReview = false;

            // Check if the user can re-review (if they processed it)
            if (request.getProcessedBy() != null && request.getProcessedBy() == user.getUserId()
                    && (request.getStatus().equals("Approved") || request.getStatus().equals("Rejected"))) {
                canReReview = true;
            }

            // Check if the user can review (if it's still "Inprogress")
            if (request.getStatus().equals("Inprogress")) {
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
            }

            if (!canReview && !canReReview) {
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
