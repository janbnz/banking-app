package de.janbnz.bankingapp.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class TransferToSelfException extends RuntimeException {

    public TransferToSelfException(String message) {
        super(message);
    }
}