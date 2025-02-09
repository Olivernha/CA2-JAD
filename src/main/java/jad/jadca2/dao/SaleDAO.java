package jad.jadca2.dao;


import jad.jadca2.dbaccess.DBConnection;
import jad.jadca2.model.Customer;
import jad.jadca2.model.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class SaleDAO {

    private Connection connection; // Assume this is initialized elsewhere

    public SaleDAO() {
        this.connection = DBConnection.getConnection();
    }

    // Bookings by date
    public List<Sale> getBookingsByDate(String date) {
        String query = "SELECT bookings.*, booking_details.* " +
                "FROM bookings " +
                "JOIN booking_details ON bookings.booking_id = booking_details.booking_id " +
                "WHERE DATE(bookings.booking_date) = CAST(? AS DATE)";
        return executeBookingQuery(query, new Object[]{date});
    }

    // Bookings by period
    public List<Sale> getBookingsByPeriod(String startDate, String endDate) {
        String query = "SELECT bookings.*, booking_details.* " +
                "FROM bookings " +
                "JOIN booking_details ON bookings.booking_id = booking_details.booking_id " +
                "WHERE bookings.booking_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)";
        return executeBookingQuery(query, new Object[]{startDate, endDate});
    }

    // Bookings by month
// Bookings by month
    public List<Sale> getBookingsByMonth(int month, int year) {
        String query = "SELECT bookings.*, booking_details.* " +
                "FROM bookings " +
                "JOIN booking_details ON bookings.booking_id = booking_details.booking_id " +
                "WHERE EXTRACT(MONTH FROM bookings.booking_date) = ? " +
                "AND EXTRACT(YEAR FROM bookings.booking_date) = ?";
        return executeBookingQuery(query, new Object[]{month, year});
    }


    // Helper method to execute a query and map results to Sale objects
    private List<Sale> executeBookingQuery(String query, Object[] parameters) {
        List<Sale> sales = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set query parameters
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof String) {
                    stmt.setString(i + 1, (String) parameters[i]);
                } else if (parameters[i] instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) parameters[i]);
                }
            }

            // Execute the query and process the result set
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = mapResultSetToSale(rs); // Map ResultSet to Sale object
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
        }
        return sales;
    }

    // Helper method to map ResultSet to Sale object
    private Sale mapResultSetToSale(ResultSet rs) throws SQLException {
        Sale sale = new Sale();

        // Map fields from the 'bookings' table
        sale.setBookingId(rs.getInt("booking_id"));
        sale.setCustomerId(rs.getInt("customer_id"));
        sale.setBookingDate(rs.getDate("booking_date"));
        sale.setStatus(rs.getString("status"));
        sale.setSpecialRequest(rs.getString("special_request"));

        // Map fields from the 'booking_details' table
        sale.setBookingDetailId(rs.getInt("booking_detail_id"));
        sale.setServiceId(rs.getInt("service_id"));
        sale.setQuantity(rs.getInt("quantity"));
        sale.setPrice(rs.getDouble("total_price"));
        sale.setBookingDetailStatus(rs.getString("status"));

        // Calculate total price (optional)
        sale.calculateTotalPrice();

        return sale;
    }
    public  ArrayList<Customer> getTop10BookingsByValue() {
        ArrayList<Customer> topCustomers = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, SUM(b.total_price) AS total_spent FROM bookings b JOIN users u ON b.customer_id = u.user_id GROUP BY u.user_id, u.username ORDER BY total_spent DESC LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("user_id"));
                customer.setName(rs.getString("username"));
                customer.setTotalSpent(rs.getDouble("total_spent"));
                topCustomers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topCustomers;
    }
    public Map<String, List<Customer>> getAllServicesWithCustomers() {
        Map<String, List<Customer>> serviceCustomersMap = new LinkedHashMap<>();

        String sql = "SELECT s.service_name, u.user_id, u.username, u.email, u.phone, u.address, " +
                "s.price, bd.quantity, (s.price * bd.quantity) AS total_spent, " +
                "b.booking_date, b.status " +
                "FROM bookings b " +
                "JOIN booking_details bd ON b.booking_id = bd.booking_id " +
                "JOIN service s ON bd.service_id = s.service_id " +
                "JOIN users u ON b.customer_id = u.user_id " +
                "ORDER BY s.service_name, total_spent DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String serviceName = rs.getString("service_name");

                Customer customerBooking = new Customer();
                customerBooking.setId(rs.getInt("user_id"));
                customerBooking.setName(rs.getString("username"));
                customerBooking.setEmail(rs.getString("email"));
                customerBooking.setPhone(rs.getString("phone"));
                customerBooking.setAddress(rs.getString("address"));
                customerBooking.setPrice(rs.getDouble("price"));
                customerBooking.setQuantity(rs.getInt("quantity"));
                customerBooking.setTotalSpent(rs.getDouble("total_spent"));
                customerBooking.setBookingDate(rs.getDate("booking_date"));
                customerBooking.setStatus(rs.getString("status"));

                // If the service is not yet in the map, add it
                serviceCustomersMap.computeIfAbsent(serviceName, k -> new ArrayList<>()).add(customerBooking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceCustomersMap;
    }

}