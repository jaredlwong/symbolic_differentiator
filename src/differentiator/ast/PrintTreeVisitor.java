package differentiator.ast;

public class PrintTreeVisitor implements ExpressionEvaluationVisitor {

    @Override
    public void visit(NumberExpression expression) {
        System.out.println(expression);
    }

    @Override
    public void visit(IdentifierExpression expression) {
        System.out.println(expression);
    }

    @Override
    public void visit(SumExpression expression) {
        System.out.println(expression);
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
    }

    @Override
    public void visit(ProductExpression expression) {
        System.out.println(expression);
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
    }

    @Override
    public void visit(DifferenceExpression expression) {
        System.out.println(expression);
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
    }

    @Override
    public void visit(QuotientExpression expression) {
        System.out.println(expression);
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
    }

    @Override
    public void visit(ExponentialExpression expression) {
        System.out.println(expression);
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
    }

}
