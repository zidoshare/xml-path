package site.zido.xsoup.pred;

import site.zido.xsoup.Path;
import site.zido.xsoup.Predicate;

/**
 * Predicate keywords : not.
 *
 * @author zido
 */
public class NotPredicate implements Predicate {
    private Path path;

    public NotPredicate(Path path) {
        this.path = path;
    }

    @Override
    public void process() {

    }
}
