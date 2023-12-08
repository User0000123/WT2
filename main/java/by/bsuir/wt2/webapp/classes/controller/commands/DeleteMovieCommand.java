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
import java.io.IOException;
import java.util.List;


/**
 * The DeleteCourseCommand class implements the ICommand interface and is responsible for deleting a course.
 * It handles the logic of deleting a course from the system.
 * It logs any errors that occur during the process.
 *
 * @author Fedor
 * @version 1.0
 * @since 2023-11-27
 */
public class DeleteMovieCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(DeleteMovieCommand.class.getName());

    /**
     * Completes the command of deleting a course.
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
            MovieService movieService = new MovieService();
            int coursesOffset = Integer.parseInt(String.valueOf(context.getAttribute("offset")));
            List<Movie> currentOrderPage = movieService.getPageMoviesList(coursesOffset);
            movieService.deleteMovie(currentOrderPage.get(Integer.parseInt(request.getParameter("courseId"))));
            List<Movie> cours = movieService.getPageMoviesList(0);
            int coursesCount = movieService.getTotalMovieCount();
            context.setAttribute("courses_count", coursesCount);
            context.setAttribute("courses", cours);
            context.setAttribute("offset", 0);
            return resultRedirectPage;
        } catch (Exception e) {
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            logger.log(Level.ERROR, "Error while deleting order", e);
            return resultRedirectPage;
        }
    }
}
