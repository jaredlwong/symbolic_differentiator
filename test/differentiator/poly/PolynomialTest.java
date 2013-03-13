package differentiator.poly;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PolynomialTest {
    @Test
    public void basicTest() {
        PolynomialTerm a = new PolynomialTerm(10, "a");
        PolynomialTerm b = new PolynomialTerm(2, "b");

        // a = 10*a
        // b = 2*b
        // (10*a + 2*b)
        List<PolynomialTerm> abSum = new ArrayList<PolynomialTerm>(2);
        abSum.add(a);
        abSum.add(b);
        Polynomial abPoly = new Polynomial(abSum);
        Polynomial square = Polynomial.multiply(abPoly, abPoly);
        // 100*a^2+20*a*b+20*a*b+4*b^2

        PolynomialTerm r1 = new PolynomialTerm(100,"a","a");
        PolynomialTerm r2 = new PolynomialTerm(20,"a","b");
        PolynomialTerm r3 = new PolynomialTerm(4,"b","b");
        Polynomial expected = new Polynomial(r1,r2,r3,r2);

        assertEquals(square, expected);
    }
}
