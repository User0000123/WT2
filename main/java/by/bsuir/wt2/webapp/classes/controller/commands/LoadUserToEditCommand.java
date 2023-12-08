package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.Admin;
import by.bsuir.wt2.webapp.classes.entities.Client;
import by.bsuir.wt2.webapp.classes.service.AdminService;
import by.bsuir.wt2.webapp.classes.service.ClientService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class represents a command for loading a user to edit.
 * It retrieves the user information based on the session role (admin or client)
 * and sets the user attribute in the session for editing.
 *
 * @author Fedor
 * @since 2023-11-27
 * @version 1.0
 */
public class LoadUserToEditCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(LoadOrdersCommand.class.getName());

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
        PageAddress resultRedirectPage = PageAddresses.USER_EDIT;
        try {
            AdminService adminService = new AdminService();
            ClientService clientService = new ClientService();
            Admin admin;
            Client client;
            HttpSession session = request.getSession();
            if (session.getAttribute("role").equals("admin")) {
                admin = adminService.loginAdmin(String.valueOf(session.getAttribute("login")),
                        String.valueOf(session.getAttribute("password_hash")), true);
                session.setAttribute("user", admin);
            } else {
                client = clientService.loginClient(String.valueOf(session.getAttribute("login")),
                        String.valueOf(session.getAttribute("password_hash")), true);
                session.setAttribute("user", client);
            }
            request.getSession().setAttribute("input_error", null);
            return resultRedirectPage;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while getting editable user info", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }
}
