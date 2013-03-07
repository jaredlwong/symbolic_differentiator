package differentiator.ast;

public interface ExpressionEvaluationVisitor {
    void visit(NumberExpression expression);
    void visit(IdentifierExpression expression);

    void visit(SumExpression expression);
    void visit(ProductExpression expression);

    void visit(DifferenceExpression expression);
    void visit(QuotientExpression expression);
    void visit(ExponentialExpression expression);
}
