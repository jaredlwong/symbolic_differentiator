package differentiator.ast;

import differentiator.type.Type;

/**
 * The Class SumExpression.
 */
public class SumExpression extends ExpressionElement {

    /**
     * Instantiates a new sum expression.
     */
    public SumExpression() {
        super(Type.PLUS);
    }

    @Override
    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionElement#toString()
     */
    @Override
    public String toString() {
        return "Sum";
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionElement#interpret()
     */
    @Override
    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(this.getLeft().interpret());
        result.append("+");
        result.append(this.getRight().interpret());
        result.append(")");
        return result.toString();
    }
}
