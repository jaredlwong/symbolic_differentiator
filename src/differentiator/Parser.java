package differentiator;

import java.util.ArrayList;
import java.util.List;

/**
 * The parser gets a bunch of tokens from the lexer and determines what
 * expression was written by the user.
 */
public enum Parser {
    INSTANCE;

    private Lexer lexer;

    public void setLexer(Lexer _lexer) {
        lexer = _lexer;
    }

    private AbstractSyntaxElement getParseTree() {
        // Convert tokens to ase representation
        Token tokens[] = lexer.getTokens();
        AbstractSyntaxElement elements[] =
                new AbstractSyntaxElement[tokens.length];
        for (int i = 0; i < tokens.length; ++i) {
            elements[i] = new AbstractSyntaxElement(tokens[i]);
        }

        ParserStack stack = ParserStack.INSTANCE;
        int elementIndex = 0;
        stack.push(elements[elementIndex++]);
        AbstractSyntaxElement nextElement = elements[elementIndex];
        while (true) {
            System.out.println("stack: " + stack);
            // If our stack = [$,E*] or [$] and the next terminal in our
            // elements to process is $, then break. Our tokens must have
            // start and end terminals.
            if (stack.peekLastTerminal().getType() == Type.TERMINAL &&
                    nextElement.getType() == Type.TERMINAL) {
                break;
            } else {
                AbstractSyntaxElement lastTerminalOnStack =
                        stack.peekLastTerminal();
                // Comparison of the top-most terminal in the stack to the
                // next element (which must be a terminal) in our element
                // array.
                if (lastTerminalOnStack.compareTo(nextElement) <= 0) {
                    //System.out.println(nextElement.getToken());
                    stack.push(nextElement);
                    ++elementIndex;
                    nextElement = elements[elementIndex];
                } else {
                    List<AbstractSyntaxElement> newExpression =
                        new ArrayList<AbstractSyntaxElement>(3);
                    AbstractSyntaxElement lastTerminalSeen = nextElement;
                    while (!stack.peek().isTerminal() ||
                            stack.peekLastTerminal().compareTo(lastTerminalSeen) >= 0) {
                        AbstractSyntaxElement bb = stack.pop();
                        if (bb.isTerminal()) {
                            lastTerminalSeen = bb;
                        }
                        newExpression.add(bb);
                    }
                    AbstractSyntaxElement newElement =
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
        return null;
    }

    private static AbstractSyntaxElement
            processExpression(List<AbstractSyntaxElement> expression) {
        System.out.println("reducing: " + expression);
        if (expression.size() == 1) {
            AbstractSyntaxElement onlyElem = expression.get(0);
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
                    "Malformed input");
        }
        assert (expression.size() == 3);

        // items are entered in backwards
        AbstractSyntaxElement left = expression.get(2);
        AbstractSyntaxElement right = expression.get(0);
        AbstractSyntaxElement center = expression.get(1);

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
                "Malformed input");
    }

    public static void main(String args[]) {
        Lexer lex = Lexer.INSTANCE;

        String input = "(1 - 10 * foobar ^ 10)";
        lex.setInput(input);

        INSTANCE.setLexer(lex);
        AbstractSyntaxElement root = INSTANCE.getParseTree();
        System.out.println(root.printTreePrefix());
    }
}
