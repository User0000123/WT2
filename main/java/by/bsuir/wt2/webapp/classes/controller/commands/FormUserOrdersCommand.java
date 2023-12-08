package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.Admin;
import by.bsuir.wt2.webapp.classes.entities.Movie;
import by.bsuir.wt2.webapp.classes.entities.Client;
import by.bsuir.wt2.webapp.classes.entities.Order;
import by.bsuir.wt2.webapp.classes.service.AdminService;
import by.bsuir.wt2.webapp.classes.service.ClientService;
import by.bsuir.wt2.webapp.classes.service.LinkTablesService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a command for forming user orders.
 *
 * @author Fedor
 * @version 1.0
 * @since 2023-11-27
 */
public class FormUserOrdersCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(FormUserOrdersCommand.class.getName());

    /**
     * This method completes the command for forming user orders.
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
        PageAddress resultRedirectPage = PageAddresses.USER_ORDERS;
        try {
            HttpSession session = request.getSession();
            ClientService clientService = new ClientService();
            AdminService adminService = new AdminService();
            LinkTablesService linkTablesService = new LinkTablesService();
            if (session.getAttribute("role").equals("client")) {
                Client client = clientService.loginClient(String.valueOf(session.getAttribute("login")),
                        String.valueOf(session.getAttribute("password_hash")), true);
                List<Order> orders = linkTablesService.getOrdersByUser(client);
                List<List<Movie>> orderCourses = new ArrayList<>();
                for (Order order : orders) {
                    orderCourses.add(linkTablesService.getMoviesByOrder(order));
                }
                session.setAttribute("orders", orders);
                session.setAttribute("courses", orderCourses);
            } else {
                Admin admin = adminService.loginAdmin(String.valueOf(session.getAttribute("login")),
                        String.valueOf(session.getAttribute("password_hash")), true);
                List<Order> orders = linkTablesService.getOrdersByUser(admin);
                List<List<Movie>> orderCourses = new ArrayList<>();
                for (Order order : orders) {
                    orderCourses.add(linkTablesService.getMoviesByOrder(order));
                }
                session.setAttribute("orders", orders);
                session.setAttribute("courses", orderCourses);
            }
            return resultRedirectPage;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while getting client/admin orders", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }
}
