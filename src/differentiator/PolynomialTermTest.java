package differentiator;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class PolynomialTermTest {
    @Test
    public void basicTest() {
        List<String> av = new ArrayList<String>(1);
        av.add("a");
        List<String> bv = new ArrayList<String>(1);
        bv.add("b");
        PolynomialTerm a = new PolynomialTerm(12300, av);
        PolynomialTerm b = new PolynomialTerm(0.5, bv);
        String expectv[] = {"a", "b"};
        PolynomialTerm ab = PolynomialTerm.multiply(a, b);
        assertArrayEquals(ab.getVariables(), expectv);
        assertTrue(ab.getCoefficient().equals(BigDecimal.valueOf(12300*0.5)));
    }
}
