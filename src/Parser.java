import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by petrovich on 4/21/17.
 */
public class Parser {
    private LexicalAnalyzer lex;

    private Tree S() {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
            case CHARACTER:
                Tree arg = C();
                if (lex.curToken() == Token.END) {
                    return new Tree("S", arg);
                }
            case END:
                return new Tree("S");
            default:
                throw new AssertionError();
        }
    }

    private Tree C() {
        return new Tree("C");
    }

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }
}
