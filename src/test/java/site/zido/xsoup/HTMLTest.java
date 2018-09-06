package site.zido.xsoup;

import org.junit.Test;

public class HTMLTest {
    @Test
    public void testRootText(){
        String xml = "<root>a<foo>b</foo>c<bar>d</bar>e<bar>f</bar>g</root>";
        Node node = Xsoup.parse(xml);
    }
}
