package com.pet.project.weathertracker.exceptions;

import jakarta.servlet.ServletException;

public class NotFoundCookieException extends ServletException {
    public NotFoundCookieException(String msg) {
        super(msg);
    }
}
