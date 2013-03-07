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
}
