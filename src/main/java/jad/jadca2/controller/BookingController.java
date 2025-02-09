package jad.jadca2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jad.jadca2.dao.BookingDAO;
import jad.jadca2.model.Booking;
import jad.jadca2.model.Service;

@RestController
@RequestMapping("/")
public class BookingController {

    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            BookingDAO db = new BookingDAO();
            int bookingId = db.createBooking(booking);

            for (Service service : booking.getServices()) {
                db.createBookingDetail(bookingId, service.getId(), service.getQuantity());
            }

            return ResponseEntity.ok("{\"message\": \"Booking created successfully\", \"bookingId\": " + bookingId + "}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Error creating booking: " + e.getMessage() + "\"}");
        }
    }
}

