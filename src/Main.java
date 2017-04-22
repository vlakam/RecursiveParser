import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

/**
 * Created by petrovich on 4/23/17.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        new Main().run();
    }

    private void run() throws IOException {
        InputStream stream = new ByteArrayInputStream("(g|a)((de*)(ifmo|)(r*u*))*".getBytes(StandardCharsets.UTF_8));
        BufferedWriter bw = new BufferedWriter(new FileWriter("out.dot"));
        try {
            Tree t = (new Parser()).parse(stream);
            t.printTree(0, bw);
        } catch (ParseException e) {
            System.err.println(e.getMessage() + e.getErrorOffset());
        }
        bw.flush();
    }
}
