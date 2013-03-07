package differentiator.ast;

import differentiator.parse.Token;
import differentiator.type.Type;

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

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    @Override
    public String toString() {
        return "Identifier: " + value;
    }

    public String interpret() {
        return value;
    }
}
