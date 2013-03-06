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
     * The map of operator Types to Tokens. Note that all operator Tokens are
     * singletons.
     */
    private static final Map<Type, Token> OPMAP =
            new EnumMap<Type, Token>(Type.class);

    /** Intended to hold the characters of the operators, this excludes our
     * special starting and ending parens.
     */
    private static final Set<Character> OPCHARSET =
            new HashSet<Character>();

    static {
        OPMAP.put(Type.PLUS, new Token(Type.PLUS));
        OPMAP.put(Type.MINUS, new Token(Type.MINUS));
        OPMAP.put(Type.STAR, new Token(Type.STAR));
        OPMAP.put(Type.SLASH, new Token(Type.SLASH));
        OPMAP.put(Type.CARET, new Token(Type.CARET));
        OPMAP.put(Type.LPAR, new Token(Type.LPAR));
        OPMAP.put(Type.RPAR, new Token(Type.RPAR));
        OPMAP.put(Type.TERMINAL, new Token(Type.TERMINAL));

        for (Type type : OPMAP.keySet()) {
            OPCHARSET.add(new Character(type.toString().charAt(0)));
        }
    }

    /** The type of the Token */
    private final Type type;

    /** The value of the Token if not operator */
    private final Object value;
    private final Class<?> valueClass;

    /**
     * Private constructor for operators. Enforces singleton property.
     * @param type The Type of the Token
     */
    private Token(Type type) {
        this.type = type;
        value = null;
        valueClass = null;
    }

    /**
     * Private constructor for non-operator Tokens. Accepts the String
     * value of the Token.
     * @param type The Type of the Token
     * @param value The String value of the Token
     */
    private Token(Type type, String value) {
        this.type = type;
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
                this.value = null;
                valueClass = null;
                break;
        }
    }

    /** Get instance of Token class, if valid token. This will not return a
     * starting terminal paren or closing terminal paren.
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

    /** Get Type of a token. This will not return a starting or closing
     * terminal paren. This is for private use only.
     * @param token The token that is being parsed
     * @return The Type of the token, an InvalidTokenException is thrown if
     *          token not recognized.
     */
    private static Type getType(String token) {
        for (Type type : OPMAP.keySet()) {
            if (token.equals(type.toString())) return type;
        }
        if (token.matches("\\$")) return Type.TERMINAL;
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
            return type.toString();
        } else if (this.type == Type.TERMINAL) {
            return Type.TERMINAL.toString();
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
