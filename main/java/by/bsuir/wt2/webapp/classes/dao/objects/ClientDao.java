package by.bsuir.wt2.webapp.classes.dao.objects;

import by.bsuir.wt2.webapp.classes.dao.commands.DeleteCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.InsertionCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.SelectionCommand;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.dao.commands.UpdateCommand;
import by.bsuir.wt2.webapp.classes.dao.connection.ConnectionPool;
import by.bsuir.wt2.webapp.classes.entities.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ClientDao class provides methods for managing clients in the database.
 *
 * @version 1.0
 * @author Fedor
 * @since 2023-11-29
 */
public class ClientDao {

    private static String clientTableName = "clients";

    private static ResultSet lastResult = null;

    private static boolean lastResultEmpty = true;

    /**
     * Adds a client to the database.
     *
     * @param attributes the attributes of the client
     * @param params the parameters of the client
     * @throws DaoException if an error occurs while adding the client
     */
    public void addClient(List<String> attributes,
                         Map<String,Object> params) throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            InsertionCommand.completeCommand(connection,clientTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Updates a client in the database.
     *
     * @param updateAttributes the attributes to update
     * @param params the parameters of the client
     * @param selectAttributes the attributes to select
     * @param newParams the new parameters of the client
     * @throws DaoException if an error occurs while updating the client
     */
    public void updateClient(List<String> updateAttributes, Map<String,Object> params,
                            List<String> selectAttributes, Map<String,Object> newParams)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            UpdateCommand.completeCommand(connection,clientTableName,updateAttributes,params,
                    selectAttributes,newParams);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets a client from the database.
     *
     * @param selectionAttribute the attribute to select
     * @param attributes the attributes to return
     * @param params the parameters of the client
     * @throws DaoException if an error occurs while getting the client
     */
    public void getClient(String selectionAttribute, List<String> attributes, Map<String,Object> params)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            lastResult = SelectionCommand.completeCommand(connection,clientTableName,selectionAttribute,attributes,params);
            lastResultEmpty=!lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Deletes a client from the database.
     *
     * @param attributes the attributes of the client
     * @param params the parameters of the client
     * @throws DaoException if an error occurs while deleting the client
     */
    public void deleteClient(List<String> attributes, Map<String,Object> params)throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            DeleteCommand.completeCommand(connection,clientTableName,attributes,params);
            lastResultEmpty=!lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets the number of rows in the clients table.
     *
     * @return the number of rows
     * @throws DaoException if an error occurs while getting the number of rows
     */
    public int getTableRowsCount() throws DaoException{
        int result = -1;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            result = SelectionCommand.selectTableRowsCount(connection,clientTableName);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
        return result;
    }

    /**
     * Retrieves the selection result for a single client.
     *
     * @param attributes the attributes to retrieve
     * @return the selection result as a map of attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Map<String,Object> getClientSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultClient = new HashMap<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultClient.put(attribute, lastResult.getObject(attribute));
                }
            }
            return resultClient;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection result for a specific attribute of a single client.
     *
     * @param attribute the attribute to retrieve
     * @return the value of the attribute
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Object getClientSelectionResult(String attribute) throws DaoException {
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
     * Retrieves the selection results for multiple clients.
     *
     * @param attributes the attributes to retrieve
     * @return the selection results as a list of maps, where each map represents a client with attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Map<String,Object>> getClientsSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultClientAttributes = new HashMap<>();
        List<Map<String,Object>> resultClientsAttributes = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultClientAttributes.put(attribute, lastResult.getObject(attribute));
                }
                resultClientsAttributes.add(resultClientAttributes);
                while(lastResult.next()) {
                    resultClientAttributes = new HashMap<>();
                    for (String attribute : attributes) {
                        resultClientAttributes.put(attribute, lastResult.getObject(attribute));
                    }
                    resultClientsAttributes.add(resultClientAttributes);
                }
                lastResult.first();
            }
            return resultClientsAttributes;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection results for a specific attribute of multiple clients.
     *
     * @param attribute the attribute to retrieve
     * @return the values of the attribute as a list
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Object> getClientsSelectionResult(String attribute) throws DaoException {
        List<Object> resultClientsAttribute = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                resultClientsAttribute.add(lastResult.getObject(attribute));
                while(lastResult.next()) {
                    resultClientsAttribute.add(lastResult.getObject(attribute));
                }
            }
            return resultClientsAttribute;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the attributes of a client.
     *
     * @return the client attributes as a list
     */
    public static List<String> clientAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("cl_id");
        attributes.add("cl_is_banned");
        return attributes;
    }

    /**
     * Retrieves the parameters of a client.
     *
     * @param client the client object
     * @return the client parameters as a map of parameter-value pairs
     */
    public static Map<String,Object> clientParams(Client client) {
        Map<String,Object> params = new HashMap<>();
        params.put("cl_id", client.getId());
        if(client.isBanned()) {
            params.put("cl_is_banned", 1);
        } else {
            params.put("cl_is_banned", 0);
        }
        return params;
    }
}
