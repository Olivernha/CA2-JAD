package jad.jadca2.dao;



import jad.jadca2.dbaccess.DBConnection;
import jad.jadca2.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private Connection connection;

    // Constructor that initializes the connection
    public CategoryDAO() {
        // Initialize the connection using your DatabaseConnection utility class
        this.connection = DBConnection.getConnection();
    }

    // Fetch all categories from the database
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT category_id, category_name, description ,image_path FROM service_category ORDER BY category_id";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
                category.setDescription(resultSet.getString("description"));
                category.setImagePath(resultSet.getString("image_path"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Add a new category to the database
    public boolean addCategory(Category category) {
        boolean success = false;
        String query = "INSERT INTO service_category(category_name, description,image_path) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());
            pstmt.setString(3,category.getImagePath());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
    public String getCategoryNameByServiceId(int serviceId) {
        String categoryName = null;
        String query = "SELECT sc.category_name " +
                "FROM service_category sc " +
                "JOIN service s ON sc.category_id = s.category_id " +
                "WHERE s.service_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, serviceId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    categoryName = resultSet.getString("category_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryName;
    }
    // Update an existing category in the database
    public boolean updateCategory(Category category) {
        boolean success = false;
        String query = "UPDATE service_category SET category_name = ?, description = ? ,image_path = ? WHERE category_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());

            stmt.setString(3, category.getImagePath());
            stmt.setInt(4, category.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    // Fetch a category by its ID
    public Category getCategoryById(int categoryId) {
        Category category = null;
        String query = "SELECT category_id, category_name, description,image_path FROM service_category WHERE category_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    category = new Category();
                    category.setId(resultSet.getInt("category_id"));
                    category.setName(resultSet.getString("category_name"));
                    category.setDescription(resultSet.getString("description"));
                    category.setImagePath(resultSet.getString("image_path"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }
    public boolean deleteCategory(int categoryId) {
        boolean success = false;
        String query = "DELETE FROM service_category WHERE category_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
    public double getLowestPriceByCategoryId(int categoryId) {
        double lowestPrice = 0.0;
        String query = "SELECT MIN(s.price) AS lowest_price " +
                "FROM service s " +
                "JOIN service_category sc ON s.category_id = sc.category_id " +
                "WHERE sc.category_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    lowestPrice = resultSet.getDouble("lowest_price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lowestPrice;
    }
    // Close the connection (optional, if using an auto-closeable connection)
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
