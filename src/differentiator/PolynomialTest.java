package differentiator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PolynomialTest {
    @Test
    public void basicTest() {
        List<String> av = new ArrayList<String>(1);
        av.add("a");
        List<String> bv = new ArrayList<String>(1);
        bv.add("b");
        PolynomialTerm a = new PolynomialTerm(10, av);
        PolynomialTerm b = new PolynomialTerm(2, bv);

        // a = 10*a
        // b = 2*b
        // (10*a + 2*b)
        List<PolynomialTerm> abSum = new ArrayList<PolynomialTerm>(4);
        abSum.add(a);
        abSum.add(b);
        Polynomial abPoly = new Polynomial(abSum);
        Polynomial square = Polynomial.multiply(abPoly, abPoly);

        // (10*a + 2*b) * (10*a + 2*b)
        // (100*a*a + 20*a*b + 20*a*b + 4*b*b)
        List<String> abv = new ArrayList<String>(1);
        abv.add("a");
        abv.add("b");
        PolynomialTerm poly1 = new PolynomialTerm(100, av);
        PolynomialTerm poly23 = new PolynomialTerm(20, abv);
        PolynomialTerm poly4 = new PolynomialTerm(4, bv);
        List<PolynomialTerm> expectedProductTerms = new ArrayList<PolynomialTerm>(4);
        expectedProductTerms.add(poly1);
        expectedProductTerms.add(poly23);
        expectedProductTerms.add(poly23);
        expectedProductTerms.add(poly4);

        for (PolynomialTerm x : square.getTerms()) {
            System.out.println(x);
        }

        System.out.println(square);
        Polynomial simple = Polynomial.simplify(square);
        System.out.println(simple);
    }
}
