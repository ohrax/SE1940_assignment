/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Division;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DivisionDAO {
    public Division getDivisionById(int divisionId) throws Exception {
        String sql = "SELECT * FROM divisions WHERE division_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Division(rs.getInt("division_id"), rs.getString("division_name"));
            }
        }
        return null;
    }
}
