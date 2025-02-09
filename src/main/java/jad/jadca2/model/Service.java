package jad.jadca2.model;

public class Service {
    private int id;
    private int categoryId;
    private String categoryName;
    private String serviceName;
    private String description;
    private double price;
    private String imagePath;
    private int averageRating;
    private String feedbackComment;
    private boolean hasFeedback;
    private double discount;
    private int serviceRating;
    private int quantity;
    private boolean available; // Added missing field
    private int demandCount;

    private int rating;

    // Default constructor
    public Service() {}

    // Basic constructor
    public Service(int categoryId, String serviceName, String description, double price, String imagePath) {
        this.categoryId = categoryId;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    // Full constructor
    public Service(int id, int categoryId, String categoryName, String serviceName,
                   String description, double price, String imagePath, int averageRating) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.averageRating = averageRating;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



    public String getFeedbackComment() {
        return feedbackComment;
    }

    public void setFeedbackComment(String feedbackComment) {
        this.feedbackComment = feedbackComment;
    }

    public boolean isHasFeedback() {
        return hasFeedback;
    }

    public void setHasFeedback(boolean hasFeedback) {
        this.hasFeedback = hasFeedback;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(int serviceRating) {
        this.serviceRating = serviceRating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}