package differentiator.ast;


public class PrintTreeVisitor implements ExpressionEvaluationVisitor {
    private StringBuilder indent = new StringBuilder("");

    @Override
    public void visit(NumberExpression expression) {
        System.out.println(indent.toString() + expression);
    }

    @Override
    public void visit(IdentifierExpression expression) {
        System.out.println(indent.toString() + expression);
    }

    @Override
    public void visit(SumExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    @Override
    public void visit(ProductExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    @Override
    public void visit(DifferenceExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    @Override
    public void visit(QuotientExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    @Override
    public void visit(ExponentialExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

}
