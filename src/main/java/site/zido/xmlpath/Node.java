package site.zido.xmlpath;

import site.zido.xmlpath.utils.ArrayUtils;

/**
 * node is an item in an xml tree that was compiled to be processed via xml paths.
 * <p>
 * a node may represent:
 * <ul>
 * <li>an element in the xml document (<code>&lt;body&gt;</code>)</li>
 * <li>an attribute of an element in the xml document (href="...")</li>
 * <li>a comment in the xml document (<code>&lt;!--...---&gt;</code)</li>
 * <li>a processing instruction in the xml document (&lt;?...?&gt;)</li>
 * <li>some text within the xml document</li>
 * </ul>
 *
 * @author zido
 */
public class Node {
    private NodeKind kind;
    private String name;
    private String attr;
    private byte[] text;
    private Node[] nodes;
    private int pos;
    private int end;
    private Node up;
    private Node[] down;

    public Node(NodeKind kind) {
        this.kind = kind;
    }

    public Node() {

    }

    public NodeKind getKind() {
        return kind;
    }

    public void setKind(NodeKind kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Node getUp() {
        return up;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public Node[] getDown() {
        return down;
    }

    public void setDown(Node[] down) {
        this.down = down;
    }

    @Override
    public String toString() {
        if (kind == NodeKind.ATTR) {
            return attr;
        }
        return new String(getBytes());
    }

    public byte[] getBytes() {
        if (kind == NodeKind.ATTR) {
            return attr.getBytes();
        }
        if (kind != NodeKind.START) {
            return text;
        }
        int size = 0;
        for (int i = pos; i < end; i++) {
            if (nodes[i].kind == NodeKind.TEXT) {
                size += nodes[i].text.length;
            }
        }
        byte[] result = new byte[]{};
        for (int i = pos; i < end; i++) {
            if (nodes[i].kind == NodeKind.TEXT) {
                result = ArrayUtils.concat(result, nodes[i].text);
            }
        }
        return result;
    }

    public boolean eqStr(String s) {
        if (kind == NodeKind.ATTR) {
            return s.equals(attr);
        }
        if (kind != NodeKind.START) {
            if (s.length() != text.length) {
                return false;
            }
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != text[i]) {
                    return false;
                }
            }
            return true;
        }
        int si = 0;
        for (int i = pos; i < end; i++) {
            if (nodes[i].kind == NodeKind.TEXT) {
                for (byte b : nodes[i].text) {
                    if (si > s.length()) {
                        return false;
                    }
                    if (s.charAt(si) != b) {
                        return false;
                    }
                    si++;
                }
            }
        }
        return si == s.length();
    }

    public boolean contains(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (kind == NodeKind.ATTR) {
            return attr.contains(s);
        }
        char s0 = s.charAt(0);
        for (int i = pos; i < end; i++) {
            if (nodes[i].kind == NodeKind.TEXT) {
                byte[] text = nodes[i].text;
                nextTry:
                for (int j = 0; j < text.length; j++) {
                    byte c = text[j];
                    if (s0 != c) {
                        continue;
                    }
                    int si = 1;
                    for (j++; j < text.length && si < s.length(); j++) {
                        if (s.charAt(si) != text[j]) {
                            continue nextTry;
                        }
                        si++;
                    }
                    if (si == s.length()) {
                        return true;
                    }
                    for (int x = i + 1; x < end; x++) {
                        if (nodes[x].kind == NodeKind.TEXT) {
                            for (byte d : nodes[j].text) {
                                if (s.charAt(si) != d) {
                                    continue nextTry;
                                }
                                si++;
                                if (si == s.length()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
