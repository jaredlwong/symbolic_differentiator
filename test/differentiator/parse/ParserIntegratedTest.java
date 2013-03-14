package differentiator.parse;

import static org.junit.Assert.assertEquals;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

import org.junit.Test;

import differentiator.ast.ExpressionElement;

/**
 * Integrated tests use the javascript engine provided with Java SE 6 to
 * ensure full pareseability on complex inputs for which hand testing takes
 * inordinate amounts of time.
 */
public class ParserIntegratedTest {

    @Test
    public void basicIntegratedTest() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String input = "((x*x)+(3+x))";

        Token[] tokens = Lexer.getTokens(input);
        ExpressionElement parseTree = Parser.getParseTree(tokens);
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
    public void complexIntegratedTest() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String input = "((x*x)+(3+x)*1.00011111*99*(x*x)*x*x*x)";

        Token[] tokens = Lexer.getTokens(input);
        ExpressionElement parseTree = Parser.getParseTree(tokens);
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
    public void extraCharacterIntegratedTest() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String input = "(x*x*x-x-x*x/x/x-100*x)";

        Token[] tokens = Lexer.getTokens(input);
        ExpressionElement parseTree = Parser.getParseTree(tokens);
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