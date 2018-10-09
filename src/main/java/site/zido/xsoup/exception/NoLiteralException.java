package site.zido.xsoup.exception;

import site.zido.xsoup.CompilerException;

public class NoLiteralException extends CompilerException {
    public NoLiteralException() {
        super("expected a literal string");
    }
}
