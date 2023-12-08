package by.bsuir.wt2.webapp.classes.controller;

import by.bsuir.wt2.webapp.classes.controller.logic.CommandHandler;
import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.dao.connection.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 * The Controller servlet is responsible for handling all requests to the
 * application. It initializes the database connection pool, processes
 * requests from the client, and forwards them to the appropriate
 * command handler.
 *
 * @author Aleksej
 * @version 1.0
 * @since 2023-12-07
 */
public class Controller extends HttpServlet {

    /**
     * Method of controller initialization
     * Initializes the database connection pool.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.getInstance().initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes get requests from the client.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Processes post requests from the client.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Processes the request and forwards it to the appropriate command handler.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException if an error occurs during execution
     * @throws IOException if an error IO-error occurs
     */
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ICommand command;
        PageAddress resultRedirectPage = PageAddresses.MAIN_PAGE;
        CommandHandler handler = CommandHandler.getInstance();

        if (!(request.getParameter("command") == null)) {
            if (request.getParameter("command").equals("redirect")) {
                String redirectPage = request.getParameter("page");
                String isRedirect = request.getParameter("redirect");
                request.getSession().setAttribute("input_error", null);
                if (isRedirect.equals("true")) {
                    response.sendRedirect(redirectPage);
                } else {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(redirectPage);
                    dispatcher.forward(request, response);
                }
                return;
            }
            command = handler.getCommandByName(request.getParameter("command"));
        } else {
            command = handler.getCommandByName("main");
        }

        resultRedirectPage = command.execute(request, response, getServletContext());

        if (resultRedirectPage.isRedirect()) {
            response.sendRedirect(resultRedirectPage.getName());
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(resultRedirectPage.getName());
            dispatcher.forward(request, response);
        }
    }

    /**
     * Destroys the database connection pool.
     * Method of controller destruction
     */
    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.getInstance().destroy();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
