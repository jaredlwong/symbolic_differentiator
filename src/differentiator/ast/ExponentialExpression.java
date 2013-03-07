package differentiator.ast;

import differentiator.type.Type;

public class ExponentialExpression extends ExpressionElement {
    public ExponentialExpression() {
        super(Type.CARET);
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        eev.visit(this);
    }

    @Override
    public String toString() {
        return "Exponential";
    }

    @Override
    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append(this.getLeft().interpret());
        result.append("^");
        result.append(this.getRight().interpret());
        return result.toString();
    }
}
