package site.zido.xsoup.pred;

import site.zido.xsoup.Node;
import site.zido.xsoup.PathStepState;
import site.zido.xsoup.Predicate;

/**
 * The type Position predicate.
 *
 * @author zido
 */
public class PositionPredicate implements Predicate {
    private int pos;

    @Override
    public void predicate() {

    }

    public int getPos() {
        return pos;
    }

    @Override
    public boolean test(PathStepState state) {
        return state.getPos() == pos;
    }
}
