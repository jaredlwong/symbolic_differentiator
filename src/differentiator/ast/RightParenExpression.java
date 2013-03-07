package differentiator.ast;

import differentiator.Type;

public class RightParenExpression extends ExpressionElement {
    public RightParenExpression() {
        super(Type.RPAR);
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        return;
    }

    @Override
    public String toString() {
        return "Right Parenthesis";
    }

    @Override
    public String interpret() {
        return "";
    }
}
