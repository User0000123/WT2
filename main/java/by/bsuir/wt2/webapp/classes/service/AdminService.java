package by.bsuir.wt2.webapp.classes.service;

import by.bsuir.wt2.webapp.classes.dao.objects.AdminDao;
import by.bsuir.wt2.webapp.classes.dao.objects.UserDao;
import by.bsuir.wt2.webapp.classes.entities.Admin;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.exceptions.ServiceException;

import java.util.*;

/**
 * The AdminService class provides operations related to administrators.
 * It includes methods for registering an admin, updating an admin, and logging in an admin.
 *
 * @version 1.0
 * @author Aleskej
 * @since 2023-12-07
 */
public class AdminService {
    /**
     * Registers a new admin.
     *
     * @param admin the admin to register
     * @return true if the admin is successfully registered, false otherwise
     * @throws ServiceException if an error occurs during registration
     */
    public boolean registerAdmin(Admin admin) throws ServiceException {
        try {
            UserService userService = new UserService();
            AdminDao adminDao = new AdminDao();
            UserDao userDao = new UserDao();
            boolean userCreated = userService.registerUser(admin);
            if(userCreated) {
                List<String> attributes = AdminDao.adminAttributes();
                Map<String, Object> params = AdminDao.adminParams(admin);
                List<String> userAttributes = UserDao.userAttributes();
                Map<String, Object> userParams = UserDao.userParams(admin);
                userAttributes.remove("u_id");
                userParams.remove("u_id");
                userDao.getUser("u_id",userAttributes,
                        userParams);
                String userId = userDao.getUserById();
                params.put("adm_id", userId);
                adminDao.addAdmin(attributes, params);
            } else {
                throw new ServiceException("Error occurred during registration");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Updates an existing admin.
     *
     * @param originalAdmin the original admin to be updated
     * @param updatedAdmin the updated admin
     * @return true if the admin is successfully updated, false otherwise
     * @throws ServiceException if an error occurs during admin update
     */
    public boolean updateAdmin(Admin originalAdmin,Admin updatedAdmin) throws ServiceException {
        try {
            UserService userService = new UserService();
            AdminDao adminDao = new AdminDao();
            boolean userUpdated = userService.updateUser(originalAdmin,updatedAdmin);
            if(userUpdated) {
                List<String> attributes = AdminDao.adminAttributes();
                Map<String, Object> oldParams = AdminDao.adminParams(originalAdmin);
                Map<String, Object> newParams = AdminDao.adminParams(updatedAdmin);
                adminDao.updateAdmin(attributes, oldParams,attributes,newParams);
            } else {
                throw new ServiceException("Error occurred during admin update");
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Logs in an admin with the specified login and password.
     *
     * @param login the login of the admin
     * @param password the password of the admin
     * @param passwordIsHashed true if the password is already hashed, false otherwise
     * @return the logged-in admin, or null if login fails
     * @throws ServiceException if an error occurs during login
     */
    public Admin loginAdmin(String login, String password,boolean passwordIsHashed) throws ServiceException {
        try {
            AdminDao adminDao = new AdminDao();
            UserDao userDao = new UserDao();
            List<String> attributes = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            attributes.add("u_login");
            params.put("u_login", login);
            attributes.add("u_pass_hash");
            String passwordHash = PasswordHashing.generatePasswordHash(password);
            if(passwordIsHashed) {
                params.put("u_pass_hash", password);
            } else {
                params.put("u_pass_hash", passwordHash);
            }
            userDao.getUser("*", attributes, params);
            Object userParams = userDao.getUserSelectionResult("u_id");
            attributes.clear();
            params.clear();
            attributes.add("adm_id");
            params.put("adm_id", userParams);
            adminDao.getAdmin("*", attributes, params);
            Object adminParams = adminDao.getAdminSelectionResult("adm_id");
            if(adminParams == null) {
                return null;
            } else {
                Admin admin = new Admin();
                Map<String,Object> allAdminParams=userDao
                        .getUserSelectionResult(UserDao.userAttributes());
                allAdminParams.putAll(adminDao
                        .getAdminSelectionResult(AdminDao.adminAttributes()));
                fillAdminWithParams(admin,allAdminParams);
                return admin;
            }

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Fills the admin object with the specified parameters.
     *
     * @param admin the admin object to fill
     * @param params the parameters to fill the admin object with
     */
    public void fillAdminWithParams(Admin admin,Map<String,Object> params) {
        UserService.fillUserWithParams(admin,params);
        admin.setId(Integer.parseInt(String.valueOf(params.get("adm_id"))));
        admin.setActive(String.valueOf(params.get("adm_is_active")).equals("true"));
    }
}
