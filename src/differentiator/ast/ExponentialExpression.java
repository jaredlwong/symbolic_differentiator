package differentiator.ast;

import differentiator.Type;

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
}
