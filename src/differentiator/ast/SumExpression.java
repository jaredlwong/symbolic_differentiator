package differentiator.ast;

import differentiator.Type;

public class SumExpression extends ExpressionElement {

    public SumExpression() {
        super(Type.PLUS);
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        eev.visit(this);
    }

    @Override
    public String toString() {
        return "Sum";
    }

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
