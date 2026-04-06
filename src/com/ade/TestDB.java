import com.ade.repository.*;
import com.ade.models.*;
import com.ade.services.AssignmentService;

import java.util.*;

public class TestDB {
    public static void main(String[] args) {

        DriverRepository driverRepo = new DriverRepository();
        OrderRepository orderRepo = new OrderRepository();


        List<Order> orders = orderRepo.getPendingOrders();

        AssignmentService service = new AssignmentService();
        AssignmentRepository assignmentRepo = new AssignmentRepository();


        for (Order o : orders) {
            List<Driver> drivers = driverRepo.getAvailableDrivers();

            Driver assigned = service.assignDriver(o, drivers);

            if (assigned != null) {
                System.out.println("Order " + o.id + " assigned to Driver " + assigned.id);

                assignmentRepo.saveAssignment(o.id, assigned.id, 10.0);
                driverRepo.markDriverUnavailable(assigned.id);
                orderRepo.markOrderAssigned(o.id);

            } else {
                System.out.println("No driver available for Order " + o.id);
            }
        }
    }
}