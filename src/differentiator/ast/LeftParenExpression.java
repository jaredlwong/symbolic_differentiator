package differentiator.ast;

import differentiator.type.Type;

/**
 * The Class LeftParenExpression defines a Left parenthesis element of a
 * parseable expression. This object is only used for initial parsing. It
 * should not be an element in an Abstract Syntax Tree presented by the parser.
 */
public class LeftParenExpression extends ExpressionElement {
    /**
     * Instantiates a new Left terminal expression.
     */
    public LeftParenExpression() {
        super(Type.LPAR);
    }

    /**
     * A Left parenthesis has no interpretation in a mathematical
     * expression.
     */
    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        throw new UnsupportedOperationException();
    }

    /**
     * A Left parenthesis has no interpretation in a mathematical
     * expression.
     */
    public String interpret() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "Left Parenthesis";
    }
}
