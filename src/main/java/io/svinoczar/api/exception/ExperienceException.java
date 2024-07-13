package io.svinoczar.api.exception;

public class ExperienceException extends ApiException{
    public ExperienceException(String message, String errorCode) {
        super(message, errorCode);
    }
}
