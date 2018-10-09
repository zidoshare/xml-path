package site.zido.xmlpath.pred;

import site.zido.xmlpath.Path;
import site.zido.xmlpath.PathStepState;
import site.zido.xmlpath.Predicate;

/**
 * Predicate keywords : exists.
 *
 * @author zido
 */
public class ExistsPredicate implements Predicate {
    private Path path;

    public ExistsPredicate(Path path) {
        this.path = path;
    }

    @Override
    public void predicate() {

    }

    public Path path() {
        return path;
    }

    @Override
    public boolean test(PathStepState state) {
        return path.exists(state.getNode());
    }
}
