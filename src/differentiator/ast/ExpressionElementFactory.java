package differentiator.ast;

import java.math.BigDecimal;
import java.math.BigInteger;

import differentiator.Token;
import differentiator.Type;

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

/*
    public static ExpressionElement simplify(ExpressionElement tree) {
        ExpressionElement result;
        if (tree.getType() == Type.PLUS) {
            result = simplifiedSum(simplify(tree.getLeft()), simplify(tree.getRight()));
        } else if (tree.getType() == Type.STAR) {
            result = simplify(tree.left).multiply(simplify(tree.right));
        } else {
            result = tree;
        }
        return result;
    }

    private static ExpressionElement simplifiedSum(ExpressionElement one, ExpressionElement two) {
        // If either node is 0, return the other
        if (one.getType() == Type.INTEGER) {
            if (((BigInteger) this.token.getValue())
                    .compareTo(BigInteger.valueOf(0)) == 0) {
                return other;
            }
        }
        if (other.token.getType() == Type.INTEGER) {
            if (((BigInteger) other.token.getValue())
                    .compareTo(BigInteger.valueOf(0)) == 0) {
                return this;
            }
        }
        if (this.token.getType() == Type.REAL) {
            if (((BigDecimal) this.token.getValue())
                    .compareTo(BigDecimal.valueOf(0)) == 0) {
                return other;
            }
        }
        if (other.token.getType() == Type.REAL) {
            if (((BigDecimal) other.token.getValue())
                    .compareTo(BigDecimal.valueOf(0)) == 0) {
                return this;
            }
        }
        // If both nodes are numbers add them
        if (this.token.getType() == Type.INTEGER &&
                other.token.getType() == Type.INTEGER) {
            BigInteger sum = 
                    ((BigInteger) this.token.getValue())
                    .add
                    (((BigInteger) other.token.getValue()));
            return new ExpressionElement(
                    Token.getInstance(sum.toString()));
        }
        if ((this.token.getType() == Type.INTEGER ||
                this.token.getType() == Type.REAL) &&
                (other.token.getType() == Type.INTEGER ||
                other.token.getType() == Type.REAL)) {
            String thisValue = this.token.getValue().toString();
            String otherValue = other.token.getValue().toString();
            BigDecimal thisDec = new BigDecimal(thisValue);
            BigDecimal otherDec = new BigDecimal(otherValue);
            BigDecimal sum = thisDec.add(otherDec);
            return new ExpressionElement(
                    Token.getInstance(sum.toString()));
        }
        // If both nodes are the same identifier add them

        ExpressionElement newPlus =
                new ExpressionElement(Token.getInstance("+"));
        newPlus.setLeftElement(this);
        newPlus.setRightElement(other);
        return newPlus;
    }

    private ExpressionElement multiply(ExpressionElement other) {
        // If either node is 0, return the zero
        if (this.token.getType() == Type.INTEGER) {
            if (((BigInteger) this.token.getValue())
                    .compareTo(BigInteger.valueOf(0)) == 0) {
                return new ExpressionElement(Token.getInstance("0"));
            }
        }
        if (other.token.getType() == Type.INTEGER) {
            if (((BigInteger) other.token.getValue())
                    .compareTo(BigInteger.valueOf(0)) == 0) {
                return new ExpressionElement(Token.getInstance("0"));
            }
        }
        if (this.token.getType() == Type.REAL) {
            if (((BigDecimal) this.token.getValue())
                    .compareTo(BigDecimal.valueOf(0)) == 0) {
                return new ExpressionElement(Token.getInstance("0"));
            }
        }
        if (other.token.getType() == Type.REAL) {
            if (((BigDecimal) other.token.getValue())
                    .compareTo(BigDecimal.valueOf(0)) == 0) {
                return new ExpressionElement(Token.getInstance("0"));
            }
        }
        // If either node is 1, return the other
        if (this.token.getType() == Type.INTEGER) {
            if (((BigInteger) this.token.getValue())
                    .compareTo(BigInteger.valueOf(1)) == 0) {
                return other;
            }
        }
        if (other.token.getType() == Type.INTEGER) {
            if (((BigInteger) other.token.getValue())
                    .compareTo(BigInteger.valueOf(1)) == 0) {
                return this;
            }
        }
        if (this.token.getType() == Type.REAL) {
            if (((BigDecimal) this.token.getValue())
                    .compareTo(BigDecimal.valueOf(1)) == 0) {
                return other;
            }
        }
        if (other.token.getType() == Type.REAL) {
            if (((BigDecimal) other.token.getValue())
                    .compareTo(BigDecimal.valueOf(1)) == 0) {
                return this;
            }
        }
        // If both nodes are numbers multiply them
        if (this.token.getType() == Type.INTEGER &&
                other.token.getType() == Type.INTEGER) {
            BigInteger sum = 
                    ((BigInteger) this.token.getValue())
                    .multiply
                    (((BigInteger) other.token.getValue()));
            return new ExpressionElement(
                    Token.getInstance(sum.toString()));
        }
        if ((this.token.getType() == Type.INTEGER ||
                this.token.getType() == Type.REAL) &&
                (other.token.getType() == Type.INTEGER ||
                other.token.getType() == Type.REAL)) {
            String thisValue = this.token.getValue().toString();
            String otherValue = other.token.getValue().toString();
            BigDecimal thisDec = new BigDecimal(thisValue);
            BigDecimal otherDec = new BigDecimal(otherValue);
            BigDecimal product = thisDec.multiply(otherDec);
            return new ExpressionElement(
                    Token.getInstance(product.toString()));
        }
        // If both nodes are the same identifier add them
        ExpressionElement newProduct =
                new ExpressionElement(Token.getInstance("*"));
        newProduct.setLeftElement(this);
        newProduct.setRightElement(other);
        return newProduct;
    }
*/
}
