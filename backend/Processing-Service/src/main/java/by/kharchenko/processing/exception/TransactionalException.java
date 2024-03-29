package by.kharchenko.processing.exception;

public class TransactionalException extends Exception{
    public TransactionalException() {
        super();
    }

    public TransactionalException(String message) {
        super(message);
    }

    public TransactionalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalException(Throwable cause) {
        super(cause);
    }

    protected TransactionalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
