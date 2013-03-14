package differentiator.parse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import differentiator.ast.ExpressionElement;

/**
 * 1. number
 * 2. identifier
 * 3. mixed
 * 4. mixed backwards
 * @author jaredlwong
 *
 */
public class ParserUnitTests {
    private static Token[] generateTokens(String ... tokenStrings) {
        Token[] tokens = new Token[tokenStrings.length];
        for (int i = 0; i < tokenStrings.length; ++i) {
            tokens[i] = Token.getInstance(tokenStrings[i]);
        }
        return tokens;
    }
    @Test
    public void singleIntegerParseTest() {
        Token[] tokens = generateTokens("$","123","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "123";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void singleIdentifierParseTest() {
        Token[] tokens = generateTokens("$","foo","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "foo";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void numberAdditionParseTest() {
        Token[] tokens = generateTokens("$","1","+","1","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(1+1)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void identifierAdditionParseTest() {
        Token[] tokens = generateTokens("$","a","+","b","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(a+b)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void mixedAdditionParseTest() {
        Token[] tokens = generateTokens("$","123","+","foo","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(123+foo)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void mixedAdditionReverseParseTest() {
        Token[] tokens = generateTokens("$","foo","+","123","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(foo+123)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void numberSubtractionParseTest() {
        Token[] tokens = generateTokens("$","1","-","1","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(1-1)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void identifierSubtractionParseTest() {
        Token[] tokens = generateTokens("$","a","-","b","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(a-b)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void mixedSubtractionParseTest() {
        Token[] tokens = generateTokens("$","foo","-","123","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(foo-123)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void numberMultiplyParseTest() {
        Token[] tokens = generateTokens("$","1","*","1","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(1*1)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void identifierMultiplyParseTest() {
        Token[] tokens = generateTokens("$","a","*","b","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(a*b)";
        assertEquals(expected, expression.interpret());
    }

    @Test
    public void mixedMultiplyParseTest() {
        Token[] tokens = generateTokens("$","123","*","foo","$");
        ExpressionElement expression = Parser.getParseTree(tokens);
        String expected = "(123*foo)";
        assertEquals(expected, expression.interpret());
    }
}
