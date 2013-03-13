package differentiator.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class LexerTest {

    @Test
    public void extremelyBasicTest() {
        String input = "()";

        Token[] tokens = Lexer.getTokens(input);
        assertTrue(tokens.length == 2);
        List<Token> expected = new ArrayList<Token>(2);
        expected.add(Token.getInstance("$"));
        expected.add(Token.getInstance("$"));
        assertArrayEquals(expected.toArray(new Token[expected.size()]),tokens);

        input = "(x)";
        tokens = Lexer.getTokens(input);
        assertTrue(tokens.length == 3);
        expected = new ArrayList<Token>(3);
        expected.add(Token.getInstance("$"));
        expected.add(Token.getInstance("x"));
        expected.add(Token.getInstance("$"));
        assertArrayEquals(expected.toArray(new Token[expected.size()]),tokens);
    }

    @Test
    public void basicTest() {
        String input = "((0 + 0.0) * foobar)";
        Token[] tokens = Lexer.getTokens(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {"$","(","0","+","0.0",")","*","foobar","$"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        assertArrayEquals(expected.toArray(new Token[expected.size()]),tokens);

        input = "((0+0.0)*foobar)";
        tokens = Lexer.getTokens(input);
        assertArrayEquals(expected.toArray(new Token[expected.size()]),tokens);

        input = "   (    (   0+   0.0)   *  foobar)   ";
        tokens = Lexer.getTokens(input);
        assertArrayEquals(expected.toArray(new Token[expected.size()]),tokens);
    }

    @Test
    public void enumerateTest() {
        String input = "((0 + 0.0) * foobar)";
        Token[] tokens = Lexer.getTokens(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {"$","(","0","+","0.0",")","*","foobar","$"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        Iterator<Token> expIter = expected.iterator();
        for (Token t : tokens) {
            assertEquals(t, expIter.next());
        }
    }

    @Test
    public void unaryMinusTest() {
        String input = "(-(0 + - 0.0) * - foobar)";
        Token[] tokens = Lexer.getTokens(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {
                "$","-1","*","(","0","+","(","-1","*","0.0",")",
                ")","*","(","-1","*","foobar",")","$"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        Iterator<Token> expIter = expected.iterator();
        for (Token t : tokens) {
            assertEquals(t, expIter.next());
        }
    }

    @Test
    public void unaryMinus2Test() {
        String input = "(-(0 +- 0.0) * - foobar)";
        Token[] tokens = Lexer.getTokens(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {
                "$","-1","*","(","0","+","(","-1","*","0.0",")",
                ")","*","(","-1","*","foobar",")","$"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        Iterator<Token> expIter = expected.iterator();
        for (Token t : tokens) {
            assertEquals(t, expIter.next());
        }
    }

    @Test
    public void unaryMinus3Test() {
        String input = "(-(0 +-0.0) * - foobar)";
        Token[] tokens = Lexer.getTokens(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {
                "$","-1","*","(","0","+","(","-1","*","0.0",")",
                ")","*","(","-1","*","foobar",")","$"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        Iterator<Token> expIter = expected.iterator();
        for (Token t : tokens) {
            assertEquals(t, expIter.next());
        }
    }

    @Test
    public void unaryMinus4Test() {
        String input = "(-foo^bar)";
        Token[] tokens = Lexer.getTokens(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {
                "$","(","-1","*","foo",")","^","bar","$"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        Iterator<Token> expIter = expected.iterator();
        for (Token t : tokens) {
            assertEquals(t, expIter.next());
        }
    }

    @Test
    public void unaryMinus5Test() {
        String input = "( -1 - 10 * -foobar ^ 10)";
        Token[] tokens = Lexer.getTokens(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {
                "$","(","-1","*","1",")","+","(","-1","*","10",")","*",
                "(","-1","*","foobar",")","^","10","$"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        Iterator<Token> expIter = expected.iterator();
        for (Token t : tokens) {
            assertEquals(t, expIter.next());
        }
    }

    @Test
    public void unaryMinus6Test() {
        String input = "((x+y)^(-1)-b)";
        Token[] tokens = Lexer.getTokens(input);
        List<Token> expected = new LinkedList<Token>();
        String expectedTokens[] = {
                "$","(","x","+","y",")","^",
                "(","(","-1","*","1",")",")","+",
                "(","-1","*","b",")","$"};
        for (String t : expectedTokens) {
           expected.add(Token.getInstance(t));
        }
        Iterator<Token> expIter = expected.iterator();
        for (Token t : tokens) {
            assertEquals(t, expIter.next());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void simpleBadInput() {
        String input = "(";
        Lexer.getTokens(input);
    }
}
