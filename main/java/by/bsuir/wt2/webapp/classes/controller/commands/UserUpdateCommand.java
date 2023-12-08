package by.bsuir.wt2.webapp.classes.controller.commands;

import by.bsuir.wt2.webapp.classes.controller.logic.ICommand;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddress;
import by.bsuir.wt2.webapp.classes.controller.logic.PageAddresses;
import by.bsuir.wt2.webapp.classes.entities.User;
import by.bsuir.wt2.webapp.classes.exceptions.ServiceException;
import by.bsuir.wt2.webapp.classes.service.UserService;
import by.bsuir.wt2.webapp.classes.validation.ValidatorHandler;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a command for updating user profile.
 * It updates the user data and checks if user data is valid for change.
 *
 * @author Fedor
 * @since 2023-11-27
 * @version 1.0
 */
public class UserUpdateCommand implements ICommand {

    private static final Logger logger = Logger.getLogger(UserUpdateCommand.class.getName());

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
        boolean valid;
        try{
            HttpSession session = request.getSession();
            UserService userService = new UserService();
            User updatedUser = new User();
            Map<String, String[]> requestParams = request.getParameterMap();
            Map<String, Object> params = new HashMap<>();
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                params.put(entry.getKey(), new String(entry.getValue()[0].getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8));
            }
            User oldUser = (User) session.getAttribute("user");
            params.put("u_id",oldUser.getId());
            params.put("u_reg_date",oldUser.getRegistrationDate());
            params.put("u_pass_hash",oldUser.getPassword());
            valid = applyValidation(params);
            if(valid) {
                UserService.fillUserWithParams(updatedUser, params);
                userService.updateUser(oldUser, updatedUser);
                session.setAttribute("login", updatedUser.getLogin());
                session.setAttribute("user", null);
            }else {
                resultRedirectPage= PageAddresses.USER_EDIT;
                session.setAttribute("input_error",true);
            }
            return resultRedirectPage;
        } catch (ServiceException e) {
            logger.log(Level.ERROR,"Error while updating user info",e);
            resultRedirectPage= PageAddresses.ERROR_PAGE;
            return resultRedirectPage;

        }
    }

    private boolean applyValidation(Map<String,Object> params){
        boolean valid;
        ValidatorHandler validator = ValidatorHandler.getInstance();
        valid = validator.getValidatorByName("name_validator").validate(String.valueOf(params.get("u_name")))
                && validator.getValidatorByName("name_validator").validate(String.valueOf(params.get("u_surname")))
                && validator.getValidatorByName("phone_validator").validate(String.valueOf(params.get("u_phone_num")))
                && validator.getValidatorByName("email_validator").validate(String.valueOf(params.get("u_email")))
                && validator.getValidatorByName("name_validator").validate(String.valueOf(params.get("u_login")));
        return valid;
    }
}
