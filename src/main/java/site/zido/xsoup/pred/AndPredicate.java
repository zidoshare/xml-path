package site.zido.xsoup.pred;

import site.zido.xsoup.Predicate;

import java.util.LinkedList;
import java.util.List;

/**
 * Predicate keywords : and.
 *
 * @author zido
 */
public class AndPredicate implements Predicate {
    private List<Predicate> sub = new LinkedList<>();

    public AndPredicate(Predicate sub) {
        this.sub.add(sub);
    }

    public AndPredicate(List<Predicate> sub){
        this.sub.addAll(sub);
    }

    @Override
    public void process() {

    }

    public void addSub(Predicate next) {
        this.sub.add(next);
    }
}
