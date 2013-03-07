package differentiator.ast;

/**
 * The Class PrintTreeVisitor defines an example ExpressionEvaluationVisitor
 * that prints out an ExpressionElement tree with indentation.
 */
public class PrintTreeVisitor implements ExpressionEvaluationVisitor {
    
    /** The indent. */
    private StringBuilder indent = new StringBuilder("");

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.NumberExpression)
     */
    @Override
    public void visit(NumberExpression expression) {
        System.out.println(indent.toString() + expression);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.IdentifierExpression)
     */
    @Override
    public void visit(IdentifierExpression expression) {
        System.out.println(indent.toString() + expression);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.SumExpression)
     */
    @Override
    public void visit(SumExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.ProductExpression)
     */
    @Override
    public void visit(ProductExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.DifferenceExpression)
     */
    @Override
    public void visit(DifferenceExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.QuotientExpression)
     */
    @Override
    public void visit(QuotientExpression expression) {
        System.out.println(indent.toString() + expression);
        indent.append("  ");
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        indent.deleteCharAt(indent.length()-1);
        indent.deleteCharAt(indent.length()-1);
    }

    /* (non-Javadoc)
     * @see differentiator.ast.ExpressionEvaluationVisitor#visit(differentiator.ast.ExponentialExpression)
     */
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
