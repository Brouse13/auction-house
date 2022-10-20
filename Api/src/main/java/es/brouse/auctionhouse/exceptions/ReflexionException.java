package es.brouse.auctionhouse.exceptions;

public class ReflexionException extends RuntimeException {
    public ReflexionException() {
        super();
    }

    public ReflexionException(String message) {
        super(message);
    }

    public ReflexionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflexionException(Throwable cause) {
        super(cause);
    }

    protected ReflexionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
