/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/LeaveManagement/login");
            return;
        }
        List<String> options;
        switch (user.getRoleName()) {
            case "employee":
                options = Arrays.asList("request/create", "request/list/personal/" + user.getUserId(), "agenda/personal/" + user.getUserId());
                break;
            case "leader":
                options = Arrays.asList("request/create", "request/list/personal/" + user.getUserId(), "agenda/personal/" + user.getUserId(), "request/list/employee", "agenda/employee");
                break;
            case "department_manager":
                options = Arrays.asList("request/create", "request/list/personal/" + user.getUserId(), "agenda/personal/" + user.getUserId(), "request/list/employee", "agenda/employee");
                break;
            case "admin":
                options = Arrays.asList("request/list/employee", "agenda/employee");
                break;
            default:
                options = Arrays.asList();
        }
        req.setAttribute("options", options);
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
