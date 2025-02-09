package jad.jadca2.dao;

import jad.jadca2.model.Customer;
import jad.jadca2.dbaccess.DBConnection;
import jad.jadca2.utils.Bcrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private Connection connection;

    // Constructor that initializes the connection
    public AdminDAO() {
        // Initialize the connection using your DatabaseConnection utility class
        this.connection = DBConnection.getConnection();
    }
    public boolean createCustomer(Customer customer) {
        String sql = "INSERT INTO users (username, email, phone, address, role, password, postal_code, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getRole());
            // Assuming you're handling password hashing similarly to your update method
            String hashedPassword = Bcrypt.hashpw(customer.getPassword(), Bcrypt.gensalt());
            stmt.setString(6, hashedPassword);
            stmt.setString(7, customer.getPostalCode());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY user_id";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("user_id"));
                customer.setName(rs.getString("username"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setRole(rs.getString("role"));
                customer.setAddress(rs.getString("address"));
                customer.setAvatar(rs.getString("avatar"));
                customer.setPostalCode(rs.getString("postal_code"));
                // date to string;
                customer.setJoinedDate(rs.getDate("created_at").toLocalDate());
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public boolean deleteCustomer(int userId) {
        String sql = "DELETE FROM users WHERE  user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String query = "SELECT * FROM users WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    customer = new Customer();
                    customer.setId(resultSet.getInt("user_id"));
                    customer.setName(resultSet.getString("username"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setPhone(resultSet.getString("phone"));
                    customer.setAddress(resultSet.getString("address"));
                    customer.setRole(resultSet.getString("role"));
                    customer.setJoinedDate(resultSet.getDate("created_at").toLocalDate());
                    customer.setAvatar(resultSet.getString("avatar"));
                    customer.setPostalCode(resultSet.getString("postal_code"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }


    // Update customer in the database
    public boolean updateCustomer(Customer customer, String newPassword) {
        String sql;

        // Decide whether to include the password update
        if (newPassword == null || newPassword.isEmpty()) {
            sql = "UPDATE users SET username = ?, email = ?, phone = ?, address = ? , avatar = ? ,postal_code = ? WHERE user_id = ?";
        } else {
            sql = "UPDATE users SET username = ?, email = ?, phone = ?, address = ?, avatar = ? , password = ? WHERE user_id = ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for common fields
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getAvatar());
            preparedStatement.setString(6, customer.getPostalCode());

            // Include the password if provided
            if (newPassword != null && !newPassword.isEmpty()) {
                String hashedPassword = Bcrypt.hashpw(newPassword, Bcrypt.gensalt());
                preparedStatement.setString(7, hashedPassword);
                preparedStatement.setInt(8, customer.getId());
            } else {
                preparedStatement.setInt(7, customer.getId());
            }

            // Execute update
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Customer> getCustomersByAreaCode(String areaCode) {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM users WHERE postal_code LIKE  ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + areaCode + "%" );
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("user_id"));
                    customer.setName(rs.getString("username"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setRole(rs.getString("role"));
                    customer.setAddress(rs.getString("address"));
                    customer.setJoinedDate(rs.getDate("created_at").toLocalDate());
                    customer.setPostalCode(rs.getString("postal_code"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
