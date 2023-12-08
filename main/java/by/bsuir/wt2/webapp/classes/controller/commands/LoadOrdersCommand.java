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
import java.io.IOException;
import java.util.List;

/**
 * This class represents a command for loading orders for the accept page.
 *
 * @author Fedor
 * @since 2023-11-27
 * @version 1.0
 */
public class LoadOrdersCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(LoadOrdersCommand.class.getName());

    /**
     * This method executes the command.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param context The servlet context.
     * @return The name of the page to redirect to.
     * @throws ServletException If an error occurs during execution.
     * @throws IOException If an error occurs during I/O.
     */
    @Override
    public PageAddress execute(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
        PageAddress resultRedirectPage = PageAddresses.ORDER_ACCEPT;
        try {
            OrderService orderService = new OrderService();
            List<Order> orders = orderService.getPageOrdersList(0);
            int ordersCount = orderService.getTotalOrdersCount();
            context.setAttribute("orders_count", ordersCount);
            context.setAttribute("orders", orders);
            context.setAttribute("orders_offset", 0);
            return resultRedirectPage;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while loading orders for accept page", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }
}
