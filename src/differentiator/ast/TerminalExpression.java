package differentiator.ast;

import differentiator.type.Type;

public class TerminalExpression extends ExpressionElement {
    public TerminalExpression() {
        super(Type.TERMINAL);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return null;
    }

    @Override
    public String toString() {
        return "Terminal";
    }

    @Override
    public String interpret() {
        return "";
    }
}
