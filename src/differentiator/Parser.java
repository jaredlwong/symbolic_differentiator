package differentiator;

import java.util.ArrayList;
import java.util.List;

import differentiator.ast.DifferentiationVisitor;
import differentiator.ast.ExpressionElement;
import differentiator.ast.ExpressionElementFactory;
import differentiator.ast.PrintTreeVisitor;

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

    public ExpressionElement getParseTree() {
        // Convert tokens to ase representation
        Token tokens[] = lexer.getTokens();
        ExpressionElement elements[] =
                new ExpressionElement[tokens.length];
        for (int i = 0; i < tokens.length; ++i) {
            elements[i] = ExpressionElementFactory.getInstance(tokens[i]);
        }

        ParserStack stack = ParserStack.INSTANCE;
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
                            stack.peekLastTerminal().compareTo(lastTerminalSeen) >= 0) {
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
        return null;
    }

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
                    "Malformed input");
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
                "Malformed input");
    }

    public static void main(String args[]) {
        Lexer lex = Lexer.INSTANCE;

        String input = "(x^x)";
        lex.setInput(input);

        INSTANCE.setLexer(lex);
        ExpressionElement root = INSTANCE.getParseTree();
        DifferentiationVisitor diff = new DifferentiationVisitor("x");
        root.accept(diff);
        ExpressionElement dres = diff.getDerivative();
        System.out.println(root.interpret());
        System.out.println(dres.interpret());
        //root.accept(new PrintTreeVisitor());
    }
}
