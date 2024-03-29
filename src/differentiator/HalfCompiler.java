package differentiator;

import differentiator.ast.ExpressionElement;
import differentiator.parse.Lexer;
import differentiator.parse.Parser;

/**
 * The Class HalfCompiler defines half of a compiler. It transforms an input
 * text to an expression in the form of an abstract syntax tree.
 */
public class HalfCompiler {

    /**
     * Evaluate the input string.
     * @param input A string to be parsed.
     * @return The ExpressionElement representing the input
     */
    public static ExpressionElement evaluate(String input) {
        return Parser.getParseTree(Lexer.getTokens(input));
    }
}
