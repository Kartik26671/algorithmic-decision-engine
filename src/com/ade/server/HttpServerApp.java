package com.ade.server;

import com.ade.graph.Graph;
import com.ade.repository.*;
import com.ade.services.AssignmentService;
import com.ade.models.*;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

public class HttpServerApp {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/assign", new AssignHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server running on http://localhost:8080");
    }

    static class AssignHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {

            try {
                //Only allow GET
                if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                DriverRepository driverRepo = new DriverRepository();
                OrderRepository orderRepo = new OrderRepository();
                AssignmentRepository assignmentRepo = new AssignmentRepository();
                AssignmentService service = new AssignmentService();

                List<Order> orders = orderRepo.getPendingOrders();
                List<Driver> drivers = driverRepo.getAvailableDrivers();

                StringBuilder response = new StringBuilder();

                //Edge case: no orders
                if (orders.isEmpty()) {
                    response.append("No pending orders\n");
                } else {

                    response.append("Starting DP assignment...\n");

                    Graph graph = service.buildGraph();

                    for (Driver d : drivers) {

                        if (!d.isAvailable()) continue;

                        //  DP call (capacity = 30)
                        List<Order> selected = service.getOptimalOrders(orders, 50,d.locationNode);

                        if (selected.isEmpty()) {
                            continue;
                        }

                        response.append("Driver ")
                                .append(d.id)
                                .append(" assigned orders: ");


                        for (Order o : selected) {
                            int dist = graph.shortestPath(d.locationNode,o.locationNode);

                            assignmentRepo.saveAssignment(o.id, d.id, dist);
                            orderRepo.markOrderAssigned(o.id);

                            response.append("Order ")
                                    .append(o.id)
                                    .append(" (dist=")
                                    .append(dist)
                                    .append(") ");
                        }

                        // mark driver unavailable
                        driverRepo.markDriverUnavailable(d.id);

                        response.append("\n");

                        // remove assigned orders
                        orders.removeAll(selected);

                        // stop if no orders left
                        if (orders.isEmpty()) break;
                    }
                }

                // IMPORTANT: Always send response
                byte[] responseBytes = response.toString().getBytes();

                exchange.sendResponseHeaders(200, responseBytes.length);

                OutputStream os = exchange.getResponseBody();
                os.write(responseBytes);
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}