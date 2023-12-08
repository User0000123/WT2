package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.Movie;
import by.bsuir.wt2.webapp.classes.entities.Order;
import by.bsuir.wt2.webapp.classes.entities.User;
import by.bsuir.wt2.webapp.classes.service.*;
import by.bsuir.wtl2.webapp.classes.service.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;


/**
 * The CreateOrderCommand class implements the ICommand interface and is responsible for creating a new order.
 * It handles the logic of creating an order based on the user's cart and user information.
 * It also handles the linking of courses to the order and the user to the order.
 * It logs any errors that occur during the process.
 *
 * @author Fedor
 * @version 1.0
 * @since 2023-11-27
 */
public class CreateOrderCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(CreateOrderCommand.class.getName());

    /**
     * Completes the command of creating a new order.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param context  The ServletContext object.
     * @return The name of the page to redirect to.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    public PageAddress execute(HttpServletRequest request, HttpServletResponse response, ServletContext context)
            throws ServletException, IOException {
        PageAddress resultRedirectPage = PageAddresses.MAIN_PAGE;
        try {
            HttpSession session = request.getSession();
            ClientService clientService = new ClientService();
            AdminService adminService = new AdminService();
            OrderService orderService = new OrderService();
            Cart userCart = (Cart) session.getAttribute("cart");
            User user;
            if (session.getAttribute("role").equals("admin")) {
                String login = String.valueOf(session.getAttribute("login"));
                String password = String.valueOf(session.getAttribute("password_hash"));
                user = adminService.loginAdmin(login, password, true);
            } else {
                String login = String.valueOf(session.getAttribute("login"));
                String password = String.valueOf(session.getAttribute("password_hash"));
                user = clientService.loginClient(login, password, true);
            }
            LinkTablesService linkTablesService = new LinkTablesService();
            Order clientOrder = new Order();
            clientOrder.setAccepted(false);
            clientOrder.setCreationDate(new Date(System.currentTimeMillis()));
            double summaryPrice = 0;
            for (Movie movie : userCart.getAll()) {
                summaryPrice += movie.getPrice();
            }
            clientOrder.setSummaryPrice(summaryPrice);
            orderService.createOrder(clientOrder);
            for (Movie movie : userCart.getAll()) {
                linkTablesService.createLinkOrderMovie(movie, clientOrder);
            }
            linkTablesService.createLinkUserOrder(user, clientOrder);
            session.setAttribute("cart", null);
            return resultRedirectPage;
        } catch (Exception e) {
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            logger.log(Level.ERROR, "Error in creating user order", e);
            return resultRedirectPage;
        }
    }
}
