package differentiator.poly;

import differentiator.ast.DifferenceExpression;
import differentiator.ast.ExponentialExpression;
import differentiator.ast.ExpressionEvaluationVisitor;
import differentiator.ast.IdentifierExpression;
import differentiator.ast.NumberExpression;
import differentiator.ast.ProductExpression;
import differentiator.ast.QuotientExpression;
import differentiator.ast.SumExpression;

public class GeneratePolynomialVisitor implements ExpressionEvaluationVisitor<Polynomial> {

    @Override
    public Polynomial visit(NumberExpression expression) {
        PolynomialTerm x = new PolynomialTerm(expression.getValue());
        return new Polynomial(x);
    }

    @Override
    public Polynomial visit(IdentifierExpression expression) {
        PolynomialTerm x = new PolynomialTerm(expression.getValue());
        return new Polynomial(x);
    }

    @Override
    public Polynomial visit(SumExpression expression) {
        return Polynomial.sum(
                    expression.getLeft().accept(this),
                    expression.getRight().accept(this));
    }

    @Override
    public Polynomial visit(ProductExpression expression) {
        return Polynomial.multiply(
                    expression.getLeft().accept(this),
                    expression.getRight().accept(this));
    }

    @Override
    public Polynomial visit(DifferenceExpression expression) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Polynomial visit(QuotientExpression expression) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Polynomial visit(ExponentialExpression expression) {
        // TODO Auto-generated method stub
        return null;
    }

}
