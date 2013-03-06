package differentiator;

import java.util.Stack;

public enum ParserStack {
    INSTANCE;

    private static final Stack<AbstractSyntaxElement> stack =
            new Stack<AbstractSyntaxElement>();
    
    private static final Stack<AbstractSyntaxElement> terminalStack =
            new Stack<AbstractSyntaxElement>();

    public void reset() {
        stack.clear();
        terminalStack.clear();
    }

    public void push(AbstractSyntaxElement e) {
        if (e.isTerminal()) {
            terminalStack.push(e);
        }
        stack.push(e);
    }

    public AbstractSyntaxElement pop() {
        AbstractSyntaxElement next = stack.pop();
        if (next.isTerminal()) {
            terminalStack.pop();
        }
        return next;
    }

    public AbstractSyntaxElement peekLastTerminal() {
        return terminalStack.peek();
    }
    
    public AbstractSyntaxElement peek() {
        return stack.peek();
    }
    
    @Override
    public String toString() {
/*        StringBuilder str = new StringBuilder();
        for (AbstractSyntaxElement ase : stack) {
            str.append(ase.toString());
            str.append(" ");
        }
        return str.toString();*/
        return stack.toString();
    }
}
