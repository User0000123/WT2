package by.bsuir.wt2.webapp.classes.dao.objects;

import by.bsuir.wt2.webapp.classes.dao.commands.DeleteCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.InsertionCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.SelectionCommand;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.dao.commands.UpdateCommand;
import by.bsuir.wt2.webapp.classes.dao.connection.ConnectionPool;
import by.bsuir.wt2.webapp.classes.entities.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The OrderDao class provides methods for managing orders in the database.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class OrderDao {

    private static String orderTableName = "user_order";

    private static ResultSet lastResult = null;

    private static boolean lastResultEmpty = true;

    /**
     * Adds an order to the database.
     *
     * @param attributes the attributes of the order
     * @param params the parameters of the order
     * @throws DaoException if an error occurs while adding the order
     */
    public void addOrder(List<String> attributes,
                        Map<String,Object> params) throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            InsertionCommand.completeCommand(connection, orderTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Updates an order in the database.
     *
     * @param updateAttributes the attributes to update
     * @param params the parameters of the order
     * @param selectAttributes the attributes to select
     * @param newParams the new parameters of the order
     * @throws DaoException if an error occurs while updating the order
     */
    public void updateOrder(List<String> updateAttributes, Map<String,Object> params,
                           List<String> selectAttributes, Map<String,Object> newParams)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            UpdateCommand.completeCommand(connection, orderTableName,updateAttributes,params,
                    selectAttributes,newParams);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets an order from the database.
     *
     * @param selectionAttribute the attribute to select
     * @param attributes the attributes to return
     * @param params the parameters of the order
     * @throws DaoException if an error occurs while getting the order
     */
    public void getOrder(String selectionAttribute, List<String> attributes, Map<String,Object> params)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            lastResult = SelectionCommand.completeCommand(connection, orderTableName,
                    selectionAttribute,attributes,params);
            lastResultEmpty = !lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Deletes an order from the database.
     *
     * @param attributes the attributes of the order
     * @param params the parameters of the order
     * @throws DaoException if an error occurs while deleting the order
     */
    public void deleteOrder(List<String> attributes, Map<String,Object> params)throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            DeleteCommand.completeCommand(connection, orderTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets the number of rows in the order table.
     *
     * @return the number of rows
     * @throws DaoException if an error occurs while getting the number of rows
     */
    public int getTableRowsCount() throws DaoException{
        int result = -1;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            result = SelectionCommand.selectTableRowsCount(connection,orderTableName);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
        return result;
    }

    /**
     * Gets a list of orders from the database using pagination.
     *
     * @param selectionAttribute the attribute to select
     * @param offset the offset for pagination
     * @param limit the limit for pagination
     * @throws DaoException if an error occurs while getting the order list
     */
    public void getOrderList(String selectionAttribute,int offset,int limit)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            lastResult = SelectionCommand.completeCommandForPagination(connection, orderTableName,
                    selectionAttribute,offset,limit);
            lastResultEmpty=!lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection result for a single order.
     *
     * @param attributes the attributes to retrieve
     * @return the selection result as a map of attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Map<String,Object> getOrderSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultUser = new HashMap<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultUser.put(attribute, lastResult.getObject(attribute));
                }
            }
            return resultUser;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection result for a specific attribute of a single order.
     *
     * @param attribute the attribute to retrieve
     * @return the value of the attribute
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Object getOrderSelectionResult(String attribute) throws DaoException {
        try {
            if(!lastResultEmpty) {
                return lastResult.getObject(attribute);
            }
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
        return null;
    }

    /**
     * Retrieves the selection results for multiple orders.
     *
     * @param attributes the attributes to retrieve
     * @return the selection results as a list of maps, where each map represents an order with attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Map<String,Object>> getOrdersSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultOrder = new HashMap<>();
        List<Map<String,Object>> resultOrders = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultOrder.put(attribute, lastResult.getObject(attribute));
                }
                resultOrders.add(resultOrder);
                while(lastResult.next()) {
                    resultOrder = new HashMap<>();
                    for (String attribute : attributes) {
                        resultOrder.put(attribute, lastResult.getObject(attribute));
                    }
                    resultOrders.add(resultOrder);
                }
                lastResult.first();
            }
            return resultOrders;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection results for a specific attribute of multiple orders.
     *
     * @param attribute the attribute to retrieve
     * @return the values of the attribute as a list
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Object> getOrdersSelectionResult(String attribute) throws DaoException {
        List<Object> resultOrders = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                resultOrders.add(lastResult.getObject(attribute));
                while(lastResult.next()) {
                    resultOrders.add(lastResult.getObject(attribute));
                }
            }
            return resultOrders;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the attributes of an order.
     *
     * @return the order attributes as a list
     */
    public static List<String> orderAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("ord_id");
        attributes.add("ord_creation_date");
        attributes.add("ord_sum_price");
        attributes.add("ord_is_accepted");
        return attributes;
    }

    /**
     * Retrieves the parameters of an order.
     *
     * @param order the order object
     * @return the order parameters as a map of parameter-value pairs
     */
    public static Map<String,Object> orderParams(Order order) {
        Map<String,Object> params = new HashMap<>();
        params.put("ord_id",order.getId());
        params.put("ord_creation_date",order.getCreationDate());
        params.put("ord_sum_price",order.getSummaryPrice());
        if(order.isAccepted()) {
            params.put("ord_is_accepted", 1);
        } else {
            params.put("ord_is_accepted", 0);
        }
        return params;
    }
}
