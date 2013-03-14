package differentiator.parse;

import org.junit.Test;

public class ParserBadTests {
    @Test(expected = RuntimeException.class)
    public void onlyTerminalTest() {
        Parser.getParseTree(Token.getInstance("$"));
    }

    @Test(expected = RuntimeException.class)
    public void emptyTest() {
        Parser.getParseTree(Token.getInstance(""));
    }

    @Test(expected = RuntimeException.class)
    public void noExpressionTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void noExpressionOnlyLeftParenTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("("),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void noExpressionOnlyRightParenTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance(")"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void noExpressionOnlyParensTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("("),
                Token.getInstance(")"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void noExpressionOnlyParensNestedTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("("),
                Token.getInstance("("),
                Token.getInstance("("),
                Token.getInstance(")"),
                Token.getInstance(")"),
                Token.getInstance(")"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void unmatchedParensLeftHeavyTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("("),
                Token.getInstance("("),
                Token.getInstance("("),
                Token.getInstance("a"),
                Token.getInstance(")"),
                Token.getInstance(")"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void unmatchedParensRightHeavyTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("("),
                Token.getInstance("("),
                Token.getInstance("a"),
                Token.getInstance(")"),
                Token.getInstance(")"),
                Token.getInstance(")"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void noExpressionOnlyPlusTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("+"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void noExpressionOnlyStarTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("*"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }
    
    @Test(expected = RuntimeException.class)
    public void noOperatorNumbersTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("1"),
                Token.getInstance("1"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }
    @Test(expected = RuntimeException.class)
    public void noOperatorVariablesTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("a"),
                Token.getInstance("b"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void noOperatorMixTest() {
        Token[] tokens = {
                Token.getInstance("$"),
                Token.getInstance("a"),
                Token.getInstance("1"),
                Token.getInstance("$")};
        Parser.getParseTree(tokens);
    }
}
