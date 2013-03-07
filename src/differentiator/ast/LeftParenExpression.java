package differentiator.ast;

import differentiator.Type;

public class LeftParenExpression extends ExpressionElement {
    public LeftParenExpression() {
        super(Type.LPAR);
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        return;
    }

    @Override
    public String toString() {
        return "Left Parenthesis";
    }
}
