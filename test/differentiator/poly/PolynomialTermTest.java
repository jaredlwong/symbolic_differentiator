package differentiator.poly;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class PolynomialTermTest {
    @Test
    public void basicTest() {
        PolynomialTerm a = new PolynomialTerm(100, "a", "a", "c");
        PolynomialTerm b = new PolynomialTerm(2, "a", "a", "c");
        PolynomialTerm ab = PolynomialTerm.multiply(a, b);
        System.out.println(a);
        System.out.println(b);
        System.out.println(ab);
        System.out.println(a.getVariables().hashCode());
        System.out.println(b.getVariables().hashCode());
    }

    @Test
    public void oneTest() {
        PolynomialTerm one = new PolynomialTerm(1, "a");
        System.out.println(one);
        System.out.println(one.getVariables().size());
    }
}
