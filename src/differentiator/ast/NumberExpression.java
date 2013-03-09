package differentiator.ast;

import java.math.BigDecimal;

import differentiator.parse.Token;
import differentiator.type.Type;

/**
 * The Class NumberExpression represents a number.
 */
public class NumberExpression extends ExpressionElement {
    private final BigDecimal value;

    /**
     * Instantiates a new NumberExpression.
     * @param token A token representing a number.
     * @throws NumberFormatException if token is not valid number.
     */
    public NumberExpression(Token token) {
        super(Type.NUMBER);
        assert(token.getType() == Type.NUMBER);
        value = new BigDecimal(token.getValue());
    }

    /**
     * @return The numerical value that this NumberExpression represents.
     */
    public BigDecimal getValue() {
        return value;
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    public String interpret() {
        if (value.compareTo(BigDecimal.valueOf(0)) < 0) {
            return "(" + 
                    value.stripTrailingZeros().toPlainString()
                    + ")";
        }
        return value.toString();
    }

    public String toString() {
        return "Number: " + value;
    }
}
