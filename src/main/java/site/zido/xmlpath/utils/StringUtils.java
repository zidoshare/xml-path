package site.zido.xmlpath.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern NUMBER_PATTERN;
    static {
        NUMBER_PATTERN = Pattern.compile("^[1-9]+$");
    }
    public static boolean isNumber(String s){
        Matcher matcher = NUMBER_PATTERN.matcher(s);
        return matcher.matches();
    }
}
