package differentiator.ast;

import differentiator.parse.Token;
import differentiator.type.Type;

/**
 * The Class IdentifierExpression represents an identifier.
 */
public class IdentifierExpression extends ExpressionElement {
    private final String value;

    /**
     * Instantiates a new IdentifierExpression.
     * @param token A token representing an identifier.
     */
    public IdentifierExpression(Token token) {
        super(Type.IDENTIFIER);
        value = token.getValue();
    }

    /**
     * @return The identifier that this IdentifierExpression represents.
     */
    public String getValue() {
        return value;
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    public String interpret() {
        return value;
    }

    public String toString() {
        return "Identifier: " + value;
    }
}
