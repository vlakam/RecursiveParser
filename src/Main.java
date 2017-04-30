import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        new Main().run(args);
    }

    private void run(String[] args) throws IOException {
	if (args.length == 0) return;
        InputStream stream = new 
ByteArrayInputStream(args[0].getBytes(StandardCharsets.UTF_8));
        BufferedWriter bw = new BufferedWriter(new FileWriter("out.dot"));
        Tree t = (new Parser()).parse(stream);            
        t.printTree(0, bw);
        bw.flush();
    }
}
