package jad.jadca2.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jad.jadca2.model.Booking;
import jad.jadca2.model.BookingDetail;
import jad.jadca2.dbaccess.DBConnection;

public class BookingDAO {
    private Connection connection;

//     Constructor that initializes the connection
    public BookingDAO() {
        // Initialize the connection using your DatabaseConnection utility class
        this.connection = DBConnection.getConnection();
    }
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setCustomerId(resultSet.getInt("customer_id"));
                booking.setBookingDate(resultSet.getDate("booking_date"));
                booking.setStatus(resultSet.getString("status"));
                booking.setSpecialRequest(resultSet.getString("special_request"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    public int createBooking(Booking booking) throws Exception {
        String sql = "INSERT INTO bookings (customer_id, status, special_request,total_price) VALUES (?, ?, ?, ?) RETURNING booking_id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, booking.getCustomerId());
            pstmt.setString(2, booking.getStatus());
            pstmt.setString(3, booking.getSpecialRequest());
            pstmt.setDouble(4, booking.getTotalPrice());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("booking_id");
            }
            throw new Exception("Failed to retrieve generated booking_id.");
        } catch (Exception e) {
            throw new Exception("Failed to create booking: " + e.getMessage(), e);
        }
    }
    public void createBookingDetail(int bookingId, int serviceId, int quantity) throws Exception {
        String sql = "INSERT INTO booking_details (booking_id, service_id, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            pstmt.setInt(2, serviceId);
            pstmt.setInt(3, quantity);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Failed to insert booking detail: " + e.getMessage(), e);
        }
    }
    public Booking getBookingById(int bookingId) {
        Booking booking = null;
        String query = "SELECT * FROM bookings WHERE booking_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    booking = new Booking();
                    booking.setBookingId(resultSet.getInt("booking_id"));
                    booking.setStatus(resultSet.getString("status"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }
    public boolean updateBooking(int bookingId ,String status) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteBooking(int bookingId) {
        boolean success = false;
        String query = "DELETE FROM bookings WHERE booking_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
