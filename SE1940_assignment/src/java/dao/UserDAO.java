/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public User getUserByUsernameAndPassword(String username, String password) throws Exception {
        String sql = "SELECT u.*, r.role_name, d.division_name FROM users u " +
                     "JOIN user_roles ur ON u.user_id = ur.user_id " +
                     "JOIN roles r ON ur.role_id = r.role_id " +
                     "JOIN divisions d ON u.division_id = d.division_id " +
                     "WHERE u.username = ? AND u.password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
                                rs.getString("full_name"), rs.getInt("division_id"), 
                                rs.getInt("manager_id") == 0 ? 0 : rs.getInt("manager_id"), 
                                rs.getString("role_name"), rs.getString("division_name"), 
                                rs.getDate("created_date")); 
            }
        }
        return null;
    }
}
