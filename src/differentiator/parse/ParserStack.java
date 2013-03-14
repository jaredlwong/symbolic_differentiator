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
     * Reset this stack by clearing all elements from this stack.
     */
    public void reset() {
        stack.clear();
        terminalStack.clear();
    }

    /**
     * Push and element onto the top of this stack.
     * @param e the e
     */
    public void push(ExpressionElement e) {
        if (e.isTerminal()) {
            terminalStack.push(e);
        }
        stack.push(e);
    }

    /**
     * Get the element at the top of this stack.
     * @return the expression element at the top fo the stack
     * @throws EmptyStackException if the stack is empty
     */
    public ExpressionElement pop() {
        ExpressionElement next = stack.pop();
        if (next.isTerminal()) {
            terminalStack.pop();
        }
        return next;
    }

    /**
     * Looks at the last terminal expression element in this stack without
     * removing it.
     * @return The last terminal expression element in this stack without
     * removing it.
     */
    public ExpressionElement peekLastTerminal() {
        return terminalStack.peek();
    }
    
    /**
     * Looks at the object at the top of this stack without removing it from
     * this stack.
     * @return the object at the top of this stack
     */
    public ExpressionElement peek() {
        return stack.peek();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
