package differentiator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/** Symbolic differentiator */
public class Differentiator implements Evaluator {
    private final static Token oneToken = Token.getInstance("1");
    private final static Token zeroToken = Token.getInstance("0");

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
        return "";
    }
    
    public static AbstractSyntaxElement differentiate(
            AbstractSyntaxElement expression, Token variable) {
        AbstractSyntaxElement result = recursiveDifferentiate(expression, variable);
        return result;
    }

    private static AbstractSyntaxElement recursiveDifferentiate(
            AbstractSyntaxElement expression, Token variable) {
        AbstractSyntaxElement result;
        if (expression.isLeaf()) {
            if (expression.getToken().equals(variable)) {
                result = new AbstractSyntaxElement(oneToken);
            } else {
                result = new AbstractSyntaxElement(zeroToken);
            }
        } else if (expression.getToken().equals(Token.getInstance("+"))) {
            result =
                    differentiate(expression.getLeft(), variable)
                .add(
                    differentiate(expression.getRight(), variable));
        } else if (expression.getToken().equals(Token.getInstance("*"))) {
            result =
                        expression.getRight()
                    .multiply(
                        differentiate(expression.getLeft(), variable))
                .add(
                        expression.getLeft()
                    .multiply(
                        differentiate(expression.getRight(), variable)));
        } else {
            throw new IllegalArgumentException("Unsupported thing");
        }
        return result;
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
