package differentiator.poly;

import org.junit.Test;

import differentiator.DifferentiationVisitor;
import differentiator.ast.ExpressionElement;
import differentiator.parse.Lexer;
import differentiator.parse.Parser;
import differentiator.parse.Token;

public class GeneratePolynomialVisitorTest {

    @Test
    public void basicTest() {
        String expression = "((x+1)*(y+x))";
        Token[] tokens = Lexer.getTokens(expression);

        ExpressionElement input = Parser.getParseTree(tokens);

        DifferentiationVisitor derivativeVisitor =
                new DifferentiationVisitor("x");
        input.accept(derivativeVisitor);

        ExpressionElement derivative = derivativeVisitor.getDerivative();
        Polynomial derivPoly = derivative.accept(new GeneratePolynomialVisitor());
        System.out.println(derivPoly);
    }
}
