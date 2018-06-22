package site.zido.xsoup.pred;

import site.zido.xsoup.Path;
import site.zido.xsoup.PathStepState;
import site.zido.xsoup.Predicate;

public class ContainsPredicate implements Predicate {
    private Path path;
    private String value;

    @Override
    public void predicate() {

    }

    @Override
    public boolean test(PathStepState state) {
        Path.Iter iter = path.iter(state.getNode());
        while (iter.next()){
            if(iter.node().contains(value)){
                return true;
            }
        }
        return false;
    }
}
