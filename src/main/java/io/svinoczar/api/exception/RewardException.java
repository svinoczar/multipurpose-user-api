package io.svinoczar.api.exception;

public class RewardException extends ApiException {
    public RewardException(String message, String errorCode) {
        super(message, errorCode);
    }
}
