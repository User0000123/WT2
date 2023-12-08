package by.bsuir.wt2.webapp.classes.service;

import by.bsuir.wt2.webapp.classes.dao.objects.MovieDao;
import by.bsuir.wt2.webapp.classes.dao.objects.OrderDao;
import by.bsuir.wt2.webapp.classes.entities.Movie;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MovieService class provides various operations related to movies.
 * It includes methods for creating a movie, deleting a movie, updating a movie, retrieving a movie by ID,
 * retrieving the movie ID associated with an order, retrieving a list of movies for a page, and retrieving the total count of movies.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class MovieService {

    /**
     * Creates a new movie by adding movie-specific attributes to the database.
     *
     * @param movie the movie to create
     * @return true if the movie is successfully created, false otherwise
     * @throws ServiceException if an error occurs during the creation process
     */
    public boolean createMovie(Movie movie) throws ServiceException {
        try {
            MovieDao movieDao = new MovieDao();
            List<String> attributes =  MovieDao.movieAttributes();
            Map<String, Object> params = MovieDao.movieParams(movie);
            attributes.remove("m_id");
            params.remove("m_id");
            movieDao.addMovie(attributes, params);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Deletes a movie from the database.
     *
     * @param movie the movie to delete
     * @return true if the movie is successfully deleted, false otherwise
     * @throws ServiceException if an error occurs during the delete process
     */
    public boolean deleteMovie(Movie movie) throws ServiceException{
        try {
            MovieDao movieDao = new MovieDao();
            Map<String, Object> orderParams = MovieDao.movieParams(movie);
            movieDao.deleteMovie(OrderDao.orderAttributes(), orderParams);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Updates a movie by updating the movie-specific attributes in the database.
     *
     * @param originalMovie the original movie information
     * @param updatedMovie the updated movie information
     * @return true if the movie is successfully updated, false otherwise
     * @throws ServiceException if an error occurs during the update process
     */
    public boolean updateMovie(Movie originalMovie, Movie updatedMovie) throws ServiceException{
        try {
            MovieDao movieDao = new MovieDao();
            List<String> attributes = MovieDao.movieAttributes();
            Map<String, Object> oldParams = MovieDao.movieParams(originalMovie);
            Map<String, Object> newParams = MovieDao.movieParams(updatedMovie);
            movieDao.updateMovie(attributes, oldParams, attributes, newParams);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Retrieves a movie by its ID from the database.
     *
     * @param id the ID of the movie
     * @return the movie with the specified ID if found, null otherwise
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public Movie getMovieById(int id) throws ServiceException {
        try {
            MovieDao movieDao = new MovieDao();
            List<String> attributes = new ArrayList<>();
            attributes.add("m_id");
            Map<String, Object> params = new HashMap<>();
            params.put("m_id", id);
            movieDao.getMovie("*", attributes, params);
            Map<String, Object> movieParams = movieDao.getMovieSelectionResult(MovieDao
                    .movieAttributes());
            if (movieParams.isEmpty()) {
                return null;
            } else {
                Movie movie = new Movie();
                fillMovieWithParams(movie, movieParams);
                return movie;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the movie ID associated with an order from the database.
     *
     * @param movie the movie to retrieve the ID for
     * @return the movie ID associated with the order if found, null otherwise
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public int getMovieId(Movie movie) throws ServiceException{
        try {
            OrderDao orderDao = new OrderDao();
            List<String> attributes = MovieDao.movieAttributes();
            Map<String, Object> params = MovieDao.movieParams(movie);
            orderDao.getOrder("*",attributes,params);
            Object orderIdObj = orderDao.getOrderSelectionResult("m_id");
            return Integer.parseInt(String.valueOf(orderIdObj));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves a list of movies for a specific page from the database.
     *
     * @param offset the offset of the page
     * @return a list of movies for the page
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public List<Movie> getPageMoviesList(int offset) throws ServiceException{
        try {
            MovieDao movieDao = new MovieDao();
            List<Movie> movies = new ArrayList<>();
            movieDao.getMovieList("*",offset,10);
            List<Map<String, Object>> moviesParams = movieDao.getMoviesSelectionResult(MovieDao
                    .movieAttributes());
            for(Map<String,Object> movieParams : moviesParams) {
                Movie currentMovie = new Movie();
                fillMovieWithParams(currentMovie,movieParams);
                movies.add(currentMovie);
            }
            return movies;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the total count of movies from the database.
     *
     * @return the total count of movies
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public int getTotalMovieCount() throws ServiceException{
        try {
            MovieDao movieDao = new MovieDao();
            int count = movieDao.getTableRowsCount();
            return count;
        } catch (DaoException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Fills a movie object with the provided parameters.
     *
     * @param movie the movie object to fill
     * @param params the parameters to fill the movie object with
     */
    public void fillMovieWithParams(Movie movie, Map<String,Object> params) {
        movie.setId(Integer.parseInt(String.valueOf(params.get("m_id"))));
        movie.setName(String.valueOf(params.get("m_name")));
        movie.setPrice(Double.parseDouble(String.valueOf(params.get("m_price"))));
        movie.setDirector(String.valueOf(params.get("m_director")));
        movie.setDescription(String.valueOf(params.get("m_description")));
        movie.setGenre(String.valueOf(params.get("m_genre")));
    }
}
