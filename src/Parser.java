import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by petrovich on 4/21/17.
 */
public class Parser {
    private LexicalAnalyzer lex;

    private void check(Token... arr) throws Pexception {
        for (Token t : arr) {
            if (t == lex.curToken()) {
                return;
            }
        }
        throw new Pexception("unexpected symbol: " + lex.curToken().toString() + " expected: " + Arrays.toString(arr));
    }

    private Tree S() throws Pexception {
        Tree e = E();
        switch (lex.curToken()) {
            case END:
                return new Tree("S", e);
            default:
                throw new Pexception("Unexpected symbol: " + lex.curToken().toString() + " expected: END");
        }
    }

    private Tree E() throws Pexception {
        return new Tree("E", T(), E1());
    }

    private Tree E1() throws Pexception {
        check(Token.CHOOSE, Token.CLOSE_BRACKET, Token.END, Token.OPEN_BRACKET, Token.CHARACTER);
        switch (lex.curToken()) {
            case CHOOSE:
            case CHARACTER:
            case OPEN_BRACKET:
                lex.nextToken();
                return new Tree("E'", new Tree("|"), T(), E1());
            case END:
            case CLOSE_BRACKET:
                return new Tree("E'");
            default:
                throw new AssertionError();
        }
    }

    private Tree F() throws Pexception {
        return new Tree("F", A(), F1());
    }

    private Tree F1() throws Pexception {
        check(Token.KLEENE_CLOSURE, Token.CHARACTER, Token.CHOOSE, Token.END, Token.OPEN_BRACKET, Token.CLOSE_BRACKET, Token.PLUS);
        switch (lex.curToken()) {
            case KLEENE_CLOSURE:
                lex.nextToken();
                return new Tree("F'", new Tree("*"), F1());
            case PLUS:
                lex.nextToken();
                return new Tree("F'", new Tree("+"), F1());
            case CHARACTER:
            case CHOOSE:
            case END:
            case OPEN_BRACKET:
            case CLOSE_BRACKET:
                return new Tree("F'");
            default:
                throw new AssertionError();
        }
    }

    private Tree T() throws Pexception {
        return new Tree("T", F(), T1());
    }

    private Tree T1() throws Pexception {
        check(Token.CHOOSE, Token.END, Token.CLOSE_BRACKET, Token.CHARACTER, Token.OPEN_BRACKET);
        switch (lex.curToken()) {
            case CHARACTER:
            case OPEN_BRACKET:
                return new Tree("T'", F(), T1());
            case CHOOSE:
            case END:
            case CLOSE_BRACKET:
                return new Tree("T'");
            default:
                throw new AssertionError();
        }
    }


    private Tree A() throws Pexception {
        check(Token.CHARACTER, Token.OPEN_BRACKET);
        switch (lex.curToken()) {
            case CHARACTER:
                String ch = String.valueOf((char) lex.lastChar());
                lex.nextToken();
                return new Tree("A", new Tree(ch));
            case OPEN_BRACKET:
                lex.nextToken();
                Tree a = new Tree("A", new Tree("("), E(), new Tree(")"));
                lex.nextToken();
                return a;
            default:
                throw new AssertionError();
        }
    }

    Tree parse(InputStream is) throws Pexception {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }
}
