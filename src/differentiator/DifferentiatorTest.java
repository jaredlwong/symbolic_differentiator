package differentiator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

import org.junit.Test;

public class DifferentiatorTest {
    private static final Differentiator differ = new Differentiator();
    private static final ScriptEngineManager mgr =
            new ScriptEngineManager();
    private static final ScriptEngine engine =
            mgr.getEngineByName("JavaScript");

    /** ((x*x)+(3+x)) -> (2*x+1) */
    @Test
    public void basicTest() {
        String input = "((x*x)+(3+x))";

        String myOutput = differ.evaluate(input, "x");
        String expected = "(2*x+1)";

        for (int i = -100; i < 100; ++i) {
            String iStr =  "(" + Integer.valueOf(i).toString() + ")";
            String expectedVal = expected.replace("x",iStr);
            String myOutputVal = myOutput.replace("x", iStr);
            try {
                assertEquals(engine.eval(expectedVal),
                        engine.eval(myOutputVal));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void involvedTest() {
        String input = "(((x*x)+(3+(x*x)))*(y*x))";

        String myOutput = differ.evaluate(input, "x");
        String expected = "(3*(2*x*x*y+y))";

        for (int i = 0; i < 250; ++i) {
            String iStr =  "(" + Integer.valueOf(i).toString() + ")";
            String expectedVal = expected.replace("x",iStr).replace("y", iStr);
            String myOutputVal = myOutput.replace("x", iStr).replace("y", iStr);
            try {
                assertEquals(engine.eval(expectedVal),
                        engine.eval(myOutputVal));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void allSymbolsTest() {
        String input = "(x*x*x*x*x*y/(x-20*y*x)-10*x*x*x+y)";

        String myOutput = differ.evaluate(input, "x");
        String expected = "(-(2*x*x*(-15+2*(150+x)*y))/(-1+20*y))";

        for (int i = 1; i < 250; ++i) {
            String iStr =  "(" + Integer.valueOf(i).toString() + ")";
            String expectedVal = expected.replace("x",iStr).replace("y", iStr);
            String myOutputVal = myOutput.replace("x", iStr).replace("y", iStr);
            try {
                BigDecimal expectedDec =
                        new BigDecimal((Double) engine.eval(expectedVal));
                BigDecimal myOutputDec =
                        new BigDecimal((Double)  engine.eval(myOutputVal));
                assertTrue(
                        expectedDec.subtract(myOutputDec)
                        .abs()
                        .compareTo(BigDecimal.valueOf(0.00001)) < 0);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
