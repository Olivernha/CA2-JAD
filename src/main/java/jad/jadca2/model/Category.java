package jad.jadca2.model;


public class Category {
    private int id;
    private String name;
    private String description;
    private String imagePath;
    public Category() {}

    // Constructor with parameters
    public Category(String name, String description) {
        this.name = name;
        this.description = description;

    }

    // Constructor with all parameters
    public Category(int id, String name, String description,String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }
    public Category( String name, String description,String imagePath) {

        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
}