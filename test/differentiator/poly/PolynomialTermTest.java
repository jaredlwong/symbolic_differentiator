package differentiator.poly;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;


public class PolynomialTermTest {

    @Test
    public void allNumberCoeffsSupportedAndEqualsForJustCoeffsTest() {
        PolynomialTerm a = new PolynomialTerm(new AtomicInteger(0));
        PolynomialTerm b = new PolynomialTerm(new AtomicLong(0));
        PolynomialTerm c = new PolynomialTerm(new BigDecimal(0));
        PolynomialTerm d = new PolynomialTerm(BigInteger.valueOf(0));
        PolynomialTerm e = new PolynomialTerm(Byte.valueOf((byte)0));
        PolynomialTerm f = new PolynomialTerm(new Double(0));
        PolynomialTerm g = new PolynomialTerm(new Float(0));
        PolynomialTerm h = new PolynomialTerm(new Integer(0));
        PolynomialTerm i = new PolynomialTerm(new Long(0));
        PolynomialTerm j = new PolynomialTerm(Short.valueOf((short)0));
        PolynomialTerm terms[] = {a,b,c,d,e,f,g,h,i,j};
        for (int x = 0; x < terms.length; ++x) {
            for (int y = 0; y < terms.length; ++y) {
                assertEquals(terms[x],terms[y]);
            }
        }
    }
    
    @Test
    public void singleVarAndMultipleVarsSupportedTest() {
        PolynomialTerm a = new PolynomialTerm("a");
        PolynomialTerm b = new PolynomialTerm("a");
        assertEquals(a,b);

        a = new PolynomialTerm("a","b");
        b = new PolynomialTerm("a","b");
        assertEquals(a,b);

        a = new PolynomialTerm("a","b","b","b","c","c");
        b = new PolynomialTerm("a","b","b","b","c","c");
        assertEquals(a,b);

        a = new PolynomialTerm(1,"a","b","b","b","c","c");
        b = new PolynomialTerm(1,"a","b","b","b","c","c");
        assertEquals(a,b);
    }

    @Test
    public void allVarsSupportedAndEqualsForJustVarsTest() {
        PolynomialTerm a = new PolynomialTerm("a","a","b","c","foobar","","");
        PolynomialTerm b = new PolynomialTerm("a","a","b","c","foobar","","");
        assertEquals(a,b);
    }

    @Test
    public void mixCoeffVarSupportedAndEquals() {
        PolynomialTerm a = new PolynomialTerm(new AtomicInteger(0),
                "a","a","b","c","foobar","","");
        PolynomialTerm b = new PolynomialTerm(new AtomicLong(0),
                "a","a","b","c","foobar","","");
        PolynomialTerm c = new PolynomialTerm(new BigDecimal(0),
                "a","a","b","c","foobar","","");
        PolynomialTerm d = new PolynomialTerm(BigInteger.valueOf(0),
                "a","a","b","c","foobar","","");
        PolynomialTerm e = new PolynomialTerm(Byte.valueOf((byte)0),
                "a","a","b","c","foobar","","");
        PolynomialTerm f = new PolynomialTerm(new Double(0),
                "a","a","b","c","foobar","","");
        PolynomialTerm g = new PolynomialTerm(new Float(0),
                "a","a","b","c","foobar","","");
        PolynomialTerm h = new PolynomialTerm(new Integer(0),
                "a","a","b","c","foobar","","");
        PolynomialTerm i = new PolynomialTerm(new Long(0),
                "a","a","b","c","foobar","","");
        PolynomialTerm j = new PolynomialTerm(Short.valueOf((short)0),
                "a","a","b","c","foobar","","");
        PolynomialTerm terms[] = {a,b,c,d,e,f,g,h,i,j};
        for (int x = 0; x < terms.length; ++x) {
            for (int y = 0; y < terms.length; ++y) {
                assertEquals(terms[x],terms[y]);
            }
        }
    }

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
