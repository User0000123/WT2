package by.bsuir.wt2.webapp.classes.validation.validators;

import by.bsuir.wt2.webapp.classes.validation.IValidator;

/**
 * The NumberValidator class implements the IValidator interface and provides a method for number validation.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class NumberValidator implements IValidator {
    @Override
    public boolean validate(String value) {
        boolean valid;
        valid = value.matches("^\\d+(\\.\\d+)?$");
        return valid;
    }
}
