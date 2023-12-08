package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.Order;
import by.bsuir.wt2.webapp.classes.service.OrderService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * This class represents a command for accepting orders.
 * It retrieves the changed orders from the session and updates their acceptance status in the database.
 *
 * @author Fedor
 * @since 2023-11-27
 * @version 1.0
 */
public class OrdersAcceptCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(OrdersAcceptCommand.class.getName());

    /**
     * This method executes the command.
     *
     * @param request  The HTTP request.
     * @param response The HTTP response.
     * @param context  The servlet context.
     * @return The name of the page to redirect to.
     * @throws ServletException If an error occurs during execution.
     * @throws IOException      If an error occurs during I/O.
     */
    @Override
    public PageAddress execute(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
        PageAddress resultRedirectPage = PageAddresses.MAIN_PAGE;
        try {
            HttpSession session = request.getSession();
            OrderService orderService = new OrderService();
            if (session.getAttribute("changed_orders") != null) {
                List<Order> changedOrders = (List<Order>) session.getAttribute("changed_orders");
                for (Order order : changedOrders) {
                    Order oldOrder = new Order();
                    oldOrder.setId(order.getId());
                    oldOrder.setSummaryPrice(order.getSummaryPrice());
                    oldOrder.setCreationDate(order.getCreationDate());
                    oldOrder.setAccepted(order.isAccepted());
                    order.setAccepted(!order.isAccepted());
                    orderService.updateOrder(oldOrder, order);
                }
                session.setAttribute("changed_orders", null);
            }
            return resultRedirectPage;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while loading orders for accept page", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }
}
