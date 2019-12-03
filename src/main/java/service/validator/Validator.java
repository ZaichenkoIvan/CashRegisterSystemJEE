package service.validator;

import exception.InvalidDataRuntimeException;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validator<E> {
    protected Logger LOGGER = Logger.getLogger(Validator.class);

    public abstract void validate(E entity);

    void validateByStringParam(String param, Pattern pattern, String errorMessage) {
        Matcher matcher = pattern.matcher(param);

        if (!matcher.matches()) {
            LOGGER.warn(errorMessage);
            throw new InvalidDataRuntimeException(errorMessage);
        }
    }
}