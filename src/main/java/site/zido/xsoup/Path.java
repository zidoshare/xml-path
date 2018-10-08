package site.zido.xsoup;

import java.util.List;

/**
 * Path is a compiled path that can be applied to a context node to obtain a matching node set.
 * A single Path can be applied concurrently to any number of context nodes.
 *
 * @author zido
 */
public class Path {
    /**
     * the path str
     */
    private String path;
    private List<PathStep> steps;

    public Path(List<PathStep> steps, String path) {
        this.steps = steps;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    /**
     * Exists boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public boolean exists(Node context) {
        return iter(context).next();
    }

    /**
     * iterator of context.
     *
     * @param context the context
     * @return the iterator
     */
    public Iter iter(Node context) {
        Iter iter = new Iter(new PathStepState[steps.size()], new boolean[context.getNodes().length]);
        for (int i = 0; i < steps.size(); i++) {
            PathStepState state = new PathStepState();
            state.setStep(steps.get(i));
            iter.states[i] = state;
        }
        iter.states[0].init(context);
        return iter;
    }

    /**
     * The type Iter.
     */
    public class Iter {
        private PathStepState[] states;
        private boolean[] seen;

        private Iter(PathStepState[] states, boolean[] seen) {
            this.states = states;
            this.seen = seen;
        }


        /**
         * Node node.
         *
         * @return the node
         */
        public Node node() {
            PathStepState state = states[states.length - 1];
            if (state.getPos() == 0) {
                throw new RuntimeException("Iter.node called before Iter.next");
            }
            if (state.getNode() == null) {
                throw new RuntimeException("Iter.node called after Iter.next false");
            }
            return state.getNode();
        }

        /**
         * Next boolean.
         *
         * @return the boolean
         */
        public boolean next() {
            int tip = states.length - 1;
            outer:
            while (true) {
                while (!states[tip].next()) {
                    tip--;
                    if (tip == -1) {
                        return false;
                    }
                }
                while (tip < states.length - 1) {
                    tip++;
                    states[tip].init(states[tip - 1].getNode());
                    if (!states[tip].next()) {
                        tip--;
                        continue outer;
                    }
                }
                if (seen[states[tip].getNode().getPos()]) {
                    continue;
                }
                seen[states[tip].getNode().getPos()] = true;
                return true;
            }
            //unreachable
        }
    }

    /**
     * To string string.
     *
     * @param context the context
     * @return the string
     */
    public String toString(Node context) throws NoContentException {
        Iter iter = iter(context);
        if (iter.next()) {
            return iter.node().toString();
        }
        throw new NoContentException();
    }

    /**
     * Get bytes byte [ ].
     *
     * @param node the node
     * @return the byte [ ]
     */
    public byte[] bytes(Node node) throws NoContentException {
        Iter iter = iter(node);
        if (iter.next()) {
            return iter.node().getBytes();
        }
        throw new NoContentException();
    }
}
