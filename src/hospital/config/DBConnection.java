package hospital.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DBConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("db.properties NOT FOUND in classpath");
            }

            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load DB properties", e);
        }
    }

    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        props.getProperty("url"),
                        props.getProperty("user"),
                        props.getProperty("password"));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("MySQL Driver not found.", e);
            }
        }

        return connection;
    }
}