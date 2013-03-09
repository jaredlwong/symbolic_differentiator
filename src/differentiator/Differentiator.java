package differentiator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import differentiator.ast.ExpressionElement;

/**
 * Symbolic differentiator
 * 
 * Can differentiate any expression with numbers (integer or floating),
 * variables (defined as any continuous string of [a-zA-Z]), or the operators
 * +, *, -, /, ^ (see note below on exponentiation).
 * <br>
 * <b>NOTE: Exponentiation is not fully supported. Only exponentials of the
 * form x^(something) are supported. Exponentials without a base of x are not
 * evaluated properly.</b>
 */
public class Differentiator implements Evaluator {
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
        ExpressionElement astRepresentation =
                HalfCompiler.evaluate(expression);

        DifferentiationVisitor derivativeVisitor =
                new DifferentiationVisitor(variable);
        astRepresentation.accept(derivativeVisitor);

        ExpressionElement derivative = derivativeVisitor.getDerivative();
        return derivative.interpret();
    }

    /**
     * State to keep track of state in main of differentiator.
     */
    private static enum State {
        BEGIN, EVAL, SIMPLIFY;
    }

    /**
     * Tests whether a given expression can be simplified. Currently only
     * support for expressions with operators + and * is supported.
     * @param expression
     * @return Whether the given expression can be simplified
     */
    private static boolean canBeSimplified(ExpressionElement expression) {
        String representation = expression.interpret();
        if (representation.matches("[ ()0-9a-zA-Z*+.\\-]+")) {
            return true;
        }
        return false;
    }
    
    private static final String NOTICE =
        "You can differentiate any expression with numbers\n"
      + "(integer or floating), variables (defined as any\n"
      + "continuous string of [a-zA-Z]), or the operators\n"
      + "+, *, -, /, ^ (see note below on exponentiation).\n"
      + "\n"
      + "Normal operator precedence holds and the only\n"
      + "requirement is that each expression start and end\n"
      + "with a parenthesis; left and right, respectively.\n"
      + "\n"
      + "NOTE: Exponentiation is not fully supported. Only\n"
      + "exponentials of the form x^(something) are\n"
      + "supported. Exponentials without a base of x are not\n"
      + "evaluated properly.\n"
      + "\n"
      + "Type \"#simplify\" after you have evaluated an\n"
      + "expression in order to see a simplified form of\n"
      + "your input and the derivative of the input.\n"
      + "\n"
      + "NOTE: For now, simplification only works on\n"
      + "expressions with numbers, identifiers, +, and *.\n";

    /**
     * Repeatedly reads expressions from the console, and outputs the results of
     * evaluating them. Inputting an empty line will terminate the program.
     * @param args unused
     */
    public static void main(String[] args) throws IOException {
        System.out.println(NOTICE);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        String input = null;
        ExpressionElement expression = null;
        ExpressionElement derivative = null;

        State currentState = State.BEGIN;

        do {
            System.out.print("$ ");
            input = in.readLine();
            // terminate if input empty
            if (!input.equals("")) {
                if (input.equals("#simplify")) {
                    if (!canBeSimplified(expression)) {
                        System.err.println(
                                "Error: unsupported operators for "
                                + "simplification");
                        continue;
                    }
                    if (currentState == State.BEGIN) {
                        System.err.println("Error: no previous expressions");
                    } else if (currentState == State.SIMPLIFY) {
                        System.err.println("Error: cannot simplify again");
                    } else {
                        StringBuilder simplification = new StringBuilder();
                        simplification.append(expression.interpret());
                        simplification.append(" = ");
                        simplification.append(
                                SimplifiedPolynomial.getInstance(expression));
                        simplification.append("\n");
                        simplification.append(derivative.interpret());
                        simplification.append(" = ");
                        simplification.append(
                                SimplifiedPolynomial.getInstance(derivative));
                        System.out.println(simplification);
                        currentState = State.SIMPLIFY;
                    }
                    continue;
                }
                try {
                    expression = HalfCompiler.evaluate(input);
                    DifferentiationVisitor derivativeVisitor =
                            new DifferentiationVisitor("x");
                    expression.accept(derivativeVisitor);

                    derivative = derivativeVisitor.getDerivative();

                    System.out.println(derivative.interpret());
                    currentState = State.EVAL;
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                }
            }
        } while (!input.equals(""));
        System.out.println("goodbye");
    }
}
