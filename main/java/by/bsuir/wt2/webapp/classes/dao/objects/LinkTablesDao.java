package by.bsuir.wt2.webapp.classes.dao.objects;

import by.bsuir.wt2.webapp.classes.dao.commands.DeleteCommand;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.dao.commands.InsertionCommand;
import by.bsuir.wt2.webapp.classes.dao.commands.SelectionCommand;
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
 * The LinkTablesDao class provides methods for managing link tables in the database.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class LinkTablesDao {

    private static ResultSet lastResult = null;

    private static boolean lastResultEmpty = true;

    /**
     * Creates a link in the specified link table.
     *
     * @param linkTableName the name of the link table
     * @param attributes the attributes of the link
     * @param params the parameters of the link
     * @throws DaoException if an error occurs while creating the link
     */
    public void createLink(String linkTableName,List<String> attributes,
                         Map<String,Object> params) throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            InsertionCommand.completeCommand(connection, linkTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }
    /**
     * Updates a link in the specified link table.
     *
     * @param linkTableName the name of the link table
     * @param updateAttributes the attributes to update
     * @param params the parameters of the link
     * @param selectAttributes the attributes to select
     * @param newParams the new parameters of the link
     * @throws DaoException if an error occurs while updating the link
     */
    public void updateLink(String linkTableName,List<String> updateAttributes, Map<String,Object> params,
                             List<String> selectAttributes, Map<String,Object> newParams)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            UpdateCommand.completeCommand(connection, linkTableName,updateAttributes,params,
                    selectAttributes,newParams);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves links from the specified link table.
     *
     * @param linkTableName the name of the link table
     * @param attributes the attributes to retrieve
     * @param params the parameters of the links
     * @throws DaoException if an error occurs while retrieving the links
     */
    public void getLinks(String linkTableName, List<String> attributes, Map<String,Object> params)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            lastResult = SelectionCommand.completeCommand(connection, linkTableName,
                    "*",attributes,params);
            lastResultEmpty = !lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Deletes a link from the specified link table.
     *
     * @param linkTableName the name of the link table
     * @param attributes the attributes of the link
     * @param params the parameters of the link
     * @throws DaoException if an error occurs while deleting the link
     */
    public void deleteLink(String linkTableName,List<String> attributes,
                           Map<String,Object> params) throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            DeleteCommand.completeCommand(connection, linkTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets the number of rows in the specified table.
     *
     * @param tableName the name of the table
     * @return the number of rows
     * @throws DaoException if an error occurs while getting the number of rows
     */
    public int getTableRowsCount(String tableName) throws DaoException{
        int result = -1;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            result = SelectionCommand.selectTableRowsCount(connection,tableName);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
        return result;
    }


    /**
     * Retrieves the selection result for a single link.
     *
     * @param attributes the attributes to retrieve
     * @return the selection result as a map of attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Map<String,Object> getLinkSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultLinks = new HashMap<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultLinks.put(attribute, lastResult.getObject(attribute));
                }
            }
            return resultLinks;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }


    /**
     * Retrieves the selection result for a specific attribute of a single link.
     *
     * @param attribute the attribute to retrieve
     * @return the value of the attribute
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Object getLinkSelectionResult(String attribute) throws DaoException {
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
     * Retrieves the selection results for multiple links.
     *
     * @param attributes the attributes to retrieve
     * @return the selection results as a list of maps, where each map represents a link with attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Map<String,Object>> getLinksSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultLink = new HashMap<>();
        List<Map<String,Object>> resultLinks = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultLink.put(attribute, lastResult.getObject(attribute));
                }
                resultLinks.add(resultLink);
                while(lastResult.next()) {
                    resultLink = new HashMap<>();
                    for (String attribute : attributes) {
                        resultLink.put(attribute, lastResult.getObject(attribute));
                    }
                    resultLinks.add(resultLink);
                }
                lastResult.first();
            }
            return resultLinks;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection results for a specific attribute of multiple links.
     *
     * @param attribute the attribute to retrieve
     * @return the values of the attribute as a list
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Object> getLinksSelectionResult(String attribute) throws DaoException {
        List<Object> resultLinks = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                resultLinks.add(lastResult.getObject(attribute));
                while(lastResult.next()) {
                    resultLinks.add(lastResult.getObject(attribute));
                }
            }
            return resultLinks;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }
}
