package christmas.exceptions;

import static christmas.exceptions.ErrorMessage.INVALID_DATE_MESSAGE;

public class InvalidDateException extends IllegalArgumentException {
    public InvalidDateException() {
        super(INVALID_DATE_MESSAGE.getMessage());
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}