/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.LeaveRequest;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDAO {
    public void createRequest(LeaveRequest request) throws Exception {
        String sql = "INSERT INTO leave_requests (user_id, title, from_date, to_date, reason, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, request.getUserId());
            ps.setString(2, request.getTitle());
            ps.setDate(3, request.getFromDate());
            ps.setDate(4, request.getToDate());
            ps.setString(5, request.getReason());
            ps.setString(6, "Inprogress");
            ps.executeUpdate();
        }
    }

    public List<LeaveRequest> getPersonalRequests(int userId) throws Exception {
        List<LeaveRequest> requests = new ArrayList<>();
        String sql = "SELECT lr.*, u.full_name AS created_by, m.full_name AS processed_by_username " + // Use full_name
                     "FROM leave_requests lr " +
                     "JOIN users u ON lr.user_id = u.user_id " +
                     "LEFT JOIN users m ON lr.processed_by = m.user_id " +
                     "WHERE lr.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest req = new LeaveRequest(rs.getInt("request_id"), rs.getInt("user_id"), rs.getString("title"),
                        rs.getDate("from_date"), rs.getDate("to_date"), rs.getString("reason"), rs.getString("status"),
                        rs.getInt("processed_by") == 0 ? null : rs.getInt("processed_by"));
                req.setCreatedBy(rs.getString("created_by"));
                req.setProcessedByUsername(rs.getString("processed_by_username"));
                requests.add(req);
            }
        }
        return requests;
    }

    public List<LeaveRequest> getEmployeeRequests(int userId, String role) throws Exception {
        List<LeaveRequest> requests = new ArrayList<>();
        String sql = "SELECT lr.*, u.full_name AS created_by, m.full_name AS processed_by_username " + // Use full_name
                     "FROM leave_requests lr " +
                     "JOIN users u ON lr.user_id = u.user_id " +
                     "LEFT JOIN users m ON lr.processed_by = m.user_id";
        if (role.equals("leader")) {
            sql += " WHERE u.manager_id = ?";
        } else if (role.equals("department_manager")) {
            sql += " WHERE u.division_id = (SELECT division_id FROM users WHERE user_id = ?)";
        }
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (!role.equals("admin")) ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest req = new LeaveRequest(rs.getInt("request_id"), rs.getInt("user_id"), rs.getString("title"),
                        rs.getDate("from_date"), rs.getDate("to_date"), rs.getString("reason"), rs.getString("status"),
                        rs.getInt("processed_by") == 0 ? null : rs.getInt("processed_by"));
                req.setCreatedBy(rs.getString("created_by"));
                req.setProcessedByUsername(rs.getString("processed_by_username"));
                requests.add(req);
            }
        }
        return requests;
    }

    public LeaveRequest getRequestById(int requestId) throws Exception {
        String sql = "SELECT lr.*, u.full_name AS created_by, m.full_name AS processed_by_username " + // Use full_name
                     "FROM leave_requests lr " +
                     "JOIN users u ON lr.user_id = u.user_id " +
                     "LEFT JOIN users m ON lr.processed_by = m.user_id " +
                     "WHERE lr.request_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LeaveRequest req = new LeaveRequest(rs.getInt("request_id"), rs.getInt("user_id"), rs.getString("title"),
                        rs.getDate("from_date"), rs.getDate("to_date"), rs.getString("reason"), rs.getString("status"),
                        rs.getInt("processed_by") == 0 ? null : rs.getInt("processed_by"));
                req.setCreatedBy(rs.getString("created_by"));
                req.setProcessedByUsername(rs.getString("processed_by_username"));
                return req;
            }
        }
        return null;
    }

    public void reviewRequest(int requestId, String status, int processedBy) throws Exception {
        String sql = "UPDATE leave_requests SET status = ?, processed_by = ? WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, processedBy);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        }
    }
}
