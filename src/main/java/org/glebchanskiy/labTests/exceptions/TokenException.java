package org.glebchanskiy.labTests.exceptions;

public class TokenException
        extends RuntimeException {
    public TokenException(String errorMessage) {
        super(errorMessage);
    }
}
