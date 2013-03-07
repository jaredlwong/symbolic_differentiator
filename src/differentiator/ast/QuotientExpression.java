package differentiator.ast;

import differentiator.Type;

public class QuotientExpression extends ExpressionElement {
    public QuotientExpression() {
        super(Type.SLASH);
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        eev.visit(this);
    }

    @Override
    public String toString() {
        return "Quotient";
    }

    @Override
    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(this.getLeft().interpret());
        result.append("/");
        result.append(this.getRight().interpret());
        result.append(")");
        return result.toString();
    }
}
