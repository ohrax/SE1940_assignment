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

public class CreateRequestServlet extends HttpServlet {

    private LeaveRequestDAO requestDAO = new LeaveRequestDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }
        req.getRequestDispatcher("/request_create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }
        String title = req.getParameter("title");
        Date fromDate = Date.valueOf(req.getParameter("from_date"));
        Date toDate = Date.valueOf(req.getParameter("to_date"));
        String reason = req.getParameter("reason");
        LeaveRequest request = new LeaveRequest(0, user.getUserId(), title, fromDate, toDate, reason, "Inprogress", null);
        try {
            requestDAO.createRequest(request);
            resp.sendRedirect("/LeaveManagement/request/list/personal/" + user.getUserId());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
