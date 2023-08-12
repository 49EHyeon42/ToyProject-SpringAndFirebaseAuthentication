package dev.ehyeon.SpringAndFirebaseAuthentication.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN"),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND.value(), "NOT_FOUND_MEMBER");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
