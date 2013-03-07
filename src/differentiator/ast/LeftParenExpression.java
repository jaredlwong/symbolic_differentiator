package differentiator.ast;

import differentiator.type.Type;

public class LeftParenExpression extends ExpressionElement {
    public LeftParenExpression() {
        super(Type.LPAR);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return null;
    }

    @Override
    public String toString() {
        return "Left Parenthesis";
    }

    @Override
    public String interpret() {
        return "";
    }
}
