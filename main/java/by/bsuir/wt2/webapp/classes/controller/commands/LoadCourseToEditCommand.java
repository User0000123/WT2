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
 * This class represents a command for loading a course to edit.
 *
 * @author Fedor
 * @version 1.0
 * @since 2023-11-27
 */
public class LoadCourseToEditCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(LoadOrdersCommand.class.getName());

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
        PageAddress resultRedirectPage = PageAddresses.MOVIE_EDIT;
        HttpSession session = request.getSession();
        List<Movie> cours;
        int currentOffset = Integer.parseInt(String.valueOf(context.getAttribute("offset")));
        try {
            MovieService movieService = new MovieService();
            cours = movieService.getPageMoviesList(currentOffset);
            session.setAttribute("course", cours.get(Integer
                    .parseInt(request.getParameter("courseId"))));
            request.getSession().setAttribute("input_error", null);
            return resultRedirectPage;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while loading course to edit", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }
}
