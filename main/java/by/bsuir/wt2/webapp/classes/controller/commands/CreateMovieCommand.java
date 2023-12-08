package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.Movie;
import by.bsuir.wt2.webapp.classes.service.MovieService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import by.bsuir.wt2.webapp.classes.validation.ValidatorHandler;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * The CreateCourseCommand class implements the ICommand interface and is
 * responsible for creating a new movie entity.
 *
 * @author Fedor
 * @version 1.0
 * @since 2023-11-27
 */
public class CreateMovieCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(CreateMovieCommand.class.getName());

    /**
     * Creates a new movie.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param context  The ServletContext object.
     * @return The name of the page to redirect to.
     * @throws ServletException If an error occurs during execution.
     * @throws IOException If an error occurs during I/O.
     */
    @Override
    public PageAddress execute(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException {
        PageAddress resultRedirectPage = PageAddresses.MAIN_PAGE;
        try {
            boolean valid;
            HttpSession session = request.getSession();
            Movie movie = new Movie();
            Map<String, String[]> requestParams = request.getParameterMap();
            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<String, String[]> param : requestParams.entrySet()) {
                params.put(param.getKey(), new String(param.getValue()[0]
                        .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            }
            MovieService movieService = new MovieService();
            params.put("m_id", 0);
            valid = applyValidation(params);
            if (valid) {
                movieService.fillMovieWithParams(movie, params);
                movieService.createMovie(movie);
                session.setAttribute("chosen", null);
                session.setAttribute("cart", null);
            } else {
                session.setAttribute("input_error", true);
                resultRedirectPage = PageAddresses.MOVIE_ENTITY_CREATION;
            }
            return resultRedirectPage;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error while creating course", e);
            resultRedirectPage = PageAddresses.ERROR_PAGE;
            return resultRedirectPage;
        }
    }

    /**
     * Applies validation to the movie parameters.
     *
     * @param params The movie parameters.
     * @return True if the parameters are valid, false otherwise.
     */
    private boolean applyValidation(Map<String, Object> params) {
        boolean valid;
        ValidatorHandler validator = ValidatorHandler.getInstance();
        valid = validator.getValidatorByName("name_validator").validate(String.valueOf(params.get("m_name")))
                && validator.getValidatorByName("name_validator").validate(String.valueOf(params.get("m_director")))
                && validator.getValidatorByName("text_validator").validate(String.valueOf(params.get("m_genre")))
                && validator.getValidatorByName("text_validator").validate(String.valueOf(params.get("m_description")))
                && validator.getValidatorByName("price_validator").validate(String.valueOf(params.get("m_price")));
        return valid;
    }
}
