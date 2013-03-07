package differentiator.ast;

import java.math.BigDecimal;
import java.math.BigInteger;

import differentiator.Token;
import differentiator.Type;

public class BasicSimplificationVisitor implements ExpressionEvaluationVisitor {
    public void visit(SumExpression se) {
        ExpressionElement one = se.getLeft();
        ExpressionElement two = se.getRight();
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
    public void visit(ProductExpression pe) {
        
    }
    public void visit(DifferenceExpression de) {
        
    }
    public void visit(NumberExpression ne) {
        
    }
}
