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

    public PositionPredicate(int pos) {
        this.pos = pos;
    }

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
