package differentiator;

/**
 * All the types of tokens that can be made.
 */
public enum Type {
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

    private final Integer leftPrecedence;
    private final Integer rightPrecedence;

    private Type(String rep, int leftPrecedence, int rightPrecedence) {
        this.rep = rep;
        this.leftPrecedence = leftPrecedence;
        this.rightPrecedence = rightPrecedence;
    }

    /**
     * Compares the left precedence of this object's type to the right
     * precedence of another object's type.
     * @param t
     * @return
     */
    public int comparePrecedence(Type t) {
        return leftPrecedence.compareTo(t.rightPrecedence);
    }

    public int getLeftPrecedence() {
        return leftPrecedence;
    }

    public int getRightPrecedence() {
        return rightPrecedence;
    }

    public boolean isVariable() {
        return this == IDENTIFIER || this == NUMBER;
    }

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
