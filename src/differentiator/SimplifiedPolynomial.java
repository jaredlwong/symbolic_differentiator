package differentiator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import differentiator.ast.ExpressionElement;
import differentiator.parse.Lexer;
import differentiator.parse.Parser;

public class SimplifiedPolynomial {
    private final static Lexer lexer = Lexer.INSTANCE;
    private final static Parser parser = Parser.INSTANCE;

    public static String evaluate(String expression) {
        lexer.setInput(expression);

        parser.setLexer(lexer);
        ExpressionElement input = parser.getParseTree();

        Polynomial polynomialRepresentation =
                input.accept(new GeneratePolynomialVisitor());
        Polynomial simplifiedExpression =
                Polynomial.simplify(polynomialRepresentation);

        return simplifiedExpression.toString();
    }
    
    /**
     * Repeatedly reads expressions from the console, and outputs the results of
     * evaluating them. Inputting an empty line will terminate the program.
     * @param args unused
     */
    public static void main(String[] args) throws IOException {
        String result;

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String expression;
        do {
            // display prompt
            System.out.print("$ ");
            // read input
            expression = in.readLine();
            // terminate if input empty
            if (!expression.equals("")) {
                try {
                    result = SimplifiedPolynomial.evaluate(expression);
                    System.out.println(result);
                } catch (RuntimeException re) {
                    System.err.println("Error: " + re.getMessage());
                }
            }
        } while (!expression.equals(""));
    }
}
