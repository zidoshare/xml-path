package site.zido.xmlpath.pred;

import site.zido.xmlpath.Path;
import site.zido.xmlpath.Predicate;

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
    public void predicate() {

    }
}
