/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Role;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    public List<Role> getRolesByUserId(int userId) throws Exception {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT r.* FROM roles r JOIN user_roles ur ON r.role_id = ur.role_id WHERE ur.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                roles.add(new Role(rs.getInt("role_id"), rs.getString("role_name")));
            }
        }
        return roles;
    }
}
