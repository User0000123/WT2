package by.bsuir.wt2.webapp.classes.dao.objects;

import by.bsuir.wt2.webapp.classes.dao.commands.DeleteCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.InsertionCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.SelectionCommand;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.dao.commands.UpdateCommand;
import by.bsuir.wt2.webapp.classes.dao.connection.ConnectionPool;
import by.bsuir.wt2.webapp.classes.entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The UserDao class provides methods for managing users in the database.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class UserDao {

    private static String userTableName = "users";

    private static ResultSet lastResult = null;

    private static boolean lastResultEmpty = true;

    /**
     * Adds a user to the database.
     *
     * @param attributes the attributes of the user
     * @param params the parameters of the user
     * @throws DaoException if an error occurs while adding the user
     */
    public void addUser(List<String> attributes,
                         Map<String,Object> params) throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            InsertionCommand.completeCommand(connection, userTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Updates a user in the database.
     *
     * @param updateAttributes the attributes to update
     * @param params the parameters of the user
     * @param selectAttributes the attributes to select
     * @param newParams the new parameters of the user
     * @throws DaoException if an error occurs while updating the user
     */
    public void updateUser(List<String> updateAttributes, Map<String,Object> params,
                            List<String> selectAttributes, Map<String,Object> newParams)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            UpdateCommand.completeCommand(connection, userTableName,updateAttributes,params,
                    selectAttributes,newParams);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets a user from the database.
     *
     * @param selectionAttribute the attribute to select
     * @param attributes the attributes to return
     * @param params the parameters of the user
     * @throws DaoException if an error occurs while getting the user
     */
    public void getUser(String selectionAttribute, List<String> attributes, Map<String,Object> params)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            lastResult = SelectionCommand.completeCommand(connection, userTableName,selectionAttribute,attributes,params);
            lastResultEmpty = !lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param attributes the attributes of the user
     * @param params the parameters of the user
     * @throws DaoException if an error occurs while deleting the user
     */
    public void deleteUser(List<String> attributes, Map<String,Object> params)throws DaoException {
        try{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            DeleteCommand.completeCommand(connection, userTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets the number of rows in the user table.
     *
     * @return the number of rows
     * @throws DaoException if an error occurs while getting the number of rows
     */
    public int getTableRowsCount() throws DaoException{
        int result = -1;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            result = SelectionCommand.selectTableRowsCount(connection,userTableName);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
        return result;
    }

    /**
     * Retrieves the user ID from the last selection result.
     *
     * @return the user ID
     * @throws DaoException if an error occurs while retrieving the user ID
     */
    public String getUserById() throws DaoException {
        try {
            if(!lastResultEmpty) {
                return lastResult.getString("u_id");
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
        return null;
    }

    /**
     * Retrieves the selection result for a single user.
     *
     * @param attributes the attributes to retrieve
     * @return the selection result as a map of attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Map<String,Object> getUserSelectionResult(List<String> attributes) throws DaoException {
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
     * Retrieves the selection result for a specific attribute of a single user.
     *
     * @param attribute the attribute to retrieve
     * @return the value of the attribute
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Object getUserSelectionResult(String attribute) throws DaoException {
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
     * Retrieves the selection results for multiple users.
     *
     * @param attributes the attributes to retrieve
     * @return the selection results as a list of maps, where each map represents a user with attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Map<String,Object>> getUsersSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultUserAttributes = new HashMap<>();
        List<Map<String,Object>> resultUsersAttributes = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultUserAttributes.put(attribute, lastResult.getObject(attribute));
                }
                resultUsersAttributes.add(resultUserAttributes);
                while(lastResult.next()) {
                    resultUserAttributes = new HashMap<>();
                    for (String attribute : attributes) {
                        resultUserAttributes.put(attribute, lastResult.getObject(attribute));
                    }
                    resultUsersAttributes.add(resultUserAttributes);
                }
                lastResult.first();
            }
            return resultUsersAttributes;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection results for a specific attribute of multiple users.
     *
     * @param attribute the attribute to retrieve
     * @return the values of the attribute as a list
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Object> getUsersSelectionResult(String attribute) throws DaoException {
        List<Object> resultUsers = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                resultUsers.add(lastResult.getObject(attribute));
                while(lastResult.next()) {
                    resultUsers.add(lastResult.getObject(attribute));
                }
            }
            return resultUsers;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the attributes of a user.
     *
     * @return the user attributes as a list
     */
    public static List<String> userAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("u_id");
        attributes.add("u_name");
        attributes.add("u_surname");
        attributes.add("u_phone_num");
        attributes.add("u_email");
        attributes.add("u_login");
        attributes.add("u_pass_hash");
        attributes.add("u_reg_date");
        return attributes;
    }

    /**
     * Retrieves the parameters of a user.
     *
     * @param user the user object
     * @return the user parameters as a map of parameter-value pairs
     */
    public static Map<String,Object> userParams(User user) {
        Map<String,Object> params = new HashMap<>();
        params.put("u_id", user.getId());
        params.put("u_name", user.getName());
        params.put("u_surname", user.getSurname());
        params.put("u_phone_num", user.getPhoneNumber());
        params.put("u_email", user.getEmail());
        params.put("u_login", user.getLogin());
        params.put("u_pass_hash", user.getPassword());
        params.put("u_reg_date", user.getRegistrationDate());
        return params;
    }
}
