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
    INTEGER("Z",    6, 5),
    REAL("R",       6, 5),
    IDENTIFIER("X", 6, 5),

    // Pseudo-type. Ie, not Token Type
    EXPRESSION("E", 6, 5);

    /**
     * rep is the String representation of the Type.
     * Stored as String in anticipation of multi-character type
     * representations.
     */
    private final String rep;

    private Type(String rep, int leftPrecedence, int rightPrecedence) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return rep;
    }
}
