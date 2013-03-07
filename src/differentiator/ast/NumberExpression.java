package differentiator.ast;

import java.math.BigDecimal;

import differentiator.Token;
import differentiator.Type;

public class NumberExpression extends ExpressionElement {
    private final BigDecimal value;

    /**
     * 
     * @param token
     * @throws NumberFormatException if token is not valid number.
     */
    public NumberExpression(Token token) {
        super(Type.NUMBER);
        assert(token.getType() == Type.NUMBER);
        value = new BigDecimal(token.getValue());
    }

    public BigDecimal getValue() {
        return value;
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        eev.visit(this);
    }

    @Override
    public String toString() {
        return "Number: " + value;
    }
}
