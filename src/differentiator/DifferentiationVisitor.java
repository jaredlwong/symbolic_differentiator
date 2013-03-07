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
 */
public class DifferentiationVisitor implements ExpressionEvaluationVisitor {

    /** The variable to differentiate on. */
    private final String variable;

    /** The buffer to keep our partial and full derivatives. */
    private final Stack<ExpressionElement> buffer;

    /**
     * Instantiates a new differentiation visitor.
     *
     * @param variable the variable
     */
    public DifferentiationVisitor(String variable) {
        this.variable = variable;
        buffer = new Stack<ExpressionElement>();
    }

    /**
     * Gets the derivative.
     *
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

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.NumberExpression)
     */
    @Override
    public void visit(NumberExpression expression) {
        buffer.push(new NumberExpression(Token.getInstance("0")));
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.IdentifierExpression)
     */
    @Override
    public void visit(IdentifierExpression expression) {
        if (expression.getValue().equals(variable)) {
            buffer.push(new NumberExpression(Token.getInstance("1")));
        } else {
            buffer.push(new NumberExpression(Token.getInstance("0")));
        }
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.SumExpression)
     */
    @Override
    public void visit(SumExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        ExpressionElement sumDx = ExpressionElementFactory.sum(
                buffer.pop(), buffer.pop());
        buffer.push(sumDx);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.ProductExpression)
     */
    @Override
    public void visit(ProductExpression expression) {
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
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.DifferenceExpression)
     */
    @Override
    public void visit(DifferenceExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);

        ExpressionElement rDiv = buffer.pop();
        ExpressionElement lDiv = buffer.pop();

        ExpressionElement differenceDx =
                ExpressionElementFactory.difference(lDiv, rDiv);

        buffer.push(differenceDx);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.QuotientExpression)
     */
    @Override
    public void visit(QuotientExpression expression) {
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
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.ExponentialExpression)
     */
    @Override
    public void visit(ExponentialExpression expression) {
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
    }

}
