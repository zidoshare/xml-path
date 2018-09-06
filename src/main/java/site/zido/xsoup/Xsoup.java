package site.zido.xsoup;

public class Xsoup {
    private String xpath;
    private transient volatile boolean compiled = false;

    public Xsoup(String xpath){
        this.xpath = xpath;
        if(xpath.length() > 0){
            compile();
        }
    }

    public static Node parse(String xml) {
        return null;
    }

    private void compile() {


    }
}
