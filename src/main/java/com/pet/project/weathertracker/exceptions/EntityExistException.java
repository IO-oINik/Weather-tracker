package com.pet.project.weathertracker.exceptions;

import jakarta.persistence.PersistenceException;

public class EntityExistException extends PersistenceException {
    public EntityExistException(String msg) {
        super(msg);
    }
}
