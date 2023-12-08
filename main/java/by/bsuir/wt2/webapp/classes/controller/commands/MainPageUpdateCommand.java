package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.Movie;
import by.bsuir.wt2.webapp.classes.service.MovieService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * This class represents a command for updating the main page.
 * It performs various operations such as setting session attributes,
 * retrieving courses and their count from the database, and updating the context.
 *
 * @author Fedor
 * @since 2023-11-27
 * @version 1.0
 */
public class MainPageUpdateCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(MainPageUpdateCommand.class.getName());

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
        PageAddress resultRedirectPage = PageAddresses.MAIN_PAGE;
        HttpSession session = request.getSession();
        if (session.isNew()) {
            session.setMaxInactiveInterval(300 * 60);
            session.setAttribute("log", false);
            request.setAttribute("locale", "en");
        }
        context.setAttribute("offset", 0);
        session.setAttribute("courses", null);
        session.setAttribute("orders", null);
        session.setAttribute("input_error", null);
        session.setAttribute("email_error", null);
        session.setAttribute("phone_error", null);
        session.setAttribute("login_error", null);
        session.setAttribute("auth_error", null);
        try {
            MovieService movieService = new MovieService();
            List<Movie> cours = movieService.getPageMoviesList(0);
            int coursesCount = movieService.getTotalMovieCount();
            context.setAttribute("courses_count", coursesCount);
            context.setAttribute("courses", cours);
            return resultRedirectPage;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while loading courses for pagination", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }
}
