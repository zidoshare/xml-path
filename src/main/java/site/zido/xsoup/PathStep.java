package site.zido.xsoup;

import site.zido.xsoup.exception.LexException;
import site.zido.xsoup.exception.NoLiteralException;
import site.zido.xsoup.exception.UnreachableException;
import site.zido.xsoup.pred.*;
import site.zido.xsoup.utils.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Path step.
 *
 * @author zido
 */
public class PathStep {
    private boolean root;
    private Axis axis;
    private String name;
    private NodeKind kind;
    private Predicate pred;

    public PathStep() {

    }

    public PathStep(Axis axis) {
        this.axis = axis;
    }

    public boolean match(Node node) {
        return node.getKind() != NodeKind.END &&
                (kind == NodeKind.ANY || kind == node.getKind()) &&
                ("*".equals(name) || node.getName().equals(name));
    }

    public Path mustCompile(String path) {
        try {
            return compile(path);
        } catch (CompilerException e) {
            throw new RuntimeException(e);
        }
    }

    public Path compile(String path) throws CompilerException {
        PathCompiler pathCompiler = new PathCompiler(path, 0);
        if ("".equals(path)) {
            throw new IllegalArgumentException("empty path");
        }
        return pathCompiler.parsePath();
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public Axis axis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeKind getKind() {
        return kind;
    }

    public void setKind(NodeKind kind) {
        this.kind = kind;
    }

    public Predicate pred() {
        return pred;
    }

    public void setPred(Predicate pred) {
        this.pred = pred;
    }

    private class PathCompiler {
        private String path;
        private int i;

        private class State {
            private List<Predicate> sub;
            private boolean and;

            private State(List<Predicate> sub, boolean and) {
                this.sub = sub;
                this.and = and;
            }
        }

        private PathCompiler(String path, int i) {
            this.path = path;
            this.i = i;
        }

        public CompilerException errorf(String format, Object... args) {
            return new CompilerException(String.format("compiling xml path %s:%d: %s", path, i, String.format(format, args)));
        }

        private Path parsePath() throws CompilerException {
            List<PathStep> steps = new LinkedList<>();
            int start = i;

            PathStep step = new PathStep(Axis.CHILD);
            skipSpaces();
            if (i == 0 && skipByte('/')) {
                skipSpaces();
                step.root = true;
                if (path.length() == 1) {
                    step.name = "*";
                }
            }
            if (peekByte('/')) {
                step.axis = Axis.DESCENDANT_OR_SELF;
                step.name = "*";
            } else if (skipByte('@')) {
                int mark = i;
                if (skipName()) {
                    throw errorf("missing name after @");
                }
                step.axis = Axis.ATTRIBUTE;
                step.name = path.substring(mark, i);
                step.kind = NodeKind.ATTR;
            } else {
                int mark = i;
                if (skipName()) {
                    step.name = path.substring(mark, i);
                    skipSpaces();
                }
                if ("".equals(step.name)) {
                    throw errorf("missing name");
                } else if ("*".equals(step.name)) {
                    step.kind = NodeKind.START;
                } else if (".".equals(step.name)) {
                    step.axis = Axis.PARENT;
                    step.name = "*";
                } else {
                    if (skipByte(':')) {
                        if (!skipByte(':')) {
                            throw errorf("missing ':'");
                        }
                        skipSpaces();
                        switch (step.name) {
                            case "attribute":
                                step.kind = NodeKind.ATTR;
                                step.axis = Axis.ATTRIBUTE;
                                break;
                            case "self":
                                step.axis = Axis.SELF;
                                break;
                            case "child":
                                step.axis = Axis.CHILD;
                                break;
                            case "parent":
                                step.axis = Axis.PARENT;
                                break;
                            case "descendant":
                                step.axis = Axis.DESCENDANT;
                                break;
                            case "descendant-or-self":
                                step.axis = Axis.DESCENDANT_OR_SELF;
                                break;
                            case "ancestor":
                                step.axis = Axis.ANCESTOR;
                                break;
                            case "ancestor-or-self":
                                step.axis = Axis.ANCESTOR_OR_SELF;
                                break;
                            case "following":
                                step.axis = Axis.FOLLOWING;
                                break;
                            case "following-sibling":
                                step.axis = Axis.FOLLOWING_SIBLING;
                                break;
                            case "preceding":
                                step.axis = Axis.PRECEDING;
                                break;
                            case "preceding-sibling":
                                step.axis = Axis.PRECEDING_SIBLING;
                                break;
                            default:
                                throw errorf("unsupported axis: %s", step.name);
                        }
                        mark = i;
                        if (!skipName()) {
                            throw errorf("missing name");
                        }
                        step.name = path.substring(mark, i);
                        skipSpaces();
                    }
                    if (skipByte('(')) {
                        skipSpaces();
                        boolean conflict = step.kind != NodeKind.ANY;
                        ;
                        switch (step.name) {
                            case "node":
                            case "text":
                                step.kind = NodeKind.TEXT;
                                break;
                            case "comment":
                                step.kind = NodeKind.COMMENT;
                                break;
                            case "processing-instruction":
                                step.kind = NodeKind.PROCINST;
                                break;
                            default:
                                throw errorf("unsupported expression: %s()", step.name);
                        }
                        if (conflict) {
                            throw errorf("%s() cannot succeed on axis %s", step.name, step.axis);
                        }

                        String name = step.name;
                        String literal;
                        try {
                            literal = parseLiteral();
                            if (step.kind == NodeKind.PROCINST) {
                                skipSpaces();
                                step.name = literal;
                            } else {
                                throw errorf("%s() has no arguments", name);
                            }
                        } catch (CompilerException e) {
                            if (e instanceof NoLiteralException) {
                                step.name = "*";
                            } else {
                                throw errorf("%v", e.getMessage());
                            }
                        }
                        if (!skipByte(')')) {
                            throw errorf("%s() missing ')'", name);
                        }
                        skipSpaces();
                    } else if ("*".equals(step.name) && step.kind == NodeKind.ANY) {
                        step.kind = NodeKind.START;
                    }
                }
            }
            if (skipByte('[')) {
                skipSpaces();
                LinkedList<State> stack = new LinkedList<>();
                LinkedList<Predicate> sub = new LinkedList<>();
                boolean and = false;
                nextPred:
                while (true) {
                    if (skipByte('(')) {
                        stack.add(new State(sub, and));
                    }
                    Predicate next;
                    try {
                        int pos = parseInt();
                        if (pos == 0) {
                            throw errorf("positions start at 1");
                        }
                        next = new PositionPredicate(pos);
                    } catch (CompilerException e) {
                        if (skipString("contains(")) {
                            Path path;
                            path = parsePath();
                            skipSpaces();
                            if (!skipByte(',')) {
                                throw errorf("contains() expected ',' followed by a literal string");
                            }
                            skipSpaces();
                            String value;
                            value = parseLiteral();
                            skipSpaces();
                            if (skipByte(')')) {
                                throw errorf("contains() missing ')'");
                            }
                            next = new ContainsPredicate(path, value);
                        } else if (skipString("not(")) {
                            Path path = parsePath();
                            skipSpaces();
                            if (!skipByte(')')) {
                                throw errorf("not() missing ')'");
                            }
                            next = new NotPredicate(path);
                        } else {
                            Path path = parsePath();
                            if (path.getPath().charAt(0) == '-') {
                                if (!StringUtils.isNumber(path.getPath())) {
                                    throw errorf("positions must be positive");
                                }
                            }
                            skipSpaces();
                            if (skipByte('=')) {
                                skipSpaces();
                                String value;
                                value = parseLiteral();
                                next = new EqualsPredicate(path, value);
                            } else {
                                next = new ExistsPredicate(path);
                            }
                        }
                    }
                    while (true) {
                        if (and) {
                            AndPredicate p = (AndPredicate) sub.poll();
                            p.addSub(next);
                            sub.push(p);
                        } else {
                            sub.push(next);
                        }
                        if (skipSpaces()) {
                            int mark = i;
                            if (skipString("and") && skipSpaces()) {
                                if (!and) {
                                    and = true;
                                    sub.push(new AndPredicate(sub.poll()));
                                }
                                continue nextPred;
                            } else if (skipString("or") && skipSpaces()) {
                                and = false;
                                continue nextPred;
                            } else {
                                i = mark;
                            }
                        }
                        if (skipByte(')')) {
                            if (stack.size() == 0) {
                                throw new LexException("unexpected ')");
                            }
                            if (sub.size() == 1) {
                                next = sub.peekFirst();
                            } else {
                                next = new OrPredicate(sub);
                            }
                            State s = stack.poll();
                            sub = (LinkedList<Predicate>) s.sub;
                            and = s.and;
                            continue;
                        }
                        if (stack.size() > 0) {
                            throw errorf("expected ')'");
                        }
                        if (sub.size() == 1) {
                            step.pred = sub.peek();
                        } else {
                            step.pred = new OrPredicate(sub);
                        }
                        if (!skipByte(']')) {
                            throw errorf("expected ']'");
                        }
                        skipSpaces();
                        break;
                    }
                    break;
                }
                steps.add(step);
                if (skipByte('/')) {
                    if ((start == 0 || start == i) && i < path.length()) {
                        throw errorf(String.format("unexpected %c", path.charAt('i')));
                    }
                    return new Path(steps, path.substring(start, i));
                }
            }
            throw new UnreachableException();
        }

        private String parseLiteral() throws CompilerException {
            if (skipByte('"')) {
                int mark = i;
                if (!skipByteFind('"')) {
                    throw new CompilerException("missing '\"'");
                }
                return path.substring(mark, i - 1);
            }
            if (skipByte('\'')) {
                int mark = i;
                if (skipByteFind('\'')) {
                    throw new CompilerException("missing '\"'");
                }
                return path.substring(mark, i - 1);
            }
            throw new NoLiteralException();
        }

        private int parseInt() throws CompilerException {
            int mark = i;
            int v = 0;
            while (i < path.length() && path.charAt(i) >= '0' && path.charAt(i) < '9') {
                v *= 10;
                v += path.charAt(i) - '0';
                i++;
            }
            if (i == mark) {
                throw new CompilerException("no number to format");
            }
            return v;
        }

        private int parseInt(int v) {
            int mark = i;
            while (i < path.length() && path.charAt(i) >= '0' &&
                    path.charAt(i) <= '9') {
                v *= 10;
                v += path.charAt(i) - '0';
                i++;
            }
            if (i == mark) {
                return 0;
            }
            return v;
        }

        private boolean skipByte(char b) {
            if (i < path.length() && path.charAt(i) == b) {
                i++;
                return true;
            }
            return false;
        }

        private boolean skipByteFind(char b) {
            for (int x = i; x < path.length(); x++) {
                if (path.charAt(x) == b) {
                    i = x = 1;
                    return true;
                }
            }
            return false;
        }

        private boolean peekByte(char b) {
            return i < path.length() && path.charAt(i) == b;
        }

        private boolean skipSpaces() {
            int mark = i;
            for (; i < path.length(); i++) {
                if (path.charAt(i) != ' ') {
                    break;
                }
                i++;
            }
            return i != mark;
        }

        private boolean skipString(String s) {
            if (i + s.length() <= path.length() && path.substring(i, i + s.length()).equals(s)) {
                i += s.length();
                return true;
            }
            return false;
        }

        private boolean skipName() {
            if (i >= path.length()) {
                return false;
            }
            if (path.charAt(i) == '*') {
                i++;
                return true;
            }
            int start = i;
            while (i < path.length() && (path.charAt(i) >= 0x80) || isNameByte((byte) path.charAt(i))) {
                i++;
            }
            return i > start;
        }

        private boolean isNameByte(byte c) {
            return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z' || '0' <= c && c <= '9' || c == '_' || c == '.' || c == '-';
        }

    }
}
