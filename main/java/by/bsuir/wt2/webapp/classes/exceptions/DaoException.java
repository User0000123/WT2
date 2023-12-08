package by.bsuir.wt2.webapp.classes.exceptions;

import java.sql.SQLException;

/**
 * The DaoException class represents an exception that occurs in the DAO (Data Access Object) layer.
 * It extends the SQLException class.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class DaoException extends SQLException {

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message,Throwable packedException) {
        super(message,packedException);
    }

}
