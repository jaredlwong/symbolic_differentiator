package differentiator.ast;

/**
 * The Class PrintTreeVisitor defines an example ExpressionEvaluationVisitor
 * that prints out an ExpressionElement tree with indentation.
 */
public class PrintTreeVisitor implements ExpressionEvaluationVisitor<Void> {

    /** The indent. */
    private StringBuilder indent = new StringBuilder("");

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.NumberExpression)
     */
    @Override
    public Void visit(NumberExpression expression) {
        System.out.println(indent.toString() + expression);
        return null;
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.IdentifierExpression)
     */
    @Override
    public Void visit(IdentifierExpression expression) {
        System.out.println(indent.toString() + expression);
        return null;
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.SumExpression)
     */
    @Override
    public Void visit(SumExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
        return null;
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.ProductExpression)
     */
    @Override
    public Void visit(ProductExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
        return null;
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.DifferenceExpression)
     */
    @Override
    public Void visit(DifferenceExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
        return null;
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.QuotientExpression)
     */
    @Override
    public Void visit(QuotientExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
        return null;
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.ExponentialExpression)
     */
    @Override
    public Void visit(ExponentialExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
        return null;
    }

}
