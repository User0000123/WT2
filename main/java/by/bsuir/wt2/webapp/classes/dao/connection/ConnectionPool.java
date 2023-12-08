package by.bsuir.wt2.webapp.classes.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The ConnectionPool class is responsible for managing a pool of database connections.
 * It provides methods to create, release, and get connections from the pool.
 *
 * @author Aleksej
 * @version 1.0
 * @since 2023-12-07
 */
public class ConnectionPool {

    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> usedConnections;

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    private ConnectionPool() {

    }
    /**
     * Initializes the connection pool.
     *
     * @throws SQLException if an error occurs while initializing the pool
     */
    public void initialize() throws SQLException {
        int poolSize = 50;
        availableConnections = new ArrayBlockingQueue<>(poolSize);
        usedConnections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = ConnectionFactory.createConnection();
            availableConnections.add(connection);
        }
    }

    /**
     * Releases a connection to the pool.
     *
     * @param proxyConnection the connection to release
     * @throws SQLException if an error occurs while releasing the connection
     */
    public void releaseConnection(Connection proxyConnection) throws SQLException {
        if (proxyConnection != null) {
            usedConnections.remove(proxyConnection);
            try {
                availableConnections.put(proxyConnection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new SQLException(e.getMessage(), e);
            }
        }
    }

    /**
     * Gets a connection from the pool.
     *
     * @return the connection
     * @throws SQLException if an error occurs while getting the connection
     */
    public Connection getConnection() throws SQLException {
        Connection connection;
        try {
            connection = availableConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException(e.getMessage(), e);
        }
        return connection;
    }
    /**
     * Destroys the connection pool.
     *
     * @throws SQLException if an error occurs while destroying the pool
     */
    public void destroy() throws SQLException {
        try {
            for (Connection connection : availableConnections) {
                connection.close();
            }
            for (Connection connection : usedConnections) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        }

    }
}
