import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

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

    public void printTree(int level, BufferedWriter out) throws IOException {
        if (level == 0) {
            out.write("graph g {" + System.lineSeparator());
        }
        out.write("\t" + "\"" + this.hashCode() + node + level + "\"" + " [shape=circle, style=filled, fillcolor=\"" + Colorize() + "\", label=\""+node+"\"];" + System.lineSeparator());
        if (children != null) {
            for (Tree t : children) {
                t.printTree(level+1, out);
            }
            out.write(generateEdges(node, level));
            out.write(generateChildRanks(level+1));
        }
        if (level == 0) {
            out.write("}" + System.lineSeparator());
        }
    }

    private String Colorize() {
        if (node.equals("S") && children != null) {
            return "green";
        } else if (node.equals("A")) {
            return "darkviolet";
        } else if (node.equals("E1")) {
            return "gold3";
        } else if (node.equals("F1") && children != null) {
            return "cyan";
        } else return "sienna";
    }

    private String generateChildRanks(int level) {
        String result = "\t{ rank=same ";
        StringJoiner joiner = new StringJoiner(",");
        for (Tree t : children) {
            joiner.add("\"" + t.hashCode() + t.node+level + "\"");
        }
        result += joiner.toString();
        result+=" }" + System.lineSeparator();
        return result;
    }

    private String generateEdges(String currNode, int level) {
        String result = "";
        for (Tree t : children) {
            result += "\t" + "\"" + this.hashCode() + currNode + level + "\"" + " -- " + "\"" + t.hashCode() + t.node + (level + 1) + "\"" + ";" + System.lineSeparator();
        }

        return result;
    }
}
