import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by petrovich on 4/21/17.
 */
public class Parser {
    private LexicalAnalyzer lex;

    private Tree S() throws ParseException {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
            case CHARACTER:
                Tree arg = C();
                if (lex.curToken() == Token.END) {
                    return new Tree("S", arg);
                }
                throw new ParseException("Unexpected non-end at ", lex.curPos());
            case END:
                return new Tree("S");
            case CHOOSE:
                throw new ParseException("Unexpected token | at", lex.curPos());
            case KLEENE_CLOSURE:
                throw new ParseException("Unexpected token * at", lex.curPos());
            case CLOSE_BRACKET:
                throw new ParseException("Unexpected token ) at", lex.curPos());
            default:
                throw new AssertionError();
        }
    }

    private Tree Scont() throws ParseException {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
            case CHARACTER:
            case KLEENE_CLOSURE:
            case CHOOSE:
                Tree arg = Ccont();
                return new Tree("S'", arg);
            case END:
                return new Tree("S'");
            default:
                return new Tree("S'");
        }
    }

    private Tree Ccont() throws ParseException {
        Tree cont, after;
        switch (lex.curToken()) {
            case KLEENE_CLOSURE:
                lex.nextToken();
                cont = Scont();
                return new Tree("C'", new Tree("*"), cont);
            case CHOOSE:
                lex.nextToken();
                cont = C();
                after = Scont();
                return new Tree("C'", new Tree("|"), cont, after);
            case OPEN_BRACKET:
            case CHARACTER:
                cont = C();
                after = Scont();
                return new Tree("C'", cont, after);
            case CLOSE_BRACKET:
                throw new ParseException(")", lex.curPos());
            default:
                throw new AssertionError();
        }
    }

    private Tree C() throws ParseException {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
                lex.nextToken();
                Tree sub = C();
                if (lex.curToken() != Token.CLOSE_BRACKET) {
                    throw new ParseException("Expected token ) at ", lex.curPos());
                }
                lex.nextToken();
                Tree cont = Scont();
                return new Tree("C", new Tree("("), sub, new Tree(")"), cont);
            case CHARACTER:
                String val = String.valueOf((char) lex.lastChar());
                lex.nextToken();
                Tree s = Scont();
                return new Tree("C", new Tree(val), s);
            case CHOOSE:
                throw new ParseException("Unexpected token | at", lex.curPos());
            case KLEENE_CLOSURE:
                throw new ParseException("Unexpected token * at", lex.curPos());
            case CLOSE_BRACKET:
                throw new ParseException("Unexpected token ) at", lex.curPos());
            default:
                throw new AssertionError();
        }
    }

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }
}
