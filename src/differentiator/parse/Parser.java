package differentiator.parse;

import java.util.ArrayList;
import java.util.List;

import differentiator.ast.ExpressionElement;
import differentiator.ast.ExpressionElementFactory;
import differentiator.type.Type;

/**
 * The parser gets a bunch of tokens from the lexer and determines what
 * expression was written by the user. This parser is an implementation
 * of an operator precedence parser as described in "The Dragon Book"
 */
public class Parser {

    private Parser() {
        // This is a static class
    }

    /**
     * This method generates an ExpressionElement node that represents the
     * parse tree from the tokens in the lexer. It determines the order in
     * which to parse expressions based on operator-precedence parsing.
     * @param An array of tokens, must be well formed expression. Must start
     * with a terminal element, end with a terminal element, and every
     * other token must be in a proper mathematical expression.
     * @return a parse tree representing the expression enumrated by the
     * tokens.
     */
    public static ExpressionElement getParseTree(Token ... tokens) {
        // Convert tokens to ase representation
        ExpressionElement elements[] =
                new ExpressionElement[tokens.length];
        for (int i = 0; i < tokens.length; ++i) {
            elements[i] = ExpressionElementFactory.getInstance(tokens[i]);
        }

        ParserStack stack = new ParserStack();
        int elementIndex = 0;
        stack.push(elements[elementIndex++]);
        ExpressionElement nextElement = elements[elementIndex];
        while (true) {
            // If our stack = [$,E*] or [$] and the next terminal in our
            // elements to process is $, then break. Our tokens must have
            // start and end terminals.
            if (stack.peekLastTerminal().getType() == Type.TERMINAL &&
                    nextElement.getType() == Type.TERMINAL) {
                break;
            } else {
                ExpressionElement lastTerminalOnStack =
                        stack.peekLastTerminal();
                // Comparison of the top-most terminal in the stack to the
                // next element (which must be a terminal) in our element
                // array.
                if (lastTerminalOnStack.compareTo(nextElement) <= 0) {
                    stack.push(nextElement);
                    ++elementIndex;
                    nextElement = elements[elementIndex];
                } else {
                    List<ExpressionElement> newExpression =
                        new ArrayList<ExpressionElement>(3);
                    ExpressionElement lastTerminalSeen = nextElement;
                    while (!stack.peek().isTerminal() ||
                            stack.peekLastTerminal()
                                .compareTo(lastTerminalSeen) >= 0) {
                        ExpressionElement bb = stack.pop();
                        if (bb.isTerminal()) {
                            lastTerminalSeen = bb;
                        }
                        newExpression.add(bb);
                    }
                    ExpressionElement newElement =
                            processExpression(newExpression);
                    if (newElement != null) {
                        stack.push(newElement);
                    }
                }
            }
        }
        // analyze remaining on stack and return ast
        if (!stack.peek().isTerminal()) {
            return stack.pop();
        }
        throw new IllegalArgumentException("Malformed input, tokens did not"
                + " start or end with a terminal.");
    }

    /**
     * This method takes an expression in the form of a list of
     * ExpressionElements and returns another ExpressionElement that
     * represents that expression.
     * @param expression
     * @return
     */
    private static ExpressionElement
            processExpression(List<ExpressionElement> expression) {
        if (expression.size() == 1) {
            ExpressionElement onlyElem = expression.get(0);
            if (!(onlyElem.getType().isVariable())) {
                throw new IllegalArgumentException(
                        "Malformed input, operator may not precede or follow" +
                        " a parenthesis");
            }
            onlyElem.setIsTerminal(false);
            return onlyElem;
        }

        if (expression.size() != 3) {
            throw new IllegalArgumentException(
                    "Malformed input, there is an expression that is not of" +
                    " 1 or 3 tokens.");
        }
        assert (expression.size() == 3);

        // items are entered in backwards
        ExpressionElement left = expression.get(2);
        ExpressionElement right = expression.get(0);
        ExpressionElement center = expression.get(1);

        // If it is (E) -> E
        if (left.getType() == Type.LPAR &&
                right.getType() == Type.RPAR &&
                !center.isTerminal()) {
            return center;
        }
        // all other binary expressions
        // E op E -> E
        if (!left.isTerminal() &&
                !right.isTerminal() &&
                center.getType().isOperator() &&
                center.isTerminal()) {
            center.setLeftElement(left);
            center.setRightElement(right);
            center.setIsTerminal(false);
            return center;
        }
        throw new IllegalArgumentException(
                "Malformed input, could not process expression." +
                " Unknown expression.");
    }
}
