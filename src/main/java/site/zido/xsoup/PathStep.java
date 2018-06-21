package site.zido.xsoup;

/**
 * The type Path step.
 *
 * @author zido
 */
public class PathStep {
    private boolean root;
    private Axis axis;
    private String name;
    private NodeKind kind;
    private Predicate pred;

    public boolean match(Node node) {
        return node.getKind() != NodeKind.END &&
                (kind == NodeKind.ANY || kind == node.getKind()) &&
                ("*".equals(name) || node.getName().equals(name));
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeKind getKind() {
        return kind;
    }

    public void setKind(NodeKind kind) {
        this.kind = kind;
    }

    public Predicate getPred() {
        return pred;
    }

    public void setPred(Predicate pred) {
        this.pred = pred;
    }
}
