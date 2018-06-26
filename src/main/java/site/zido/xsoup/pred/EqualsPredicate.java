package site.zido.xsoup.pred;

import site.zido.xsoup.Path;
import site.zido.xsoup.PathStepState;
import site.zido.xsoup.Predicate;

/**
 * Predicate keywords : equals.
 *
 * @author zido
 */
public class EqualsPredicate implements Predicate {
    private String value;
    private Path path;

    public EqualsPredicate(Path path, String value) {
        this.path = path;
        this.value = value;
    }

    @Override
    public void process() {

    }

    public Path getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean test(PathStepState state) {
        Path.Iter iter = path.iter(state.getNode());
        while (iter.next()) {
            if (iter.node().eqStr(value)) {
                return true;
            }
        }
        return false;
    }
}
