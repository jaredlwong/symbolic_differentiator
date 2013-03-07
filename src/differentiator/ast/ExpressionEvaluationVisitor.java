package differentiator.ast;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExpressionEvaluationVisitor.
 */
public interface ExpressionEvaluationVisitor<R> {

    /**
     * Visit.
     *
     * @param expression the expression
     */
    R visit(NumberExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    R visit(IdentifierExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    R visit(SumExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    R visit(ProductExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    R visit(DifferenceExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    R visit(QuotientExpression expression);

    /**
     * Visit.
     *
     * @param expression the expression
     */
    R visit(ExponentialExpression expression);
}
