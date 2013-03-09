package differentiator.ast;

import differentiator.type.Type;

/**
 * The Class TerminalExpression defines a terminal element at the start and end
 * of a parseable expression. This object is only used for initial parsing. It
 * should not be an element in an Abstract Syntax Tree presented by the parser.
 */
public class TerminalExpression extends ExpressionElement {

    /**
     * Instantiates a new terminal expression.
     */
    public TerminalExpression() {
        super(Type.TERMINAL);
    }

    /**
     * A terminal expression has no interpretation in a mathematical
     * expression.
     */
    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        throw new UnsupportedOperationException();
    }

    /**
     * A terminal expression has no interpretation in a mathematical
     * expression.
     */
    public String interpret() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "Terminal";
    }
}
