package com.ade.server;

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
                //  Only allow GET
                if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                DriverRepository driverRepo = new DriverRepository();
                OrderRepository orderRepo = new OrderRepository();
                AssignmentRepository assignmentRepo = new AssignmentRepository();
                AssignmentService service = new AssignmentService();

                // ALWAYS initialize with something (avoid blank)
                StringBuilder response = new StringBuilder("Starting assignment...\n");

                List<Order> orders = orderRepo.getPendingOrders();

                //handle empty case
                if (orders.isEmpty()) {
                    response.append("No pending orders found\n");
                }

                for (Order o : orders) {

                    List<Driver> drivers = driverRepo.getAvailableDrivers();

                    Driver assigned = service.assignDriver(o, drivers);

                    if (assigned != null) {

                        assignmentRepo.saveAssignment(o.id, assigned.id, 10.0);
                        driverRepo.markDriverUnavailable(assigned.id);
                        orderRepo.markOrderAssigned(o.id);

                        response.append("Order ")
                                .append(o.id)
                                .append(" -> Driver ")
                                .append(assigned.id)
                                .append("\n");

                    } else {
                        response.append("No driver available for Order ")
                                .append(o.id)
                                .append("\n");
                    }
                }

                // CORRECT BYTE HANDLING
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