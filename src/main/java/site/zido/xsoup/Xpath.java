package site.zido.xsoup;

public class Xpath {
    private String xpath;
    private transient volatile boolean compiled = false;

    public Xpath(String xpath){
        this.xpath = xpath;
        if(xpath.length() > 0){
            compile();
        }
    }

    private void compile() {


    }
}
