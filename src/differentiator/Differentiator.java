package differentiator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import differentiator.ast.ExpressionElement;
import differentiator.parse.Lexer;
import differentiator.parse.Parser;


/** Symbolic differentiator */
public class Differentiator implements Evaluator {
    private final static Lexer lexer = Lexer.INSTANCE;
    private final static Parser parser = Parser.INSTANCE;

    /**
     * Differentiates the passed expression string with respect to variable
     * and returns its derivative as a string.  If the expression or variable
     * is null, behavior is undefined.  If the expression is not parsable,
     * throws an exception.
     * @param expression The expression.
     * @param variable The variable to differentiate by.
     * @return The expression's derivative.
     */
    public String evaluate(String expression, String variable) {
        lexer.setInput(expression);

        parser.setLexer(lexer);
        ExpressionElement input = parser.getParseTree();

        DifferentiationVisitor derivativeVisitor =
                new DifferentiationVisitor(variable);
        input.accept(derivativeVisitor);

        ExpressionElement derivative = derivativeVisitor.getDerivative();
        return derivative.interpret();
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
                    Evaluator diff = new Differentiator();
                    result = diff.evaluate(expression, "x");
                    System.out.println(result);
                } catch (RuntimeException re) {
                    System.err.println("Error: " + re.getMessage());
                }
            }
        } while (!expression.equals(""));
    }
}
