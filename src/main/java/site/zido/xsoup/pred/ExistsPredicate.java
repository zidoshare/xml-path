package site.zido.xsoup.pred;

import site.zido.xsoup.Path;
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
}
