package differentiator.parse;

import java.util.Stack;

import differentiator.ast.ExpressionElement;

public enum ParserStack {
    INSTANCE;

    private static final Stack<ExpressionElement> stack =
            new Stack<ExpressionElement>();
    
    private static final Stack<ExpressionElement> terminalStack =
            new Stack<ExpressionElement>();

    public void reset() {
        stack.clear();
        terminalStack.clear();
    }

    public void push(ExpressionElement e) {
        if (e.isTerminal()) {
            terminalStack.push(e);
        }
        stack.push(e);
    }

    public ExpressionElement pop() {
        ExpressionElement next = stack.pop();
        if (next.isTerminal()) {
            terminalStack.pop();
        }
        return next;
    }

    public ExpressionElement peekLastTerminal() {
        return terminalStack.peek();
    }
    
    public ExpressionElement peek() {
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
