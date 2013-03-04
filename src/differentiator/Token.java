package differentiator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A token is a lexical item that the parser uses.
 */
public class Token {
    /**
     * All the types of tokens that can be made.
     */
    public static enum Type {
        // operators
        LPAR("("), RPAR(")"), PLUS("+"),
        MINUS("-"), STAR("*"), SLASH("/"),
        CARET("^"),

        // non-operators
        INTEGER("Z"),
        REAL("R"),
        IDENTIFIER("X");

        /**
         * rep is the String representation of the Type.
         * Stored as String in anticipation of multi-character type
         * representations.
         */
        private final String rep;

        private Type(String rep) {
            this.rep = rep;
        }

        @Override
        public String toString() {
            return rep;
        }
    }

    /** 
     * The map of operator Types to Tokens. Note that all operator Tokens are
     * singletons.
     */
    private static final Map<Type, Token> OPMAP =
            new EnumMap<Type, Token>(Type.class);

    private static final Set<Character> OPCHARSET =
            new HashSet<Character>();

    static {
        for (Type type : Type.values()) {
            OPMAP.put(type, new Token(type));
        }
        OPMAP.remove(Type.INTEGER);
        OPMAP.remove(Type.REAL);
        OPMAP.remove(Type.IDENTIFIER);
        
        for (Type type : OPMAP.keySet()) {
            OPCHARSET.add(new Character(type.rep.charAt(0)));
        }
    }

    /** The type of the Token */
    private final Type type;

    /** The value of the Token if not operator */
    private Object value;
    private Class<?> valueClass;

    /**
     * Private constructor for operators. Enforces singleton property.
     * @param type The Type of the Token
     */
    private Token(Type type) {
        this.type = type;
    }

    /**
     * Private constructor for non-operator Tokens. Accepts the String
     * value of the Token.
     * @param type The Type of the Token
     * @param value The String value of the Token
     */
    private Token(Type type, String value) {
        this(type);
        switch (type) {
            case INTEGER:
                this.value = new BigInteger(value);
                valueClass = BigInteger.class;
                break;
            case REAL:
                this.value = new BigDecimal(value);
                valueClass = BigDecimal.class;
                break;
            case IDENTIFIER:
                this.value = value;
                valueClass = String.class;
                break;
            default:
                break;
        }
    }

    /**
     * Get instance of Token class, if valid token.
     * @param token The token that needs to be represented
     * @return An instance of Token that represents token
     */
    public static Token getInstance(String token) {
        Type type = getType(token);
        if (OPMAP.containsKey(type)) {
            return OPMAP.get(type);
        }
        return new Token(type, token);
    }

    /**
     * Get Type of a token
     * @param token The token that is being parsed
     * @return The Type of the token, an InvalidTokenException is thrown if
     *          token not recognized.
     */
    private static Type getType(String token) {
        for (Type type : OPMAP.keySet()) {
            if (token.equals(type.rep)) return type;
        }
        if (token.matches("-?[0-9]+")) return Type.INTEGER;
        if (token.matches("-?[0-9]*\\.[0-9]+|-?[0-9]+\\.[0-9]*"))
            return Type.REAL;
        if (token.matches("[a-zA-Z]+")) return Type.IDENTIFIER;
        throw new InvalidTokenException("Invalid Token: " + token);
    }

    /** 
     * @return The Type of the Token
     */
    public Type getType() {
        return type;
    }

    public boolean isOperator() {
        return OPMAP.containsKey(type);
    }

    public static boolean isOperator(char c) {
        return OPCHARSET.contains(c);
    }

    /**
     * Can only call getValue on non-operator Tokens.
     * @return value of non-operator Token
     */
    public Object getValue() {
        return value;
    }

    public Class<?> getValueClass() {
        return valueClass;
    }

    @Override
    public String toString() {
        if (OPMAP.containsKey(type)) {
            return type.rep;
        }
        return this.value.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result
                + ((valueClass == null) ? 0 : valueClass.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Token)) {
            return false;
        }
        Token other = (Token) obj;
        if (type != other.type) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        if (valueClass == null) {
            if (other.valueClass != null) {
                return false;
            }
        } else if (!valueClass.equals(other.valueClass)) {
            return false;
        }
        return true;
    }
}