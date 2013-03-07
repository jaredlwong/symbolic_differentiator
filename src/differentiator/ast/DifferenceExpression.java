package differentiator.ast;

import differentiator.type.Type;

public class DifferenceExpression extends ExpressionElement {
    public DifferenceExpression() {
        super(Type.MINUS);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    @Override
    public String toString() {
        return "Difference";
    }

    @Override
    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(this.getLeft().interpret());
        result.append("-");
        result.append(this.getRight().interpret());
        result.append(")");
        return result.toString();
    }
}
