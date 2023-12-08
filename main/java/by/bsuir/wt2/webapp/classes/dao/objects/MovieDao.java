package by.bsuir.wt2.webapp.classes.dao.objects;

import by.bsuir.wt2.webapp.classes.dao.commands.DeleteCommand;
import by.bsuir.wt2.webapp.classes.entities.Movie;
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
 * The MovieDao class provides methods for managing movies in the database.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class MovieDao {

    private static final String movieTableName = "movie";

    private static ResultSet lastResult = null;

    private static boolean lastResultEmpty = true;

    /**
     * Adds a movie to the database.
     *
     * @param attributes the attributes of the movie
     * @param params the parameters of the movie
     * @throws DaoException if an error occurs while adding the movie
     */
    public void addMovie(List<String> attributes,
                         Map<String,Object> params) throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            InsertionCommand.completeCommand(connection, movieTableName,attributes,params);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Updates a movie in the database.
     *
     * @param updateAttributes the attributes to update
     * @param params the parameters of the movie
     * @param selectAttributes the attributes to select
     * @param newParams the new parameters of the movie
     * @throws DaoException if an error occurs while updating the movie
     */
    public void updateMovie(List<String> updateAttributes, Map<String,Object> params,
                            List<String> selectAttributes, Map<String,Object> newParams)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            UpdateCommand.completeCommand(connection, movieTableName,updateAttributes,params,
                    selectAttributes,newParams);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets a movie from the database.
     *
     * @param selectionAttribute the attribute to select
     * @param attributes the attributes to return
     * @param params the parameters of the movie
     * @throws DaoException if an error occurs while getting the movie
     */
    public void getMovie(String selectionAttribute, List<String> attributes, Map<String,Object> params)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            lastResult = SelectionCommand.completeCommand(connection, movieTableName,selectionAttribute,attributes,params);
            lastResultEmpty=!lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Deletes a movie from the database.
     *
     * @param attributes the attributes of the movie
     * @param params the parameters of the movie
     * @throws DaoException if an error occurs while getting the movie
     */
    public void deleteMovie(List<String> attributes, Map<String,Object> params) throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            DeleteCommand.completeCommand(connection, movieTableName,attributes,params);
            lastResultEmpty=!lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Gets the number of rows in the movie table.
     *
     * @return the number of rows
     * @throws DaoException if an error occurs while getting the number of rows
     */
    public int getTableRowsCount() throws DaoException{
        int result = -1;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            result = SelectionCommand.selectTableRowsCount(connection, movieTableName);
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
        return result;
    }

    /**
     * Gets a list of movies from the database using pagination.
     *
     * @param selectionAttribute the attribute to select
     * @param offset the offset for pagination
     * @param limit the limit for pagination
     * @throws DaoException if an error occurs while getting the movie list
     */
    public void getMovieList(String selectionAttribute, int offset, int limit)
            throws DaoException {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            lastResult = SelectionCommand.completeCommandForPagination(connection, movieTableName,selectionAttribute,offset,limit);
            lastResultEmpty=!lastResult.next();
            pool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection result for a single movie.
     *
     * @param attributes the attributes to retrieve
     * @return the selection result as a map of attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Map<String,Object> getMovieSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultOrder = new HashMap<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultOrder.put(attribute, lastResult.getObject(attribute));
                }
            }
            return resultOrder;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection result for a specific attribute of a single movie.
     *
     * @param attribute the attribute to retrieve
     * @return the value of the attribute
     * @throws DaoException if an error occurs while retrieving the selection result
     */
    public Map<String,Object> getMovieSelectionResult(String attribute) throws DaoException {
        Map<String,Object> resultOrder = new HashMap<>();
        try {
            if(!lastResultEmpty) {
                resultOrder.put(attribute, lastResult.getObject(attribute));
            }
            return resultOrder;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selection results for multiple movies.
     *
     * @param attributes the attributes to retrieve
     * @return the selection results as a list of maps, where each map represents a movie with attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection results
     */

    public List<Map<String,Object>> getMoviesSelectionResult(List<String> attributes) throws DaoException {
        Map<String,Object> resultMovieAttributes = new HashMap<>();
        List<Map<String,Object>> resultMoviesAttributes = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                for (String attribute : attributes) {
                    resultMovieAttributes.put(attribute, lastResult.getObject(attribute));
                }
                resultMoviesAttributes.add(resultMovieAttributes);
                while(lastResult.next()) {
                    resultMovieAttributes = new HashMap<>();
                    for (String attribute : attributes) {
                        resultMovieAttributes.put(attribute, lastResult.getObject(attribute));
                    }
                    resultMoviesAttributes.add(resultMovieAttributes);
                }
                lastResult.first();
            }
            return resultMoviesAttributes;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the selected attribute values for multiple movies.
     *
     * @param attribute the attribute to retrieve
     * @return the selection results as a list of maps, where each map represents a movie with attribute-value pairs
     * @throws DaoException if an error occurs while retrieving the selection results
     */
    public List<Object> getMoviesSelectionResult(String attribute) throws DaoException {
        List<Object> resultMoviesAttribute = new ArrayList<>();
        try {
            if(!lastResultEmpty) {
                resultMoviesAttribute.add(lastResult.getObject(attribute));
                while(lastResult.next()) {
                    resultMoviesAttribute.add(lastResult.getObject(attribute));
                }
            }
            return resultMoviesAttribute;
        }catch (SQLException e){
            throw new DaoException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the attributes of a movie.
     *
     * @return the movie attributes as a list
     */
    public static List<String> movieAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("m_id");
        attributes.add("m_name");
        attributes.add("m_price");
        attributes.add("m_director");
        attributes.add("m_description");
        attributes.add("m_genre");
        return attributes;
    }

    /**
     * Retrieves the parameters of a movie.
     *
     * @param movie the movie object
     * @return the movie parameters as a map of parameter-value pairs
     */
    public static Map<String,Object> movieParams(Movie movie) {
        Map<String,Object> params = new HashMap<>();
        params.put("m_id", movie.getId());
        params.put("m_name", movie.getName());
        params.put("m_price", movie.getPrice());
        params.put("m_director", movie.getDirector());
        params.put("m_description", movie.getDescription());
        params.put("m_genre", movie.getGenre());
        return params;
    }
}
