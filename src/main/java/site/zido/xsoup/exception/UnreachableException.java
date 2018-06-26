package site.zido.xsoup.exception;

public class UnreachableException extends RuntimeException {
    public UnreachableException() {
        super("unreachable");
    }
}
