package by.bsuir.wt2.webapp.classes.validation.validators;

import by.bsuir.wt2.webapp.classes.validation.IValidator;

/**
 * The NameValidator class implements the IValidator interface and provides a method for name validation.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class NameValidator implements IValidator {
    @Override
    public boolean validate(String value) {
        boolean valid;
        valid = value.matches("[A-Za-z\\s_0-9]+");
        return valid;
    }
}
