package by.bsuir.wt2.webapp.classes.dao.objects;

import by.bsuir.wt2.webapp.classes.dao.commands.DeleteCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.InsertionCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.SelectionCommand;
import by.bsuir.wt2.webapp.classes.entities.Admin;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.dao.commands.UpdateCommand;
import by.bsuir.wt2.webapp.classes.dao.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The AdminDao class provides methods for managing admins in the database.
 *
 * @author Aleksej
 * @version 1.0
 * @since 2023-12-07
 */
public class AdminDao {

    private static String adminTableName = "Admins";

    private static ResultSet lastResult = null;

    private static boolean lastResultEmpty = true;

    /**
     * Adds an admin to the database.
     *
     * @param attributes the attributes of the admin
     * @param params the parameters of the admin
     * @throws DaoException if an error occurs while adding the admin
     */
    public void addAdmin(List<String> attributes,
                         Map<String,Object> params) throws DaoException{
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            InsertionCommand.completeCommand(connection,adminTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Updates an admin in the database.
     *
     * @param updateAttributes the attributes to update
     * @param params the parameters of the admin
     * @param selectAttributes the attributes to select
     * @param newParams the new parameters of the admin
     * @throws DaoException if an error occurs while updating the admin
     */
    public void updateAdmin(List<String> updateAttributes, Map<String,Object> params,
                            List<String> selectAttributes, Map<String,Object> newParams)
                            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            UpdateCommand.completeCommand(connection,adminTableName,updateAttributes,params,
                    selectAttributes,newParams);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets an admin from the database.
     *
     * @param selectionAttribute the attribute to select
     * @param attributes the attributes to return
     * @param params the parameters of the admin
     * @throws DaoException if an error occurs while getting the admin
     */
    public void getAdmin(String selectionAttribute, List<String> attributes, Map<String,Object> params)
                         throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            lastResult = SelectionCommand.completeCommand(connection,adminTableName,selectionAttribute,attributes,params);
            lastResultEmpty=!lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Deletes an admin from the database.
     *
     * @param attributes the attributes of the admin
     * @param params the parameters of the admin
     * @throws DaoException if an error occurs while deleting the admin
     */
    public void deleteAdmin(List<String> attributes, Map<String,Object> params) throws DaoException{
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            DeleteCommand.completeCommand(connection,adminTableName,attributes,params);
            lastResultEmpty=!lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }


    /**
     * Gets the number of rows in the admins table.
     *
     * @return the number of rows
     * @throws DaoException if an error occurs while getting the number of rows
     */
    public int getTableRowsCount() throws DaoException{
        int result = -1;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            result = SelectionCommand.selectTableRowsCount(connection,adminTableName);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
        return result;
    }

    /**
     * Retrieves the selection result for a single admin.
     *
     * @param attributes the attributes to retrieve
     * @return the selection result as a map of attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Map<String,Object> getAdminSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultAdmin = new HashMap<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultAdmin.put(attribute, lastResult.getObject(attribute));
                }
            }
            return resultAdmin;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }
    /**
     * Retrieves the selection result for a specific attribute of a single admin.
     *
     * @param attribute the attribute to retrieve
     * @return the value of the attribute
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Object getAdminSelectionResult(String attribute) throws DaoException {
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
     * Retrieves the selection results for multiple admins.
     *
     * @param attributes the attributes to retrieve
     * @return the selection results as a list of maps, where each map represents an admin with attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Map<String,Object>> getAdminsSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultAdminAttributes = new HashMap<>();
        List<Map<String,Object>> resultAdminsAttributes = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultAdminAttributes.put(attribute, lastResult.getObject(attribute));
                }
                resultAdminsAttributes.add(resultAdminAttributes);
                while(lastResult.next()) {
                    resultAdminAttributes = new HashMap<>();
                    for (String attribute : attributes) {
                        resultAdminAttributes.put(attribute, lastResult.getObject(attribute));
                    }
                    resultAdminsAttributes.add(resultAdminAttributes);
                }
                lastResult.first();
            }
            return resultAdminsAttributes;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection results for a specific attribute of multiple admins.
     *
     * @param attribute the attribute to retrieve
     * @return the values of the attribute as a list
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Object> getAdminsSelectionResult(String attribute) throws DaoException {
        List<Object> resultAdminsAttribute = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                resultAdminsAttribute.add(lastResult.getObject(attribute));
                while(lastResult.next()) {
                    resultAdminsAttribute.add(lastResult.getObject(attribute));
                }
            }
            return resultAdminsAttribute;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the attributes of an admin.
     *
     * @return the admin attributes as a list
     */
    public static List<String> adminAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("adm_id");
        attributes.add("adm_is_active");
        return attributes;
    }

    /**
     * Retrieves the parameters of an admin.
     *
     * @param admin the admin object
     * @return the admin parameters as a map of parameter-value pairs
     */
    public static Map<String,Object> adminParams(Admin admin) {
        Map<String,Object> params = new HashMap<>();
        params.put("adm_id",admin.getId());
        if(admin.isActive()) {
            params.put("adm_is_active", 1);
        } else {
            params.put("adm_is_active", 0);
        }
        return params;
    }

}
