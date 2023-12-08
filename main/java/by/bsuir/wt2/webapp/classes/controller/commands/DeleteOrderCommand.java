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
 * This class represents a command for deleting an order.
 *
 * @author Fedor
 * @version 1.0
 * @since 2023-11-27
 */
public class DeleteOrderCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(DeleteOrderCommand.class.getName());

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
        PageAddress resultRedirectPage = PageAddresses.MAIN_PAGE;
        try {
            OrderService orderService = new OrderService();
            int ordersOffset = Integer.parseInt(String
                    .valueOf(context.getAttribute("orders_offset")));
            List<Order> currentOrderPage = orderService.getPageOrdersList(ordersOffset);
            orderService.deleteOrder(currentOrderPage.get(Integer
                    .parseInt(request.getParameter("orderId"))));
            request.getSession().setAttribute("changed_orders",null);
            context.setAttribute("orders",null);
            context.setAttribute("orders_offset",null);
            context.setAttribute("orders_count",null);
            return resultRedirectPage;
        }catch (Exception e){
            resultRedirectPage= PageAddresses.ERROR_PAGE;
            logger.log(Level.ERROR,"Error while deleting order",e);
            return resultRedirectPage;
        }
    }
}
