package differentiator;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ExpressionElement implements
        Comparable<ExpressionElement> {
    private final Token token;
    private ExpressionElement left;
    private ExpressionElement right;
    private boolean isTerminal;

    /**
     * Creates an AbstractSyntaxElement from a single token. It sets both
     * left and right subtrees to null and sets the ASE to be a terminal.
     * @param _token
     */
    public ExpressionElement(Token _token) {
        token = _token;
        left = null;
        right = null;
        isTerminal = true;
    }

    private ExpressionElement() {
        token = null;
    }

    public static ExpressionElement getVoid() {
        return new ExpressionElement();
    }

    public void setLeftElement(ExpressionElement _left) {
        left = _left;
    }
    
    public void setRightElement(ExpressionElement _right) {
        right = _right;
    }

    public void setIsTerminal(boolean _isTerminal) {
        isTerminal = _isTerminal;
    }

    public Token getToken() {
        return token;
    }

    public Type getType() {
        return token.getType();
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    @Override
    public int compareTo(ExpressionElement e) {
        return token.getType().comparePrecedence(e.getType());
    }

    public ExpressionElement getLeft() {
        return left;
    }

    public ExpressionElement getRight() {
        return right;
    }

    public static ExpressionElement
            add(ExpressionElement one, ExpressionElement two) {
        ExpressionElement sum = new ExpressionElement(Token.getInstance("+"));
        sum.setLeftElement(one);
        sum.setRightElement(two);
        return sum;
    }

    public static ExpressionElement multiply(ExpressionElement one, ExpressionElement two) {
        ExpressionElement product =
                new ExpressionElement(Token.getInstance("*"));
        product.setLeftElement(one);
        product.setRightElement(two);
        return product;
    }

    public static ExpressionElement simplify(ExpressionElement tree) {
        ExpressionElement result;
        if (tree.token.getType() == Type.PLUS) {
            result = simplify(tree.left).add(simplify(tree.right));
        } else if (tree.token.getType() == Type.STAR) {
            result = simplify(tree.left).multiply(simplify(tree.right));
        } else {
            result = tree;
        }
        return result;
    }

    public ExpressionElement add(ExpressionElement other) {
        // If either node is 0, return the other
        if (this.token.getType() == Type.INTEGER) {
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

    public ExpressionElement multiply(ExpressionElement other) {
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

    @Override
    public String toString() {
        if (!isTerminal) {
            return "E";
        }
        return token.toString();
    }

    public String printTreePrefix() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        str.append(token.toString());
        if (!this.isLeaf()) {
            str.append(",");
            str.append(this.left.printTreePrefix());
            str.append(",");
            str.append(this.right.printTreePrefix());
        }
        str.append("]");
        return str.toString();
    }

    public String printTreeInfix() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        if (!this.isLeaf()) {
            str.append(this.left.printTreeInfix());
        }
        str.append(token.toString());
        if (!this.isLeaf()) {
            str.append(this.right.printTreeInfix());
        }
        str.append("]");
        return str.toString();
    }

    public String printEvaluationString() {
        StringBuilder str = new StringBuilder();
        if (!this.isLeaf()) {
            str.append("(");
            str.append(this.left.printEvaluationString());
        }
        str.append(token.toString());
        if (!this.isLeaf()) {
            str.append(this.right.printEvaluationString());
            str.append(")");
        }
        
        return str.toString();
    }
}
