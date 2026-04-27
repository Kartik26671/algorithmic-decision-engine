package com.ade.repository;

import com.ade.db.DBConnection;
import com.ade.models.Driver;

import java.sql.*;
import java.util.*;

public class DriverRepository {

    public List<Driver> getAvailableDrivers() {
        List<Driver> drivers = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM drivers WHERE available = 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Driver d = new Driver(
                        rs.getInt("id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getBoolean("available")
                );
                d.locationNode =1;
                drivers.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return drivers;
    }

    public void markDriverUnavailable(int driverId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE drivers SET available = false WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, driverId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}