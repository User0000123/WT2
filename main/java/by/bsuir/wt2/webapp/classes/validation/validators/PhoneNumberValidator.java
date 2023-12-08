package by.bsuir.wt2.webapp.classes.validation.validators;

import by.bsuir.wt2.webapp.classes.validation.IValidator;

/**
 * The PhoneNumberValidator class implements the IValidator interface and provides a method for phone number validation.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class PhoneNumberValidator implements IValidator {
    @Override
    public boolean validate(String value) {
        boolean valid;
        valid = value.matches("^\\+[0-9]{10,}$");
        return valid;
    }
}
