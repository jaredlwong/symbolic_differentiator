package differentiator;

import differentiator.ast.ExpressionElement;
import differentiator.parse.Lexer;
import differentiator.parse.Parser;

/**
 * The Class HalfCompiler defines half of a compiler. It transforms an input
 * text to an expression in the form of an abstract syntax tree.
 */
public class HalfCompiler {

    /** The lexer instance. */
    private final static Lexer lexer = Lexer.INSTANCE;

    /** The parser instance. */
    private final static Parser parser = Parser.INSTANCE;

    /**
     * Evaluate the input string.
     * @param input A string to be parsed.
     * @return The ExpressionElement representing the input
     */
    public static ExpressionElement evaluate(String input) {
        lexer.setInput(input);
        parser.setLexer(lexer);
        return parser.getParseTree();
    }
}
