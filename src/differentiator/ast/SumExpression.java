package differentiator.ast;

import differentiator.type.Type;

/**
 * The Class SumExpression represents the sum of two other expressions.
 */
public class SumExpression extends ExpressionElement {

    /**
     * Instantiates a new sum expression.
     */
    public SumExpression() {
        super(Type.PLUS);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(this.getLeft().interpret());
        result.append("+");
        result.append(this.getRight().interpret());
        result.append(")");
        return result.toString();
    }

    @Override
    public String toString() {
        return "Sum";
    }
}
