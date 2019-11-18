package com.epam.project.commands;

import com.epam.project.exceptions.InvalidValueRuntimeException;

import java.util.regex.Pattern;

public class DataValidator {

    //TODO: rename, util, change
    public static Double filterDouble(String value) throws InvalidValueRuntimeException {
        if (isValidDouble(value)) {
            return Double.parseDouble(value);
        }
        String filtered = value.replaceAll("[a-zA-Zа-яА-Я~`!@#$%^&*()_+:\"'?<>{}]", "");
        filtered = filtered.replace(",", ".");
        if (isValidDouble(filtered)) {
            return Double.parseDouble(filtered);
        }
        throw new InvalidValueRuntimeException();
    }

    public static boolean isValidDouble(String value) {
        return Pattern.compile("([0-9]*[.]?[0-9]*([eE][+-])?[0-9]*)").matcher(value).matches();
    }
}
