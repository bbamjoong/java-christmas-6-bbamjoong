package christmas.exceptions;

import static christmas.exceptions.ErrorMessage.INVALID_ORDER_MESSAGE;

public class InvalidOrderException extends IllegalArgumentException {
    public InvalidOrderException() {
        super(INVALID_ORDER_MESSAGE.getMessage());
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}