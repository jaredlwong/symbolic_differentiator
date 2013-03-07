package differentiator.ast;

import differentiator.type.Type;

public class RightParenExpression extends ExpressionElement {
    public RightParenExpression() {
        super(Type.RPAR);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return null;
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
