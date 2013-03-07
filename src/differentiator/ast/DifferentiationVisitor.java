package differentiator.ast;

import java.util.Stack;

import differentiator.Token;

public class DifferentiationVisitor implements ExpressionEvaluationVisitor {
    private final String variable;
    private final Stack<ExpressionElement> buffer;

    public DifferentiationVisitor(String variable) {
        this.variable = variable;
        buffer = new Stack<ExpressionElement>();
    }

    public ExpressionElement getDerivative() {
        return buffer.peek();
    }

    @Override
    public void visit(NumberExpression expression) {
        buffer.push(new NumberExpression(Token.getInstance("0")));
    }

    @Override
    public void visit(IdentifierExpression expression) {
        if (expression.getValue().equals(variable)) {
            buffer.push(new NumberExpression(Token.getInstance("1")));
        } else {
            buffer.push(new NumberExpression(Token.getInstance("0")));
        }
    }

    @Override
    public void visit(SumExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        ExpressionElement sumDx = ExpressionElementFactory.sum(
                buffer.pop(), buffer.pop());
        buffer.push(sumDx);
    }

    @Override
    public void visit(ProductExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        ExpressionElement rDiv = buffer.pop();
        ExpressionElement lDiv = buffer.pop();

        ExpressionElement lPart =
                ExpressionElementFactory.product(rDiv, expression.getLeft());
        ExpressionElement rPart =
                ExpressionElementFactory.product(lDiv, expression.getRight());

        ExpressionElement productDx =
                ExpressionElementFactory.sum(lPart, rPart);

        buffer.push(productDx);
    }

    @Override
    public void visit(DifferenceExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);

        ExpressionElement rDiv = buffer.pop();
        ExpressionElement lDiv = buffer.pop();

        ExpressionElement differenceDx =
                ExpressionElementFactory.difference(lDiv, rDiv);

        buffer.push(differenceDx);
    }

    @Override
    public void visit(QuotientExpression expression) {
        expression.getLeft().accept(this);
        expression.getRight().accept(this);
        ExpressionElement rDiv = buffer.pop();
        ExpressionElement lDiv = buffer.pop();

        ExpressionElement minuend =
                ExpressionElementFactory.product(lDiv, expression.getRight());
        ExpressionElement subtrahend =
                ExpressionElementFactory.product(expression.getLeft(), rDiv);

        ExpressionElement differenceNumerator =
                ExpressionElementFactory.difference(minuend, subtrahend);

        ExpressionElement productDenominator =
                ExpressionElementFactory.product(
                        expression.getRight(),
                        expression.getRight());

        ExpressionElement divisionDx =
                ExpressionElementFactory.quotient(
                        differenceNumerator,
                        productDenominator);

        buffer.push(divisionDx);
    }

    @Override
    public void visit(ExponentialExpression expression) {
        ExpressionElement power =
                ExpressionElementFactory.difference(
                        expression.getRight(),
                        new NumberExpression(Token.getInstance("1")));

        ExpressionElement exponential = new ExponentialExpression();
        exponential.setLeftElement(expression.getLeft());
        exponential.setRightElement(power);
        exponential.setIsTerminal(false);

        ExpressionElement diffExp =
                ExpressionElementFactory.product(
                        expression.getRight(),
                        exponential);

        buffer.push(diffExp);
    }

}
