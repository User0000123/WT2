package by.bsuir.wt2.webapp.classes.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The ConnectionFactory class is responsible for creating a database connection.
 * It uses the JDBC driver to establish a connection to the specified database URL.
 * The class provides a static method to create the connection.
 *
 * @author Aleksej
 * @version 1.0
 * @since 2023-12-07
 */
public class ConnectionFactory {
    private static final String DB_URL = "localhost/onlineshop";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";
    private static final String DB_DRIVER = "jdbc:mysql://";

    /**
     * Creates a database connection.
     *
     * @return the database connection
     * @throws SQLException if an error occurs while creating the connection
     */
    static Connection createConnection() throws SQLException {
        Connection proxyConnection = null;
        try {
            proxyConnection = DriverManager.getConnection(DB_DRIVER + DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        }
        return proxyConnection;
    }
}
