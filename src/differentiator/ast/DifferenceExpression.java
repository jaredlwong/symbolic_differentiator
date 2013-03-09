package differentiator.ast;

import differentiator.type.Type;

/**
 * The Class DifferenceExpression represents the product of two other
 * expressions.
 */
public class DifferenceExpression extends ExpressionElement {

    /**
     * Instantiates a new difference expression.
     */
    public DifferenceExpression() {
        super(Type.MINUS);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(this.getLeft().interpret());
        result.append("-");
        result.append(this.getRight().interpret());
        result.append(")");
        return result.toString();
    }

    public String toString() {
        return "Difference";
    }
}
