package by.kharchenko.processing.exception;

public class MoreAmountException extends Exception {
    public MoreAmountException() {
        super();
    }

    public MoreAmountException(String message) {
        super(message);
    }

    public MoreAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoreAmountException(Throwable cause) {
        super(cause);
    }

    protected MoreAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
