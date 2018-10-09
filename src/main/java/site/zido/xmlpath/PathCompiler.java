package site.zido.xmlpath;

import java.util.ArrayList;
import java.util.List;

public class PathCompiler {
    private String path;
    private int i;



    public Path parsePath() {
        List<PathStep> steps = new ArrayList<>();
        int start = i;
        while(true){
            PathStep step = new PathStep(Axis.CHILD);
            
        }
    }
}
