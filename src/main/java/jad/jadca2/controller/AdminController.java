package jad.jadca2.controller;

import jad.jadca2.dao.AdminDAO;
import jad.jadca2.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {

    private final AdminDAO customerDAO = new AdminDAO();

    // CREATE - Post new customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            boolean created = customerDAO.createCustomer(customer);
            if (created) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // READ - Get all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // READ - Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
        try {
            Customer customer = customerDAO.getCustomerById(id);
            if (customer != null) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // UPDATE - Update existing customer
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(
            @PathVariable("id") int id,
            @RequestBody Customer customer,
            @RequestParam(required = false) String newPassword) {
        try {
            customer.setId(id);
            boolean updated = customerDAO.updateCustomer(customer, newPassword);
            if (updated) {
                return ResponseEntity.ok("Customer updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE - Delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id) {
        try {
            boolean deleted = customerDAO.deleteCustomer(id);
            if (deleted) {
                return ResponseEntity.ok("Customer deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/area/{code}")
    public ResponseEntity<List<Customer>> getCustomersByAreaCode(@PathVariable("code") String code) { // area code (from code) {
        try {
            List<Customer> customer = customerDAO.getCustomersByAreaCode(code);
            if (customer != null) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}