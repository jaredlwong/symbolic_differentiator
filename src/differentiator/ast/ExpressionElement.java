package differentiator.ast;

import differentiator.type.Type;

/**
 * All ExpressionElements are instantiated before parsing. The isTerminal
 * field determines whether or not the ExpressionElement has been parsed.
 */
public abstract class ExpressionElement implements
        Comparable<ExpressionElement> {
    private ExpressionElement left;
    private ExpressionElement right;
    private boolean isTerminal;
    private final Type type;

    /**
     * A constructor for a generic ExpressionElement. It sets the type of the
     * ExpressionElement. It sets the left and right children to null and
     * makes the ExpressionElement a terminal.
     * 
     * Note: All ExpressionElements should be instantiated before parsing.
     * @param t The Type of the ExpressionElement
     */
    public ExpressionElement(Type t) {
        left = null;
        right = null;
        isTerminal = true;
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
    public final void setIsTerminal(boolean _isTerminal) {
        isTerminal = _isTerminal;
    }

    /**
     * @return Whether the element is a terminal. The ExpressionElement is only
     * terminal if it hasn't been parsed. Note that an all ExpressionElements
     * are initialized before parsing.
     */
    public final boolean isTerminal() {
        return isTerminal;
    }

    /**
     * @return The left child of this ExpressionElement.
     */
    public final ExpressionElement getLeft() {
        return left;
    }

    /**
     * @return The right child of this ExpressionElement.
     */
    public final ExpressionElement getRight() {
        return right;
    }

    /**
     * @return The type of the ExpressionElement.
     */
    public final Type getType() {
        return type;
    }

    /**
     * Compare the left-precedence of one ExpressionElement to the
     * right-precedence of another. This is used for operator precedence
     * parsing.
     */
    @Override
    public final int compareTo(ExpressionElement e) {
        return this.type.comparePrecedence(e.getType());
    }

    /**
     * Every ExpressionElement should be able to accept an
     * ExpressionEvaluationVisitor.
     * 
     * ExpressionElements may not implement an accept method if they should
     * never logically appear in an abstract syntax tree presented by the
     * parser.
     * @param eev The Expression Evaluation Visitor
     * @return A variable type dependent on the Visitor.
     */
    public abstract <R> R accept(ExpressionEvaluationVisitor<R> eev);

    /**
     * The interpret method should create a mathematical interpretation of
     * the Expression Element.
     * 
     * ExpressionElements may not implement an interpret method if they should
     * never logically appear in an abstract syntax tree presented by the
     * parser.
     * @return A mathematical representation of the Expression rooted at the
     * ExpressionElement.
     */
    public abstract String interpret();

    /**
     * Each extending class should implement a toString method that prints
     * out the name of the Expression type, or equivalent.
     */
    @Override
    public abstract String toString();
}
