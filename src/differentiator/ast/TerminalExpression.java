package differentiator.ast;

import differentiator.Type;

public class TerminalExpression extends ExpressionElement {
    public TerminalExpression() {
        super(Type.TERMINAL);
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        return;
    }

    @Override
    public String toString() {
        return "Terminal";
    }
}
