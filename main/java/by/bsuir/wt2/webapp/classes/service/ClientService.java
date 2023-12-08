package by.bsuir.wt2.webapp.classes.service;

import by.bsuir.wt2.webapp.classes.dao.objects.AdminDao;
import by.bsuir.wt2.webapp.classes.dao.objects.ClientDao;
import by.bsuir.wt2.webapp.classes.dao.objects.UserDao;
import by.bsuir.wt2.webapp.classes.entities.Client;
import by.bsuir.wt2.webapp.classes.exceptions.DaoException;
import by.bsuir.wt2.webapp.classes.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ClientService class provides various operations related to client users.
 * It includes methods for registering a client, logging in a client, updating a client, and retrieving a client by ID.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class ClientService {
    /**
     * Registers a client by creating a user and adding client-specific attributes to the database.
     *
     * @param client the client to register
     * @return true if the client is successfully registered, false otherwise
     * @throws ServiceException if an error occurs during the registration process
     */
    public boolean registerClient(Client client) throws ServiceException{
        try {
            UserService userService = new UserService();
            ClientDao clientDao = new ClientDao();
            UserDao userDao = new UserDao();
            boolean userCreated = userService.registerUser(client);
            if (userCreated) {
                List<String> attributes = ClientDao.clientAttributes();
                Map<String, Object> params = ClientDao.clientParams(client);
                List<String> userAttributes = UserDao.userAttributes();
                Map<String, Object> userParams = UserDao.userParams(client);
                userAttributes.remove("u_id");
                userParams.remove("u_id");
                userDao.getUser("u_id", userAttributes,
                       userParams);
                String userId = userDao.getUserById();
                params.put("cl_id", userId);
                clientDao.addClient(attributes, params);
            } else {
                throw new ServiceException("Error occurred during registration");
            }
        } catch (DaoException e){
            throw new ServiceException(e.getMessage(),e);
        }
        return true;
    }

    /**
     * Logs in a client by verifying the login credentials and retrieving the client information from the database.
     *
     * @param login the login of the client
     * @param password the password of the client
     * @param passwordIsHashed true if the password is already hashed, false otherwise
     * @return the logged in client if the login is successful, null otherwise
     * @throws ServiceException if an error occurs during the login process
     */
    public Client loginClient(String login,String password,boolean passwordIsHashed) throws ServiceException {
        try {
            ClientDao clientDao = new ClientDao();
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
            attributes.add("cl_id");
            params.put("cl_id", userParams);
            clientDao.getClient("*", attributes, params);
            Object clientParams = clientDao.getClientSelectionResult("cl_id");
            if(clientParams == null) {
                return null;
            } else {
                Client client = new Client();
                Map<String,Object> allUserParams=userDao
                        .getUserSelectionResult(UserDao.userAttributes());
                allUserParams.putAll(clientDao
                        .getClientSelectionResult(ClientDao.clientAttributes()));
                fillClientWithParams(client,allUserParams);
                return client;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    /**
     * Updates a client by updating the user information and client-specific attributes in the database.
     *
     * @param originalClient the original client information
     * @param updatedClient the updated client information
     * @return true if the client is successfully updated, false otherwise
     * @throws ServiceException if an error occurs during the update process
     */
    public boolean updateClient(Client originalClient,Client updatedClient) throws ServiceException {
        try {
            UserService userService = new UserService();
            AdminDao adminDao = new AdminDao();
            boolean userUpdated = userService.updateUser(originalClient,updatedClient);
            if(userUpdated) {
                List<String> attributes = ClientDao.clientAttributes();
                Map<String, Object> oldParams = ClientDao.clientParams(originalClient);
                Map<String, Object> newParams = ClientDao.clientParams(updatedClient);
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
     * Retrieves a client by their ID from the database.
     *
     * @param id the ID of the client
     * @return the client with the specified ID if found, null otherwise
     * @throws ServiceException if an error occurs during the retrieval process
     */
    public Client getClientById(int id) throws ServiceException{
        Client client = new Client();
        try {
            ClientDao clientDao = new ClientDao();
            UserDao userDao = new UserDao();
            List<String> attributes = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            attributes.add("u_id");
            params.put("u_id", id);
            userDao.getUser("*", attributes, params);
            attributes.clear();
            params.clear();
            attributes.add("cl_id");
            params.put("cl_id", id);
            clientDao.getClient("*", attributes, params);
            attributes.add("cl_is_banned");
            params.put("cl_is_banned", 0);
            Map<String,Object> clientAttributes = clientDao.getClientSelectionResult(attributes);
            clientAttributes.putAll(userDao.getUserSelectionResult(UserDao.userAttributes()));
            if(clientAttributes.isEmpty()){
                throw new ServiceException("Role error: provided user isn't client");
            } else {
                return client;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }


    /**
     * Fills a client object with the provided parameters.
     *
     * @param client the client object to fill
     * @param params the parameters to fill the client object with
     */
    public void fillClientWithParams(Client client,Map<String,Object> params) {
        UserService.fillUserWithParams(client,params);
        client.setId(Integer.parseInt(String.valueOf(params.get("cl_id"))));
        client.setBanned(String.valueOf(params.get("cl_is_banned")).equals("true"));
    }
}
