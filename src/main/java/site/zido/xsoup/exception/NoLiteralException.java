package site.zido.xsoup.exception;

public class NoLiteralException extends ParseException {
    public NoLiteralException() {
        super("expected a literal string");
    }
}
