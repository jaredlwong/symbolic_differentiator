package differentiator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tokenizer splits string on three things:
 * 1) every terminal character (, ), +, *, -, /, or ^.
 * 2) Whitespace
 * 3) Letter/number changes.
 * 
 * Note: No negative numbers are produced.
 */
public enum Tokenizer implements Iterator<String> {
    /** Make sure only one instance of Tokenizer is ever created. */
    INSTANCE;

    /** This enum is used to help the tokenizer tell when to continue parsing
     * the next character from the input. It does not determine what Token the
     * character should be, that is left to the Token class.
     */
    private static enum CharType {
        IDENTIFIER("[a-zA-Z]"),
        NUMBER("[0-9\\.]"),
        TERMINAL("[()+*\\-/^]"),
        WHITESPACE("\\s");

        private final String regex;

        private CharType(String regex) {
            this.regex = regex;
        }

        public static CharType getCharType(char c) throws IllegalArgumentException {
            String cstr = Character.toString(c);
            for (CharType type : CharType.values()) {
                if (cstr.matches(type.regex)) {
                    return type;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    /** Keep the current list of tokens generated from the input. */
    private String input;
    private int position;

    /** Generate the tokens given a new input string.
     * @param input The String to be tokenized.
     */
    public void setInput(String _input) {
        input = _input;
        position = 0;
        // Increase position to first token that isn't whitespace.
        while (position < input.length()) {
            if (CharType.getCharType(input.charAt(position))
                    == CharType.WHITESPACE) {
                ++position;
            } else {
                break;
            }
        }
    }

    /** Iterator method.
     * @return Returns true when the input is set and the current position is
     * less than the length of the input.
     */
    @Override
    public boolean hasNext() {
        return input != null && position < input.length();
    }

    /** Reset the position of the Iterator.
     * @throws IllegalStateException if the input is not set.
     */
    public void reset() {
        if (input == null) {
            throw new IllegalStateException("The input is not set.");
        }
        position = 0;
        // Increase position to first token that isn't whitespace.
        while (position < input.length()) {
            if (CharType.getCharType(input.charAt(position))
                    == CharType.WHITESPACE) {
                ++position;
            } else {
                break;
            }
        }
    }

    /** 
     * Split the input string into String tokens. These tokens haven't yet
     * been parsed into instances of Tokens. Split them on every terminal
     * character (, ), +, *, -, /, or ^. Split them on whitespace. And split
     * them on letter/number splits. No negative numbers are produced.
     * 
     * @param input The String to split.
     * @return A String List of logical tokens.
     */
    @Override
    public String next() {
        if (position >= input.length()) {
            throw new NoSuchElementException();
        }
        String tok = null;

        int startIndex = position;
        int endIndex = seekNext(input, startIndex);
        position = endIndex;
        tok = input.substring(startIndex, endIndex);

        // Increase token to next non-whitespace
        while (position < input.length()) {
            if (CharType.getCharType(input.charAt(position))
                    == CharType.WHITESPACE) {
                ++position;
            } else {
                break;
            }
        }

        return tok;
    }

    /** 
     * Find the end of the token starting at index from the input. This method
     * is used to find the end of a token. For example, given "hello 100" and
     * an index of 6, it will return 9. This represents the token 100.
     * @param input The string to search in.
     * @param index The index to start at.
     * @return The index of the last character in the token starting at index
     * plus one.
     */
    private static int seekNext(String input, int index) {
        // For multi-character tokens take note of the last token type
        // (CharTypes).
        CharType lastType = null;
        while (index < input.length()) {
            char currentChar = input.charAt(index);
            CharType currentType;
            try {
                currentType = CharType.getCharType(currentChar);
            } catch(Exception e) {
                throw new IllegalArgumentException(
                        "Unrecognized character in input at position " + index
                        + ": " + "\"" + currentChar + "\"");
            }
            // If the first thing we see is definitely any of these terminals
            // return immediately.
            if (lastType == null && currentType == CharType.TERMINAL) {
                return index+1;
            }

            // If not whitespace, do state machine
            if (lastType == null) {
                lastType = currentType;
                ++index;
                continue;
            } else if (lastType == currentType) {
                ++index;
            } else {
                // when lastType != curType, break
                break; 
            }
        }

        // if index = input.length() || lastType != curType
        return index;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
