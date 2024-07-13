package io.svinoczar.api.exception;

public class ExponentialIncreaseError extends ExperienceException{
    public ExponentialIncreaseError(String message, String errorCode) {
        super(message, errorCode);
    }
}
