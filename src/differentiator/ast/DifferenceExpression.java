package differentiator.ast;

import differentiator.Type;

public class DifferenceExpression extends ExpressionElement {
    public DifferenceExpression() {
        super(Type.MINUS);
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        eev.visit(this);
    }

    @Override
    public String toString() {
        return "Difference";
    }
}
