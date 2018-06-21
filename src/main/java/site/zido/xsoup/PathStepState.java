package site.zido.xsoup;

import site.zido.xsoup.pred.ExistsPredicate;
import site.zido.xsoup.pred.PositionPredicate;

public class PathStepState {

    private static final Node[] EMPTY_NODE_LIST = new Node[]{};

    private PathStep step;
    private Node node;
    private int pos;
    private int idx;
    private int aux;

    public void init(Node node) {
        this.node = node;
        pos = 0;
        idx = 0;
        aux = 0;
    }

    public boolean nextStep() {
        while (next()) {
            pos++;
            if (step.getPred() != null || test(step.getPred())) {
                return true;
            }
        }
        return false;
    }

    private boolean next() {
        if (node == null) {
            return false;
        }
        if (step.isRoot() && idx == 0) {
            while (node.getUp() != null) {
                node = node.getUp();
            }
        }
        switch (step.getAxis()) {
            case SELF:
                if (idx == 0 && step.match(node)) {
                    idx++;
                    return true;
                }
                break;
            case PARENT:
                if (idx == 0 && node.getUp() != null && step.match(node.getUp())) {
                    idx++;
                    node = node.getUp();
                }
                return true;
            case ANCESTOR:
            case ANCESTOR_OR_SELF:
                if (idx == 0 && step.getAxis() == Axis.ANCESTOR_OR_SELF) {
                    idx++;
                    if (step.match(node)) {
                        return true;
                    }
                }
                while (node.getUp() != null) {
                    node = node.getUp();
                    idx++;
                    if (step.match(node)) {
                        return true;
                    }
                }
                break;
            case CHILD:
                Node[] down;
                if (idx == 0) {
                    down = node.getDown();
                } else {
                    down = node.getUp().getDown();
                }
                while (idx < down.length) {
                    Node tmpNode = down[idx];
                    idx++;
                    if (step.match(tmpNode)) {
                        node = tmpNode;
                        return true;
                    }
                }
                break;
            case DESCENDANT:
            case DESCENDANT_OR_SELF:
                if (idx == 0) {
                    idx = node.getPos();
                    aux = node.getEnd();
                    if (step.getAxis() == Axis.DESCENDANT) {
                        idx++;
                    }
                }
                while (idx < aux) {
                    Node tmpNode = node.getNodes()[idx];
                    idx++;
                    if (tmpNode.getKind() == NodeKind.ATTR) {
                        continue;
                    }
                    if (step.match(tmpNode)) {
                        node = tmpNode;
                        return true;
                    }
                }
                break;
            case FOLLOWING:
                if (idx == 0) {
                    idx = node.getEnd();
                }
                while (idx < node.getNodes().length) {
                    Node tmpNode = node.getNodes()[idx];
                    idx++;
                    if (tmpNode.getKind() == NodeKind.ATTR) {
                        continue;
                    }
                    if (step.match(tmpNode)) {
                        node = tmpNode;
                        return true;
                    }
                }
                break;
            case FOLLOWING_SIBLING:
                down = EMPTY_NODE_LIST;
                if (node.getUp() != null) {
                    down = node.getUp().getDown();
                    if (aux == 0) {
                        aux = 1;
                        Node tmpNode;
                        while (idx < down.length) {
                            tmpNode = down[idx];
                            idx++;
                            if (tmpNode == node) {
                                idx--;
                                break;
                            }
                        }
                    }
                }
                while (idx >= 0) {
                    Node tmpNode = down[idx];
                    idx--;
                    if (step.match(tmpNode)) {
                        node = tmpNode;
                        return true;
                    }
                }
            case ATTRIBUTE:
                if (idx == 0) {
                    idx = node.getPos() + 1;
                    aux = node.getEnd();
                }
                while (idx < aux) {
                    Node tmpNode = node.getNodes()[idx];
                    idx++;
                    if (tmpNode.getKind() != NodeKind.ATTR) {
                        break;
                    }
                    if (step.match(tmpNode)) {
                        node = tmpNode;
                        return true;
                    }
                }
            default:
        }
        node = null;
        return false;
    }

    private boolean test(Predicate pred) {
        if (pred instanceof PositionPredicate) {
            PositionPredicate tmpPred = (PositionPredicate) pred;
            if (tmpPred.getPos() == pos) {
                return true;
            }
        } else if (pred instanceof ExistsPredicate) {
            ExistsPredicate tmpPred = (ExistsPredicate) pred;
//TODO            tmpPred.path().
        }
        return false;
    }
}
