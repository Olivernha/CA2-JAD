package jad.jadca2.model;

import java.util.Date;

public class Sale {
    // Fields from 'bookings' table
    private int bookingId;
    private int customerId;
    private Date bookingDate;
    private String status;
    private String specialRequest;

    // Fields from 'booking_details' table
    private int bookingDetailId;
    private int serviceId;
    private int quantity;
    private double price;
    private String bookingDetailStatus;

    // Derived field (optional)
    private double totalPrice;

    // Constructor
    public Sale() {}

    // Getters and Setters for 'bookings' table fields
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    // Getters and Setters for 'booking_details' table fields
    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBookingDetailStatus() {
        return bookingDetailStatus;
    }

    public void setBookingDetailStatus(String bookingDetailStatus) {
        this.bookingDetailStatus = bookingDetailStatus;
    }

    // Derived field: Total Price
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Optional: Method to calculate total price
    public void calculateTotalPrice() {
        this.totalPrice = this.quantity * this.price;
    }
}