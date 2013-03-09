package differentiator.ast;

import differentiator.type.Type;

/**
 * The Class RightParenExpression defines a right parenthesis element of a
 * parseable expression. This object is only used for initial parsing. It
 * should not be an element in an Abstract Syntax Tree presented by the parser.
 */
public class RightParenExpression extends ExpressionElement {
    /**
     * Instantiates a new right terminal expression.
     */
    public RightParenExpression() {
        super(Type.RPAR);
    }

    /**
     * A right parenthesis has no interpretation in a mathematical
     * expression.
     */
    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        throw new UnsupportedOperationException();
    }

    /**
     * A right parenthesis has no interpretation in a mathematical
     * expression.
     */
    public String interpret() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "Right Parenthesis";
    }
}
