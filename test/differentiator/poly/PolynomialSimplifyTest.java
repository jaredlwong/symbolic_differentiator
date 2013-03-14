package differentiator.poly;

import java.math.BigDecimal;

import org.junit.Test;

import differentiator.ast.ExpressionElement;
import differentiator.parse.Lexer;
import differentiator.parse.Parser;
import differentiator.parse.Token;

public class PolynomialSimplifyTest {
    @Test
    public void basicSimplifyTest() {
        String input = "(x+x)";

        Token[] tokens = Lexer.getTokens(input);
        ExpressionElement parseTree = Parser.getParseTree(tokens);
        System.out.println(parseTree.interpret());
        Polynomial poly = parseTree.accept(new GeneratePolynomialVisitor());
        System.out.println(poly.getTerms());
        
        System.out.println(BigDecimal.valueOf(0).equals(BigDecimal.valueOf(0.0)));
    }
}
