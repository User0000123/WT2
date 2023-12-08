package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.Order;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    /**
     * The ChangeOrderStatusCommand class implements the ICommand interface and is
     * responsible for changing the status of an order.
     *
     * @author Aleksej
     * @version 1.0
     * @since 2023-12-07
     */
    public class ChangeOrderStatusCommand implements ICommand {

        /**
         * Changes the status of an order.
         *
         * @param request  The HttpServletRequest object.
         * @param response The HttpServletResponse object.
         * @param context  The ServletContext object.
         * @return The name of the page to redirect to.
         * @throws ServletException If an error occurs during execution.
         * @throws IOException If an error occurs during I/O.
         */
        @Override
        public PageAddress execute(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
            PageAddress resultRedirectPage = PageAddresses.ORDER_ACCEPT;
            HttpSession session = request.getSession();
            if (session.getAttribute("changed_orders") == null) {
                List<Order> changedOrders = new ArrayList<>();
                List<Order> orders = (List<Order>) context.getAttribute("orders");
                Order changedOrder = orders.get(Integer.parseInt(request.getParameter("orderId")));
                changedOrders.add(changedOrder);
                session.setAttribute("changed_orders", changedOrders);
            } else {
                List<Order> changedOrders = (List<Order>) session.getAttribute("changed_orders");
                List<Order> orders = (List<Order>) context.getAttribute("orders");
                Order changedOrder = orders.get(Integer.parseInt(request.getParameter("orderId")));
                if (changedOrders.contains(changedOrder)) {
                    changedOrders.remove(changedOrder);
                } else {
                    changedOrders.add(changedOrder);
                }
                session.setAttribute("changed_orders", changedOrders);
            }
            return resultRedirectPage;
        }
}
