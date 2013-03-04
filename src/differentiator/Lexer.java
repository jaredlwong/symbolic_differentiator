package differentiator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import differentiator.Token.Type;

/**
 * A lexer takes a string and splits it into tokens that are meaningful to a
 * parser.
 */
public enum Lexer implements Iterable<Token> {
    /** Make sure only one instance of Lexer is ever created. */
    INSTANCE;

    /** Save away the Tokenizer in a convenient class variable */
    private final static Tokenizer tokenizer = Tokenizer.INSTANCE;

    private List<Token> tokens;

    /** Generate the tokens given a new input string.
     * @param input The String to be tokenized.
     */
    public void setInput(String input) {
        tokenizer.setInput(input);
        tokens = getTokensFromTokenizer();
    }

    /** This an accessor method that returns a copy of the List tokens in an
     * array form (intended to avoid the underlying list to be exposed).
     * @return The array of tokens for the current input */
    public Token[] getTokens() {
        return tokens.toArray(new Token[tokens.size()]);
    }

    private List<Token> getTokensFromTokenizer() {
        List<Token> parsedTokens = new LinkedList<Token>();
        tokenizer.reset();
        Token lastToken = null;
        Token token = null;
        // Here we change our starting parenthesis to a special terminal Token
        // and our end parenthesis to a special terminal Token.
        while (true) {
            String strTok = tokenizer.next();
            token = Token.getInstance(strTok);
            if (lastToken == null) {
                if (token.getType() == Type.LPAR) {
                    token = Token.getInstance("$");
                } else {
                    throw new IllegalArgumentException(
                            "Input did not start with a left parenthesis.");
                }
                lastToken = token;
            }

            // If there are no more tokens ensure that there is a terminal
            // right parenthesis and change it into a TERMINAL token.
            if (!tokenizer.hasNext()) {
                if (token.getType() == Type.RPAR) {
                    token = Token.getInstance("$");
                    parsedTokens.add(token);
                } else {
                    throw new IllegalArgumentException(
                            "Input did not end with a right parenthesis.");
                }
                break;
            }
            parsedTokens.add(token);
        }
        return parsedTokens;
    }

    /** Provides a way conveniently iterate over our tokens.
     * @return An instance of the iterator over tokens.
     */
    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }
}
