package differentiator.ast;

import differentiator.Type;

public abstract class ExpressionElement implements
        Comparable<ExpressionElement> {
    private ExpressionElement left;
    private ExpressionElement right;
    private boolean isTerminal;
    private final Type type;

    public ExpressionElement(Type t) {
        left = null;
        right = null;
        isTerminal = false;
        type = t;
    }

    /**
     * Set left child of ExpressionElement node.
     * @param _left The left child of this node
     */
    final public void setLeftElement(ExpressionElement _left) {
        left = _left;
    }

    /**
     * Set right child of ExpressionElement node.
     * @param _right The right child of this node
     */
    final public void setRightElement(ExpressionElement _right) {
        right = _right;
    }

    /**
     * Set whether the node is a terminal token in abstract syntax tree.
     * Generally only terminal when first created to be parsed.
     * @param _isTerminal
     */
    final public void setIsTerminal(boolean _isTerminal) {
        isTerminal = _isTerminal;
    }

    final public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    final public ExpressionElement getLeft() {
        return left;
    }

    final public ExpressionElement getRight() {
        return right;
    }

    final public Type getType() {
        return type;
    }

    /*
    final public String printTreePrefix() {
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

    final public String printTreeInfix() {
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

    final public String printEvaluationString() {
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
*/
    @Override
    public int compareTo(ExpressionElement e) {
        return this.type.comparePrecedence(e.getType());
    }

    @Override
    public abstract String toString();

    public abstract void accept(ExpressionEvaluationVisitor eev);
}
