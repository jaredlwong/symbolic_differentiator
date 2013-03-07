package differentiator.ast;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExpressionEvaluationVisitor.
 */
public interface ExpressionEvaluationVisitor {

    /**
     * Visit.
     *
     * @param expression the expression
     */
    void visit(NumberExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    void visit(IdentifierExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    void visit(SumExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    void visit(ProductExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    void visit(DifferenceExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    void visit(QuotientExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    void visit(ExponentialExpression expression);
}
