package by.bsuir.wt2.webapp.classes.service;

import by.bsuir.wt2.webapp.classes.exceptions.ServiceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The PasswordHashing class provides methods for generating password hashes using the SHA-256 algorithm.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class PasswordHashing {
    public static String generatePasswordHash(String password) throws ServiceException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder hashFormer = new StringBuilder();
            byte[] hash = digest.digest(String.valueOf(password).getBytes());
            for (byte b : hash) {
               hashFormer.append(String.format("%02x", b));
            }
            String result = hashFormer.toString();
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error in password hashing");
        }
    }
}
