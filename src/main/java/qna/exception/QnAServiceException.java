package qna.exception;

public class QnAServiceException extends RuntimeException {
    public QnAServiceException() {
    }

    public QnAServiceException(String message,
                               Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public QnAServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public QnAServiceException(String message) {
        super(message);
    }

    public QnAServiceException(Throwable cause) {
        super(cause);
    }

}
