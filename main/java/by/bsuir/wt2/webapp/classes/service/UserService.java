package by.bsuir.wt2.webapp.classes.service;

import by.bsuir.wt2.webapp.classes.dao.objects.UserDao;
import by.bsuir.wt2.webapp.classes.entities.User;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.exceptions.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;


/**
 * The UserService class provides methods for managing user operations.
 * It includes methods for user registration, updating user information, and checking user login, email, and phone number.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class UserService {

    /**
     * Registers a user by adding them to the database.
     *
     * @param user the user to register
     * @return true if the user is successfully registered, false otherwise
     * @throws ServiceException if an error occurs while registering the user
     */
    public boolean registerUser(User user) throws ServiceException {
        try {
            String hashNumber = PasswordHashing.generatePasswordHash(user.getPassword());
            user.setPassword(hashNumber);
            UserDao userDao = new UserDao();
            List<String> attributes =  UserDao.userAttributes();
            Map<String, Object> params = UserDao.userParams(user);
            attributes.remove("u_id");
            params.remove("u_id");
            userDao.addUser(attributes, params);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Updates a user's information in the database.
     *
     * @param originalUser the original user information
     * @param updatedUser the updated user information
     * @return true if the user information is successfully updated, false otherwise
     * @throws ServiceException if an error occurs while updating the user information
     */
    public boolean updateUser(User originalUser, User updatedUser) throws ServiceException {
        try {
            UserDao userDao = new UserDao();
            List<String> attributes = UserDao.userAttributes();
            Map<String, Object> oldParams = UserDao.userParams(originalUser);
            Map<String, Object> newParams = UserDao.userParams(updatedUser);
            userDao.updateUser(attributes, oldParams, attributes, newParams);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Checks if a user login exists in the database.
     *
     * @param user the user to check
     * @return true if the user login exists, false otherwise
     * @throws ServiceException if an error occurs while checking the user login
     */
    public boolean checkUserLogin(User user) throws ServiceException{
        try {
            UserDao userDao = new UserDao();
            List<String> attributes = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            attributes.add("u_login");
            params.put("u_login", user.getLogin());
            userDao.getUser("*", attributes, params);
            Object existingUserId = userDao.getUserSelectionResult("u_id");
            return existingUserId != null;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Checks if a user email exists in the database.
     *
     * @param user the user to check
     * @return true if the user email exists, false otherwise
     * @throws ServiceException if an error occurs while checking the user email
     */
    public boolean checkUserEmail(User user) throws ServiceException{
        try {
            UserDao userDao = new UserDao();
            List<String> attributes = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            attributes.add("u_email");
            params.put("u_email", user.getEmail());
            userDao.getUser("*", attributes, params);
            Object existingUserId = userDao.getUserSelectionResult("u_id");
            return existingUserId != null;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Checks if a user phone number exists in the database.
     *
     * @param user the user to check
     * @return true if the user phone number exists, false otherwise
     * @throws ServiceException if an error occurs while checking the user phone number
     */
    public boolean checkUserPhoneNumber(User user) throws ServiceException{
        try {
            UserDao userDao = new UserDao();
            List<String> attributes = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            attributes.add("u_phone_num");
            params.put("u_phone_num", user.getPhoneNumber());
            userDao.getUser("*", attributes, params);
            Object existingUserId = userDao.getUserSelectionResult("u_id");
            return existingUserId != null;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    /**
     * Fills a user object with parameters retrieved from the database.
     *
     * @param user the user object to fill
     * @param params the parameters retrieved from the database
     */
    public static void fillUserWithParams(User user, Map<String,Object> params) {
        user.setId(Integer.parseInt(String.valueOf(params.get("u_id"))));
        user.setName(String.valueOf(params.get("u_name")));
        user.setSurname(String.valueOf(params.get("u_surname")));
        user.setPhoneNumber(String.valueOf(params.get("u_phone_num")));
        user.setEmail(String.valueOf(params.get("u_email")));
        user.setLogin(String.valueOf(params.get("u_login")));
        user.setPassword(String.valueOf(params.get("u_pass_hash")));
        String dateString = String.valueOf(params.get("u_reg_date"));
        Date date = Date.valueOf(dateString);
        user.setRegistrationDate(date);
    }
}
