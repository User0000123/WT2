package by.bsuir.wt2.webapp.classes.service;

import by.bsuir.wt2.webapp.classes.dao.objects.MovieDao;
import by.bsuir.wt2.webapp.classes.dao.objects.OrderDao;
import by.bsuir.wt2.webapp.classes.entities.Order;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.exceptions.ServiceException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The OrderService class provides various operations related to orders.
 * It includes methods for creating an order, deleting an order, updating an order,
 * retrieving the ID of an order, and retrieving the total count of orders.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class OrderService {

    /**
     * Creates an order.
     *
     * @param order the order to create
     * @return true if the order is successfully created, false otherwise
     * @throws ServiceException if an error occurs during the creation process
     */
    public boolean createOrder(Order order) throws ServiceException {
        try {
            OrderDao orderDao = new OrderDao();
            List<String> attributes =  OrderDao.orderAttributes();
            Map<String, Object> params = OrderDao.orderParams(order);
            attributes.remove("ord_id");
            params.remove("ord_id");
            orderDao.addOrder(attributes, params);

            return true;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Deletes an order.
     *
     * @param order the order to delete
     * @return true if the order is successfully deleted, false otherwise
     * @throws ServiceException if an error occurs during the deletion process
     */
    public boolean deleteOrder(Order order) throws ServiceException{
        try {
            OrderDao orderDao = new OrderDao();
            Map<String, Object> orderParams = OrderDao.orderParams(order);
            orderDao.deleteOrder(OrderDao.orderAttributes(), orderParams);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Updates an order.
     *
     * @param originalOrder the original order to update
     * @param updatedOrder the updated order
     * @return true if the order is successfully updated, false otherwise
     * @throws ServiceException if an error occurs during the update process
     */
    public boolean updateOrder(Order originalOrder,Order updatedOrder) throws ServiceException{
        try {
            OrderDao userDao = new OrderDao();
            List<String> attributes = OrderDao.orderAttributes();
            Map<String, Object> oldParams = OrderDao.orderParams(originalOrder);
            Map<String, Object> newParams = OrderDao.orderParams(updatedOrder);
            userDao.updateOrder(attributes, oldParams, attributes, newParams);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Retrieves the ID of an order.
     *
     * @param order the order to retrieve the ID for
     * @return the ID of the order
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public int getOrderId(Order order) throws ServiceException{
        try {
            OrderDao orderDao = new OrderDao();
            List<String> attributes = OrderDao.orderAttributes();
            Map<String, Object> params = OrderDao.orderParams(order);
            orderDao.getOrder("*",attributes,params);
            Object orderIdObj = orderDao.getOrderSelectionResult("ord_id");
            return Integer.parseInt(String.valueOf(orderIdObj));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves the total count of orders.
     *
     * @return the total count of orders
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public int getTotalOrdersCount() throws ServiceException{
        try {
            MovieDao movieDao = new MovieDao();
            int count = movieDao.getTableRowsCount();
            return count;
        } catch (DaoException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Retrieves a list of orders for a specific page.
     *
     * @param offset the offset value for pagination
     * @return a list of orders for the specified page
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public List<Order> getPageOrdersList (int offset) throws ServiceException{
        try {
            OrderDao orderDao = new OrderDao();
            List<Order> orders = new ArrayList<>();
            orderDao.getOrderList("*",offset,10);
            List<Map<String, Object>> ordersParams = orderDao.getOrdersSelectionResult(OrderDao
                    .orderAttributes());
            for(Map<String,Object> orderParams : ordersParams) {
                Order currentOrder = new Order();
                fillOrderWithParams(currentOrder,orderParams);
                orders.add(currentOrder);
            }
            return orders;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Fills an order object with the provided parameters.
     *
     * @param order the order object to fill
     * @param params the parameters to fill the order object with
     */
    public void fillOrderWithParams(Order order,Map<String,Object> params) {
        order.setId(Integer.parseInt(String.valueOf(params.get("ord_id"))));
        String dateString = String.valueOf(params.get("ord_creation_date"));
        order.setCreationDate(Date.valueOf(dateString));
        order.setSummaryPrice(Double.parseDouble(String.valueOf(params.get("ord_sum_price"))));
        order.setAccepted(Boolean.parseBoolean(String.valueOf(params.get("ord_is_accepted"))));
    }
}
