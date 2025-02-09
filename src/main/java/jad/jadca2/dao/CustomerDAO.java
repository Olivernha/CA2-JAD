package jad.jadca2.dao;

import jad.jadca2.dbaccess.DBConnection;
import jad.jadca2.model.Booking;
import jad.jadca2.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO {

    public List<Service> getRecentServices(int userId) {
        List<Service> services = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT s.service_id,s.service_name,c.category_name,COALESCE(f.rating, 0) AS rating FROM booking_details bd JOIN bookings b ON bd.booking_id = b.booking_id JOIN service s ON bd.service_id = s.service_id JOIN service_category c ON s.category_id = c.category_id LEFT JOIN feedback f ON s.service_id = f.service_id WHERE b.customer_id = ? AND b.status = 'Completed' ORDER BY b.booking_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("service_id"));
                service.setServiceName(rs.getString("service_name"));
                service.setCategoryName(rs.getString("category_name"));
                service.setRating(rs.getInt("rating"));
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
    public List<Booking> getUpcomingBookings(int userId) {
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT b.booking_id,b.booking_date,s.service_id,s.service_name FROM bookings b JOIN booking_details bd ON b.booking_id = bd.booking_id JOIN service s ON bd.service_id = s.service_id WHERE b.customer_id = ? AND b.status = 'Confirmed' AND b.booking_date > CURRENT_DATE ORDER BY b.booking_date ASC";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            Map<Integer, Booking> bookingMap = new HashMap<>();
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");

                Booking booking = bookingMap.getOrDefault(bookingId, new Booking());
                if (!bookingMap.containsKey(bookingId)) {
                    booking.setBookingId(bookingId);
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setServices(new ArrayList<>());
                    bookingMap.put(bookingId, booking);
                }

                // Add services to the booking
                Service service = new Service();
                service.setId(rs.getInt("service_id"));
                service.setServiceName(rs.getString("service_name"));
                booking.getServices().add(service);
            }
            bookings.addAll(bookingMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public String getUserAddress(int userId) {
        String address = null;
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT address FROM users WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                address = rs.getString("address");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    public boolean updateUserAddress(int userId, String newAddress) {
        boolean updated = false;
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE users SET address = ? WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newAddress);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();
            updated = (rowsAffected > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }

    public boolean updateCustomerProfile(int userId, String name, String email, String phone, String address, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establish database connection
            conn = DBConnection.getConnection();
            String sql;
            if (password == null || password.isEmpty()) {
                sql = "UPDATE users SET username = ?, email = ?, phone = ?, address = ?   WHERE user_id = ?";
            } else {
                sql = "UPDATE users SET username = ?, email = ?, phone = ?, address = ?,  password = ? WHERE user_id = ?";
            }

            // SQL query to update customer profile
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);

            if (password != null && !password.isEmpty()) {
                String hashedPassword = login.hashing_algorithm.Bcrypt.hashpw(password, login.hashing_algorithm.Bcrypt.gensalt());
                pstmt.setString(5, hashedPassword);
                pstmt.setInt(6, userId);
            } else {
                pstmt.setInt(5, userId);
            }

            // Execute update
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelBooking(int bookingId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM bookings WHERE booking_id = ? AND status = 'Confirmed'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
