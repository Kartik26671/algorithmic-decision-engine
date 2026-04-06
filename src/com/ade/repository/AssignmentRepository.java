package com.ade.repository;

import com.ade.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AssignmentRepository {

    public void saveAssignment(int orderId, int driverId, double time) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO assignments (order_id, driver_id, estimated_time) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, orderId);
            ps.setInt(2, driverId);
            ps.setDouble(3, time);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}