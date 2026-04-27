package com.ade.services;

import com.ade.graph.Graph;
import com.ade.models.Driver;
import com.ade.models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssignmentService {

    public Driver assignDriver(Order order, List<Driver> drivers) {
        Driver best = null;
        double minDist = Double.MAX_VALUE;

        for (Driver d : drivers) {
            if (!d.isAvailable()) continue;

            Graph graph = buildGraph();

            int dist = graph.shortestPath(d.locationNode, order.locationNode);

            if (dist < minDist) {
                minDist = dist;
                best = d;
            }
        }

        return best;
    }

    // DP
    public List<Order> getOptimalOrders(List<Order> orders, int maxTime,int driverNode) {

        int n = orders.size();
        int[][] dp = new int[n + 1][maxTime + 1];
        Graph graph = buildGraph();

        // Build DP table
        for (int i = 1; i <= n; i++) {

            Order o = orders.get(i-1);
            int time = graph.shortestPath(driverNode,o.locationNode);

            for (int t = 0; t <= maxTime; t++) {
                if (time <= t) {
                    dp[i][t] = Math.max(
                            dp[i - 1][t],
                            1 + dp[i - 1][t - time]
                    );
                } else {
                    dp[i][t] = dp[i - 1][t];
                }
            }
        }

        // Backtrack to find selected orders
        List<Order> selected = new ArrayList<>();
        int t = maxTime;

        for (int i = n; i > 0; i--) {
            if (dp[i][t] != dp[i - 1][t]) {
                Order o = orders.get(i - 1);
                selected.add(o);
                int time = graph.shortestPath(driverNode,o.locationNode);
                t -= time;
            }
        }
        Collections.reverse(selected);
        return selected;
    }

    // graph
    public Graph buildGraph() {
        Graph graph = new Graph();

        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 3, 10);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 5, 8);
        graph.addEdge(1, 5, 20);

        return graph;
    }
}