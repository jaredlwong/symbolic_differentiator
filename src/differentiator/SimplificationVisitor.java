package differentiator;

import differentiator.ast.DifferenceExpression;
import differentiator.ast.ExponentialExpression;
import differentiator.ast.ExpressionEvaluationVisitor;
import differentiator.ast.IdentifierExpression;
import differentiator.ast.NumberExpression;
import differentiator.ast.ProductExpression;
import differentiator.ast.QuotientExpression;
import differentiator.ast.SumExpression;

public class SimplificationVisitor implements ExpressionEvaluationVisitor<Polynomial> {

    @Override
    public Polynomial visit(NumberExpression expression) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Polynomial visit(IdentifierExpression expression) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Polynomial visit(SumExpression expression) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Polynomial visit(ProductExpression expression) {
        // TODO Auto-generated method stub
        return null;
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
