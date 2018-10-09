package site.zido.xmlpath.pred;

import site.zido.xmlpath.Path;
import site.zido.xmlpath.PathStepState;
import site.zido.xmlpath.Predicate;

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
    public void predicate() {

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
