/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.LeaveRequest;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDAO {

    public void createRequest(LeaveRequest request) throws Exception {
        String sql = "INSERT INTO leave_requests (user_id, title, from_date, to_date, reason, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
        String sql = "SELECT lr.*, u.full_name AS created_by, m.full_name AS processed_by_username, r.role_name "
                + "FROM leave_requests lr "
                + "JOIN users u ON lr.user_id = u.user_id "
                + "LEFT JOIN users m ON lr.processed_by = m.user_id "
                + "LEFT JOIN user_roles ur ON u.user_id = ur.user_id "
                + "LEFT JOIN roles r ON ur.role_id = r.role_id "
                + "WHERE lr.user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest req = new LeaveRequest(rs.getInt("request_id"), rs.getInt("user_id"), rs.getString("title"),
                        rs.getDate("from_date"), rs.getDate("to_date"), rs.getString("reason"), rs.getString("status"),
                        rs.getInt("processed_by") == 0 ? null : rs.getInt("processed_by"));
                req.setCreatedBy(rs.getString("created_by"));
                req.setProcessedByUsername(rs.getString("processed_by_username"));
                req.setRequesterRole(rs.getString("role_name"));
                requests.add(req);
            }
        }
        return requests;
    }

    public List<LeaveRequest> getEmployeeRequests(int reviewerUserId, String reviewerRole, Integer targetUserId) throws Exception {
        List<LeaveRequest> requests = new ArrayList<>();
        String sql = "SELECT lr.*, u.full_name AS created_by, m.full_name AS processed_by_username, r.role_name "
                + "FROM leave_requests lr "
                + "JOIN users u ON lr.user_id = u.user_id "
                + "LEFT JOIN users m ON lr.processed_by = m.user_id "
                + "LEFT JOIN user_roles ur ON u.user_id = ur.user_id "
                + "LEFT JOIN roles r ON ur.role_id = r.role_id "
                + "WHERE lr.user_id != ? AND ";
        List<Object> params = new ArrayList<>();
        params.add(reviewerUserId);

        switch (reviewerRole) {
            case "leader":
                sql += "r.role_name = 'employee' AND u.manager_id = ?";
                params.add(reviewerUserId);
                break;
            case "department_manager":
                sql += "r.role_name IN ('employee', 'leader') AND u.division_id = (SELECT division_id FROM users WHERE user_id = ?)";
                params.add(reviewerUserId);
                break;
            case "admin":
                sql += "r.role_name IN ('employee', 'leader', 'department_manager')";
                break;
            default:
                return requests;
        }

        // If a specific targetUserId is provided, add a condition to filter by that user
        if (targetUserId != null) {
            sql += " AND lr.user_id = ?";
            params.add(targetUserId);
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest req = new LeaveRequest(rs.getInt("request_id"), rs.getInt("user_id"), rs.getString("title"),
                        rs.getDate("from_date"), rs.getDate("to_date"), rs.getString("reason"), rs.getString("status"),
                        rs.getInt("processed_by") == 0 ? null : rs.getInt("processed_by"));
                req.setCreatedBy(rs.getString("created_by"));
                req.setProcessedByUsername(rs.getString("processed_by_username"));
                req.setRequesterRole(rs.getString("role_name"));
                requests.add(req);
            }
        }
        return requests;
    }

    public LeaveRequest getRequestById(int requestId) throws Exception {
        String sql = "SELECT lr.*, u.full_name AS created_by, m.full_name AS processed_by_username, r.role_name "
                + "FROM leave_requests lr "
                + "JOIN users u ON lr.user_id = u.user_id "
                + "LEFT JOIN users m ON lr.processed_by = m.user_id "
                + "LEFT JOIN user_roles ur ON u.user_id = ur.user_id "
                + "LEFT JOIN roles r ON ur.role_id = r.role_id "
                + "WHERE lr.request_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LeaveRequest req = new LeaveRequest(rs.getInt("request_id"), rs.getInt("user_id"), rs.getString("title"),
                        rs.getDate("from_date"), rs.getDate("to_date"), rs.getString("reason"), rs.getString("status"),
                        rs.getInt("processed_by") == 0 ? null : rs.getInt("processed_by"));
                req.setCreatedBy(rs.getString("created_by"));
                req.setProcessedByUsername(rs.getString("processed_by_username"));
                req.setRequesterRole(rs.getString("role_name"));
                return req;
            }
        }
        return null;
    }

    public void reviewRequest(int requestId, String status, int processedBy) throws Exception {
        String sql = "UPDATE leave_requests SET status = ?, processed_by = ? WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, processedBy);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        }
    }

    public Date getUserStartDate(int userId) throws Exception {
        String sql = "SELECT created_date FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDate("created_date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Handle this case appropriately
    }
}
