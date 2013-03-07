package differentiator.ast;

import java.math.BigDecimal;

import differentiator.parse.Token;
import differentiator.type.Type;

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

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    @Override
    public String toString() {
        return "Number: " + value;
    }

    @Override
    public String interpret() {
        if (value.compareTo(BigDecimal.valueOf(0)) < 0) {
            return "(" + value.toString() + ")";
        }
        return value.toString();
    }
}
