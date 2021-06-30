package qna.exception;

public class ForbiddenException extends QnAServiceException {
    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
