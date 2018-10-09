package site.zido.xmlpath;

/**
 * the node kind enum
 *
 * @author zido
 */
public enum NodeKind {
    /**
     * Any node kind.
     */
    ANY,
    /**
     * Start node kind.
     */
    START,
    /**
     * End node kind.
     */
    END,
    /**
     * Attr node kind.
     */
    ATTR,
    /**
     * Text node kind.
     */
    TEXT,
    /**
     * Comment node kind.
     */
    COMMENT,
    /**
     * Procinst node kind.
     */
    PROCINST,
}
