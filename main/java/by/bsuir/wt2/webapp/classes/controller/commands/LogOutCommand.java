package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class represents a command for logging out the user.
 * It clears the session attributes related to login, password, role, and cart.
 *
 * @author Fedor
 * @since 2023-11-27
 * @version 1.0
 */
public class LogOutCommand implements ICommand {

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
        HttpSession session = request.getSession();
        session.setAttribute("log", false);
        session.setAttribute("login", null);
        session.setAttribute("password_hash", null);
        session.setAttribute("role", null);
        session.setAttribute("cart", null);
        return PageAddresses.MAIN_PAGE;
    }
}
