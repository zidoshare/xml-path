package site.zido.xsoup.pred;

import site.zido.xsoup.Path;
import site.zido.xsoup.PathStepState;
import site.zido.xsoup.Predicate;

/**
 * The type Exists predicate.
 *
 * @author zido
 */
public class ExistsPredicate implements Predicate {
    private Path path;

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
