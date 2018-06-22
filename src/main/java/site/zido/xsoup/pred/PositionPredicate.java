package site.zido.xsoup.pred;

import site.zido.xsoup.PathStepState;
import site.zido.xsoup.Predicate;

/**
 * Predicate keywords : position.
 *
 * @author zido
 */
public class PositionPredicate implements Predicate {
    private int pos;

    @Override
    public void process() {

    }

    public int getPos() {
        return pos;
    }

    @Override
    public boolean test(PathStepState state) {
        return state.getPos() == pos;
    }
}
