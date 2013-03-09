package differentiator.ast;

/**
 * The Interface ExpressionEvaluationVisitor defines a visitor for elements
 * that extend the abstract class ExpressionElement.
 */
public interface ExpressionEvaluationVisitor<R> {

    /**
     * A visitor may choose to throw an UnsupportedOperationException if visit
     * cannot be defined on this type of expression.
     * @param A NumberExpression to evaluate.
     */
    R visit(NumberExpression expression);

    /**
     * A visitor may choose to throw an UnsupportedOperationException if visit
     * cannot be defined on this type of expression.
     * @param A NumberExpression to evaluate.
     */
    R visit(IdentifierExpression expression);

    /**
     * A visitor may choose to throw an UnsupportedOperationException if visit
     * cannot be defined on this type of expression.
     * @param A SumExpression to evaluate.
     */
    R visit(SumExpression expression);

    /**
     * A visitor may choose to throw an UnsupportedOperationException if visit
     * cannot be defined on this type of expression.
     * @param A ProductExpression to evaluate.
     */
    R visit(ProductExpression expression);

    /**
     * A visitor may choose to throw an UnsupportedOperationException if visit
     * cannot be defined on this type of expression.
     * @param A DifferenceExpression to evaluate.
     */
    R visit(DifferenceExpression expression);

    /**
     * A visitor may choose to throw an UnsupportedOperationException if visit
     * cannot be defined on this type of expression.
     * @param A QuotientExpression to evaluate.
     */
    R visit(QuotientExpression expression);

    /**
     * A visitor may choose to throw an UnsupportedOperationException if visit
     * cannot be defined on this type of expression.
     * @param An ExponentialExpression to evaluate.
     */
    R visit(ExponentialExpression expression);
}
