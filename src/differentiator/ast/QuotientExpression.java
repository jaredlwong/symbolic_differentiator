package differentiator.ast;

import differentiator.type.Type;

/**
 * The Class QuotientExpression represents the quotient of two other
 * expressions.
 */
public class QuotientExpression extends ExpressionElement {

    /**
     * Instantiates a new quotient expression.
     */
    public QuotientExpression() {
        super(Type.SLASH);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(this.getLeft().interpret());
        result.append("/");
        result.append(this.getRight().interpret());
        result.append(")");
        return result.toString();
    }

    public String toString() {
        return "Quotient";
    }
}
