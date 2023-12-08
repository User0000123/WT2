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
import java.util.Map;

/**
 * This class represents a command for handling user login.
 * It validates the login credentials provided by the user,
 * checks if the user is an admin or a client, and sets the appropriate session attributes.
 *
 * @version 1.0
 */
public class LoginCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(LoginCommand.class.getName());

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
        PageAddress resultRedirectPage = PageAddresses.MAIN_PAGE;
        Admin authorizedAdmin;
        Client authorizedClient;
        Map<String, String[]> params = request.getParameterMap();
        AdminService adminService = new AdminService();
        ClientService clientService = new ClientService();
        try {
            authorizedAdmin = adminService.loginAdmin(params.get("login")[0],
                    params.get("password")[0], false);
            if (authorizedAdmin != null) {
                request.getSession().setAttribute("log", true);
                request.getSession().setAttribute("login", authorizedAdmin.getLogin());
                request.getSession().setAttribute("password_hash", authorizedAdmin.getPassword());
                request.getSession().setAttribute("role", "admin");
                return resultRedirectPage;
            } else {
                authorizedClient = clientService.loginClient(params.get("login")[0],
                        params.get("password")[0], false);
                if (authorizedClient != null) {
                    request.getSession().setAttribute("log", true);
                    request.getSession().setAttribute("login", authorizedClient.getLogin());
                    request.getSession().setAttribute("password_hash",
                            authorizedClient.getPassword());
                    request.getSession().setAttribute("role", "client");
                    return resultRedirectPage;
                } else {
                    session.setAttribute("auth_error", true);
                    resultRedirectPage = PageAddresses.LOGIN_PAGE;
                    return resultRedirectPage;
                }
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while login admin", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }
}
