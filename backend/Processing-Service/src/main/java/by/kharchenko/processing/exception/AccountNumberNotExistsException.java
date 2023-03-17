package by.kharchenko.processing.exception;

public class AccountNumberNotExistsException extends Exception{
    public AccountNumberNotExistsException() {
        super();
    }

    public AccountNumberNotExistsException(String message) {
        super(message);
    }

    public AccountNumberNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNumberNotExistsException(Throwable cause) {
        super(cause);
    }

    protected AccountNumberNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
