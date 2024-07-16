package io.svinoczar.api.exception;

public class ZeroXPStartValueException extends ExponentialIncreaseException {
    public ZeroXPStartValueException(String message, String errorCode) {
        super(message, errorCode);
    }
}
