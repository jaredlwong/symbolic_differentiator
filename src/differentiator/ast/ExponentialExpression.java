package differentiator.ast;

import differentiator.type.Type;

/**
 * The Class ProductExpression represents the exponent of one expression
 * raised to another.
 */
public class ExponentialExpression extends ExpressionElement {

    /**
     * Instantiates a new exponential expression.
     */
    public ExponentialExpression() {
        super(Type.CARET);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append(this.getLeft().interpret());
        result.append("^");
        result.append(this.getRight().interpret());
        return result.toString();
    }

    public String toString() {
        return "Exponential";
    }
}
