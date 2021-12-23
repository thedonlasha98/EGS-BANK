package com.egs.bank.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

public enum ErrorKey {

    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND),
    NOT_ENOUGH_BALANCE(HttpStatus.FORBIDDEN);

    private HttpStatus status;

    ErrorKey(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
