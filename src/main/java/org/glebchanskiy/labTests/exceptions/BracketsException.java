package org.glebchanskiy.labTests.exceptions;

public class BracketsException
        extends RuntimeException {
    public BracketsException(String errorMessage) {
        super(errorMessage);
    }
}