package differentiator.ast;

import differentiator.parse.Token;

public class ExpressionElementFactory {
    /**
     * Convert tokens to ExpressionElements. Will return an ExpressionElement
     * that is _terminal_.
     * @param token
     * @return
     */
    public static ExpressionElement getInstance(Token token) {
        switch(token.getType()) {
            case NUMBER:
                return new NumberExpression(token);
            case IDENTIFIER:
                return new IdentifierExpression(token);
            case PLUS:
                return new SumExpression();
            case STAR:
                return new ProductExpression();
            case MINUS:
                return new DifferenceExpression();
            case SLASH:
                return new QuotientExpression();
            case CARET:
                return new ExponentialExpression();
            case LPAR:
                return new LeftParenExpression();
            case RPAR:
                return new RightParenExpression();
            case TERMINAL:
                return new TerminalExpression();
            default:
                throw new IllegalArgumentException("Token is invalid: " + token);
        }
    }

    /**
     * Take the sum of two ExpressionElements by representing them as a
     * SumExpression with a left and right child. The resulting expression
     * will *not* be _terminal_.
     * @param one
     * @param two
     * @return
     */
    public static ExpressionElement
            sum(ExpressionElement one, ExpressionElement two) {
        ExpressionElement sum = new SumExpression();
        sum.setLeftElement(one);
        sum.setRightElement(two);
        sum.setIsTerminal(false);
        return sum;
    }

    /**
     * Take the difference of two ExpressionElements by representing them as a
     * DifferenceExpression with a left and right child. The resulting
     * expression will *not* be _terminal_.
     * @param one
     * @param two
     * @return
     */
    public static ExpressionElement
            difference(ExpressionElement one, ExpressionElement two) {
        ExpressionElement difference = new SumExpression();
        difference.setLeftElement(one);

        // set right element to second part * -1
        ExpressionElement product = product(
                new NumberExpression(Token.getInstance("-1")),two);
        difference.setRightElement(product);

        difference.setIsTerminal(false);

        return difference;
    }

    /**
     * Take the product of two ExpressionElements by representing them as a
     * ProductExpression with a left and right child. The resulting expression
     * will *not* be _terminal_.
     * @param one
     * @param two
     * @return
     */
    public static ExpressionElement
            product(ExpressionElement one, ExpressionElement two) {
        ExpressionElement product = new ProductExpression();
        product.setIsTerminal(false);
        product.setLeftElement(one);
        product.setRightElement(two);
        return product;
    }

    /**
     * Take the quotient of two ExpressionElements by representing them as a
     * QuotientExpression with a left and right child. The resulting expression
     * will *not* be _terminal_.
     * @param one
     * @param two
     * @return
     */
    public static ExpressionElement
            quotient(ExpressionElement one, ExpressionElement two) {
        ExpressionElement quotient = new QuotientExpression();
        quotient.setIsTerminal(false);
        quotient.setLeftElement(one);
        quotient.setRightElement(two);
        return quotient;
    }
}
