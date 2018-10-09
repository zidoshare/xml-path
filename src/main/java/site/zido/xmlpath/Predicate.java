package site.zido.xmlpath;

/**
 * The interface of Predicate.
 *
 * @author zido
 */
public interface Predicate {
    /**
     * predicate .
     */
    void predicate();

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
