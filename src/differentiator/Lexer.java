package differentiator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A lexer takes a string and splits it into tokens that are meaningful to a
 * parser.
 */
public enum Lexer implements Iterable<Token> {
    INSTANCE;

    private List<Token> tokens;

    public void setInput(String input) {
        tokens = getTokens(input);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private static enum CharType {
        IDENTIFIER("[a-zA-Z]"), NUMBER("-?[0-9\\.]"),
        OP("[() +\\-*/^]");

        private final String regex;

        private CharType(String regex) {
            this.regex = regex;
        }

        public static CharType getCharType(char c) {
            String cstr = Character.toString(c);
            for (CharType type : CharType.values()) {
                if (cstr.matches(type.regex)) {
                    return type;
                }
            }
            throw new IllegalArgumentException(
                    "Illegal character in input: \"" + cstr + "\"");
        }
    }

    private static List<Token> getTokens(String input) {
        List<String> strTokens = splitInput(input);
        List<Token> tokens = new ArrayList<Token>(strTokens.size());
        for (String token : strTokens ) {
            tokens.add(Token.getInstance(token));
        }
        return tokens;
    }

    private static List<String> splitInput(String input) {
        List<String> tokens = new LinkedList<String>();
        int startIndex = 0;
        int endIndex = 0;
        while (startIndex < input.length() && endIndex < input.length()) {
            if (input.charAt(startIndex) == ' ') {
                ++startIndex;
                continue;
            }
            endIndex = seekNext(input, startIndex);
            String tok = input.substring(startIndex, endIndex);
            tokens.add(tok);
            startIndex = endIndex;
        }
        return tokens;
    }

    private static int seekNext(String input, int index) {
        CharType lastType = null;
        while (index < input.length()) {
            char cur = input.charAt(index);
            if (lastType == null && Token.isOperator(cur)) {
                return index+1;
            }

            // If not whitespace, do state machine
            CharType curType = CharType.getCharType(cur);
            if (lastType == null) {
                lastType = CharType.getCharType(cur);
                ++index;
                continue;
            } else if (lastType == curType) {
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
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

}
