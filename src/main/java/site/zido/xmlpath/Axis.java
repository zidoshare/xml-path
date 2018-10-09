package site.zido.xmlpath;

/**
 * XPath Axes.please {@see https://www.w3schools.com/xml/xpath_axes.asp}
 *
 * @author zido
 */
public enum Axis {
    /**
     * Selects the current node.
     */
    SELF("self"),
    /**
     * Selects the parent of the current node.
     */
    PARENT("parent"),
    /**
     * Selects all ancestors (parent, grandparent, etc.) of the current node.
     */
    ANCESTOR("ancestor"),
    /**
     * Selects all ancestors (parent, grandparent, etc.) of the current node and the current node itself.
     */
    ANCESTOR_OR_SELF("ancestor-or-self"),
    /**
     * Selects all children of the current node.
     */
    CHILD("child"),
    /**
     * Selects all descendants (children, grandchildren, etc.) of the current node.
     */
    DESCENDANT("descendant"),
    /**
     * Selects all descendants (children, grandchildren, etc.) of the current node and the current node itself.
     */
    DESCENDANT_OR_SELF("descendant-or-self"),
    /**
     * Selects everything in the document after the closing tag of the current node.
     */
    FOLLOWING("following"),
    /**
     * Selects all siblings after the current node.
     */
    FOLLOWING_SIBLING("following-sibling"),
    /**
     * Selects all nodes that appear before the current node in the document, except ancestors, attribute nodes and namespace nodes.
     */
    PRECEDING("preceding"),
    /**
     * Selects all siblings before the current node.
     */
    PRECEDING_SIBLING("preceding-sibling"),
    /**
     * Selects all attributes of the current node.
     */
    ATTRIBUTE("attribute");

    private String type;
    Axis(String str){
        type = str;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
}
