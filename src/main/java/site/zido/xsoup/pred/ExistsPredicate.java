package site.zido.xsoup.pred;

import site.zido.xsoup.Path;
import site.zido.xsoup.PathStepState;
import site.zido.xsoup.Predicate;

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
