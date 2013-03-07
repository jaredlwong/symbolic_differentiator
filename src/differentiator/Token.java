package differentiator;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * A token is a lexical item that the parser uses.
 */
public class Token {

    /** 
     * The map of operator Types to Tokens. Note that all operator Tokens are
     * singletons.
     */
    private static final Map<Type, Token> SINGLETON_MAP =
            new EnumMap<Type, Token>(Type.class);

    static {
        SINGLETON_MAP.put(Type.PLUS, new Token(Type.PLUS));
        SINGLETON_MAP.put(Type.MINUS, new Token(Type.MINUS));
        SINGLETON_MAP.put(Type.STAR, new Token(Type.STAR));
        SINGLETON_MAP.put(Type.SLASH, new Token(Type.SLASH));
        SINGLETON_MAP.put(Type.CARET, new Token(Type.CARET));
        SINGLETON_MAP.put(Type.LPAR, new Token(Type.LPAR));
        SINGLETON_MAP.put(Type.RPAR, new Token(Type.RPAR));
        SINGLETON_MAP.put(Type.TERMINAL, new Token(Type.TERMINAL));
    }

    private static final Map<String, Type> REGEX_PATTERNS =
            new HashMap<String, Type>();

    static {
        REGEX_PATTERNS.put("\\+", Type.PLUS);
        REGEX_PATTERNS.put("-", Type.MINUS);
        REGEX_PATTERNS.put("\\*", Type.STAR);
        REGEX_PATTERNS.put("/", Type.SLASH);
        REGEX_PATTERNS.put("\\^", Type.CARET);
        REGEX_PATTERNS.put("\\(", Type.LPAR);
        REGEX_PATTERNS.put("\\)", Type.RPAR);
        REGEX_PATTERNS.put("\\$", Type.TERMINAL);
        REGEX_PATTERNS.put("-?[0-9]*\\.[0-9]+|-?[0-9]+(\\.[0-9]*)?", Type.NUMBER);
        REGEX_PATTERNS.put("[a-zA-Z]+", Type.IDENTIFIER);
    }

    /** The type of the Token */
    private final Type type;

    /** The value of the Token if not operator */
    private final String value;

    /**
     * Private constructor for operators. Enforces singleton property.
     * @param type The Type of the Token
     */
    private Token(Type type) {
        this.type = type;
        value = null;
    }

    /**
     * Private constructor for non-operator Tokens. Accepts the String
     * value of the Token.
     * @param type The Type of the Token
     * @param value The String value of the Token
     */
    private Token(Type type, String value) {
        this.type = type;
        if (type == Type.NUMBER || type == Type.IDENTIFIER) {
            this.value = value;
        } else {
            this.value = null;
        }
    }

    /** Get instance of Token class, if valid token. This will not return a
     * starting terminal paren or closing terminal paren.
     * @param token The token that needs to be represented
     * @return An instance of Token that represents token
     */
    public static Token getInstance(String token) {
        Type type = getType(token);
        if (SINGLETON_MAP.containsKey(type)) {
            return SINGLETON_MAP.get(type);
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
        for (String pattern : REGEX_PATTERNS.keySet()) {
            if (token.matches(pattern)) {
                return REGEX_PATTERNS.get(pattern);
            }
        }
        throw new InvalidTokenException("Invalid Token: " + token);
    }

    /** 
     * @return The Type of the Token
     */
    public Type getType() {
        return type;
    }

    /**
     * Can only call getValue on non-operator Tokens.
     * @return value of non-operator Token
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "Token [type=" + type;
        }
        return "Token [type=" + type + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Token other = (Token) obj;
        if (type != other.type)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
