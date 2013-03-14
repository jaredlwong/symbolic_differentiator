package differentiator.poly;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class PolynomialTest {
    @Test(expected = RuntimeException.class)
    public void nullTermsArrayConstructorTest() {
        new Polynomial((PolynomialTerm[])null);
    }

    @Test(expected = RuntimeException.class)
    public void nullTermsListConstructorTest() {
        new Polynomial((List<PolynomialTerm>)null);
    }

    @Test
    public void sumTest() {
        PolynomialTerm a = new PolynomialTerm(10, "a");
        PolynomialTerm b = new PolynomialTerm(2, "b");
        Polynomial aPoly = new Polynomial(a);
        Polynomial bPoly = new Polynomial(b);
        Polynomial sum = Polynomial.sum(aPoly, bPoly);
        Polynomial expected = new Polynomial(a,b);
        assertEquals(sum, expected);
    }

    @Test
    public void multiplyTest() {
        PolynomialTerm a = new PolynomialTerm(10, "a");
        PolynomialTerm b = new PolynomialTerm(2, "b");

        // a = 10*a
        // b = 2*b
        // (10*a + 2*b)
        Polynomial abPoly = new Polynomial(a, b);
        Polynomial square = Polynomial.multiply(abPoly, abPoly);
        // 100*a^2+20*a*b+20*a*b+4*b^2

        PolynomialTerm r1 = new PolynomialTerm(100,"a","a");
        PolynomialTerm r2 = new PolynomialTerm(20,"a","b");
        PolynomialTerm r3 = new PolynomialTerm(4,"b","b");
        Polynomial expected = new Polynomial(r1,r2,r3,r2);

        assertEquals(square, expected);
    }

    @Test
    public void simplifyTest() {

        PolynomialTerm r1 = new PolynomialTerm(100,"a","a");
        PolynomialTerm r2 = new PolynomialTerm(20,"a","b");
        PolynomialTerm r3 = new PolynomialTerm(4,"b","b");
        Polynomial unsimplified = new Polynomial(r1,r1,r2,r3,r2,r2);
        Polynomial simplified = Polynomial.simplify(unsimplified);

        PolynomialTerm e1 = new PolynomialTerm(200,"a","a");
        PolynomialTerm e2 = new PolynomialTerm(60,"a","b");
        PolynomialTerm e3 = new PolynomialTerm(4,"b","b");
        Polynomial expected = new Polynomial(e1,e2,e3);

        assertEquals(simplified, expected);
    }
}
