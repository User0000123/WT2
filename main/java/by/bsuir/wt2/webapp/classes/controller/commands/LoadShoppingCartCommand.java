package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.Movie;
import by.bsuir.wt2.webapp.classes.service.Cart;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a command for loading the cart.
 *
 * @author Fedor
 * @since 2023-11-27
 * @version 1.0
 */
public class LoadShoppingCartCommand implements ICommand {

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
        Cart cart = new Cart();
        HttpSession session = request.getSession();
        Object objCart = session.getAttribute("cart");
        if (objCart instanceof Cart) {
            cart = (Cart) objCart;
        }
        List<Movie> chosenCours = new ArrayList<>(cart.getAll());
        if (chosenCours.isEmpty()) {
            session.setAttribute("chosen", null);
        } else {
            session.setAttribute("chosen", chosenCours);
        }
        return PageAddresses.ORDER_CREATION;
    }
}
