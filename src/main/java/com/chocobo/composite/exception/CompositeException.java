package com.chocobo.composite.exception;

public class CompositeException extends Exception {

    public CompositeException() {
        super();
    }

    public CompositeException(String message) {
        super(message);
    }

    public CompositeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompositeException(Throwable cause) {
        super(cause);
    }
}
