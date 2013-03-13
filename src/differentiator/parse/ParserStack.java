package differentiator.parse;

import java.util.Stack;

import differentiator.ast.ExpressionElement;

/**
 * The ParserStack defines the custom stack for the parser. It keeps track
 * of all elements and a particular subset of those elements, those elements
 * which are terminals. If an element is a terminal then it defines an element
 * which hasn't yet been simplified. Thus, they are very important when trying
 * to track when to reduce an expression.
 */
public class ParserStack {

    /** The Constant stack. */
    private static final Stack<ExpressionElement> stack =
            new Stack<ExpressionElement>();

    /** The Constant terminalStack. */
    private static final Stack<ExpressionElement> terminalStack =
            new Stack<ExpressionElement>();

    /**
     * Reset.
     */
    public void reset() {
        stack.clear();
        terminalStack.clear();
    }

    /**
     * Push.
     *
     * @param e the e
     */
    public void push(ExpressionElement e) {
        if (e.isTerminal()) {
            terminalStack.push(e);
        }
        stack.push(e);
    }

    /**
     * Pop.
     *
     * @return the expression element
     */
    public ExpressionElement pop() {
        ExpressionElement next = stack.pop();
        if (next.isTerminal()) {
            terminalStack.pop();
        }
        return next;
    }

    /**
     * Peek last terminal.
     *
     * @return the expression element
     */
    public ExpressionElement peekLastTerminal() {
        return terminalStack.peek();
    }
    
    /**
     * Peek.
     *
     * @return the expression element
     */
    public ExpressionElement peek() {
        return stack.peek();
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stack.toString();
    }
}
