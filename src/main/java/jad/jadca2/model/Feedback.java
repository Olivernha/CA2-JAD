package jad.jadca2.model;
import java.util.Date;

public class Feedback {
    private int id;
    private int customerId;
    private int serviceId;
    private int bookingId;
    private int rating;
    private String comments;
    private String avatar;
    private Date feedbackDate;
    private String customerName;

    // Constructor
    public Feedback() {
    }
    public Feedback(int customerId,int serviceId,int bookingId,int rating,String comments) {
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.bookingId = bookingId;
        this.rating = rating;
        this.comments = comments;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comments;
    }

    public void setComment(String comment) {
        this.comments = comment;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}