package by.bsuir.wt2.webapp.classes.service;

import by.bsuir.wt2.webapp.classes.dao.objects.MovieDao;
import by.bsuir.wt2.webapp.classes.dao.objects.LinkTablesDao;
import by.bsuir.wt2.webapp.classes.dao.objects.OrderDao;
import by.bsuir.wt2.webapp.classes.dao.objects.UserDao;
import by.bsuir.wt2.webapp.classes.entities.Movie;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.exceptions.ServiceException;
import by.bsuir.wt2.webapp.classes.entities.Order;
import by.bsuir.wt2.webapp.classes.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The LinkTablesService class provides various operations related to linking tables.
 * It includes methods for creating links between users and orders, creating links between orders and movies,
 * retrieving orders by user, retrieving movies by order, and utility methods for link attributes and parameters.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class LinkTablesService {
    /**
     * Creates a link between a user and an order in the linking table.
     *
     * @param user the user to link
     * @param order the order to link
     * @return true if the link is successfully created, false otherwise
     * @throws ServiceException if an error occurs during the creation process
     */
    public boolean createLinkUserOrder(User user, Order order) throws ServiceException {
        try {
            UserDao userDao = new UserDao();
            OrderDao orderDao = new OrderDao();
            LinkTablesDao tablesDao = new LinkTablesDao();
            List<String> attributes =  UserDao.userAttributes();
            Map<String, Object> params = UserDao.userParams(user);
            userDao.getUser("*",attributes,params);
            attributes.remove("u_id");
            params.remove("u_id");
            Object userId = userDao.getUserSelectionResult("u_id");
            attributes =  OrderDao.orderAttributes();
            params = OrderDao.orderParams(order);
            attributes.remove("ord_id");
            params.remove("ord_id");
            orderDao.getOrder("*",attributes,params);
            List<Object> orderIds = orderDao.getOrdersSelectionResult("ord_id");
            int orderId = Integer.parseInt(String.valueOf(orderIds.get(orderIds.size() - 1)));
            tablesDao.createLink("users_orders",linkAttributes("u_id","ord_id"),
                    linkParam("u_id","ord_id",userId, orderId));
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Creates a link between an order and a movie in the linking table.
     *
     * @param movie the movie to link
     * @param order the order to link
     * @return true if the link is successfully created, false otherwise
     * @throws ServiceException if an error occurs during the creation process
     */
    public boolean createLinkOrderMovie(Movie movie, Order order) throws ServiceException {
        try {
            MovieDao movieDao = new MovieDao();
            OrderDao orderDao = new OrderDao();
            LinkTablesDao tablesDao = new LinkTablesDao();
            List<String> attributes =  MovieDao.movieAttributes();
            Map<String, Object> params = MovieDao.movieParams(movie);
            attributes.remove("m_id");
            params.remove("m_id");
            movieDao.getMovie("*",attributes,params);
            Map<String,Object> movieId = movieDao.getMovieSelectionResult("m_id");
            attributes =  OrderDao.orderAttributes();
            params = OrderDao.orderParams(order);
            attributes.remove("ord_id");
            params.remove("ord_id");
            orderDao.getOrder("*",attributes,params);
            List<Object> orderIds = orderDao.getOrdersSelectionResult("ord_id");
            int orderId = Integer.parseInt(String.valueOf(orderIds.get(orderIds.size() - 1)));
            tablesDao.createLink("orders_movies",linkAttributes("m_id","ord_id"),
                    linkParam("m_id","ord_id",movieId.get("m_id"), orderId));
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves a list of orders associated with a user from the linking table.
     *
     * @param user the user to retrieve orders for
     * @return a list of orders associated with the user
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public List<Order> getOrdersByUser(User user) throws ServiceException {
        try {
            List<Order> result = new ArrayList<>();
            OrderService orderService = new OrderService();
            UserDao userDao = new UserDao();
            OrderDao orderDao = new OrderDao();
            LinkTablesDao linkTablesDao = new LinkTablesDao();
            userDao.getUser("*", UserDao.userAttributes(),
                    UserDao.userParams(user));
            Object clientId = userDao.getUserSelectionResult("u_id");
            List<String> attributes = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            attributes.add("u_id");
            params.put("u_id", clientId);
            linkTablesDao.getLinks("users_orders", attributes, params);
            List<Object> userOrdersLink = linkTablesDao.getLinksSelectionResult("ord_id");
            for (Object orderIdObj : userOrdersLink) {
                int orderId = Integer.parseInt(String.valueOf(orderIdObj));
                List<String> orderAttributes = new ArrayList<>();
                Map<String, Object> orderParams = new HashMap<>();
                orderAttributes.add("ord_id");
                orderParams.put("ord_id", orderId);
                orderDao.getOrder("*", orderAttributes, orderParams);
                Map<String, Object> fullOrderParams = orderDao
                        .getOrderSelectionResult(OrderDao.orderAttributes());
                Order order = new Order();
                orderService.fillOrderWithParams(order, fullOrderParams);
                result.add(order);
            }
            return result;
        }catch (DaoException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves a list of movies associated with an order from the linking table.
     *
     * @param order the order to retrieve movies for
     * @return a list of movies associated with the order
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public List<Movie> getMoviesByOrder(Order order) throws  ServiceException {
        try {
            List<Movie> result = new ArrayList<>();
            MovieService movieService = new MovieService();
            MovieDao movieDao = new MovieDao();
            OrderDao orderDao = new OrderDao();
            LinkTablesDao linkTablesDao = new LinkTablesDao();
            orderDao.getOrder("*", OrderDao.orderAttributes(),
                    OrderDao.orderParams(order));
            Object orderId = orderDao.getOrderSelectionResult("ord_id");
            List<String> attributes = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            attributes.add("ord_id");
            params.put("ord_id", orderId);
            linkTablesDao.getLinks("orders_movies", attributes, params);
            List<Object> orderMoviesLink = linkTablesDao.getLinksSelectionResult("m_id");
            for (Object movieIdObj : orderMoviesLink) {
                int movieId = Integer.parseInt(String.valueOf(movieIdObj));
                List<String> movieAttributes = new ArrayList<>();
                Map<String, Object> movieParams = new HashMap<>();
                movieAttributes.add("m_id");
                movieParams.put("m_id", movieId);
                movieDao.getMovie("*", movieAttributes, movieParams);
                Map<String, Object> fullMovieParams = movieDao
                        .getMovieSelectionResult(MovieDao.movieAttributes());
                Movie movie = new Movie();
                movieService.fillMovieWithParams(movie, fullMovieParams);
                result.add(movie);
            }
            return result;
        }catch (DaoException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Utility method to create a list of link attributes.
     *
     * @param firstIdName the name of the first ID attribute
     * @param secondIdName the name of the second ID attribute
     * @return a list of link attributes
     */
    public static List<String> linkAttributes(String firstIdName,String secondIdName) {
        List<String> attributes = new ArrayList<>();
        attributes.add(firstIdName);
        attributes.add(secondIdName);
        return attributes;
    }

    /**
     * Utility method to create a map of link parameters.
     *
     * @param firstIdName the name of the first ID attribute
     * @param secondIdName the name of the second ID attribute
     * @param firstParam the value of the first ID parameter
     * @param secondParam the value of the second ID parameter
     * @return a map of link parameters
     */
    public static Map<String,Object> linkParam(String firstIdName,String secondIdName,
                                               Object firstParam,Object secondParam) {
        Map<String,Object> params = new HashMap<>();
        params.put(firstIdName,firstParam);
        params.put(secondIdName,secondParam);
        return params;
    }
}
