package differentiator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class LexerTest {
    @Test
    public void extremelyBasicTest() {
        Lexer lex = Lexer.INSTANCE;

        String input = "()";
        lex.setInput(input);
        assertTrue(lex.getTokens().size() == 2);
        List<Token> expected = new ArrayList<Token>(2);
        expected.add(Token.getInstance("("));
        expected.add(Token.getInstance(")"));
        assertTrue(expected.equals(lex.getTokens()));

        input = "(x)";
        lex.setInput(input);
        assertTrue(lex.getTokens().size() == 3);
        expected = new ArrayList<Token>(3);
        expected.add(Token.getInstance("("));
        expected.add(Token.getInstance("x"));
        expected.add(Token.getInstance(")"));
        assertTrue(expected.equals(lex.getTokens()));
    }

    @Test
    public void basicTest() {
        Lexer lex = Lexer.INSTANCE;

        String input = "((0 + 0.0) * foobar)";
        lex.setInput(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {"(","(","0","+","0.0",")","*","foobar",")"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        assertTrue(expected.equals(lex.getTokens()));

        input = "((0+0.0)*foobar)";
        lex.setInput(input);
        assertTrue(expected.equals(lex.getTokens()));

        input = "   (    (   0+   0.0)   *  foobar)   ";
        lex.setInput(input);
        assertTrue(expected.equals(lex.getTokens()));
    }

    @Test
    public void enumerateTest() {
        Lexer lex = Lexer.INSTANCE;

        String input = "((0 + 0.0) * foobar)";
        lex.setInput(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {"(","(","0","+","0.0",")","*","foobar",")"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        Iterator<Token> expIter = expected.iterator();
        for (Token t : lex) {
            assertEquals(t, expIter.next());
        }
        assertTrue(true);
        
    }
}
