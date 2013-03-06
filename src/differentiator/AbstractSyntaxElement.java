package differentiator;

public class AbstractSyntaxElement implements
        Comparable<AbstractSyntaxElement> {
    private final Token token;
    private AbstractSyntaxElement left;
    private AbstractSyntaxElement right;
    private boolean isTerminal;

    /**
     * Creates an AbstractSyntaxElement from a single token. It sets both
     * left and right subtrees to null and sets the ASE to be a terminal.
     * @param _token
     */
    public AbstractSyntaxElement(Token _token) {
        token = _token;
        left = null;
        right = null;
        isTerminal = true;
    }

    private AbstractSyntaxElement() {
        token = null;
    }

    public static AbstractSyntaxElement getVoid() {
        return new AbstractSyntaxElement();
    }

    public void setLeftElement(AbstractSyntaxElement _left) {
        left = _left;
    }
    
    public void setRightElement(AbstractSyntaxElement _right) {
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
    public int compareTo(AbstractSyntaxElement e) {
        return token.getType().comparePrecedence(e.getType());
    }

    public AbstractSyntaxElement getLeft() {
        return left;
    }

    public AbstractSyntaxElement getRight() {
        return right;
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
}
