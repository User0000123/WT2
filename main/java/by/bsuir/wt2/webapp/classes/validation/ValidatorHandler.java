package by.bsuir.wt2.webapp.classes.validation;

import by.bsuir.wt2.webapp.classes.validation.validators.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The ValidatorHandler class is responsible for managing validators.
 * It provides methods for retrieving validators by name.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class ValidatorHandler {
        private static final ValidatorHandler instance = new ValidatorHandler();

        private static Map<String, IValidator> validatorMap;

    /**
     * Creates a new instance of the ValidatorHandler class and initializes the validator map.
     */
    public ValidatorHandler(){
            validatorMap = new HashMap<>();
            validatorMap.put("name_validator",new NameValidator());
            validatorMap.put("text_validator",new TextValidator());
            validatorMap.put("email_validator",new EmailValidator());
            validatorMap.put("phone_validator",new PhoneNumberValidator());
            validatorMap.put("price_validator",new NumberValidator());
        }

    /**
     * Retrieves the singleton instance of the ValidatorHandler class.
     *
     * @return the singleton instance
     */
    public static ValidatorHandler getInstance(){
            return instance;
        }

    /**
     * Retrieves a validator by name.
     *
     * @param name the name of the validator
     * @return the validator with the specified name, or null if not found
     */
    public IValidator getValidatorByName(String name){
            return validatorMap.get(name);
        }

}
