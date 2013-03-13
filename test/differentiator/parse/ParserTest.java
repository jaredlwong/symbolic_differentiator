package differentiator.parse;

import static org.junit.Assert.assertEquals;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

import org.junit.Test;

import differentiator.ast.ExpressionElement;

public class ParserTest {
    private static final Parser parser = Parser.INSTANCE;
    private static final Lexer lexer = Lexer.INSTANCE;

    @Test
    public void basicTest() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String input = "((x*x)+(3+x))";

        lexer.setInput(input);
        parser.setTokens(lexer.getTokens());
        ExpressionElement parseTree = parser.getParseTree();
        String output = parseTree.interpret();

        for (int i = 0; i < 100; ++i) {
            String iStr =  Integer.valueOf(i).toString();
            String expected = input.replace("x",iStr);
            String myOutput = output.replace("x", iStr);
            try {
            assertEquals(engine.eval(expected), engine.eval(myOutput));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void complexTest() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String input = "((x*x)+(3+x)*1.00011111*99*(x*x)*x*x*x)";

        lexer.setInput(input);
        parser.setTokens(lexer.getTokens());
        ExpressionElement parseTree = parser.getParseTree();
        String output = parseTree.interpret();

        for (int i = -100; i < 100; ++i) {
            String iStr =  "(" + Integer.valueOf(i).toString() + ")";
            String expected = input.replace("x",iStr);
            String myOutput = output.replace("x", iStr);
            try {
                assertEquals(engine.eval(expected), engine.eval(myOutput));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void extraCharacterTest() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String input = "(x*x*x-x-x*x/x/x-100*x)";

        lexer.setInput(input);
        parser.setTokens(lexer.getTokens());
        ExpressionElement parseTree = parser.getParseTree();
        String output = parseTree.interpret();

        for (int i = -100; i < 100; ++i) {
            String iStr =  "(" + Integer.valueOf(i).toString() + ")";
            String expected = input.replace("x",iStr);
            String myOutput = output.replace("x", iStr);
            try {
                assertEquals(engine.eval(expected), engine.eval(myOutput));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}