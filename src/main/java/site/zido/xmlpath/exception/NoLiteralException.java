package site.zido.xmlpath.exception;

import site.zido.xmlpath.CompilerException;

public class NoLiteralException extends CompilerException {
    public NoLiteralException() {
        super("expected a literal string");
    }
}
