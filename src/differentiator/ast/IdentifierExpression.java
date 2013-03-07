package differentiator.ast;

import differentiator.Token;
import differentiator.Type;

public class IdentifierExpression extends ExpressionElement {
    private final String value;

    public IdentifierExpression(Token token) {
        super(Type.IDENTIFIER);
        assert(token.getType() == Type.IDENTIFIER);
        value = token.getValue();
    }

    public String getValue() {
        return value;
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        eev.visit(this);
    }

    @Override
    public String toString() {
        return "Identifier: " + value;
    }
}
