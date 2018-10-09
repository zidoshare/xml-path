package site.zido.xmlpath.pred;

import site.zido.xmlpath.Path;
import site.zido.xmlpath.PathStepState;
import site.zido.xmlpath.Predicate;

/**
 * Predicate keywords : contains.
 *
 * @author zido
 */
public class ContainsPredicate implements Predicate {
    private Path path;
    private String value;

    public ContainsPredicate(Path path, String value) {
        this.path = path;
        this.value = value;
    }

    @Override
    public void predicate() {

    }

    @Override
    public boolean test(PathStepState state) {
        Path.Iter iter = path.iter(state.getNode());
        while (iter.next()) {
            if (iter.node().contains(value)) {
                return true;
            }
        }
        return false;
    }
}
