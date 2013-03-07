package differentiator;

import java.util.ArrayList;
import java.util.List;

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
        List<String> terms = new ArrayList<String>();
        PolynomialTerm x = new PolynomialTerm(expression.getValue(), terms);
        return new Polynomial(x);
    }

    @Override
    public Polynomial visit(IdentifierExpression expression) {
        List<String> terms = new ArrayList<String>(1);
        terms.add(expression.getValue());
        PolynomialTerm x = new PolynomialTerm(1, terms);
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
