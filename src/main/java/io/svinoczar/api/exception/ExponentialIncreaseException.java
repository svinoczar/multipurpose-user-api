package io.svinoczar.api.exception;

public class ExponentialIncreaseException extends ExperienceException{
    public ExponentialIncreaseException(String message, String errorCode) {
        super(message, errorCode);
    }
}
