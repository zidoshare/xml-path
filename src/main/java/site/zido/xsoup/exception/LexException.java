package site.zido.xsoup.exception;

public class LexException extends RuntimeException {
    public LexException() {
    }

    public LexException(String message) {
        super(message);
    }

    public LexException(Throwable cause) {
        super(cause);
    }
}
