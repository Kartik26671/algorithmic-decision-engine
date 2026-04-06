package com.ade.services;

import com.ade.models.Driver;
import com.ade.models.Order;

import java.util.List;

public class AssignmentService {

    public Driver assignDriver(Order order, List<Driver> drivers) {
        Driver best = null;
        double minDist = Double.MAX_VALUE;

        for (Driver d : drivers) {
            if (!d.isAvailable()) continue;

            double dist = Math.sqrt(
                    Math.pow(order.latitude - d.latitude, 2) +
                            Math.pow(order.longitude - d.longitude, 2)
            );

            if (dist < minDist) {
                minDist = dist;
                best = d;
            }
        }

        return best;
    }
}