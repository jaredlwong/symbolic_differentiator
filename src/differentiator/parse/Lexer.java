package differentiator.parse;

import java.util.LinkedList;
import java.util.List;

import differentiator.type.Type;

/**
 * A lexer takes a string and splits it into tokens that are meaningful to a
 * parser. This isn't a static class because 
 */
public class Lexer {
    private Lexer() {
        // This is a static class
    }

    /** 
     * This an accessor method that returns a copy of the List tokens in an
     * array form (intended to avoid the underlying list to be exposed).
     * <br>
     * Note: this returns an array because the input should be fully processed
     * by this step of our parser/compiler.
     * @return The array of tokens for the current input
     * @throws NullPointerException if the specified input is null
     * */
    public static Token[] getTokens(String input) {
        if (input == null) {
            throw new NullPointerException("input was null");
        }
        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = getTokensFromTokenizer(tokenizer);
        return tokens.toArray(new Token[tokens.size()]);
    }

    /** This private helper method takes the tokenizer and transforms each
     * token into appropriate tokens. This includes transformations of TERMINAL
     * tokens and handling of Unary Minus.
     * 
     * Unary minus gets expanded if it ever comes after an operator of terminal
     * token. Even if it immediately precedes a number.
     * 
     * @return A list of Tokens representing the input
     */
    private static List<Token> getTokensFromTokenizer(Tokenizer tokenizer) {
        List<Token> parsedTokens = new LinkedList<Token>();
        tokenizer.reset();
        Token lastToken = null;
        Token token = null;
        // Do the transformations of Terminals and Unary Minus.
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
            } else {
                // If the last token was an operator or terminal (ie it wasn't
                // a variable, and the current token is a minus assume it's
                // a unary minus and do the transformation "-" -> "-1 *"
                if (token.getType() == Type.MINUS &&
                        (!lastToken.getType().isVariable() ||
                        lastToken.getType() == Type.TERMINAL)) {
                    Token nextToken = Token.getInstance(tokenizer.next());
                    // If the last token was not an operator, it must have been
                    // a right paren
                    if (lastToken.getType() == Type.RPAR) {
                        parsedTokens.add(Token.getInstance("+"));
                    }
                    if (nextToken.getType().isVariable()) {
                        parsedTokens.add(Token.getInstance("("));
                        parsedTokens.add(Token.getInstance("-1"));
                        parsedTokens.add(Token.getInstance("*"));
                        parsedTokens.add(nextToken);
                        token = Token.getInstance(")");
                    } else {
                        parsedTokens.add(Token.getInstance("-1"));
                        parsedTokens.add(Token.getInstance("*"));
                        token = nextToken;
                    }
                }
            }
            lastToken = token;

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
}
