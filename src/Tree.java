import java.util.Arrays;
import java.util.List;

/**
 * Created by petrovich on 4/21/17.
 */
public class Tree {

    private String node;
    private List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }
}
