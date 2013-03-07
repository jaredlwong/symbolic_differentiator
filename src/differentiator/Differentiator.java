package differentiator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import differentiator.ast.ExpressionElement;


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
        Lexer lexer = Lexer.INSTANCE;
        Parser parser = Parser.INSTANCE;

        lexer.setInput(expression);
        parser.setLexer(lexer);

        ExpressionElement parseTree = parser.getParseTree();
        ExpressionElement differentialParseTree =
                Differentiator.differentiate(parseTree,
                        Token.getInstance(variable));

        differentialParseTree =
                ExpressionElement.simplify(differentialParseTree);

        return differentialParseTree.printEvaluationString();
    }

    public static ExpressionElement differentiate(
            ExpressionElement expression, Token variable) {
        ExpressionElement result;
        if (expression.isLeaf()) {
            if (expression.getToken().equals(variable)) {
                result = new ExpressionElement(oneToken);
            } else {
                result = new ExpressionElement(zeroToken);
            }
        } else if (expression.getToken().equals(Token.getInstance("+"))) {
            result = ExpressionElement.add(
                    differentiate(expression.getLeft(), variable),
                    differentiate(expression.getRight(), variable));
        } else if (expression.getToken().equals(Token.getInstance("*"))) {
            result = ExpressionElement.add(
                        ExpressionElement.multiply(
                            expression.getRight(),
                            differentiate(expression.getLeft(), variable)),
                        ExpressionElement.multiply(
                            expression.getLeft(),
                            differentiate(expression.getRight(), variable))
                     );
        } else if (expression.getToken().equals(Token.getInstance("-"))) {
            result = ExpressionElement.subtract(
                    differentiate(expression.getLeft(), variable),
                    differentiate(expression.getRight(), variable));
        }
        else {
            throw new IllegalArgumentException("Unsupported operation");
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
