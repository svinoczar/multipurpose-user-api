package io.svinoczar.api.exception;

public class ZeroXPStartValueError extends ExponentialIncreaseError{
    public ZeroXPStartValueError(String message, String errorCode) {
        super(message, errorCode);
    }
}
