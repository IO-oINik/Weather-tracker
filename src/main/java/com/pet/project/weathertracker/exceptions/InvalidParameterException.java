package com.pet.project.weathertracker.exceptions;

import jakarta.servlet.ServletException;

public class InvalidParameterException extends ServletException {
    public InvalidParameterException(String msg) {
        super(msg);
    }
}
