package site.zido.xsoup;

/**
 * The interface of Predicate.
 *
 * @author zido
 */
public interface Predicate {
    /**
     * process .
     */
    void process();

    /**
     * test the state
     *
     * @param state path state.
     * @return true/false
     */
    default boolean test(PathStepState state) {
        throw new UnsupportedOperationException();
    }
}
