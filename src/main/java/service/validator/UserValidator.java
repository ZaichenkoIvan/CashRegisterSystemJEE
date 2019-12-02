package main.java.service.validator;



import main.java.domain.User;
import main.java.exception.InvalidDataRuntimeException;

import java.util.regex.Pattern;

public class UserValidator extends Validator<User> {

    private static final String PASSWORD_REGEX = "([A-Za-z0-9]{6,})";
    private static final String EMAIL_REGEX = "(\\w{2,})@(\\w+\\.)([a-z]{2,5})";
    private static final String NAME_REGEX = "([A-Za-z]{1,12})|([А-Яa-я]{1,12})";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public void validate(User user) {
        if (user == null) {
            LOGGER.warn("User is not valid");
            throw new InvalidDataRuntimeException("User is not valid");
        }

        validateByStringParam(user.getName(), NAME_PATTERN, "Incorrect name");
        validateByStringParam(user.getLogin(), EMAIL_PATTERN, "Incorrect email");
        validateByStringParam(user.getPassword(), PASSWORD_PATTERN, "Incorrect password");
    }
}
