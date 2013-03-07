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
}
