package differentiator.ast;

/**
 * The Class PrintTreeVisitor defines an example ExpressionEvaluationVisitor
 * that prints out an ExpressionElement tree with indentation.
 */
public class PrintTreeVisitor implements ExpressionEvaluationVisitor<Void> {

    /** The running indent of consecutive branches. */
    private StringBuilder indent = new StringBuilder("");

    /**
     * @param expression The operator expression to visit.
     */
    private void operatorVisit(ExpressionElement expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    public Void visit(NumberExpression expression) {
        System.out.println(indent.toString() + expression);
        return null;
    }

    public Void visit(IdentifierExpression expression) {
        System.out.println(indent.toString() + expression);
        return null;
    }

    public Void visit(SumExpression expression) {
        operatorVisit(expression);
        return null;
    }

    public Void visit(ProductExpression expression) {
        operatorVisit(expression);
        return null;
    }

    public Void visit(DifferenceExpression expression) {
        operatorVisit(expression);
        return null;
    }

    public Void visit(QuotientExpression expression) {
        operatorVisit(expression);
        return null;
    }

    public Void visit(ExponentialExpression expression) {
        operatorVisit(expression);
        return null;
    }
}
