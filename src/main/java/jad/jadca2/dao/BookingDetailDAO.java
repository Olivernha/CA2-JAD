package jad.jadca2.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jad.jadca2.model.BookingDetail;
//import com.ca1.utils.DatabaseConnection;

public class BookingDetailDAO {
    private Connection connection;

    // Constructor that initializes the connection
    public BookingDetailDAO() {
        // Initialize the connection using your DatabaseConnection utility class
//        try {
//            this.connection = DatabaseConnection.getConnection();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
    public List<BookingDetail> getBookingDetailsByBookingId(int bookingId) {
        List<BookingDetail> details = new ArrayList<>();
        String query = "SELECT * FROM booking_details WHERE booking_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    BookingDetail detail = new BookingDetail();
                    detail.setBookingDetailId(resultSet.getInt("booking_detail_id"));
                    detail.setBookingId(resultSet.getInt("booking_id"));
                    detail.setServiceId(resultSet.getInt("service_id"));
                    detail.setQuantity(resultSet.getInt("quantity"));

                    details.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }


}

