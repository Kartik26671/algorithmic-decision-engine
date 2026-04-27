package com.ade.repository;

import com.ade.db.DBConnection;
import com.ade.models.Order;

import java.sql.*;
import java.util.*;

public class OrderRepository {


    private int mapToNode(double lat, double lon) {

        // simple mapping logic (dummy for now)
        if (lat < 20) return 1;
        else if (lat < 25) return 2;
        else if (lat < 30) return 3;
        else return 4;
    }

    public List<Order> getPendingOrders() {
        List<Order> orders = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM orders WHERE status = 'PENDING'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Order o = new Order(
                        rs.getInt("id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("status")
                );

//                // (IMPORTANT)
//                o.locationNode = mapToNode(o.latitude, o.longitude);
                o.locationNode = (o.id ==1) ? 3 : 5;

                orders.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public void markOrderAssigned(int orderId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE orders SET status = 'ASSIGNED' WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, orderId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}