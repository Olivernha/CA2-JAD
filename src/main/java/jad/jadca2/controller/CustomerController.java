package jad.jadca2.controller;

import jad.jadca2.dao.CustomerDAO;
import jad.jadca2.model.Customer;
import jad.jadca2.model.Service;
import jad.jadca2.model.Booking;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CustomerController {
    private CustomerDAO customerDAO = new CustomerDAO();

    @GetMapping("/getRecentServices/{userId}")
    public ResponseEntity<List<Service>> getRecentServices(@PathVariable int userId) {
        List<Service> services = customerDAO.getRecentServices(userId);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/getUpcomingBookings/{userId}")
    public ResponseEntity<List<Booking>> getUpcomingBookings(@PathVariable int userId) {
        List<Booking> bookings = customerDAO.getUpcomingBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/getUserAddress")
    public String getUserAddress(@RequestParam int userId) {
        try {
            CustomerDAO CustomerDAO = new CustomerDAO();
            String address = CustomerDAO.getUserAddress(userId);
            return (address != null && !address.isEmpty()) ? address : "No address found";
        } catch (Exception e) {
            return "Error fetching address: " + e.getMessage();
        }
    }

    @PostMapping("/updateUserAddress/{userId}")
    public ResponseEntity<Map<String, String>> updateUserAddress(@PathVariable int userId, @RequestBody Map<String, String> payload) {
        try {
            System.out.println(userId);
            String newAddress = payload.get("newAddress");
            System.out.println(newAddress);
            if (newAddress == null || newAddress.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid address"));
            }

            CustomerDAO customerDAO = new CustomerDAO();
            boolean success = customerDAO.updateUserAddress(userId, newAddress);

            return success
                    ? ResponseEntity.ok(Map.of("message", "Address updated successfully"))
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update address"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error updating address: " + e.getMessage()));
        }
    }

    @PostMapping("/updateCustomerProfile/{userId}")
    public ResponseEntity<Map<String, String>> updateCustomerProfile(@PathVariable int userId, @RequestBody Map<String, String> payload) {
        try {
            // Extract user details from request
            String name = payload.get("name");
            String email = payload.get("email");
            String phone = payload.get("phone");
            String address = payload.get("address");
            String password = payload.get("password");

            // Validate input
            if (name == null || email == null || phone == null || address == null ||
                    name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid input fields"));
            }

            // Update profile in database using DAO
            CustomerDAO customerDAO = new CustomerDAO();
            boolean success = customerDAO.updateCustomerProfile(userId, name, email, phone, address, password);

            return success
                    ? ResponseEntity.ok(Map.of("message", "Profile updated successfully"))
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update profile"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error updating profile: " + e.getMessage()));
        }
    }

    @DeleteMapping("/cancelBooking/{bookingId}")
    public ResponseEntity<Map<String, String>> cancelBooking(@PathVariable int bookingId) {
        boolean success = customerDAO.cancelBooking(bookingId);

        if (success) {
            return ResponseEntity.ok(Map.of("message", "Booking cancelled successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Booking cancellation failed"));
        }
    }

}
