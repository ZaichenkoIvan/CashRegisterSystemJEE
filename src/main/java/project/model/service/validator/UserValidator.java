package project.model.service.validator;

import org.apache.log4j.Logger;
import project.model.domain.User;
import project.model.exception.InvalidRegistrationRuntimeException;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    private static final Logger LOGGER = Logger.getLogger(UserValidator.class);

    private static final String PASSWORD_REGEX =  "[a-zA-Z0-9]{4,}";
    private static final String EMAIL_REGEX =  "[a-zA-Z0-9]+[@][a-z]{5,}[.][a-z]{3}";
    private static final String NAME_REGEX = "[a-zA-Z]{2,}";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public void validate(User user) {
        if (user == null) {
            LOGGER.warn("User is not valid");
            throw new InvalidRegistrationRuntimeException("User is not valid");
        }

        validateName(user.getName());
        validateSurname(user.getSurname());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }

    private void validatePassword(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);

        if (!matcher.find()) {
            LOGGER.warn("Incorrect password");
            throw new InvalidRegistrationRuntimeException("Incorrect password");
        }
    }

    private void validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (!matcher.find()) {
            LOGGER.warn("Incorrect e-mail");
            throw new InvalidRegistrationRuntimeException("Incorrect e-mail");
        }
    }

    private void validateName(String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);

        if (!matcher.find()) {
            LOGGER.warn("Incorrect name");
            throw new InvalidRegistrationRuntimeException("Incorrect name");
        }
    }

    private void validateSurname(String surname) {
        Matcher matcher = NAME_PATTERN.matcher(surname);

        if (!matcher.find()) {
            LOGGER.warn("Incorrect surname");
            throw new InvalidRegistrationRuntimeException("Incorrect surname");
        }
    }
}
