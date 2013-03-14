package differentiator.poly;

import differentiator.ast.DifferenceExpression;
import differentiator.ast.ExponentialExpression;
import differentiator.ast.ExpressionEvaluationVisitor;
import differentiator.ast.IdentifierExpression;
import differentiator.ast.NumberExpression;
import differentiator.ast.ProductExpression;
import differentiator.ast.QuotientExpression;
import differentiator.ast.SumExpression;

/**
 * The GeneratePolynomialVisitor class defines a visitor on ElementExpressions.
 * It creates a Polynomial from an expression composed of numbers, variables,
 * +, and *.
 */
public class GeneratePolynomialVisitor implements
        ExpressionEvaluationVisitor<Polynomial> {

    /**
     * This method defines the conversion between a number expression and its
     * polynomial representation.
     * @return A Polynomial representing this number expression
     */
    public Polynomial visit(NumberExpression expression) {
        PolynomialTerm x = new PolynomialTerm(expression.getValue());
        return new Polynomial(x);
    }

    /**
     * This method defines the conversion between an identifier expression and
     * its polynomial representation.
     * @return A Polynomial representing this identifier expression
     */
    public Polynomial visit(IdentifierExpression expression) {
        PolynomialTerm x = new PolynomialTerm(expression.getValue());
        return new Polynomial(x);
    }

    /**
     * This method defines the sum of two expression in terms of their
     * polynomial representations.
     * @return A polynomial representing the sum of the polynomial
     * representations of its left and right sides.
     */
    public Polynomial visit(SumExpression expression) {
        return Polynomial.sum(
                    expression.getLeft().accept(this),
                    expression.getRight().accept(this));
    }

    /**
     * This method defines the product of two expression in terms of their
     * polynomial representations.
     * @return A polynomial representing the product of the polynomial
     * representations of its left and right sides.
     */
    public Polynomial visit(ProductExpression expression) {
        return Polynomial.multiply(
                    expression.getLeft().accept(this),
                    expression.getRight().accept(this));
    }

    public Polynomial visit(DifferenceExpression expression) {
        throw new UnsupportedOperationException();
    }

    public Polynomial visit(QuotientExpression expression) {
        throw new UnsupportedOperationException();
    }

    public Polynomial visit(ExponentialExpression expression) {
        throw new UnsupportedOperationException();
    }

}
