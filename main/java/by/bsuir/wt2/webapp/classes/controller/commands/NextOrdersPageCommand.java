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
 * This class represents a command for navigating to the next page of orders.
 * It updates the offset value in the servlet context and retrieves the next page of orders from the database.
 *
 * @author Fedor
 * @since 2023-11-27
 * @version 1.0
 */
public class NextOrdersPageCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(NextMoviesPageCommand.class.getName());

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
        PageAddress resultRedirectPage = PageAddresses.ORDER_ACCEPT;
        int currentOffset = Integer.parseInt(String.valueOf(context.getAttribute("orders_offset")));
        currentOffset += 10;
        context.setAttribute("orders_offset", currentOffset);
        try {
            OrderService orderService = new OrderService();
            List<Order> orders = orderService.getPageOrdersList(currentOffset);
            context.setAttribute("orders", orders);
            return resultRedirectPage;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while using orders pagination (next pages)", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }
}
