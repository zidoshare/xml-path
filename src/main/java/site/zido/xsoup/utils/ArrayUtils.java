package site.zido.xsoup.utils;

public class ArrayUtils {
    public static  byte[] concat(byte[] a, byte[] b) {
        byte[] result =  new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
