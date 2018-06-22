package site.zido.xsoup;

/**
 * The interface Predicate.
 *
 * @author zido
 */
public interface Predicate {
    void predicate();
    default boolean test(PathStepState state){
        throw new UnsupportedOperationException();
    }
}
