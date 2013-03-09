package differentiator.type;

/**
 * All the types of tokens that can be made.
 */
public enum Type {
    /* The precedence ordering is partially based on ideas from
     * "The Dragon Book"
     */
    // operators
    PLUS("+",  2, 1),
    MINUS("-", 2, 1),
    STAR("*",  4, 3),
    SLASH("/", 4, 3),
    CARET("^", 4, 5),
    LPAR("(", 0, 5),
    RPAR(")", 6, 0),

    // terminal to represent start and end parens
    TERMINAL("$", 0, 0),

    // non-operators
    NUMBER("N",       6, 5),
    IDENTIFIER("X", 6, 5);

    /**
     * rep is the String representation of the Type.
     * Stored as String in anticipation of multi-character type
     * representations.
     */
    private final String rep;

    /** The left precedence of the operator. */
    private final Integer leftPrecedence;

    /** The right precedence of the operator. */
    private final Integer rightPrecedence;

    /**
     * Instantiates a new type.
     * @param rep the rep
     * @param leftPrecedence the left precedence
     * @param rightPrecedence the right precedence
     */
    private Type(String rep, int leftPrecedence, int rightPrecedence) {
        this.rep = rep;
        this.leftPrecedence = leftPrecedence;
        this.rightPrecedence = rightPrecedence;
    }

    /**
     * Compares the left precedence of this object's type to the right
     * precedence of another object's type.
     * @param t the t
     * @return the int
     */
    public int comparePrecedence(Type t) {
        return leftPrecedence.compareTo(t.rightPrecedence);
    }

    /**
     * Gets the left precedence of this Type.
     * @return the left precedence
     */
    public int getLeftPrecedence() {
        return leftPrecedence;
    }

    /**
     * Gets the right precedence of this Type.
     * @return the right precedence
     */
    public int getRightPrecedence() {
        return rightPrecedence;
    }

    /**
     * Checks if this Type is a variable.
     * @return true, if is variable
     */
    public boolean isVariable() {
        return this == IDENTIFIER || this == NUMBER;
    }

    /**
     * Checks if this Type is an operator.
     * @return true, if is operator
     */
    public boolean isOperator() {
        return this == PLUS ||
                this == MINUS ||
                this == STAR ||
                this == SLASH ||
                this == CARET;
    }

    @Override
    public String toString() {
        return rep;
    }
}
