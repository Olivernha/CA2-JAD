package jad.jadca2.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        String dbUrl = "jdbc:postgresql://ep-spring-lake-a114bnaa.ap-southeast-1.aws.neon.tech/jadAssignment1?sslmode=require";
        String dbUser = "neondb_owner";
        String dbPassword = "TRJNu3Qtckm6";
        String dbClass = "org.postgresql.Driver";

        Connection connection = null;
        try {
            Class.forName(dbClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
