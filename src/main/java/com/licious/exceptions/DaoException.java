package com.licious.exceptions;

public class DaoException extends Exception {
    public DaoException(Exception e, String message) {
        super(message, e);
    }
}
