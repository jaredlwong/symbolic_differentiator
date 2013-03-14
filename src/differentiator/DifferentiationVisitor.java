package differentiator;

import java.util.Stack;

import differentiator.ast.DifferenceExpression;
import differentiator.ast.ExponentialExpression;
import differentiator.ast.ExpressionElement;
import differentiator.ast.ExpressionElementFactory;
import differentiator.ast.ExpressionEvaluationVisitor;
import differentiator.ast.IdentifierExpression;
import differentiator.ast.NumberExpression;
import differentiator.ast.ProductExpression;
import differentiator.ast.QuotientExpression;
import differentiator.ast.SumExpression;
import differentiator.parse.Token;

/**
 * The Class DifferentiationVisitor defines a visitor for an abstract syntax
 * tree. It will take the derivative of the input on which it is called.
 * <br>
 * This uses a buffer in order to non-recursively handle long expressions.
 */
public class DifferentiationVisitor implements ExpressionEvaluationVisitor<Void> {

    /** The variable to differentiate on. */
    private final String variable;

    /** The buffer to keep our partial and full derivatives. */
    private final Stack<ExpressionElement> buffer;

    /**
     * Instantiates a new differentiation visitor.
     * @param variable the variable to differentiate on.
     */
    public DifferentiationVisitor(String variable) {
        this.variable = variable;
        buffer = new Stack<ExpressionElement>();
    }

    /**
     * Gets the derivative.
     * @return the derivative
     */
    public ExpressionElement getDerivative() {
        return buffer.peek();
    }

    /**
     * After each differentiation operation one more derivative will be on the
     * stack. The client may choose to clear this buffer using this method.
     */
    public void clearBuffer() {
        buffer.clear();
    }

    /**
     * This defines differentiation of a number. It is always 0.
     */
    public Void visit(NumberExpression expression) {
        buffer.push(new NumberExpression(Token.getInstance("0")));
        return null;
    }

    /**
     * This method defines differentiation of a variable. If it equals the
     * variable we are differentiating on it is 1, otherwise 0.
     */
    public Void visit(IdentifierExpression expression) {
        if (expression.getValue().equals(variable)) {
            buffer.push(new NumberExpression(Token.getInstance("1")));
        } else {
            buffer.push(new NumberExpression(Token.getInstance("0")));
        }
        return null;
    }

    /**
     * This method defines differentiation of a sum of expressions. It does the
     * transformation:
     * A + B -> dA/dx + DB/dx
     */
    public Void visit(SumExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        ExpressionElement sumDx = ExpressionElementFactory.sum(
                buffer.pop(), buffer.pop());
        buffer.push(sumDx);
        return null;
    }

    /**
     * This method defines differentiation of a product of expressions. It does
     * the transformation:
     * A * B -> dA/dx*B + dB/dx * A
     */
    public Void visit(ProductExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        ExpressionElement rDiv = buffer.pop();
        ExpressionElement lDiv = buffer.pop();

        ExpressionElement lPart =
                ExpressionElementFactory.product(rDiv, expression.getLeft());
        ExpressionElement rPart =
                ExpressionElementFactory.product(lDiv, expression.getRight());

        ExpressionElement productDx =
                ExpressionElementFactory.sum(lPart, rPart);

        buffer.push(productDx);
        return null;
    }

    /**
     * This method defines differentiation of a product of expressions. It does
     * the transformation:
     * A * B -> dA/dx*B + dB/dx * A 
     */
    public Void visit(DifferenceExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);

        ExpressionElement rDiv = buffer.pop();
        ExpressionElement lDiv = buffer.pop();

        ExpressionElement differenceDx =
                ExpressionElementFactory.difference(lDiv, rDiv);

        buffer.push(differenceDx);
        return null;
    }

    /**
     * This method defines differentiation of a quotient of expressions. It
     * implements the quotient rule of differentiation. It does the
     * transformation:
     * A / B -> (dA/dx*B - dB/dx * A)/(B * B)
     */
    public Void visit(QuotientExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        ExpressionElement rDiv = buffer.pop();
        ExpressionElement lDiv = buffer.pop();

        ExpressionElement minuend =
                ExpressionElementFactory.product(lDiv, expression.getRight());
        ExpressionElement subtrahend =
                ExpressionElementFactory.product(expression.getLeft(), rDiv);

        ExpressionElement differenceNumerator =
                ExpressionElementFactory.difference(minuend, subtrahend);

        ExpressionElement productDenominator =
                ExpressionElementFactory.product(
                        expression.getRight(),
                        expression.getRight());

        ExpressionElement divisionDx =
                ExpressionElementFactory.quotient(
                        differenceNumerator,
                        productDenominator);

        buffer.push(divisionDx);
        return null;
    }

    /**
     * This method defines differentiation of an exponential. It does
     * the transformation:
     * A ^ B -> B * A ^ (B-1)
     * 
     * NOTE: It does not differentiate properly unless A === variable that
     * this DifferentiationVisitor is taking the derivative of.
     */
    public Void visit(ExponentialExpression expression) {
        ExpressionElement power =
                ExpressionElementFactory.difference(
                        expression.getRight(),
                        new NumberExpression(Token.getInstance("1")));

        ExpressionElement exponential = new ExponentialExpression();
        exponential.setLeftElement(expression.getLeft());
        exponential.setRightElement(power);
        exponential.setIsTerminal(false);

        ExpressionElement diffExp =
                ExpressionElementFactory.product(
                        expression.getRight(),
                        exponential);

        buffer.push(diffExp);
        return null;
    }

}
