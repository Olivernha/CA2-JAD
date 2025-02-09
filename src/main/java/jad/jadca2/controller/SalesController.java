package jad.jadca2.controller;

import jad.jadca2.dao.SaleDAO;
import jad.jadca2.model.Customer;
import jad.jadca2.model.Sale;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SalesController {
    private final SaleDAO saleDAO = new SaleDAO();

    @GetMapping("/sale/date/{date}")
    public ResponseEntity<List<Sale>> getBookingsByDate(@PathVariable("date") String date) {
        try {
            List<Sale> sales = saleDAO.getBookingsByDate(date);
            if (sales.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/period/{startDate}/{endDate}")
    public ResponseEntity<List<Sale>> getBookingsByPeriod(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        try {
            List<Sale> sales = saleDAO.getBookingsByPeriod(startDate, endDate);
            if (sales.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/monthYear/{month}/{year}")
    public ResponseEntity<List<Sale>> getBookingsByMonth(@PathVariable("month") int month, @PathVariable("year") int year) {
        try {
            List<Sale> sales = saleDAO.getBookingsByMonth(month, year);
            if (sales.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/getTop10BookingsByValue")
    public List<Customer> getTop10BookingsByValue() {
        List<Customer> customers = new ArrayList<>();
        try {
            customers = saleDAO.getTop10BookingsByValue();
        } catch (Exception e) {
            System.out.println("Error retrieving top 10 data: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for debugging
        }
        return customers;  // Always return a list, even if it's empty
    }

    @GetMapping("/getCustomersForCleaningServices")
    public ResponseEntity<Map<String, List<Customer>>> getCustomersForCleaningServices() {
        try {
            Map<String, List<Customer>> serviceCustomers = saleDAO.getAllServicesWithCustomers();
            return ResponseEntity.ok(serviceCustomers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

}