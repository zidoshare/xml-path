package site.zido.xsoup.utils;

/**
 * array utils.
 *
 * @author zido
 */
public class ArrayUtils {

    /**
     * concat a and b.
     *
     * @param a array a.
     * @param b array b.
     * @return new array with a and b
     */
    public static byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
