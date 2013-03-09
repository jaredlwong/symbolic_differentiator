package differentiator.poly;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class PolynomialTermTest {
    @Test
    public void basicTest() {
        PolynomialTerm a = new PolynomialTerm(100, "a", "a", "c");
        PolynomialTerm b = new PolynomialTerm(2, "a", "a", "c");
        PolynomialTerm ab = PolynomialTerm.multiply(a, b);
        String expectA = "100*c*a^2";
        String expectB = "2*c*a^2";
        String expectAB = "200*c^2*a^4";
        assertEquals(expectA, a.toString());
        assertEquals(expectB, b.toString());
        assertEquals(expectAB, ab.toString());
    }

    @Test
    public void oneTest() {
        PolynomialTerm one = new PolynomialTerm(1, "a");
        assertEquals("a", one.toString());
    }
}
