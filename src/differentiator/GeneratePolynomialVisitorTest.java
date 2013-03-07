package differentiator;

import org.junit.Test;

import differentiator.ast.ExpressionElement;
import differentiator.parse.Lexer;
import differentiator.parse.Parser;

public class GeneratePolynomialVisitorTest {
    private static final Lexer lexer = Lexer.INSTANCE;
    private static final Parser parser = Parser.INSTANCE;

    @Test
    public void basicTest() {
        String expression = "((x+1)*(y+x))";
        lexer.setInput(expression);

        parser.setLexer(lexer);
        ExpressionElement input = parser.getParseTree();

        DifferentiationVisitor derivativeVisitor =
                new DifferentiationVisitor("x");
        input.accept(derivativeVisitor);

        ExpressionElement derivative = derivativeVisitor.getDerivative();
        Polynomial derivPoly = derivative.accept(new GeneratePolynomialVisitor());
        System.out.println(derivPoly);
    }
}
