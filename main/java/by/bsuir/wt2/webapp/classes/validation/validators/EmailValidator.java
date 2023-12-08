package by.bsuir.wt2.webapp.classes.validation.validators;

import by.bsuir.wt2.webapp.classes.validation.IValidator;

/**
 * The EmailValidator class implements the IValidator interface and provides a method for email validation.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class EmailValidator implements IValidator {
    @Override
    public boolean validate(String value) {
        boolean valid;
        valid = value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+([.\\-]?[A-Za-z0-9]+)*$");
        return valid;
    }
}
